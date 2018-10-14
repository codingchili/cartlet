<jsp:useBean id="product" class="com.codingchili.webshoppe.model.Product" scope="session"/>
<%@include file="header.jsp" %>

<script>
    function showFileDialog() {
        document.getElementById('upload-file-dialog').click();
    }
</script>

<div class="margin-top card col-12">
    <form method="POST" action="edit">
        <input type="hidden" name="csrf" value="${sessionScope.csrf}">
        <input type="hidden" value="${product.id}" name="product">

        <div class="row">
            <h3 class="product-title col-11 col-md-9 col-lg-9 offset-md-2 offset-lg-2">
                <input type="text" class="form-control" name="name" value="${product.name}">
            </h3>
            <a href="view?product=${product.id}" class="text-success edit-product-icon">
                <i class="fas fa-times"></i>
            </a>
        </div>

        <div class="row">
            <div id="drop-area" class="col-12 col-md-8 offset-md-2 col-lg-4 offset-lg-2"
                 style="text-align: center;">

                <img src="image/${product.imageId}" id="product-image" class="product-image-edit" onclick="showFileDialog()">

                <input type="hidden" class="file-input-hidden" id="upload-file" name="product-image" value="">
                <input type="file" class="file-input-hidden" id="upload-file-dialog" name="product-image" value="">
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
                    <textarea style="height:156px;" class="form-control" rows="12"
                              name="description">${product.description}</textarea>
                </p>

            </div>
        </div>

        <div class="row">
            <div class="col-4 offset-4">
                <input type="text" class="form-control text-warning" name="cost" value="${product.cost}">
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
