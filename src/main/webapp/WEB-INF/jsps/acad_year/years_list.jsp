<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/table.css">
                <title>${estm_name} - Academic Years</title>
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
                    <p id="main_title" class="g_h_clr">Academic Years list</p>
                    <a href="${pageContext.request.contextPath}/acadYear/create">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/add_icon.png" alt="Add icon" width="32" height="32">
                    </a>
                </header>

                <c:choose>
                        <c:when test="${empty list}">
                            <p id="empty_message">There are no Academic Years.</p>
                        </c:when>
                        <c:otherwise>
                            <table>
                                <tr>
                                    <th class="th" rowspan="2">Abbreviation</th>
                                    <th class="th" rowspan="2">Start date</th>
                                    <th class="th" rowspan="2">Finish date</th>
                                    <th class="th" colspan="2">Action</th>
                                </tr>
                                <tr>
                                    <th class="th">Edit</th>
                                    <th class="th" >Delete</th>
                                </tr>
                                <c:forEach var="list_item" items="${list}">
                                    <tr>
                                        <td>
                                            ${list_item.ABBR}
                                        </td>
                                        <td>
                                            ${list_item.start_date}
                                        </td>
                                        <td>
                                            ${list_item.finish_date}
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/acadYear/edit?id=${list_item.id}">
                                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/edit_icon.png" alt="Edit icon" width="32" height="32">
                                            </a>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/acadYear/delete?id=${list_item.id}">
                                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/delete_icon.png" alt="Delete icon" width="32" height="32" onclick="return confirm('Are you sure you want to delete the ${list_item.ABBR} Year?')">
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