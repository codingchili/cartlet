<%@include file="header.jsp" %>

<div class="row">
    <h3 style="text-align: center;">
        <span class="text-red">
           Order total ${sessionScope.cart.totalCost} :-
        </span>
    </h3>
</div>

<div class="row">
    <table class="table table-striped table-hover ">
        <thead>
        <tr>
            <th>Thumbnail</th>
            <th>Name</th>
            <th>Each</th>
            <th>Quantity</th>
            <th>Total</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${sessionScope.cart.items}" var="product">
            <tr>
                <td><img class="cart-thumbnail" src="image?id=${product.frontImage}"></td>
                <td><a href="view?product=${product.id}">${product.name}</a></td>
                <td>${product.cost}</td>
                <td>${product.count}</td>
                <td>${product.count * product.cost}</td>
                <td>
                    <form method="POST" action="cart" class="margin: 0px; padding: 0px;">
                        <div class="text-danger">
                            <input type="hidden" name="action" value="remove">
                            <input type="hidden" name="product" value="${product.id}">
                            <button type="submit" class="btn text-danger"><span
                                    class="glyphicon glyphicon-remove"></span></button>
                        </div>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<div class="row">
    <form method="POST" action="cart" class="col-xs-6 col-xs-offset-2">
        <input type="hidden" name="action" value="order">
        <button class="btn btn-primary btn-block">Place Order</button>
    </form>

    <form method="POST" action="cart" class="col-xs-2 col-xs-offset-0">
        <input type="hidden" name="action" value="clear">
        <button class="btn btn-danger btn-block">Clear Cart</button>
    </form>
</div>

<%@include file="footer.jsp" %>