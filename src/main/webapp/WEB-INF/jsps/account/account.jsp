<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>${estm_name} - Account</title>
        <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
        <style>
            header {
                display: flex;
                flex-flow: column nowrap;
                align-items: center;
                margin-top: auto;
                margin-bottom: auto;
            }
            #main_title {
                font-size: 64px;
                margin-bottom: 5vw;
            }
            header .icon {
                margin-right: 1vw;
            }
            header .text {
                display: flex;
                flex-flow: row nowrap;
                align-items: center;
                margin-right: auto;
                margin-left: 3vw;
                margin-bottom: 2vw;
            }
            .icon:hover {
                transform: scale(1.2);
                transition-duration: .3s;
            }
        </style>
    </head>
    <body>
            <%@ include file= "/resources/includes/main_panel_header.jsp" %>

            <header>
                <p id="main_title" class="g_h_clr">Welcome ${item.formalFullName}</p>
                <c:if test="${isStudent}">
                    <a class="text" href="${pageContext.request.contextPath}/account/info">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/info_icon.png" alt="Information icon" width="32" height="32"> Your personal information
                    </a>
                    <a class="text" href="${pageContext.request.contextPath}/account/grades">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/graph_icon.png" alt="Graph icon" width="32" height="32"> Your grades
                    </a>
                </c:if>
                <a class="text" href="${pageContext.request.contextPath}/account/password">
                    <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/settings_icon.png" alt="Settings icon" width="32" height="32"> Change your password
                </a>
                <a class="text" href="${pageContext.request.contextPath}/account/logout">
                    <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/logout_icon.png" alt="Logout icon" width="32" height="32"> Logout
                </a>
            </header>

            <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
    </body>
</html>