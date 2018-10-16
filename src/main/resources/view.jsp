<jsp:useBean id="product" class="com.codingchili.webshoppe.model.Product" scope="session"/>
<%@include file="header.jsp" %>

<div class="col-12 card info-container">

    <div class="row">
        <h3 class="product-title col-11 col-md-9 col-lg-9 offset-md-2 offset-lg-22">
            ${product.name}
        </h3>

        <c:if test="${!empty sessionScope.account && sessionScope.account.role.id > 1}">
            <a href="edit?product=${product.id}" class="text-success edit-product-icon">
                <i class="far fa-edit"></i>
            </a>
        </c:if>
    </div>

    <div class="row">

        <div class="col-12 col-md-8 offset-md-2 col-lg-4 offset-lg-2" style="text-align: center;">
            <img src="image/${product.imageId}" id="product-image">
        </div>

        <div class="col-12 col-md-10 offset-md-1 offset-lg-0 col-lg-5">
            <h5><fmt:message key="description"/></h5>

            <div class="stock-badge">
                <c:if test="${product.count > 0}">
                    <span class="badge badge-success">${product.count} <fmt:message key="view.in_stock"/></span>
                </c:if>

                <c:if test="${product.count < 1}">
                    <span class="badge badge-danger"><fmt:message key="view.out_of_stock"/></span>
                </c:if>
            </div>
            <p>
                ${product.description}
            </p>

        </div>
    </div>

    <div>
        <h6 class="text-center">
            <span class="text-warning">
                <fmt:formatNumber type="number" maxFractionDigits="2" value="${product.cost * currency_value}"/>
                <fmt:message key="currency"/>
            </span>
        </h6>
    </div>

    <div class="row buy-form">
        <div class="col-6 col-md-4 offset-3 offset-md-4">
            <form method="POST" action="buy">
                <input type="hidden" name="csrf" value="${sessionScope.csrf}">
                <div class="form-group">
                    <label for="quantity" class="control-label"><fmt:message key="product.quantity"/></label>
                    <input type="number" class="form-control" id="quantity" name="count" value="1" autofocus="true">
                    <input type="hidden" name="product" value="${product.id}">
                </div>

                <button class="btn btn-primary btn-block"><i class="fas fa-cart-plus"></i></button>
            </form>
        </div>
    </div>
</div>

<%@include file="footer.jsp" %>
