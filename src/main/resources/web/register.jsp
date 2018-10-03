<jsp:useBean id="registerResult" class="com.codingchili.webshoppe.model.RegisterResult" scope="session"/>

<%@include file="header.jsp" %>

<div class="row" style="height: 25%;"></div>
<div class="col-xs-6 col-xs-offset-3">
    <div class="panel ${(registerResult.erroneous) ? "panel-danger" : "panel-primary"}">
        <div class="panel-heading">
            <h3 class="panel-title">Shoppe Register</h3>
        </div>
        <div class="panel-body">
            <form class="form-horizontal" method="POST" action="register">
                <fieldset>
                    <c:if test="${registerResult.erroneous}">
                        <ul style="text-align: center; list-style-type: none;">
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
                        <label for="username" class="col-lg-2 control-label">Email</label>

                        <div class="col-lg-10">
                            <input type="text" class="form-control" name="username" id="username"
                                   placeholder="example@domain.tld" autofocus="true">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="col-lg-2 control-label">Password</label>

                        <div class="col-lg-10">
                            <input type="password" class="form-control" name="password" id="password"
                                   placeholder="9 characters or longer..">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password-repeat" class="col-lg-2 control-label">Password</label>

                        <div class="col-lg-10">
                            <input type="password" class="form-control" name="password-repeat" id="password-repeat"
                                   placeholder="Password (again)">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="zip" class="col-lg-2 control-label">Zip code</label>

                        <div class="col-lg-10">
                            <input type="text" maxlength="12" class="form-control" name="zip" id="zip"
                                   placeholder="137 32 SE">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="street" class="col-lg-2 control-label">Street</label>

                        <div class="col-lg-10">
                            <input type="text" class="form-control" name="street" id="street"
                                   placeholder="Streetname 37">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="row button-spacer-top"></div>
                        <div class="col-lg-10 col-lg-offset-1">
                            <button type="submit" class="btn btn-block btn-primary">Register</button>
                            <a href="login" class="form-footer-link">Login</a>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>
<%@include file="footer.jsp" %>