<%@include file="header.jsp" %>

<div class="row">
    <div class="col-6 offset-3">
        <div class="text-center margin-top-2">
            <span class="badge badge-danger"><fmt:message key="account.locked"/></span>
        </div>

        <div class="panel panel-default margin-top-1">
            <div class="panel-heading">
                <h4><fmt:message key="account.details"/></h4>
            </div>
            <div class="panel-body">
                <p>
                    <b><fmt:message key="account.username"/></b> <c:out value="${sessionScope.account.username}"/>
                </p>

                <p>
                    <b><fmt:message key="account.zip"/></b> <c:out value="${sessionScope.account.zip}"/>
                </p>

                <p>
                    <b><fmt:message key="account.street"/></b> <c:out value="${sessionScope.account.street}"/>
                </p>

            </div>
        </div>

        <c:if test="${sessionScope.account.role.id eq 1}">
            <form method="POST" action="account" class="margin-top-1">
                <input type="hidden" name="csrf" value="${sessionScope.csrf}">
                <input type="hidden" name="action" value="deRegister">
                <button class="btn btn-danger btn-block"><fmt:message key="account.close"/></button>
            </form>
        </c:if>
    </div>
</div>

<%@include file="footer.jsp" %>