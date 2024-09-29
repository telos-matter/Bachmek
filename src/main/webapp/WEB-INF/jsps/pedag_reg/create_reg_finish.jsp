<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
      <head>
            <meta charset="UTF-8">
            <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/horizontal_form.css">
            <title>${estm_name} - Create a Pedagogical Registration</title>
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

            <p id="form_statement">Create a new Pedag. Reg. for ${adminReg.fullName}</p>

            <c:if test="${error_message != null}">
                  <span class="error_message">${error_message}</span>
            </c:if>

            <form action="${pageContext.request.contextPath}/pedagReg/create/finish" method="post">
                  <div class="input_form">
                        <label for="elements_ids">Elements</label>
                        <select id="elements_ids" name="elements_ids" multiple size="${view_size +2}" required>
                              <c:forEach var="list_item" items="${list}">
                                    <option value="${list_item.id}">${list_item.name}</option>
                              </c:forEach> 
                        </select>
                  </div>
                  
                  <input type="hidden" name="adminReg_id" value="${adminReg.id}">
                  <input type="hidden" name="semester_id" value="${semester.id}">

                  <input id="submit_button" type="submit" value="Create">
            </form>

            <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
      </body>
</html>