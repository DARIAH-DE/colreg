<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>


<s:bind path="${vocabularyIdentifier}[${currIndex}]">
	<li class="collection-editor-list-item form-item${status.error ? ' has-error' : ' '}">
		<div class="collection-editor-list-input col-sm-5">
			<span class="attribute-name-helper">${vocabularyIdentifier}</span>
			<span style="display: inline-block; width: 100%;">
				<select id="${vocabularyIdentifier}${currIndex}" name="${vocabularyIdentifier}[${currIndex}]" class="form-control">
					<option value="" disabled selected hidden></option>
					<c:forEach items="${availableVocabularyItems}" var="availableVocabularyItem">
						<option value="${availableVocabularyItem.identifier}" 
								${availableVocabularyItem.identifier==vocabularyModelItems[currIndex] ? "selected='selected'" : ""}
								${availableVocabularyItem.disabled ? "hidden" : ""}>
							${availableVocabularyItem.displayLabel} 
							<c:if test="${availableVocabularyItem.disabled}">[<s:message code="~eu.dariah.de.colreg.common.labels.disabled" />]</c:if>
						</option>
					</c:forEach>
				</select>
			</span>
		</div>
		<div class="collection-editor-list-item-buttons">
			<button onclick="editor.lists['${vocabularyIdentifier}'].pushEntryUp(this); return false;" class="btn btn-xs btn-link btn-push-up"><span class="glyphicon glyphicon glyphicon-arrow-up" aria-hidden="true"></span></button>
			<button onclick="editor.lists['${vocabularyIdentifier}'].pushEntryDown(this); return false;" class="btn btn-xs btn-link btn-push-down"><span class="glyphicon glyphicon glyphicon-arrow-down" aria-hidden="true"></span></button>
			<button onclick="editor.lists['${vocabularyIdentifier}'].removeEntry(this); return false;" class="btn btn-xs btn-link"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
		</div>
		<sf:errors element="div" cssClass="validation-error col-sm-12" path="${vocabularyIdentifier}[${currIndex}]" />
	</li>
</s:bind>