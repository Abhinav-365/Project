<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>BOOK_STORE</title>
  <link rel="stylesheet" href="<c:url value='/resources/Styles.css'/>" />
</head>
<body style="margin:0; font-family: Arial, sans-serif; background: linear-gradient(135deg, #0f2027, #203a43, #2c5364); min-height:100vh;">

  <!-- Logo (clickable) -->
  <div style="position: absolute; top: 30px; left: 40px; z-index: 1000;">
    <a href="list">
      <img src="<c:url value='/resources/logo3.png' />" alt="BookStore Logo"
           style="height: 150px; margin: -27px; cursor: pointer;" />
    </a>
  </div>

  <!-- Form Section -->
  <div style="background: rgba(255, 255, 255, 0.08); padding: 30px; border-radius: 10px; box-shadow: 0 4px 20px rgba(0,0,0,0.4); width: 350px; margin: 120px auto 40px auto;">
    <c:choose>
      <c:when test="${book != null}">
<h2 style="color: #ffffff;">Edit Record</h2>
        <div class="form-wrapper">
          <form action="update" method="post">
            <input type="hidden" name="id" value="${book.id}" />
            
            <input type="text" name="author" placeholder="Author Name" value="${book.author}"
                   required pattern="[A-Za-z\s]{1,50}" maxlength="50"
                   title="Only letters and spaces, up to 50 characters" />

            <input type="text" name="customer" placeholder="Customer Name" value="${book.customerName}"
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
<h2 style="color: #ffffff;">New Record</h2>
        <form action="insert" method="post">

          <input type="text" name="author" placeholder="Author Name"
                 required pattern="[A-Za-z\s]{1,50}" maxlength="50"
                 title="Only letters and spaces, up to 50 characters" />

          <input type="text" name="customer" placeholder="Customer Name"
                 required pattern="[A-Za-z\s]{1,50}" maxlength="50"
                 title="Only letters and spaces, up to 50 characters" />

          <input type="text" name="bookName" placeholder="Book Name"
                 required pattern="[A-Za-z0-9\s@&:;.,!?()\-']{1,100}" maxlength="100"
                 title="Letters, numbers and spaces, up to 100 characters" />

          <input type="number" name="price" placeholder="Price"
                 required step="0.01" min="0" max="100000"
                 title="Enter a valid price (0 - 100000)" />

          <input type="submit" value="Save" />
        </form>
      </c:otherwise>
    </c:choose>

    <!-- Cancel Button -->
    <div style="text-align: center; margin-top: 20px;">
      <a href="list" style="color: #ddd; text-decoration: none; font-weight: bold;">Cancel</a>
    </div>
  </div>

<!-- Footer -->
<footer style="width:100%; background:rgba(0,0,0,0.8); color:rgba(0,255,213,0.8);
               text-align:center; padding:8px 0;
               position:fixed; bottom:0; left:0; font-size:14px;">
  <p style="margin:0;">&copy; 2025 BookStore Inc.</p>
  <a href='privacy.jsp' style='color:rgba(0,255,213,0.8); text-decoration:none; margin:0 5px;'>Privacy Policy</a> |
  <a href='contact.jsp' style='color:rgba(0,255,213,0.8); text-decoration:none; margin:0 5px;'>Contact Us</a>
</footer>
</body>
</html>
