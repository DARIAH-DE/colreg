<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<s:bind path="accessMethods[${currIndex}].*">
	<tr class="list${status.error ? ' has-error' : ' '}">
		<td class="accessMethodTable_uri" onclick="editor.tables['accessMethodTable'].editEntry(this); return false;">
			<c:choose>
				<c:when test="${currMethod!=null}">
					<a class="control-link" href="javascript:void(0);">${currMethod.uri}</a>
				</c:when>
				<c:otherwise><s:message code="~eu.dariah.de.colreg.common.labels.new_entry" /></c:otherwise>
			</c:choose>
		</td>
		<td class="accessMethodTable_type nowrap" onclick="editor.tables['accessMethodTable'].editEntry(this); return false;">
			<c:if test="${currMethod!=null}">
				<c:forEach items="${accessTypes}" var="type">
					<c:if test="${currMethod.type==type.id}">${type.label}</c:if>
				</c:forEach>
			</c:if>
		</td>
		<c:if test="${editMode}">
			<td class="nowrap">
				<button onclick="editor.tables['accessMethodTable'].pushEntryUp(this); return false;" class="btn btn-xs btn-link btn-push-up"><span class="glyphicon glyphicon glyphicon-arrow-up" aria-hidden="true"></span></button>
				<button onclick="editor.tables['accessMethodTable'].pushEntryDown(this); return false;" class="btn btn-xs btn-link btn-push-down"><span class="glyphicon glyphicon glyphicon-arrow-down" aria-hidden="true"></span></button>
				<button onclick="editor.tables['accessMethodTable'].removeEntry(this); return false;" class="btn btn-xs btn-link"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
			</td>
		</c:if>
	</tr>
