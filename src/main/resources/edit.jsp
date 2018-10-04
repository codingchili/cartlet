<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="product" class="com.codingchili.webshoppe.model.Product" scope="session"/>
<%@include file="header.jsp" %>

<form method="POST" action="edit">
    <input type="hidden" value="${product.id}" name="product">

    <div class="col-xs-12 well">
            <a href="view?product=${product.id}">
                <span class="glyphicon glyphicon-backward pull-right text-success"></span>
            </a>

        <div class="row">
            <h1 class="col-xs-8 col-sm-4 col-sm-offset-1 col-lg-offset-2">
                <input type="text" class="form-control" name="name" value="${product.name}">
            </h1>
        </div>

        <div class="row">
            <div id="drop-area" class="col-xs-3 col-xs-offset-2 col-sm-5 col-sm-offset-1 col-lg-3 col-lg-offset-2">
                <img src="image?id=${product.frontImage}" id="product-image">
                <input type="hidden" id="upload-file" name="product-image" value="">
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
                    <textarea class="form-control" rows="12" name="description">${product.description}</textarea>
                </p>

            </div>
        </div>

        <div class="row margin-top-1">
            <div class="col-xs-4 col-xs-offset-4 col-sm-2 col-sm-offset-5">
                <input type="text" class="form-control text-red" name="cost" value="${product.cost}">
            </div>
            <div class="" style="font-size: 22px;">
               :-
            </div>
        </div>

        <div class="row buy-form">
            <div class="row">
                <div class="col-xs-6 col-md-4 col-xs-offset-3 col-md-offset-4">
                    <div class="form-group">
                        <label for="quantity" class="col-lg-6 control-label">Modify stock +/-</label>
                        <input type="text" class="form-control" id="quantity" name="quantity" value="0" autofocus="true">
                    </div>

                    <button class="btn btn-primary btn-block">Apply</button>
                </div>
            </div>
        </div>
    </div>

</form>

<script src="js/imagedrop.js"></script>

<%@include file="footer.jsp" %>
