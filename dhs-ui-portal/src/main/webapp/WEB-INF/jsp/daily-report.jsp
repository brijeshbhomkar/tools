<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<div class="container">
 <div class="panel panel-primary">
      <div class="panel-heading">Reporting</div>
      <div class="panel-body">
      <form:form method="post" action="/uploadFile" enctype="multipart/form-data">
          <div class="form-group">
            <label for="file">Upload File</label>
            <input type="file" name="file" class="form-control-file">
          </div>
           <button type="submit" class="btn btn-primary">Upload</button>
         </form:form>
        <div>
            ${message}
        </div>

        <form:form method="post" action="/readFile" enctype="multipart/form-data">
              <button type="submit" class="btn btn-success">Process</button>
        </form:form>
        </div>
</div>
<%@ include file="common/footer.jspf"%>