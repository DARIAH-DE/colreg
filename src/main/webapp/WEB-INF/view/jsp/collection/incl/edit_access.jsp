<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tr class="list">
	<td class="accessMethodTable_uri" onclick="editor.tables['accessMethodTable'].editEntry(this); return false;">
		<c:choose>
			<c:when test="${currMethod!=null}">${currMethod.uri}</c:when>
			<c:otherwise>~ New entry</c:otherwise>
		</c:choose>
	</td>
	<td class="accessMethodTable_type nowrap" onclick="editor.tables['accessMethodTable'].editEntry(this); return false;">
		<c:if test="${currMethod!=null}">
			<c:forEach items="${accessTypes}" var="type">
				<c:if test="${currMethod.type==type.id}">${type.label}</c:if>
			</c:forEach>
		</c:if>
	</td>
	<td class="nowrap">
		<button onclick="editor.tables['accessMethodTable'].pushEntryUp(this); return false;" class="btn btn-xs btn-link btn-push-up"><span class="glyphicon glyphicon glyphicon-arrow-up" aria-hidden="true"></span></button>
		<button onclick="editor.tables['accessMethodTable'].pushEntryDown(this); return false;" class="btn btn-xs btn-link btn-push-down"><span class="glyphicon glyphicon glyphicon-arrow-down" aria-hidden="true"></span></button>
		<button onclick="editor.tables['accessMethodTable'].removeEntry(this); return false;" class="btn btn-xs btn-link"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
	</td>
</tr>
<tr class="edit" style="display: none;">
	<td colspan="3">
		<div class="form-group">
			<label for="title" class="col-sm-4 control-label">~Access type</label>
			<div class="col-sm-4">
				<span class="attribute-name-helper">accessMethods{}.type</span>
				<select class="form-control" name="accessMethods[${currIndex}].type" id="accessMethods${currIndex}.type" 
					onchange="editor.tables['accessMethodTable'].handleSelectChange(this, 'accessMethodTable_type');" autocomplete="off">
					<c:forEach items="${accessTypes}" var="type">
						<option <c:if test="${currMethod.type==type.id}">selected="selected"</c:if> value="${type.id}">${type.label}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label for="title" class="col-sm-4 control-label">~Uri</label>
			<div class="col-sm-8">
				<span class="attribute-name-helper">accessMethods{}.uri</span>
				<input type="text" 
					onchange="editor.tables['accessMethodTable'].handleInputChange(this, 'accessMethodTable_uri');" 
					onkeyup="editor.tables['accessMethodTable'].handleInputChange(this, 'accessMethodTable_uri');" 
					class="form-control" id="accessMethods${currIndex}.uri" name="accessMethods[${currIndex}].uri" 
					value="<c:if test="${currMethod!=null}">${currMethod.uri}</c:if>" placeholder="~URI">
			</div>
		</div>
		<div class="form-group">
			<label for="title" class="col-sm-4 control-label">~Encoding schemes</label>
			<div class="col-sm-8">
				<ul class="lst-collection-access-schemes collection-editor-list">
					<c:if test="${fn:length(currMethod.schemeIds)>0}">
						<c:forEach items="${currMethod.schemeIds}" var="schemeId" varStatus="status" >
							<c:set var="currSchemeId" value="${schemeId}" scope="request" />
							<c:set var="currIndex" value="${status.index}" scope="request" />
							<jsp:include page="edit_encodingscheme.jsp" />
						</c:forEach>
						<c:remove var="currScheme" />	
					</c:if>
					<li class="collection-editor-list-buttons">
						<div class="col-sm-12">
							<button onclick="editor.tables['accessMethodTable'].schemesList.triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add-scheme"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>~ Add encoding scheme</button>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<div class="form-group">
			<label for="title" class="col-sm-4 control-label">~Description</label>
			<div class="col-sm-8">
				<span class="attribute-name-helper">accessMethods{}.description</span>
				<textarea class="form-control" rows="3" id="accessMethods${currIndex}.description" name="accessMethods[${currIndex}].description" placeholder="~Description"><c:if test="${currMethod!=null}">${currMethod.description}</c:if></textarea>
			</div>
		</div>
	</td>
</tr>