<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/table.css">
                <title>${estm_name} - Student information</title>
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
                    <a href="${pageContext.request.contextPath}/account">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/back_icon.png" alt="Go back icon" width="32" height="32">
                    </a>
                    <p id="main_title" class="g_h_clr">${item.fullName} Admin. Reg. information</p>
                </header>

                <table>
                    <tr>
                        <th class="th">Massar code</th>
                        <td>${item.student.administrativeRegistration.onlineRegistration.massar_code}</td>
                    </tr>
                    <tr>
                        <th class="th">الإسم</th>
                        <td>${item.student.administrativeRegistration.onlineRegistration.ar_first_name}</td>
                    </tr>
                    <tr>
                        <th class="th">النسب</th>
                        <td>${item.student.administrativeRegistration.onlineRegistration.ar_last_name}</td>
                    </tr>
                    <tr>
                        <th class="th">First name</th>
                        <td>${item.student.administrativeRegistration.onlineRegistration.first_name}</td>
                    </tr>
                    <tr>
                        <th class="th">Last name</th>
                        <td>${item.student.administrativeRegistration.onlineRegistration.last_name}</td>
                    </tr>
                    <tr>
                        <th class="th">CIN</th>
                        <td>${item.student.administrativeRegistration.onlineRegistration.cin}</td>
                    </tr>
                    <tr>
                        <th class="th">Nationality</th>
                        <td>${item.student.administrativeRegistration.onlineRegistration.nationality}</td>
                    </tr>
                    <tr>
                        <th class="th">Gender</th>
                        <td>${item.student.administrativeRegistration.onlineRegistration.gender.gender}</td>
                    </tr>
                    <tr>
                        <th class="th">Birthdate</th>
                        <td>${item.student.administrativeRegistration.onlineRegistration.birthdate}</td>
                    </tr>
                    <tr>
                        <th class="th">مكان الولادة</th>
                        <td>${item.student.administrativeRegistration.onlineRegistration.ar_birth_place}</td>
                    </tr>
                    <tr>
                        <th class="th">Birth place</th>
                        <td>${item.student.administrativeRegistration.onlineRegistration.birth_place}</td>
                    </tr>
                    <tr>
                        <th class="th">Residence town</th>
                        <td>${item.student.administrativeRegistration.onlineRegistration.residence_town}</td>
                    </tr>
                    <tr>
                        <th class="th">Province</th>
                        <td>${item.student.administrativeRegistration.onlineRegistration.province}</td>
                    </tr>
                    <tr>
                        <th class="th">Bac year</th>
                        <td>${item.student.administrativeRegistration.onlineRegistration.bac_year}</td>
                    </tr>
                    <tr>
                        <th class="th">High school</th>
                        <td>${item.student.administrativeRegistration.onlineRegistration.high_school}</td>
                    </tr>
                    <tr>
                        <th class="th">Bac place</th>
                        <td>${item.student.administrativeRegistration.onlineRegistration.bac_place}</td>
                    </tr>
                    <tr>
                        <th class="th">Academy</th>
                        <td>${item.student.administrativeRegistration.onlineRegistration.academy}</td>
                    </tr>
                    <tr>
                        <th class="th">Bac serie</th>
                        <td>${item.student.administrativeRegistration.onlineRegistration.bac_serie.bac_serie}</td>
                    </tr>
                    <tr>
                        <th class="th">Bac honour</th>
                        <td>${item.student.administrativeRegistration.onlineRegistration.bac_honour.bac_honour}</td>
                    </tr>
                    <tr>
                        <th class="th">Major</th>
                        <td>${item.student.administrativeRegistration.onlineRegistration.major.major}</td>
                    </tr>
                    <tr>
                        <th class="th">CNE</th>
                        <td>${item.student.administrativeRegistration.cne}</td>
                    </tr>
                    <tr>
                        <th class="th">Personal address</th>
                        <td>${item.student.administrativeRegistration.personal_address}</td>
                    </tr>
                    <tr>
                        <th class="th">Phone number</th>
                        <td>${item.student.administrativeRegistration.phone_number}</td>
                    </tr>
                    <tr>
                        <th class="th">Parents address</th>
                        <td>${item.student.administrativeRegistration.parents_address}</td>
                    </tr>
                    <tr>
                        <th class="th">Diplomas</th>
                        <td style="white-space:pre-line;"><c:forTokens var="list_item" items="${item.student.administrativeRegistration.diplomas}" delims=",">${list_item}
                        </c:forTokens></td>
                    </tr>
                    <tr>
                        <th class="th">Has tuition</th>
                        <td>${item.student.administrativeRegistration.isTuition}</td>
                    </tr>
                    <tr>
                        <th class="th">Academic year</th>
                        <td>${item.student.administrativeRegistration.academicYear.ABBR}</td>
                    </tr>
                    <tr>
                        <th class="th">Academic stage</th>
                        <td>${item.student.administrativeRegistration.academicStage.name}</td>
                    </tr>
                </table>

                <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
        </body>
</html>