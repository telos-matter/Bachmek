<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/horizontal_form.css">
                <title>${estm_name} - Create ${coefEnum.name}</title>
        </head>
        <body>
                <%@ include file= "/resources/includes/main_panel_header.jsp" %>

                <p id="form_statement">Create a new ${coefEnum.name} for the ${acadYear.ABBR} Acad. Year</p>
                <form id="form" action="${pageContext.request.contextPath}/coef/create" method="post">
                    <div class="input_form">
                        <label for="associated_id">${coefEnum.associatedClass.simpleName}</label>
                        <select id="associated_id" name="associated_id" required>
                            <c:forEach var="list_item" items="${associated_list}">
                                <option value="${list_item.id}">${list_item.fullName}</option>
                            </c:forEach> 
                        </select>
                    </div>
                    <div class="input_form">
                        <label for="coefficient">Coefficient</label>
                        <input id="coefficient" name="coefficient" type="number" value="1" min="0" max="10" step="1" required>
                    </div>

                    <input type="hidden" name="acadYear_id" value="${acadYear.id}">
                    <input type="hidden" name="coefEnum" value="${coefEnum}">

                </form>
                <input id="submit_button" form="form" type="submit" value="Create">

                <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
        </body>
</html>