<jsp:useBean id="managers" class="com.codingchili.webshoppe.model.AccountList" scope="session"/>
<jsp:useBean id="registerResult" class="com.codingchili.webshoppe.model.RegisterResult" scope="session"/>
<%@include file="header.jsp" %>

<div class="margin-top row">
    <div class="col-8 offset-2 col-md-6 offset-md-1">

        <h2 class="text-center"><fmt:message key="managers.managers"/></h2>

        <table class="table table-striped table-hover ">
            <thead>
            <tr>
                <th><fmt:message key="account.username"/></th>
                <th><fmt:message key="account.zip"/></th>
                <th><fmt:message key="account.street"/></th>
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
                            <input type="hidden" name="csrf" value="${sessionScope.csrf}">
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
                <h3 class="panel-title"><fmt:message key="managers.add"/></h3>
            </div>
            <div class="panel-body">
                <c:set var="action" scope="request" value="managers"/>
                <%@include file="registration_form.jsp"%>
            </div>
        </div>
    </div>
</div>

<%@include file="footer.jsp" %>