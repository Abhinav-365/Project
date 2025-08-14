<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome to Book Store</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #0f2027, #203a43, #2c5364);
            color: white;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            flex-direction: column;
            text-align: center;
        }
        .consistent-value {
            font-size: 44px;
            font-weight: bold;
            letter-spacing: 2px;
            margin-bottom: 35px;
            text-shadow: 2px 2px 10px rgba(0,0,0,0.6);
            animation: fadeIn 2s ease forwards;
        }
        .card {
            background: rgba(255, 255, 255, 0.1);
            padding: 35px 25px;
            border-radius: 12px;
            box-shadow: 0 6px 25px rgba(0,0,0,0.5);
            width: 370px;
            animation: fadeInUp 2s ease forwards;
        }
        .logo img {
            height: 182px;
            margin-bottom: 0px;
        }
        h1 {
            font-size: 26px;
            margin-bottom: 25px;
            font-weight: 600;
            text-shadow: 1px 1px 6px rgba(0,0,0,0.4);
        }
        .button-group {
            display: flex;
            justify-content: center;
            gap: 18px;
            margin-bottom: 18px;
        }
        .btn {
            padding: 12px 25px;
            font-size: 16px;
            font-weight: bold;
            text-decoration: none;
            color: white;
            border-radius: 6px;
            transition: all 0.3s ease;
            box-shadow: 0 4px 12px rgba(0,0,0,0.3);
        }
        .btn-customer {
            background-color: #28a745;
        }
        .btn-customer:hover {
            background-color: #218838;
            box-shadow: 0 6px 15px rgba(40, 167, 69, 0.5);
        }
        .btn-admin {
            background-color: #4285f4;
        }
        .btn-admin:hover {
            background-color: #3367d6;
            box-shadow: 0 6px 15px rgba(66, 133, 244, 0.5);
        }
        footer {
            font-size: 13px;
            color: rgba(255, 255, 255, 0.75);
            margin-top: 10px;
        }
        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }
        @keyframes fadeInUp {
            from { opacity: 0; transform: translateY(30px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>
    <div class="consistent-value">BOOK STORE</div>

    <div class="card">
        <div class="logo">
            <img src="<c:url value='/resources/logo3.png'/>" alt="Book Store Logo">
        </div>
        <div class="button-group">
            <a href="customer-login.jsp" class="btn btn-customer">Customer</a>
            <a href="login.jsp" class="btn btn-admin">Admin</a>
        </div>
        <footer>
            &copy; 2025 Book Store. All rights reserved.
        </footer>
    </div>
</body>
</html>
