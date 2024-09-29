<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/table.css">
                <title>${estm_name} - Grades</title>
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
                </style>
        </head>
        <body>
                <%@ include file= "/resources/includes/main_panel_header.jsp" %>

                <header>
                    <select id="academicYear"  onchange="window.location.href = '${pageContext.request.contextPath}/grade/list?acadYear_id=' +document.getElementById('academicYear').value +'&assessment_id=${assessment.id}'">
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
                    <select id="assessment" onchange="window.location.href = '${pageContext.request.contextPath}/grade/list?acadYear_id=${acadYear.id}&assessment_id=' +document.getElementById('assessment').value">
                        <c:forEach var="list_item" items="${assessments_list}">
                            <c:choose>
                                <c:when test="${list_item.id == assessment.id}">
                                    <option value="${list_item.id}" selected>${list_item.fullName}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${list_item.id}">${list_item.fullName}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach> 
                    </select>
                    <p id="main_title" class="g_h_clr">${assessment.fullName} grades list</p>
                    <c:if test="${canBeApproved}">
                        <a href="${pageContext.request.contextPath}/grade/approve?acadYear_id=${acadYear.id}&assessment_id=${assessment.id}">
                            <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/approve_icon.png" alt="Approve icon" title="Approve this assessment" width="32" height="32" onclick="return confirm('Are you sure you want to approve this Assessment?')">
                        </a>
                    </c:if>
                    <c:if test="${not isApproved}">
                        <a href="${pageContext.request.contextPath}/grade/file?acadYear_id=${acadYear.id}&assessment_id=${assessment.id}" target="_blank">
                            <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/file_icon.png" alt="File icon" title="Handle the Grades trough Excel files" width="32" height="32">
                        </a>
                    </c:if>
                </header>

                <c:choose>
                    <c:when test="${empty list}">
                        <p id="empty_message">There are no ${assessment.fullName} grades to show.</p>
                    </c:when>
                    <c:otherwise>
                        <table>
                            <tr>
                                <th class="th">CNE</th>
                                <th class="th">Full name</th>
                                <th class="th">Major</th>
                                <th class="th">Normal session grade</th>
                                <th class="th">Catch up session grade</th>
                                <th class="th">Create / Edit</th>
                            </tr>
                            <c:forEach var="list_item" items="${list}">
                                <tr>
                                    <td>
                                        ${list_item.pedagogicalRegistration.administrativeRegistration.cne}
                                    </td>
                                    <td>
                                        ${list_item.pedagogicalRegistration.fullName}
                                    </td>
                                    <td>
                                        ${list_item.pedagogicalRegistration.administrativeRegistration.academicStage.major.name}
                                    </td>
                                    <c:choose>
                                        <c:when test="${list_item.hasGrade}">
                                            <td>
                                                ${list_item.grade.normal_session}
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${list_item.grade.catch_up_session == null}">
                                                        <span>-</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${list_item.grade.catch_up_session}
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${isApproved}">
                                                        <span>-</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="${pageContext.request.contextPath}/grade/edit?id=${list_item.grade.id}">
                                                            <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/edit_icon.png" alt="Edit icon" width="32" height="32">
                                                        </a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>
                                                <span>-</span>
                                            </td>
                                            <td>
                                                <span>-</span>
                                            </td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/grade/create?pedagogicalRegistration_id=${list_item.pedagogicalRegistration.id}&assessment_id=${assessment.id}&academicYear_id=${acadYear.id}">
                                                    <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/add_icon.png" alt="Create icon" width="32" height="32">
                                                </a>
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