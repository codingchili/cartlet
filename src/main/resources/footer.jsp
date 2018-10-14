</div>

<hr style="margin-top: 6px;">

<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">
                    <fmt:message key="footer.contact.title"/>
                </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <fmt:message key="footer.contact.body"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">
                    <fmt:message key="footer.contact.close"/>
                </button>
            </div>
        </div>
    </div>
</div>

<div id="footer">
    <div id="footer-content" class="panel panel-default col-10 offset-1">
        <div>
            <a href="#exampleModal" data-toggle="modal" data-target="#exampleModal">
                <fmt:message key="footer.contact"/>
            </a>
        </div>

        <div>
            <fmt:message key="footer.text"/>
        </div>

        <div>
            [
            <form method="POST" action="language" class="footer-form">
                <button type="submit" class="btn btn-link inline-button"><fmt:message
                        key="footer.language.en"/></button>
                <input type="hidden" name="csrf" value="${sessionScope.csrf}">
                <input type="hidden" name="callback"
                       value="${requestScope["javax.servlet.forward.request_uri"]}?${requestScope["javax.servlet.forward.query_string"]}">
                <input type="hidden" name="language" value="en">
            </form>
            |
            <form method="POST" action="language" class="footer-form">
                <button type="submit" class="btn btn-link inline-button"><fmt:message
                        key="footer.language.sv"/></button>
                <input type="hidden" name="csrf" value="${sessionScope.csrf}">
                <input type="hidden" name="callback"
                       value="${requestScope["javax.servlet.forward.request_uri"]}?${requestScope["javax.servlet.forward.query_string"]}">
                <input type="hidden" name="language" value="sv">
            </form>
            ]
        </div>
    </div>
</div>
</body>
</html>