<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/table.css">
                <title>${estm_name} - Semester</title>
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
                    <p id="main_title" class="g_h_clr"><a class="g_h_clr" href="${pageContext.request.contextPath}/semester/list?acadYear_id=${item.academicYear.id}">${item.name}</a> modules</p>
                    <a href="${pageContext.request.contextPath}/semester/delete?id=${item.id}">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/delete_icon.png" alt="Delete icon" width="32" height="32" onclick="return confirm('Are you sure you want to delete the ${item.name} for the Acad. Year ${item.academicYear.ABBR} Semester?')">
                    </a>
                </header>

                <table>
                    <tr>
                        <th class="th">Name</th>
                    </tr>
                    <c:forEach var="list_item" items="${list}">
                        <tr>
                            <td>
                                <a href="${pageContext.request.contextPath}/module?id=${list_item.module.id}">
                                    ${list_item.module.name}
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

                <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
        </body>
</html>