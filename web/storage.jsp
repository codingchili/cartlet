<%@include file="header.jsp" %>

<div class="row well col-xs-8 col-xs-offset-2 col-md-6 col-md-offset-0">
    <form method="POST" action="storage" class="form-horizontal">
        <input type="hidden" name="action" value="addProduct">

        <a href="storage"><span class="glyphicon glyphicon-remove text-red pull-right"></span></a>
        <fieldset>
            <legend>New Product</legend>
            <div class="form-group">
                <label for="name" class="col-lg-2 control-label">Name</label>

                <div class="col-lg-10">
                    <input type="text" class="form-control" name="name" id="name" placeholder="Product name">
                </div>
            </div>

            <div class="form-group">
                <label for="description" class="col-lg-2 control-label">Description</label>

                <div class="col-lg-10">
                    <textarea class="form-control" rows="3" name="description" id="description" placeholder="Product details.."></textarea>
                    <span class="help-block">Formatting with HTML is <span class="text-success">ENABLED</span>.</span>
                </div>
            </div>
            <div class="form-group">
                <label for="category" class="col-lg-2 control-label">Category</label>

                <div class="col-lg-10">
                    <select class="form-control" id="category" name="category">
                        <c:forEach items="${categories}" var="category">
                            <option value="${category.categoryId}">${category.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label for="cost" class="col-lg-2 control-label">Cost</label>

                <div class="col-lg-10">
                    <input type="text" name="cost" class="form-control" id="cost"
                           placeholder="Cost without concurrency unit">
                </div>

            </div>

            <div class="form-group">
                <label for="quantity" class="col-lg-2 control-label">Count</label>

                <div class="col-lg-10">
                    <input type="text" name="quantity" class="form-control" id="quantity"
                           placeholder="Number of items in stock">
                </div>
            </div>

            <div class="form-group">
                <div class="col-lg-10 col-lg-offset-2">
                    <button type="submit" class="btn btn-primary btn-block">Add</button>
                </div>
            </div>
        </fieldset>
    </form>
    <div class="text-center">
        <span class="label label-success">Drop an image over the existing to replace in product edit mode.</span>
    </div>
</div>


<div class="row well col-xs-8 col-xs-offset-2 col-md-5 col-md-offset-1">
    <form method="POST" action="storage" class="form-horizontal">
        <input type="hidden" name="action" value="addCategory">

        <a href="storage"><span class="glyphicon glyphicon-remove text-red pull-right"></span></a>
        <fieldset>
            <legend>New Category</legend>
            <div class="form-group">
                <label for="name" class="col-lg-2 control-label">Name</label>

                <div class="col-lg-10">
                    <input type="text" class="form-control" id="category-name" name="name" placeholder="Category name..">
                </div>
            </div>

            <div class="form-group">
                <div class="col-lg-10 col-lg-offset-2">
                    <button type="submit" class="btn btn-primary btn-block">Add</button>
                </div>
            </div>
        </fieldset>
    </form>
</div>

<%@include file="footer.jsp" %>