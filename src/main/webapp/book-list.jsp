<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>BOOK STORE</title>
  <link rel="stylesheet" href="<c:url value='/resources/Styles.css'/>" />
</head>
<body style="margin: 0; padding: 0; font-family: Arial, sans-serif; background: linear-gradient(to right, #b3d9ff, #e6f0ff); min-height: 100vh;">

  <!-- Logo -->
  <div style="position: absolute; top: 30px; left: 40px; z-index: 1000;">
    <img src="<c:url value='/resources/logo3.png' />" alt="BookStore Logo" style="height: 180px;" />
  </div>

  <!-- Logout Button -->
  <div style="position: absolute; top: 40px; right: 20px; z-index: 1000;">
    <a href="login.jsp" onclick="return confirm('Log Out?')"
       style="background-color: red; color: white; padding: 8px 16px;
              text-decoration: none; border: none; border-radius: 4px;
              font-weight: bold;">
      Log Out
    </a>
  </div>

  <!-- Spacer to push content below the logo -->
  <div style="height: 140px;"></div>

  <!-- Main Container Wrapper -->
  <div style="max-width: 1000px; margin: 0 auto; padding: 0 20px;">
    <div style="background-color: white; padding: 20px 30px;
                border-radius: 10px; box-shadow: 0 4px 12px rgba(0,0,0,0.1);
                display: flex; flex-direction: column; align-items: center;">
      <h1 style="margin-bottom: -20px;">Book Store Records</h1>

      <!-- Search Bar -->
      <form action="search" method="get" style="display: flex; gap: 10px; align-items: center; width: 100%; margin-bottom: 20px;">
        <input type="text" name="query" value="${param.query}" placeholder="Search"
               style="flex: 1; padding: 7px; border: 2px solid #ccc; border-radius: 6px; font-size: 15px; box-sizing: border-box;" />
        <button type="submit"
                style="padding: 9px 20px; background-color: #3399ff; color: white; font-weight: bold;
                       border: none; border-radius: 6px; cursor: pointer;">
          Search
        </button>
        <a href="list"
           style="padding: 7px 20px; background-color: #ccc; color: black; font-weight: bold;
                  border-radius: 6px; text-decoration: none;">
          Cancel
        </a>
      </form>

      <c:if test="${not empty searchQuery}">
        <p style="text-align: center; margin-top: -10px; margin-bottom: 10px;">Search results for: "<strong>${searchQuery}</strong>"</p>
      </c:if>

      <!-- Add New Button -->
      <div style="width: 100%; text-align: right; margin-bottom: -12px;">
        <a href="new" style="text-decoration: none; font-weight: bold; color: #3399ff;">+ Add New</a>
      </div>

      <!-- Book Table -->
      <table style="width: 100%; border-collapse: collapse; text-align: center;">
        <tr style="background-color: #f0f0f0; color: black; font-weight: bold; font-size: 16px; border-bottom: 2px solid #ccc;">
          <th style="padding: 12px; border: 1px solid #ddd;">ID</th>
          <th style="padding: 12px; border: 1px solid #ddd;">Author</th>
          <th style="padding: 12px; border: 1px solid #ddd;">Customer</th>
          <th style="padding: 12px; border: 1px solid #ddd;">Price</th>
          <th style="padding: 12px; border: 1px solid #ddd;">Book Name</th>
          <th style="padding: 12px; border: 1px solid #ddd;">Actions</th>
        </tr>
        <c:forEach var="b" items="${listBook}">
          <tr style="background-color: #f9f9f9;">
            <td style="padding: 8px;">${b.id}</td>
            <td>${b.author}</td>
            <td>${b.customerName}</td>
            <td>${b.price}</td>
            <td>${b.bookName}</td>
            <td>
              <a href="edit?id=${b.id}" style="color: #0077cc; text-decoration: none; margin-right: 8px;">Edit</a>
              <a href="delete?id=${b.id}" onclick="return confirm('Delete?')"
                 style="color: red; text-decoration: none;">Delete</a>
            </td>
          </tr>
        </c:forEach>
      </table>

      <!-- Enhanced Pagination -->
      <div style="margin-top: 30px; text-align: center;">
        <c:if test="${totalPages > 1}">
          <c:set var="window" value="2" />
          <c:set var="start" value="${currentPage - window}" />
          <c:set var="end" value="${currentPage + window}" />

          <!-- First Page + Ellipsis -->
          <c:if test="${start > 1}">
            <a href="list?page=1"
               style="margin: 0 4px; padding: 6px 12px; border: 1px solid #3399ff; border-radius: 6px; text-decoration: none; font-weight: bold; background-color: white; color: #3399ff;">1</a>
            <span style="margin: 0 5px;">...</span>
          </c:if>

          <!-- Page Numbers -->
          <c:forEach var="i" begin="${start < 1 ? 1 : start}" end="${end > totalPages ? totalPages : end}">
            <a href="list?page=${i}"
               style="display: inline-block; margin: 0 4px; padding: 6px 12px;
                      border: 1px solid #3399ff; border-radius: 6px; text-decoration: none;
                      font-weight: bold;
                      background-color: ${i == currentPage ? '#3399ff' : 'white'};
                      color: ${i == currentPage ? 'white' : '#3399ff'};">
              ${i}
            </a>
          </c:forEach>

          <!-- Ellipsis + Last Page -->
          <c:if test="${end < totalPages}">
            <span style="margin: 0 5px;">...</span>
            <a href="list?page=${totalPages}"
               style="margin: 0 4px; padding: 6px 12px; border: 1px solid #3399ff; border-radius: 6px; text-decoration: none; font-weight: bold; background-color: white; color: #3399ff;">${totalPages}</a>
          </c:if>
        </c:if>
      </div>
    </div>
  </div>

  <!-- Footer -->
  <footer style="width: 100%; background: linear-gradient(to right, #b3d9ff, #e6f0ff);
                 color: #333; text-align: center; padding: 8px 0;
                 position: fixed; bottom: 0; left: 0; font-size: 14px;">
    <p style="margin: 0;">&copy; 2025 ValueFirst BookStore Inc.</p>
    <a href='/privacy' style='color: #333; text-decoration: none; margin: 0 5px;'>Privacy Policy</a> |
    <a href='/contact' style='color: #333; text-decoration: none; margin: 0 5px;'>Contact Us</a>
  </footer>

</body>
</html>
