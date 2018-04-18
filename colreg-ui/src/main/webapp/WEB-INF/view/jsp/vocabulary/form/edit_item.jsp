<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<s:url value="${actionPath}" var="saveUrl" />
<sf:form method="POST" action="${saveUrl}" cssClass="form-horizontal" modelAttribute="vocabularyItem">
	<div class="form-header">
		<h2 id="form-header-title">
			${vocabulary.localizedLabel}
			<small>
				<c:choose>
					<c:when test="${vocabularyItem.id=='new'}">
						<s:message code="~eu.dariah.de.colreg.common.labels.new_entry" />
					</c:when>
					<c:otherwise>
						${vocabularyItem.identifier}
					</c:otherwise>
				</c:choose>
			</small>
		</h2>
		<input type="hidden" id="id" name="id" value="${vocabularyItem.id}" />
		<input type="hidden" id="vocabularyIdentifier" name="vocabularyIdentifier" value="${vocabularyItem.vocabularyIdentifier}" />
	</div>
	<div class="form-content">
		<fieldset>
			<div class="form-group">
				<label class="control-label col-sm-4" for="vocabularyItem_identifier"><s:message code="~eu.dariah.de.colreg.model.vocabulary_item.identifier" />:</label>
				<div class="col-sm-8">
					<sf:input path="identifier" class="form-control" id="vocabularyItem_identifier" />
					<sf:errors path="identifier" cssClass="error" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-4" for="vocabularyItem_disabled"><s:message code="~eu.dariah.de.colreg.common.labels.disabled" />:</label>
				<div class="col-sm-8">
					<sf:checkbox path="disabled" />
					<div class="alert alert-info alert-sm pull-right"><s:message code="~eu.dariah.de.colreg.view.vocabulary_item.labels.hint_disabled" /></div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-4" for="vocabularyItem_defaultName"><s:message code="~eu.dariah.de.colreg.model.vocabulary_item.default_name" />:</label>
				<div class="col-sm-8">
					<sf:input path="defaultName" class="form-control" id="vocabularyItem_defaultName" />
					<sf:errors path="defaultName" cssClass="error" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-8 col-sm-offset-4">
					<div class="alert alert-info alert-sm"><s:message code="~eu.dariah.de.colreg.view.vocabulary_item.labels.hint_translations" /></div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-4" for="vocabularyItem_identifier"><s:message code="~eu.dariah.de.colreg.model.vocabulary_item.external_identifier" />:</label>
				<div class="col-sm-8">
					<sf:input path="externalIdentifier" class="form-control" id="vocabularyItem_externalIdentifier" />
					<sf:errors path="externalIdentifier" cssClass="error" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-8 col-sm-offset-4">
					<div class="alert alert-info alert-sm"><s:message code="~eu.dariah.de.colreg.view.vocabulary_item.labels.hint_external_identifier" /></div>
				</div>
			</div>
		</fieldset>
	</div>
	<div class="form-footer control-group">
		<div class="controls">
			<button class="btn cancel form-btn-cancel" type="reset"><i class="icon-ban-circle icon-black"></i><span><s:message code="~eu.dariah.de.colreg.common.actions.cancel" /></span></button>
			<button class="btn btn-primary start form-btn-submit" type="submit"><i class="icon-upload icon-white"></i><span><s:message code="~eu.dariah.de.colreg.common.actions.save" /></span></button>
		</div>
	</div>
</sf:form>