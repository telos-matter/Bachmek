<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
      <head>
            <meta charset="UTF-8">
            <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/form.css">
            <title>${estm_name} - Create an Administrative Registration</title>
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
            <p id="form_statement">Create a new Admin. Reg. for ${onlineReg.fullName}</p>
            <form action="${pageContext.request.contextPath}/adminReg/create/next" method="post">
                  <div class="input_form">
                        <label for="cne">CNE</label>
                        <input id="cne" type="text" name="cne" value="${cne}" pattern="^([0-9]{9})$" title="The CNE is composed of 9 numbers." placeholder="123456789" required>      
                  </div>
                  <div class="input_form">
                        <label for="personal_address">Personal address</label>
                        <input id="personal_address" type="text" name="personal_address" value="${personal_address}" maxlength="128" minlength="2">
                  </div>
                  <div class="input_form">
                        <label for="phone_number">Phone number</label>
                        <input id="phone_number" type="tel" name="phone_number" value="${phone_number}" pattern="^([0-9]{9,12})$" placeholder="212612345678">
                  </div>
                  <div class="input_form">
                        <label for="parents_address">Parents address</label>
                        <input id="parents_address" type="text" name="parents_address" value="${parents_address}" maxlength="128" minlength="2">
                  </div>
                  <div class="input_form">
                        <label for="diplomas">Diplomas</label>
                        <textarea id="diplomas" name="diplomas" maxlength="10000" min="2" placeholder="Diplomas names seperated by &quot;,&quot;.">${diplomas}</textarea>
                  </div>                  
                  <div class="input_form">
                        <label for="isTuition">Has tuition?</label>
                        <c:choose>
                              <c:when test="${isTuition != null}">
                                    <input id="isTuition" type="checkbox"  name="isTuition" value="true" checked>
                              </c:when>
                              <c:otherwise>
                                    <input id="isTuition" type="checkbox"  name="isTuition" value="true">
                              </c:otherwise>
                        </c:choose>
                  </div>
                  <div class="input_form">
                        <label for="academicYear">Academic Year</label>
                        <select id="academicYear" name="academicYear_id" required>
                              <c:forEach var="academicYear_item" items="${acadYear_list}">
                                    <option value="${academicYear_item.id}">${academicYear_item.ABBR}</option>
                              </c:forEach>
                        </select>
                  </div>
                  <div class="input_form">
                        <label for="academicStage">Academic Stage</label>
                        <select id="academicStage" name="academicStage_id" required>
                              <c:forEach var="academicStage_item" items="${acadStage_list}">
                                    <option value="${academicStage_item.id}">${academicStage_item.name}</option>
                              </c:forEach>
                        </select>
                  </div>
                  <input type="hidden" name="onlineRegistration_id" value="${onlineRegistration.id}">

                  <input id="submit_button" type="submit" value="Create">
            </form>

            <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
      </body>
</html>