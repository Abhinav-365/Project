<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>OTP Verification</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
background: linear-gradient(135deg, #0f2027, #203a43, #2c5364);            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            color: white;
        }

        .container {
            background: rgba(255, 255, 255, 0.08);
            padding: 30px;
            border-radius: 10px;
            width: 350px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.3);
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #06b6d4;
        }

        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 4px;
            border: 1px solid #444;
            background: rgba(255,255,255,0.1);
            color: white;
            outline: none;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background: #06b6d4;
            color: white;
            font-weight: bold;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background 0.3s;
        }

        input[type="submit"]:hover {
            background: #0497a8;
        }

        .error-message {
            color: #ff4d4d;
            font-size: 14px;
            text-align: center;
            margin-bottom: 10px;
            font-weight: bold;
        }

        .info-message {
            color: #00ff99;
            font-size: 14px;
            text-align: center;
            margin-bottom: 10px;
            font-weight: bold;
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
