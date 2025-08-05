<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>BOOK_STORE</title>
  <link rel="stylesheet" href="<c:url value='/resources/Styles.css'/>" />
</head>
<body>
<!-- Logo (clickable) -->
<div style="position: absolute; top: 30px; left: 40px; z-index: 1000;">
  <a href="list">
    <img src="<c:url value='/resources/logo3.png' />" alt="BookStore Logo"
         style="height: 150px; margin: -27px; cursor: pointer;" />
  </a>
</div>
  <!-- Form Section -->
  <div class="body-container" style="width: 38%">
    <c:choose>
      <c:when test="${book != null}">
        <h2>Edit Record</h2>
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
  
   <!-- Footer -->
  <footer style="width: 100%; background: linear-gradient(to right, #b3d9ff, #e6f0ff);
                 color: #333; text-align: center; padding: 1px 0;
                 position: fixed; bottom: 0; left: 0; font-size: 14px;">
    <p style="margin: 0;">&copy; 2025 ValueFirst BookStore Inc.</p>
    <a href='/privacy' style='color: #333; text-decoration: none; margin: 0 5px;'>Privacy Policy</a> |
    <a href='/contact' style='color: #333; text-decoration: none; margin: 0 5px;'>Contact Us</a>
  </footer>
</body>
</html>