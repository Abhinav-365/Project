<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>BOOK_STORE</title>
  <link rel="stylesheet" href="<c:url value='/resources/Styles.css'/>" />
</head>
<body class="body-container">
  <h2>Book Store Records</h2>
    <a href="new" 
     style="display: block; width: 120px; margin: 20px auto; text-align: center; padding: 10px;
            border: 2px solid #4CAF50; border-radius: 5px; color: #4CAF50; text-decoration: none;
            font-weight: bold; cursor: pointer;">
     Add New
  </a>
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
</body>
</html>
