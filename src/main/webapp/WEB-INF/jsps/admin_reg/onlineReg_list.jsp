<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
      <head>
            <meta charset="UTF-8">
            <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/table.css">
            <title>${estm_name} - Create an Administrative Registration</title>
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
                  #empty_message {
                        font-size: x-large;
                  }
            </style>
            <script defer>
                  async function deleteOnlineReg () {
                        await new Promise(resolve => setTimeout(resolve, 1000)); 
                        window.location.reload();
                  }
            </script>
      </head>
      <body>
            <%@ include file= "/resources/includes/main_panel_header.jsp" %>

            <header>
                  <p id="main_title">Select the Online Reg. to which you want to create the Admin. Reg.</p>
                  <a href="${pageContext.request.contextPath}/adminReg/onlineReg/create">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/add_icon.png" alt="Add icon" title="Create a new Online Registration instead" width="32" height="32">
                  </a>
                  <a href="${pageContext.request.contextPath}/adminReg/create">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/switch_icon.png" alt="Switch icon" title="Select from the Online Registrations(s) instead" width="32" height="32">
                  </a>
            </header>

            <c:choose>
                  <c:when test="${empty list}">
                        <p id="empty_message">There are no Online Registration(s) to show.</p>
                  </c:when>
                  <c:otherwise>
                        <table>
                              <tr>
                                    <th class="th" rowspan="2">Massar code</th>
                                    <th class="th" rowspan="2">Full name</th>
                                    <th class="th" rowspan="2">Gender</th>
                                    <th class="th" rowspan="2">Major</th>
                                    <th class="th" colspan="3">Action</th>
                              </tr>
                              <tr>
                                    <th class="th">Full info.</th>
                                    <th class="th">Select</th>
                                    <th class="th">Delete</th>
                              </tr>
                              <c:forEach var="list_item" items="${list}">
                                    <tr>
                                          <td>
                                                ${list_item.massar_code}
                                          </td>
                                          <td>
                                                ${list_item.fullName}
                                          </td>
                                          <td>
                                                <c:choose>
                                                      <c:when test="${list_item.gender.gender.id == 1}">
                                                            <img src="${pageContext.request.contextPath}/resources/images/icons/male_icon.png" alt="Male icon" width="32" height="32">
                                                      </c:when>
                                                      <c:otherwise>
                                                            <img src="${pageContext.request.contextPath}/resources/images/icons/female_icon.png" alt="Female icon" width="32" height="32"> 
                                                      </c:otherwise>
                                                </c:choose>
                                          </td>
                                          <td>
                                                ${list_item.major.major}
                                          </td>
                                          <td>
                                                <a href="${pageContext.request.contextPath}/adminReg/onlineReg?id=${list_item.id}" target="_blank">
                                                      <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/info_icon.png" alt="Information icon" width="32" height="32">
                                                </a>
                                          </td>
                                          <td>
                                                <a href="${pageContext.request.contextPath}/adminReg/create/next?onlineReg_id=${list_item.id}">
                                                      <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/add_icon.png" alt="Add icon" title="Create a new Administrative Registration for this Online Registration" width="32" height="32">
                                                </a>
                                          </td>
                                          <td>
                                                <a href="${pageContext.request.contextPath}/adminReg/onlineReg/delete?id=${list_item.id}" target="_blank">
                                                      <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/delete_icon.png" alt="Delete icon" width="32" height="32" onclick="deleteOnlineReg()">
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

