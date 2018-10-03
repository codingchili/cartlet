<%@include file="header.jsp" %>
<jsp:useBean id="order" class="com.codingchili.webshoppe.model.Order" scope="session"/>

<div class="row">
    <h3 style="text-align: center;">
        <span class="text-red">
            Total ${order.total}:-
        </span>
    </h3>
</div>

<div class="row">
    <table class="table table-striped table-hover ">
        <thead>
        <tr>
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
                <td>${product.name}</td>
                <td>${product.cost}</td>
                <td>${product.count}</td>
                <td><a href="view?product=${product.id}">View</a></td>
                <td class="text-red">${product.count * product.cost}:-</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<div class="row">
    <h3 class="text-center">
    <span class="label label-success">
        Status: ${order.status}
    </span>
    </h3>
</div>


<%@include file="footer.jsp" %>