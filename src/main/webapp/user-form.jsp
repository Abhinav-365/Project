<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <title>Add User - Book Store</title>
  <style>
    html, body {
      height: 100%;
      margin: 0;
      font-family: Arial, sans-serif;
      background: linear-gradient(135deg, #0f2027, #203a43, #2c5364);
      color: white;
      display: flex;
      justify-content: center;
      align-items: center;
      text-align: center;
    }
    .container {
      background: rgba(255,255,255,0.1);
      padding: 30px 40px;
      border-radius: 6px;
      max-width: 400px;
      width: 100%;
    }
    form {
      margin-top: 10px;
    }
    input, select {
      display: block;
      margin: 10px auto;
      padding: 8px;
      width: 90%;
      border-radius: 3px;
      border: none;
      font-size: 1em;
      color: #0f2027;
      font-weight: normal;
    }
    button {
      background: none;
      border: 1.5px solid rgba(0,255,213,0.8);
      color: rgba(0,255,213,0.8);
      cursor: pointer;
      font-weight: normal;
      padding: 8px 20px;
      border-radius: 3px;
      margin-top: 10px;
      width: 50%;
    }
    button:hover {
      background-color: rgba(0,255,213,0.8);
      color: #0f2027;
    }
    h1 {
      font-weight: normal;
      margin-bottom: 20px;
      color: rgba(0,255,213,0.8);
    }
    .error {
      color: #ff6666;
      margin-bottom: 15px;
    }
    a {
      color: rgba(0,255,213,0.8);
      text-decoration: none;
      display: inline-block;
      margin-top: 20px;
      font-size: 0.95em;
    }
    a:hover {
      text-decoration: underline;
    }
  </style>
</head>
<body>

  <div class="container">
    <h1>Add New User</h1>

    <c:if test="${not empty errorMsg}">
      <div class="error">${errorMsg}</div>
    </c:if>

    <form action="addUser" method="post" autocomplete="off">
      <input type="text" name="username" placeholder="User Name" required />
      <input type="email" name="email" placeholder="Email" required />
      <input type="password" name="password" placeholder="Password" required />
      <input type="password" name="confirmPassword" placeholder="Confirm Password" required />
      <select name="userType" required>
        <option value="" disabled selected>Select User Type</option>
        <option value="admin">ADMIN</option>
        <option value="user">USER</option>
      </select>
      <button type="submit">Add User</button>
    </form>

    <a href="users">Back to User List</a>
  </div>

</body>
</html>
