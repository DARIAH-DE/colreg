<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tr class="list">
	<td class="identifier_identifier" onclick="editor.identifierTable.editEntry(this); return false;">
		<c:choose>
			<c:when test="${currIdentifier!=null}">${currIdentifier}</c:when>
			<c:otherwise>~ New entry</c:otherwise>
		</c:choose>
	</td>
	<td class="nowrap">
		<button onclick="editor.identifierTable.pushEntryUp(this); return false;" class="btn btn-xs btn-link btn-push-up"><span class="glyphicon glyphicon glyphicon-arrow-up" aria-hidden="true"></span></button>
		<button onclick="editor.identifierTable.pushEntryDown(this); return false;" class="btn btn-xs btn-link btn-push-down"><span class="glyphicon glyphicon glyphicon-arrow-down" aria-hidden="true"></span></button>
		<button onclick="editor.identifierTable.removeEntry(this); return false;" class="btn btn-xs btn-link"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
	</td>
</tr>
<tr class="edit" style="display: none;">
	<td colspan="2">
		<div class="form-group">
			<label for="title" class="col-sm-4 control-label">~Identifier</label>
			<div class="col-sm-4">
				<span class="attribute-name-helper">providedIdentifier{}</span>
				<input type="text" 
					onchange="editor.identifierTable.handleInputChange(this, 'identifier_identifier');" 
					onkeyup="editor.identifierTable.handleInputChange(this, 'identifier_identifier');" 
					class="form-control typeahead" id="providedIdentifier${currIndex}" name="providedIdentifier[${currIndex}]" 
					value="<c:if test="${currIdentifier!=null}">${currIdentifier}</c:if>" placeholder="~Provided Identifier">
			</div>
		</div>
	</td>
</tr>