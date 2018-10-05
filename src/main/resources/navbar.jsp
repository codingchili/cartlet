<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="categories" class="com.codingchili.webshoppe.model.Category" scope="session"/>

<nav class="navbar sticky-top navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="/products">Webshop</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01"
            aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarColor01">
        <ul class="navbar-nav mr-auto">
            <ul class="nav navbar-nav">
                <ul class="nav nav-pills">
                    <%--<li class="nav-item">
                        <a class="nav-link active" href="#">Active</a>
                    </li>--%>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button"
                           aria-haspopup="true" aria-expanded="false">Categories</a>
                        <div class="dropdown-menu" x-placement="bottom-start"
                             style="position: absolute; transform: translate3d(0px, 38px, 0px); top: 0px; left: 0px; will-change: transform;">
                            <c:forEach items="${categories}" var="category">
                                <a class="dropdown-item" href="category?q=${category.categoryId}">${category.name}</a>
                            </c:forEach>
                        </div>
                    </li>
                    <%--<li class="nav-item">
                        <a class="nav-link" href="#">Link</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link disabled" href="#">Disabled</a>
                    </li>--%>

                        <c:if test="${!empty sessionScope.account}">
                            <c:if test="${sessionScope.account.role.id eq 3}">
                                <li class="nav-item"><a class="nav-link" href="managers">Managers</a></li>
                            </c:if>

                            <c:if test="${sessionScope.account.role.id > 1}">
                                <li class="nav-item"><a class="nav-link" href="process">Process</a></li>
                                <li class="nav-item"><a class="nav-link" href="storage">Storage</a></li>
                            </c:if>

                            <li class="nav-item"><a class="nav-link" href="cart">Cart [${sessionScope.cart.uniqueProducts}]</a></li>
                            <li class="nav-item"><a class="nav-link" href="orders">Orders</a></li>
                            <li class="nav-item"><a class="nav-link" href="account">${sessionScope.account.username}</a></li>
                            <li class="nav-item"><a class="nav-link" href="logout">Logout</a></li>
                        </c:if>

                        <c:if test="${sessionScope.account eq null}">
                            <li class="nav-item"><a class="nav-link" href="login">Login</a></li>
                        </c:if>

                </ul>
            </ul>
        </ul>

<%--        <ul class="navbar-nav mr-auto">
            <ul class="nav navbar-nav">
                <ul class="nav nav-pills">
                    <c:if test="${!empty sessionScope.account}">
                        <c:if test="${sessionScope.account.role.id eq 3}">
                            <li class="nav-item"><a class="nav-link" href="managers">Managers</a></li>
                        </c:if>

                        <c:if test="${sessionScope.account.role.id > 1}">
                            <li class="nav-item"><a class="nav-link" href="process">Process</a></li>
                            <li class="nav-item"><a class="nav-link" href="storage">Storage</a></li>
                        </c:if>

                        <li class="nav-item"><a class="nav-link" href="cart">Cart [${sessionScope.cart.uniqueProducts}]</a></li>
                        <li class="nav-item"><a class="nav-link" href="orders">Orders</a></li>
                        <li class="nav-item"><a class="nav-link" href="account">${sessionScope.account.username}</a></li>
                        <li class="nav-item"><a class="nav-link" href="logout">Logout</a></li>
                    </c:if>

                    <c:if test="${sessionScope.account eq null}">
                        <li class="nav-item"><a class="nav-link" href="login">Login</a></li>
                    </c:if>
                </ul>
            </ul>
        </ul>--%>

        <form method="GET" action="products" class="form-inline my-2 my-lg-0" role="search">
            <div class="form-group">
                <input type="text" class="form-control" placeholder="Search" name="search">
            </div>
            <button type="submit" class="btn btn-default">Search</button>
        </form>
    </div>
</nav>

<%--
<nav class="navbar navbar-inverse navbar-static-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="products">BookShoppe</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Categories
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="products">Browse all</a></li>
                        <li class="divider"></li>

                        <c:forEach items="${categories}" var="category">
                            <li><a href="category?q=${category.categoryId}">${category.name}</a></li>
                        </c:forEach>

                    </ul>
                </li>
            </ul>

            <form method="GET" action="products" class="navbar-form navbar-left" role="search">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Search" name="search">
                </div>
                <button type="submit" class="btn btn-default">Submit</button>
            </form>

            <ul class="nav navbar-nav navbar-right">

                <c:if test="${!empty sessionScope.account}">
                    <c:if test="${sessionScope.account.role.id eq 3}">
                        <li><a href="managers">Managers</a></li>
                    </c:if>

                    <c:if test="${sessionScope.account.role.id > 1}">
                        <li><a href="process">Process</a></li>
                        <li><a href="storage">Storage</a></li>
                    </c:if>

                    <li><a href="cart">Cart [${sessionScope.cart.uniqueProducts}]</a></li>
                    <li><a href="orders">Orders</a></li>
                    <li><a href="account">${sessionScope.account.username}</a></li>
                    <li><a href="logout">Logout</a></li>
                </c:if>

                <c:if test="${sessionScope.account eq null}">
                    <li><a href="login">Login</a></li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>--%>
