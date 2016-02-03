<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<li class="collection-editor-list-item">
	<div class="collection-editor-list-input col-sm-5">
		<span class="attribute-name-helper">accessMethods{}.schemeIds</span>
		<span style="display: inline-block;">
			<input type="text" class="form-control typeahead encoding-scheme-typeahead" 
				value="<c:if test="${currSchemeId!=null}">${currSchemeId}</c:if>" placeholder="~Scheme Id">
		</span>
	</div>
	<div class="collection-editor-list-item-buttons">
		<button onclick="editor.accessMethodTable.schemesList.pushEntryUp(this); return false;" class="btn btn-xs btn-link btn-push-up"><span class="glyphicon glyphicon glyphicon-arrow-up" aria-hidden="true"></span></button>
		<button onclick="editor.accessMethodTable.schemesList.pushEntryDown(this); return false;" class="btn btn-xs btn-link btn-push-down"><span class="glyphicon glyphicon glyphicon-arrow-down" aria-hidden="true"></span></button>
		<button onclick="editor.accessMethodTable.schemesList.removeEntry(this); return false;" class="btn btn-xs btn-link"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
	</div>
</li>