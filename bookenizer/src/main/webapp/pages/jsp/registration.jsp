<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<<div class="container">
<header class="page-header">
    <h1 class="page-title">Registration</h1>
</header>
<div class=" well">
    <div class="row">
        <form method="post" action="/registration_sumbit" class="login" >
            <div class="col-xs-12 col-sm-12 col-md-offset-1 col-md-10 col-lg-offset-2 col-lg-8">
                <div class="row">
                <div class="col-sm-6 form-group">
                    <label>Login</label>
                    <input name="login" type="text" placeholder="Login..." class="form-control">
                </div>
                <div class="col-sm-6 form-group">
                    <label>Passwor</label>
                    <input name="password" type="text" placeholder="Password..." class="form-control">
                </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 form-group">
                        <label>First Name</label>
                        <input name="firstName" type="text" placeholder="Enter First Name Here.." class="form-control">
                    </div>
                    <div class="col-sm-6 form-group">
                        <label>Last Name</label>
                        <input name="lastName" type="text" placeholder="Enter Last Name Here.." class="form-control">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 form-group">
                        <label>Age</label>
                        <input  name="age" type="text" placeholder="Enter City Name Here.." class="form-control">
                    </div>
                    <div class="col-sm-6 form-group">
                        <label>Email </label>
                        <input name="email" type="text" placeholder="Enter Email Address Here.." class="form-control">
                    </div>

                </div>

                <div class="form-group">
                    <label>Country</label>
                    <select name="country" class="form-control">
                        <option>Russian Federation</option>
                        <option>USA</option>
                        <option>Germany</option>
                        <option>France</option>
                    </select>
                </div>

                <button type="submit"  class="btn btn-lg btn-info">Submit</button>
            </div>
        </form>
    </div>
</div>
</div>