<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<div class="container">
 <div class="panel panel-primary">
      <div class="panel-heading">Login CVAnalytics</div>
      <div class="panel-body">
                <form:form method="post" action="/user/validate" modelAttribute="user">
                       <fieldset class="form-group">
                           <form:label path="username">Username</form:label>
                           <form:input path="username" type="text" class="form-control"
                               required="required" />
                           <form:errors path="username" cssClass="text-warning" />
                       </fieldset>
                       <fieldset class="form-group">
                           <form:label path="password">Password</form:label>
                           <form:input path="password" type="password" class="form-control"
                               required="required" />
                           <form:errors path="password" cssClass="text-warning" />
                       </fieldset>
                       <button type="submit" class="btn btn-success">Submit</button>
                   </form:form>
</div>
</div>
<%@ include file="common/footer.jspf"%>