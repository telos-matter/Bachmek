<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/table.css">
                <title>${estm_name} - Semesters</title>
                <style>
                    header {
                        display: flex;
                        flex-flow: row nowrap;
                        align-items: center;
                        margin-bottom: 3vw;
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
                    <select id="academicYear"  onchange="window.location.href = '${pageContext.request.contextPath}/semester/list?acadYear_id=' +document.getElementById('academicYear').value">
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
                    <p id="main_title" class="g_h_clr">${acadYear.ABBR} Semesters list</p>
                    <a href="${pageContext.request.contextPath}/semester/create?acadYear_id=${acadYear.id}">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/add_icon.png" alt="Add icon" title="Create a new Semester for this Acad. Year" width="32" height="32">
                    </a>
                </header>

                <c:choose>
                    <c:when test="${empty list}">
                        <p id="empty_message">There are no Semesters to show.</p>
                    </c:when>
                    <c:otherwise>
                        <table>
                            <tr>
                                <th class="th">Name</th>
                                <th class="th">Delete</th>
                            </tr>
                            <c:forEach var="list_item" items="${list}">
                                <tr>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/semester?id=${list_item.id}">
                                            ${list_item.shortName}
                                        </a>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/semester/delete?id=${list_item.id}">
                                            <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/delete_icon.png" alt="Delete icon" width="32" height="32" onclick="return confirm('Are you sure you want to delete the ${list_item.name} Semester?')">
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