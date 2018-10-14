<%@include file="header.jsp" %>
<jsp:useBean id="orders" class="com.codingchili.webshoppe.model.OrderList" scope="request"/>

<div class="row">
    <h3 class="heading-text">
        <span class="text-warning">
            ${orders.items.size()}
        </span>
        <fmt:message key="orders.placed"/>
    </h3>
</div>

<div class="row">
    <table class="table table-striped table-hover ">
        <thead>
        <tr>
            <th><fmt:message key="orders.created"/></th>
            <th><fmt:message key="orders.status"/></th>
            <th><fmt:message key="orders.changed"/></th>
            <th><fmt:message key="product.item_total"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders.items}" var="order">
            <tr>
                <td class="align-middle">${order.created}</td>
                <td class="align-middle"><fmt:message key="order.status_${order.status}"/></td>
                <td class="align-middle">${order.changed}</td>
                <td class="align-middle text-danger">
                    <a href="order?id=${order.orderId}">
                        <fmt:formatNumber type="number" maxFractionDigits="2"
                                          value="${order.orderTotal * currency_value}"/><fmt:message key="currency"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@include file="footer.jsp" %>