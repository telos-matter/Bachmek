<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/form.css">
                <title>${estm_name} - Edit Academic Year</title>
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
            <p id="form_statement">Edit the ${item.ABBR} Academic Year</p>
            <form action="${pageContext.request.contextPath}/acadYear/edit?id=${item.id}" method="post">
                <div class="input_form">
                    <label for="start_date">Starting date</label>
                    <input id="start_date" type="date" name="start_date" value="${item.start_date}" required>      
                </div>
                <div class="input_form">
                    <label for="finish_date">Finishin date</label>
                    <input id="finish_date" type="date" name="finish_date" value="${item.finish_date}" required>      
                </div>
                
                <input id="submit_button" type="submit" value="Edit">
            </form>

            <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
        </body>
</html>