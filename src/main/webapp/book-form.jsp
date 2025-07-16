<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>BOOK_STORE</title>
  <link rel="stylesheet" href="<c:url value='/resources/Styles.css'/>" />
</head>
<body>
  <c:choose>
    <c:when test="${book != null}">
      <h2>Edit Record</h2>
      <form action="update" method="post">
        <input type="hidden" name="id" value="${book.id}"/>
        <input type="text" name="author"     placeholder="Author"     value="${book.author}"/>
        <input type="text" name="customer"   placeholder="Customer"   value="${book.customerName}"/>
        <input type="text" name="bookName"   placeholder="Book Name"  value="${book.bookName}"/>
        <input type="number" name="price"    placeholder="Price"      value="${book.price}"/>
        <input type="submit" value="Update"/>
      </form>
    </c:when>
    <c:otherwise>
      <h2>New Record</h2>
      <form action="insert" method="post">
        <input type="text" name="author"     placeholder="Author"/>
        <input type="text" name="customer"   placeholder="Customer"/>
        <input type="text" name="bookName"   placeholder="Book Name"/>
        <input type="number" name="price"    placeholder="Price"/>
        <input type="submit" value="Save"/>
      </form>
    </c:otherwise>
  </c:choose>

  <a href="list" style="display: block; width: 100px; margin: 20px auto; text-align: center; padding: 10px; 
       border: 2px solid #4CAF50; border-radius: 5px; color: #4CAF50; text-decoration: none; font-weight: bold; cursor: pointer;">
       Cancel
  </a>
</body>
</html>
