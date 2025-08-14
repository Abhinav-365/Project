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
                case "/signup":      showSignupForm(req, resp);  break;
                case "/register":    registerUser(req, resp);    break;
                case "/logout":      logoutUser(req, resp);      break;
                case "/users":       listUsers(req, resp);       break;
                case "/delete-user":
                    deleteUser(req, resp);
                    break;
                case "/user-form":
                    showUserForm(req, resp);
                    break;
                case "/addUser":
                    addUser(req, resp);
                    break;


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

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userName") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        int page = 1;
        int recordsPerPage = 7;

        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }
        
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);


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
	  
	    HttpSession session = req.getSession(false);
	    if (session == null || session.getAttribute("userName") == null) {
	        resp.sendRedirect("login.jsp");
	        return;
	    }

	    resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    resp.setHeader("Pragma", "no-cache");
	    resp.setDateHeader("Expires", 0);

        String keyword = req.getParameter("query");
        if (keyword == null || keyword.trim().isEmpty()) {
            resp.sendRedirect("list");
            return;
        }

        int page = 1;
        int recordsPerPage = 5;

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

	    HttpSession session = req.getSession(false);
	    if (session == null || session.getAttribute("userName") == null) {
	        resp.sendRedirect("login.jsp");
	        return;
	    }

	    resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    resp.setHeader("Pragma", "no-cache");
	    resp.setDateHeader("Expires", 0);

	    logger.info("Showing book form");
	    req.getRequestDispatcher("book-form.jsp").forward(req, resp);
	}


    private void insert(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userName") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);
        logger.info("Inserting a new book");
        Book book = new Book(
                req.getParameter("author"),
                req.getParameter("customer"),
                Float.parseFloat(req.getParameter("price")),
                req.getParameter("bookName"),
                req.getParameter("issuedDate"),
                req.getParameter("updatedDate")
        );

        dao.insertBook(book);
        logger.debug("Book inserted: " + book);
        req.setAttribute("message", "Book inserted successfully!");
        list(req, resp); // forward with message
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userName") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);

        int id = Integer.parseInt(req.getParameter("id"));
        Book existing = dao.getBook(id);
        logger.info("Editing book with ID: " + id);
        req.setAttribute("book", existing);
        req.getRequestDispatcher("book-form.jsp").forward(req, resp);
    }


    private void update(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userName") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);
        Book book = new Book(
                Integer.parseInt(req.getParameter("id")),
                req.getParameter("author"),
                req.getParameter("customer"),
                Float.parseFloat(req.getParameter("price")),
                req.getParameter("bookName"),
                req.getParameter("issuedDate"),
                req.getParameter("updatedDate")
        );
        dao.updateBook(book);
        logger.info("Updated book: " + book);
        req.setAttribute("message", "Customer ID: "+req.getParameter("id") +" updated successfully!");
        list(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userName") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);
        int id = Integer.parseInt(req.getParameter("id"));
        dao.deleteBook(id);
        logger.warn("Deleted book with ID: " + id);
        req.setAttribute("message", "Customer ID: "+ id+" deleted successfully!");
        list(req, resp);
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
            session.setAttribute("userName", username);

            dao.saveOTPToDatabase(username, otp); // Save OTP to DB
            
            String finalOtp = otp;
            String finalUsername = username;
            String finalEmail=dao.userEmail(finalUsername);
            System.out.println(finalEmail);
            
            new Thread(() -> {
                try {
                    sendOTPEmail(finalEmail, finalOtp);
                } catch (Exception e) {
                    logger.error("Error sending OTP email in background", e);
                }
            }).start();

            req.setAttribute("message", "OTP sent to your registered email.");
            req.getRequestDispatcher("otp-verify.jsp").forward(req, resp);

        } else {
            logger.warn("Login failed");
            req.setAttribute("errorMessage", "Session Expired!");
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
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Your One-Time Password (OTP) for Login Verification");
            message.setText("Hello [User Name],\n"
            		+ "\n"
            		+ "We have received a login request for your account on the Book Store Management System.\n"
            		+ "\n"
            		+ "Your One-Time Password (OTP) is:"	+ otp 
            		+"\n"
            		+ "This OTP will expire in 5 minutes, so please use it promptly to complete your login process.\n"
            		+ "\n"
            		+ "If you did not initiate this request, please ignore this email or contact our support team immediately.\n"
            		+ "\n"
            		+ "Thank you,\n"
            		+ "Book Store Management System Team\n"
            		+ "\n"
            		+ "Confidentiality Notice:\n"
            		+ "This email contains confidential information intended only for the registered user of the Book Store Management System. If you are not the intended recipient, please delete this email immediately. Sharing or disclosing the One-Time Password (OTP) to others is strictly prohibited, as it may compromise your account’s security. ");

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

        if (session == null || session.getAttribute("userName") == null) {
            req.setAttribute("errorMsg", "Session expired. Please log in again.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        String username = (String) session.getAttribute("userName");

        try {
            boolean isValid = dao.verifyOTPFromDatabase(username, enteredOtp);
            if (isValid) {
                logger.info("OTP verified successfully");
                dao.recordLoginTime(username);
                dao.clearOTP(username);
                list(req, resp);
            } else {
                logger.warn("Invalid or expired OTP");
                req.setAttribute("errorMsg", "Invalid or expired OTP. Please try again.");
                req.getRequestDispatcher("otp-verify.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            logger.error("Error verifying OTP from DB", e);
            throw new ServletException(e);
        }
    }
    
    private void showSignupForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("signup.jsp").forward(req, resp);
    }

    private void registerUser(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("userName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        if (username == null || email == null || password == null || confirmPassword == null ||
            username.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {
            req.setAttribute("errorMsg", "All fields are required!");
            req.getRequestDispatcher("signup.jsp").forward(req, resp);
            return;
        }

        if (!password.equals(confirmPassword)) {
            req.setAttribute("errorMsg", "Passwords do not match!");
            req.getRequestDispatcher("signup.jsp").forward(req, resp);
            return;
        }

        try {
            if (dao.isUserExists(username)) {
                req.setAttribute("errorMsg", "Username already exists!");
                req.getRequestDispatcher("signup.jsp").forward(req, resp);
                return;
            }

            dao.insertUser(username, dao.sha256(password), email);

            String otp = String.valueOf(100000 + new Random().nextInt(900000));
            dao.saveOTPToDatabase(username, otp);

            HttpSession session = req.getSession();
            session.setAttribute("userName", username);

            new Thread(() -> {
                try {
                    sendOTPEmail(email, otp);
                } catch (Exception e) {
                    logger.error("Error sending OTP email in background", e);
                }
            }).start();

            req.setAttribute("message", "Registration successful. OTP sent to your email.");
            req.getRequestDispatcher("otp-verify.jsp").forward(req, resp);

        } catch (Exception e) {
            logger.error("Error during registration", e);
            throw new ServletException(e);
        }
    }

    private void logoutUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String username = (String) session.getAttribute("userName");
            try {
                dao.recordLogoutTime(username);
            } catch (SQLException e) {
                logger.error("Error updating logout time", e);
            }
            session.invalidate();
        }
        response.sendRedirect("login.jsp");
    }
    
    private void listUsers(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, ServletException, IOException {
        
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userName") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        List<User> users = dao.listUsers();  // Call your DAO method that returns List<User>
        System.out.println("Users found: " + users.size());
        for (User u : users) {
            System.out.println("User: " + u.getId() + ", " + u.getUsername() + ", " + u.getEmail());
        }

        req.setAttribute("listUsers", users);
        req.getRequestDispatcher("user-list.jsp").forward(req, resp);
    }
    private void deleteUser(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userName") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String idStr = req.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            req.setAttribute("errorMsg", "User ID missing.");
            listUsers(req, resp);
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            boolean deleted = dao.deleteUser(id);
            if (deleted) {
                req.setAttribute("message", "User ID " + id + " deleted successfully.");
            } else {
                req.setAttribute("errorMsg", "Failed to delete User ID " + id);
            }
        } catch (NumberFormatException e) {
            req.setAttribute("errorMsg", "Invalid User ID.");
        }

        listUsers(req, resp);
    }

    private void showUserForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userName") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        req.getRequestDispatcher("user-form.jsp").forward(req, resp);
    }
    
    private void addUser(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userName") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String userType = req.getParameter("userType");

        if (username == null || username.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty() ||
            userType == null || userType.trim().isEmpty()) {
            
            req.setAttribute("errorMsg", "All fields are required!");
            req.getRequestDispatcher("user-form.jsp").forward(req, resp);
            return;
        }

        if (!password.equals(confirmPassword)) {
            req.setAttribute("errorMsg", "Passwords do not match!");
            req.getRequestDispatcher("user-form.jsp").forward(req, resp);
            return;
        }

        try {
            if (dao.isUserExists(username)) {
                req.setAttribute("errorMsg", "Username already exists!");
                req.getRequestDispatcher("user-form.jsp").forward(req, resp);
                return;
            }

            String passwordHash = dao.sha256(password);
            boolean inserted = dao.insertUserWithType(username, passwordHash, email, userType);

            if (inserted) {
                req.setAttribute("message", "User added successfully!");
                listUsers(req, resp);
            } else {
                req.setAttribute("errorMsg", "Failed to add user!");
                req.getRequestDispatcher("user-form.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            logger.error("Error adding user", e);
            throw new ServletException(e);
        }
    }


}