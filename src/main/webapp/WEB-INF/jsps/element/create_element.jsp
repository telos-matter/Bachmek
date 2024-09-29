<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/form.css">
                <title>${estm_name} - Create Element</title>
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
        </head>
        <body>
                <%@ include file= "/resources/includes/main_panel_header.jsp" %>

                <c:if test="${error_message != null}">
                    <span class="error_message">${error_message}</span>
                </c:if>
                <p id="form_statement">Create a new Element for the ${module.name} Module</p>
                <form action="${pageContext.request.contextPath}/element/create?module_id=${module.id}" method="post">
                    <div class="input_form">
                        <label for="name">Name</label>
                        <input id="name" type="text" name="name" value="${name}" maxlength="32" minlength="2" required>      
                    </div>
                    <div class="input_form">
                        <c:choose>
                            <c:when test="${not module.isActive}">
                                <label>Active</label>
                                <input type="radio" disabled>
                                <label>Inactive</label>
                                <input type="radio"checked disabled>
                                <input type="hidden" name="isActive" value="false" checked required readonly>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${isActive == null}">
                                        <label for="active">Active</label>
                                        <input id="active" type="radio" name="isActive" value="true" required checked>
                                        <label for="inactive">Inactive</label>
                                        <input id="inactive" type="radio" name="isActive" value="false" required>
                                    </c:when>
                                    <c:otherwise>
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
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="input_form">
                        <c:choose>
                            <c:when test="${create != null}">
                                <input id="create" type="checkbox"  name="create" value="true" checked>
                            </c:when>
                            <c:otherwise>
                                <input id="create" type="checkbox"  name="create" value="true">
                            </c:otherwise>
                        </c:choose>
                        <label for="create">Automatically create the PW and EXAM assessments</label>
                    </div>
                    
                    <input id="submit_button" type="submit" value="Create">
                </form>

                <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
        </body>
</html>