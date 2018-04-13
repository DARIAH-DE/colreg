<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<s:bind path="collectionTypes[${currIndex}]">
	<li class="collection-editor-list-item form-item${status.error ? ' has-error' : ' '}">
		<div class="collection-editor-list-input col-sm-5">
			<span class="attribute-name-helper">collectionTypes{}</span>
			<span style="display: inline-block; width: 100%;">
				<select id="collectionTypes${currIndex}" name="collectionTypes[${currIndex}]" class="form-control">
					<option value="" disabled selected hidden></option>
					<c:forEach items="${vocabularyItems}" var="vocabularyItem">
						<option value="${vocabularyItem.identifier}" ${vocabularyItem.identifier==collection.collectionTypes[currIndex] ? "selected='selected'" : ""}>${vocabularyItem.displayLabel}</option>
					</c:forEach>
				</select>
			</span>
		</div>
		<div class="collection-editor-list-item-buttons">
			<button onclick="editor.lists['collectionTypes'].pushEntryUp(this); return false;" class="btn btn-xs btn-link btn-push-up"><span class="glyphicon glyphicon glyphicon-arrow-up" aria-hidden="true"></span></button>
			<button onclick="editor.lists['collectionTypes'].pushEntryDown(this); return false;" class="btn btn-xs btn-link btn-push-down"><span class="glyphicon glyphicon glyphicon-arrow-down" aria-hidden="true"></span></button>
			<button onclick="editor.lists['collectionTypes'].removeEntry(this); return false;" class="btn btn-xs btn-link"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
		</div>
		<sf:errors element="div" cssClass="validation-error col-sm-12" path="collectionTypes[${currIndex}]" />
	</li>
</s:bind>