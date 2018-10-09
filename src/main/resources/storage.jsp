<%@include file="header.jsp" %>

<div class="row">
    <div class="card margin-top col-8 offset-2 col-md-6 offset-md-0">
        <form method="POST" action="storage" class="form-horizontal">
            <input type="hidden" name="csrf" value="${sessionScope.csrf}">
            <input type="hidden" name="action" value="addProduct">

            <a href="storage"><span class="glyphicon glyphicon-remove text-red pull-right"></span></a>
            <fieldset>
                <legend><fmt:message key="storage.new_product"/></legend>
                <div class="form-group">
                    <label for="name" class="control-label"><fmt:message key="storage.product_name"/></label>

                    <fmt:message key="storage.product_name.placeholder" var="productNamePlaceholder"/>
                    <input type="text" class="form-control" name="name" id="name" placeholder="${productNamePlaceholder}">
                </div>

                <div class="form-group">
                    <label for="description" class="control-label"><fmt:message key="description"/></label>

                    <fmt:message key="storage.product_details.placeholder" var="productDetailsPlaceholder"/>
                    <textarea class="form-control" rows="3" name="description" id="description"
                              placeholder="${productDetailsPlaceholder}"></textarea>

                    <span class="help-block"><fmt:message key="storage.formatting"/>
                        <span class="text-success">
                            <fmt:message key="storage.formatting_enabled"/>
                        </span>.
                    </span>
                </div>
                <div class="form-group">
                    <label for="category" class="control-label"><fmt:message key="storage.category"/></label>

                    <select class="form-control" id="category" name="category">
                        <c:forEach items="${categories}" var="category">
                            <option value="${category.categoryId}">${category.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="cost" class="control-label"><fmt:message key="storage.cost"/></label>

                    <fmt:message key="storage.product_cost.placeholder" var="productCostPlaceholder"/>
                    <input type="text" name="cost" class="form-control" id="cost"
                           placeholder="${productCostPlaceholder}">
                </div>

                <div class="form-group">
                    <label for="quantity" class="control-label"><fmt:message key="storage.count"/></label>

                    <fmt:message key="storage.product_quantity.placeholder" var="productQuantityPlaceholder"/>
                    <input type="text" name="quantity" class="form-control" id="quantity"
                           placeholder="${productQuantityPlaceholder}">
                </div>

                <div class="form-group">
                    <button type="submit" class="btn btn-primary btn-block"><fmt:message key="storage.add"/></button>
                </div>
            </fieldset>
        </form>
        <div class="text-center">
            <span class="badge badge-success"><fmt:message key="storage.upload_image"/></span>
        </div>
    </div>

    <div class="card col-8 offset-2 col-md-5 offset-md-1 margin-top">
        <form method="POST" action="storage" class="form-horizontal">
            <input type="hidden" name="csrf" value="${sessionScope.csrf}">
            <input type="hidden" name="action" value="addCategory">

            <a href="storage"><span class="glyphicon glyphicon-remove text-red pull-right"></span></a>
            <fieldset>
                <legend><fmt:message key="storage.new_category"/></legend>
                <div class="form-group">
                    <label for="name" class="control-label"><fmt:message key="storage.category_name"/></label>

                    <fmt:message key="storage.category_name.placeholder" var="categoryNamePlaceholder"/>
                    <input type="text" class="form-control" id="category-name" name="name"
                           placeholder="${categoryNamePlaceholder}">
                </div>

                <div class="form-group">
                    <button type="submit" class="btn btn-primary btn-block"><fmt:message key="storage.add"/></button>
                </div>
            </fieldset>
        </form>
    </div>

</div>

<%@include file="footer.jsp" %>