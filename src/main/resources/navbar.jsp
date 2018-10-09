<jsp:useBean id="categories" class="com.codingchili.webshoppe.model.Category" scope="session"/>

<nav class="navbar sticky-top navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="/products"><fmt:message key="navbar.brand"/></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">

            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    <fmt:message key="navbar.categories"/>
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <c:forEach items="${categories}" var="category">
                        <a class="dropdown-item" href="category?q=${category.categoryId}">${category.name}</a>
                    </c:forEach>
                </div>
            </li>

            <li class="nav-item"><a class="nav-link" href="cart"><fmt:message key="navbar.cart"/> [${sessionScope.cart.uniqueProducts}]</a></li>

            <c:if test="${!empty sessionScope.account}">
                <li class="nav-item"><a class="nav-link" href="orders"><fmt:message key="navbar.orders"/></a></li>

                <c:if test="${sessionScope.account.role.id eq 3}">
                    <li class="nav-item"><a class="nav-link" href="managers"><fmt:message key="navbar.managers"/></a></li>
                </c:if>

                <c:if test="${sessionScope.account.role.id > 1}">
                    <li class="nav-item"><a class="nav-link" href="process"><fmt:message key="navbar.process"/></a></li>
                    <li class="nav-item"><a class="nav-link" href="storage"><fmt:message key="navbar.storage"/></a></li>
                </c:if>

                <li class="nav-item"><a class="nav-link" href="account"><c:out value="${sessionScope.account.username}"/></a></li>
                <li class="nav-item"><a class="nav-link" href="logout"><fmt:message key="navbar.logout"/></a></li>
            </c:if>


            <c:if test="${sessionScope.account eq null}">
                <li class="nav-item"><a class="nav-link" href="login"><fmt:message key="navbar.login"/></a></li>
            </c:if>

        </ul>
        <fmt:message key="navbar.search.placeholder" var="search"/>
        <form method="GET" action="products" class="form-inline my-2 my-lg-0" role="search">
            <div class="form-group">
                <input type="text" class="form-control" placeholder="${search}" name="search">
            </div>
            <button type="submit" class="btn btn-warning" style="margin-left: 16px;">
                <i class="fas fa-search"></i>
            </button>
        </form>
    </div>
</nav>