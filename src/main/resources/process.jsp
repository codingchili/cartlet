<%@include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="order" class="com.codingchili.webshoppe.model.Order" scope="session"/>

<div class="row col-xs-8 col-xs-offset-2">
    <c:if test="${!empty order.account}">

        <div class="panel panel-default margin-top-1">
            <div class="panel-heading">
                <h4>Shipping details</h4>
            </div>
            <div class="panel-body">
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
            <h3 style="text-align: center;">
        <span class="text-red">
           Order total ${order.total} :-
        </span>
            </h3>
        </div>

        <div class="row col-xs-10 col-xs-offset-1">
            <form method="POST" action="process">
                <input type="hidden" name="action" value="getOrder">
                <button class="btn btn-lg btn-primary btn-block">Next order</button>
            </form>
        </div>
    </c:if>

    <c:if test="${empty order.account}">
        <div class="well">
            <h4>Manager instructions</h4>

            <pre>
                Welcome Employee,

                Packing orders is easy,
                just follow these 3 simple steps

                1. Click the "Get order" button to get an order.
                2. Pack the items in the order, these are
                    already allocated from the stock for you.
                3. Move the package to the shipping area.
            </pre>
        </div>

        <div class="row col-xs-10 col-xs-offset-1">
            <form method="POST" action="process">
                <input type="hidden" name="action" value="getOrder">
                <button class="btn btn-lg btn-primary btn-block">Get order</button>
            </form>
        </div>
    </c:if>
</div>

<%@include file="footer.jsp" %>