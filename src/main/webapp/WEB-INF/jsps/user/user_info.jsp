<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/table.css">
                <title>${estm_name} - User</title>
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
                </style>
                <script defer>
                    var password = '${decrypted_password}'
                    var visible = false
                    function change() {
                        visible = !visible
                        var field = document.getElementById("password")
                        if (visible) {
                            field.innerHTML = password
                        } else {
                            field.innerHTML = '**********'
                        }
                    }
                </script>
        </head>
        <body>
                <%@ include file= "/resources/includes/main_panel_header.jsp" %>

                <header>
                    <a href="${pageContext.request.contextPath}/user/list?filter_role=${item_role}">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/back_icon.png" alt="Back icon" width="32" height="32">
                    </a>
                    <p id="main_title" class="g_h_clr">${item.fullName} account information</p>
                    <a href="${pageContext.request.contextPath}/user/edit?id=${item.id}">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/edit_icon.png" alt="Edit icon" width="32" height="32">
                    </a>
                    <c:choose>
                        <c:when test="${item.isActive}">
                            <a href="${pageContext.request.contextPath}/user/activate?id=${item.id}">
                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/active_icon.png" alt="Active icon" title="Inactivate this user" width="32" height="32">
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/user/activate?id=${item.id}">
                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/inactive_icon.png" alt="Inactive icon" title="Activate this user" width="32" height="32">
                            </a>
                        </c:otherwise>
                    </c:choose>
                </header>

                <table>
                    <tr>
                        <th class="th">Role</th>
                        <td>${item_role}</td>
                    </tr>
                    <tr>
                        <th class="th">Username</th>
                        <td>${item.username}</td>
                    </tr>
                    <tr>
                        <th class="th"><span onclick="change()" style="cursor: pointer;">Password</span></th>
                        <td><span id="password">**********</span></td>
                    </tr>
                    <tr>
                        <th class="th">Email</th>
                        <td>${item.email}</td>
                    </tr>
                    <tr>
                        <th class="th">First name</th>
                        <td>${item.first_name}</td>
                    </tr>
                    <tr>
                        <th class="th">Last name</th>
                        <td>${item.last_name}</td>
                    </tr>
                    <tr>
                        <th class="th">Gender</th>
                        <td>${item.gender.gender}</td>
                    </tr>
                    <tr>
                        <th class="th">Birthdate</th>
                        <td>${item.birthdate}</td>
                    </tr>
                    <tr>
                        <th class="th">Phone number</th>
                        <td>${item.phone_number}</td>
                    </tr>
                    <tr>
                        <th class="th">Personal address</th>
                        <td>${item.personal_address}</td>
                    </tr>
                    <tr>
                        <th class="th">CIN</th>
                        <td>${item.cin}</td>
                    </tr>
                    <c:if test="${item_role.toString().equals('INSTRUCTOR')}">
                        <tr>
                            <th class="th">Discipline</th>
                            <td>${item.instructor.discipline}</td>
                        </tr>
                    </c:if>
                    <tr>
                        <th class="th">Creation date</th>
                        <td>${item.creation_date}</td>
                    </tr>   
                </table>

                <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
        </body>
</html>