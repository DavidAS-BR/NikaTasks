<%@ page import="java.io.*,java.lang.*,java.util.*,java.net.*,java.util.*,java.text.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="javax.activation.*"%>

<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1" name="viewport">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/js/bootstrap.bundle.min.js"></script>
    <link href="css/index.css" rel="stylesheet" type="text/css">
    <link href="resources/N.png" rel="shortcut icon" type="image/x-icon"/>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>

    <title>NikaTasks</title>
</head>
<body>
<div class="container-fluid">

    <div class="container-fluid" id="header">
        <h1>NikaTasks</h1>
        <br>
        <h6>Ajude a sua empresa a ir para frente.</h6>
    </div>
    <div class="loader"></div>
    <div class="container-xxl col-9">
        <form action="login" id="left" method="post">
            <br>
            <h5>Login</h5>
            <div class="form-group col-8" id="login-form">
                <label for="email">Email ou Username:</label>
                <input class="form-control" name="emailorusername" placeholder="Digite seu e-mail ou nome de usuario" type="text">
            </div>
            <br>
            <div class="form-group col-8" id="login-form">
                <label for="password">Senhaaaa:</label>
                <input class="form-control" name="password" placeholder="Digite sua senha" type="password">
                <small class="form-text text-muted" id="passwordinfo">Sua senha será criptografada no nosso banco de
                    dados!</small>
            </div>
            <br>
            <br>
            <input name="" type="submit" value="Entrar" id="submitbtn">

            <script type="text/javascript">
                var form = $("#left");

                form.submit(function (e) {
                    e.preventDefault();
                    console.log("Form ajax")
                    $.ajax({
                        type: form.attr("method"),
                        url: form.attr("action"),
                        data: form.serialize(),

                        success: function (data, status) {
                            // console.log("success "+data);
                            top.location.href = "home"
                        },
                        error: function (data, status, error) {
                            $("body").html(data.response);
                        }
                    })
                })
            </script>

            <br>
            <c:if test="${ not empty requestScope.error }">
                <div class="alert alert-danger" role="alert">
                        ${requestScope.error}
                </div>
            </c:if>
            <p id='create-account'>Ainda não tem uma conta? <a href="/create-account">Crie uma clicando aqui</a>! </p>
            <!--                    <br>-->
            <!--                    <br>-->
            <!--                    <a href="index4.html" id="dd">Cadastrar funcionario.</a>-->
        </form>
    </div>
    <br>
    <div class="container-fluid col-11" id="ldo">
        <h5>Um pouco sobre nós:</h5>
        <br>
        <br>
        <p>Somos a Sun Desk Desenvolvimento de Software, criadora e desenvolvimento do NikaTasks, um site criado para
            ajudar empresas a cumprir suas retrospectivas tarefas.</p>
    </div>
    <br>
    <div class="container-fluid col-11" id="ldo">
        <h5>Quer que a sua empresa cresca?</h5>
        <br>
        <br>
        <p>Use o NikaTasks, a gente ajudará a gerenciar as suas tarefas para tornar sua empresa uma das melhores do
            mundo.</p>
    </div>
    <!--<footer id="footer">Sun Desk Desenvolvimento de Software, 2022.</footer>-->
</div>
</body>
</html>