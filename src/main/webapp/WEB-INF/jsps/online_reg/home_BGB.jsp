<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/foundation.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/ul.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/header.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/plain_panel.css">
        <title>${estm_name} - BGB</title>
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
    </head>
    <body>
        <%@ include file= "/resources/includes/header.jsp" %>
        <div id="plain_panel">
            <h2 class="g_h_clr">A first in Morocco</h2>
            <br>
            <p>Do you want to devote yourself to medical progress and participate in the well-being of society?</p>
            <p>Does biology fascinate you?</p>
            <p>Are you passionate about science and technology?</p>
            <p>Biomedical engineering could well be your path!</p>
            <br>
            <br>
            <h2 class="g_h_clr">What is Biomedical Engineering?</h2>
            <br>
            <p>A multidisciplinary branch of engineering, biomedical engineering is concerned with the functioning of the human body as well as the design, manufacture and maintenance of devices for the diagnosis or treatment of diseases. Biomedical engineering brings together many specialties, from tissue engineering to orthopedic engineering, including bio-instrumentation and imaging. The training of the biomedical engineer draws on health sciences, engineering sciences, basic sciences and mathematics.</p>
            <br>
            <br>
            <h2 class="g_h_clr">Why choose biomedical engineering?</h2>
            <br>
            <p>Biomedical engineering has grown and become a key technology worldwide. It is based on the fundamental principles of engineering sciences and combines medical, technical and scientific expertise. Only through interdisciplinary collaboration will scientists be able to develop tailor-made solutions for the problems arising from the increasing mechanization of medicine.</p>
            <br>
            <br>
            <h2 class="g_h_clr">Training aims</h2>
            <br>
            <p>Biomedical engineering is a multidisciplinary branch of engineering, it is interested in the functioning of the human body as well as the design, manufacture and maintenance of devices for the diagnosis or treatment of diseases. Biomedical engineering brings together many specialties, from tissue engineering to orthopedic engineering, including bio-instrumentation and imaging.</p>
            <p>The objective of this training is to meet a real need for skills in the biomedical field.</p>
            <br>
            <br>
            <h2 class="g_h_clr">Training presentation</h2>
            <br>
            <ul>
                <li>This training is offered jointly by Moulay Ismaïl University (UMI) and Aachen University of Applied Sciences (ACUAS).</li>
                <li>The training lasts 4 years or 8 semesters. The first 5 semesters at UMI and the remaining 3 at ACUAS.</li>
                <li>From the 4th semester, students will have the opportunity to follow courses provided by the German teaching staff, either by videoconference or face-to-face at UMI.</li>
                <li>The end-of-study internships will take place in Germany.</li>
                <li>The grads will benefit from:</li>
                <li class="sub_li">a university degree from UMI,</li>
                <li class="sub_li">a German diploma from ACUAS.</li>
                <li>Grads will have the opportunity to enroll in Masters and Ph.D. with the same privileges as German students.</li>
                <li>Scholarships could be awarded to deserving students.</li>
                <li>The training is paid, it costs:</li>
                <li class="sub_li">50,000 DH for the first year in Morocco,</li>
                <li class="sub_li">50,000 DH for the second year in Morocco,</li>
                <li class="sub_li">and €8,200 for further training in Germany.</li>
            </ul>
            <br>
            <br>
            <h2 class="g_h_clr">Outcomes of training</h2>
            <br>
            <p>Biomedical engineers are found in companies dedicated to the development of biomedical equipment, hospitals, consulting engineering firms, research centers of university hospitals, government agencies and universities.</p>
            <img id="training" src="${pageContext.request.contextPath}/resources/images/trainings/BGB_training.png" alt="BGB training" width="620" height="975">
            <br>
            <br>
            <br>
            <a id="apply" class="g_h_clr" href="${pageContext.request.contextPath}/home/apply?major=BGB">! apply to BGB now !</a>
        </div>
        <div id="panel_footer"></div>
    </body>
</html>