<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Sign Up</title>
</head>
<body style="margin: 0; font-family: Arial, sans-serif; background: linear-gradient(135deg, #0f2027, #203a43,#2c5364); display: flex; justify-content: center; align-items: center; height: 100vh; color: white;">

    <div style="background-color:rgba(255,255,255,0.1); padding: 25px; border-radius: 8px; width: 350px;">
        <form action="${pageContext.request.contextPath}user-list.jsp" method="post">
            <h2 style="text-align: center; margin-bottom: 20px; color: #06b6d4;">Sign Up</h2>

            <input type="text" name="userName" placeholder="User Name" required 
                style="width: 100%; padding: 8px; margin-bottom: 10px; border-radius: 4px; border: 1px solid #444; background-color: rgba(255, 255, 255, 0.15); color: white; outline: none;">
            
            <input type="email" name="email" placeholder="Email" required 
                style="width: 100%; padding: 8px; margin-bottom: 10px; border-radius: 4px; border: 1px solid #444; background-color: rgba(255, 255, 255, 0.15); color: white; outline: none;">
            
            <input type="password" name="password" placeholder="Password" required 
                style="width: 100%; padding: 8px; margin-bottom: 10px; border-radius: 4px; border: 1px solid #444; background-color: rgba(255, 255, 255, 0.15); color: white; outline: none;">
            
            <input type="password" name="confirmPassword" placeholder="Confirm Password" required 
                style="width: 100%; padding: 8px; margin-bottom: 15px; border-radius: 4px; border: 1px solid #444; background-color: rgba(255, 255, 255, 0.15); color: white; outline: none;">

            <input type="submit" value="Sign Up" 
                style="width: 100%; padding: 8px; background-color: #06b6d4; color: white; font-weight: bold; border: none; border-radius: 4px; cursor: pointer; transition: background-color 0.3s;"
                onmouseover="this.style.backgroundColor='#0497a8';" 
                onmouseout="this.style.backgroundColor='#06b6d4';" />
        </form>

        <c:if test="${not empty errorMsg}">
            <p style="color: #ff4d4d; font-weight: bold; text-align: center; margin-top: 10px;">${errorMsg}</p>
        </c:if>
        <c:if test="${not empty successMsg}">
            <p style="color: #00ff99; font-weight: bold; text-align: center; margin-top: 10px;">${successMsg}</p>
        </c:if>

        <div style="text-align: center; margin-top: 15px; font-size: 14px;">
            <span style="color: #ccc;">Already have an account?</span>
            <a href="${pageContext.request.contextPath}/login.jsp" 
               style="color: #06b6d4; text-decoration: none; font-weight: bold;">Log In</a>
        </div>
    </div>

</body>
</html>
