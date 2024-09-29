<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/horizontal_form.css">
                <title>${estm_name} - Create ${instructorRole.name}</title>
        </head>
        <body>
                <%@ include file= "/resources/includes/main_panel_header.jsp" %>

                <p id="form_statement">Create a new ${instructorRole.name} for the ${acadYear.ABBR} Acad. Year</p>
                <form id="form" action="${pageContext.request.contextPath}/instructor/create" method="post">
                    <div class="input_form">
                        <label for="instructor_id">Instructors' full name and CIN</label>
                        <select id="instructor_id" name="instructor_id" required>
                            <c:forEach var="list_item" items="${instructors_list}">
                                <option value="${list_item.id}">${list_item.user.fullName} - ${list_item.user.cin}</option>
                            </c:forEach> 
                        </select>
                    </div>
                    <div class="input_form">
                        <label for="item_id">${instructorRole.associatedClass.simpleName}</label>
                        <select id="item_id" name="item_id" required>
                            <c:forEach var="list_item" items="${items_list}">
                                <c:choose>
                                    <c:when test="${extra}">
                                        <option value="${list_item.id}">${list_item.fullName}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${list_item.id}">${list_item.name}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>  
                        </select>
                    </div>

                    <input type="hidden" name="acadYear_id" value="${acadYear.id}">
                    <input type="hidden" name="instructorRole" value="${instructorRole}">

                </form>
                <input id="submit_button" form="form" type="submit" value="Create">

                <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
        </body>
</html>