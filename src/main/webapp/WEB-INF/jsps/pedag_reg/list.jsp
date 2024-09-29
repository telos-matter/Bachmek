<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/table.css">
                <title>${estm_name} - Pedagogical Registrations list</title>
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
                        margin-left: 3vw;
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

                <header>
                    <select id="academicYear"  onchange="window.location.href = '${pageContext.request.contextPath}/pedagReg/list?acadYear_id=' +document.getElementById('academicYear').value +'&acadSemester_id=${acadSemester.id}'">
                        <c:forEach var="list_item" items="${acadYears_list}">
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
                    <select id="academicSemester"  onchange="window.location.href = '${pageContext.request.contextPath}/pedagReg/list?acadYear_id=${acadYear.id}&acadSemester_id=' +document.getElementById('academicSemester').value">
                        <c:forEach var="list_item" items="${acadSemesters_list}">
                            <c:choose>
                                <c:when test="${list_item.id == acadSemester.id}">
                                    <option value="${list_item.id}" selected>${list_item.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${list_item.id}">${list_item.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach> 
                    </select>
                    <p id="main_title" class="g_h_clr">${acadSemester.name} - ${acadYear.ABBR} students list</p>
                    <a href="${pageContext.request.contextPath}/pedagReg/create">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/add_icon.png" alt="Add icon" title="Create a new Pedagogical Registration" width="32" height="32">
                    </a>
                    <c:if test="${not empty list and semester != null}">
                        <a href="${pageContext.request.contextPath}/pedagReg/download?id=${semester.id}" target="_blank">
                            <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/download_icon.png" alt="Download icon" title="Download an extensive list" width="32" height="32">
                        </a>
                    </c:if>
                </header>

                <c:choose>
                    <c:when test="${semester == null}">
                        <span class="error_message">There is no Semester in for the selected Academic Year and Academic Semester</span>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${empty list}">
                                <p id="empty_message">There are no Pedagogical Registrations to show.</p>
                            </c:when>
                            <c:otherwise>
                                <table>
                                    <tr>
                                        <th class="th">CNE</th>
                                        <th class="th">Last name</th>
                                        <th class="th">First name</th>
                                        <th class="th">Gender</th>
                                    </tr>
                                    <c:forEach var="list_item" items="${list}">
                                        <tr>
                                            <td>
                                                ${list_item.cne}
                                            </td>
                                            <td>
                                                ${list_item.administrativeRegistration.onlineRegistration.last_name}
                                            </td>
                                            <td>
                                                ${list_item.administrativeRegistration.onlineRegistration.first_name}
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${list_item.administrativeRegistration.onlineRegistration.gender.gender.id == 1}">
                                                            <img src="${pageContext.request.contextPath}/resources/images/icons/male_icon.png" alt="Male icon" width="32" height="32">
                                                    </c:when>
                                                    <c:otherwise>
                                                            <img src="${pageContext.request.contextPath}/resources/images/icons/female_icon.png" alt="Female icon" width="32" height="32"> 
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>

                <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
        </body>
</html>