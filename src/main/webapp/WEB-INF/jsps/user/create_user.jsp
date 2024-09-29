<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/form.css">
                <title>${estm_name} - Create User</title>
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
                <c:choose>
                    <c:when test="${createAdmin}">
                        <p id="form_statement">Create a new Administrator</p>
                    </c:when>
                    <c:otherwise>
                        <p id="form_statement">Create a new Instructor</p>
                    </c:otherwise>
                </c:choose>

                <form action="${pageContext.request.contextPath}/user/create" method="post">
                    <div class="input_form">
                        <label for="username">Username</label>
                        <input id="username" type="text" name="username" value="${username}" maxlength="32" minlength="3" pattern="^([A-Za-z0-9\-_]{3,32})$" title="The username is 3 to 32 charachters long and can contain upper/lower case letters, numbers and - or _ but no spaces." required>      
                    </div>
                    <div class="input_form">
                        <label for="password">Password</label>
                        <input id="password" type="text" name="password" value="${password}" maxlength="64" minlength="4" pattern="^([A-Za-z0-9`&quot;',/!#\$%&amp;\(\)\*\+\-\.:;&lt;=&gt;\?@\[\]\^_\{\|\}~]{4,64})$" title="The password is 4 to 64 charachters long and can contain upper/lower case letters, numbers and any of these special charachters ~`!@#$%^&amp;*()_-+={[}]|:;&quot;'&lt;,&gt;.?/ but no spaces." required>      
                    </div>
                    <div class="input_form">
                            <label for="email">Email</label>
                            <input id="email" type="email" name="email" value="${email}" maxlength="64" minlength="2">
                    </div>
                    <div class="input_form">
                        <label for="first_name">First name</label>
                        <input id="first_name" type="text" name="first_name" value="${first_name}" pattern="^([A-Za-z\s]{2,64})$" maxlength="64" minlength="2" required>
                    </div>
                    <div class="input_form">
                            <label for="last_name">Last name</label>
                            <input id="last_name" type="text" name="last_name" value="${last_name}" pattern="^([A-Za-z\s]{2,64})$" maxlength="64" minlength="2" required>
                            
                    </div>
                    <div class="input_form">
                        <label for="gender">Gender</label>
                        <select id="gender" name="gender" required>
                            <c:forEach var="gender_item" items="${gender_list}">
                                    <c:choose>
                                        <c:when test="${gender.equals(gender_item.toString())}">
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
                        <input id="birthdate" type="date" name="birthdate" value="${birthdate}">
                    </div>
                    <div class="input_form">
                        <label for="phone_number">Phone number</label>
                        <input id="phone_number" type="tel" name="phone_number" value="${phone_number}" pattern="^([0-9]{9,12})$" placeholder="212612345678">
                    </div>
                    <div class="input_form">
                        <label for="personal_address">Personal address</label>
                        <input id="personal_address" type="text" name="personal_address" value="${personal_address}" maxlength="128" minlength="2">
                    </div>
                    <div class="input_form">
                        <label for="cin">CIN</label>
                        <input id="cin" type="text" name="cin" value="${cin}" maxlength="16" minlength="5" pattern="^([A-Z][0-9]{4,15})$" placeholder="K01234567">
                    </div>
                    <c:choose>
                        <c:when test="${createAdmin}">
                            <c:if test="${isRoot}">
                                <div class="input_form">
                                    <input id="root" type="checkbox" name="root" value="true" onclick="return confirm('Are you sure you want to give \(or take\) this Administrator root access ?')">
                                    <label for="root">Has root permission?</label>
                                </div>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <div class="input_form">
                                <label for="discipline">Discipline</label>
                                <input id="discipline" type="text" name="discipline" value="${discipline}" maxlength="64" minlength="2">
                            </div>
                        </c:otherwise>
                    </c:choose>
                    
                    <input type="hidden" name="createAdmin" value="${createAdmin}">

                    <input id="submit_button" type="submit" value="Create">
                </form>

                <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
        </body>
</html>