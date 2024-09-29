<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
            <meta charset="UTF-8">
            <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/table.css">
            <title>${estm_name} - File handling Grades</title>
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
                input {
                        margin-inline: 1.2vw;
                }
                .submit_button:hover {
                        cursor: pointer;
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
                <p id="main_title" class="g_h_clr">Handle ${acadYear.ABBR}s' ${assessment.fullName} Assessment trough Excel</p>
            </header>

            <c:if test="${error_message != null}">
                <span class="error_message">${error_message}</span>
            </c:if>

            <table>
                <tr>
                    <th class="th">Upload</th>
                    <th class="th">Download</th>
                </tr>
                <tr>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/grade/upload?acadYear_id=${acadYear.id}&assessment_id=${assessment.id}" enctype="multipart/form-data">
                            <input type="file" name="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" required>
                            <input class="submit_button" type="submit" value="Send">
                        </form>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/grade/download?acadYear_id=${acadYear.id}&assessment_id=${assessment.id}" target="_blank">
                            <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/download_icon.png" alt="Download icon" title="Download template" width="32" height="32">
                        </a>
                    </td>
                </tr>
            </table>           

            <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
    </body>
</html>