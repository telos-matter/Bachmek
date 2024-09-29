<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/form.css">
                <title>${estm_name} - Create grade</title>
        </head>
        <body>
                <%@ include file= "/resources/includes/main_panel_header.jsp" %>

                <p id="form_statement">Create a new Grade for ${pedagogicalRegistration.fullName} (${assessment.fullName} - ${acadYear.ABBR})</p>
                <form id="form" action="${pageContext.request.contextPath}/grade/create" method="post">
                    <div class="input_form">
                        <label for="normal_session">Normal session</label>
                        <input id="normal_session" name="normal_session" type="number" value="10" min="0" max="100" step="any" required>
                    </div>

                    <input type="hidden" name="academicYear_id" value="${acadYear.id}">
                    <input type="hidden" name="assessment_id" value="${assessment.id}">
                    <input type="hidden" name="pedagogicalRegistration_id" value="${pedagogicalRegistration.id}">

                </form>
                <input id="submit_button" form="form" type="submit" value="Create">

                <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
        </body>
</html>