<%@ page import="java.io.*,java.lang.*,java.util.*,java.net.*,java.util.*,java.text.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="javax.activation.*"%>

<html lang="pt">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/js/bootstrap.bundle.min.js%22%3E"></script>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
    <link rel="shortcut icon" href="N.png" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="css/home.css">
    <title>NikaTasks</title>
</head>
<body>
<div class="container-fluid">
    <h2>Minhas empresas</h2>
</div>
<div class="container-fluid">
    <c:forEach items="${requestScope.userCompanies}" var="usrCompanies">
        <c:if test="${usrCompanies.isOwner()}">
            <button id="${usrCompanies.getCompanieID()}" onclick="companie_click(this.id)" class="custombtn"><h4>${usrCompanies.getCompanieName()}</h4>
                <p>Pessoas: ${usrCompanies.getCompanieTotalMembers()}</p><p>Tarefas: ${usrCompanies.getCompanieTotalTasks()}</p>
            </button>
        </c:if>
    </c:forEach>
    <br>
    <input id="createcompanie" type="submit" name="" value="Criar">

    <script type="text/javascript">
        function companie_click(companie_id) {
            top.location.href = "companies?id="+companie_id
        }

        $(document).ready(function () {
            $('#createcompanie').click(function () {
                $('#centerform').show();
                // $(this).hide();
            })

            $('#close').click(function () {
                $('#centerform').hide();
                $('#show').show();
            })

            $("#createcompaniesubmit").click(function () {
                $('#centerform').hide();
                $('#show').show();
            })

            // $(".custombtn").click(function (companie_id) {
            //     alert(companie_id)
            // })

            var form = $("#createcompaniesubmitform");

            form.submit(function (e) {
                e.preventDefault();
                console.log("Form ajax")
                $.ajax({
                    type: form.attr("method"),
                    url: form.attr("action"),
                    data: form.serialize(),

                    success: function (data, status) {
                        console.log("success " + form.attr("action"));
                        top.location.href = "home"
                    },
                    error: function (data, status, error) {
                        console.log("error");
                        // $("body").html(data.response);
                    }
                })
            })
        })
    </script>

</div><br>
<div id="centerform" class="centerform hideform">
    <button id="close" style="float: right;">X</button>
    <form id="createcompaniesubmitform" method="post" action="create-companie">
        Nome da empresa:<br>
        <input type="text" name="companieName">
        <br>
        <input id="createcompaniesubmit" type="submit" value="Criar">
    </form>
</div>
<div class="container-fluid">
    <h2>Times que faço parte</h2>
</div>
<div class="container-fluid">
    <c:forEach items="${requestScope.userCompanies}" var="usrCompanies">
        <c:if test="${not usrCompanies.isOwner()}">
            <button id="${usrCompanies.getCompanieID()}" onclick="companie_click(this.id)" class="custombtn"><h4>${usrCompanies.getCompanieName()}</h4>
                <p>Pessoas: ${usrCompanies.getCompanieTotalMembers()}</p><p>Tarefas: ${usrCompanies.getCompanieTotalTasks()}</p>
            </button>
        </c:if>
    </c:forEach>
</div>
</body>
</html>
