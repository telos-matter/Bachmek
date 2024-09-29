<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
      <head>
            <meta charset="UTF-8">
            <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/table.css">
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/form.css">
            <title>${estm_name} - Create a Pedagogical Registration</title>
            <style>
                  header {
                        display: flex;
                        flex-flow: row nowrap;
                        align-items: center;
                        margin-bottom: 3vw;
                  }
                  #main_title {
                        margin-right: 3vw;
                        margin-left: 3vw;
                        font-size: xx-large;
                  }
                  header .icon {
                        margin-left: 2vw;
                  }
                  .icon:hover {
                        transform: scale(1.2);
                        transition-duration: .3s;
                  }
                  #form {
                        display: flex;
                        flex-flow: row nowrap;
                        align-items: center;
                        margin-bottom: 3vw; 
                  }
                  #empty_message {
                        font-size: x-large;
                  }
            </style>
      </head>
      <body>
            <%@ include file= "/resources/includes/main_panel_header.jsp" %>

            <header>
                  <p id="main_title">Select the Admin Reg. to which you want to create the Pedag. Reg.</p>
            </header>
      
            <div id="form">
                  <div class="input_form">
                        <label for="acadStage_id">Academic Stage</label>
                        <select id="acadStage_id" onchange="window.location.href = '${pageContext.request.contextPath}/pedagReg/create?acadStage_id=' +document.getElementById('acadStage_id').value +'&acadYear_id=' +document.getElementById('acadYear_id').value">
                              <c:choose>
                                    <c:when test="${acadStage == null}">
                                          <c:forEach var="list_item" items="${acadStage_list}">
                                                <option value="${list_item.id}">${list_item.name}</option>
                                          </c:forEach> 
                                    </c:when>
                                    <c:otherwise>
                                          <c:forEach var="list_item" items="${acadStage_list}">
                                                <c:choose>
                                                      <c:when test="${list_item.id == acadStage.id}">
                                                            <option value="${list_item.id}" selected>${list_item.name}</option>
                                                      </c:when>
                                                      <c:otherwise>
                                                            <option value="${list_item.id}">${list_item.name}</option>
                                                      </c:otherwise>
                                                </c:choose>
                                          </c:forEach>  
                                    </c:otherwise>
                              </c:choose>
                        </select>
                  </div>
                  <div class="input_form">
                        <label for="acadYear_id">Admin. Reg. Academic Year</label>
                        <select id="acadYear_id" onchange="window.location.href = '${pageContext.request.contextPath}/pedagReg/create?acadStage_id=' +document.getElementById('acadStage_id').value +'&acadYear_id=' +document.getElementById('acadYear_id').value">
                              <c:choose>
                                    <c:when test="${acadYear == null}">
                                          <c:forEach var="list_item" items="${acadYear_list}">
                                                <option value="${list_item.id}">${list_item.ABBR}</option>
                                          </c:forEach> 
                                    </c:when>
                                    <c:otherwise>
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
                                    </c:otherwise>
                              </c:choose>
                        </select>
                  </div>
            </div>

            <c:choose>
                  <c:when test="${empty list}">
                        <p id="empty_message">There are no Administrative Registrations to show.</p>
                  </c:when>
                  <c:otherwise>
                        <table>
                              <tr>
                                    <th class="th" rowspan="2">CNE</th>
                                    <th class="th" rowspan="2">Massar code</th>
                                    <th class="th" rowspan="2">Full name</th>
                                    <th class="th" rowspan="2">Gender</th>
                                    <th class="th" colspan="2">Action</th>
                              </tr>
                              <tr>
                                    <th class="th">Full info.</th>
                                    <th class="th">Select</th>
                              </tr>
                              <c:forEach var="list_item" items="${list}">
                                    <tr>
                                          <td>
                                                ${list_item.cne}
                                          </td>
                                          <td>
                                                ${list_item.onlineRegistration.massar_code}
                                          </td>
                                          <td>
                                                ${list_item.onlineRegistration.fullName}
                                          </td>
                                          <td>
                                                <c:choose>
                                                      <c:when test="${list_item.onlineRegistration.gender.gender.id == 1}">
                                                            <img src="${pageContext.request.contextPath}/resources/images/icons/male_icon.png" alt="Male icon" width="32" height="32">
                                                      </c:when>
                                                      <c:otherwise>
                                                            <img src="${pageContext.request.contextPath}/resources/images/icons/female_icon.png" alt="Female icon" width="32" height="32"> 
                                                      </c:otherwise>
                                                </c:choose>
                                          </td>
                                          <td>
                                                <a href="${pageContext.request.contextPath}/adminReg?id=${list_item.id}" target="_blank">
                                                      <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/info_icon.png" alt="Information icon" width="32" height="32">
                                                </a>
                                          </td>
                                          <td>
                                                <a href="${pageContext.request.contextPath}/pedagReg/create/next?adminReg_id=${list_item.id}">
                                                      <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/add_icon.png" alt="Add icon" title="Create a new Pedagogical Registration for this Administrative Registrations" width="32" height="32">
                                                </a>
                                          </td>
                                    </tr>
                              </c:forEach>
                        </table>
                  </c:otherwise>
            </c:choose>

            <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
      </body>
</html>

