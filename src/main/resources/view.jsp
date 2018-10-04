<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="product" class="com.codingchili.webshoppe.model.Product" scope="session"/>
<%@include file="header.jsp" %>

<div class="col-xs-12 well">
    <c:if test="${!empty sessionScope.account && sessionScope.account.role.id > 1}">
        <a href="edit?product=${product.id}">
            <span class="glyphicon glyphicon-edit pull-right text-success"></span>
        </a>
    </c:if>

    <div class="row">
        <h1 class="col-sm-offset-1 col-lg-offset-2">
            ${product.name}
        </h1>

        <div class="col-xs-3 col-xs-offset-2 col-sm-5 col-sm-offset-1 col-lg-3 col-lg-offset-2">
            <img src="image?id=${product.frontImage}" id="product-image">
        </div>

        <div class="col-xs-12 col-sm-5 col-sm-offset-1 col-lg-3 col-lg-offset-2">
            <h2>Description</h2>

            <p>

            <div class="">
                <c:if test="${product.count > 0}">
                    <span class="label label-success">${product.count} in stock.</span>
                </c:if>

                <c:if test="${product.count < 1}">
                    <span class="label label-danger">Out of stock</span>
                </c:if>
            </div>
            </p>

            <p>
                ${product.description}
            </p>

        </div>
    </div>

    <div class="row">
        <h2 class="text-center">
            <span class="text-red">${product.cost}:-</span>
        </h2>
    </div>

    <div class="row buy-form">
        <div class="row">
            <div class="col-xs-6 col-md-4 col-xs-offset-3 col-md-offset-4">
                <form method="POST" action="buy">
                    <div class="form-group">
                        <label for="quantity" class="col-lg-2 control-label">Quantity</label>
                        <input type="text" class="form-control" id="quantity" name="count" value="1" autofocus="true">
                        <input type="hidden" name="product" value="${product.id}">
                    </div>

                    <button class="btn btn-primary btn-block">Buy</button>
                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="footer.jsp" %>
