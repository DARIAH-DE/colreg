<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tr class="list">
	<c:choose>
		<c:when test="${currDesc!=null}">
			<td>${currDesc.title}</td>
			<td>${currDesc.lang}</td>
			<td>?</td>
		</c:when>
		<c:otherwise>
			<td colspan="3">~ New entry</td>
		</c:otherwise>
	</c:choose>
</tr>
<tr>
	<td colspan="3">
		<div class="form-group">
			<label for="title" class="col-sm-3 control-label">~Language</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" id="localizedDescriptions${currIndex}.lang" name="localizedDescriptions[${currIndex}].lang" value="<c:if test="${currDesc!=null}">${currDesc.lang}</c:if>" placeholder="~Language">
			</div>
		</div>
		<div class="form-group">
			<label for="title" class="col-sm-3 control-label">~Title</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" id="localizedDescriptions${currIndex}.title" name="localizedDescriptions[${currIndex}].title" value="<c:if test="${currDesc!=null}">${currDesc.title}</c:if>" placeholder="~Title">
			</div>
		</div>
		<div class="form-group">
			<label for="description" class="col-sm-3 control-label">~Description</label>
			<div class="col-sm-9">
				<textarea class="form-control" rows="3" id="localizedDescriptions${currIndex}.description" name="localizedDescriptions[${currIndex}].description" placeholder="~Description">
					<c:if test="${currDesc!=null}">${currDesc.description}</c:if>
				</textarea>
			</div>
		</div>
		<div class="form-group">
			<label for="audience" class="col-sm-3 control-label">~Audience</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" id="localizedDescriptions${currIndex}.audience" name="localizedDescriptions[${currIndex}].audience" value="<c:if test="${currDesc!=null}">${currDesc.audience}</c:if>" placeholder="~Audience">
			</div>
		</div>
		<div class="form-group">
			<label for="provenance" class="col-sm-3 control-label">~Provenance</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" id="localizedDescriptions${currIndex}.provenance" name="localizedDescriptions[${currIndex}].provenance" value="<c:if test="${currDesc!=null}">${currDesc.provenance}</c:if>" placeholder="~Provenance">
			</div>
		</div>
	</td>
</tr>