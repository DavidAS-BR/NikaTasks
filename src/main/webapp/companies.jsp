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
<section class="vh-0">
    <!--<div class="container py-5 h-100">-->
    <div class="row d-flex justify-content-center align-items-center h-100">
        <div class="col">
            <div class="card" id="list1" style="border-radius: .75rem; background-color: #eff1f2;">
                <div class="card-body py-4 px-4 px-md-5">

                    <nav class="navbar navbar-light justify-content-betwee background-color: #eff1f2;">
                        <a class="navbar-brand"></a>
                        <form class="form-inline">
                            <button class="btn btn-outline-success my-2 my-sm-0" type="button">
                                Adicionar Membro
                            </button>
                            <button type="button" class="btn btn-outline-secondary">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-lines-fill" viewBox="0 0 16 16">
                                    <path d="M6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm-5 6s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zM11 3.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5zm.5 2.5a.5.5 0 0 0 0 1h4a.5.5 0 0 0 0-1h-4zm2 3a.5.5 0 0 0 0 1h2a.5.5 0 0 0 0-1h-2zm0 3a.5.5 0 0 0 0 1h2a.5.5 0 0 0 0-1h-2z"/>
                                </svg>
                            </button>
                        </form>
                    </nav>

                    <p class="h1 text-center mt-3 mb-4 pb-3 text-primary">
                        <i class="fas fa-check-square me-1"></i>
                        <u>Companie Name</u>
                    </p>

                    <div class="pb-2">
                        <div class="card">
                            <div class="card-body">
                                <div class="d-flex flex-row align-items-center">
                                    <input type="text" class="form-control form-control-lg" id="exampleFormControlInput1" placeholder="Add new...">
                                    <a href="#!" data-mdb-toggle="tooltip" title="Set due date"><i class="fas fa-calendar-alt fa-lg me-3"></i></a>
                                    <div>
                                        <button type="button" class="btn btn-primary">Adicionar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <hr class="my-4">

                    <ul class="list-group list-group-horizontal rounded-0 bg-transparent">
                        <li class="list-group-item d-flex align-items-center ps-0 pe-3 py-1 rounded-0 border-0 bg-transparent">
                            <div class="form-check">
                                <input
                                        class="form-check-input me-0"
                                        type="checkbox"
                                        value=""
                                        id="flexCheckChecked1"
                                        aria-label="..."
                                        checked
                                />
                            </div>
                        </li>
                        <li class="list-group-item px-3 py-1 d-flex align-items-center flex-grow-1 border-0 bg-transparent">
                            <p class="lead fw-normal mb-0">Buy groceries for next week</p>
                        </li>
                    </ul>

                    <ul class="list-group list-group-horizontal rounded-0">
                        <li class="list-group-item d-flex align-items-center ps-0 pe-3 py-1 rounded-0 border-0 bg-transparent">
                            <div class="form-check">
                                <input
                                        class="form-check-input me-0"
                                        type="checkbox"
                                        value=""
                                        id="flexCheckChecked2"
                                        aria-label="..."
                                />
                            </div>
                        </li>
                        <li class="list-group-item px-3 py-1 d-flex align-items-center flex-grow-1 border-0 bg-transparent">
                            <p class="lead fw-normal mb-0">Renew car insurance</p>
                        </li>
                        <li class="list-group-item px-2 py-1 d-flex align-items-center border-0 bg-transparent">
                            <div class="py-2 px-3 me-2 border border-warning rounded-3 d-flex align-items-center bg-light">
                                <p class="small mb-0">
                                    <a href="#!" data-mdb-toggle="tooltip" title="Due on date">
                                        <i class="fas fa-hourglass-half me-2 text-warning text-center"></i>
                                    </a>
                                    Feito por: David
                                </p>
                            </div>
                        </li>
                    </ul>

                    <ul class="list-group list-group-horizontal rounded-0 mb-2">
                        <li class="list-group-item d-flex align-items-center ps-0 pe-3 py-1 rounded-0 border-0 bg-transparent">
                            <div class="form-check">
                                <input
                                        class="form-check-input me-0"
                                        type="checkbox"
                                        value=""
                                        id="flexCheckChecked3"
                                        aria-label="..."
                                />
                            </div>
                        </li>
                        <li class="list-group-item px-3 py-1 d-flex align-items-center flex-grow-1 border-0 bg-transparent">
                            <p class="lead fw-normal mb-0 bg-light w-100 ms-n2 ps-2 py-1 rounded">Sign up for online course</p>
                        </li>
                    </ul>

                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
