<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/table.css">
                <title>${estm_name} - Student grades</title>
                <style>
                    header {
                        display: flex;
                        flex-flow: row nowrap;
                        align-items: center;
                        margin-bottom: 3vw;
                    }
                    select {
                        margin-left: 3vw;
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
        </head>
        <body>
                <%@ include file= "/resources/includes/main_panel_header.jsp" %>

                <header>
                    <a href="${pageContext.request.contextPath}/account">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/back_icon.png" alt="Go back icon" width="32" height="32">
                    </a>
                    <select id="pedagogicalRegistration"  onchange="window.location.href = '${pageContext.request.contextPath}/account/grades?id=' +document.getElementById('pedagogicalRegistration').value">
                        <c:forEach var="list_item" items="${pedagRegs_list}">
                            <c:choose>
                                <c:when test="${list_item.id == pedagReg.id}">
                                    <option value="${list_item.id}" selected>${list_item.registrationName}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${list_item.id}">${list_item.registrationName}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach> 
                    </select>
                    <p id="main_title" class="g_h_clr">${user.fullName}s' ${pedagReg.registrationName} Grades</p>
                </header>

                <c:choose>
                    <c:when test="${empty list}">
                        <p id="empty_message">There are currently no approved Grades to show.</p>
                    </c:when>
                    <c:otherwise>
                        <table>
                            <tr>
                                <th class="th">Element</th>
                                <th class="th">Grade</th>
                            </tr>
                            <c:forEach var="list_item" items="${list}">
                                <tr>
                                    <td>
                                        ${list_item.element.name}
                                    </td>
                                    <td>
                                        ${list_item.grade}
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:otherwise>
                </c:choose>

                <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
        </body>
</html>