</s:bind>
<tr class="edit" style="display: none;">
	<td colspan="${editMode ? 3 : 2}">
		<s:bind path="accessMethods[${currIndex}].type">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="title" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.access.type" /></label>
				
				<c:choose>
					<c:when test="${editMode}">
						<div class="col-sm-4">
							<span class="attribute-name-helper">accessMethods{}.type</span>
							<select class="form-control" name="accessMethods[${currIndex}].type" id="accessMethods${currIndex}.type" 
								onchange="editor.handleAccessTypeChange(this); editor.tables['accessMethodTable'].handleSelectChange(this, 'accessMethodTable_type');" autocomplete="off">
								<c:forEach items="${accessTypes}" var="type">
									<c:set var="selected"></c:set>
									<c:if test="${currMethod.type==type.id}"><c:set var="selected">selected="selected"</c:set></c:if>
									<option ${selected} value="${type.id}">${type.label}</option>
								</c:forEach>
							</select>
						</div>
						<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
							path="accessMethods[${currIndex}].type" />
					</c:when>
					<c:otherwise>
						<div class="col-sm-9">
							<label class="content-label">
								<c:forEach items="${accessTypes}" var="type">
									<c:if test="${currMethod.type==type.id}">${type.label}</c:if>
								</c:forEach>
							</label>
						</div>
					</c:otherwise>
				</c:choose>		
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.access.type" />
					</div>
				</div>
			</div>
		</s:bind>
		<s:bind path="accessMethods[${currIndex}].uri">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="title" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.access.uri" /></label>
				<div class="col-sm-9">
					<c:choose>
						<c:when test="${editMode}">
							<span class="attribute-name-helper">accessMethods{}.uri</span>
							<input type="text" 
								onchange="editor.tables['accessMethodTable'].handleInputChange(this, 'accessMethodTable_uri');" 
								onkeyup="editor.tables['accessMethodTable'].handleInputChange(this, 'accessMethodTable_uri');" 
								class="form-control" id="accessMethods${currIndex}.uri" name="accessMethods[${currIndex}].uri" 
								value="<c:if test="${currMethod!=null}">${currMethod.uri}</c:if>">
						</c:when>
						<c:otherwise>
							<label class="content-label">${currMethod!=null ? currMethod.uri : ''}</label>
						</c:otherwise>
					</c:choose>		
				</div>
				<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
					path="accessMethods[${currIndex}].uri" />
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.access.uri" />
					</div>
				</div>
			</div>
		</s:bind>
		
		<c:set var="isOaiPMH" value="false" />
		<c:forEach items="${accessTypes}" var="accessType">
			<c:if test="${currMethod.type==accessType.id && accessType.identifier=='oaipmh'}">
				<c:set var="isOaiPMH" value="true" />
			</c:if>
		</c:forEach>
		
		<s:bind path="accessMethods[${currIndex}].oaiSet">
			<div class="form-group oaiset${status.error ? ' has-error' : ''}" ${isOaiPMH ? '' : 'style="display: none;"'}>
				<label for="title" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.access.oaipmh_set" /></label>
				<div class="col-sm-9">
					<c:choose>
						<c:when test="${editMode}">
							<span class="attribute-name-helper">accessMethods{}.oaiSet</span>
							<input type="text" 
								onchange="editor.tables['accessMethodTable'].handleInputChange(this, 'accessMethodTable_oaiSet');" 
								onkeyup="editor.tables['accessMethodTable'].handleInputChange(this, 'accessMethodTable_oaiSet');" 
								class="form-control" id="accessMethods${currIndex}.oaiSet" name="accessMethods[${currIndex}].oaiSet" 
								value="<c:if test="${currMethod!=null}">${currMethod.oaiSet}</c:if>">
						</c:when>
						<c:otherwise>
							<label class="content-label">${currMethod!=null ? currMethod.oaiSet : ''}</label>
						</c:otherwise>
					</c:choose>	
				</div>
				<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
					path="accessMethods[${currIndex}].oaiSet" />
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.access.oaipmh_set" />
					</div>
				</div>
			</div>
		</s:bind>
		<s:bind path="accessMethods[${currIndex}].schemeIds*">
			<div class="form-group">
				<label for="title" class="col-sm-3 control-label${status.error ? ' container-error' : ' '}"><s:message code="~eu.dariah.de.colreg.model.access.encoding_schemes" /></label>
				<div class="col-sm-7">
					<c:choose>
						<c:when test="${editMode}">
							<span class="attribute-name-helper">accessMethods{}.schemeIds</span>
							<select class="form-control" id="accessMethods[${currIndex}].schemeIds" name="accessMethods[${currIndex}].schemeIds" size="8" multiple="multiple" autocomplete="off">
								<c:forEach items="${encodingSchemes}" var="scheme">
									<c:set var="contains" value="false" />
									<c:forEach items="${currMethod.schemeIds}" var="schemeId">
										<c:if test="${schemeId==scheme.id}">
											<c:set var="contains" value="true" />
										</c:if>
									</c:forEach>
									<c:set var="selected"></c:set>
									<c:if test="${contains}"><c:set var="selected">selected="selected"</c:set></c:if>
									<option ${selected} value="${scheme.id}">${scheme.name} [${scheme.id}]</option>
								</c:forEach>
							</select>
						</c:when>
						<c:otherwise>
							<label class="content-label">
							<c:forEach items="${encodingSchemes}" var="scheme">
								<c:forEach items="${currMethod.schemeIds}" var="schemeId">
									<c:if test="${schemeId==scheme.id}">${scheme.name}<br /></c:if>
								</c:forEach>
							</c:forEach>
							</label>
						</c:otherwise>
					</c:choose>
				</div>
				<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
					path="accessMethods[${currIndex}].schemeIds" />
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.access.scheme_ids" />
					</div>
				</div>
			</div>
		</s:bind>
		<s:bind path="accessMethods[${currIndex}].description">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="title" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.access.description" /></label>
				<div class="col-sm-9">
					<c:choose>
						<c:when test="${editMode}">
							<span class="attribute-name-helper">accessMethods{}.description</span>
					<textarea class="form-control" rows="3" id="accessMethods${currIndex}.description" name="accessMethods[${currIndex}].description"><c:if test="${currMethod!=null}">${currMethod.description}</c:if></textarea>
						</c:when>
						<c:otherwise>
							<label class="content-label">${currMethod!=null ? currMethod.description : ''}</label>
						</c:otherwise>
					</c:choose>
				</div>
				<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
					path="accessMethods[${currIndex}].description" />
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.access.description" />
					</div>
				</div>
			</div>
		</s:bind>
	</td>
</tr>