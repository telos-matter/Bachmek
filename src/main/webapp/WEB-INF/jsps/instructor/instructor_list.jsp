<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/table.css">
                <title>${estm_name} - Instructors</title>
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
                    <select id="academicYear"  onchange="window.location.href = '${pageContext.request.contextPath}/instructor/list?acadYear_id=' +document.getElementById('academicYear').value +'&instructorRole=${instructorRole}'">
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
                    <select id="instructorRole"  onchange="window.location.href = '${pageContext.request.contextPath}/instructor/list?acadYear_id=${acadYear.id}&instructorRole=' +document.getElementById('instructorRole').value">
                        <c:forEach var="list_item" items="${instructorRoles_list}">
                            <c:choose>
                                <c:when test="${list_item == instructorRole}">
                                    <option value="${list_item}" selected>${list_item.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${list_item}">${list_item.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach> 
                    </select>
                    <p id="main_title" class="g_h_clr">${instructorRole.name} list</p>
                    <a href="${pageContext.request.contextPath}/instructor/create?acadYear_id=${acadYear.id}&instructorRole=${instructorRole}">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/add_icon.png" alt="Add icon" title="Create a new ${instructorRole.name} for this Acad. Year" width="32" height="32">
                    </a>
                    <c:if test="${not empty list}">
                        <a href="${pageContext.request.contextPath}/instructor/download?acadYear_id=${acadYear.id}&instructorRole=${instructorRole}" target="_blank">
                            <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/download_icon.png" alt="Download icon" title="Download this list" width="32" height="32">
                        </a>
                    </c:if>
                </header>

                <c:choose>
                    <c:when test="${empty list}">
                        <p id="empty_message">There are no ${instructorRole.name} to show.</p>
                    </c:when>
                    <c:otherwise>
                        <table>
                            <tr>
                                <th class="th">Instructors' CIN</th>
                                <th class="th">Instructors' full name</th>
                                <th class="th">Associated ${instructorRole.associatedClass.simpleName}</th>
                            </tr>
                            <c:forEach var="list_item" items="${list}">
                                <tr>
                                    <td>
                                        ${list_item.instructor.user.cin}
                                    </td>
                                    <td>
                                        ${list_item.instructor.user.formalFullName}
                                    </td>
                                    <td>
                                        ${list_item.associatedItem.name}
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:otherwise>
                </c:choose>

                <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
        </body>
</html>