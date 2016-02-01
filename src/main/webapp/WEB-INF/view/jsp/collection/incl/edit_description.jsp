<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tr class="list">
	<td class="description_title" onclick="editor.descriptionTable.editEntry(this); return false;">
		<c:choose>
			<c:when test="${currDesc!=null}">${currDesc.title}</c:when>
			<c:otherwise>~ New entry</c:otherwise>
		</c:choose>
	</td>
	<td class="description_acronym" onclick="editor.descriptionTable.editEntry(this); return false;" class="nowrap">
		<c:if test="${currDesc!=null}">${currDesc.acronym}</c:if>
	</td>
	<td class="description_language" onclick="editor.descriptionTable.editEntry(this); return false;" class="nowrap">
		<c:if test="${currDesc!=null}">${currDesc.languageId}</c:if>
	</td>
	<td class="nowrap">
		<button onclick="editor.descriptionTable.pushEntryUp(this); return false;" class="btn btn-xs btn-link btn-push-up"><span class="glyphicon glyphicon glyphicon-arrow-up" aria-hidden="true"></span></button>
		<button onclick="editor.descriptionTable.pushEntryDown(this); return false;" class="btn btn-xs btn-link btn-push-down"><span class="glyphicon glyphicon glyphicon-arrow-down" aria-hidden="true"></span></button>
		<button onclick="editor.descriptionTable.removeEntry(this); return false;" class="btn btn-xs btn-link"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
	</td>
</tr>
<tr class="edit" style="display: none;">
	<td colspan="4">
		<div class="form-group">
			<label for="title" class="col-sm-3 control-label">~Language</label>
			<div class="col-sm-4">
				<span class="attribute-name-helper">localizedDescriptions{}.languageId</span>
				<input type="text" class="form-control typeahead" 
					onchange="editor.descriptionTable.handleInputChange(this, 'description_language');" 
					onkeyup="editor.descriptionTable.handleInputChange(this, 'description_language');" 
					id="localizedDescriptions${currIndex}.languageId" name="localizedDescriptions[${currIndex}].languageId" 
					value="<c:if test="${currDesc!=null}">${currDesc.languageId}</c:if>" placeholder="~Language">
			</div>
		</div>
		<div class="form-group">
			<label for="title" class="col-sm-3 control-label">~Title</label>
			<div class="col-sm-9">
				<span class="attribute-name-helper">localizedDescriptions{}.title</span>
				<input type="text" class="form-control" 
					onchange="editor.descriptionTable.handleInputChange(this, 'description_title');" 
					onkeyup="editor.descriptionTable.handleInputChange(this, 'description_title');"
					id="localizedDescriptions${currIndex}.title" name="localizedDescriptions[${currIndex}].title" 
					value="<c:if test="${currDesc!=null}">${currDesc.title}</c:if>" placeholder="~Title">
			</div>
		</div>
		<div class="form-group">
			<label for="title" class="col-sm-3 control-label">~Acronym</label>
			<div class="col-sm-9">
				<span class="attribute-name-helper">localizedDescriptions{}.acronym</span>
				<input type="text" class="form-control"
					onchange="editor.descriptionTable.handleInputChange(this, 'description_acronym');" 
					onkeyup="editor.descriptionTable.handleInputChange(this, 'description_acronym');" 
					id="localizedDescriptions${currIndex}.acronym" name="localizedDescriptions[${currIndex}].acronym" 
					value="<c:if test="${currDesc!=null}">${currDesc.acronym}</c:if>" placeholder="~Title">
			</div>
		</div>
		<div class="form-group">
			<label for="description" class="col-sm-3 control-label">~Description</label>
			<div class="col-sm-9">
				<span class="attribute-name-helper">localizedDescriptions{}.description</span>
				<textarea class="form-control" rows="3" id="localizedDescriptions${currIndex}.description" name="localizedDescriptions[${currIndex}].description" placeholder="~Description"><c:if test="${currDesc!=null}">${currDesc.description}</c:if></textarea>
			</div>
		</div>
		<div class="form-group">
			<label for="audience" class="col-sm-3 control-label">~Audience</label>
			<div class="col-sm-9">
				<span class="attribute-name-helper">localizedDescriptions{}.audience</span>
				<input type="text" class="form-control" id="localizedDescriptions${currIndex}.audience" name="localizedDescriptions[${currIndex}].audience" value="<c:if test="${currDesc!=null}">${currDesc.audience}</c:if>" placeholder="~Audience">
			</div>
		</div>
		<div class="form-group">
			<label for="provenance" class="col-sm-3 control-label">~Provenance</label>
			<div class="col-sm-9">
				<span class="attribute-name-helper">localizedDescriptions{}.provenance</span>
				<input type="text" class="form-control" id="localizedDescriptions${currIndex}.provenance" name="localizedDescriptions[${currIndex}].provenance" value="<c:if test="${currDesc!=null}">${currDesc.provenance}</c:if>" placeholder="~Provenance">
			</div>
		</div>
	</td>
</tr>