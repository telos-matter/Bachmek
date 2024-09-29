<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
      <head>
      <meta charset="UTF-8">
      <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/foundation.css">
      <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/form.css">
      <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/header.css">
      <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/plain_panel.css">
      <style>
#form_statement {
      display: flex;
      flex-flow: row nowrap;
      align-items: center;
      margin: 1vh 0 2vh 3vw;
}

#form_statement > p {
      margin-left: 10%;
      margin-right: auto;
      font-size: 32px;
}

#male, #female {
      height: 0;
      width: 0;
}

#male_icon, #female_icon {
      cursor: pointer;
}

#male:checked + #male_icon{
      transform: scale(1.5);
      transition: 0.2s linear;
}

#female:checked + #female_icon{
      transform: scale(1.5);
      transition: 0.2s linear;
}
      </style>
      <script defer>
function changedMajor (root, logo_path, majors_regex) {
      major = document.getElementById('major').value.toUpperCase()
      logo_path = logo_path.replace(new RegExp('('+majors_regex+'){1}'), major)
      document.getElementById("major_logo").src = root +logo_path
}
      </script>
      <title>${estm_name} - Apply</title>
      </head>
      <body>
            <%@ include file= "/resources/includes/header.jsp" %>
            <div id="plain_panel">
                  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
                  <div id="form_statement">
                        <img id="major_logo" src="${pageContext.request.contextPath}${path_to_logo}" alt="Major logo">
                        <p>Please fill out the form with correct information</p>
                  </div>
                  <form action="${pageContext.request.contextPath}/home/apply" method="post">
                        <div class="input_form">
                              <label for="massar_code">Massar code</label>
                              <input id="massar_code" type="text" name="massar_code" pattern="^([A-Z][0-9]{9})$" title="The massar code is an uppercase letter followed by 9 numbers." placeholder="M123456789" required>      
                        </div>
                        <div class="input_form">
                              <input id="ar_first_name" type="text" name="ar_first_name" dir="rtl" pattern="^([ء-ي\s]{2,64})$" minlength="2" maxlength="64" required>
                              <label for="ar_first_name">الإسم</label>   
                        </div>
                        <div class="input_form">
                              <input id="ar_last_name" type="text" name="ar_last_name" dir="rtl" pattern="^([ء-ي\s]{2,64})$" maxlength="64" minlength="2" required>
                              <label for="ar_last_name">النسب</label>
                        </div>
                        <div class="input_form">
                              <label for="first_name">First name</label>
                              <input id="first_name" type="text" name="first_name" pattern="^([A-Za-z\s]{2,64})$" maxlength="64" minlength="2" required>
                        </div>
                        <div class="input_form">
                              <label for="last_name">Last name</label>
                              <input id="last_name" type="text" name="last_name" pattern="^([A-Za-z\s]{2,64})$" maxlength="64" minlength="2" required>
                              
                        </div>
                        <div class="input_form">
                              <label for="cin">CIN</label>
                              <input id="cin" type="text" name="cin" maxlength="16" pattern="^([A-Z][0-9]{4,15})$" placeholder="K01234567" required>
                        </div>
                        <div class="input_form">
                              <label for="nationality">Nationality</label>
                              <input id="nationality" type="text" name="nationality" required maxlength="32" minlength="3">
                        </div>
                        <div class="input_form">
                              <label>Gender</label>
                              <label>
                                    <input id="male" type="radio" name="gender" value="male" required checked>
                                    <img id="male_icon" src="${pageContext.request.contextPath}/resources/images/icons/male_icon.png" alt="Male icon" height="28" width="28">
                              </label>
                              <label>
                                    <input id="female" type="radio" name="gender" value="female" required>
                                    <img id="female_icon" src="${pageContext.request.contextPath}/resources/images/icons/female_icon.png" alt="Female icon" height="28" width="28">
                              </label>
                        </div>
                        <div class="input_form">
                              <label for="birthdate">Birthdate</label>
                              <input id="birthdate" type="date" name="birthdate" value="${min_age}" max="${min_age}" required>
                        </div>
                        <div class="input_form">
                              <input id="ar_birth_place" type="text" name="ar_birth_place" dir="rtl" required maxlength="128" minlength="2">
                              <label for="ar_birth_place">مكان الولادة</label>
                        </div>
                        <div class="input_form">
                              <label for="birth_place">Birth place</label>
                              <input id="birth_place" type="text" name="birth_place" required maxlength="128" minlength="2">
                        </div>
                        <div class="input_form">
                              <label for="residence_town">Residence town</label>
                              <input id="residence_town" type="text" name="residence_town" required maxlength="64" minlength="2">
                        </div>
                        <div class="input_form">
                              <label for="province">Province</label>
                              <input id="province" type="text" name="province" required maxlength="64" minlength="2">
                        </div>
                        <div class="input_form">
                              <label for="bac_year">Bac year</label>
                              <input id="bac_year" type="number" name="bac_year" value="${current_year}" required min="1900" max="${max_year}" step="1">
                        </div>
                        <div class="input_form">
                              <label for="high_school">High school</label>
                              <input id="high_school" type="text" name="high_school" required maxlength="64" minlength="2">
                        </div>
                        <div class="input_form">
                              <label for="bac_place">Bac place</label>
                              <input id="bac_place" type="text" name="bac_place" required maxlength="64" minlength="2">
                        </div>
                        <div class="input_form">
                              <label for="academy">Academy</label>
                              <input id="academy" type="text" name="academy" required maxlength="64" minlength="2">
                        </div>
                        <div class="input_form">
                              <label for="bac_serie">Bac serie</label>
                              <select id="bac_serie" name="bac_serie" required>
                                    <c:forEach var="bacSerie" items="${bacSeries}">
                                          <option value="${bacSerie}">${bacSerie}</option>
                                    </c:forEach>
                              </select>
                        </div>
                        <div class="input_form">
                              <label for="bac_honour">Bac honour</label>
                              <select id="bac_honour" name="bac_honour" required>
                                    <c:forEach var="bacHonour" items="${bacHonours}">
                                          <option value="${bacHonour}">${bacHonour}</option>
                                    </c:forEach>
                              </select>
                        </div>
                        <div class="input_form">
                              <label for="major">Major</label>
                              <select id="major" name="major" required onchange="changedMajor('${pageContext.request.contextPath}', '${path_to_logo}', '${majors_regex}')">
                                    <c:forEach var="major" items="${majors}">
                                          <c:choose>
                                                <c:when test="${selected_major.equals(major.toString())}">
                                                      <option value="${major}" selected>${major}</option>
                                                </c:when>
                                                <c:otherwise>
                                                      <option value="${major}">${major}</option>
                                                </c:otherwise>
                                          </c:choose>
                                    </c:forEach> 
                              </select>
                        </div>
                        
                        <input id="submit_button" type="submit" value="Register">
                  </form>
            </div>
            <div id="panel_footer"></div>
      </body>
</html>