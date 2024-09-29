<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/table.css">
                <title>${estm_name} - Modules</title>
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
                    <p id="main_title" class="g_h_clr">Modules list</p>
                    <a href="${pageContext.request.contextPath}/module/create">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/add_icon.png" alt="Add icon" title="Create a new module" width="32" height="32">
                    </a>
                    <c:choose>
                        <c:when test="${hidden}">
                            <a href="${pageContext.request.contextPath}/module/list?hidden=0">
                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/hidden_icon.png" alt="Hidden icon" title="Show inactive modules as well" width="32" height="32">
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/module/list?hidden=1">
                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/visible_icon.png" alt="Visible icon" title="Show only active modules" width="32" height="32">
                            </a>
                        </c:otherwise>
                    </c:choose>
                </header>

                <c:choose>
                    <c:when test="${empty list}">
                    <p id="empty_message">There are no Modules to show.</p>
                    </c:when>
                    <c:otherwise>
                        <table>
                            <tr>
                                <th class="th" rowspan="2">Name</th>
                                <th class="th" colspan="4">Action</th>
                            </tr>
                            <tr>
                                <th class="th">(In)active</th>
                                <th class="th">Edit</th>
                                <th class="th">Element</th>
                                <th class="th" >Delete</th>
                            </tr>
                            <c:forEach var="list_item" items="${list}">
                                <tr>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/module?id=${list_item.id}&hidden=${hidden}">
                                            ${list_item.name}
                                        </a>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${list_item.isActive}">
                                                <a href="${pageContext.request.contextPath}/module/activate?id=${list_item.id}">
                                                    <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/active_icon.png" alt="Active icon" title="Inactivate this module" width="32" height="32" onclick="return confirm('Making a Module inactive will forcefully make all of its Elements and their Assessments inactive.\nAre you sure you want to make the ${list_item.name} Module inactive?')">
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="${pageContext.request.contextPath}/module/activate?id=${list_item.id}">
                                                    <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/inactive_icon.png" alt="Inactive icon" title="Activate this module" width="32" height="32" onclick="return confirm('Making a Module active will make all of its Elements and their Assessments active, but you will still be able to make them active or inactive idividually.\nAre you sure you want to make the ${list_item.name} Module active?')">
                                                </a>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/module/edit?id=${list_item.id}">
                                            <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/edit_icon.png" alt="Edit icon" width="32" height="32">
                                        </a>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/element/create?module_id=${list_item.id}">
                                            <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/add_icon.png" alt="Add icon" title="Create a new Element for this Module" width="32" height="32">
                                        </a>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/module/delete?id=${list_item.id}">
                                            <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/delete_icon.png" alt="Delete icon" width="32" height="32" onclick="return confirm('Deleting a Module will also delete all of its Elements and their Assessments.\nYou can make it inactive instead.\nAre you sure you want to delete the ${list_item.name} Module?')">
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