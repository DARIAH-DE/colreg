<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<s:bind path="localizedDescriptions[${currIndex}].*">
	<tr class="list${status.error ? ' has-error' : ' '}">
		<td class="description_title" onclick="editor.tables['descriptionTable'].editEntry(this); return false;">
			<c:choose>
				<c:when test="${currDesc!=null}">${currDesc.title}</c:when>
				<c:otherwise><s:message code="~eu.dariah.de.colreg.common.labels.new_entry" /></c:otherwise>
			</c:choose>
		</td>
		<td class="description_acronym" onclick="editor.tables['descriptionTable'].editEntry(this); return false;" class="nowrap">
			<c:if test="${currDesc!=null}">${currDesc.acronym}</c:if>
		</td>
		<td class="description_language" onclick="editor.tables['descriptionTable'].editEntry(this); return false;" class="nowrap">
			<c:if test="${currDesc!=null}">${currDesc.languageId}</c:if>
		</td>
		<td class="nowrap">
			<button onclick="editor.tables['descriptionTable'].pushEntryUp(this); return false;" class="btn btn-xs btn-link btn-push-up"><span class="glyphicon glyphicon glyphicon-arrow-up" aria-hidden="true"></span></button>
			<button onclick="editor.tables['descriptionTable'].pushEntryDown(this); return false;" class="btn btn-xs btn-link btn-push-down"><span class="glyphicon glyphicon glyphicon-arrow-down" aria-hidden="true"></span></button>
			<button onclick="editor.tables['descriptionTable'].removeEntry(this); return false;" class="btn btn-xs btn-link"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
		</td>
	</tr>
</s:bind>
<tr class="edit" style="display: none;">
	<td colspan="4">
		<s:bind path="localizedDescriptions[${currIndex}].languageId">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="title" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.localized_description.language" /></label>
				<div class="col-sm-4">
					<span class="attribute-name-helper">localizedDescriptions{}.languageId</span>
					<input type="text" class="form-control typeahead language-typeahead" 
						onchange="editor.tables['descriptionTable'].handleInputChange(this, 'description_language');" 
						onkeyup="editor.tables['descriptionTable'].handleInputChange(this, 'description_language');" 
						id="localizedDescriptions${currIndex}.languageId" name="localizedDescriptions[${currIndex}].languageId" 
						value="<c:if test="${currDesc!=null}">${currDesc.languageId}</c:if>" placeholder="<s:message code="~eu.dariah.de.colreg.view.collection.labels.type_to_search" />">
				</div>
				<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
					path="localizedDescriptions[${currIndex}].languageId" />
			</div>
		</s:bind>
		<s:bind path="localizedDescriptions[${currIndex}].title">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="title" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.localized_description.title" /></label>
				<div class="col-sm-9">
					<span class="attribute-name-helper">localizedDescriptions{}.title</span>
					<input type="text" class="form-control" 
						onchange="editor.tables['descriptionTable'].handleInputChange(this, 'description_title');" 
						onkeyup="editor.tables['descriptionTable'].handleInputChange(this, 'description_title');"
						id="localizedDescriptions${currIndex}.title" name="localizedDescriptions[${currIndex}].title" 
						value="<c:if test="${currDesc!=null}">${currDesc.title}</c:if>">
				</div>
				<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
					path="localizedDescriptions[${currIndex}].title" />
			</div>
		</s:bind>
		<s:bind path="localizedDescriptions[${currIndex}].acronym">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="title" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.localized_description.acronym" /></label>
				<div class="col-sm-9">
					<span class="attribute-name-helper">localizedDescriptions{}.acronym</span>
					<input type="text" class="form-control"
						onchange="editor.tables['descriptionTable'].handleInputChange(this, 'description_acronym');" 
						onkeyup="editor.tables['descriptionTable'].handleInputChange(this, 'description_acronym');" 
						id="localizedDescriptions${currIndex}.acronym" name="localizedDescriptions[${currIndex}].acronym" 
						value="<c:if test="${currDesc!=null}">${currDesc.acronym}</c:if>">
				</div>
				<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
					path="localizedDescriptions[${currIndex}].acronym" />
			</div>
		</s:bind>
		<s:bind path="localizedDescriptions[${currIndex}].description">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="description" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.localized_description.description" /></label>
				<div class="col-sm-9">
					<span class="attribute-name-helper">localizedDescriptions{}.description</span>
					<textarea class="form-control" rows="3" id="localizedDescriptions${currIndex}.description" name="localizedDescriptions[${currIndex}].description"><c:if test="${currDesc!=null}">${currDesc.description}</c:if></textarea>
				</div>
			</div>
		</s:bind>
	</td>
</tr>
