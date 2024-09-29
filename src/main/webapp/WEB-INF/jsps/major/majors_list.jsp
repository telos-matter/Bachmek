<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/table.css">
                <title>${estm_name} - Majors</title>
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
                    <p id="main_title" class="g_h_clr">Majors list</p>
                    <c:choose>
                        <c:when test="${hidden}">
                            <a href="${pageContext.request.contextPath}/major/list?hidden=0">
                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/hidden_icon.png" alt="Hidden icon" title="Show inactive Majors as well" width="32" height="32">
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/major/list?hidden=1">
                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/visible_icon.png" alt="Visible icon" title="Show only active Majors" width="32" height="32">
                            </a>
                        </c:otherwise>
                    </c:choose>
                </header>

                <c:choose>
                    <c:when test="${empty list}">
                    <p id="empty_message">There are no Majors to show.</p>
                    </c:when>
                    <c:otherwise>
                        <table>
                            <tr>
                                <th class="th" rowspan="2">Name</th>
                                <th class="th" colspan="2">Action</th>
                            </tr>
                            <tr>
                                <th class="th">(In)active</th>
                                <th class="th">Acad. Stage</th>
                            </tr>
                            <c:forEach var="list_item" items="${list}">
                                <tr>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/major?id=${list_item.id}&hidden=${hidden}">
                                            ${list_item.major}
                                        </a>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${list_item.isActive}">
                                                <a href="${pageContext.request.contextPath}/major/activate?id=${list_item.id}">
                                                    <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/active_icon.png" alt="Active icon" title="Inactivate this major" width="32" height="32" onclick="return confirm('Making a Major inactive will forcefully make all of its Academic Stages and their Academic Semesters inactive.\nAre you sure you want to make the ${list_item.major} Major inactive?')">
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="${pageContext.request.contextPath}/major/activate?id=${list_item.id}">
                                                    <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/inactive_icon.png" alt="Inactive icon" title="Activate this major" width="32" height="32" onclick="return confirm('Making a Major active will make all of its Academic Stages and their Academic Semesters active, but you will still be able to make them active or inactive idividually.\nAre you sure you want to make the ${list_item.major} Major active?')">
                                                </a>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/acadStage/create?major_id=${list_item.id}">
                                            <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/add_icon.png" alt="Add icon" title="Create a new Academic Stage for this Major" width="32" height="32">
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