<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/form.css">
                <title>${estm_name} - Create Academic Semester</title>
                <style>
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

                <p id="form_statement">Create a new Acad. Sem. for the ${acadStage.sequence} Acad. Stage</p>
                <form action="${pageContext.request.contextPath}/acadSem/create?acadStage_id=${acadStage.id}" method="post">
                    <div class="input_form">
                        <label for="sequence">Sequence</label>
                        <input id="sequence" type="number" name="sequence" value="${max_sequence}" min="1" max="${max_sequence}" step="1" required>
                        <img class="warning_icon" src="${pageContext.request.contextPath}/resources/images/icons/warning_icon.png" alt="Warning icon" title="Choosing an already existing sequence will cause the two Academic Semesters to swap sequences" width="32" height="32">
                    </div>
                    <div class="input_form">
                        <c:choose>
                            <c:when test="${not acadStage.isActive}">
                                <label>Active</label>
                                <input type="radio" disabled>
                                <label>Inactive</label>
                                <input type="radio"checked disabled>
                                <input type="hidden" name="isActive" value="false" checked required readonly>
                            </c:when>
                            <c:otherwise>
                                <label for="active">Active</label>
                                <input id="active" type="radio" name="isActive" value="true" required checked>
                                <label for="inactive">Inactive</label>
                                <input id="inactive" type="radio" name="isActive" value="false" required>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    
                    <input id="submit_button" type="submit" value="Create">
                </form>

                <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
        </body>
</html>