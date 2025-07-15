package net.codejava.bookstore;

import java.sql.*;
import java.util.*;

public class BookDAO {
    private final String URL = "jdbc:mysql://localhost:3306/Store";
    private final String Username = "root";
    private final String Password = "asdf@1234";
    private Connection jdbcConnection;

     void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try { Class.forName("com.mysql.cj.jdbc.Driver"); } 
            catch (ClassNotFoundException e) { throw new SQLException(e); }
            jdbcConnection = DriverManager.getConnection(URL,Username,Password);
        }
    }

     void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed())
            jdbcConnection.close();
    }

    public List<Book> listAllBooks() throws SQLException {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM Book_Store";
        connect();
        Statement stmt = jdbcConnection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            list.add(new Book(
                rs.getInt("Id"),
                rs.getString("Author_Name"),
                rs.getString("Customer_Name"),
                rs.getFloat("Price"),
                rs.getString("Book_Name")
            ));
        }
        rs.close(); stmt.close(); disconnect();
        return list;
    }

    public boolean insertBook(Book book) throws SQLException {
        String sql = "INSERT INTO Book_Store SET Author_Name=?,Price=?,Book_Name=?,Customer_Name=?";
        connect();
        PreparedStatement ps = jdbcConnection.prepareStatement(sql);
        ps.setString(1, book.getAuthor());
        ps.setFloat(2, book.getPrice());
        ps.setString(3,book.getBookName());
        ps.setString(4, book.getCustomerName());
        boolean row = ps.executeUpdate() > 0;
        ps.close(); disconnect();
        return row;
    }

    public boolean updateBook(Book book) throws SQLException {
        String sql = "UPDATE Book_Store SET Author_Name=?, Price=?, Customer_Name=?, Book_Name=? WHERE Id=?";
        connect();
        PreparedStatement ps = jdbcConnection.prepareStatement(sql);
        ps.setString(1, book.getAuthor());
        ps.setFloat(2, book.getPrice());
        ps.setString(3, book.getCustomerName());
        ps.setString(4,book.getBookName());
        ps.setInt(5, book.getId());
        boolean row = ps.executeUpdate() > 0;
        ps.close(); disconnect();
        return row;
    }

    public boolean deleteBook(int id) throws SQLException {
        String sql = "DELETE FROM Book_Store WHERE Id=?";
        connect();
        PreparedStatement ps = jdbcConnection.prepareStatement(sql);
        ps.setInt(1, id);
        boolean row = ps.executeUpdate() > 0;
        ps.close(); disconnect();
        return row;
    }

   public Book getBook(int id) throws SQLException {
        Book book = null;
        String sql = "SELECT * FROM Book_Store WHERE Id=?";
        connect();
        PreparedStatement ps = jdbcConnection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            book = new Book(
                rs.getInt("Id"),
                rs.getString("Author_Name"),
                rs.getString("Customer_Name"),
                rs.getFloat("Price"),
                rs.getString("Book_Name")
            );
        }
        rs.close(); ps.close(); disconnect();
        return book;
    }
}
