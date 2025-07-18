<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>BOOK_STORE</title>
  <link rel="stylesheet" href="<c:url value='/resources/Styles.css'/>" />
</head>
<body>
  <div class="body-container" style="width: 38%">
    <c:choose>
      <c:when test="${book != null}">
        <h2>Edit Records</h2>
        <div class="form-wrapper">
          <form action="update" method="post">
            <input type="hidden" name="id" value="${book.id}"/>
            <input type="text" name="author"     placeholder="Author"     value="${book.author}"/>
            <input type="text" name="customer"   placeholder="Customer"   value="${book.customerName}"/>
            <input type="text" name="bookName"   placeholder="Book Name"  value="${book.bookName}"/>
            <input type="number" name="price"    placeholder="Price"      value="${book.price}"/>
            <input type="submit" value="Update"/>
          </form>
        </div>
      </c:when>
      <c:otherwise>
        <h2>New Record</h2>
        <div class="form-wrapper">
          <form action="insert" method="post">
            <input type="text" name="author"     placeholder="Author"/>
            <input type="text" name="customer"   placeholder="Customer"/>
            <input type="text" name="bookName"   placeholder="Book Name"/>
            <input type="number" name="price"    placeholder="Price"/>
            <input type="submit" value="Save"/>
          </form>
        </div>
      </c:otherwise>
    </c:choose>
    <div style="text-align: center; margin-top: 20px;">
      <a href="list">Cancel</a>
    </div>
  </div>
</body>
</html>

