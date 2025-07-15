package net.codejava.bookstore;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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
                case "/new":
                    showForm(req, resp);
                    break;
                case "/insert":
                    insert(req, resp);
                    break;
                case "/edit":
                    showEdit(req, resp);
                    break;
                case "/update":
                    update(req, resp);
                    break;
                case "/delete":
                    delete(req, resp);
                    break;
                default:
                    list(req, resp);
                    break;
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
        logger.info("Listing all books");
        List<Book> list = dao.listAllBooks();
        req.setAttribute("listBook", list);
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
}