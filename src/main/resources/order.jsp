<%@include file="header.jsp" %>
<jsp:useBean id="order" class="com.codingchili.webshoppe.model.Order" scope="request"/>

<div class="row">
    <h3 class="heading-text">
        <fmt:message key="cart.total"/>
        <span class="text-warning">
            <fmt:formatNumber type="number" maxFractionDigits="2" value="${order.orderTotal * currency_value}"/>
            <fmt:message key="currency"/>
        </span>
    </h3>
</div>

<div class="row">
    <table class="table table-striped table-hover ">
        <thead>
        <tr>
            <th></th>
            <th><fmt:message key="product.name"/></th>
            <th><fmt:message key="product.each"/></th>
            <th><fmt:message key="product.quantity"/></th>
            <th><fmt:message key="product.item_total"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${order.products}" var="product">
            <tr>
                <td class="align-middle">
                    <a href="view?product=${product.id}">
                        <img class="cart-thumbnail" src="image/${product.imageId}">
                    </a>
                </td>
                <td class="align-middle">${product.name}</td>
                <td class="align-middle">
                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${product.cost * currency_value}"/>
                </td>
                <td class="align-middle">${product.count}</td>
                <td class="align-middle" class="text-danger">
                    <fmt:formatNumber type="number" maxFractionDigits="2"
                                      value="${product.count * product.cost * currency_value}"/><fmt:message
                        key="currency"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<div class="row margin-top">
    <h3 class="text-center">
    <span class="alert alert-success">
        <fmt:message key="orders.status"/>: <fmt:message key="order.status_${order.status}"/>
    </span>
    </h3>
</div>


<%@include file="footer.jsp" %>