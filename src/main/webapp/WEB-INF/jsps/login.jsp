<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/foundation.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/form.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/header.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/plain_panel.css">
        <title>${estm_name} - Login</title>
        <style>
.error_message {
    font-size: x-large;
    margin: auto;
    margin-top: 2vh;
    margin-bottom: 3vh;
    color: red;
}
#password_label {
    cursor: pointer;
}
        </style>
        <script defer>
function change() {
    var passowrd = document.getElementById("password");
    if (passowrd.type === "password") {
        passowrd.type = "text";
    } else {
        passowrd.type = "password";
    }
}
        </script>
    </head>
    <body>
        <%@ include file= "/resources/includes/header.jsp" %>
        <div id="plain_panel">
            <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <c:if test="${error_message != null}">
                <span class="error_message">${error_message}</span>
            </c:if>
            <p id="form_statement">Please fill out the login form with correct information</p>
            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="input_form">
                    <label for="username">Username</label>
                    <input id="username" type="text" name="username" value="${username}" maxlength="32" minlength="3" pattern="^([A-Za-z0-9\-_]{3,32})$" title="The username is 3 to 32 charachters long and can contain upper/lower case letters, numbers and - or _ but no spaces." required>      
                </div>
                <div class="input_form">
                    <label id="password_label" for="password" onclick="change()">Password</label>
                    <input id="password" type="password" name="password" value="${password}" maxlength="64" minlength="4" pattern="^([A-Za-z0-9`&quot;',/!#\$%&amp;\(\)\*\+\-\.:;&lt;=&gt;\?@\[\]\^_\{\|\}~]{4,64})$" title="The password is 4 to 64 charachters long and can contain upper/lower case letters, numbers and any of these special charachters ~`!@#$%^&amp;*()_-+={[}]|:;&quot;'&lt;,&gt;.?/ but no spaces." required>      
                </div>

                <input id="submit_button" type="submit" value="Login">
            </form>
        </div>
        <div id="panel_footer"></div>
    </body>
</html>