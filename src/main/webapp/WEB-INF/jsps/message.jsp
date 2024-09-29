<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/foundation.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/header.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/plain_panel.css">
        <title>${estm_name} - ?</title>
        <style>
            .message {
                font-size: xx-large;
                margin: auto;
            }
            .error {
                color: red;
            }
            .succes {
                color: green;
            }
            .code {
                font-size: 128px;
            }
        </style>
    </head>
    <body>
        <%@ include file= "/resources/includes/header.jsp" %>
        <div id="plain_panel">
            <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <c:if test="${succes_code != null}">
                <span class="succes message"><span class="succes code">${succes_code}</span> ${message}</span>
            </c:if>
            <c:if test="${error_code != null}">
                <span class="error message"><span class="error code">${error_code}</span> ${message}</span>
            </c:if>
        </div>
        <div id="panel_footer"></div>
    </body>
</html>