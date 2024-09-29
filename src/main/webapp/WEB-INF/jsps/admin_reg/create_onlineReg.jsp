<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
      <head>
            <meta charset="UTF-8">
            <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/form.css">
            <title>${estm_name} - Create Online Registration</title>
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
            <p id="form_statement">Create a new Online Reg. for a new Admin. Reg.</p>
            <form action="${pageContext.request.contextPath}/adminReg/onlineReg/create" method="post">
                  <div class="input_form">
                        <label for="massar_code">Massar code</label>
                        <input id="massar_code" type="text" name="massar_code" value="${massar_code}" pattern="^([A-Z][0-9]{9})$" title="The massar code is an uppercase letter followed by 9 numbers." placeholder="M123456789" required>      
                  </div>
                  <div class="input_form">
                        <input id="ar_first_name" type="text" name="ar_first_name" value="${ar_first_name}" dir="rtl" pattern="^([ء-ي\s]{2,64})$" minlength="2" maxlength="64">
                        <label for="ar_first_name">الإسم</label>   
                  </div>
                  <div class="input_form">
                        <input id="ar_last_name" type="text" name="ar_last_name" value="${ar_last_name}" dir="rtl" pattern="^([ء-ي\s]{2,64})$" maxlength="64" minlength="2">
                        <label for="ar_last_name">النسب</label>
                  </div>
                  <div class="input_form">
                        <label for="first_name">First name</label>
                        <input id="first_name" type="text" name="first_name" value="${first_name}" pattern="^([A-Za-z\s]{2,64})$" maxlength="64" minlength="2" required>
                  </div>
                  <div class="input_form">
                        <label for="last_name">Last name</label>
                        <input id="last_name" type="text" name="last_name" value="${last_name}" pattern="^([A-Za-z\s]{2,64})$" maxlength="64" minlength="2" required>
                        
                  </div>
                  <div class="input_form">
                        <label for="cin">CIN</label>
                        <input id="cin" type="text" name="cin" value="${cin}" maxlength="16" pattern="^([A-Z][0-9]{4,15})$" placeholder="K01234567">
                  </div>
                  <div class="input_form">
                        <label for="nationality">Nationality</label>
                        <input id="nationality" type="text" name="nationality" value="${nationality}" maxlength="32" minlength="3">
                  </div>
                  <div class="input_form">
                        <label for="gender">Gender</label>
                        <select id="gender" name="gender" required>
                              <c:forEach var="gender_item" items="${gender_list}">
                                    <c:choose>
                                          <c:when test="${gender.equals(gender_item.toString())}">
                                                <option value="${gender_item}" selected>${gender_item}</option>
                                          </c:when>
                                          <c:otherwise>
                                                <option value="${gender_item}">${gender_item}</option>
                                          </c:otherwise>
                                    </c:choose>
                              </c:forEach> 
                        </select>
                  </div>
                  <div class="input_form">
                        <label for="birthdate">Birthdate</label>
                        <input id="birthdate" type="date" name="birthdate" value="${birthdate}">
                  </div>
                  <div class="input_form">
                        <input id="ar_birth_place" type="text" name="ar_birth_place" value="${ar_birth_place}" dir="rtl" maxlength="128" minlength="2">
                        <label for="ar_birth_place">مكان الولادة</label>
                  </div>
                  <div class="input_form">
                        <label for="birth_place">Birth place</label>
                        <input id="birth_place" type="text" name="birth_place" value="${birth_place}" maxlength="128" minlength="2">
                  </div>
                  <div class="input_form">
                        <label for="residence_town">Residence town</label>
                        <input id="residence_town" type="text" name="residence_town" value="${residence_town}" maxlength="64" minlength="2">
                  </div>
                  <div class="input_form">
                        <label for="province">Province</label>
                        <input id="province" type="text" name="province" value="${province}" maxlength="64" minlength="2">
                  </div>
                  <div class="input_form">
                        <label for="bac_year">Bac year</label>
                        <input id="bac_year" type="number" name="bac_year" value="${bac_year}" required min="1900" max="9999" step="1">
                  </div>
                  <div class="input_form">
                        <label for="high_school">High school</label>
                        <input id="high_school" type="text" name="high_school" value="${high_school}" maxlength="64" minlength="2">
                  </div>
                  <div class="input_form">
                        <label for="bac_place">Bac place</label>
                        <input id="bac_place" type="text" name="bac_place" value="${bac_place}" maxlength="64" minlength="2">
                  </div>
                  <div class="input_form">
                        <label for="academy">Academy</label>
                        <input id="academy" type="text" name="academy" value="${academy}" maxlength="64" minlength="2">
                  </div>
                  <div class="input_form">
                        <label for="bac_serie">Bac serie</label>
                        <select id="bac_serie" name="bac_serie" required>
                              <c:forEach var="bac_serie_item" items="${bac_serie_list}">
                                    <c:choose>
                                          <c:when test="${bac_serie.equals(bac_serie_item.toString())}">
                                                <option value="${bac_serie_item}" selected>${bac_serie_item}</option>
                                          </c:when>
                                          <c:otherwise>
                                                <option value="${bac_serie_item}">${bac_serie_item}</option>
                                          </c:otherwise>
                                    </c:choose>
                              </c:forEach>
                        </select>
                  </div>
                  <div class="input_form">
                        <label for="bac_honour">Bac honour</label>
                        <select id="bac_honour" name="bac_honour" required>
                              <c:forEach var="bac_honour_item" items="${bac_honour_list}">
                                    <c:choose>
                                          <c:when test="${bac_honour.equals(bac_honour_item.toString())}">
                                                <option value="${bac_honour_item}" selected>${bac_honour_item}</option>
                                          </c:when>
                                          <c:otherwise>
                                                <option value="${bac_honour_item}">${bac_honour_item}</option>
                                          </c:otherwise>
                                    </c:choose>
                              </c:forEach>
                        </select>
                  </div>
                  <div class="input_form">
                        <label for="major">Major</label>
                        <select id="major" name="major" required>
                              <c:forEach var="major_item" items="${major_list}">
                                    <c:choose>
                                          <c:when test="${major.equals(major_item.toString())}">
                                                <option value="${major_item}" selected>${major_item}</option>
                                          </c:when>
                                          <c:otherwise>
                                                <option value="${major_item}">${major_item}</option>
                                          </c:otherwise>
                                    </c:choose>
                              </c:forEach>
                        </select>
                  </div>

                  <input id="submit_button" type="submit" value="Next">
            </form>

            <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
      </body>
</html>