<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>OTP Verification</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f0f0f0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0,0,0,0.2);
            width: 300px;
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
        }

        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background: #4285f4;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .error-message {
            color: red;
            font-size: 14px;
            text-align: center;
            margin-bottom: 10px;
        }

        .info-message {
            color: green;
            font-size: 14px;
            text-align: center;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <form action="verify-otp" method="post">
            <h2>Enter OTP</h2>

            <c:if test="${not empty infoMsg}">
                <div class="info-message">${infoMsg}</div>
            </c:if>

            <c:if test="${not empty errorMsg}">
                <div class="error-message">${errorMsg}</div>
            </c:if>

            <input type="text" name="otp" placeholder="Enter 6-digit OTP" required />
            <input type="submit" value="Verify OTP" />
        </form>
    </div>
</body>
</html>
