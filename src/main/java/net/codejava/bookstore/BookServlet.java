package net.codejava.bookstore;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.*;
import org.apache.log4j.Logger;

@WebServlet("/")
public class BookServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private BookDAO dao;
    private Logger logger;

    @Override
    public void init() throws ServletException {
        dao = new BookDAO();
        logger = Logger.getRootLogger();
        logger.info("BookServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getServletPath();
        logger.debug("Handling GET request for: " + action);

        try {
            switch (action) {
                case "/new":         showForm(req, resp);        break;
                case "/insert":      insert(req, resp);          break;
                case "/edit":        showEdit(req, resp);        break;
                case "/update":      update(req, resp);          break;
                case "/delete":      delete(req, resp);          break;
                case "/login":       login(req, resp);           break;
                case "/search":      search(req, resp);          break;
                case "/verify-otp":  verifyOtp(req, resp);       break;
                default:             list(req, resp);            break;
            }
        } catch (SQLException ex) {
            logger.error("SQL Exception in doGet", ex);
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.debug("Handling POST request");
        doGet(req, resp);
    }

    private void list(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, ServletException, IOException {

        int page = 1;
        int recordsPerPage = 6;

        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }

        List<Book> books = dao.listBooksPaginated((page - 1) * recordsPerPage, recordsPerPage);
        int totalRecords = dao.countBooks();
        int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

        req.setAttribute("listBook", books);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        req.getRequestDispatcher("book-list.jsp").forward(req, resp);
    }

    private void search(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, ServletException, IOException {

        String keyword = req.getParameter("query");
        if (keyword == null || keyword.trim().isEmpty()) {
            resp.sendRedirect("list");
            return;
        }

        int page = 1;
        int recordsPerPage = 6;

        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }

        List<Book> results = dao.searchBooksPaginated(keyword, (page - 1) * recordsPerPage, recordsPerPage);
        int totalRecords = dao.countSearchResults(keyword);
        int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

        req.setAttribute("listBook", results);
        req.setAttribute("searchQuery", keyword);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        req.getRequestDispatcher("book-list.jsp").forward(req, resp);
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("Showing book form");
        req.getRequestDispatcher("book-form.jsp").forward(req, resp);
    }

    private void insert(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException {
        logger.info("Inserting a new book");
        Book book = new Book(
                req.getParameter("author"),
                req.getParameter("customer"),
                Float.parseFloat(req.getParameter("price")),
                req.getParameter("bookName")
        );

        dao.insertBook(book);
        logger.debug("Book inserted: " + book);
        resp.sendRedirect("list");
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Book existing = dao.getBook(id);
        logger.info("Editing book with ID: " + id);
        req.setAttribute("book", existing);
        req.getRequestDispatcher("book-form.jsp").forward(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException {
        Book book = new Book(
                Integer.parseInt(req.getParameter("id")),
                req.getParameter("author"),
                req.getParameter("customer"),
                Float.parseFloat(req.getParameter("price")),
                req.getParameter("bookName")
        );
        dao.updateBook(book);
        logger.info("Updated book: " + book);
        resp.sendRedirect("list");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        dao.deleteBook(id);
        logger.warn("Deleted book with ID: " + id);
        resp.sendRedirect("list");
    }

    private void login(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException, ServletException {

        String username = req.getParameter("userName");
        String password = req.getParameter("password");

        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {

            req.setAttribute("errorMsg", "Username and password are required!");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        username = username.trim();

        boolean auth = dao.loginStore(username, password);

        if (auth) {
            logger.info("Login successful, generating OTP...");

            String otp = String.valueOf(100000 + new Random().nextInt(900000));
            HttpSession session = req.getSession();
            session.setAttribute("otp", otp);
            session.setAttribute("userName", username);

            sendOTPEmail(username, otp);
            req.setAttribute("infoMsg", "OTP has been sent to your registered email.");
            req.getRequestDispatcher("otp-verify.jsp").forward(req, resp);

        } else {
            logger.warn("Login failed");
            req.setAttribute("errorMessage", "Invalid username or password");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    private void sendOTPEmail(String recipientEmail, String otp) {
        final String from = "tyagiabhinav200503@gmail.com"; 
        final String pass = "onmcqlmnfxaeriub"; 

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pass);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("mailmeabhinav6@gmail.com"));
            message.setSubject("Your OTP Code");
            message.setText("Your OTP is: " + otp);

            Transport.send(message);
            logger.info("OTP sent to email: " + recipientEmail);
        } catch (MessagingException e) {
            logger.error("Failed to send OTP email", e);
        }
    }

    private void verifyOtp(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String enteredOtp = req.getParameter("otp");
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("otp") == null) {
            req.setAttribute("errorMsg", "Session expired. Please log in again.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        String generatedOtp = (String) session.getAttribute("otp");

        if (enteredOtp.equals(generatedOtp)) {
            logger.info("OTP verified successfully");
            session.removeAttribute("otp");
            resp.sendRedirect("list");
        } else {
            logger.warn("Invalid OTP");
            req.setAttribute("errorMsg", "Invalid OTP. Please try again.");
            req.getRequestDispatcher("otp-verify.jsp").forward(req, resp);
        }
    }
}