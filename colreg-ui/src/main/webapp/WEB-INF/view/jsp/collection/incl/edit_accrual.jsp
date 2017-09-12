<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<s:bind path="accrualMethods[${currIndex}].*">
	<tr class="list${status.error ? ' has-error' : ' '}">
		<td class="accrualMethodTable_accrualMethod" onclick="editor.tables['accrualMethodTable'].editEntry(this); return false;">
			<c:choose>
				<c:when test="${currMethod!=null}">
					<c:forEach items="${accrualMethods}" var="method">
						<c:if test="${currMethod.accrualMethod==method.id}">
							<a class="control-link" href="javascript:void(0);">${method.label}</a>
						</c:if>
					</c:forEach>
				</c:when>
				<c:otherwise><s:message code="~eu.dariah.de.colreg.common.labels.new_entry" /></c:otherwise>
			</c:choose>
		</td>
		<td class="accrualMethodTable_accrualPolicy nowrap" onclick="editor.tables['accrualMethodTable'].editEntry(this); return false;">
			<c:if test="${currMethod!=null}">
				<c:forEach items="${accrualPolicies}" var="policy">
					<c:if test="${currMethod.accrualPolicy==policy.id}">${policy.label}</c:if>
				</c:forEach>
			</c:if>
		</td>
		<td class="accrualMethodTable_accrualPeriodicity nowrap" onclick="editor.tables['accrualMethodTable'].editEntry(this); return false;">
			<c:if test="${currMethod!=null}">
				<c:forEach items="${accrualPeriodicities}" var="periodicity">
					<c:if test="${currMethod.accrualPeriodicity==periodicity.id}">${periodicity.label}</c:if>
				</c:forEach>
			</c:if>
		</td>
		<c:if test="${editMode}">
			<td class="nowrap">
				<button onclick="editor.tables['accrualMethodTable'].pushEntryUp(this); return false;" class="btn btn-xs btn-link btn-push-up"><span class="glyphicon glyphicon glyphicon-arrow-up" aria-hidden="true"></span></button>
				<button onclick="editor.tables['accrualMethodTable'].pushEntryDown(this); return false;" class="btn btn-xs btn-link btn-push-down"><span class="glyphicon glyphicon glyphicon-arrow-down" aria-hidden="true"></span></button>
				<button onclick="editor.tables['accrualMethodTable'].removeEntry(this); return false;" class="btn btn-xs btn-link"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
			</td>
		</c:if>
	</tr>
