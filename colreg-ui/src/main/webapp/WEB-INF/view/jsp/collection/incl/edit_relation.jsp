<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<s:bind path="relations[${currIndex}].*">
	<tr class="list${status.error ? ' has-error' : ' '}">
		<td class="relationTable_relationName" onclick="editor.tables['relations'].editEntry(this); return false;">
			<c:choose>
				<c:when test="${currRelation!=null}">
					<a class="control-link" href="javascript:void(0);">${currRelation.agent.name} ${currRelation.agent.foreName}</a>
				</c:when>
				<c:otherwise><s:message code="~eu.dariah.de.colreg.common.labels.new_entry" /></c:otherwise>
			</c:choose>
		</td>
		<td class="relationTable_agentType" onclick="editor.tables['relationTable'].editEntry(this); return false;">
			<c:if test="${currRelation!=null}">
				<c:forEach items="${agentRelationTypes}" var="type">
					<c:set var="contains" value="false" />
					<c:forEach items="${currRelation.typeIds}" var="typeId">
						<c:if test="${typeId==type.id}">
							<c:set var="contains" value="true" />
						</c:if>
					</c:forEach>
					<c:if test="${contains}">${type.label} </c:if>
				</c:forEach>
			</c:if>
		</td>
		<c:if test="${editMode}">
			<td class="nowrap">
				<button onclick="editor.tables['relationTable'].pushEntryUp(this); return false;" class="btn btn-xs btn-link btn-push-up"><span class="glyphicon glyphicon glyphicon-arrow-up" aria-hidden="true"></span></button>
				<button onclick="editor.tables['relationTable'].pushEntryDown(this); return false;" class="btn btn-xs btn-link btn-push-down"><span class="glyphicon glyphicon glyphicon-arrow-down" aria-hidden="true"></span></button>
				<button onclick="editor.tables['relationTable'].removeEntry(this); return false;" class="btn btn-xs btn-link"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
			</td>
		</c:if>
	</tr>
</s:bind>
<tr class="edit" style="display: none;">
	<td colspan="${editMode ? 3 : 2}">
		<s:bind path="relations[${currIndex}].typeIds">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="description" class="col-sm-3 control-label mandatory"><s:message code="~eu.dariah.de.colreg.model.agent_relation.relation" /></label>
				<c:choose>
					<c:when test="${editMode}">
						<div class="col-sm-5">
							<span class="attribute-name-helper">relations{}.typeIds</span>
							<select class="form-control select-relation-type" id="relations${currIndex}.typeIds" name="relations[${currIndex}].typeIds" size="4" multiple="multiple" autocomplete="off">
								<c:forEach items="${agentRelationTypes}" var="type">
									<c:set var="contains" value="false" />
									<c:forEach items="${currRelation.typeIds}" var="typeId">
										<c:if test="${typeId==type.id}">
											<c:set var="contains" value="true" />
										</c:if>
									</c:forEach>
									<c:set var="selected"></c:set>
									<c:if test="${contains}"><c:set var="selected">selected="selected"</c:set></c:if>
									<option ${selected} value="${type.id}">${type.label}</option>
								</c:forEach>
							</select>
							<input type="hidden" class="agent-type-display-helper" onchange="editor.tables['relationTable'].handleInputChange(this, 'relationTable_agentType');" />
						</div>
					</c:when>
					<c:otherwise>
						<div class="col-sm-9">
							<label class="content-label">
								<c:forEach items="${agentRelationTypes}" var="type">
									<c:forEach items="${currRelation.typeIds}" var="typeId">
										<c:if test="${typeId==type.id}">${type.label}<br /></c:if>
									</c:forEach>
								</c:forEach>
							</label>
						</div>
					</c:otherwise>
				</c:choose>
				
				<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
					path="relations[${currIndex}].typeIds" />
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.agent_relation.relation" />
					</div>
				</div>
			</div>
		</s:bind>
		<s:bind path="relations[${currIndex}].agentId">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="title" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent_relation.agent" /></label>
				<div class="col-sm-9">
					<c:if test="${editMode}">
						<div class="row">
							<div class="col-sm-12">
								<span class="attribute-name-helper">relations{}.agentId</span>
								<input type="hidden" class="form-control" id="relations${currIndex}.agentId" name="relations[${currIndex}].agentId" 
									value="<c:if test="${currRelation!=null}">${currRelation.agentId}</c:if>" />
								<input type="hidden" class="agent-name-display-helper" onchange="editor.tables['relationTable'].handleInputChange(this, 'relationTable_agentName');" />
								<input type="text" class="form-control typeahead agent-typeahead" placeholder="<s:message code="~eu.dariah.de.colreg.view.collection.labels.type_to_search" />" />
							</div>
						</div>
					</c:if>
					<div class="row">
						<div class="col-sm-12">
							<div class="agent-display alert alert-default <c:if test="${currRelation.agent==null}">hide</c:if>">
								<c:if test="${editMode}">
									<button type="button" class="btn btn-xs btn-link pull-right agent-reset"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
								</c:if>
								<p>
									<c:if test="${currRelation.agent!=null}">
										<a href="<s:url value="/agents/${currRelation.agent.entityId}" />"><button type="button" class="btn btn-xs btn-link pull-right"><span class="glyphicon glyphicon-link" aria-hidden="true"></span></button><strong>${currRelation.agent.name} ${currRelation.agent.foreName}</strong><br />
										<small><em>ID: ${currRelation.agent.entityId}</em></small></a>
									</c:if>	
								</p>
							</div>
							<div class="agent-display-null <c:if test="${currRelation.agent!=null}">hide</c:if>">
								<label class="control-label"><em><s:message code="~eu.dariah.de.colreg.view.collection.labels.no_agent_set" /></em></label><br />
							</div>
						
							<sf:errors element="div" cssClass="validation-error" path="relations[${currIndex}].agentId" />
							<s:url value="/agents/new" var="newAgentUrl" />
							<div class="editor-hint">
								<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
								<s:message code="~eu.dariah.de.colreg.editorhint.agent_relation.agent" arguments="${newAgentUrl}" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</s:bind>
		<s:bind path="relations[${currIndex}].annotation">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="description" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent_relation.description" /></label>
				<div class="col-sm-9">
					<c:choose>
						<c:when test="${editMode}">
							<span class="attribute-name-helper">relations{}.annotation</span>
							<textarea class="form-control" rows="3" id="relations${currIndex}.annotation" name="relations[${currIndex}].annotation"><c:if test="${currRelation!=null}">${currRelation.annotation}</c:if></textarea>
						</c:when>
						<c:otherwise>
							<label class="content-label">${currRelation!=null ? currRelation.annotation : ''}</label>
						</c:otherwise>
					</c:choose>
				</div>
				<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" 
					path="relations[${currIndex}].annotation" />
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.agent_relation.annotation" />
					</div>
				</div>
			</div>
		</s:bind>
	</td>
</tr>