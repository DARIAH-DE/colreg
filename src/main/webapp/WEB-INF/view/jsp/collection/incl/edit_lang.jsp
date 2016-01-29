<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tr class="list">
	<c:choose>
		<c:when test="${currLang!=null}">
			<td>${currLang.title}</td>
			<td>${currLang.lang}</td>
		</c:when>
		<c:otherwise>
			<td colspan="2"><button class="btn btn-sm btn-primary pull-right">~Add lang...</button></td>
		</c:otherwise>
	</c:choose>
</tr>
<tr style="display: none;">
	<td colspan="2">
		<div class="form-group">
			<label for="title" class="col-sm-3 control-label">~Language</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" id="langs${currIndex}.lang" name="langs[${currIndex}].lang" value="<c:if test="${currLang!=null}">${currLang.lang}</c:if>" placeholder="~Language">
			</div>
		</div>
		<div class="form-group">
			<label for="title" class="col-sm-3 control-label">~Title</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" id="langs${currIndex}.title" name="langs[${currIndex}].title" value="<c:if test="${currLang!=null}">${currLang.title}</c:if>" placeholder="~Title">
			</div>
		</div>
		<div class="form-group">
			<label for="description" class="col-sm-3 control-label">~Description</label>
			<div class="col-sm-9">
				<textarea class="form-control" rows="3" id="langs${currIndex}.description" name="langs[${currIndex}].description" placeholder="~Description">
					<c:if test="${currLang!=null}">${currLang.description}</c:if>
				</textarea>
			</div>
		</div>
		<div class="form-group">
			<label for="audience" class="col-sm-3 control-label">~Audience</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" id="langs${currIndex}.audience" name="langs[${currIndex}].audience" value="<c:if test="${currLang!=null}">${currLang.audience}</c:if>" placeholder="~Audience">
			</div>
		</div>
		<div class="form-group">
			<label for="provenance" class="col-sm-3 control-label">~Provenance</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" id="langs${currIndex}.provenance" name="langs[${currIndex}].provenance" value="<c:if test="${currLang!=null}">${currLang.provenance}</c:if>" placeholder="~Provenance">
			</div>
		</div>
	
	</td>
</tr>