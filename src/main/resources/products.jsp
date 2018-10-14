<jsp:useBean id="products" class="com.codingchili.webshoppe.model.Product" scope="session"/>
<%@include file="header.jsp" %>


<div class="col-12 card product-container">
    <c:forEach items="${products}" var="product">
        <div class="panel panel-default product col-6 col-sm-6 col-md-4 col-lg-3">
            <div class="product-header">${product.name}</div>
            <div class="panel-body">
                <a href="view?product=${product.id}">
                    <img src="image/${product.imageId}" class="product-thumbnail-img">
                </a>
                <h6 class="text-warning product-thumbnail-price">
                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${product.cost * currency_value}"/>
                    <fmt:message key="currency"/>
                </h6>
            </div>

            <div class="product-footer">
                <form method="POST" action="buy">
                    <input type="hidden" name="csrf" value="${sessionScope.csrf}">
                    <input type="hidden" name="count" value="1">
                    <input type="hidden" name="product" value="${product.id}">
                    <button class="btn btn-primary btn-block">
                        <i class="fas fa-cart-plus"></i>
                    </button>
                </form>
            </div>
        </div>
    </c:forEach>
</div>

<%@include file="footer.jsp" %>
