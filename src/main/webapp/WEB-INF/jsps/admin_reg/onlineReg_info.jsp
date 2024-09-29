<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/table.css">
                <title>${estm_name} - Online Registration</title>
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
                    <p id="main_title" class="g_h_clr">${item.fullName} Online. Reg. information</p>
                    <a href="${pageContext.request.contextPath}/adminReg/onlineReg/delete?id=${item.id}">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/delete_icon.png" alt="Delete icon" width="32" height="32">
                    </a>
                </header>

                <table>
                    <tr>
                        <th class="th">Massar code</th>
                        <td>${item.massar_code}</td>
                    </tr>
                    <tr>
                        <th class="th">الإسم</th>
                        <td>${item.ar_first_name}</td>
                    </tr>
                    <tr>
                        <th class="th">النسب</th>
                        <td>${item.ar_last_name}</td>
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
                        <th class="th">CIN</th>
                        <td>${item.cin}</td>
                    </tr>
                    <tr>
                        <th class="th">Nationality</th>
                        <td>${item.nationality}</td>
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
                        <th class="th">مكان الولادة</th>
                        <td>${item.ar_birth_place}</td>
                    </tr>
                    <tr>
                        <th class="th">Birth place</th>
                        <td>${item.birth_place}</td>
                    </tr>
                    <tr>
                        <th class="th">Residence town</th>
                        <td>${item.residence_town}</td>
                    </tr>
                    <tr>
                        <th class="th">Province</th>
                        <td>${item.province}</td>
                    </tr>
                    <tr>
                        <th class="th">Bac year</th>
                        <td>${item.bac_year}</td>
                    </tr>
                    <tr>
                        <th class="th">High school</th>
                        <td>${item.high_school}</td>
                    </tr>
                    <tr>
                        <th class="th">Bac place</th>
                        <td>${item.bac_place}</td>
                    </tr>
                    <tr>
                        <th class="th">Academy</th>
                        <td>${item.academy}</td>
                    </tr>
                    <tr>
                        <th class="th">Bac serie</th>
                        <td>${item.bac_serie.bac_serie}</td>
                    </tr>
                    <tr>
                        <th class="th">Bac honour</th>
                        <td>${item.bac_honour.bac_honour}</td>
                    </tr>
                    <tr>
                        <th class="th">Major</th>
                        <td>${item.major.major}</td>
                    </tr>
                    <tr>
                        <th class="th">Online Reg. date</th>
                        <td>${item.registration_date}</td>
                    </tr>
                </table>

                <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
        </body>
</html>