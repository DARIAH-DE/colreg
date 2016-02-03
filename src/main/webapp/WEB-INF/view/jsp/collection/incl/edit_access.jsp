<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tr class="list">
	<td class="accessMethodTable_uri" onclick="editor.accessMethodTable.editEntry(this); return false;">
		<c:choose>
			<c:when test="${currMethod!=null}">${currMethod.uri}</c:when>
			<c:otherwise>~ New entry</c:otherwise>
		</c:choose>
	</td>
	<td class="accessMethodTable_type" onclick="editor.accessMethodTable.editEntry(this); return false;">
		<c:if test="${currMethod!=null}">${currMethod.type}</c:if>
	</td>
	<td class="nowrap">
		<button onclick="editor.accessMethodTable.pushEntryUp(this); return false;" class="btn btn-xs btn-link btn-push-up"><span class="glyphicon glyphicon glyphicon-arrow-up" aria-hidden="true"></span></button>
		<button onclick="editor.accessMethodTable.pushEntryDown(this); return false;" class="btn btn-xs btn-link btn-push-down"><span class="glyphicon glyphicon glyphicon-arrow-down" aria-hidden="true"></span></button>
		<button onclick="editor.accessMethodTable.removeEntry(this); return false;" class="btn btn-xs btn-link"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
	</td>
</tr>
<tr class="edit" style="display: none;">
	<td colspan="3">
		<div class="form-group">
			<label for="title" class="col-sm-4 control-label">~Access type</label>
			<div class="col-sm-4">
				<span class="attribute-name-helper">accessMethods{}.type</span>
				<select class="form-control" name="accessMethods[${currIndex}].type" id="accessMethods${currIndex}.type" 
					onchange="editor.accessMethodTable.handleSelectChange(this, 'accessMethodTable_type');" autocomplete="off">
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
					onchange="editor.accessMethodTable.handleInputChange(this, 'accessMethodTable_uri');" 
					onkeyup="editor.accessMethodTable.handleInputChange(this, 'accessMethodTable_uri');" 
					class="form-control" id="accessMethods${currIndex}.uri" name="accessMethods[${currIndex}].uri" 
					value="<c:if test="${currMethod!=null}">${currMethod.uri}</c:if>" placeholder="~URI">
			</div>
		</div>
		<div class="form-group">
			<label for="title" class="col-sm-4 control-label">~Encoding schemes</label>
			<div class="col-sm-4">
				<span class="attribute-name-helper">accessMethods{}.schemes</span>
				
			</div>
		</div>
	</td>
</tr>