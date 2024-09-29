<%@ include file= "/resources/includes/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="main_container_div" class="g_marg">
        <div id="main_side_panel">
                <div id="main_side_panel_icon_label">
                        <c:if test="${role.equals('STUDENT')}">
                                <img id="main_side_panel_icon" src="${pageContext.request.contextPath}/resources/images/icons/student_icon.png" alt="Student" width="44" height="44">
                        </c:if>
                        <c:if test="${role.equals('INSTRUCTOR')}">
                                <img id="main_side_panel_icon" src="${pageContext.request.contextPath}/resources/images/icons/instructor_icon.png" alt="Instructor" width="44" height="44">
                        </c:if>
                        <c:if test="${role.equals('ADMINISTRATOR')}">
                                <img id="main_side_panel_icon" src="${pageContext.request.contextPath}/resources/images/icons/administrator_icon.png" alt="Administrator" width="44" height="44">
                        </c:if>
                        <label id="main_side_panel_label">
                                <input id="main_side_panel_toggle" type="checkbox" onclick="clicked()" checked>
                                <div class="main_side_panel_bars main_side_panel_collapse"></div>
                                <div class="main_side_panel_bars main_side_panel_collapse"></div>
                                <div class="main_side_panel_bars main_side_panel_collapse"></div>
                                <div id="main_side_panel_cross_1" class="main_side_panel_cross main_side_panel_show_cross_1"></div>
                                <div id="main_side_panel_cross_2" class="main_side_panel_cross main_side_panel_show_cross_2"></div>
                        </label>
                </div>
                <c:forEach var="menuLink" items="${menuLinks}">
                        <br class="main_side_panel_br">
                        <c:choose>
                                <c:when test="${menuLink.selected}">
                                        <a class="main_side_panel_menuItem main_side_panel_menuItem_selected" href="${menuLink.link}">${menuLink.link_name}</a>
                                </c:when>
                                <c:otherwise>
                                        <a class="main_side_panel_menuItem" href="${menuLink.link}">${menuLink.link_name}</a>
                                </c:otherwise>
                        </c:choose>
                </c:forEach>
        </div>
        <div id="main_main_panel">