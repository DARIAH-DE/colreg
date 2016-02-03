<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<li class="collection-editor-list-item">
	<div class="collection-editor-list-input col-sm-5">
		<span class="attribute-name-helper">itemLanguages{}</span>
		<input type="text" class="form-control typeahead language-typeahead" id="itemLanguages${currIndex}" name="itemLanguages[${currIndex}]" 
			value="<c:if test="${currLang!=null}">${currLang}</c:if>" placeholder="~Language">
	</div>
	<div class="collection-editor-list-item-buttons">
		<button onclick="editor.itemLanguageList.pushEntryUp(this); return false;" class="btn btn-xs btn-link btn-push-up"><span class="glyphicon glyphicon glyphicon-arrow-up" aria-hidden="true"></span></button>
		<button onclick="editor.itemLanguageList.pushEntryDown(this); return false;" class="btn btn-xs btn-link btn-push-down"><span class="glyphicon glyphicon glyphicon-arrow-down" aria-hidden="true"></span></button>
		<button onclick="editor.itemLanguageList.removeEntry(this); return false;" class="btn btn-xs btn-link"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
	</div>
</li>