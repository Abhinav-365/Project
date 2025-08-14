package net.codejava.bookstore;

import java.sql.*;
import java.util.*;
import java.security.MessageDigest;

public class BookDAO {
    private final String URL = "jdbc:mysql://localhost:3306/mystore";
    private final String Username = "root";
    private final String Password = "asdf@1234";
    private Connection jdbcConnection;

    void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(URL, Username, Password);
        }
    }

    void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed())
            jdbcConnection.close();
    }

    public List<Book> listAllBooks() throws SQLException {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM book_store WHERE entry_status = 1 AND user_id=1";
        connect();
        Statement stmt = jdbcConnection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            list.add(new Book(
                rs.getInt("id"),
                rs.getString("author_name"),
                rs.getString("customer_name"),
                rs.getFloat("price"),
                rs.getString("book_name"),
                rs.getString("inserted_date"),
                rs.getString("updated_date")
            ));
        }
        rs.close(); stmt.close(); disconnect();
        return list;
    }

    public boolean insertBook(Book book) throws SQLException {
        String sql = "INSERT INTO book_store (author_name, price, customer_name, book_name, inserted_date,updated_date)"
                   + "VALUES (?, ?,?, ?, NOW(),NOW())";
        connect();
        PreparedStatement ps = jdbcConnection.prepareStatement(sql);
        ps.setString(1, book.getAuthor());
        ps.setFloat(2, book.getPrice());
        ps.setString(3, book.getBookName());
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
        disconnect();
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
        String sql = "UPDATE book_store SET author_name=?, price=?, customer_name=?, book_name=?, updated_date=now() WHERE id=?";
        connect();
        PreparedStatement ps = jdbcConnection.prepareStatement(sql);
        ps.setString(1, book.getAuthor());
        ps.setFloat(2, book.getPrice());
        ps.setString(3, book.getCustomerName());
        ps.setString(4, book.getBookName());
        ps.setInt(5, book.getId());
        boolean row = ps.executeUpdate() > 0;
        ps.close();
        disconnect();
        return row;
    }

    public boolean deleteBook(int id) throws SQLException {
        String sql = "UPDATE book_store SET entry_status=0 WHERE id=?";
        connect();
        PreparedStatement ps = jdbcConnection.prepareStatement(sql);
        ps.setInt(1, id);
        boolean row = ps.executeUpdate() > 0;
        ps.close(); disconnect();
        return row;
    }

    public Book getBook(int id) throws SQLException {
        Book book = null;
        String sql = "SELECT * FROM book_store WHERE id = ? ";
        connect();
        PreparedStatement ps = jdbcConnection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            book = new Book(
                rs.getInt("id"),
                rs.getString("author_name"),
                rs.getString("customer_name"),
                rs.getFloat("price"),
                rs.getString("book_name"),
                rs.getString("inserted_date"),
                rs.getString("updated_date")
            );
        }
        rs.close(); ps.close(); disconnect();
        return book;
    }

    public List<Book> listBooksPaginated(int offset, int limit) throws SQLException {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM book_store WHERE entry_status = 1 LIMIT ?, ?";
        connect();
        PreparedStatement ps = jdbcConnection.prepareStatement(sql);
        ps.setInt(1, offset);
        ps.setInt(2, limit);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Book(
                rs.getInt("id"),
                rs.getString("author_name"),
                rs.getString("customer_name"),
                rs.getFloat("price"),
                rs.getString("book_name"),
                rs.getString("inserted_date"),
                rs.getString("updated_date")
            ));
        }
        rs.close(); ps.close(); disconnect();
        return list;
    }

    public int countBooks() throws SQLException {
        String sql = "SELECT COUNT(*) FROM book_store WHERE entry_status=1";
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
        String sql = "SELECT * FROM book_store WHERE entry_status=1 AND (uthor_Name LIKE ? OR Customer_Name LIKE OR Book_Name LIKE ?)";
        connect();
        PreparedStatement ps = jdbcConnection.prepareStatement(sql);
        String kw = "%" + keyword + "%";
        ps.setString(1, kw);
        ps.setString(2, kw);
        ps.setString(3, kw);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Book(
                rs.getInt("id"),
                rs.getString("author_name"),
                rs.getString("customer_name"),
                rs.getFloat("price"),
                rs.getString("book_name"),
                rs.getString("inserted_date"),
                rs.getString("updated_date")
            ));
        }
        rs.close(); ps.close(); disconnect();
        return list;
    }

    public List<Book> searchBooksPaginated(String keyword, int offset, int limit) throws SQLException {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM book_store WHERE entry_status=1 AND (Author_Name LIKE ? OR Customer_Name LIKE ? OR Book_Name LIKE ?) LIMIT ?, ?";
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
                rs.getInt("id"),
                rs.getString("author_name"),
                rs.getString("customer_name"),
                rs.getFloat("price"),
                rs.getString("book_name"),
                rs.getString("inserted_date"),
                rs.getString("updated_date")
            ));
        }
        rs.close(); ps.close(); disconnect();
        return list;
    }

    public int countSearchResults(String keyword) throws SQLException {
        String sql = "SELECT COUNT(*) FROM book_store WHERE entry_status=1 AND (Author_Name LIKE ? OR Customer_Name LIKE ? OR Book_Name LIKE ?)";
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
        rs.close(); ps.close(); disconnect();
        return count;
    }

    public void saveOTPToDatabase(String username, String otp) throws SQLException {
        String sql = "UPDATE login SET otp = ?, otp_expiry = ? WHERE user_name = ?";
        connect();
        PreparedStatement ps = jdbcConnection.prepareStatement(sql);
        ps.setString(1, otp);
        ps.setTimestamp(2, new Timestamp(System.currentTimeMillis() + 5 * 60 * 1000)); // 5 min
        ps.setString(3, username);
        ps.executeUpdate();
        ps.close();
        disconnect();
    }

    public boolean verifyOTPFromDatabase(String username, String enteredOtp) throws SQLException {
        String sql = "SELECT otp, otp_expiry FROM login WHERE user_name = ?";
        connect();
        PreparedStatement ps = jdbcConnection.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        boolean isValid = false;

        if (rs.next()) {
            String dbOtp = rs.getString("otp");
            Timestamp expiry = rs.getTimestamp("otp_expiry");
            Timestamp now = new Timestamp(System.currentTimeMillis());

            if (dbOtp != null && dbOtp.equals(enteredOtp) && expiry != null && now.before(expiry)) {
                isValid = true;
            }
        }

        rs.close();
        ps.close();
        disconnect();
        return isValid;
    }

    public void clearOTP(String username) throws SQLException {
        String sql = "UPDATE login SET otp = NULL, otp_expiry = NULL WHERE user_name = ?";
        connect();
        PreparedStatement ps = jdbcConnection.prepareStatement(sql);
        ps.setString(1, username);
        ps.executeUpdate();
        ps.close();
        disconnect();
    }
    
    public boolean isUserExists(String username) throws SQLException {
        String sql = "SELECT user_name FROM login WHERE user_name = ?";
        connect();
        PreparedStatement ps = jdbcConnection.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        boolean exists = rs.next();
        rs.close(); ps.close(); disconnect();
        return exists;
    }

    public boolean insertUser(String username, String passwordHash, String email) throws SQLException {
        String sql = "INSERT INTO login (user_name, password, email_id) VALUES (?, ?, ?)";
        connect();
        PreparedStatement ps = jdbcConnection.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, passwordHash);
        ps.setString(3, email);
        boolean inserted = ps.executeUpdate() > 0;
        ps.close(); disconnect();
        return inserted;
    }
    
    public String userEmail(String username) throws SQLException {
        String email = null;
        String sql = "SELECT email_id FROM login WHERE user_name=?";
        connect();

        PreparedStatement ps = jdbcConnection.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            email = rs.getString("email_id");
        }
        rs.close();
        ps.close();
        disconnect();
        
        return email;
    }
    
    public void recordLoginTime(String username) throws SQLException {
        int userId = getUserIdByUsername(username); // You'll need this method
        String sql = "INSERT INTO user_sessions (user_id) VALUES (?)";
        connect();
        PreparedStatement stmt = jdbcConnection.prepareStatement(sql);
        stmt.setInt(1, userId);
        stmt.executeUpdate();
        disconnect();
    }


    
    public void recordLogoutTime(String username) throws SQLException {
        int userId = getUserIdByUsername(username);
        String sql = "UPDATE user_sessions SET logout_time = CURRENT_TIMESTAMP WHERE user_id = ? AND logout_time IS NULL";
        connect();
        PreparedStatement stmt = jdbcConnection.prepareStatement(sql);
        stmt.setInt(1, userId);
        stmt.executeUpdate();
        disconnect();
    }
    
    public int getUserIdByUsername(String username) throws SQLException {
        String sql = "SELECT user_id FROM login WHERE user_name = ?";
        connect();
        PreparedStatement stmt = jdbcConnection.prepareStatement(sql);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        int id = -1;
        if (rs.next()) {
            id = rs.getInt("user_id");
        }
        rs.close();
        stmt.close();
        disconnect();
        return id;
    }

    public List<User> listUsers() throws SQLException {
        List<User> listUsers = new ArrayList<>();
        String sql = "SELECT user_id, user_name, user_type, email_id FROM login";

        connect();
        PreparedStatement stmt = jdbcConnection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setUsername(rs.getString("user_name"));  // fixed
            user.setUserType(rs.getString("user_type"));  // fixed
            user.setEmail(rs.getString("email_id"));
            listUsers.add(user);
        }

        rs.close();
        stmt.close();
        disconnect();

        return listUsers;
    }

    public boolean insertUser(String username, String passwordHash, String email, String userType) throws SQLException {
        String sql = "INSERT INTO login (user_name, password, email_id, user_type) VALUES (?, ?, ?, ?)";
        connect();
        PreparedStatement ps = jdbcConnection.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, passwordHash);
        ps.setString(3, email);
        ps.setString(4, userType);
        boolean inserted = ps.executeUpdate() > 0;
        ps.close();
        disconnect();
        return inserted;
    }

    public boolean deleteUser(int userId) throws SQLException {
        connect();

        try {
            // Delete dependent records first
            String sqlBookStore = "DELETE FROM book_store WHERE user_id = ?";
            PreparedStatement psBookStore = jdbcConnection.prepareStatement(sqlBookStore);
            psBookStore.setInt(1, userId);
            psBookStore.executeUpdate();
            psBookStore.close();

            // Now delete from login
            String sqlLogin = "DELETE FROM login WHERE user_id = ?";
            PreparedStatement psLogin = jdbcConnection.prepareStatement(sqlLogin);
            psLogin.setInt(1, userId);
            boolean deleted = psLogin.executeUpdate() > 0;
            psLogin.close();

            return deleted;
        } finally {
            disconnect();
        }
    }



    public boolean insertUserWithType(String username, String passwordHash, String email, String userType) throws SQLException {
        String sql = "INSERT INTO login (user_name, password, email_id, user_type) VALUES (?, ?, ?, ?)";
        connect();
        PreparedStatement ps = jdbcConnection.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, passwordHash);
        ps.setString(3, email);
        ps.setString(4, userType);
        boolean inserted = ps.executeUpdate() > 0;
        ps.close();
        disconnect();
        return inserted;
    }


}