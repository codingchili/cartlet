<jsp:useBean id="loginResult" class="com.codingchili.webshoppe.model.LoginResult" scope="session"/>

<%@include file="header.jsp" %>

<div class="container">
    <div class="row" style="height: 25%;"></div>
    <div class="col-xs-6 col-xs-offset-3">
        <div class="panel ${(!empty loginResult && loginResult.erroneous) ? "panel-danger" : "panel-primary"}">
            <div class="panel-heading">
                <h3 class="panel-title">Shoppe Login</h3>
            </div>

            <div class="panel-body">
                <c:if test="${!empty loginResult && loginResult.erroneous}">
                    <div style="text-align: center;">
                        Could not authenticate account.
                    </div>
                </c:if>

                <form class="form-horizontal" method="POST" action="login">
                    <div class="form-group">
                        <div class="col-lg-10 col-lg-offset-1">
                            <input type="text" class="form-control" name="username" id="username"
                                   placeholder="example@domain.tld" autofocus="true">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-lg-10 col-lg-offset-1">
                            <input type="password" class="form-control" name="password" id="password"
                                   placeholder="************">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row button-spacer-top"></div>
                        <div class="col-lg-8 col-lg-offset-2">
                            <button type="submit" class="btn btn-block btn-primary">Login</button>
                            <a href="register" class="form-footer-link">Register Account</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <%@include file="footer.jsp" %>

