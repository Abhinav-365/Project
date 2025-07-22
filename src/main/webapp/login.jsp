<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Login</title>
    <style>
        .error-message {
            color: red;
            font-weight: bold;
            margin-top: 15px;
            text-align: center;
        }
    </style>
    <link rel="stylesheet" href="<c:url value='/resources/Styles.css'/>" />
</head>
<body>

    <div class="body-container" style="width: 38%">

        <form action="${pageContext.request.contextPath}/login" method="post">
            <h2>Login</h2>
            <input type="text" name="userName" placeholder="User Name" value="${book.userName}" /><br>
            <input type="password" name="password" placeholder="Password" value="${book.password}" /><br>
            <input type="submit" value="Log in" /><br>
        </form>
<div class="error-container">
  <c:if test="${not empty errorMsg}">
    <p class="error-message">${errorMsg}</p>
  </c:if>

  <c:if test="${not empty errorMessage}">
    <p class="error-message">${errorMessage}</p>
  </c:if>
</div>
    </div>
</body>
</html>