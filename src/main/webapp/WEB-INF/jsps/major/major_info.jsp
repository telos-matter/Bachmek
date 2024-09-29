<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/table.css">
                <title>${estm_name} - Major</title>
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
                    <a href="${pageContext.request.contextPath}/acadStage/create?major_id=${item.id}">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/add_icon.png" alt="Add icon" title="Create a new Academic Stage for this Major" width="32" height="32">
                    </a>
                    <p id="main_title" class="g_h_clr"><a class="g_h_clr" href="${pageContext.request.contextPath}/major/list?hidden=${hidden}">${item.major}</a> academic stages</p>
                    <c:choose>
                        <c:when test="${hidden}">
                            <a href="${pageContext.request.contextPath}/major?id=${item.id}&hidden=0">
                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/hidden_icon.png" alt="Hidden icon" title="Show inactive academic stages as well" width="32" height="32">
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/major?id=${item.id}&hidden=1">
                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/visible_icon.png" alt="Visible icon" title="Show only active academic stages" width="32" height="32">
                            </a>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${item.isActive}">
                            <a href="${pageContext.request.contextPath}/major/activate?id=${item.id}">
                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/active_icon.png" alt="Active icon" title="Inactivate this major" width="32" height="32" onclick="return confirm('Making a Major inactive will forcefully make all of its Academic Stages and their Academic Semesters inactive.\nAre you sure you want to make the ${item.major} Major inactive?')">
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/major/activate?id=${item.id}">
                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/inactive_icon.png" alt="Inactive icon" title="Activate this major" width="32" height="32" onclick="return confirm('Making a Major active will make all of its Academic Stages and their Academic Semesters active, but you will still be able to make them active or inactive idividually.\nAre you sure you want to make the ${item.major} Major active?')">
                            </a>
                        </c:otherwise>
                    </c:choose>
                </header>

                <c:choose>
                        <c:when test="${empty list}">
                            <p id="empty_message">The ${item.major} Major has no Academic Stages to show.</p>
                        </c:when>
                        <c:otherwise>
                            <table>
                                <tr>
                                    <th class="th" rowspan="2">Sequence</th>
                                    <th class="th" rowspan="2">Has diploma</th>
                                    <th class="th" colspan="4">Action</th>
                                </tr>
                                <tr>
                                    <th class="th">(In)active</th>
                                    <th class="th">Edit</th>
                                    <th class="th">Acad. Sem.</th>
                                    <th class="th">Delete</th>
                                </tr>
                                <c:forEach var="list_item" items="${list}">
                                    <tr>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/acadStage?id=${list_item.id}&hidden=${hidden}">
                                                ${list_item.sequence}
                                            </a>
                                        </td>
                                        <td>
                                            ${list_item.isDiploma}
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${item.isActive}">
                                                    <c:choose>
                                                        <c:when test="${list_item.isActive}">
                                                            <a href="${pageContext.request.contextPath}/acadStage/activate?id=${list_item.id}">
                                                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/active_icon.png" alt="Active icon" title="Inactivate this academic stage" width="32" height="32" onclick="return confirm('Making an Academic Stage inactive will forcefully make all of its Academic Semesters inactive.\nAre you sure you want to make the ${list_item.sequence} Academic Stage inactive?')">
                                                            </a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a href="${pageContext.request.contextPath}/acadStage/activate?id=${list_item.id}">
                                                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/inactive_icon.png" alt="Inactive icon" title="Activate this academic stage" width="32" height="32" onclick="return confirm('Making an Academic Stage active will make all of its Academic Semesters active, but you will still be able to make them active or inactive idividually.\nAre you sure you want to make the ${list_item.sequence} Academic Stage active?')">
                                                            </a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <img class="disabled_icon" src="${pageContext.request.contextPath}/resources/images/icons/disabled_inactive_icon.png" alt="Disabled inactive icon" title="To activate this academic stage, first activate the major" width="32" height="32">
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/acadStage/edit?id=${list_item.id}">
                                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/edit_icon.png" alt="Edit icon" width="32" height="32">
                                            </a>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/acadSem/create?acadStage_id=${list_item.id}">
                                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/add_icon.png" alt="Add icon" title="Create a new Academic Semester for this Academic Stage" width="32" height="32">
                                            </a>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/acadStage/delete?id=${list_item.id}">
                                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/delete_icon.png" alt="Delete icon" width="32" height="32" onclick="return confirm('Deleting an Academic Stage will also delete all of its Academic Semesters.\nYou can make it inactive instead.\nAre you sure you want to delete the ${list_item.sequence} Academic Semester?')">
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