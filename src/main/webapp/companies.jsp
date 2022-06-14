<%--<jsp:include page="/companies" />--%>

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
    <link href="css/companie.css" rel="stylesheet" type="text/css">
    <link href="resources/N.png" rel="shortcut icon" type="image/x-icon"/>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>

    <title>NikaTasks</title>
</head>
<body>
<div class="modal fade" id="listmembersmodal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="listmembersmodalTitle">Adicionar membro</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="closeListMemberForm()">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <ul class="list-group list-group-flush">
                <c:forEach items="${requestScope.accesscompanie.memberList}" var="memberlist">
                    <li class="list-group-item">${memberlist.values()}</li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>

<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Adicionar membro</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="closeAddMemberForm()">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <form id="addMemberForm" method="post" action="/companies?id=${requestScope.accesscompanie.companieID}">
                <div class="modal-body">
                    <!--...-->
                    <div class="col-xs-1" align="center">
                        <div class="col-auto">
                            <label class="sr-only" for="inlineFormInput">Name</label>
                            <input name="adduserform" type="text" class="form-control mb-2" id="inlineFormInput" placeholder="E-mail ou nome de usuário">
                        </div>
                    </div>
                    <!--...-->
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" onclick="closeAddMemberForm()">Cancelar</button>
                    <button type="submit" class="btn btn-primary" form="addMemberForm">Adicionar</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    function openAddMemberForm() {
        // document.getElementById('main').style.opacity = '0.5';
        $("#exampleModal").modal("show")
    }

    function closeAddMemberForm() {
        $("#exampleModal").modal("hide")
        // document.getElementById('main').style.opacity = '1';
    }

    function openListMemberForm() {
        // document.getElementById('main').style.opacity = '0.5';
        $("#listmembersmodal").modal("show")
    }

    function closeListMemberForm() {
        $("#listmembersmodal").modal("hide")
        // document.getElementById('main').style.opacity = '1';
    }

    var form = $("#addMemberForm");
    // var fd = new FormData(form);
    form.submit(function (e) {
        e.preventDefault();
        var adduserform = $("#inlineFormInput").val();

        $.ajax({
            type: form.attr("method"),
            url: window.location.href+'&id=${requestScope.accesscompanie.companieID}'+'&action=add',
            data: $("#addMemberForm").serialize(),

            success: function (data, status) {
                note = document.getElementById("note");
                note.textContent = 'Membro adicionado com sucesso!'
                note.style.display = 'block';
                closeAddMemberForm()
                closeNotification();
            },
            error: function (data, status, error) {
                note = document.getElementById("note");
                note.textContent = 'Algum erro ocorreu!'
                note.style.display = 'block';
                closeAddMemberForm()
                closeNotification();
            }
        })
    })
</script>

