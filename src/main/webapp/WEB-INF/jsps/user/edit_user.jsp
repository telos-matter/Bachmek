<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/form.css">
                <title>${estm_name} - Edit User</title>
                <style>
                    .error_message {
                        font-size: x-large;
                        margin: auto;
                        margin-top: 2vh;
                        margin-bottom: 3vh;
                        color: red;
                    }

                    #form_statement {
                        margin-left: auto;
                        margin-right: auto;
                        font-size: 32px;
                        margin-top: 7%;
                        margin-bottom: 10%;
                    }
                </style>
                <script defer>
                    function change() {
                        var field = document.getElementById("password")
                        if (field.type === "password") {
                            field.type = "text"
                        } else {
                            field.type = "password"
                        }
                    }
                </script>
        </head>
        <body>
                <%@ include file= "/resources/includes/main_panel_header.jsp" %>

                <c:if test="${error_message != null}">
                    <span class="error_message">${error_message}</span>
                </c:if>
                <p id="form_statement">Edit ${item.fullName} the ${item_role}</p>
                <form action="${pageContext.request.contextPath}/user/edit?id=${item.id}" method="post">
                    <div class="input_form">
                        <label for="username">Username</label>
                        <input id="username" type="text" name="username" value="${item.username}" maxlength="32" minlength="3" pattern="^([A-Za-z0-9\-_]{3,32})$" title="The username is 3 to 32 charachters long and can contain upper/lower case letters, numbers and - or _ but no spaces." required>      
                    </div>
                    <div class="input_form">
                        <label for="password" onclick="change()" style="cursor: pointer;">Password</label>
                        <input id="password" type="password" name="password" value="${decrypted_password}" maxlength="64" minlength="4" pattern="^([A-Za-z0-9`&quot;',/!#\$%&amp;\(\)\*\+\-\.:;&lt;=&gt;\?@\[\]\^_\{\|\}~]{4,64})$" title="The password is 4 to 64 charachters long and can contain upper/lower case letters, numbers and any of these special charachters ~`!@#$%^&amp;*()_-+={[}]|:;&quot;'&lt;,&gt;.?/ but no spaces." required>      
                    </div>
                    <div class="input_form">
                            <label for="email">Email</label>
                            <input id="email" type="email" name="email" value="${item.email}" maxlength="64" minlength="2">
                    </div>
                    <div class="input_form">
                        <label for="first_name">First name</label>
                        <input id="first_name" type="text" name="first_name" value="${item.first_name}" pattern="^([A-Za-z\s]{2,64})$" maxlength="64" minlength="2" required>
                    </div>
                    <div class="input_form">
                            <label for="last_name">Last name</label>
                            <input id="last_name" type="text" name="last_name" value="${item.last_name}" pattern="^([A-Za-z\s]{2,64})$" maxlength="64" minlength="2" required>
                            
                    </div>
                    <div class="input_form">
                        <label for="gender">Gender</label>
                        <select id="gender" name="gender" required>
                            <c:forEach var="gender_item" items="${gender_list}">
                                    <c:choose>
                                        <c:when test="${item.gender.gender.toString().equals(gender_item.toString())}">
                                                <option value="${gender_item}" selected>${gender_item}</option>
                                        </c:when>
                                        <c:otherwise>
                                                <option value="${gender_item}">${gender_item}</option>
                                        </c:otherwise>
                                    </c:choose>
                            </c:forEach> 
                        </select>
                    </div>
                    <div class="input_form">
                        <label for="birthdate">Birthdate</label>
                        <input id="birthdate" type="date" name="birthdate" value="${item.birthdate}">
                    </div>
                    <div class="input_form">
                        <label for="phone_number">Phone number</label>
                        <input id="phone_number" type="tel" name="phone_number" value="${item.phone_number}" pattern="^([0-9]{9,12})$" placeholder="212612345678">
                    </div>
                    <div class="input_form">
                        <label for="personal_address">Personal address</label>
                        <input id="personal_address" type="text" name="personal_address" value="${item.personal_address}" maxlength="128" minlength="2">
                    </div>
                    <div class="input_form">
                        <label for="cin">CIN</label>
                        <input id="cin" type="text" name="cin" value="${item.cin}" maxlength="16" minlength="5" pattern="^([A-Z][0-9]{4,15})$" placeholder="K01234567">
                    </div>
                    <c:if test="${item_role.toString().equals('INSTRUCTOR')}">
                        <div class="input_form">
                            <label for="discipline">Discipline</label>
                            <input id="discipline" type="text" name="discipline" value="${item.instructor.discipline}" maxlength="64" minlength="2">
                        </div>
                    </c:if>

                    <input id="submit_button" type="submit" value="Edit">
                </form>

                <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
        </body>
</html>