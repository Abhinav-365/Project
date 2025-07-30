package net.codejava.bookstore;

import java.sql.*;
import java.util.*;
import java.security.MessageDigest;


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

    public boolean loginStore(String username, String password) throws SQLException {
        String sql = "SELECT password FROM login WHERE user_name = ?";
        connect();

        boolean auth = false;

        try (PreparedStatement smt = jdbcConnection.prepareStatement(sql)) {
            smt.setString(1, username);
            try (ResultSet rs = smt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    String inputHash = sha256(password); 
                    auth = storedHash.equals(inputHash);
                }
            }
        }

        return auth;
    }

    public String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
   
   public List<Book> listBooksPaginated(int offset, int limit) throws SQLException {
       List<Book> list = new ArrayList<>();
       String sql = "SELECT * FROM Book_Store LIMIT ?, ?";
       connect();
       PreparedStatement ps = jdbcConnection.prepareStatement(sql);
       ps.setInt(1, offset);
       ps.setInt(2, limit);
       ResultSet rs = ps.executeQuery();

       while (rs.next()) {
           list.add(new Book(
               rs.getInt("Id"),
               rs.getString("Author_Name"),
               rs.getString("Customer_Name"),
               rs.getFloat("Price"),
               rs.getString("Book_Name")
           ));
       }
       rs.close(); ps.close(); disconnect();
       return list;
   }

   public int countBooks() throws SQLException {
       String sql = "SELECT COUNT(*) FROM Book_Store";
       connect();
       Statement stmt = jdbcConnection.createStatement();
       ResultSet rs = stmt.executeQuery(sql);
       rs.next();
       int total = rs.getInt(1);
       rs.close(); stmt.close(); disconnect();
       return total;
   }
   
   public List<Book> searchBooks(String keyword) throws SQLException {
       List<Book> list = new ArrayList<>();
       String sql = "SELECT * FROM Book_Store " +
                    "WHERE Author_Name   LIKE ? " +
                    "   OR Customer_Name LIKE ? " +
                    "   OR Book_Name     LIKE ?";
       connect();
       PreparedStatement ps = jdbcConnection.prepareStatement(sql);
       String kw = "%" + keyword + "%";
       ps.setString(1, kw);
       ps.setString(2, kw);
       ps.setString(3, kw);
       ResultSet rs = ps.executeQuery();

       while (rs.next()) {
           list.add(new Book(
               rs.getInt("Id"),
               rs.getString("Author_Name"),
               rs.getString("Customer_Name"),
               rs.getFloat("Price"),
               rs.getString("Book_Name")
           ));
       }
       rs.close();
       ps.close();
       disconnect();
       return list;
   }

   public List<Book> searchBooksPaginated(String keyword, int offset, int limit) throws SQLException {
	    List<Book> list = new ArrayList<>();
	    String sql = "SELECT * FROM Book_Store WHERE Author_Name LIKE ? OR Customer_Name LIKE ? OR Book_Name LIKE ? LIMIT ?, ?";
	    connect();
	    PreparedStatement ps = jdbcConnection.prepareStatement(sql);
	    String kw = "%" + keyword + "%";
	    ps.setString(1, kw);
	    ps.setString(2, kw);
	    ps.setString(3, kw);
	    ps.setInt(4, offset);
	    ps.setInt(5, limit);
	    ResultSet rs = ps.executeQuery();
	    while (rs.next()) {
	        list.add(new Book(
	            rs.getInt("Id"),
	            rs.getString("Author_Name"),
	            rs.getString("Customer_Name"),
	            rs.getFloat("Price"),
	            rs.getString("Book_Name")
	        ));
	    }
	    rs.close();
	    ps.close();
	    disconnect();
	    return list;
	}

	public int countSearchResults(String keyword) throws SQLException {
	    String sql = "SELECT COUNT(*) FROM Book_Store WHERE Author_Name LIKE ? OR Customer_Name LIKE ? OR Book_Name LIKE ?";
	    connect();
	    PreparedStatement ps = jdbcConnection.prepareStatement(sql);
	    String kw = "%" + keyword + "%";
	    ps.setString(1, kw);
	    ps.setString(2, kw);
	    ps.setString(3, kw);
	    ResultSet rs = ps.executeQuery();
	    int count = 0;
	    if (rs.next()) {
	        count = rs.getInt(1);
	    }
	    rs.close();
	    ps.close();
	    disconnect();
	    return count;
	}
}