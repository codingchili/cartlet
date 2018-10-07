<%@include file="header.jsp" %>

<div class="row">
    <h3 class="heading-text">
        <span class="text-danger">
           Order total ${sessionScope.cart.totalCost} :-
        </span>
    </h3>
</div>

<div class="row">

    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col"></th>
            <th scope="col">Name</th>
            <th scope="col">Each</th>
            <th scope="col">Quantity</th>
            <th scope="col">Total</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${sessionScope.cart.items}" var="product">
            <tr>
                <td class="align-middle"><img class="cart-thumbnail" src="image/${product.imageId}"></td>
                <td class="align-middle"><a href="view?product=${product.id}">${product.name}</a></td>
                <td class="align-middle">${product.cost}</td>
                <td class="align-middle">${product.count}</td>
                <td class="align-middle">${product.count * product.cost}</td>
                <td class="align-middle">
                    <form method="POST" action="cart" class="margin: 0px; padding: 0px;">
                        <input type="hidden" name="csrf" value="${sessionScope.csrf}">
                        <div class="text-danger">
                            <input type="hidden" name="action" value="remove">
                            <input type="hidden" name="product" value="${product.id}">
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

<div class="row">
    <form method="POST" action="cart" class="col-5 offset-1 col-md-7 offset-md-1">
        <input type="hidden" name="csrf" value="${sessionScope.csrf}">
        <input type="hidden" name="action" value="order">
        <button class="btn btn-primary btn-block">Place Order</button>
    </form>

    <form method="POST" action="cart" class="col-5 col-md-3">
        <input type="hidden" name="csrf" value="${sessionScope.csrf}">
        <input type="hidden" name="action" value="clear">
        <button class="btn btn-danger btn-block">Clear Cart</button>
    </form>
</div>

<%@include file="footer.jsp" %>