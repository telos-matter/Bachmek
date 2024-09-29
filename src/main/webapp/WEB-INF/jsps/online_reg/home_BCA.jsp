<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/foundation.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/ul.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/header.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/plain_panel.css">
        <style>
p {
    line-height: 1.3em;
    margin-left: 1em;    
}

li {
    line-height: 1.3em;      
}

li:before {
    margin-left: 1em;
}

.sub_li {
    margin-left: 1.5em;
}

#training {
    margin-left: 10vw;
    margin-top: 2vh;
}

#apply {
    margin-left: 40%;
    margin-bottom: 1vh;
    font-size: xx-large;
    width: fit-content;
}
        </style>
        <title>${estm_name} - BCA</title>
    </head>
    <body>
        <%@ include file= "/resources/includes/header.jsp" %>
        <div id="plain_panel">
            <h2 class="g_h_clr">A first in Morocco</h2>
            <br>
            <p>Do you want to continue your studies in applied chemistry?</p>
            <p>Do you want to start your studies in Morocco and finish them in Germany?</p>
            <p>The Bachelor of Science in Applied Chemistry could well be your path!</p>
            <br>
            <br>
            <h2 class="g_h_clr">Training presentation</h2>
            <br>
            <ul>
                <li>The Training will be offered jointly by Moulay Ismail University (UMI) and the University of Aachen in Germany (FH).</li>
                <li>Students will be registered jointly at the UMI and the FH.</li>
                <li>The training lasts 3 years (6 semesters), the first 4 semesters will be taken at the UMI and the other 2 semesters will take place at the FH.</li>
                <li>The 4th semester is provided by the German teaching staff, either in direct contact with the students or via videoconference.</li>
                <li>Students will be treated in the same way as those enrolled at the FH.</li>
                <li>Merit scholarships can be awarded to students during their stay in Germany.</li>
                <li>The end-of-study internships will take place in the laboratories of the FH.</li>
                <li>The training will be sanctioned by a double diploma: from the Moulay Ismail University and the University of Aachen.</li>
                <li>Possibilities of Continuation of free studies in Germany: Master, PhD.</li>
                <li>The training is paid: 40000 DHS per year for the first 4 semesters in Morocco and 7200 Euros for the two semesters in Germany payable in two.</li>
            </ul>
            <br>
            <br>
            <h2 class="g_h_clr">Outcomes of training</h2>
            <br>
            <p>The future winners will be able to integrate both the public and private sectors in various <br>and varied fields such as the chemical industry, plastics, packaging, textiles, mattresses, plastics, glues, paint...</p>
            <img id="training" src="${pageContext.request.contextPath}/resources/images/trainings/BCA_training.png" alt="BCA training" width="511" height="789">
            <br>
            <br>
            <br>
            <a id="apply" class="g_h_clr" href="${pageContext.request.contextPath}/home/apply?major=BCA">! apply to BCA now !</a>
        </div>
        <div id="panel_footer"></div>
    </body>
</html>