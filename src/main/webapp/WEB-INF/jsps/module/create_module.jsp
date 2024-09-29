<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/form.css">
                <title>${estm_name} - Create Module</title>
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
                <p id="form_statement">Create a new Module</p>
                <form action="${pageContext.request.contextPath}/module/create" method="post">
                    <div class="input_form">
                        <label for="name">Name</label>
                        <input id="name" type="text" name="name" value="${name}" maxlength="32" minlength="2" required>      
                    </div>
                    <c:choose>
                        <c:when test="${isActive == null}">
                            <div class="input_form">
                                <label for="active">Active</label>
                                <input id="active" type="radio" name="isActive" value="true" required checked>
                                <label for="inactive">Inactive</label>
                                <input id="inactive" type="radio" name="isActive" value="false" required>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="input_form">
                                <c:choose>
                                    <c:when test="${isActive.equals('true')}">
                                        <label for="active">Active</label>
                                        <input id="active" type="radio" name="isActive" value="true" required checked>
                                        <label for="inactive">Inactive</label>
                                        <input id="inactive" type="radio" name="isActive" value="false" required>
                                    </c:when>
                                    <c:otherwise>
                                        <label for="active">Active</label>
                                        <input id="active" type="radio" name="isActive" value="true" required>
                                        <label for="inactive">Inactive</label>
                                        <input id="inactive" type="radio" name="isActive" value="false" required checked>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:otherwise>
                    </c:choose>
                    
                    <input id="submit_button" type="submit" value="Create">
                </form>

                <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
        </body>
</html>