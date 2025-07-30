<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .body-container {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0,0,0,0.2);
            width: 350px;
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }

        .password-container {
            width: 100%;
            margin-bottom: 10px;
        }

        .show-password {
            margin-top: 5px;
            font-size: 14px;
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
            font-weight: bold;
            text-align: center;
            margin-bottom: 10px;
        }
    </style>
    <link rel="stylesheet" href="<c:url value='/resources/Styles.css'/>" />
</head>
<body>

    <div class="body-container">
        <form action="${pageContext.request.contextPath}/login" method="post">
            <h2>Login</h2>

            <input type="text" name="userName" placeholder="User Name" required />

            <div class="password-container">
                <input type="password" id="passwordField" name="password" placeholder="Password" required />
                <div class="show-password">
                    <input type="checkbox" id="togglePassword" onclick="togglePasswordVisibility()" />
                    <label for="togglePassword">Show Password</label>
                </div>
            </div>

            <input type="submit" value="Log in" />
        </form>

        <c:if test="${not empty errorMsg}">
            <p class="error-message">${errorMsg}</p>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <p class="error-message">${errorMessage}</p>
        </c:if>
    </div>

    <script>
        function togglePasswordVisibility() {
            const pwdField = document.getElementById("passwordField");
            pwdField.type = pwdField.type === "password" ? "text" : "password";
        }
    </script>
</body>
</html>
