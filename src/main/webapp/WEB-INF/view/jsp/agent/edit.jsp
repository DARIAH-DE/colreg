<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<tiles:importAttribute name="fluidLayout" />

<s:url value="${agent.entityId}" var="actionPath" />

<ul class="breadcrumb">
	<li><a href='<s:url value="/" />' target="_self"><s:message code="~eu.dariah.de.colreg.titles.collection_registry" /></a></li>
	<li><a href='<s:url value="/agents/" />' target="_self"><s:message code="~eu.dariah.de.colreg.titles.agents" /></a></li>
	<li class="active">
		<c:choose>
			<c:when test="${agent.id=='new'}"><s:message code="~eu.dariah.de.colreg.titles.new_agent" /></c:when>
			<c:otherwise><s:message code="~eu.dariah.de.colreg.titles.agents" />: ${agent.entityId}</c:otherwise>
		</c:choose>
	</li>
</ul>
<div id="main-content">
	<h1><s:message code="~eu.dariah.de.colreg.titles.agent_editor" /></h1>
	<input type="hidden" id="js-form-action" value="${actionPath}" />
	<sf:form method="POST" action="javascript:void(0);" modelAttribute="agent" class="form-horizontal" autocomplete="off">
		<span id="entityId" style="display: none;">${agent.entityId}</span>
		
		<div class="form-group">
			<div class="col-sm-12">
				<c:if test="${isDeleted}">
					<div class="alert alert-danger" role="alert">
						<s:url value="/agents/${agent.entityId}" var="latest_link" />  
						<s:message code="~eu.dariah.de.colreg.view.agent.notification.agent_deleted" arguments="${latest_link}" />
					</div>
				</c:if>				
				
				<c:if test="${agent.succeedingVersionId!=null}">
					<div class="alert alert-warning" role="alert">
						<s:url value="/agents/${agent.entityId}" var="latest_link" />
						<s:message code="~eu.dariah.de.colreg.view.agent.notification.outdated_version" arguments="${latest_link}" />
					</div>
				</c:if>
				
				<div id="entity-notifications-area">
					<s:bind path="*">
					<c:if test="${status.error}">
						<div class="alert alert-danger">
							<s:message code="~eu.dariah.de.colreg.view.agent.notification.validation_errors" />
						</div>
					</c:if>
					</s:bind>
				</div>
			</div>
		</div>
		
		<div class="form-group editor-buttonbar">
			<div class="col-sm-12">
				<div class="pull-right">
					<a href='<s:url value="/agents/" />' class="btn btn-default cancel form-btn-cancel"><s:message code="~eu.dariah.de.colreg.common.actions.cancel" /></a>
					<c:if test="${!isDeleted && agent.succeedingVersionId==null}">
						<button class="btn btn-primary start form-btn-submit"><s:message code="~eu.dariah.de.colreg.common.actions.save" /></button>
					</c:if>
				</div>
			</div>
		</div>
				
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4><s:message code="~eu.dariah.de.colreg.model.agent.groups.mandatory_description" /></h4>
			</div>
			
			<!-- Agent Type -->
			<s:bind path="agentTypeId">
				<c:set var="agentIsNatural" value="${agentTypes[0].naturalPerson}" scope="request" />		
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="agentTypeId" class="col-sm-3 control-label mandatory"><s:message code="~eu.dariah.de.colreg.model.agent.type" /></label>
					<div class="col-sm-4">
						<select class="form-control" name="agentTypeId" id="agentTypeId" onchange="editor.handleAgentTypeChange(this);" autocomplete="off">
							<c:forEach items="${agentTypes}" var="type">
								<option <c:if test="${agent.agentTypeId==type.id}">selected="selected"</c:if> value="${type.id}" data-natural="${type.naturalPerson}">${type.label}</option>
								<c:if test="${agent.agentTypeId==type.id}">
									<c:set var="agentIsNatural" value="${type.naturalPerson}" scope="request" />
								</c:if>
							</c:forEach>
						</select>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="agentTypeId" />
				</div>
			</s:bind>
			
			<!-- Agent name -->
			<s:bind path="name">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="name" class="col-sm-3 control-label mandatory agent-nonnatural-only" <c:if test="${agentIsNatural}"> style="display: none;"</c:if>><s:message code="~eu.dariah.de.colreg.model.agent.name" /></label>
					<label for="name" class="col-sm-3 control-label mandatory agent-natural-only" <c:if test="${!agentIsNatural}"> style="display: none;"</c:if>><s:message code="~eu.dariah.de.colreg.model.agent.last_name" /></label>
					<div class="col-sm-9">
						<sf:input path="name" class="form-control" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="name" />
				</div>
			</s:bind>
			
			<!-- Agent forename -->
			<s:bind path="foreName">
				<div class="form-group${status.error ? ' has-error' : ' '} agent-natural-only" <c:if test="${!agentIsNatural}"> style="display: none;"</c:if>>
					<label for="foreName" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.first_name" /></label>
					<div class="col-sm-9">
						<sf:input path="foreName" class="form-control" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="foreName" />
				</div>
			</s:bind>
			
		</div>
				
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4><s:message code="~eu.dariah.de.colreg.model.agent.groups.extended_description" /></h4>
			</div>
			<!-- Agent address -->
			<s:bind path="address">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="address" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.address" /></label>
					<div class="col-sm-9">
						<sf:input path="address" class="form-control" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="address" />
				</div>
			</s:bind>
			
			<!-- Agent e-Mail -->
			<s:bind path="eMail">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="eMail" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.email" /></label>
					<div class="col-sm-9">
						<sf:input path="eMail" class="form-control" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="eMail" />
				</div>
			</s:bind>
			
			<!-- Agent webpage -->
			<s:bind path="webPage">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="webPage" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.webpage" /></label>
					<div class="col-sm-9">
						<sf:input path="webPage" class="form-control" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="webPage" />
				</div>
			</s:bind>
			
			<!-- Agent phone -->
			<s:bind path="phone">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="phone" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.phone" /></label>
					<div class="col-sm-9">
						<sf:input path="phone" class="form-control" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="phone" />
				</div>
			</s:bind>
		</div>
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4><s:message code="~eu.dariah.de.colreg.model.agent.groups.contextual" /></h4>
			</div>
			
			<!-- Parent agent -->
			<div class="form-group">
				<label for="parentAgentId" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.parent_agent" /></label>
				<div class="col-sm-5">
					<input type="text" id="parentAgentIdSelector" class="form-control" placeholder="<s:message code="~eu.dariah.de.colreg.view.agent.labels.search_by_id_name" />" />
					<sf:hidden path="parentAgentId" />
				</div>
				<div class="col-sm-9 col-sm-offset-3">
					<div id="parentAgent-display" class="alert alert-default <c:if test="${parentAgent==null}">hide</c:if>">
						<button id="parentAgentIdReset" type="button" class="btn btn-xs btn-link pull-right"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
							<p>
						<c:if test="${parentAgent!=null}">
							<a href="<s:url value="${parentAgent.entityId}" />"><button type="button" class="btn btn-xs btn-link pull-right"><span class="glyphicon glyphicon-link" aria-hidden="true"></span></button><strong>${parentAgent.name} ${parentAgent.foreName}</strong><br />
							<small><em>ID: ${parentAgent.entityId}</em></small><br />
							${parentAgent.address}</a>
						</c:if>	
						</p>
					</div>
					<div id="parentAgent-display-null" class="<c:if test="${parentAgent!=null}">hide</c:if>">
						<label class="control-label"><em><s:message code="~eu.dariah.de.colreg.view.agent.labels.no_parent_agent_set" /></em></label>
					</div>
				</div>
				
			</div>
			
			<!-- Child agents -->
			<div class="form-group">
				<label class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.child_agents" /></label>
				<div class="col-sm-9">
					<c:choose>
						<c:when test="${childAgents!=null && fn:length(childAgents)>0}">
							<c:forEach items="${childAgents}" var="child" varStatus="status" >
								<div class="alert alert-default">
									<p>
										<a href="<s:url value="${child.entityId}" />"><button type="button" class="btn btn-xs btn-link pull-right"><span class="glyphicon glyphicon-link" aria-hidden="true"></span></button><strong>${child.name} ${child.foreName}</strong><br />
										<small><em>ID: ${child.entityId}</em></small><br />
										${child.address}</a>	
									</p>
								</div>
							</c:forEach>		
						</c:when>
						<c:otherwise>
							<label class="control-label"><em><s:message code="~eu.dariah.de.colreg.view.agent.labels.no_children_assigned" /></em></label>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			
			<!-- Associated collections -->
			<div class="form-group">
				<label class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.associated_collections" /></label>
				<div class="col-sm-9">
					<c:choose>
						<c:when test="${collections!=null && fn:length(collections)>0}">
							<c:forEach items="${collections}" var="collection" varStatus="status" >
								<div class="alert alert-default">
									<p>
										<a href="<s:url value="/collections/${collection.entityId}" />"><button type="button" class="btn btn-xs btn-link pull-right"><span class="glyphicon glyphicon-link" aria-hidden="true"></span></button><strong>${collection.localizedDescriptions[0].title}</strong><br />
										<small><em>ID: ${collection.entityId}</em></small><br /></a>	
									</p>
								</div>
							</c:forEach>		
						</c:when>
						<c:otherwise>
							<label class="control-label"><em><s:message code="~eu.dariah.de.colreg.view.agent.labels.no_collections_assigned" /></em></label>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.provided_identifiers" /></label>
				<div class="col-sm-9">
					<ul id="lst-agent-provided-identifiers" class="collection-editor-list">
						<c:if test="${fn:length(agent.providedIdentifier)>0}">
							<c:forEach items="${agent.providedIdentifier}" var="identifier" varStatus="status" >
								<c:set var="currIdentifier" value="${identifier}" scope="request" />
								<c:set var="currIndex" value="${status.index}" scope="request" />
								<jsp:include page="incl/edit_identifier.jsp" />
							</c:forEach>
							<c:remove var="currIdentifier" />	
						</c:if>
						<li class="collection-editor-list-buttons">
							<div class="col-sm-12">
								<button onclick="editor.lists['identifierList'].triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.agent.actions.add_identifier" /></button>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
		
		<c:if test="${!isNew}">
			<div class="editor-section">
				<div class="editor-section-heading">
					<h4><s:message code="~eu.dariah.de.colreg.model.agent.groups.identification_and_administration" /></h4>
				</div>
				
				<!-- Entity id -->
				<div class="form-group">
					<label class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.agent_identifier" /></label>
					<div id="collection-identifier" class="col-sm-9">
						<label class="control-label">
							<a href="<s:url value="/agents/${agent.entityId}" />">${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}<s:url value="/agents/${agent.entityId}" /></a>
						</label>
					</div>
				</div>
				
				<!-- Version id -->
				<div class="form-group">
					<label class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.version_identifier" /></label>
					<div id="version-identifier" class="col-sm-9">
						<label class="control-label">
							<a href="<s:url value="/agents/${agent.id}" />">${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}<s:url value="/agents/${agent.id}" /></a>
						</label>
					</div>
				</div>
	
				<!-- Version created -->
				<div class="form-group">
					<label class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.current_version" /></label>
					<div id="current-version" class="col-sm-9">
						<label class="control-label"><joda:format value="${agent.versionTimestamp}" style="LM" /></label><br />
						<label class="control-label">${agent.versionCreator}</label>
					</div>
				</div>
	
				<!-- Entity timestamp -->
				<div class="form-group">
					<label class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.created" /></label>
					<div id="initially-created" class="col-sm-9">
						<label class="control-label"><joda:format value="${agent.entityTimestamp}" style="LM" /></label><br />
						<label class="control-label">${agent.entityCreator}</label>
					</div>
				</div>
				
				<c:if test="${!isDeleted}">
					<div class="form-group">
						<label class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.groups.administration" /></label>
						<div id="agent-administration" class="col-sm-9">
							<c:choose>
								<c:when test="${activeChildAgents==false && activeCollectionRelation==false}">
									<div class="alert alert-warning alert-sm" role="alert">
										<s:url value="/agents/${agent.entityId}" var="latest_link" />
										<s:message code="~eu.dariah.de.colreg.view.agent.notification.deletable" arguments="" />
									</div>
									<div>
										<button id="btn-delete-agent" class="btn btn-danger cancel"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span> <s:message code="~eu.dariah.de.colreg.common.actions.delete" /></button>
									</div>
								</c:when>
								<c:otherwise>
									<c:if test="${activeChildAgents}">
										<div class="alert alert-warning alert-sm" role="alert">
											<s:message code="~eu.dariah.de.colreg.view.agent.notification.not_deletable_bc_children" />
										</div>
									</c:if>
									<c:if test="${activeCollectionRelation}">
										<div class="alert alert-warning alert-sm" role="alert">
											<s:message code="~eu.dariah.de.colreg.view.agent.notification.not_deletable_bc_collections" />
										</div>
									</c:if>
								</c:otherwise>
							</c:choose>							
						</div>
					</div>
				</c:if>
			</div>		
		</c:if>
		
		
		<div class="form-group editor-buttonbar">
			<div class="col-sm-12">
				<div class="pull-right">
					<a href='<s:url value="/agents/" />' class="btn btn-default cancel form-btn-cancel"><s:message code="~eu.dariah.de.colreg.common.actions.cancel" /></a>
					<c:if test="${!isDeleted && agent.succeedingVersionId==null}">
						<button class="btn btn-primary start form-btn-submit"><s:message code="~eu.dariah.de.colreg.common.actions.save" /></button>
					</c:if>
				</div>
			</div>
		</div>
		
	</sf:form>
</div>
