<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/table.css">
                <title>${estm_name} - Approvement</title>
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
                    <select id="academicYear"  onchange="window.location.href = '${pageContext.request.contextPath}/approvement/list?acadYear_id=' +document.getElementById('academicYear').value +'&acadSemester_id=${acadSemester.id}'">
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
                    <select id="academicSemester" onchange="window.location.href = '${pageContext.request.contextPath}/approvement/list?acadYear_id=${acadYear.id}&acadSemester_id=' +document.getElementById('academicSemester').value">
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
                    <c:choose>
                        <c:when test="${semester == null}">
                            <span id="main_title" class="error_message">There is no semester for this year</span>
                        </c:when>
                        <c:otherwise>
                            <p id="main_title" class="g_h_clr">${semester.name} modules approvement list</p>
                        </c:otherwise>
                    </c:choose>
                </header>

                <c:choose>
                    <c:when test="${empty list}">
                        <p id="empty_message">There are no Semester modules grades to show.</p>
                    </c:when>
                    <c:otherwise>
                        <table>
                            <tr>
                                <th class="th">Module</th>
                                <th class="th">Is approved?</th>
                                <th class="th">(Remove) Approve</th>
                            </tr>
                            <c:forEach var="list_item" items="${list}">
                                <tr>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/module?id=${list_item.semesterModule.module.id}">
                                            ${list_item.semesterModule.module.name}
                                        </a>
                                    </td>
                                    <c:choose>
                                        <c:when test="${list_item.semesterModule.isApproved}">
                                            <td>
                                                True
                                            </td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/approvement/approve?approve=0&semesterModule_id=${list_item.semesterModule.id}">
                                                    <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/remove_approve_icon.png" alt="Remove approve icon" title="Remove approve this module" width="32" height="32" onclick="return confirm('Are you sure you want to remove the approvement this Module?')">
                                                </a>
                                            </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>
                                                False
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${list_item.canBeApproved}">
                                                        <a href="${pageContext.request.contextPath}/approvement/approve?approve=1&semesterModule_id=${list_item.semesterModule.id}">
                                                            <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/approve_icon.png" alt="Approve icon" title="Approve this module" width="32" height="32" onclick="return confirm('Are you sure you want to approve this Module?')">
                                                        </a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        -
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:otherwise>
                </c:choose>

                <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
        </body>
</html>