<%--
  Created by IntelliJ IDEA.
  User: hroniko
  Date: 02.12.17
  Time: 15:01
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
    <title>Мой профиль</title>


    <meta charset="UTF-8">

    <%@include file='header.jsp'%>

    <style>
        <%@include file="/pages/static/css/bootstrap.min.css"%>
        <%@include file="/pages/static/css/bootstrap-select.min.css"%>
        <%@include file="/pages/static/css/bootstrap-datetimepicker.min.css"%>
        <%@include file="/pages/static/css/tipped.css"%>
        <%@include file="/pages/static/css/vis.min.css"%>
        <%@include file="/pages/static/css/tlmain.css"%>
        <%@include file="/pages/static/css/jquery.mCustomScrollbar.min.css"%>
        <%@include file="/pages/static/css/style-modal.css"%>
    </style>



    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

    <script type="text/javascript">
        <%@include file="/pages/static/js/moment-with-locales.min.js"%>
        <%@include file="/pages/static/js/tipped.js"%>
        <%@include file="/pages/static/js/bootstrap.min.js"%>
        <%@include file="/pages/static/js/bootstrap-datetimepicker.min.js"%>
        <%@include file="/pages/static/js/bootstrap-select.min.js"%>
        <%@include file="/pages/static/js/jquery.mCustomScrollbar.concat.min.js"%>
        <%@include file="/pages/static/js/validator.min.js"%>
    </script>

    <script type="text/javascript">
        var d = document;

        var name;
        var initials;
        var posada;

        function addRow()
        {

            // Считываем значения с формы
            name = d.getElementById('nameBook').value;
            // d.getElementById('nameBook').value = "";

            author = d.getElementById('authorBook').value;
            // d.getElementById('authorBook').value = "";


            doAjaxSendMessage();


            // Находим нужную таблицу
            var tbody = d.getElementById('tab1').getElementsByTagName('TBODY')[0];

            // Создаем строку таблицы и добавляем ее
            var row = d.createElement("TR");
            tbody.appendChild(row);

            // Создаем ячейки в вышесозданной строке
            // и добавляем тх
            var td1 = d.createElement("TD");
            var td2 = d.createElement("TD");
            var td3 = d.createElement("TD");

            row.appendChild(td1);
            row.appendChild(td2);
            row.appendChild(td3);

            // Наполняем ячейки
            td1.innerHTML = '"' + name + '"';
            td2.innerHTML = author;
            td3.innerHTML = "<a href=\"#win1\"  role=\"button\"   class='btn btn-info btn-xs pull-right' > <span class=\"glyphicon glyphicon-glyphicon glyphicon-remove\"></span> Удалить</a>";
        }

        function doAjaxSendMessage() {

            name = d.getElementById('nameBook').value;
            d.getElementById('nameBook').value = "";

            author = d.getElementById('authorBook').value;
            d.getElementById('authorBook').value = "";



            $.ajax({
                url: '${pageContext.request.contextPath}/sendBook',
                method: 'POST',
                traditional: true,
                data: {
                    id: '${user.getId()}',
                    name: name,
                    author: author
                },
                success: function(data) {
                    if (data == "FAIL") {
                        alert("File not found!");
                    }
                },
                error: function(request, status, error) {
                    alert("The request failed: " + request.responseText);
                }
            });
        }

    </script>



</head>
<body>


<div class="resume">
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-offset-1 col-md-10 col-lg-offset-2 col-lg-8">
            <div class="col-md-12 text-center">
                <header class="page-header">
                    <h1 class="page-title">Мой профиль</h1>
                </header>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading resume-heading">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="col-xs-12 col-sm-4">

                                <figure>
                                    <img class="img-circle img-responsive center-block" alt="" src="/img/avatar.jpg">
                                </figure>

                            </div>

                            <div class="col-xs-12 col-sm-8">
                                <ul class="list-group">
                                    <li class="list-group-item">Фамилия: ${user.getFirstName()}</li>
                                    <li class="list-group-item">Имя: ${user.getLastName()} </li>
                                    <li class="list-group-item">Логин: ${user.getLogin()} </li>
                                    <li class="list-group-item">Возраст: ${user.getAge()} </li>
                                    <li class="list-group-item">Почта: ${user.getMail()}</li>
                                </ul>
                            </div>
                        </div>
                        <div class="container">
                            <div class="row col-md-6 col-md-offset-2 custyle">
                                <h3>Мои книги:</h3>
                                    <table id="tab1" class="table table-striped custab">
                                        <tr>
                                            <td>Название:</td>
                                            <td>Автор:</td>

                                            <td><a href="#"  role="button"   class="btn btn-primary btn-xs pull-right" data-toggle="modal" data-target="#myModal" > <span class="glyphicon glyphicon-plus"></span> Добавить</a></td>

                                        </tr>
                                        <c:forEach var="book" items="${books}">
                                            <tr>
                                                <td>"${book.name}"</td>
                                                <td>${book.author}</td>
                                                <td><a href="#win1"  role="button"   class='btn btn-info btn-xs pull-right' > <span class="glyphicon glyphicon-glyphicon glyphicon-remove"></span> Удалить</a></td>

                                            </tr>
                                        </c:forEach>

                                    </table>

                                <!-- Модальное окно 1 -->
                                <a href="#x" class="overlay" id="win1"></a>
                                <div class="popup">
                                    <div class="pic-left">
                                        <a href="/"><img src="/img/prog.jpeg" alt="Это будет фича" /></a>
                                    </div>
                                    <h2>Уважаемые друзья!</h2>
                                    <p>Мы очень старались все успеть, но  <strong>в силу непредвиденных обстоятельств</strong> данная возможность пока не реализована.
                                    <h3>Не расстаривайтесь!</h3>
                                    <p>Мы обязательно реализуем функционал удаления книг в следующих версиях нашего продукта!
                                    <h3>        Спасибо за понимание!</h3>
                                    <p>         С уважением, команда разработчиков
                                    <a class="close" title="Закрыть" href="#close"></a>
                                </div>


                            </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</div>
</div>





</body>
<div style="margin-bottom: 8rem;"/>
<%@include file='footer.jsp'%>
</html>