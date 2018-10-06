<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="categories" class="com.codingchili.webshoppe.model.Category" scope="session"/>

<nav class="navbar sticky-top navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="/products">Webshop</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">

            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    Categories
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <c:forEach items="${categories}" var="category">
                        <a class="dropdown-item" href="category?q=${category.categoryId}">${category.name}</a>
                    </c:forEach>
                </div>
            </li>

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
        <form method="GET" action="products" class="form-inline my-2 my-lg-0" role="search">
            <div class="form-group">
                <input type="text" class="form-control" placeholder="Search" name="search">
            </div>
            <button type="submit" class="btn btn-warning" style="margin-left: 16px;"><i class="fas fa-search"></i>
            </button>
        </form>
    </div>
</nav>