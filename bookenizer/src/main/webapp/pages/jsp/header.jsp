<%--
  Created by IntelliJ IDEA.
  User: hroniko
  Date: 02.12.17
  Time: 14:34
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.sql.SQLException" %>
<%@ page import="java.lang.reflect.InvocationTargetException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8" %>
<!DOCTYPE html>
<html>
<head>







    <style>
        <%@include file="/pages/static/font-awesome/css/font-awesome.css"%>
        <%@include file="/pages/static/css/skywalk-docs.min.css"%>
        <%@include file="/pages/static/css/bootstrap.min.css"%>
        <%@include file="/pages/static/css/bootstrap-notifications.min.css"%>
        <%@include file="/pages/static/css/footer.css"%>
        <%@include file="/pages/static/css/select2.min.css"%>
        <%@include file="/pages/static/css/tlmain.css"%>
        <%@include file="/pages/static/css/search.css"%>
    </style>

    <link rel="stylesheet" href="/font-awesome/css/font-awesome.css" rel="stylesheet">


    <script type="text/javascript">
        <%@include file="/pages/static/js/bootstrap.min.js"%>
        <%@include file="/pages/static/js/docs.min.js"%>
        <%@include file="/pages/static/js/header.js"%>
        <%@include file="/pages/static/js/select2.min.js"%>
        <%@include file="/pages/static/js/ru.js"%>
    </script>

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>





</head>

<body>
<nav class="navbar navbar-default" role="navigation" style="border-radius: 0px 0px 0px 0px;">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Навигация</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">Профиль</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="dropdown dropdown-notifications" id="notificationDrop">

                <li><a href="/recomendations">Рекомендации</a></li>

            </ul>





            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><b><span class="glyphicon glyphicon-user"></span> <sec:authentication property="principal.username"/></b> <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="/">Профиль</a></li>
                        <li class="divider"></li>
                        <li><a href="/recomendations" data-toggle="modal">Рекомендации</a></li>
                        <li class="divider"></li>
                        <li><a href="/logout">Выход</a></li>
                    </ul>
                </li>
            </ul>

        </div>
    </div>
</nav>

<div class="modal fade" id="myModal">
    <div class="modal-dialog">
        <!--  <div class="modal-content"> -->
        <div class=".col-xs-6 .col-md-4">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="text-center">
                        <h2 class="text-center">Новая книга</h2>
                        <p>Добавьте новую книгу в свой профиль</p>
                        <div class="panel-body">

                            <!--  <form class="form" id="add_persons" method="post" action="/addBook"onsubmit="addRow();return false;"> -->
                            <form class="form" id="add_persons" method="post" action=""onsubmit="addRow();return false;">

                                <fieldset>


                                    <div class="form-group">
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="glyphicon glyphicon-book color-blue"></i></span>
                                            <input name="nameBook" id="nameBook" placeholder="Название книги" class="form-control" type="text" >
                                        </div>
                                    </div>


                                    <div class="form-group">
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="glyphicon glyphicon-user color-blue"></i></span>
                                            <input id="authorBook" name="authorBook" placeholder="Автор книги" class="form-control" type="text" >
                                        </div>
                                    </div>




                                    <div class="form-group">
                                        <input class="btn btn-lg btn-primary btn-block" id="addBook" value="Добавить" type="submit" > <!-- data-dismiss="modal" -->
                                    </div>
                                </fieldset>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>



</body>

</html>