<section id="main" class="vh-0">
    <!--<div class="container py-5 h-100">-->
    <div id="note" class="alert alert-success" role="alert" style="display: none">
        Teste. <a id="close">[close]</a>
    </div>

    <div class="row d-flex justify-content-center align-items-center h-100">
        <div class="col">
            <div class="card" id="list1" style="border-radius: .75rem; background-color: #eff1f2;">

                <script>
                    $(document).ready(function() {
                        var pageRefresh = 5000; //5 s
                        setInterval(function() {
                            refresh();
                        }, pageRefresh);
                    });

                    // Functions

                    function refresh() {
                        $('#taskList').load(location.href + " #taskList");
                    }

                    function closeNotification() {
                        setTimeout(function() {
                            note = document.getElementById("note");
                            note.style.display = 'none';
                        }, 3000);
                    }

                    function updateTask(taskID) {

                        $.ajax({
                            type: 'POST',
                            url: window.location.href+'&task='+taskID+'&action=update',

                            success: function (data, status) {
                                note = document.getElementById("note");
                                note.textContent = 'Status da tarefa alterado com sucesso!'
                                note.style.display = 'block';
                                closeNotification();
                                refresh()
                            },

                            error: function (data, status, error) {
                                console.log("error");
                            }
                        })
                    }

                    function deleteTask(taskID) {

                        $.ajax({
                            type: 'POST',
                            url: window.location.href+'&task='+taskID+'&action=delete',

                            success: function (data, status) {
                                console.log("success deleted task")
                                refresh()
                                note = document.getElementById("note");
                                note.textContent = 'Tarefa deletada com sucesso!'
                                note.style.display = 'block';
                                closeNotification();
                            },

                            error: function (data, status, error) {
                                console.log("error");
                            }
                        })
                    }

                    close = document.getElementById("close");
                    close.addEventListener('click', function() {
                        note = document.getElementById("note");
                        note.style.display = 'none';
                    }, false);
                </script>

                <div id="taskbox" class="card-body py-4 px-4 px-md-5">

                    <nav class="navbar navbar-light justify-content-betwee background-color: #eff1f2;">
                        <a class="navbar-brand"></a>
                        <form class="form-inline">
                            <button class="btn btn-outline-success my-2 my-sm-0" type="button" onclick="openAddMemberForm()">
                                Adicionar Membro
                            </button>

                            <button type="button" class="btn btn-outline-secondary" onclick="openListMemberForm()">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-lines-fill" viewBox="0 0 16 16">
                                    <path d="M6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm-5 6s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zM11 3.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5zm.5 2.5a.5.5 0 0 0 0 1h4a.5.5 0 0 0 0-1h-4zm2 3a.5.5 0 0 0 0 1h2a.5.5 0 0 0 0-1h-2zm0 3a.5.5 0 0 0 0 1h2a.5.5 0 0 0 0-1h-2z"/>
                                </svg>
                            </button>
                        </form>
                    </nav>

                    <p class="h1 text-center mt-3 mb-4 pb-3 text-primary">
                        <i class="fas fa-check-square me-1"></i>
                        <u>${requestScope.accesscompanie.companieName}</u>
                    </p>

                    <div class="pb-2">
                        <div class="card">
                            <div class="card-body">
                                <form id="addtaskform" class="d-flex flex-row align-items-center" method="POST" action="/companies?id=${requestScope.accesscompanie.companieID}" autocomplete="off">
                                    <input name="taskdescription" type="text" class="form-control form-control-lg" id="exampleFormControlInput1" placeholder="Adicionar tarefa...">
                                    <a href="#!" data-mdb-toggle="tooltip" title="Set due date"><i class="fas fa-calendar-alt fa-lg me-3"></i></a>
                                    <div>
                                        <button type="submit" class="btn btn-primary">Adicionar</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <script type="text/javascript">
                        var form = $("#addtaskform");

                        form.submit(function (e) {
                            e.preventDefault();
                            console.log("Form ajax")
                            $.ajax({
                                type: form.attr("method"),
                                url: form.url,
                                data: form.serialize(),

                                success: function (data, status) {
                                    document.getElementById('exampleFormControlInput1').value = ''

                                    refresh();

                                    note = document.getElementById("note");
                                    note.textContent = 'Tarefa adicionada com sucesso!'
                                    note.style.display = 'block';
                                    closeNotification();
                                },
                                error: function (data, status, error) {
                                    note = document.getElementById("note");
                                    note.textContent = 'Algum erro ocorreu!'
                                    note.style.display = 'block';
                                    closeNotification();
                                }
                            })
                        })
                    </script>

                    <hr class="my-4">

                    <div id="taskList">
                        <c:forEach items="${requestScope.accesscompanie.tasklist}" var="taskList">
                            <c:if test="${not taskList.isTaskCompleted()}">
                                <ul class="list-group list-group-horizontal rounded-0 mb-2">
                                    <li class="list-group-item d-flex align-items-center ps-0 pe-3 py-1 rounded-0 border-0 bg-transparent">
                                        <div class="form-check">
                                            <input
                                                    class="form-check-input me-0"
                                                    type="checkbox"
                                                    value=""
                                                    id="flexCheckChecked3"
                                                    aria-label="..."
                                                    onclick="updateTask('${taskList.getTaskID()}')"
                                            />
                                        </div>
                                    </li>
                                    <li class="list-group-item px-3 py-1 d-flex align-items-center flex-grow-1 border-0 bg-transparent">
                                        <p class="lead fw-normal mb-0 bg-light w-100 ms-n2 ps-2 py-1 rounded">${taskList.getTaskDescription()}</p>
                                    </li>
                                    <li class="list-group-item px-2 py-1 d-flex align-items-center border-0 bg-transparent">
                                        <div class="py-2 px-3 me-2 border border-danger rounded-3 d-flex align-items-center bg-light">
                                            <button class="btn btn-danger btn-sm rounded-0" type="button" data-toggle="tooltip" data-placement="top" title="Deletar" onclick="deleteTask('${taskList.getTaskID()}')"><i class="fa-solid fa-minus"></i></button>
                                        </div>
                                    </li>
                                </ul>
                            </c:if>

                            <c:if test="${taskList.isTaskCompleted()}">
                                <ul class="list-group list-group-horizontal rounded-0">
                                    <li class="list-group-item d-flex align-items-center ps-0 pe-3 py-1 rounded-0 border-0 bg-transparent">
                                        <div class="form-check">
                                            <input
                                                    class="form-check-input me-0"
                                                    type="checkbox"
                                                    value=""
                                                    id="flexCheckChecked2"
                                                    aria-label="..."
                                                    checked
                                                    onclick="updateTask('${taskList.getTaskID()}')"
                                            />
                                        </div>
                                    </li>
                                    <li class="list-group-item px-3 py-1 d-flex align-items-center flex-grow-1 border-0 bg-transparent">
                                        <p class="lead fw-normal mb-0">${taskList.getTaskDescription()}</p>
                                    </li>
                                    <li class="list-group-item px-2 py-1 d-flex align-items-center border-0 bg-transparent">
                                        <div class="py-2 px-3 me-2 border border-warning rounded-3 d-flex align-items-center bg-light">
                                            <p class="small mb-0">
                                                <a href="#!" data-mdb-toggle="tooltip" title="--">
                                                    <i class="fas fa-hourglass-half me-2 text-warning text-center"></i>
                                                </a>
                                                Feito por: ${taskList.getCompletedBy()}
                                            </p>
                                        </div>
                                    </li>
                                </ul>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
