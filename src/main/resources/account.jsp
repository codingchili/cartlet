<%@include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div class="col-6 offset-3">
        <div class="text-center margin-top-2">
            <span class="badge badge-danger">Locked for editing.</span>
        </div>

        <div class="panel panel-default margin-top-1">
            <div class="panel-heading">
                <h4>Account Details</h4>
            </div>
            <div class="panel-body">
                <p>
                    <b>Username</b><c:out value="${sessionScope.account.username}"/>
                </p>

                <p>
                    <b>ZIP</b> <c:out value="${sessionScope.account.zip}"/>
                </p>

                <p>
                    <b>Street</b> <c:out value="${sessionScope.account.street}"/>
                </p>

            </div>
        </div>

        <c:if test="${sessionScope.account.role.id eq 1}">
            <form method="POST" action="account" class="margin-top-1">
                <input type="hidden" name="csrf" value="${sessionScope.csrf}">
                <input type="hidden" name="action" value="deRegister">
                <button class="btn btn-danger btn-block">Close Account</button>
            </form>
        </c:if>
    </div>
</div>

<%@include file="footer.jsp" %>