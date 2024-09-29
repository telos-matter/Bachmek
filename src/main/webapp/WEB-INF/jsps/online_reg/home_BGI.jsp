<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/foundation.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/ul.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/header.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/plain_panel.css">
        <title>${estm_name} - BGI</title>
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
            <p>Do you want to continue your studies in engineering?</p>
            <p>Do you want to combine between mathematics and computer science?</p>
            <p>Do you want to start your studies in Morocco and finish them in Germany?</p>
            <p>The Bachelor in Computer Engineering could well be your path!</p>
            <br>
            <br>
            <h2 class="g_h_clr">What is the Bachelor in Computer Engineering?</h2>
            <br>
            <p>This "Bachelor in Computer Engineering" training will allow students to acquire solid skills in mathematics, computer science and engineering, both theoretically and practically. The students will follow a good part of the theory in Morocco and will complete their theoretical training as well as the practical part in Germany. The practical part will take place in the form of internships in large German companies.</p>
            <p>The program of this training (mathematics and computer science) is developed so that the winners will be able to solve complex problems both at the level of industry and at the level of research and then implement them in the form of software.</p>
            <p>Given their theoretical and practical training, the winners will have skills that will allow them to easily integrate the business environment and can also continue their higher education, namely a master's and a doctorate.</p>
            <br>
            <br>
            <h2 class="g_h_clr">Training presentation</h2>
            <br>
            <ul>
                <li>This training is offered jointly by Moulay Ismaïl University (UMI) and Aachen University of Applied Sciences (ACUAS).</li>
                <li>The training lasts 4 years or 8 semesters. The first 5 semesters at UMI and the remaining 3 at ACUAS.</li>
                <li>From the 5th semester, students will have the opportunity to follow courses provided by the German teaching staff, either by videoconference or face-to-face at UMI.</li>
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
            <p>Graduates in computer engineering can be found in companies operating in the field of software development, university research centers, government agencies.</p>
            <img id="training" src="${pageContext.request.contextPath}/resources/images/trainings/BGI_training.png" alt="BGI training" width="563" height="736">
            <br>
            <br>
            <br>
            <a id="apply" class="g_h_clr" href="${pageContext.request.contextPath}/home/apply?major=BGI">! apply to BGI now !</a>
        </div>
        <div id="panel_footer"></div>
    </body>
</html>