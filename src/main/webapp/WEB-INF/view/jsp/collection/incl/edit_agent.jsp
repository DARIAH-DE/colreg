<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<tr class="list">
	<td class="agentRelationTable_agentName" onclick="editor.tables['agentRelationTable'].editEntry(this); return false;">
		<c:choose>
			<c:when test="${currAgentRelation!=null}">${currAgentRelation.agent.name} ${currAgentRelation.agent.foreName}</c:when>
			<c:otherwise>~ New entry</c:otherwise>
		</c:choose>
	</td>
	<td class="agentRelationTable_agentType" onclick="editor.tables['agentRelationTable'].editEntry(this); return false;">
		<c:if test="${currAgentRelation!=null}">
			<c:forEach items="${agentRelationTypes}" var="type">
				<c:set var="contains" value="false" />
				<c:forEach items="${currAgentRelation.typeIds}" var="typeId">
					<c:if test="${typeId==type.id}">
						<c:set var="contains" value="true" />
					</c:if>
				</c:forEach>
				<c:if test="${contains}">${type.label} </c:if>
			</c:forEach>
		</c:if>
	</td>
	<td class="nowrap">
		<button onclick="editor.tables['agentRelationTable'].pushEntryUp(this); return false;" class="btn btn-xs btn-link btn-push-up"><span class="glyphicon glyphicon glyphicon-arrow-up" aria-hidden="true"></span></button>
		<button onclick="editor.tables['agentRelationTable'].pushEntryDown(this); return false;" class="btn btn-xs btn-link btn-push-down"><span class="glyphicon glyphicon glyphicon-arrow-down" aria-hidden="true"></span></button>
		<button onclick="editor.tables['agentRelationTable'].removeEntry(this); return false;" class="btn btn-xs btn-link"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
	</td>
</tr>
<tr class="edit" style="display: none;">
	<td colspan="3">
		<div class="form-group">
			<label for="description" class="col-sm-3 control-label mandatory">~Type</label>
			<div class="col-sm-5">
				<span class="attribute-name-helper">agentRelations{}.typeIds</span>
				<select class="form-control select-relation-type" id="agentRelations${currIndex}.typeIds" name="agentRelations[${currIndex}].typeIds" size="4" multiple="multiple" autocomplete="off">
					<c:forEach items="${agentRelationTypes}" var="type">
						<c:set var="contains" value="false" />
						<c:forEach items="${currAgentRelation.typeIds}" var="typeId">
							<c:if test="${typeId==type.id}">
								<c:set var="contains" value="true" />
							</c:if>
						</c:forEach>
						<option <c:if test="${contains}">selected="selected"</c:if> value="${type.id}">${type.label}</option>
					</c:forEach>
				</select>
				<input type="hidden" class="agent-type-display-helper" onchange="editor.tables['agentRelationTable'].handleInputChange(this, 'agentRelationTable_agentType');" />
			</div>
		</div>
		<div class="form-group">
			<label for="title" class="col-sm-3 control-label">~Related Agent</label>
			<div class="col-sm-9">
				<span class="attribute-name-helper">agentRelations{}.agentId</span>
				<input type="hidden" class="form-control" id="agentRelations${currIndex}.agentId" name="agentRelations[${currIndex}].agentId" 
					value="<c:if test="${currAgentRelation!=null}">${currAgentRelation.agentId}</c:if>" />
				<input type="hidden" class="agent-name-display-helper" onchange="editor.tables['agentRelationTable'].handleInputChange(this, 'agentRelationTable_agentName');" />
				<input type="text" class="form-control typeahead agent-typeahead" placeholder="~Search for agent">
			</div>
			<div class="col-sm-9 col-sm-offset-3">
				<div class="agent-display alert alert-default <c:if test="${currAgentRelation.agent==null}">hide</c:if>">
					<button type="button" class="btn btn-xs btn-link pull-right agent-reset"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
						<p>
						<c:if test="${currAgentRelation.agent!=null}">
							<a href="<s:url value="/agents/${currAgentRelation.agent.entityId}" />"><button type="button" class="btn btn-xs btn-link pull-right"><span class="glyphicon glyphicon-link" aria-hidden="true"></span></button><strong>${currAgentRelation.agent.name} ${currAgentRelation.agent.foreName}</strong><br />
							<small><em>ID: ${currAgentRelation.agent.entityId}</em></small><br />
							${currAgentRelation.agent.address}</a>
						</c:if>	
					</p>
				</div>
				<div class="agent-display-null alert alert-default <c:if test="${currAgentRelation.agent!=null}">hide</c:if>">
					~ No agent set
				</div>
			</div>
		</div>
		<div class="form-group">
			<label for="description" class="col-sm-3 control-label">~Description</label>
			<div class="col-sm-9">
				<span class="attribute-name-helper">agentRelations{}.annotation</span>
				<textarea class="form-control" rows="3" id="agentRelations${currIndex}.annotation" name="agentRelations[${currIndex}].annotation" placeholder="~Description"><c:if test="${currAgentRelation!=null}">${currAgentRelation.annotation}</c:if></textarea>
			</div>
		</div>
	</td>
</tr>