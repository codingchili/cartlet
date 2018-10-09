<jsp:useBean id="product" class="com.codingchili.webshoppe.model.Product" scope="session"/>
<%@include file="header.jsp" %>

<div class="margin-top card col-12">
    <a href="view?product=${product.id}" class="text-success edit-product-icon">
        <i class="fas fa-times"></i>
    </a>

    <form method="POST" action="edit">
        <input type="hidden" name="csrf" value="${sessionScope.csrf}">
        <input type="hidden" value="${product.id}" name="product">

        <div class="row">
            <h2 class="product-title text-center product-title col-10 col-md-6 offset-md-2">
                <input type="text" class="form-control" name="name" value="${product.name}">
            </h2>
        </div>

        <div class="row">
            <div id="drop-area" class="col-8 offset-2 col-md-6 offset-md-3 col-lg-3 offset-lg-2"
                 style="text-align: center;">
                <img src="image/${product.imageId}" id="product-image">
                <input type="hidden" id="upload-file" name="product-image" value="">
            </div>

            <div class="col-12 col-md-4 offset-md-2 col-xl-3 offset-xl-2">
                <h2><fmt:message key="description"/></h2>

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
                    <textarea style="height:156px;" class="form-control" rows="12"
                              name="description">${product.description}</textarea>
                </p>

            </div>
        </div>

        <div class="row">
            <div class="col-4 offset-4">
                <input type="text" class="form-control text-red" name="cost" value="${product.cost}">
            </div>
            <div class="" style="font-size: 22px;">
                <fmt:message key="currency"/>
            </div>
        </div>

        <div class="row buy-form">
            <div class="col-4 offset-4">
                <div class="form-group">
                    <label for="quantity" class="control-label"><fmt:message key="edit.stock"/> +/-</label>
                    <input type="text" class="form-control" id="quantity" name="quantity" value="0" autofocus="true">
                </div>
                <button class="btn btn-primary btn-block"><fmt:message key="edit.apply"/></button>
            </div>
        </div>
    </form>
</div>

<script src="js/imagedrop.js"></script>

<%@include file="footer.jsp" %>
