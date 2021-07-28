<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<div class="container">
 <div class="panel panel-primary">
      <div class="panel-heading">Download</div>
      <div class="panel-body">
      <form:form method="post" action="/download/filter" modelAttribute="filteroption">
        <fieldset class="form-group">
                           <form:label path="startDate">Start Date:</form:label>
                           <form:input path="startDate" type="datetime-local" class="form-control"
                               required="required" />
                           <form:errors path="startDate" cssClass="text-warning" />
                       </fieldset>
                       <fieldset class="form-group">
                           <form:label path="endDate">End Date:</form:label>
                           <form:input path="endDate" type="datetime-local" class="form-control"
                               required="required" />
                           <form:errors path="endDate" cssClass="text-warning" />
                       </fieldset>
                        <fieldset class="form-group">
                             <form:label path="selectedOption">Select Option:</form:label>
                             <form:select path="selectedOption">
                                 <form:options items="${optionList}" />
                             </form:select>
                             <form:errors path="selectedOption" cssClass="text-warning" />
                             </fieldset>

                        <button type="submit" class="btn btn-success">Submit</button>
        </form:form>
      </div>
</div>
<%@ include file="common/footer.jspf"%>