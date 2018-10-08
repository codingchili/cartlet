<jsp:useBean id="loginResult" class="com.codingchili.webshoppe.model.LoginResult" scope="session"/>
<%@include file="header.jsp" %>

<div class="row" style="height: 8%;"></div>

<div class="container">
    <div class="card col-10 offset-1 col-md-6 offset-md-3">
        <div class="panel ${(!empty loginResult && loginResult.erroneous) ? "panel-danger" : "panel-primary"}">
            <div class="panel-heading margin-top-0">
                <h4 class="panel-title"><fmt:message key="login.title"/></h4>
            </div>

            <div class="panel-body panel-body-spacer">
                <c:if test="${!empty loginResult && loginResult.erroneous}">
                    <div class="error-list text-danger">
                        <fmt:message key="login.authentication_fail"/>
                    </div>
                </c:if>

                <form class="form-horizontal" method="POST" action="login">
                    <input type="hidden" name="csrf" value="${sessionScope.csrf}">
                    <div class="form-group">
                        <fmt:message key="account.username.placeholder" var="usernamePlaceholder"/>
                        <input type="text" class="form-control" name="username" id="username"
                               placeholder="${usernamePlaceholder}" autofocus="true">
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control" name="password" id="password"
                               placeholder="************">
                    </div>
                    <div class="form-group">
                        <div class="row button-spacer-top"></div>
                        <button type="submit" class="btn btn-block btn-primary"><fmt:message key="login.button_login"/> </button>
                        <a href="register" class="form-footer-link"><fmt:message key="login.link_register"/></a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <%@include file="footer.jsp" %>

