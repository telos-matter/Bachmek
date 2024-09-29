<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
      <head>
            <meta charset="UTF-8">
            <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/form.css">
            <title>${estm_name} - Edit Administrative Registration</title>
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
            <p id="form_statement">Edit ${item.fullName} Admin. Reg.</p>
            <form action="${pageContext.request.contextPath}/adminReg/edit?id=${item.id}" method="post">
                  <div class="input_form">
                        <label for="massar_code">Massar code</label>
                        <input id="massar_code" type="text" name="massar_code" value="${item.onlineRegistration.massar_code}" pattern="^([A-Z][0-9]{9})$" title="The massar code is an uppercase letter followed by 9 numbers." placeholder="M123456789" required>      
                  </div>
                  <div class="input_form">
                        <input id="ar_first_name" type="text" name="ar_first_name" value="${item.onlineRegistration.ar_first_name}" dir="rtl" pattern="^([ء-ي\s]{2,64})$" minlength="2" maxlength="64">
                        <label for="ar_first_name">الإسم</label>   
                  </div>
                  <div class="input_form">
                        <input id="ar_last_name" type="text" name="ar_last_name" value="${item.onlineRegistration.ar_last_name}" dir="rtl" pattern="^([ء-ي\s]{2,64})$" maxlength="64" minlength="2">
                        <label for="ar_last_name">النسب</label>
                  </div>
                  <div class="input_form">
                        <label for="first_name">First name</label>
                        <input id="first_name" type="text" name="first_name" value="${item.onlineRegistration.first_name}" pattern="^([A-Za-z\s]{2,64})$" maxlength="64" minlength="2" required>
                  </div>
                  <div class="input_form">
                        <label for="last_name">Last name</label>
                        <input id="last_name" type="text" name="last_name" value="${item.onlineRegistration.last_name}" pattern="^([A-Za-z\s]{2,64})$" maxlength="64" minlength="2" required>
                        
                  </div>
                  <div class="input_form">
                        <label for="cin">CIN</label>
                        <input id="cin" type="text" name="cin" value="${item.onlineRegistration.cin}" maxlength="16" pattern="^([A-Z][0-9]{4,15})$" placeholder="K01234567">
                  </div>
                  <div class="input_form">
                        <label for="nationality">Nationality</label>
                        <input id="nationality" type="text" name="nationality" value="${item.onlineRegistration.nationality}" maxlength="32" minlength="3">
                  </div>
                  <div class="input_form">
                        <label for="gender">Gender</label>
                        <select id="gender" name="gender" required>
                              <c:forEach var="gender_item" items="${gender_list}">
                                    <c:choose>
                                          <c:when test="${item.onlineRegistration.gender.gender.equals(gender_item)}">
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
                        <input id="birthdate" type="date" name="birthdate" value="${item.onlineRegistration.birthdate}">
                  </div>
                  <div class="input_form">
                        <input id="ar_birth_place" type="text" name="ar_birth_place" value="${item.onlineRegistration.ar_birth_place}" dir="rtl" maxlength="128" minlength="2">
                        <label for="ar_birth_place">مكان الولادة</label>
                  </div>
                  <div class="input_form">
                        <label for="birth_place">Birth place</label>
                        <input id="birth_place" type="text" name="birth_place" value="${item.onlineRegistration.birth_place}" maxlength="128" minlength="2">
                  </div>
                  <div class="input_form">
                        <label for="residence_town">Residence town</label>
                        <input id="residence_town" type="text" name="residence_town" value="${item.onlineRegistration.residence_town}" maxlength="64" minlength="2">
                  </div>
                  <div class="input_form">
                        <label for="province">Province</label>
                        <input id="province" type="text" name="province" value="${item.onlineRegistration.province}" maxlength="64" minlength="2">
                  </div>
                  <div class="input_form">
                        <label for="bac_year">Bac year</label>
                        <input id="bac_year" type="number" name="bac_year" value="${item.onlineRegistration.bac_year}" required min="1900" max="9999" step="1">
                  </div>
                  <div class="input_form">
                        <label for="high_school">High school</label>
                        <input id="high_school" type="text" name="high_school" value="${item.onlineRegistration.high_school}" maxlength="64" minlength="2">
                  </div>
                  <div class="input_form">
                        <label for="bac_place">Bac place</label>
                        <input id="bac_place" type="text" name="bac_place" value="${item.onlineRegistration.bac_place}" maxlength="64" minlength="2">
                  </div>
                  <div class="input_form">
                        <label for="academy">Academy</label>
                        <input id="academy" type="text" name="academy" value="${item.onlineRegistration.academy}" maxlength="64" minlength="2">
                  </div>
                  <div class="input_form">
                        <label for="bac_serie">Bac serie</label>
                        <select id="bac_serie" name="bac_serie" required>
                              <c:forEach var="bac_serie_item" items="${bac_serie_list}">
                                    <c:choose>
                                          <c:when test="${item.onlineRegistration.bac_serie.bac_serie.equals(bac_serie_item)}">
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
                                          <c:when test="${item.onlineRegistration.bac_honour.bac_honour.equals(bac_honour_item)}">
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
                        <label for="cne">CNE</label>
                        <input id="cne" type="text" name="cne" value="${item.cne}" pattern="^([0-9]{9})$" title="The CNE is composed of 9 numbers." placeholder="123456789" required>      
                  </div>
                  <div class="input_form">
                        <label for="personal_address">Personal address</label>
                        <input id="personal_address" type="text" name="item.personal_address" value="${personal_address}" maxlength="128" minlength="2">
                  </div>
                  <div class="input_form">
                        <label for="phone_number">Phone number</label>
                        <input id="phone_number" type="tel" name="phone_number" value="${item.phone_number}" pattern="^([0-9]{9,12})$" placeholder="212612345678">
                  </div>
                  <div class="input_form">
                        <label for="parents_address">Parents address</label>
                        <input id="parents_address" type="text" name="parents_address" value="${item.parents_address}" maxlength="128" minlength="2">
                  </div>
                  <div class="input_form">
                        <label for="diplomas">Diplomas</label>
                        <textarea id="diplomas" name="diplomas" maxlength="10000" min="2" placeholder="Diplomas names seperated by &quot;,&quot;.">${item.diplomas}</textarea>
                  </div>                  
                  <div class="input_form">
                        <label for="isTuition">Has tuition?</label>
                        <c:choose>
                              <c:when test="${item.isTuition != null && item.isTuition}">
                                    <input id="isTuition" type="checkbox"  name="isTuition" value="true" checked>
                              </c:when>
                              <c:otherwise>
                                    <input id="isTuition" type="checkbox"  name="isTuition" value="true">
                              </c:otherwise>
                        </c:choose>
                  </div>

                  <input id="submit_button" type="submit" value="Edit">
            </form>

            <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
      </body>
</html>