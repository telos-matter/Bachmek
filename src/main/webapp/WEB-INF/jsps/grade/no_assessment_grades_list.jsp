<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <title>${estm_name} - Grades</title>
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
                    .error_message {
                        font-size: x-large;
                        margin: auto;
                        margin-top: 2vh;
                        margin-bottom: 3vh;
                        color: red;
                    }
                </style>
        </head>
        <body>
                <%@ include file= "/resources/includes/main_panel_header.jsp" %>

                <header>
                    <select id="academicYear"  onchange="window.location.href = '${pageContext.request.contextPath}/grade/list?acadYear_id=' +document.getElementById('academicYear').value">
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
                </header>
                
                <span class="error_message">${error_message}</span>
                
                <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
        </body>
</html>