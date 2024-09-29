<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/table.css">
                <title>${estm_name} - Administrative Registration</title>
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
        </head>
        <body>
                <%@ include file= "/resources/includes/main_panel_header.jsp" %>

                <header>
                    <a href="${pageContext.request.contextPath}/adminReg/list">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/back_icon.png" alt="Go back icon" width="32" height="32">
                    </a>
                    <c:choose>
                        <c:when test="${item.isActive}">
                            <a href="${pageContext.request.contextPath}/pedagReg/create/next?adminReg_id=${item.id}">
                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/add_icon.png" alt="Add icon" title="Create a new Pedagogical Registration for this Administrative Registration" width="32" height="32">
                            </a>
                        </c:when>
                        <c:otherwise>
                            <img src="${pageContext.request.contextPath}/resources/images/icons/disabled_add_icon.png" alt="Disabled add icon" title="To create a new Pedagogical Registration for this Administrative Registration first activate it" width="32" height="32">
                        </c:otherwise>
                    </c:choose>
                    <p id="main_title" class="g_h_clr">${item.fullName} Admin. Reg. information</p>
                    <a href="${pageContext.request.contextPath}/adminReg/edit?id=${item.id}">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/edit_icon.png" alt="Edit icon" width="32" height="32">
                    </a>
                    <c:choose>
                        <c:when test="${item.isActive}">
                            <a href="${pageContext.request.contextPath}/adminReg/activate?id=${item.id}">
                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/active_icon.png" alt="Active icon" title="Inactivate this Administrative Registration" width="32" height="32">
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/adminReg/activate?id=${item.id}">
                                <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/inactive_icon.png" alt="Inactive icon" title="Activate this Administrative Registration" width="32" height="32">
                            </a>
                        </c:otherwise>
                    </c:choose>
                    <a href="${pageContext.request.contextPath}/adminReg/delete?id=${item.id}">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/delete_icon.png" alt="Delete icon" width="32" height="32" onclick="return confirm('Are you sure you want to delete this Administrative Registration?\nIt will also delete its Online Registration and its Pedagogical Registrations')">
                    </a>
                </header>

                <table>
                    <tr>
                        <th class="th">Massar code</th>
                        <td>${item.onlineRegistration.massar_code}</td>
                    </tr>
                    <tr>
                        <th class="th">الإسم</th>
                        <td>${item.onlineRegistration.ar_first_name}</td>
                    </tr>
                    <tr>
                        <th class="th">النسب</th>
                        <td>${item.onlineRegistration.ar_last_name}</td>
                    </tr>
                    <tr>
                        <th class="th">First name</th>
                        <td>${item.onlineRegistration.first_name}</td>
                    </tr>
                    <tr>
                        <th class="th">Last name</th>
                        <td>${item.onlineRegistration.last_name}</td>
                    </tr>
                    <tr>
                        <th class="th">CIN</th>
                        <td>${item.onlineRegistration.cin}</td>
                    </tr>
                    <tr>
                        <th class="th">Nationality</th>
                        <td>${item.onlineRegistration.nationality}</td>
                    </tr>
                    <tr>
                        <th class="th">Gender</th>
                        <td>${item.onlineRegistration.gender.gender}</td>
                    </tr>
                    <tr>
                        <th class="th">Birthdate</th>
                        <td>${item.onlineRegistration.birthdate}</td>
                    </tr>
                    <tr>
                        <th class="th">مكان الولادة</th>
                        <td>${item.onlineRegistration.ar_birth_place}</td>
                    </tr>
                    <tr>
                        <th class="th">Birth place</th>
                        <td>${item.onlineRegistration.birth_place}</td>
                    </tr>
                    <tr>
                        <th class="th">Residence town</th>
                        <td>${item.onlineRegistration.residence_town}</td>
                    </tr>
                    <tr>
                        <th class="th">Province</th>
                        <td>${item.onlineRegistration.province}</td>
                    </tr>
                    <tr>
                        <th class="th">Bac year</th>
                        <td>${item.onlineRegistration.bac_year}</td>
                    </tr>
                    <tr>
                        <th class="th">High school</th>
                        <td>${item.onlineRegistration.high_school}</td>
                    </tr>
                    <tr>
                        <th class="th">Bac place</th>
                        <td>${item.onlineRegistration.bac_place}</td>
                    </tr>
                    <tr>
                        <th class="th">Academy</th>
                        <td>${item.onlineRegistration.academy}</td>
                    </tr>
                    <tr>
                        <th class="th">Bac serie</th>
                        <td>${item.onlineRegistration.bac_serie.bac_serie}</td>
                    </tr>
                    <tr>
                        <th class="th">Bac honour</th>
                        <td>${item.onlineRegistration.bac_honour.bac_honour}</td>
                    </tr>
                    <tr>
                        <th class="th">Major</th>
                        <td>${item.onlineRegistration.major.major}</td>
                    </tr>
                    <tr>
                        <th class="th">Online Reg. date</th>
                        <td>${item.onlineRegistration.registration_date}</td>
                    </tr>
                    <tr>
                        <th class="th">CNE</th>
                        <td>${item.cne}</td>
                    </tr>
                    <tr>
                        <th class="th">Personal address</th>
                        <td>${item.personal_address}</td>
                    </tr>
                    <tr>
                        <th class="th">Phone number</th>
                        <td>${item.phone_number}</td>
                    </tr>
                    <tr>
                        <th class="th">Parents address</th>
                        <td>${item.parents_address}</td>
                    </tr>
                    <tr>
                        <th class="th">Diplomas</th>
                        <td style="white-space:pre-line;"><c:forTokens var="list_item" items="${item.diplomas}" delims=",">${list_item}
                        </c:forTokens></td>
                    </tr>
                    <tr>
                        <th class="th">Has tuition</th>
                        <td>${item.isTuition}</td>
                    </tr>
                    <tr>
                        <th class="th">Academic year</th>
                        <td>${item.academicYear.ABBR}</td>
                    </tr>
                    <tr>
                        <th class="th">Academic stage</th>
                        <td>${item.academicStage.name}</td>
                    </tr>
                    <tr>
                        <th class="th">Admin. Reg. date</th>
                        <td>${item.registration_date}</td>
                    </tr>   
                </table>

                <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
        </body>
</html>