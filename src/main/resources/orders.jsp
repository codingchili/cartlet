<%@include file="header.jsp" %>
<jsp:useBean id="orders" class="com.codingchili.webshoppe.model.OrderList" scope="session"/>

<div class="row">
    <h3 class="heading-text">
        <span class="text-danger">
            ${orders.items.size()} <fmt:message key="orders.placed"/>
        </span>
    </h3>
</div>

<div class="row">
    <table class="table table-striped table-hover ">
        <thead>
        <tr>
            <th><fmt:message key="orders.created"/></th>
            <th><fmt:message key="orders.status"/></th>
            <th><fmt:message key="orders.changed"/></th>
            <th></th>
            <th><fmt:message key="product.item_total"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders.items}" var="order">
            <tr>
                <td class="align-middle">${order.created}</td>
                <td class="align-middle">${order.status}</td>
                <td class="align-middle">${order.changed}</td>
                <td class="align-middle"><a href="order?id=${order.orderId}"><fmt:message key="product.view"/></a></td>
                <td class="align-middle" class="text-danger">
                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${order.total * currency_value}"/><fmt:message key="currency"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@include file="footer.jsp" %>