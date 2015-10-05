<%@include file="header.jsp" %>
<jsp:useBean id="orders" class="Model.OrderList" scope="session"/>

<div class="row">
    <h3 style="text-align: center;">
        <span class="text-red">
            ${orders.items.size()} Placed orders.
        </span>
    </h3>
</div>

<div class="row">
    <table class="table table-striped table-hover ">
        <thead>
        <tr>
            <th>Created</th>
            <th>Status</th>
            <th>Changed</th>
            <th></th>
            <th>Total</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders.items}" var="order">
            <tr>
                <td>${order.created}</td>
                <td>${order.status}</td>
                <td>${order.changed}</td>
                <td><a href="order?id=${order.orderId}">View</a></td>
                <td class="text-red">${order.total}:-</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@include file="footer.jsp" %>