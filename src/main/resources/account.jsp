<%@include file="header.jsp" %>

<div class="row">
    <div class="col-xs-6 col-xs-offset-3">
        <div class="text-center margin-top-2">
            <span class="label label-danger">Locked for editing.</span>
        </div>

        <div class="panel panel-default margin-top-1">
            <div class="panel-heading">
                <h4>Account Details</h4>
            </div>
            <div class="panel-body">
                <p>
                    <b>Username</b> ${sessionScope.account.username}
                </p>

                <p>
                    <b>ZIP</b> ${sessionScope.account.zip}
                </p>

                <p>
                    <b>Street</b> ${sessionScope.account.street}
                </p>

            </div>
        </div>

        <c:if test="${sessionScope.account.role.id eq 1}">
            <form method="POST" action="account" class="margin-top-1">
                <input type="hidden" name="action" value="deRegister">
                <button class="btn btn-danger btn-block">Close Account</button>
            </form>
        </c:if>
    </div>
</div>

<%@include file="footer.jsp" %>