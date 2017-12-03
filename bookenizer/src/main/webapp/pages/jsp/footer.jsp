<%--
  Created by IntelliJ IDEA.
  User: hroniko
  Date: 02.12.17
  Time: 14:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8" %>

<footer class="footer">
    <div class="container">
        <div class="row">
            <div id="left" class="col-sm-4">

                <h5><br>AT Consulting</h5>
            </div>
            <div id="center" class="col-sm-4">
                <h5><br>© "Внешний круг". Курс BigData. <%= new java.text.SimpleDateFormat("dd.MM.yyyy").format( new java.util.Date()) %></h5>
            </div>
            <div class="col-sm-4">
                <div class="row">
                    <br>
                </div>
                <div id="right" class="row">
                    <div class="col-md-12 hidden-xs text-right">

                    </div>
                </div>
            </div>
        </div>
    </div>
</footer>
