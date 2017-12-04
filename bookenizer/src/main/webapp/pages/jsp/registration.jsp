<%--
  Created by IntelliJ IDEA.
  User: hroniko
  Date: 04.12.17
  Time: 3:01
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
    <title>Регистрация</title>

    <%@include file='headerGuest.jsp'%>

</head>
<body>


<div class="container" style="margin-top:30px">

    <div class=" well">
        <div class="col-md-12 text-center">
            <header class="page-header">
                <h1 class="page-title">Регистрация</h1>
            </header>
        </div>

        <div class="row">
            <form method="post" action="/registration_sumbit" class="login" >
                <div class="col-xs-12 col-sm-12 col-md-offset-1 col-md-10 col-lg-offset-2 col-lg-8">
                    <div class="row">
                        <div class="col-sm-6 form-group">
                            <label>Логин</label>
                            <input name="login" type="text" placeholder="Логин..." class="form-control">
                        </div>
                        <div class="col-sm-6 form-group">
                            <label>Пароль</label>
                            <input name="password" type="text" placeholder="Пароль..." class="form-control">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 form-group">
                            <label>Фамилия</label>
                            <input name="firstName" type="text" placeholder="Фамилия.." class="form-control">
                        </div>
                        <div class="col-sm-6 form-group">
                            <label>Имя</label>
                            <input name="lastName" type="text" placeholder="Имя.." class="form-control">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 form-group">
                            <label>Возраст</label>
                            <input  name="age" type="text" placeholder="Возраст.." class="form-control">
                        </div>
                        <div class="col-sm-6 form-group">
                            <label>Email </label>
                            <input name="email" type="text" placeholder="Почта.." class="form-control">
                        </div>

                    </div>

                    <div class="form-group">
                        <label>Страна</label>
                        <select name="country" class="form-control">
                            <option>Россия</option>
                            <option>США</option>
                            <option>Германия</option>
                            <option>Франция</option>
                        </select>
                    </div>
                    <div class="col-md-12 text-center">
                        <button type="submit" class="btn btn-sm btn-info">Отправить</button>
                    </div>

                </div>
            </form>
        </div>
    </div>
</div>





<%@include file='footer.jsp'%>

</body>
</html>

