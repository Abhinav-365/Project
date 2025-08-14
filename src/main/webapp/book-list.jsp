<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <title>BOOK STORE</title>
  <script>
    if (window.history.replaceState) {
      window.history.replaceState(null, null, window.location.href);
    }
    window.onload = function () {
      if (performance.navigation.type === 2) {
        location.reload(true);
      }
    };
  </script>
</head>
<body style="margin:0; font-family: Arial, sans-serif; 
             background: linear-gradient(135deg, #0f2027, #203a43, #2c5364); 
             display: flex; flex-direction: column; min-height: 100vh; color: white;">


<!-- Logo (clickable) -->
<div style="position: absolute; top: 30px; left: 40px; z-index: 1000;">
  <a href="list">
    <img src="<c:url value='/resources/logo3.png' />" alt="BookStore Logo"
         style="height: 150px; margin: -27px; cursor: pointer;" />
  </a>
</div>

<!-- Logout Button and Users Button below it -->
<div style="position: absolute; top: 40px; right: 20px; z-index: 1000; text-align: right;">
  <a href="login.jsp" onclick="return confirm('Log Out?')"
     style="background-color: red; color: white; padding: 8px 16px;
            text-decoration: none; border: none; border-radius: 4px;
            font-weight: bold; display: inline-block;">
    Log Out
  </a>
  <br/>
  <a href="users" 
     style="background-color: rgba(0,255,213,0.85); color: black; padding: 8px 16px;
            border-radius: 4px; font-weight: bold; text-decoration: none;
            margin-top: 8px; display: inline-block; cursor: pointer;">
    Users
  </a>
</div>




  <!-- Main Container -->
  <div style="background: rgba(255, 255, 255, 0.08); 
              padding: 30px; border-radius: 10px; box-shadow: 0 4px 20px rgba(0,0,0,0.4); 
              width: 900px; max-width: 95%; margin: 30px auto 0 auto; flex-grow: 1; 
              box-sizing: border-box;">
              
          <!-- Welcome Message (Left Aligned) -->
    <div style="width: 100%; text-align: left; margin: -20px -5px -15px 2px;">
      <p style="font-size: 16px; font-weight: bold; color: #white;">
        ADMIN:${sessionScope.userName}
      </p>
    </div>        
              

    <h1 style="text-align: center; margin-bottom: 20px; font-weight: bold; font-size: 32px; 
               text-shadow: none;">
      Book Store Records
    </h1>
    

    <!-- Search Form -->
    <form action="search" method="get" style="display: flex; gap: 10px; margin-bottom: 20px;">
      <input type="text" name="query" value="${searchQuery}" placeholder="Search"
             style="flex: 1; padding: 10px; border-radius: 5px; border: none; outline: none; 
                    background: rgba(255,255,255,0.1); color: white; font-size: 16px;" />
      <button type="submit" 
              style="padding: 10px 20px; background: rgba(0,255,213,0.85); color: black; 
                     font-weight: bold; border: none; border-radius: 5px; cursor: pointer;">
        Search
      </button>
    </form>

    <!-- Search Info -->
    <c:if test="${not empty searchQuery}">
      <p style="text-align: center; margin: -10px 0 15px 0; color: rgba(0,255,213,0.8); font-weight: 600;">
        Search results for: "<strong>${searchQuery}</strong>"
      </p>
    </c:if>

    <!-- Message -->
    <c:if test="${not empty message}">
      <div style="background: rgba(0,255,213,0.1); color: rgba(0,255,213,0.9); 
                  border: 1px solid rgba(0,255,213,0.4); padding: 10px 1px; border-radius: 6px; 
                  margin: 0 auto 20px auto; text-align: center; font-weight: bold; width: 100%;">
        ${message}
      </div>
    </c:if>

    <!-- Add New Link -->
    <div style="text-align: right; margin-bottom: 10px;">
      <a href="new" style="color: rgba(0,255,213,0.8); font-weight: bold; text-decoration: none;">
        + Add New
      </a>
    </div>

    <!-- Records Table -->
   <table style="width: 100%; border-collapse: collapse; text-align: center; color: white; border: 1px solid rgba(0,255,213,0.3);">
  <thead>
    <tr style="background: rgba(0,255,213,0.1); font-weight: bold; font-size: 16px;">
      <th style="padding: 12px; border: 1px solid rgba(0,255,213,0.3);">ID</th>
      <th style="padding: 12px; border: 1px solid rgba(0,255,213,0.3);">Customer</th>
      <th style="padding: 12px; border: 1px solid rgba(0,255,213,0.3);">Book Name</th>
      <th style="padding: 12px; border: 1px solid rgba(0,255,213,0.3);">Author</th>
      <th style="padding: 12px; border: 1px solid rgba(0,255,213,0.3);">Issued</th>
      <th style="padding: 12px; border: 1px solid rgba(0,255,213,0.3);">Updated</th>
      <th style="padding: 12px; border: 1px solid rgba(0,255,213,0.3);">Price</th>
      <th style="padding: 12px; border: 1px solid rgba(0,255,213,0.3);">Actions</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="b" items="${listBook}">
      <tr style="background: rgba(255,255,255,0.05);">
        <td style="padding: 8px; vertical-align: middle; border: 1px solid rgba(0,255,213,0.3);">${b.id}</td>
        <td style="vertical-align: middle; border: 1px solid rgba(0,255,213,0.3);">${b.customerName}</td>
        <td style="vertical-align: middle; border: 1px solid rgba(0,255,213,0.3);">${b.bookName}</td>
        <td style="vertical-align: middle; border: 1px solid rgba(0,255,213,0.3);">${b.author}</td>
        <td style="vertical-align: middle; border: 1px solid rgba(0,255,213,0.3);"><c:out value="${b.issuedDate}" default="NULL" /></td>
        <td style="vertical-align: middle; border: 1px solid rgba(0,255,213,0.3);"><c:out value="${b.updatedDate}" default="NOT UPDATED" /></td>
        <td style="vertical-align: middle; border: 1px solid rgba(0,255,213,0.3);">${b.price}</td>
        <td style="vertical-align: middle; border: 1px solid rgba(0,255,213,0.3);">
          <a href="edit?id=${b.id}" style="color: rgba(0,255,213,0.8); text-decoration: none; margin-right: 10px;">Edit</a>
          <a href="delete?id=${b.id}" onclick="return confirm('Delete?')" style="color: #ff4b1f; text-decoration: none;">Delete</a>
        </td>
      </tr>
    </c:forEach>
  </tbody>
</table>


  </div>

  <!-- Pagination OUTSIDE main container -->
  <div style="max-width: 900px; margin: 20px auto 80px auto; text-align: center; user-select: none;">
    <c:if test="${totalPages > 1}">
      <c:set var="window" value="2" />
      <c:set var="start" value="${currentPage - window}" />
      <c:set var="end" value="${currentPage + window}" />

      <c:if test="${start > 1}">
        <c:url var="firstUrl" value="${empty searchQuery ? 'list' : 'search'}">
          <c:param name="page" value="1" />
          <c:if test="${not empty searchQuery}">
            <c:param name="query" value="${searchQuery}" />
          </c:if>
        </c:url>
        <a href="${firstUrl}"
           style="display: inline-block; margin: 0 5px; padding: 7px 14px;
                  border: 1px solid rgba(0,255,213,0.5); border-radius: 6px;
                  text-decoration: none; font-weight: bold; color: rgba(0,255,213,0.8);">
          1
        </a>
        <span style="margin: 0 5px; color: #aaa;">...</span>
      </c:if>

      <c:forEach var="i" begin="${start < 1 ? 1 : start}" end="${end > totalPages ? totalPages : end}">
        <c:url var="pageUrl" value="${empty searchQuery ? 'list' : 'search'}">
          <c:param name="page" value="${i}" />
          <c:if test="${not empty searchQuery}">
            <c:param name="query" value="${searchQuery}" />
          </c:if>
        </c:url>

        <c:choose>
          <c:when test="${i == currentPage}">
            <span
              style="display: inline-block; margin: 0 5px; padding: 7px 14px;
                     border-radius: 6px; font-weight: bold; background: rgba(0,255,213,0.85);
                     color: black; user-select: none;">
              ${i}
            </span>
          </c:when>
          <c:otherwise>
            <a href="${pageUrl}"
               style="display: inline-block; margin: 0 5px; padding: 7px 14px;
                      border: 1px solid rgba(0,255,213,0.5); border-radius: 6px;
                      text-decoration: none; font-weight: bold; color: rgba(0,255,213,0.8);
                      transition: background-color 0.3s ease, color 0.3s ease;">
              ${i}
            </a>
          </c:otherwise>
        </c:choose>
      </c:forEach>

      <c:if test="${end < totalPages}">
        <span style="margin: 0 5px; color: #aaa;">...</span>
        <c:url var="lastUrl" value="${empty searchQuery ? 'list' : 'search'}">
          <c:param name="page" value="${totalPages}" />
          <c:if test="${not empty searchQuery}">
            <c:param name="query" value="${searchQuery}" />
          </c:if>
        </c:url>
        <a href="${lastUrl}"
           style="display: inline-block; margin: 0 5px; padding: 7px 14px;
                  border: 1px solid rgba(0,255,213,0.5); border-radius: 6px;
                  text-decoration: none; font-weight: bold; color: rgba(0,255,213,0.8);">
          ${totalPages}
        </a>
      </c:if>
    </c:if>
  </div>

  <!-- Footer fixed at bottom -->
  <footer style="width: 100%; background: linear-gradient(135deg, #0f2027, #203a43, #2c5364); color: rgba(0,255,213,0.8);
                 text-align: center; padding: 8px 0; font-size: 14px;
                 position: fixed; bottom: 0; left: 0; box-sizing: border-box;">
    <p style="margin: 0;">&copy; 2025 BookStore Inc.</p>
    <a href="privacy.jsp" style="color: rgba(0,255,213,0.8); text-decoration: none; margin: 0 5px;">Privacy Policy</a> |
    <a href="contact.jsp" style="color: rgba(0,255,213,0.8); text-decoration: none; margin: 0 5px;">Contact Us</a>
  </footer>

</body>
</html>
