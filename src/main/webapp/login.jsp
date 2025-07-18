<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
      <link rel="stylesheet" href="<c:url value='/resources/Styles.css'/>" />
</head>
<body>
 <form action="${pageContext.request.contextPath}/book" method="post">
        <h2>Login</h2>
        <input type="text" name="user_name" placeholder="User Name" required><br>
        <input type="password" name="password" placeholder="Password" required><br>
        <input type="submit" value="Login"><br>
    </form>
</body>
</html>
