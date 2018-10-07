<jsp:useBean id="loginResult" class="com.codingchili.webshoppe.model.LoginResult" scope="session"/>

<%@include file="header.jsp" %>

<div class="row" style="height: 8%;"></div>

<div class="container">
    <div class="card col-10 offset-1 col-md-6 offset-md-3">
        <div class="panel ${(!empty loginResult && loginResult.erroneous) ? "panel-danger" : "panel-primary"}">
            <div class="panel-heading">
                <h3 class="panel-title">Shoppe Login</h3>
            </div>

            <div class="panel-body panel-body-spacer">
                <c:if test="${!empty loginResult && loginResult.erroneous}">
                    <div class="error-list text-danger">
                        Could not authenticate account.
                    </div>
                </c:if>

                <form class="form-horizontal" method="POST" action="login">
                    <input type="hidden" name="callback" value="${requestScope.callback}">
                    <input type="hidden" name="csrf" value="${sessionScope.csrf}">
                    <div class="form-group">
                        <input type="text" class="form-control" name="username" id="username"
                               placeholder="example@domain.tld" autofocus="true">
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control" name="password" id="password"
                               placeholder="************">
                    </div>
                    <div class="form-group">
                        <div class="row button-spacer-top"></div>
                        <button type="submit" class="btn btn-block btn-primary">Login</button>
                        <a href="register" class="form-footer-link">Register Account</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <%@include file="footer.jsp" %>

