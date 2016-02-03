<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tr class="list">
	<td class="accrualMethodTable_accrualMethod" onclick="editor.accrualMethodTable.editEntry(this); return false;">
		<c:choose>
			<c:when test="${currMethod!=null}">
				<c:forEach items="${accrualMethods}" var="method">
					<c:if test="${currMethod.accrualMethod==method.id}">${method.label}</c:if>
				</c:forEach>
			</c:when>
			<c:otherwise>~ New entry</c:otherwise>
		</c:choose>
	</td>
	<td class="accrualMethodTable_accrualPolicy nowrap" onclick="editor.accrualMethodTable.editEntry(this); return false;">
		<c:if test="${currMethod!=null}">
			<c:forEach items="${accrualPolicies}" var="policy">
				<c:if test="${currMethod.accrualPolicy==policy.id}">${policy.label}</c:if>
			</c:forEach>
		</c:if>
	</td>
	<td class="nowrap">
		<button onclick="editor.accrualMethodTable.pushEntryUp(this); return false;" class="btn btn-xs btn-link btn-push-up"><span class="glyphicon glyphicon glyphicon-arrow-up" aria-hidden="true"></span></button>
		<button onclick="editor.accrualMethodTable.pushEntryDown(this); return false;" class="btn btn-xs btn-link btn-push-down"><span class="glyphicon glyphicon glyphicon-arrow-down" aria-hidden="true"></span></button>
		<button onclick="editor.accrualMethodTable.removeEntry(this); return false;" class="btn btn-xs btn-link"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
	</td>
</tr>
<tr class="edit" style="display: none;">
	<td colspan="3">
		<div class="form-group">
			<label for="title" class="col-sm-4 control-label">~Accrual method</label>
			<div class="col-sm-4">
				<span class="attribute-name-helper">accrualMethods{}.accrualMethod</span>
				<select class="form-control" name="accrualMethods[${currIndex}].accrualMethod" id="accrualMethods${currIndex}.accrualMethod" 
					onchange="editor.accrualMethodTable.handleSelectChange(this, 'accrualMethodTable_accrualMethod');" autocomplete="off">
					<c:forEach items="${accrualMethods}" var="accMethod">
						<option <c:if test="${currMethod.accrualMethod==accMethod.id}">selected="selected"</c:if> value="${accMethod.id}">${accMethod.label}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label for="title" class="col-sm-4 control-label">~Accrual policy</label>
			<div class="col-sm-4">
				<span class="attribute-name-helper">accrualMethods{}.accrualPolicy</span>
				<select class="form-control" name="accrualMethods[${currIndex}].accrualPolicy" id="accrualMethods${currIndex}.accrualPolicy" 
					onchange="editor.accrualMethodTable.handleSelectChange(this, 'accrualMethodTable_accrualPolicy');" autocomplete="off">
					<c:forEach items="${accrualPolicies}" var="accPolicy">
						<option <c:if test="${currMethod.accrualPolicy==accPolicy.id}">selected="selected"</c:if> value="${accPolicy.id}">${accPolicy.label}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label for="title" class="col-sm-4 control-label">~Description</label>
			<div class="col-sm-8">
				<span class="attribute-name-helper">accrualMethods{}.description</span>
				<textarea class="form-control" rows="3" id="accrualMethods${currIndex}.description" name="accrualMethods[${currIndex}].description" placeholder="~Description"><c:if test="${currMethod!=null}">${currMethod.description}</c:if></textarea>
			</div>
		</div>
	</td>
</tr>