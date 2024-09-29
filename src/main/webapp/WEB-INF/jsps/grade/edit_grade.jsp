<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/form.css">
                <title>${estm_name} - Edit grade</title>
        </head>
        <body>
                <%@ include file= "/resources/includes/main_panel_header.jsp" %>

                <p id="form_statement">Edit ${item.pedagogicalRegistration.fullName}s' ${item.assessment.fullName} Grade</p>
                <form id="form" action="${pageContext.request.contextPath}/grade/edit" method="post">
                        <div class="input_form">
                                <label for="normal_session">Normal session</label>
                                <input id="normal_session" name="normal_session" type="number" value="${item.normal_session}" min="0" max="100" step="any" required>
                        </div>
                        <div class="input_form">
                                <label for="catch_up_session">Catch up session</label>
                                <input id="catch_up_session" name="catch_up_session" type="number" value="${item.catch_up_session}" min="0" max="100" step="any">
                        </div>

                        <input type="hidden" name="id" value="${item.id}">

                </form>
                <input id="submit_button" form="form" type="submit" value="Edit">

                <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
        </body>
</html>