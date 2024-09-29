<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/table.css">
                <title>${estm_name} - MMs</title>
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
                    <select id="academicYear"  onchange="window.location.href = '${pageContext.request.contextPath}/mm?acadYear_id=' +document.getElementById('academicYear').value +'&type=${type}'">
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
                    <select id="type"  onchange="window.location.href = '${pageContext.request.contextPath}/mm?acadYear_id=${acadYear.id}&type=' +document.getElementById('type').value">
                        <c:forEach var="list_item" items="${types_list}">
                            <c:choose>
                                <c:when test="${list_item == type}">
                                    <option value="${list_item}" selected>${list_item.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${list_item}">${list_item.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach> 
                    </select>
                    <p id="main_title" class="g_h_clr">Approved ${type.name} list</p>
                </header>

                <c:choose>
                    <c:when test="${empty list}">
                        <p id="empty_message">There are no ${type.name} to show.</p>
                    </c:when>
                    <c:otherwise>
                        <table>
                            <tr>
                                <th class="th" rowspan="2">${type.name}</th>
                                <th class="th" colspan="2">Download</th>
                            </tr>
                            <tr>
                                <th class="th">Grades</th>
                                <th class="th">Catch up session</th>
                            </tr>
                            <c:forEach var="list_item" items="${list}">
                                <tr>
                                    <td>
                                        ${list_item.name}
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/mm/download?acadYear_id=${acadYear.id}&type=${type}&normal=1&id=${list_item.id}" target="_blank">
                                            <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/download_icon.png" alt="Download icon" title="Download list of Students' grades" width="32" height="32">
                                        </a>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/mm/download?acadYear_id=${acadYear.id}&type=${type}&normal=0&id=${list_item.id}" target="_blank">
                                            <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/download_icon.png" alt="Download icon" title="Download list of Student that should pass the Catch up session" width="32" height="32">
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