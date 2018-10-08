<form class="form-horizontal" method="POST" action="${action}">
    <input type="hidden" name="csrf" value="${sessionScope.csrf}">
    <input type="hidden" name="action" value="${action}">
    <fieldset>
        <c:if test="${registerResult.erroneous}">
            <ul class="text-danger login-messages">
                <c:if test="${registerResult.accountExists}">
                    <li><fmt:message key="account.exists"/></li>
                </c:if>
                <c:if test="${registerResult.accountNameTooShort}">
                    <li><fmt:message key="account.too_short_name"/></li>
                </c:if>
                <c:if test="${registerResult.passwordMismatch}">
                    <li><fmt:message key="account.password_mismatch"/></li>
                </c:if>
                <c:if test="${registerResult.passwordTooShort}">
                    <li><fmt:message key="account.too_short_pass"/></li>
                </c:if>
                <c:if test="${registerResult.zipSet eq false}">
                    <li><fmt:message key="account.no_zip"/></li>
                </c:if>
                <c:if test="${registerResult.streetSet eq false}">
                    <li><fmt:message key="account.no_street"/></li>
                </c:if>
            </ul>
        </c:if>

        <div class="form-group">
            <label for="username" class="control-label"><fmt:message key="account.username"/></label>

            <fmt:message key="account.username.placeholder" var="usernamePlaceholder"/>
            <input type="text" class="form-control" name="username" id="username"
                   placeholder="${usernamePlaceholder}" value="${fn:escapeXml(registerResult.account.username)}" autofocus="true">
        </div>
        <div class="form-group">
            <label for="password" class="control-label"><fmt:message key="account.password"/></label>

            <fmt:message key="account.password.placeholder" var="passwordPlaceholder"/>
            <input type="password" class="form-control" name="password" id="password"
                   placeholder="${passwordPlaceholder}">
        </div>
        <div class="form-group">
            <label for="password-repeat" class="control-label"><fmt:message key="account.password"/></label>

            <fmt:message key="account.password_repeat.placeholder" var="passwordRepeatPlaceholder"/>
            <input type="password" class="form-control" name="password-repeat" id="password-repeat"
                   placeholder="${passwordRepeatPlaceholder}">
        </div>

        <div class="form-group">
            <label for="zip" class="control-label"><fmt:message key="account.zip"/></label>

            <fmt:message key="account.zip.placeholder" var="zipPlaceholder"/>
            <input type="text" maxlength="12" class="form-control" name="zip" id="zip"
                   placeholder="${zipPlaceholder}" value="${fn:escapeXml(registerResult.account.zip)}">
        </div>

        <div class="form-group">
            <label for="street" class="control-label"><fmt:message key="account.street"/></label>

            <fmt:message key="account.street.placeholder" var="streetPlaceholder"/>
            <input type="text" class="form-control" name="street" id="street"
                   placeholder="${streetPlaceholder}" value="${fn:escapeXml(registerResult.account.street)}">
        </div>

        <div class="form-group">
            <div class="row button-spacer-top"></div>
            <button type="submit" class="btn btn-block btn-primary"> <fmt:message key="account.register"/></button>
        </div>
    </fieldset>
</form>