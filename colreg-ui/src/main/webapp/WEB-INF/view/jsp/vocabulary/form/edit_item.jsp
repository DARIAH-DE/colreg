<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<s:url value="${actionPath}" var="saveUrl" />
<form method="POST" action="${saveUrl}" class="form-horizontal" id="frm-add-user-collections">
	<div class="form-header">
		<h2 id="form-header-title">
			~fafa
			<small>~fufu</small>
		</h2>
		<input type="hidden" id="id" name="id" value="${vocabularyItem.id}" />
		<input type="hidden" id="vocabularyId" name="vocabularyId" value="${vocabularyItem.vocabularyId}" />
	</div>
	<div class="form-content">
			
	</div>
	<div class="form-footer control-group">
		<div class="controls">
			<button class="btn cancel form-btn-cancel" type="reset"><i class="icon-ban-circle icon-black"></i><span><s:message code="~eu.dariah.de.minfba.common.actions.cancel" /></span></button>
			<button id="btn-add-user-collections-submit" disabled="disabled" class="btn btn-primary start form-btn-submit" type="submit"><i class="icon-upload icon-white"></i><span><s:message code="~eu.dariah.de.minfba.common.actions.add" /></span></button>
		</div>
	</div>
</form>



