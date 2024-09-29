<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
      <head>
            <meta charset="UTF-8">
            <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/table.css">
            <title>${estm_name} - Administrative Registrations</title>
            <style>
                  header {
                        display: flex;
                        flex-flow: row nowrap;
                        align-items: center;
                        margin-bottom: 3vw;
                  }
                  #main_title {
                        margin-right: 3vw;
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
      </head>
      <body>
            <%@ include file= "/resources/includes/main_panel_header.jsp" %>

            <header>
                  <p id="main_title" class="g_h_clr">Admin. Regs. list</p>
                  <a href="${pageContext.request.contextPath}/adminReg/create">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/add_icon.png" alt="Add icon" title="Create a new Administrative Registration" width="32" height="32">
                  </a>
                  <c:choose>
                        <c:when test="${hidden}">
                        <a href="${pageContext.request.contextPath}/adminReg/list?hidden=0">
                              <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/hidden_icon.png" alt="Hidden icon" title="Show inactive Administrative Registrations as well" width="32" height="32">
                        </a>
                        </c:when>
                        <c:otherwise>
                        <a href="${pageContext.request.contextPath}/adminReg/list?hidden=1">
                              <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/visible_icon.png" alt="Visible icon" title="Show only active Administrative Registrations" width="32" height="32">
                        </a>
                        </c:otherwise>
                  </c:choose>
                  <a href="${pageContext.request.contextPath}/adminReg/file">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/file_icon.png" alt="File icon" title="Handle Administrative Registration trough Excel files" width="32" height="32">
                  </a>
            </header>

            <c:choose>
                  <c:when test="${empty list}">
                        <p id="empty_message">There are no Administrative Registrations to show.</p>
                  </c:when>
                  <c:otherwise>
                        <table>
                              <tr>
                                    <th class="th" rowspan="2">Full name</th>
                                    <th class="th" colspan="4">Action</th>
                              </tr>
                              <tr>
                                    <th class="th">(In)active</th>
                                    <th class="th">Edit</th>
                                    <th class="th">Pedag. Reg.</th>
                                    <th class="th" >Delete</th>
                              </tr>
                              <c:forEach var="list_item" items="${list}">
                                    <tr>
                                          <td>
                                                <a href="${pageContext.request.contextPath}/adminReg?id=${list_item.id}">
                                                      ${list_item.fullName}
                                                </a>
                                          </td>
                                          <td>
                                                <c:choose>
                                                      <c:when test="${list_item.isActive}">
                                                            <a href="${pageContext.request.contextPath}/adminReg/activate?id=${list_item.id}">
                                                                  <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/active_icon.png" alt="Active icon" title="Inactivate this Administrative Registration" width="32" height="32">
                                                            </a>
                                                      </c:when>
                                                      <c:otherwise>
                                                            <a href="${pageContext.request.contextPath}/adminReg/activate?id=${list_item.id}">
                                                                  <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/inactive_icon.png" alt="Inactive icon" title="Activate this Administrative Registration" width="32" height="32">
                                                            </a>
                                                      </c:otherwise>
                                                </c:choose>
                                          </td>
                                          <td>
                                                <a href="${pageContext.request.contextPath}/adminReg/edit?id=${list_item.id}">
                                                      <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/edit_icon.png" alt="Edit icon" width="32" height="32">
                                                </a>
                                          </td>
                                          <td>
                                                <c:choose>
                                                      <c:when test="${list_item.isActive}">
                                                            <a href="${pageContext.request.contextPath}/pedagReg/create/next?adminReg_id=${list_item.id}">
                                                                  <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/add_icon.png" alt="Add icon" title="Create a new Pedagogical Registration for this Administrative Registration" width="32" height="32">
                                                            </a>
                                                      </c:when>
                                                      <c:otherwise>
                                                            <img src="${pageContext.request.contextPath}/resources/images/icons/disabled_add_icon.png" alt="Disabled add icon" title="To create a new Pedagogical Registration for this Administrative Registration first activate it" width="32" height="32">
                                                      </c:otherwise>
                                                </c:choose>
                                          </td>
                                          <td>
                                                <a href="${pageContext.request.contextPath}/adminReg/delete?id=${list_item.id}">
                                                      <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/delete_icon.png" alt="Delete icon" width="32" height="32" onclick="return confirm('Are you sure you want to delete ${list_item.fullName} Administrative Registration?\nIt will also delete its Online Registration and its Pedagogical Registrations')">
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