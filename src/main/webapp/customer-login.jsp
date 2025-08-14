<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Customer Login</title>
</head>
<body style="margin:0; font-family: Arial, sans-serif; background: linear-gradient(135deg, #0f2027, #203a43, #2c5364); display: flex; justify-content: center; align-items: center; height: 100vh; color: white;">

    <div style="background: rgba(255, 255, 255, 0.08); padding: 30px; border-radius: 10px; box-shadow: 0 4px 20px rgba(0,0,0,0.4); width: 350px;">
        <form action="${pageContext.request.contextPath}/login" method="post">
            <h2 style="text-align: center; margin-bottom: 20px; font-weight: bold; font-size: 24px;">Customer Login</h2>
            
            <!-- Fixed User Type Field -->
            <input type="text" name="userType" value="Customer" readonly
                   style="width: 100%; padding: 10px; margin-bottom: 15px; border-radius: 5px; border: none; outline: none; background: rgba(255,255,255,0.1); color: white;" />

            <input type="text" name="userName" placeholder="User Name" required
                   style="width: 100%; padding: 10px; margin-bottom: 15px; border-radius: 5px; border: none; outline: none; background: rgba(255,255,255,0.1); color: white;" />

            <div style="width: 100%; margin-bottom: 10px;">
                <input type="password" id="passwordField" name="password" placeholder="Password" required
                       style="width: 100%; padding: 10px; border-radius: 5px; border: none; outline: none; background: rgba(255,255,255,0.1); color: white;" />
                <div style="margin-top: 5px; font-size: 14px;">
                    <input type="checkbox" id="togglePassword" onclick="togglePasswordVisibility()" />
                    <label for="togglePassword">Show Password</label>
                </div>
            </div>

            <input type="submit" value="Log in"
                   style="width: 100%; padding: 10px; background: #34a853; color: white; border: none; border-radius: 5px; cursor: pointer; font-weight: bold;" />
        </form>

        <c:if test="${not empty errorMsg}">
            <p style="color: #ff4d4d; font-weight: bold; text-align: center; margin-bottom: 10px;">${errorMsg}</p>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <p style="color: #ff4d4d; font-weight: bold; text-align: center; margin-bottom: 10px;">${errorMessage}</p>
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
