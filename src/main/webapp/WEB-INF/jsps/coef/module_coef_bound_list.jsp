<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/table.css">
                <title>${estm_name} - Coefficients</title>
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
                    <select id="academicYear"  onchange="window.location.href = '${pageContext.request.contextPath}/coef/list?acadYear_id=' +document.getElementById('academicYear').value +'&coefEnum=${coefEnum}'">
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
                    <select id="coefEnum"  onchange="window.location.href = '${pageContext.request.contextPath}/coef/list?acadYear_id=${acadYear.id}&coefEnum=' +document.getElementById('coefEnum').value">
                        <c:forEach var="list_item" items="${coefEnums_list}">
                            <c:choose>
                                <c:when test="${list_item == coefEnum}">
                                    <option value="${list_item}" selected>${list_item.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${list_item}">${list_item.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach> 
                    </select>
                    <p id="main_title" class="g_h_clr">${coefEnum.name} list</p>
                    <c:if test="${canCreate}">
                        <a href="${pageContext.request.contextPath}/coef/create?acadYear_id=${acadYear.id}&coefEnum=${coefEnum}">
                            <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/add_icon.png" alt="Add icon" title="Create a new ${coefEnum.name} for this Acad. Year" width="32" height="32">
                        </a>
                    </c:if>
                </header>

                <c:choose>
                    <c:when test="${empty list}">
                        <p id="empty_message">There are no ${coefEnum.name} to show.</p>
                    </c:when>
                    <c:otherwise>
                        <table>
                            <tr>
                                <th class="th">Module</th>
                                <th class="th">Coefficient</th>
                                <th class="th">Passing bound</th>
                                <th class="th">Failling bound</th>
                            </tr>
                            <c:forEach var="list_item" items="${list}">
                                <tr>
                                    <td>
                                        ${list_item.module.name}
                                    </td>
                                    <td>
                                        ${list_item.coefficient}
                                    </td>
                                    <td>
                                        ${list_item.passing_bound}
                                    </td>
                                    <td>
                                        ${list_item.failling_bound}
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:otherwise>
                </c:choose>

                <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
        </body>
</html>