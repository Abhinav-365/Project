<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome to Book Store</title>
    <link rel="stylesheet" href="<c:url value='/resources/Styles.css'/>" />
    <style>
        body {
            text-align: center;
            padding-top: 100px;
        }
        h1 {
            font-size: 40px;
            margin-bottom: 30px;
        }
        .btn-container {
            display: flex;
            justify-content: center;
            gap: 50px;
        }
        .btn {
            background-color: #4CAF50;
            border: none;
            color: white;
            padding: 20px 40px;
            text-align: center;
            font-size: 20px;
            border-radius: 10px;
            text-decoration: none;
            transition: 0.3s;
        }
        .btn:hover {
            background-color: #45a049;
            transform: scale(1.05);
        }
        .admin-btn {
            background-color: #2196F3;
        }
        .admin-btn:hover {
            background-color: #1976D2;
        }
    </style>
</head>
<body>
    <h1>Welcome to the Book Store</h1>
    <div class="btn-container">
        <a href="<c:url value='/customer-login.jsp'/>" class="btn">Customer</a>
        <a href="<c:url value='/admin-login.jsp'/>" class="btn admin-btn">Admin</a>
    </div>
</body>
</html>
