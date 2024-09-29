<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/table.css">
                <title>${estm_name} - Academic Stage</title>
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
                    header .disabled_icon {
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
                    <a href="${pageContext.request.contextPath}/acadSem/create?acadStage_id=${item.id}">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/add_icon.png" alt="Add icon" title="Create a new Academic Semester for this Academic Stage" width="32" height="32">
                    </a>
                    <p id="main_title" class="g_h_clr"><a class="g_h_clr" href="${pageContext.request.contextPath}/major?id=${item.major.id}&hidden=${hidden}">Acad. Stage #${item.sequence}</a> Acad. Sems. <span class="g_h_clr" style="font-size:large;">(has Diploma? ${item.isDiploma})</span></p>
                    <c:choose>
                        <c:when test="${hidden}">
                            <a href="${pageContext.request.contextPath}/acadStage?id=${item.id}&hidden=0">
                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/hidden_icon.png" alt="Hidden icon" title="Show inactive academic semesters as well" width="32" height="32">
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/acadStage?id=${item.id}&hidden=1">
                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/visible_icon.png" alt="Visible icon" title="Show only active academic semesters" width="32" height="32">
                            </a>
                        </c:otherwise>
                    </c:choose>
                    <a href="${pageContext.request.contextPath}/acadStage/edit?id=${item.id}">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/edit_icon.png" alt="Edit icon" width="32" height="32">
                    </a>
                    <c:choose>
                        <c:when test="${item.major.isActive}">
                            <c:choose>
                                <c:when test="${item.isActive}">
                                    <a href="${pageContext.request.contextPath}/acadStage/activate?id=${item.id}">
                                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/active_icon.png" alt="Active icon" title="Inactivate this academic stage" width="32" height="32" onclick="return confirm('Making an Academic Stage inactive will forcefully make all of its Academic Semesters inactive.\nAre you sure you want to make the ${item.sequence} Academic Stage inactive?')">
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/acadStage/activate?id=${item.id}">
                                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/inactive_icon.png" alt="Inactive icon" title="Activate this academic stage" width="32" height="32" onclick="return confirm('Making an Academic Stage active will make all of its Academic Semesters active, but you will still be able to make them active or inactive idividually.\nAre you sure you want to make the ${item.sequence} Academic Stage active?')">
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <img class="disabled_icon" src="${pageContext.request.contextPath}/resources/images/icons/disabled_inactive_icon.png" alt="Disabled inactive icon" title="To activate this academic stage, first activate the major" width="32" height="32">
                        </c:otherwise>
                    </c:choose>
                    <a href="${pageContext.request.contextPath}/acadStage/delete?id=${item.id}">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/delete_icon.png" alt="Delete icon" width="32" height="32" onclick="return confirm('Deleting an Academic Stage will also delete all of its Academic Semesters.\nYou can make it inactive instead.\nAre you sure you want to delete the ${item.sequence} Academic Stage?')">
                    </a>
                </header>

                <c:choose>
                        <c:when test="${empty list}">
                            <p id="empty_message">The ${item.sequence} Academic Stage has no Academic Semesters to show.</p>
                        </c:when>
                        <c:otherwise>
                            <table>
                                <tr>
                                    <th class="th" rowspan="2">Sequence</th>
                                    <th class="th" colspan="3">Action</th>
                                </tr>
                                <tr>
                                    <th class="th">(In)active</th>
                                    <th class="th">Edit</th>
                                    <th class="th">Delete</th>
                                </tr>
                                <c:forEach var="list_item" items="${list}">
                                    <tr>
                                        <td>
                                            ${list_item.sequence}
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${item.isActive}">
                                                    <c:choose>
                                                        <c:when test="${list_item.isActive}">
                                                            <a href="${pageContext.request.contextPath}/acadSem/activate?id=${list_item.id}">
                                                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/active_icon.png" alt="Active icon" title="Inactivate this academic semester" width="32" height="32">
                                                            </a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a href="${pageContext.request.contextPath}/acadSem/activate?id=${list_item.id}">
                                                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/inactive_icon.png" alt="Inactive icon" title="Activate this academic semester" width="32" height="32">
                                                            </a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <img class="disabled_icon" src="${pageContext.request.contextPath}/resources/images/icons/disabled_inactive_icon.png" alt="Disabled inactive icon" title="To activate this academic semester, first activate the academic stage" width="32" height="32">
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/acadSem/edit?id=${list_item.id}">
                                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/edit_icon.png" alt="Edit icon" width="32" height="32">
                                            </a>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/acadSem/delete?id=${list_item.id}">
                                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/delete_icon.png" alt="Delete icon" width="32" height="32"  onclick="return confirm('Are you sure you want to delete the ${list_item.sequence} academic semester?\nYou can make it inactive instead.')">
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