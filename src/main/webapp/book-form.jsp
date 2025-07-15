<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<c:choose>
  <c:when test="${book != null}">
    <h2>Edit Record</h2>
    <form action="update" method="post">
      <input type="hidden" name="id" value="${book.id}"/>
      Author:   <input name="author" value="${book.author}"/><br/>
      Customer: <input name="customer" value="${book.customerName}"/><br/>
      Book Name:<input name="bookName" value="${book.bookName}"/><br/>
      Price:    <input name="price" value="${book.price}"/><br/>
      <input type="submit" value="Update"/>
    </form>
  </c:when>
  <c:otherwise>
    <h2>New Record</h2>
    <form action="insert" method="post">
      Author:   <input name="author" value=""/><br/>
      Customer: <input name="customer" value=""/><br/>
      Book Name:<input name="bookName" value=""/><br/>
      Price:    <input name="price" value=""/><br/>
      <input type="submit" value="Save"/>
    </form>
  </c:otherwise>
</c:choose>
<a href="list">Cancel</a>
</body>
</html>
