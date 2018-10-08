<%@include file="header.jsp" %>
<jsp:useBean id="registerResult" class="com.codingchili.webshoppe.model.RegisterResult" scope="session"/>

<div class="row" style="height: 8%;"></div>

<div class="container">
    <div class="card col-10 offset-1 col-md-6 offset-md-3">
        <div class="panel ${(registerResult.erroneous) ? "panel-danger" : "panel-primary"}">
            <div class="panel-heading">
                <h3 class="panel-title"><fmt:message key="register.title"/></h3>
            </div>
            <div class="panel-body panel-body-spacer">
                <c:set var="action" scope="request" value="register"/>
                <%@include file="registration_form.jsp"%>
            </div>
        </div>
    </div>
</div>
<%@include file="footer.jsp" %>