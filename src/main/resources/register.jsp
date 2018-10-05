<%@include file="header.jsp" %>
<jsp:useBean id="registerResult" class="com.codingchili.webshoppe.model.RegisterResult" scope="session"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row" style="height: 8%;"></div>

<div class="container">
    <div class="card col-10 offset-1 col-md-6 offset-md-3">
        <div class="panel ${(registerResult.erroneous) ? "panel-danger" : "panel-primary"}">
            <div class="panel-heading">
                <h3 class="panel-title">Shoppe Register</h3>
            </div>
            <div class="panel-body panel-body-spacer">
                <form class="form-horizontal" method="POST" action="register" accept-charset="UTF-8">
                    <fieldset>
                        <c:if test="${registerResult.erroneous}">
                            <ul class="error-list text-danger">
                                <c:if test="${registerResult.accountExists}">
                                    <li>Specified account already exists.</li>
                                </c:if>
                                <c:if test="${registerResult.accountNameTooShort}">
                                    <li>Account name too short.</li>
                                </c:if>
                                <c:if test="${registerResult.passwordMismatch}">
                                    <li>Passwords does not match.</li>
                                </c:if>
                                <c:if test="${registerResult.passwordTooShort}">
                                    <li>Password is too short.</li>
                                </c:if>
                                <c:if test="${registerResult.zipSet eq false}">
                                    <li>Zip is a required field.</li>
                                </c:if>
                                <c:if test="${registerResult.streetSet eq false}">
                                    <li>Street is a required field.</li>
                                </c:if>
                            </ul>
                        </c:if>

                        <div class="form-group">
                            <label for="username" class="control-label">Email</label>
                            <input type="text" class="form-control" name="username" id="username"
                                   placeholder="example@domain.tld" value="${registerResult.account.username}" autofocus="true">
                        </div>
                        <div class="form-group">
                            <label for="password" class="control-label">Password</label>
                            <input type="password" class="form-control" name="password" value="${registerResult.account.password}" id="password"
                                   placeholder="9 characters or longer..">
                        </div>
                        <div class="form-group">
                            <label for="password-repeat" class="control-label">Password</label>
                            <input type="password" class="form-control" name="password-repeat" id="password-repeat"
                                   placeholder="Password (again)">
                        </div>

                        <div class="form-group">
                            <label for="zip" class="control-label">Zip code</label>
                            <input type="text" maxlength="12" class="form-control" value="${registerResult.account.zip}" name="zip" id="zip"
                                   placeholder="137 32 SE">
                        </div>

                        <div class="form-group">
                            <label for="street" class="control-label">Street</label>
                            <input type="text" class="form-control" name="street" id="street"
                                   placeholder="Streetname 37" value="${registerResult.account.street}">
                        </div>

                        <div class="form-group">
                            <div class="row button-spacer-top"></div>
                            <div class="col-lg-10 offset-lg-1">
                                <button type="submit" class="btn btn-block btn-primary">Register</button>
                                <a href="login" class="form-footer-link">Login</a>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
<%@include file="footer.jsp" %>