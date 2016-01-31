<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tr class="list">
	<c:choose>
		<c:when test="${currLang!=null}">
			<td onclick="editor.itemLanguageTable.editEntry(this); return false;">${currLang}</td>
		</c:when>
		<c:otherwise>
			<td onclick="editor.itemLanguageTable.editEntry(this); return false;">~ New entry</td>
		</c:otherwise>
	</c:choose>
	<td class="nowrap">
		<button onclick="editor.itemLanguageTable.pushEntryUp(this); return false;" class="btn btn-xs btn-link btn-push-up"><span class="glyphicon glyphicon glyphicon-arrow-up" aria-hidden="true"></span></button>
		<button onclick="editor.itemLanguageTable.pushEntryDown(this); return false;" class="btn btn-xs btn-link btn-push-down"><span class="glyphicon glyphicon glyphicon-arrow-down" aria-hidden="true"></span></button>
		<button onclick="editor.itemLanguageTable.removeEntry(this); return false;" class="btn btn-xs btn-link"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
	</td>
</tr>
<tr class="edit" style="display: none;">
	<td colspan="2">
		<div class="form-group">
			<label for="title" class="col-sm-4 control-label">~Item Language</label>
			<div class="col-sm-4">
				<span class="attribute-name-helper">itemLanguages{}</span>
				<input type="text" class="form-control typeahead" id="itemLanguages${currIndex}" name="itemLanguages[${currIndex}]" value="<c:if test="${currDesc!=null}">${currLang}</c:if>" placeholder="~Language">
			</div>
		</div>
	</td>
</tr>