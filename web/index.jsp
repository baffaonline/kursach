<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
    <title>Ведомость</title>
</head>
<body>
<div id="page-body">
    <c:import url="${pageContext.request.contextPath}/jsp/parts/header.jsp">
        <c:param name="user" value="${user}"/>
    </c:import>
    <div class="indexContent">
        <div class="container">
            <c:choose>
                <c:when test="${user.type.typeName == 'guest'}">
                    <h1>Войдите для того, чтобы получить доступ к ведомости</h1>

                    <form class="loginForm" action="${pageContext.request.contextPath}/MainController"
                          method="get">
                        <input type="hidden" name="command" value="prepare_login">
                        <button type="submit" class="btn btn-primary">Войти</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <h1>Приветствуем, ${role.name} ${role.fathername}</h1>

                    <form class="loginForm" action="${pageContext.request.contextPath}/MainController"
                          method="get">
                        <input type="hidden" name="command" value="subjects">
                        <button type="submit" class="btn btn-primary">Зайти в ведомость</button>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div>
        <p>${error}</p>
    </div>
</div>
<c:import url="${pageContext.request.contextPath}/jsp/parts/footer.jsp"/>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>