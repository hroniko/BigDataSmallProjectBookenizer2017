<%--
  Created by IntelliJ IDEA.
  User: hroniko
  Date: 02.12.17
  Time: 17:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8" %>
<html lang="en">
<head>
    <title>Книги</title>


    <style>
        <%@include file="/pages/static/css/bootstrap.min.css"%>
        <%@include file="/pages/static/font-awesome/css/font-awesome.css"%>
        <%@include file="/pages/static/css/header.css"%>
        <%@include file="/pages/static/css/tlmain.css"%>
        <%@include file="/pages/static/css/footer.css"%>
    </style>

    <script type="text/javascript">
        <%@include file="/pages/static/js/bootstrap.min.js"%>
    </script>




</head>

<body>

<nav class="navbar navbar-default" role="navigation" style="border-radius: 0px;">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Навигация</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">Главная</a>
        </div>


        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="/registration">Регистрация</a></li>
            </ul>

        </div>
    </div>

</nav>



</body>
</html>

