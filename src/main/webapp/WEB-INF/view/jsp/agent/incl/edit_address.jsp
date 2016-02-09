<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<s:bind path="addresses[${currIndex}].*">
	<tr class="list${status.error ? ' has-error' : ' '}">
		<td class="addresses_place" onclick="editor.tables['addresses'].editEntry(this); return false;">
			<c:choose>
				<c:when test="${currAddr!=null}">${currAddr.place}</c:when>
				<c:otherwise><s:message code="~eu.dariah.de.colreg.common.labels.new_entry" /></c:otherwise>
			</c:choose>
		</td>
		<td class="addresses_country nowrap" onclick="editor.tables['addresses'].editEntry(this); return false;">
			<c:if test="${currAddr!=null}">${currAddr.country}</c:if>
		</td>
		<td class="nowrap">
			<button onclick="editor.tables['addresses'].pushEntryUp(this); return false;" class="btn btn-xs btn-link btn-push-up"><span class="glyphicon glyphicon glyphicon-arrow-up" aria-hidden="true"></span></button>
			<button onclick="editor.tables['addresses'].pushEntryDown(this); return false;" class="btn btn-xs btn-link btn-push-down"><span class="glyphicon glyphicon glyphicon-arrow-down" aria-hidden="true"></span></button>
			<button onclick="editor.tables['addresses'].removeEntry(this); return false;" class="btn btn-xs btn-link"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
		</td>
	</tr>
</s:bind>
<tr class="edit" style="display: none;">
	<td colspan="3">

		<!-- Street -->
		<s:bind path="addresses[${currIndex}].street">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="street" class="col-sm-4 control-label"><s:message code="~eu.dariah.de.colreg.model.access.uri" /></label>
				<div class="col-sm-8">
					<span class="attribute-name-helper">accessMethods{}.uri</span>
					<input type="text" 
						onchange="editor.tables['accessMethodTable'].handleInputChange(this, 'accessMethodTable_uri');" 
						onkeyup="editor.tables['accessMethodTable'].handleInputChange(this, 'accessMethodTable_uri');" 
						class="form-control" id="accessMethods${currIndex}.uri" name="accessMethods[${currIndex}].uri" 
						value="<c:if test="${currMethod!=null}">${currMethod.uri}</c:if>">
				</div>
				<sf:errors element="div" cssClass="validation-error col-sm-8 col-sm-offset-4" 
					path="accessMethods[${currIndex}].uri" />
			</div>
		</s:bind>
		
	</td>
</tr>