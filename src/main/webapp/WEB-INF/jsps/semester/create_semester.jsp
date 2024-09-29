<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="UTF-8">
                <%@ include file= "/resources/includes/main_panel_includes.jsp" %>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/horizontal_form.css">
                <title>${estm_name} - Create Semester</title>
                <script defer>
                    const MAX_MODULE = parseInt('${max_module}')
                    function countSelected (modules) {
                        count = 0
                        for (var option of modules.options) {
                            if (option.selected) {
                                count++
                            }
                        }
                        return count;
                    }
                    function removeSelect (modules) {
                        for (var option of modules.options) {
                            if (option.selected) {
                                option.selected = ''
                                break
                            }
                        }
                    }
                    function moduleCheck () {
                        modules = document.getElementById('modules_ids')
                        if (countSelected (modules) > MAX_MODULE) {
                            removeSelect(modules)
                            alert("You can only select a maximum of " +MAX_MODULE +" Modules!")
                        }
                    }
                </script>
        </head>
        <body>
                <%@ include file= "/resources/includes/main_panel_header.jsp" %>

                <p id="form_statement">Create a new Semester for the ${acadYear.ABBR} Acad. Year</p>
                <form id="form" action="${pageContext.request.contextPath}/semester/create?acadYear_id=${acadYear.id}" method="post">
                    <div class="input_form">
                        <label for="acadSem_id">Academic Semester</label>
                        <select id="acadSem_id" name="acadSem_id" required onchange="window.location.href = '${pageContext.request.contextPath}/semester/create?acadYear_id=${acadYear.id}&acadSem_id=' +document.getElementById('acadSem_id').value">
                            <c:choose>
                                <c:when test="${acadSem == null}">
                                    <c:forEach var="list_item" items="${acadSems_list}">
                                        <option value="${list_item.id}">${list_item.name}</option>
                                    </c:forEach> 
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="list_item" items="${acadSems_list}">
                                        <c:choose>
                                            <c:when test="${list_item.id == acadSem.id}">
                                                <option value="${list_item.id}" selected>${list_item.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${list_item.id}">${list_item.name}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>  
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </div>
                    <div class="input_form">
                        <label for="modules_ids">Modules</label>
                        <select id="modules_ids" name="modules_ids" required multiple size="${max_module +2}" title="Select a maximum of ${max_module} Modules. You can select multiple by pressing down either Ctrl (for Windows) or Command (for MacOs) and clicking" onchange="moduleCheck()">
                            <c:forEach var="list_item" items="${modules_list}">
                                <c:choose>
                                    <c:when test="${list_item.selected}">
                                        <option value="${list_item.module.id}"  style="margin-right: 1.2vw;" selected>${list_item.module.name}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${list_item.module.id}"  style="margin-right: 1.2vw;">${list_item.module.name}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>  
                        </select>
                    </div>

                </form>
                <input id="submit_button" form="form" type="submit" value="Create">

                <%@ include file= "/resources/includes/main_panel_footer.jsp" %>
        </body>
</html>