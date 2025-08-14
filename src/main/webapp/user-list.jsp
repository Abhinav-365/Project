<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <title>User List - Book Store</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background: linear-gradient(135deg, #0f2027, #203a43, #2c5364);
      color: white;
      padding: 20px;
    }
    table {
      width: 100%;
      border-collapse: collapse;
      background: rgba(255,255,255,0.1);
      box-shadow: 0 0 10px rgba(0,255,213,0.3);
    }
    th, td {
      padding: 12px;
      border-bottom: 1px solid rgba(0,255,213,0.3);
      text-align: center;
    }
    th {
      background: rgba(0,255,213,0.2);
    }
    h1 {
      margin-bottom: 20px;
      text-align: center;
      font-weight: bold;
    }
    a, button {
      color: rgba(0,255,213,0.8);
      text-decoration: none;
      background: transparent;
      border: 1px solid rgba(0,255,213,0.8);
      padding: 8px 16px;
      cursor: pointer;
      font-size: 14px;
      border-radius: 4px;
      transition: background-color 0.3s, color 0.3s;
    }
    a:hover, button:hover {
      background-color: rgba(0,255,213,0.8);
      color: #0f2027;
      text-decoration: none;
    }
    .top-actions {
      margin-bottom: 15px;
      text-align: right;
    }
    .action-btn {
      border: none;
      background: none;
      color: rgba(0,255,213,0.8);
      cursor: pointer;
      font-size: 14px;
      padding: 6px 12px;
      border-radius: 4px;
    }
    .action-btn:hover {
      background-color: rgba(0,255,213,0.8);
      color: #0f2027;
    }
  </style>
</head>
<body>

  <h1>Registered Users</h1>

  <div class="top-actions">
    <a href="user-form.jsp">+ Add New User</a>
  </div>

  <c:if test="${empty listUsers}">
    <p style="text-align:center; font-weight:bold;">No users found.</p>
  </c:if>

  <c:if test="${not empty listUsers}">
    <table>
      <thead>
        <tr>
          <th>User ID</th>
          <th>User Name</th>
          <th>User Type</th>
          <th>Email</th>
          <th>Action</th> <!-- Added action column -->
        </tr>
      </thead>
      <tbody>
        <c:forEach var="user" items="${listUsers}">
          <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.userType}</td>
            <td>${user.email}</td>
           <td>
  <a href="delete-user?id=${user.id}" 
     onclick="return confirm('Are you sure you want to delete user ${user.username}?');" 
     class="action-btn">Delete</a>
</td>

          </tr>
        </c:forEach>
      </tbody>
    </table>
  </c:if>

  <div style="margin-top: 20px; text-align:center;">
    <a href="list">Back to Book List</a>
  </div>

</body>
</html>
