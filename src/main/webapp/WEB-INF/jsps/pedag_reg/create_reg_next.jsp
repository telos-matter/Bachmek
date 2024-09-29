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

            <div class="input_form">
                  <label for="acadYear_id">Academic Year</label>
                  <select id="acadYear_id" onchange="window.location.href = '${pageContext.request.contextPath}/pedagReg/create/next?adminReg_id=${adminReg.id}&acadYear_id=' +document.getElementById('acadYear_id').value">
                        <c:forEach var="list_item" items="${acadYear_list}">
                              <c:choose>
                                    <c:when test="${list_item.id == acadYear.id}">
                                          <option value="${list_item.id}" selected>${list_item.ABBR}</option>
                                    </c:when>
                                    <c:otherwise>
                                          <option value="${list_item.id}">${list_item.ABBR}</option>
                                    </c:otherwise>
                              </c:choose>
                        </c:forEach>
                  </select>
            </div>

            <form action="${pageContext.request.contextPath}/pedagReg/create/next" method="post">
                  <div class="input_form">
                        <label for="semester_id">Semester</label>
                        <select id="semester_id" name="semester_id" required>
                              <c:forEach var="list_item" items="${semester_list}">
                                    <option value="${list_item.id}">${list_item.shortName}</option>
                              </c:forEach> 
                        </select>
                  </div>
                  
                  <input type="hidden" name="adminReg_id" value="${adminReg.id}">

                  <input id="submit_button" type="submit" value="Next">
            </form>

            <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
      </body>
</html>