</s:bind>
<tr class="edit" style="display: none;">
	<td colspan="${editMode ? 4 : 3}">
	
		<!-- Method -->
		<s:bind path="accrualMethods[${currIndex}].accrualMethod">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="title" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.accrual.method" /></label>
				<c:choose>
					<c:when test="${editMode}">
						<div class="col-sm-4">
							<span class="attribute-name-helper">accrualMethods{}.accrualMethod</span>
							<select class="form-control" name="accrualMethods[${currIndex}].accrualMethod" id="accrualMethods${currIndex}.accrualMethod" 
								onchange="editor.tables['accrualMethodTable'].handleSelectChange(this, 'accrualMethodTable_accrualMethod');" autocomplete="off">
								<c:forEach items="${accrualMethods}" var="accMethod">
									<c:if test="${currMethod.accrualMethod==accMethod.id}"><c:set var="selected">selected="selected"</c:set></c:if>
									<option ${selected} value="${accMethod.id}">${accMethod.label}</option>
								</c:forEach>
							</select>
						</div>
						<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
							path="accrualMethods[${currIndex}].accrualMethod" />
					</c:when>
					<c:otherwise>
						<div class="col-sm-9">
							<label class="content-label">
								<c:forEach items="${accrualMethods}" var="accMethod">
									<c:if test="${currMethod.accrualMethod==accMethod.id}">${accMethod.label}</c:if>
								</c:forEach>
							</label>
						</div>
					</c:otherwise>
				</c:choose>		
				
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.accrual.accrual_method" />
					</div>
				</div>
			</div>
		</s:bind>
		
		<!-- Policy -->
		<s:bind path="accrualMethods[${currIndex}].accrualPolicy">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="title" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.accrual.policy" /></label>
				<c:choose>
					<c:when test="${editMode}">
						<div class="col-sm-4">
							<span class="attribute-name-helper">accrualMethods{}.accrualPolicy</span>
							<select class="form-control" name="accrualMethods[${currIndex}].accrualPolicy" id="accrualMethods${currIndex}.accrualPolicy" 
								onchange="editor.tables['accrualMethodTable'].handleSelectChange(this, 'accrualMethodTable_accrualPolicy');" autocomplete="off">
								<c:forEach items="${accrualPolicies}" var="accPolicy">
									<c:if test="${currMethod.accrualPolicy==accPolicy.id}"><c:set var="selected">selected="selected"</c:set></c:if>
									<option ${selected} value="${accPolicy.id}">${accPolicy.label}</option>
								</c:forEach>
							</select>
						</div>
						<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
							path="accrualMethods[${currIndex}].accrualPolicy" />
					</c:when>
					<c:otherwise>
						<div class="col-sm-9">
							<label class="content-label">
								<c:forEach items="${accrualPolicies}" var="accPolicy">
									<c:if test="${currMethod.accrualPolicy==accPolicy.id}">${accPolicy.label}</c:if>
								</c:forEach>
							</label>
						</div>
					</c:otherwise>
				</c:choose>		
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.accrual.accrual_policy" />
					</div>
				</div>
			</div>
		</s:bind>
		
		<!-- Periodicity -->
		<s:bind path="accrualMethods[${currIndex}].accrualPeriodicity">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="title" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.accrual.periodicity" /></label>
				<c:choose>
					<c:when test="${editMode}">
						<div class="col-sm-4">
							<span class="attribute-name-helper">accrualMethods{}.accrualPeriodicity</span>
							<select class="form-control" name="accrualMethods[${currIndex}].accrualPeriodicity" id="accrualMethods${currIndex}.accrualPeriodicity" 
								onchange="editor.tables['accrualMethodTable'].handleSelectChange(this, 'accrualMethodTable_accrualPeriodicity');" autocomplete="off">
								<c:forEach items="${accrualPeriodicities}" var="accPeriodicity">
									<c:if test="${currMethod.accrualPeriodicity==accPeriodicity.id}"><c:set var="selected">selected="selected"</c:set></c:if>
									<option ${selected} value="${accPeriodicity.id}">${accPeriodicity.label}</option>
								</c:forEach>
							</select>
						</div>
						<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
							path="accrualMethods[${currIndex}].accrualPeriodicity" />
					</c:when>
					<c:otherwise>
						<div class="col-sm-9">
							<label class="content-label">
								<c:forEach items="${accrualPeriodicities}" var="accPeriodicity">
									<c:if test="${currMethod.accrualPeriodicity==accPeriodicity.id}">${accPeriodicity.label}</c:if>
								</c:forEach>
							</label>
						</div>
					</c:otherwise>
				</c:choose>		
				
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.accrual.accrual_periodicity" />
					</div>
				</div>
			</div>
		</s:bind>
		
		<!-- Note -->
		<s:bind path="accrualMethods[${currIndex}].description">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="title" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.accrual.description" /></label>
				<div class="col-sm-9">
					<c:choose>
						<c:when test="${editMode}">
							<span class="attribute-name-helper">accrualMethods{}.description</span>
							<textarea class="form-control" rows="3" id="accrualMethods${currIndex}.description" name="accrualMethods[${currIndex}].description"><c:if test="${currMethod!=null}">${currMethod.description}</c:if></textarea>
						</c:when>
						<c:otherwise>
							<label class="content-label">${currMethod!=null ? currMethod.description : ''}</label>
						</c:otherwise>
					</c:choose>
				</div>
				<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
					path="accrualMethods[${currIndex}].description" />
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.accrual.description" />
					</div>
				</div>
			</div>
		</s:bind>
	</td>
</tr>