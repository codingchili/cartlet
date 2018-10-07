<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="product" class="com.codingchili.webshoppe.model.Product" scope="session"/>
<%@include file="header.jsp" %>

<form method="POST" action="edit">
    <input type="hidden" name="csrf" value="${sessionScope.csrf}">
    <input type="hidden" value="${product.id}" name="product">

    <div class="col-12 card margin-top">
        <a href="view?product=${product.id}" class="text-success edit-product-icon">
            <i class="fas fa-times"></i>
        </a>

        <div class="row">
            <h1 class="offset-md-1 offset-xl-2">
                <input type="text" class="form-control" name="name" value="${product.name}">
            </h1>
        </div>

        <div class="row">
            <div id="drop-area" class="col-8 offset-2 col-md-6 offset-md-3 col-lg-3 offset-lg-2">
                <img src="image/${product.imageId}" id="product-image">
                <input type="hidden" id="upload-file" name="product-image" value="">
            </div>

            <div class="col-12 col-md-4 offset-md-2 col-xl-3 offset-xl-2">
                <h2>Description</h2>

                <p>

                <div class="">
                    <c:if test="${product.count > 0}">
                        <span class="badge badge-success">${product.count} in stock.</span>
                    </c:if>

                    <c:if test="${product.count < 1}">
                        <span class="badge badge-danger">Out of stock</span>
                    </c:if>
                </div>
                </p>

                <p>
                    <textarea style="height:156px;" class="form-control" rows="12" name="description">${product.description}</textarea>
                </p>

            </div>
        </div>

        <div class="row">
            <div class="col-4 offset-2 col-sm-2 offset-sm-5">
                <input type="text" class="form-control text-red" name="cost" value="${product.cost}">
            </div>
            <div class="" style="font-size: 22px;">
                :-
            </div>
        </div>

        <div class="row buy-form">
            <div class="col-6 col-md-4 offset-3 offset-md-4">
                <div class="form-group">
                    <label for="quantity" class="control-label">Stock +/-</label>
                    <input type="text" class="form-control" id="quantity" name="quantity" value="0" autofocus="true">
                </div>
                <button class="btn btn-primary btn-block">Apply</button>
            </div>
        </div>
    </div>

</form>

<script src="js/imagedrop.js"></script>

<%@include file="footer.jsp" %>
