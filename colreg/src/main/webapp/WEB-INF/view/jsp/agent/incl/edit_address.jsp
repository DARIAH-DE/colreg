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
		<c:if test="${editMode}">
			<td class="nowrap">
				<button onclick="editor.tables['addresses'].pushEntryUp(this); return false;" class="btn btn-xs btn-link btn-push-up"><span class="glyphicon glyphicon glyphicon-arrow-up" aria-hidden="true"></span></button>
				<button onclick="editor.tables['addresses'].pushEntryDown(this); return false;" class="btn btn-xs btn-link btn-push-down"><span class="glyphicon glyphicon glyphicon-arrow-down" aria-hidden="true"></span></button>
				<button onclick="editor.tables['addresses'].removeEntry(this); return false;" class="btn btn-xs btn-link"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
			</td>
		</c:if>
	</tr>
</s:bind>
<tr class="edit" style="display: none;">
	<td colspan="${editMode ? 3 : 2}">

		<!-- Street -->
		<s:bind path="addresses[${currIndex}].street">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="street" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.address.street" /></label>
				<div class="col-sm-7">
					<c:choose>
						<c:when test="${editMode}">
							<span class="attribute-name-helper">addresses{}.street</span>
							<input type="text" 
								onchange="editor.tables['addresses'].handleInputChange(this, 'addresses_street');" 
								onkeyup="editor.tables['addresses'].handleInputChange(this, 'addresses_street');" 
								class="form-control" id="addresses${currIndex}.street" name="addresses[${currIndex}].street" 
								value="<c:if test="${currAddr!=null}">${currAddr.street}</c:if>">
						</c:when>
						<c:otherwise>
							<label class="content-label">${currAddr!=null ? currAddr.street : ''}</label>
						</c:otherwise>
					</c:choose>		
				</div>
				<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
					path="addresses[${currIndex}].street" />
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.address.street" />
					</div>
				</div>
			</div>
		</s:bind>
		
		<!-- Number -->
		<s:bind path="addresses[${currIndex}].number">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="number" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.address.number" /></label>
				<div class="col-sm-3">
					<c:choose>
						<c:when test="${editMode}">
							<span class="attribute-name-helper">addresses{}.number</span>
							<input type="text" 
								onchange="editor.tables['addresses'].handleInputChange(this, 'addresses_number');" 
								onkeyup="editor.tables['addresses'].handleInputChange(this, 'addresses_number');" 
								class="form-control" id="addresses${currIndex}.number" name="addresses[${currIndex}].number" 
								value="<c:if test="${currAddr!=null}">${currAddr.number}</c:if>">
						</c:when>
						<c:otherwise>
							<label class="content-label">${currAddr!=null ? currAddr.number : ''}</label>
						</c:otherwise>
					</c:choose>
				</div>
				<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
					path="addresses[${currIndex}].number" />
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.address.number" />
					</div>
				</div>
			</div>
		</s:bind>
		
		<!-- Postal code -->
		<s:bind path="addresses[${currIndex}].postalCode">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="street" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.address.postal_code" /></label>
				<div class="col-sm-3">
					<c:choose>
						<c:when test="${editMode}">
							<span class="attribute-name-helper">addresses{}.postalCode</span>
							<input type="text" 
								onchange="editor.tables['addresses'].handleInputChange(this, 'addresses_postalCode');" 
								onkeyup="editor.tables['addresses'].handleInputChange(this, 'addresses_postalCode');" 
								class="form-control" id="addresses${currIndex}.postalCode" name="addresses[${currIndex}].postalCode" 
								value="<c:if test="${currAddr!=null}">${currAddr.postalCode}</c:if>">
						</c:when>
						<c:otherwise>
							<label class="content-label">${currAddr!=null ? currAddr.postalCode : ''}</label>
						</c:otherwise>
					</c:choose>
				</div>
				<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
					path="addresses[${currIndex}].postalCode" />
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.address.postal_code" />
					</div>
				</div>
			</div>
		</s:bind>
		
		<!-- Place -->
		<s:bind path="addresses[${currIndex}].place">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="street" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.address.place" /></label>
				<div class="col-sm-7">
					<c:choose>
						<c:when test="${editMode}">
							<span class="attribute-name-helper">addresses{}.place</span>
							<input type="text" 
								onchange="editor.tables['addresses'].handleInputChange(this, 'addresses_place');" 
								onkeyup="editor.tables['addresses'].handleInputChange(this, 'addresses_place');" 
								class="form-control" id="addresses${currIndex}.place" name="addresses[${currIndex}].place" 
								value="<c:if test="${currAddr!=null}">${currAddr.place}</c:if>">
						</c:when>
						<c:otherwise>
							<label class="content-label">${currAddr!=null ? currAddr.place : ''}</label>
						</c:otherwise>
					</c:choose>
				</div>
				<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
					path="addresses[${currIndex}].place" />
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.address.place" />
					</div>
				</div>
			</div>
		</s:bind>
		
		<!-- Country -->
		<s:bind path="addresses[${currIndex}].country">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="street" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.address.country" /></label>
				<div class="col-sm-6">
					<c:choose>
						<c:when test="${editMode}">
							<span class="attribute-name-helper">addresses{}.country</span>
							<input type="text" 
								onchange="editor.tables['addresses'].handleInputChange(this, 'addresses_country');" 
								onkeyup="editor.tables['addresses'].handleInputChange(this, 'addresses_country');" 
								class="form-control" id="addresses${currIndex}.country" name="addresses[${currIndex}].country" 
								value="<c:if test="${currAddr!=null}">${currAddr.country}</c:if>">
						</c:when>
						<c:otherwise>
							<label class="content-label">${currAddr!=null ? currAddr.country : ''}</label>
						</c:otherwise>
					</c:choose>
				</div>
				<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
					path="addresses[${currIndex}].country" />
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.address.country" />
					</div>
				</div>
			</div>
		</s:bind>
		
		<!-- Note -->
		<s:bind path="addresses[${currIndex}].note">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="note" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.address.note" /></label>
				<div class="col-sm-9">
					<c:choose>
						<c:when test="${editMode}">
							<span class="attribute-name-helper">addresses{}.note</span>
					<textarea class="form-control" rows="3" id="addresses${currIndex}.note" name="addresses[${currIndex}].note"><c:if test="${currAddr!=null}">${currAddr.note}</c:if></textarea>
						</c:when>
						<c:otherwise>
							<label class="content-label">${currAddr!=null ? currAddr.note : ''}</label>
						</c:otherwise>
					</c:choose>
				</div>
				<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
					path="addresses[${currIndex}].note" />
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