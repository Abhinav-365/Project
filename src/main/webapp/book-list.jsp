<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>BOOK_STORE</title>
  <link rel="stylesheet" href="<c:url value='/resources/Styles.css'/>" /> 
</head>
<body>
 <div style="position: absolute; top: 30px; left: 40px; z-index: 1000;">
 <img src="<c:url value='/resources/logo.png' />" alt="BookStore Logo" style="height: 120px;" />
 </div>
  <div style="position: absolute; top: 40px; right: 20px; z-index: 1000;">
    <a href="login.jsp" onclick="return confirm('Log Out?')"
       style="background-color: red; color: white; 
              padding: 8px 16px; text-decoration: none; 
              border: none; border-radius: 4px; 
              font-weight: bold; font-family: Arial, sans-serif;">
      Log Out
    </a>
  </div>
 <div class="body-container" style="display: flex; flex-direction: column; min-height: 420px;">
  <h2>Book Store Records</h2>
  <a href="new">Add New</a>

  <table>
    <tr>
      <th>ID</th><th>Author</th><th>Customer</th><th>Price</th><th>Book Name</th><th>Actions</th>
    </tr>
    <c:forEach var="b" items="${listBook}">
      <tr>
        <td>${b.id}</td>
        <td>${b.author}</td>
        <td>${b.customerName}</td>
        <td>${b.price}</td>
        <td>${b.bookName}</td>
        <td>
          <a href="edit?id=${b.id}">Edit</a>
          <a href="delete?id=${b.id}" onclick="return confirm('Delete?')">Delete</a>
        </td>
      </tr>
    </c:forEach>
  </table>

  <!-- Pagination at bottom of body-container -->
  <div style="
      margin-top: auto;
      padding-top: 10px;
      text-align: center;
      border-top: 1px solid #ddd;
  ">
    <c:forEach var="i" begin="1" end="${totalPages}">
      <a href="list?page=${i}" style="
          display: inline-block;
          margin: 0 6px;
          padding: 6px 12px;
          border: 1px solid #3399ff;
          border-radius: 6px;
          text-decoration: none;
          font-weight: bold;
          background-color: ${i == currentPage ? '#3399ff' : 'white'};
          color: ${i == currentPage ? 'white' : '#3399ff'};
      ">
        ${i}
      </a>
    </c:forEach>
  </div>
</div>
 
  
<footer style="
  width: 100%;
  background: linear-gradient(to right, #b3d9ff, #e6f0ff);
  color: #333;
  text-align: center;
  padding: 15px 0;
  position: absolute;
  bottom: 0;
  left: 0;
  font-size: 14px;
">
  <p style="margin: 0;">&copy; 2025 ValueFirst BookStore Inc.</p>
  <a href='/privacy' style='color: #333; text-decoration: none; margin: 0 10px;'>Privacy Policy</a> |
  <a href='/contact' style='color: #333; text-decoration: none; margin: 0 10px;'>Contact Us</a>
</footer>

</body>
</html>