<%@include file="header.jsp" %>
<jsp:useBean id="order" class="com.codingchili.webshoppe.model.Order" scope="session"/>

<div class="row">
    <h3 class="heading-text">
        <span class="text-danger">
            Total ${order.total}:-
        </span>
    </h3>
</div>

<div class="row">
    <table class="table table-striped table-hover ">
        <thead>
        <tr>
            <th></th>
            <th>Name</th>
            <th>Cost</th>
            <th>Count</th>
            <th></th>
            <th>Total</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${order.products}" var="product">
            <tr>
                <td class="align-middle"><img class="cart-thumbnail" src="image/${product.imageId}"></td>
                <td class="align-middle">${product.name}</td>
                <td class="align-middle">${product.cost}</td>
                <td class="align-middle">${product.count}</td>
                <td class="align-middle"><a href="view?product=${product.id}">View</a></td>
                <td class="align-middle" class="text-danger">${product.count * product.cost}:-</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<div class="row margin-top">
    <h3 class="text-center">
    <span class="alert alert-success">
        Status: ${order.status}
    </span>
    </h3>
</div>


<%@include file="footer.jsp" %>