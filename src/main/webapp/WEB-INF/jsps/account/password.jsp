<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
        <title>${estm_name} - Change password</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/form.css">
        <style>
.error_message {
    font-size: x-large;
    margin: auto;
    margin-top: 2vh;
    margin-bottom: 3vh;
    color: red;
}
        </style>
    </head>
    <body>
            <%@ include file= "/resources/includes/main_panel_header.jsp" %>

            <c:if test="${error_message != null}">
                <span class="error_message">${error_message}</span>
            </c:if>
            <p id="form_statement">Edit your password</p>
            <form id="form" action="${pageContext.request.contextPath}/account/password" method="post">
                <div class="input_form">
                    <label for="old_password">Current password</label>
                    <input id="old_password" type="text" name="old_password" maxlength="64" minlength="4" pattern="^([A-Za-z0-9`&quot;',/!#\$%&amp;\(\)\*\+\-\.:;&lt;=&gt;\?@\[\]\^_\{\|\}~]{4,64})$" title="The password is 4 to 64 charachters long and can contain upper/lower case letters, numbers and any of these special charachters ~`!@#$%^&amp;*()_-+={[}]|:;&quot;'&lt;,&gt;.?/ but no spaces." required>      
                </div>
                <div class="input_form">
                    <label for="new_password">New password</label>
                    <input id="new_password" type="text" name="new_password" maxlength="64" minlength="4" pattern="^([A-Za-z0-9`&quot;',/!#\$%&amp;\(\)\*\+\-\.:;&lt;=&gt;\?@\[\]\^_\{\|\}~]{4,64})$" title="The password is 4 to 64 charachters long and can contain upper/lower case letters, numbers and any of these special charachters ~`!@#$%^&amp;*()_-+={[}]|:;&quot;'&lt;,&gt;.?/ but no spaces." required>      
                </div>
                <div class="input_form">
                    <label for="repeat_password">Confirm new password</label>
                    <input id="repeat_password" type="text" name="repeat_password" maxlength="64" minlength="4" pattern="^([A-Za-z0-9`&quot;',/!#\$%&amp;\(\)\*\+\-\.:;&lt;=&gt;\?@\[\]\^_\{\|\}~]{4,64})$" title="The password is 4 to 64 charachters long and can contain upper/lower case letters, numbers and any of these special charachters ~`!@#$%^&amp;*()_-+={[}]|:;&quot;'&lt;,&gt;.?/ but no spaces." required>      
                </div>

                <input id="submit_button" type="submit" value="Edit">
            </form>

            <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
    </body>
</html>