<%@include file="header.jsp" %>

<div class="row">
    <div class="card margin-top col-8 offset-2 col-md-6 offset-md-0">
        <form method="POST" action="storage" class="form-horizontal">
            <input type="hidden" name="action" value="addProduct">

            <a href="storage"><span class="glyphicon glyphicon-remove text-red pull-right"></span></a>
            <fieldset>
                <legend>New Product</legend>
                <div class="form-group">
                    <label for="name" class="control-label">Name</label>

                    <input type="text" class="form-control" name="name" id="name" placeholder="Product name">
                </div>

                <div class="form-group">
                    <label for="description" class="control-label">Description</label>

                    <textarea class="form-control" rows="3" name="description" id="description"
                              placeholder="Product details.."></textarea>
                    <span class="help-block">Formatting with HTML is <span class="text-success">ENABLED</span>.</span>
                </div>
                <div class="form-group">
                    <label for="category" class="control-label">Category</label>

                    <select class="form-control" id="category" name="category">
                        <c:forEach items="${categories}" var="category">
                            <option value="${category.categoryId}">${category.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="cost" class="control-label">Cost</label>

                    <input type="text" name="cost" class="form-control" id="cost"
                           placeholder="Cost without concurrency unit">
                </div>

                <div class="form-group">
                    <label for="quantity" class="control-label">Count</label>

                    <input type="text" name="quantity" class="form-control" id="quantity"
                           placeholder="Number of items in stock">
                </div>

                <div class="form-group">
                    <button type="submit" class="btn btn-primary btn-block">Add</button>
                </div>
            </fieldset>
        </form>
        <div class="text-center">
            <span class="badge badge-success">Upload product image in product edit mode.</span>
        </div>
    </div>

    <div class="card col-8 offset-2 col-md-5 offset-md-1 margin-top">
        <form method="POST" action="storage" class="form-horizontal">
            <input type="hidden" name="action" value="addCategory">

            <a href="storage"><span class="glyphicon glyphicon-remove text-red pull-right"></span></a>
            <fieldset>
                <legend>New Category</legend>
                <div class="form-group">
                    <label for="name" class="control-label">Name</label>

                    <input type="text" class="form-control" id="category-name" name="name"
                           placeholder="Category name..">
                </div>

                <div class="form-group">
                    <button type="submit" class="btn btn-primary btn-block">Add</button>
                </div>
            </fieldset>
        </form>
    </div>

</div>

<%@include file="footer.jsp" %>