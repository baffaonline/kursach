<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/fontawesome-free-5.0.6/web-fonts-with-css/css/fontawesome.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
    <title>Ведомость</title>
</head>
<body>
<div id="page-body">
    <c:import url="${pageContext.request.contextPath}/jsp/parts/header.jsp">
        <c:param name="user" value="${user}"/>
    </c:import>

    <div class="text-white">
        <h2 id="header"><c:choose>
            <c:when test="${user.type.typeName.equals('student')}">${role.group} группа ${role.course} курс</c:when>
            <c:otherwise>${group} группа ${course} курс</c:otherwise>
        </c:choose><br/>
            Предмет : ${subject.name}<br/>
            Преподаватель: ${subject.teacher.surname} ${subject.teacher.name} ${subject.teacher.fathername}</h2>
        <div class="text-white">
            <c:choose>
                <c:when test="${user.type.typeName.equals('student')}">
                    <table class="table table-bordered" id="white-table">
                        <thead>
                        <tr>
                            <th scope="col">Студент</th>
                            <c:forEach items="${dates}" var="date">
                                <th scope="col">${date.dayOfMonth}.${date.month.value}.${date.year}</th>
                            </c:forEach>
                            <th scope="col"></th>
                            <th scope="col"></th>
                            <th scope="col"></th>
                            <th scope="col"></th>
                            <th scope="col"></th>
                            <th scope="col"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="elem" items="${students}">
                            <tr>
                                <td>${elem.surname} ${elem.name} ${elem.fathername}</td>
                                <c:forEach items="${dates}" var="markDate">
                                    <td>
                                        <c:forEach items="${elem.marks}" var="mark">
                                            <c:if test="${mark.date.equals(markDate)}">
                                                <c:out value="${mark.mark}"/>
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                </c:forEach>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <table class="table table-bordered" id="white-table">
                        <thead>
                        <tr>
                            <th scope="col">Студент</th>
                            <c:forEach items="${dates}" var="date">
                                <th scope="col">${date.dayOfMonth}.${date.month.value}.${date.year}</th>
                            </c:forEach>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="elem" items="${students}">
                            <tr>
                                <td>${elem.surname} ${elem.name} ${elem.fathername}</td>
                                <c:forEach items="${dates}" var="markDate">
                                    <td>
                                        <c:forEach items="${elem.marks}" var="mark">
                                            <c:if test="${mark.date.equals(markDate)}">
                                                <c:out value="${mark.mark}"/>
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div>
                        <form method="get" action="${pageContext.request.contextPath}/">
                            <button class="btn btn-danger" type="submit">Выйти</button>
                        </form>
                        <form method="get" action="${pageContext.request.contextPath}/MainController">
                            <input type="hidden" name="command" value="before_add_mark">
                            <button class="btn btn-primary" type="submit">Добавить отметку</button>
                        </form>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
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