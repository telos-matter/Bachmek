<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
      <head>
            <meta charset="UTF-8">
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/foundation.css">
            <style>
html {
      scroll-behavior: smooth;
      height: 100%;
}

body {
      height: 100%;
}

#wlcm_div {
      display: flex;
      flex-flow: column nowrap;
      align-items: center;
      min-height: 100vh;
      max-height: 100vh;
      background-attachment: fixed;
      background-size: cover;
}

#wlcm_div > #wlcm_h {
      margin-top: 15vh;
      font-size: 48px;
      color: white;
      -webkit-text-stroke: 1.5px whitesmoke;
}

#wlcm_div > .estm_name {
      margin-top: 20vh;
      font-size: 128px;
      font-weight: bold;
      color: white;
      -webkit-text-stroke: 1.5px whitesmoke;
}

#display_div {
      display: flex;
      flex-flow: row nowrap;
      align-items: center; 
}

#display_div > .major_link {
      position: relative;
      top: -13vh;
}

#display_div > .major_link:nth-child(1) {
      left: 64px;
      animation: orbit 18s linear infinite;
}

#display_div > .major_link:nth-child(2) {
      animation: orbit 18s linear -6s  infinite;
}

#display_div > .major_link:nth-child(3) {
      right: 64px;
      animation: orbit 18s linear -12s infinite;
}

@keyframes orbit {
      0% {
            transform: rotate(0deg) translateX(355px) rotate(0deg);
      }
      12.5% {
            transform: rotate(45deg) translateX(150px) rotate(-45deg); 
      }
      25% {
            transform: rotate(90deg) translateX(100px) rotate(-90deg);
      }
      37.5% {
            transform: rotate(135deg) translateX(150px) rotate(-135deg);  
      }
      50% {
            transform: rotate(180deg) translateX(355px) rotate(-180deg);
      }
      62.5% {
            transform: rotate(225deg) translateX(150px) rotate(-225deg); 
      }
      75% {
            transform: rotate(270deg) translateX(100px) rotate(-270deg);
      }
      87.5% {
            transform: rotate(315deg) translateX(150px) rotate(-315deg); 
      }
      100% {
            transform: rotate(360deg) translateX(355px) rotate(-360deg);
      }
}

#display_BCA_logo:hover {
      content:url("${pageContext.request.contextPath}/resources/images/logos/BCA_logo.png");
}

#display_BGB_logo:hover {
      content:url("${pageContext.request.contextPath}/resources/images/logos/BGB_logo.png");
}

#display_BGI_logo:hover {
      content:url("${pageContext.request.contextPath}/resources/images/logos/BGI_logo.png");
}

#scroll {
      position: relative;
      margin-top: auto;
      transform: rotate(90deg);
      font-size: 64px;
      color: white;
      -webkit-text-stroke: 1px whitesmoke;
      animation: levitate 2.5s linear infinite; 
}

#scroll:hover {
      cursor: pointer;
}

@keyframes levitate {
      0% {
            top: 0;
      }
      50% {
            top: -3vh;
      }
      100% {
            top: 0;
      }
}

#info {
      display: flex;
      flex-flow: row nowrap;
      align-items: flex-start; 
      justify-content: space-evenly;      
      min-height: 100vh;
      max-height: 100vh;
}

.major_div {
      display: flex;
      flex-flow: column nowrap;
      align-items: center;
      height: 100%;  
}

.major_div > .major_logo {
      margin-top: 3vh;
      margin-bottom: 2vh;
}

.major_text {
      font-size: x-large;
      border-radius: 14px;
      padding: 8px;
      height: inherit;
      width: 20vw;
      padding: 18px;
      margin-bottom: 6vh;
}

.major_text:hover {
      transform: scale(1.05);
}
            </style>
            <title>${estm_name} - Home</title>
      </head>
      <body>
            <div id="wlcm_div" style="background-image: url('${pageContext.request.contextPath}/resources/images/backgrounds/night_sky.png');">
                  <span id="wlcm_h">Welcome to</span>
                  <br>
                  <a class="estm_name" href="${pageContext.request.contextPath}/account">${estm_name}</a>
                  <div id="display_div">
                        <a class="major_link" href="${pageContext.request.contextPath}/home/BCA">
                              <img id="display_BCA_logo" class="major_logo" src="${pageContext.request.contextPath}/resources/images/logos/BCA_logo_dark.png" alt="BCA logo" width="64" height="64">
                        </a>
                        <a class="major_link" href="${pageContext.request.contextPath}/home/BGB">
                              <img id="display_BGB_logo" class="major_logo" src="${pageContext.request.contextPath}/resources/images/logos/BGB_logo_dark.png" alt="BGB logo" width="64" height="64">
                        </a>
                        <a class="major_link" href="${pageContext.request.contextPath}/home/BGI">
                              <img id="display_BGI_logo" class="major_logo" src="${pageContext.request.contextPath}/resources/images/logos/BGI_logo_dark.png" alt="BGI logo" width="64" height="64">
                        </a>
                  </div>
                  <a id="scroll" href="#info">&#10095;</a>
            </div>
            <div id="info" class="g_marg_as_padd">
                  <div class="major_div">
                        <img class="major_logo" src="${pageContext.request.contextPath}/resources/images/logos/BCA_logo_text.png" alt="BCA logo" width="128" height="128"> 
                        <a class="major_text g_bg_clr g_border" href="${pageContext.request.contextPath}/home/apply?major=BCA">The applied chemistry training provided aims to train graduates who master the fundamental bases of chemistry as well as aspects of synthesis and development as well as aspects of analysis.<br><br>Apply to BCA</a>
                  </div>
                  <div class="major_div">
                        <img class="major_logo" src="${pageContext.request.contextPath}/resources/images/logos/BGB_logo_text.png" alt="BGB logo" width="128" height="128"> 
                        <a class="major_text g_bg_clr g_border" href="${pageContext.request.contextPath}/home/apply?major=BGB">Biomedical engineering is an application of the principles and techniques of engineering in the medical field aimed at the control of biological systems or the development of useful devices.<br><br>Apply to BGB</a>
                  </div>
                  <div class="major_div">
                        <img class="major_logo" src="${pageContext.request.contextPath}/resources/images/logos/BGI_logo_text.png" alt="BGI logo" width="128" height="128">
                        <a class="major_text g_bg_clr g_border" href="${pageContext.request.contextPath}/home/apply?major=BGI">This Bachelor in Computer Engineering training will allow students to acquire solid skills in mathematics, computer science and engineering, both theoretically and practically.<br><br>Apply to BGI</a>
                  </div>
            </div>
      </body>
</html>