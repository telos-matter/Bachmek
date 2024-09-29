<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/table.css">
                <title>${estm_name} - Users</title>
                <style>
                    header {
                        display: flex;
                        flex-flow: row nowrap;
                        align-items: center;
                        margin-bottom: 3vw;
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
                    <select id="filter_role"  onchange="window.location.href = '${pageContext.request.contextPath}/user/list?filter_role=' +document.getElementById('filter_role').value +'&hidden=${hidden}'">
                        <c:forEach var="list_item" items="${roles_list}">
                            <c:choose>
                                <c:when test="${list_item == filter_role}">
                                    <option value="${list_item}" selected>${list_item}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${list_item}">${list_item}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach> 
                    </select>
                    <p id="main_title" class="g_h_clr">Users list</p>
                    <a href="${pageContext.request.contextPath}/user/create?createAdmin=1">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/add_administrator_icon.png" alt="Add icon" title="Create a new Administrator" width="32" height="32">
                    </a>
                    <a href="${pageContext.request.contextPath}/user/create?createAdmin=0">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/add_instructor_icon.png" alt="Add icon" title="Create a new Instructor" width="32" height="32">
                    </a>
                    <c:choose>
                        <c:when test="${hidden}">
                            <a href="${pageContext.request.contextPath}/user/list?filter_role=${filter_role}&hidden=0">
                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/hidden_icon.png" alt="Hidden icon" title="Show inactive users as well" width="32" height="32">
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/user/list?filter_role=${filter_role}&hidden=1">
                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/visible_icon.png" alt="Visible icon" title="Show only active users" width="32" height="32">
                            </a>
                        </c:otherwise>
                    </c:choose>
                </header>

                <c:choose>
                    <c:when test="${empty list}">
                    <p id="empty_message">There are no Users with the specified Role to show.</p>
                    </c:when>
                    <c:otherwise>
                        <table>
                            <tr>
                                <th class="th" rowspan="2">Username</th>
                                <th class="th" rowspan="2">Full name</th>
                                <th class="th" colspan="2">Action</th>
                            </tr>
                            <tr>
                                <th class="th">(In)active</th>
                                <th class="th">Edit</th>
                            </tr>
                            <c:forEach var="list_item" items="${list}">
                                <tr>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/user?id=${list_item.id}">
                                            ${list_item.username}
                                        </a>
                                    </td>
                                    <td>
                                        ${list_item.fullName}
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${list_item.isActive}">
                                                <a href="${pageContext.request.contextPath}/user/activate?id=${list_item.id}">
                                                    <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/active_icon.png" alt="Active icon" title="Inactivate this account" width="32" height="32">
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="${pageContext.request.contextPath}/user/activate?id=${list_item.id}">
                                                    <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/inactive_icon.png" alt="Inactive icon" title="Activate this account" width="32" height="32">
                                                </a>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/user/edit?id=${list_item.id}">
                                            <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/edit_icon.png" alt="Edit icon" width="32" height="32">
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