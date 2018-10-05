<%@include file="header.jsp" %>
<jsp:useBean id="orders" class="com.codingchili.webshoppe.model.OrderList" scope="session"/>

<div class="row">
    <h3 class="heading-text">
        <span class="text-danger">
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
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders.items}" var="order">
            <tr>
                <td class="align-middle">${order.created}</td>
                <td class="align-middle">${order.status}</td>
                <td class="align-middle">${order.changed}</td>
                <td class="align-middle"><a href="order?id=${order.orderId}">View</a></td>
                <td  class="align-middle" class="text-danger">${order.total}:-</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@include file="footer.jsp" %>