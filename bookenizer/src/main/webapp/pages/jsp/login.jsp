<%--
  Created by IntelliJ IDEA.
  User: Hroniko
  Date: 02.12.2017
  Time: 18:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8" %>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Рекомендации книг</title>

    <%@include file='headerGuest.jsp'%>

</head>
<body>


<div class="container" style="margin-top:30px">


    <div class="col-md-4 col-md-offset-4">
        <div class="col-md-12 text-center">
            <header class="page-header">
                <h1 class="page-title">Авторизация</h1>
            </header>
        </div>
        <div class="panel panel-default well" >

            <div class="panel-body ">
                <form method="post" action="/login"  class="login-form">
                    <div class="form-group">
                        <label for="username">Логин</label>
                        <input  name="username" type="text" class="form-control" style="border-radius:0px" id="username" placeholder="Enter email">
                    </div>
                    <div class="form-group">
                        <label for="password">Пароль </label>
                        <input name="password"  type="password" class="form-control" style="border-radius:0px" id="password" placeholder="Password">
                    </div>
                    <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
                    <div class="col-md-12 text-center">
                        <button type="submit" class="btn btn-sm btn-default">Войти</button>
                    </div>

                </form>
            </div>
            <div class="bottom text-center">
                Нет аккаунта? <a href="/registration"><b>Зарегистрируйтесь</b></a>
            </div>
        </div>
    </div>
</div>





<%@include file='footer.jsp'%>

</body>
</html>
