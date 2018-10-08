<jsp:useBean id="product" class="com.codingchili.webshoppe.model.Product" scope="session"/>
<%@include file="header.jsp" %>

<div class="col-12 card info-container">

    <c:if test="${!empty sessionScope.account && sessionScope.account.role.id > 1}">
        <a href="edit?product=${product.id}" class="text-success edit-product-icon">
            <i class="far fa-edit"></i>
        </a>
    </c:if>

    <div class="row">
        <h2 class="product-title offset-md-1 offset-xl-2">
            ${product.name}
        </h2>
    </div>

    <div class="row">

        <div class="col-12 col-md-6 offset-md-3 col-lg-3 offset-lg-2" style="text-align: center;">
            <img src="image/${product.imageId}" id="product-image">
        </div>

        <div class="col-12 col-md-4 offset-md-2 col-xl-3 offset-xl-2">
            <h4><fmt:message key="description"/></h4>

            <p>
            <div class="">
                <c:if test="${product.count > 0}">
                    <span class="badge badge-success">${product.count} <fmt:message key="view.in_stock"/></span>
                </c:if>

                <c:if test="${product.count < 1}">
                    <span class="badge badge-danger"><fmt:message key="view.out_of_stock"/></span>
                </c:if>
            </div>
            </p>
            <p>
                ${product.description}
            </p>

        </div>
    </div>

    <div>
        <h3 class="text-center" style="width:100%;">
            <span class="text-red">
                <fmt:formatNumber type="number" maxFractionDigits="2" value="${product.cost * currency_value}"/>
                <fmt:message key="currency"/>
            </span>
        </h3>
    </div>

    <div class="row buy-form">
        <div class="col-6 col-md-4 offset-3 offset-md-4">
            <form method="POST" action="buy">
                <input type="hidden" name="csrf" value="${sessionScope.csrf}">
                <div class="form-group">
                    <label for="quantity" class="control-label">Quantity</label>
                    <input type="text" class="form-control" id="quantity" name="count" value="1" autofocus="true">
                    <input type="hidden" name="product" value="${product.id}">
                </div>

                <button class="btn btn-primary btn-block"><i class="fas fa-cart-plus"></i></button>
            </form>
        </div>
    </div>
</div>

<%@include file="footer.jsp" %>
