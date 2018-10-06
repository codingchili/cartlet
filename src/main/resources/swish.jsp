<%@include file="header.jsp" %>
<jsp:useBean id="order" class="com.codingchili.webshoppe.model.Order" scope="session"/>

<div class="row">
    <div class="col-12 margin-top text-center">
        <span class="badge badge-info">paying with swish</span>
    </div>
</div>

<div class="row">
    <!-- pay with swish button -> onClick show the image -->
    <div class="col-10 offset-1">
        <a href="swish://payment?data={'version':1,'payee':{'value':'0737557122'},'amount':{'value': 200},message:{'value':'order 21'}}">
            <img src="swish?orderId=${order.orderId}">
        </a>
    </div>

    <form method="POST" action="swish" class="col-8 offset-2">
        <button class="btn btn-primary btn-block">I've sent the funds</button>
    </form>

</div>

<%@include file="footer.jsp" %>