<jsp:useBean id="managers" class="com.codingchili.webshoppe.model.AccountList" scope="session"/>
<jsp:useBean id="registerResult" class="com.codingchili.webshoppe.model.RegisterResult" scope="session"/>
<%@include file="header.jsp" %>

<div class="margin-top row">
    <div class="col-8 offset-2 col-md-6 offset-md-1">

        <h2 class="text-center">Managers</h2>

        <table class="table table-striped table-hover ">
            <thead>
            <tr>
                <th>Name</th>
                <th>Zip</th>
                <th>Street</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${managers.accounts}" var="manager">
                <tr>
                    <td class="align-middle">${manager.username}</td>
                    <td class="align-middle">${manager.zip}</td>
                    <td class="align-middle">${manager.street}</td>
                    <td>
                        <form method="POST" action="managers" class="margin: 0px; padding: 0px;">
                            <div class="text-danger">
                                <input type="hidden" name="action" value="remove">
                                <input type="hidden" name="manager" value="${manager.id}">
                                <button type="submit" class="btn text-danger table-icon-button">
                                    <i class="far fa-trash-alt"></i>
                                </button>
                            </div>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>


    <div class="col-8 offset-2 col-md-4 offset-md-1">
        <div class="panel ${(registerResult.erroneous) ? "panel-danger" : "panel-primary"}">
            <div class="panel-heading">
                <h3 class="panel-title">Add Manager</h3>
            </div>
            <div class="panel-body">
                <form class="form-horizontal" method="POST" action="managers">
                    <input type="hidden" name="action" value="register">
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

                            <input type="text" class="form-control" name="username" id="username"
                                   placeholder="example@domain.tld">
                        </div>
                        <div class="form-group">
                            <label for="password" class="col-lg-2 control-label">Password</label>

                            <input type="password" class="form-control" name="password" id="password"
                                   placeholder="9 characters or longer..">
                        </div>
                        <div class="form-group">
                            <label for="password-repeat" class="col-lg-2 control-label">Password</label>

                            <input type="password" class="form-control" name="password-repeat" id="password-repeat"
                                   placeholder="Password (again)">
                        </div>

                        <div class="form-group">
                            <label for="zip" class="col-lg-2 control-label">Zip code</label>

                            <input type="text" maxlength="12" class="form-control" name="zip" id="zip"
                                   placeholder="137 32 SE">
                        </div>

                        <div class="form-group">
                            <label for="street" class="col-lg-2 control-label">Street</label>

                            <input type="text" class="form-control" name="street" id="street"
                                   placeholder="Streetname 37">
                        </div>

                        <div class="form-group">
                            <div class="row button-spacer-top"></div>
                            <button type="submit" class="btn btn-block btn-primary">Register</button>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="footer.jsp" %>