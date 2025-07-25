<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>BOOK_STORE</title>
  <link rel="stylesheet" href="<c:url value='/resources/Styles.css'/>" />
</head>
<body>
  <!-- Logo -->
  <div style="position: absolute; top: 30px; left: 40px; z-index: 1000;">
    <img src="<c:url value='/resources/logo3.png' />" alt="BookStore Logo" style="height: 180px;" />
  </div>

  <!-- Form Section -->
  <div class="body-container" style="width: 38%">
    <c:choose>
      <c:when test="${book != null}">
        <h2>Edit Records</h2>
        <div class="form-wrapper">
          <form action="update" method="post">
            <input type="hidden" name="id" value="${book.id}" />
            
            <input type="text" name="author" placeholder="Author" value="${book.author}"
                   required pattern="[A-Za-z\s]{1,50}" maxlength="50"
                   title="Only letters and spaces, up to 50 characters" />

            <input type="text" name="customer" placeholder="Customer" value="${book.customerName}"
                   required pattern="[A-Za-z\s]{1,50}" maxlength="50"
                   title="Only letters and spaces, up to 50 characters" />

            <input type="text" name="bookName" placeholder="Book Name" value="${book.bookName}"
                   required pattern="[A-Za-z0-9\s@&:;.,!?()\-']{1,100}" maxlength="100"
                   title="Letters, numbers and spaces, up to 100 characters" />

            <input type="number" name="price" placeholder="Price" value="${book.price}"
                   required step="0.01" min="0" max="100000"
                   title="Enter a valid price (0 - 100000)" />

            <input type="submit" value="Update" />
          </form>
        </div>
      </c:when>

      <c:otherwise>
        <h2>New Record</h2>
        <div class="form-wrapper">
          <form action="insert" method="post">

            <input type="text" name="author" placeholder="Author"
                   required pattern="[A-Za-z\s]{1,50}" maxlength="50"
                   title="Only letters and spaces, up to 50 characters" />

            <input type="text" name="customer" placeholder="Customer"
                   required pattern="[A-Za-z\s]{1,50}" maxlength="50"
                   title="Only letters and spaces, up to 50 characters" />

            <input type="text" name="bookName" placeholder="Book Name"
                   required pattern="[A-Za-z0-9\s@&:;.,!?()\-']{1,100}
                   " maxlength="100"
                   title="Letters, numbers and spaces, up to 100 characters" />

            <input type="number" name="price" placeholder="Price"
                   required step="0.01" min="0" max="100000"
                   title="Enter a valid price (0 - 100000)" />

            <input type="submit" value="Save" />
          </form>
        </div>
      </c:otherwise>
    </c:choose>

    <!-- Cancel Button -->
    <div style="text-align: center; margin-top: 20px;">
      <a href="list">Cancel</a>
    </div>
  </div>
</body>
</html>
