<%@ page import="java.io.*,java.lang.*,java.util.*,java.net.*,java.util.*,java.text.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="javax.activation.*"%>

<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/index.css">
    <link rel="shortcut icon" href="N.png" type="image/x-icon" />
    <title>NikaTasks</title>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
</head>
<body>
<div class="container-fluid">
    <div class="container-fluid" id="header">
        <h1>NikaTasks</h1>
        <br>
        <h6>Ajude a sua empresa a ir para frente.</h6>
    </div>

    <script type="text/javascript">
        $(document).ready(function () {
            $("#header").click(function () {
                console.log("Hello World!");
                $.ajax({
                    type: 'post',
                    url: '/create-account',
                    data: "username="+"1234"+"&email=" + 'teste@teste.com' + "&password="+'1234' + "&confirmpassword="+'1234',
                    success: function (msg) {
                        console.log("Success", msg);
                        $("body").html(msg);
                    }
                })
            })
        })
    </script>

    <div class="container-xxl col-9">
        <form action="create-account" id="left" method="post">
            <br>
            <h5>Cadastro</h5>
            <div class="form-group col-8" id="login-form">
                <label for="username">Usuario:</label>
                <input class="form-control" name="username" placeholder="Digite seu nome de usuario" type="text">
            </div>
            <div class="form-group col-8" id="login-form">
                <label for="email">Email:</label>
                <input class="form-control" name="email" placeholder="Digite seu e-mail" type="email">
            </div>
            <br>
            <div class="row" id="login-form">
                <div class="col">
                    <label for="password">Senha:</label>
                    <input class="form-control" name="password" placeholder="Digite sua senha" type="password">
                </div>
                <div class="col">
                    <label for="password">Senha:</label>
                    <input class="form-control" name="confirmpassword" placeholder="Digite a senha novamente" type="password">
                </div>
            </div>
            <br>
            <br>
            <input name="" type="submit" value="Cadastrar">
            <br>
            <c:if test="${ not empty requestScope.error }">
                <div class="alert alert-danger" role="alert">
                        ${requestScope.error}
                </div>
            </c:if>
            <c:if test="${ not empty requestScope.success }">
                <div class="alert alert-success" role="alert">
                        ${requestScope.success}
                </div>
            </c:if>
        </form>
    </div>

</div>
</body>
</html>