<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tr class="list" onclick="editor.editEntry(this); return false;">
	<c:choose>
		<c:when test="${currDesc!=null}">
			<td>${currDesc.title}</td>
			<td>${currDesc.languageId}</td>
			<td>?</td>
			<td>
				<button onclick="editor.removeEntry(this); return false;" class="btn btn-xs btn-link"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
			</td>
		</c:when>
		<c:otherwise>
			<td colspan="4">~ New entry</td>
		</c:otherwise>
	</c:choose>
</tr>
<tr class="edit" style="display: none;">
	<td colspan="4">
		<div class="form-group">
			<label for="title" class="col-sm-3 control-label">~Language</label>
			<div class="col-sm-3">
				<span class="attribute-name-helper">localizedDescriptions{}.languageId</span>
				<input type="text" class="form-control" id="localizedDescriptions${currIndex}.languageId" name="localizedDescriptions[${currIndex}].languageId" value="<c:if test="${currDesc!=null}">${currDesc.languageId}</c:if>" placeholder="~Language">
			</div>
			<div class="col-sm-6">
				<div class="checkbox">
					<span class="attribute-name-helper">localizedDescriptions{}.collectionDefault</span>				
					<input type="checkbox" id="localizedDescriptions${currIndex}.collectionDefault" name="localizedDescriptions[${currIndex}].collectionDefault"<c:if test="${currDesc!=null && currDesc.collectionDefault}"> checked="checked"</c:if> /> ~Collection Default
				</div>
			</div>
		</div>
		<div class="form-group">
			<label for="title" class="col-sm-3 control-label">~Title</label>
			<div class="col-sm-9">
				<span class="attribute-name-helper">localizedDescriptions{}.title</span>
				<input type="text" class="form-control"  id="localizedDescriptions${currIndex}.title" name="localizedDescriptions[${currIndex}].title" value="<c:if test="${currDesc!=null}">${currDesc.title}</c:if>" placeholder="~Title">
			</div>
		</div>
		<div class="form-group">
			<label for="title" class="col-sm-3 control-label">~Acronym</label>
			<div class="col-sm-9">
				<span class="attribute-name-helper">localizedDescriptions{}.acronym</span>
				<input type="text" class="form-control"  id="localizedDescriptions${currIndex}.acronym" name="localizedDescriptions[${currIndex}].acronym" value="<c:if test="${currDesc!=null}">${currDesc.acronym}</c:if>" placeholder="~Title">
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