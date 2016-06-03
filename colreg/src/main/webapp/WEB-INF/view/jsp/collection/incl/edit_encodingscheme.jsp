<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<s:bind path="accessMethods[${currIndex}].schemeIds[${subIndex}]">
	<li class="collection-editor-list-item form-item${status.error ? ' has-error' : ' '}">
		<div class="collection-editor-list-input col-sm-5">
			<span class="attribute-name-helper">accessMethods{}.schemeIds</span>
			<span style="display: inline-block; width: 100%;">
				<input type="text" class="form-control typeahead encoding-scheme-typeahead" 
					value="<c:if test="${currSchemeId!=null}">${currSchemeId}</c:if>" placeholder="<s:message code="~eu.dariah.de.colreg.view.collection.labels.type_to_search" />">
			</span>
		</div>
		<div class="collection-editor-list-item-buttons">
			<button onclick="editor.tables['accessMethodTable'].schemesList.pushEntryUp(this); return false;" class="btn btn-xs btn-link btn-push-up"><span class="glyphicon glyphicon glyphicon-arrow-up" aria-hidden="true"></span></button>
			<button onclick="editor.tables['accessMethodTable'].schemesList.pushEntryDown(this); return false;" class="btn btn-xs btn-link btn-push-down"><span class="glyphicon glyphicon glyphicon-arrow-down" aria-hidden="true"></span></button>
			<button onclick="editor.tables['accessMethodTable'].schemesList.removeEntry(this); return false;" class="btn btn-xs btn-link"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
		</div>
		<sf:errors element="div" cssClass="validation-error col-sm-12" path="accessMethods[${currIndex}].schemeIds[${subIndex}]" />
	</li>
</s:bind>