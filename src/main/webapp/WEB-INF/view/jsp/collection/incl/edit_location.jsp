<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<s:bind path="locations[${currIndex}].*">
	<tr class="list${status.error ? ' has-error' : ' '}">
		<td class="locations_place" onclick="editor.tables['locations'].editEntry(this); return false;">
			<c:choose>
				<c:when test="${currAddr!=null}">${currAddr.place}</c:when>
				<c:otherwise><s:message code="~eu.dariah.de.colreg.common.labels.new_entry" /></c:otherwise>
			</c:choose>
		</td>
		<td class="locations_country nowrap" onclick="editor.tables['locations'].editEntry(this); return false;">
			<c:if test="${currAddr!=null}">${currAddr.country}</c:if>
		</td>
		<td class="nowrap">
			<button onclick="editor.tables['locations'].pushEntryUp(this); return false;" class="btn btn-xs btn-link btn-push-up"><span class="glyphicon glyphicon glyphicon-arrow-up" aria-hidden="true"></span></button>
			<button onclick="editor.tables['locations'].pushEntryDown(this); return false;" class="btn btn-xs btn-link btn-push-down"><span class="glyphicon glyphicon glyphicon-arrow-down" aria-hidden="true"></span></button>
			<button onclick="editor.tables['locations'].removeEntry(this); return false;" class="btn btn-xs btn-link"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
		</td>
	</tr>
</s:bind>
<tr class="edit" style="display: none;">
	<td colspan="3">

		<!-- Street -->
		<s:bind path="locations[${currIndex}].street">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="street" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.address.street" /></label>
				<div class="col-sm-7">
					<span class="attribute-name-helper">locations{}.street</span>
					<input type="text" 
						onchange="editor.tables['locations'].handleInputChange(this, 'locations_street');" 
						onkeyup="editor.tables['locations'].handleInputChange(this, 'locations_street');" 
						class="form-control" id="locations${currIndex}.street" name="locations[${currIndex}].street" 
						value="<c:if test="${currAddr!=null}">${currAddr.street}</c:if>">
				</div>
				<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
					path="locations[${currIndex}].street" />
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.address.street" />
					</div>
				</div>
			</div>
		</s:bind>
		
		<!-- Number -->
		<s:bind path="locations[${currIndex}].number">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="number" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.address.number" /></label>
				<div class="col-sm-3">
					<span class="attribute-name-helper">locations{}.number</span>
					<input type="text" 
						onchange="editor.tables['locations'].handleInputChange(this, 'locations_number');" 
						onkeyup="editor.tables['locations'].handleInputChange(this, 'locations_number');" 
						class="form-control" id="locations${currIndex}.number" name="locations[${currIndex}].number" 
						value="<c:if test="${currAddr!=null}">${currAddr.number}</c:if>">
				</div>
				<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
					path="locations[${currIndex}].number" />
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.address.number" />
					</div>
				</div>
			</div>
		</s:bind>
		
		<!-- Postal code -->
		<s:bind path="locations[${currIndex}].postalCode">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="street" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.address.postal_code" /></label>
				<div class="col-sm-3">
					<span class="attribute-name-helper">locations{}.postalCode</span>
					<input type="text" 
						onchange="editor.tables['locations'].handleInputChange(this, 'locations_postalCode');" 
						onkeyup="editor.tables['locations'].handleInputChange(this, 'locations_postalCode');" 
						class="form-control" id="locations${currIndex}.postalCode" name="locations[${currIndex}].postalCode" 
						value="<c:if test="${currAddr!=null}">${currAddr.postalCode}</c:if>">
				</div>
				<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
					path="locations[${currIndex}].postalCode" />
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.address.postal_code" />
					</div>
				</div>
			</div>
		</s:bind>
		
		<!-- Place -->
		<s:bind path="locations[${currIndex}].place">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="street" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.address.place" /></label>
				<div class="col-sm-7">
					<span class="attribute-name-helper">locations{}.place</span>
					<input type="text" 
						onchange="editor.tables['locations'].handleInputChange(this, 'locations_place');" 
						onkeyup="editor.tables['locations'].handleInputChange(this, 'locations_place');" 
						class="form-control" id="locations${currIndex}.place" name="locations[${currIndex}].place" 
						value="<c:if test="${currAddr!=null}">${currAddr.place}</c:if>">
				</div>
				<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
					path="locations[${currIndex}].place" />
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.address.place" />
					</div>
				</div>
			</div>
		</s:bind>
		
		<!-- Country -->
		<s:bind path="locations[${currIndex}].country">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="street" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.address.country" /></label>
				<div class="col-sm-6">
					<span class="attribute-name-helper">locations{}.country</span>
					<input type="text" 
						onchange="editor.tables['locations'].handleInputChange(this, 'locations_country');" 
						onkeyup="editor.tables['locations'].handleInputChange(this, 'locations_country');" 
						class="form-control" id="locations${currIndex}.country" name="locations[${currIndex}].country" 
						value="<c:if test="${currMethod!=null}">${currAddr.country}</c:if>">
				</div>
				<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
					path="locations[${currIndex}].country" />
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.address.country" />
					</div>
				</div>
			</div>
		</s:bind>
		
		<!-- Note -->
		<s:bind path="locations[${currIndex}].note">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="note" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.address.note" /></label>
				<div class="col-sm-9">
					<span class="attribute-name-helper">locations{}.note</span>
					<textarea class="form-control" rows="3" id="locations${currIndex}.note" name="locations[${currIndex}].note"><c:if test="${currAddr!=null}">${currAddr.note}</c:if></textarea>
				</div>
				<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
					path="locations[${currIndex}].note" />
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.address.note" />
					</div>
				</div>
			</div>
		</s:bind>
		
	</td>
</tr>