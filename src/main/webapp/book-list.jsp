<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>BOOK_STORE</title>
</head>
<body>
<h2>Book Store Records</h2>
<a href="new">Add New</a>
<table border="1">
  <tr><th>ID</th><th>Author</th><th>Customer</th><th>Price</th><th>Book_Name</th><th>Actions</th></tr>
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
</body>
</html>
