<%@include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="order" class="com.codingchili.webshoppe.model.Order" scope="session"/>

<div class="card row col-8 offset-2 margin-top">
    <c:if test="${!empty order.account}">

        <div class="panel panel-default margin-top-1">
            <div class="panel-heading">
                <h4>Shipping details</h4>
            </div>
            <div class="panel-body col-6 offset-3">
                <p>
                    <b>Username</b> ${sessionScope.account.username}
                </p>
                <p>
                    <b>ZIP</b> ${sessionScope.account.zip}
                </p>
                <p>
                    <b>Street</b> ${sessionScope.account.street}
                </p>
            </div>
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

        <div class="row">
            <h3 class="text-center">
                <span class="text-danger">Order total ${order.total} :-</span>
            </h3>
        </div>

        <div class="row">
            <div class="col-10 offset-1">
                <form method="POST" action="process">
                    <input type="hidden" name="action" value="getOrder">
                    <button class="btn btn-lg btn-primary btn-block">Next order</button>
                </form>
            </div>
        </div>
    </c:if>

    <c:if test="${empty order.account}">
        <h4>Manager instructions</h4>
        <p>
            Welcome Employee,

            Packing orders is easy,
            just follow these 3 simple steps

            1. Click the "Get order" button to get an order.
            2. Pack the items in the order, these are
            already allocated from the stock for you.
            3. Move the package to the shipping area.
        </p>
        <div class="row">
            <div class="col-10 offset-1">
                <form method="POST" action="process" class="col-12">
                    <input type="hidden" name="action" value="getOrder">
                    <button class="btn btn-lg btn-primary btn-block">Get order</button>
                </form>
            </div>
        </div>
    </c:if>
</div>

<%@include file="footer.jsp" %>