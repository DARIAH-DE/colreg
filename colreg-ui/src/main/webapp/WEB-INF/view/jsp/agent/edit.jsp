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
				<c:if test="${lastSavedVersion!=null}">
					<div class="alert alert-success" role="alert">
						<joda:format value="${lastSavedTimestamp}" style="LM" var="saveTimestamp" />
						<s:message code="~eu.dariah.de.colreg.view.agent.notification.agent_saved" arguments="${saveTimestamp}" /><br />
						<a href="#" onclick="editor.appendComment('agents/', '${lastSavedVersion}'); return false;"><s:message code="~eu.dariah.de.colreg.view.agent.labels.comment_revision" /></a>
					</div>
				</c:if>
			
			
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
					<c:forEach items="${entityWarnings}" var="warning">
						<div class="alert alert-warning">
							<s:message code="${warning}" />
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		
		<c:if test="${!isDeleted && agent.succeedingVersionId==null && _auth!=null && _auth.auth && editMode}">
			<div class="form-group editor-buttonbar">
				<div class="col-sm-12">
					<div class="pull-right">
						<a href='<s:url value="/agents/" />' class="btn btn-default cancel form-btn-cancel"><s:message code="~eu.dariah.de.colreg.common.actions.cancel" /></a>
						<button class="btn btn-primary start form-btn-submit"><s:message code="~eu.dariah.de.colreg.common.actions.save" /></button>
					</div>
				</div>
			</div>
		</c:if>
				
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4><s:message code="~eu.dariah.de.colreg.model.agent.groups.mandatory_description" /></h4>
			</div>
			
			<!-- Agent Type -->
			<s:bind path="agentTypeId">		
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="agentTypeId" class="col-sm-3 control-label mandatory"><s:message code="~eu.dariah.de.colreg.model.agent.type" /></label>
					<div id="agentTypeId-container" class="col-sm-4">
						<c:choose>
							<c:when test="${editMode}">
								<select class="form-control" name="agentTypeId" id="agentTypeId" onchange="editor.handleAgentTypeChange(this);" autocomplete="off">
									<c:forEach items="${agentTypes}" var="type">
										<option <c:if test="${agent.agentTypeId==type.id}">selected="selected"</c:if> value="${type.id}" data-natural="${type.naturalPerson}">${type.label}</option>
										<c:if test="${agent.agentTypeId==type.id}">
											<c:set var="agentIsNatural" value="${type.naturalPerson}" scope="request" />
										</c:if>
									</c:forEach>
								</select>
							</c:when>
							<c:otherwise>
								<c:forEach items="${agentTypes}" var="type">
									<c:if test="${agent.agentTypeId==type.id}">
										<label class="control-label"><a href="javascript:void(0)">${type.label}</a></label>
									</c:if>
								</c:forEach>
							</c:otherwise>
						</c:choose>						
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="agentTypeId" />
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.agent.type" />
						</div>
					</div>
				</div>
			</s:bind>	
						
			<!-- Agent name -->
			<s:bind path="name">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="name" class="col-sm-3 control-label mandatory agent-nonnatural-only" <c:if test="${agentIsNatural}"> style="display: none;"</c:if>><s:message code="~eu.dariah.de.colreg.model.agent.name" /></label>
					<label for="name" class="col-sm-3 control-label mandatory agent-natural-only" <c:if test="${!agentIsNatural}"> style="display: none;"</c:if>><s:message code="~eu.dariah.de.colreg.model.agent.last_name" /></label>
					<div id="name-container" class="col-sm-9">
						<c:if test="${editMode}"><sf:input path="name" class="form-control" /></c:if>
						<c:if test="${!editMode}">
							<label class="control-label"><a href="javascript:void(0)">${agent.name}</a></label>
						</c:if>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="name" />
					<div class="col-sm-9 col-sm-offset-3 agent-nonnatural-only" <c:if test="${agentIsNatural}"> style="display: none;"</c:if>>
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.agent.name" />
						</div>
					</div>
					<div class="col-sm-9 col-sm-offset-3 agent-natural-only" <c:if test="${!agentIsNatural}"> style="display: none;"</c:if>>
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.agent.last_name" />
						</div>
					</div>
				</div>
			</s:bind>
			
			<!-- Agent forename -->
			<s:bind path="foreName">
				<div class="form-group${status.error ? ' has-error' : ' '} agent-natural-only" <c:if test="${!agentIsNatural}"> style="display: none;"</c:if>>
					<label for="foreName" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.first_name" /></label>
					<div id="foreName-container" class="col-sm-9">
						<c:choose>
							<c:when test="${editMode}">
								<sf:input path="foreName" class="form-control" />
							</c:when>
							<c:otherwise>
								<label class="control-label"><a href="javascript:void(0)">${agent.foreName}</a></label>
							</c:otherwise>
						</c:choose>		
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="foreName" />
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.agent.first_name" />
						</div>
					</div>
				</div>
			</s:bind>
			
		</div>
				
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4><s:message code="~eu.dariah.de.colreg.model.agent.groups.extended_description" /></h4>
			</div>
			
			<!-- Agent address -->
			<s:bind path="addresses*">
				<div class="form-group" >
					<label for="tbl-agent-addresses" class="col-sm-3 control-label${status.error ? ' container-error' : ' '}"><s:message code="~eu.dariah.de.colreg.model.agent.addresses" /></label>
					<div class="col-sm-9">
						<table id="tbl-agent-addresses" class="collection-editor-table">
							<thead>
								<tr>
									<th class="explode">
										<c:choose>
											<c:when test="${fn:length(agent.addresses)==0}">
												<a class="control-link" href="javascript:void(0);"><s:message code="~eu.dariah.de.colreg.model.address.place" /></a>
											</c:when>
											<c:otherwise>
												<s:message code="~eu.dariah.de.colreg.model.address.place" />
											</c:otherwise>
										</c:choose>
									</th>
									<th class="nowrap"><s:message code="~eu.dariah.de.colreg.model.address.country" /></th>
									<c:if test="${editMode}"><th class="nowrap"></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(agent.addresses)>0}">
										<c:forEach items="${agent.addresses}" var="addr" varStatus="status" >
											<c:set var="currAddr" value="${addr}" scope="request" />
											<c:set var="currIndex" value="${status.index}" scope="request" />
											<jsp:include page="incl/edit_address.jsp" />
										</c:forEach>
										<c:remove var="currAddr" />	
									</c:when>
								</c:choose>
								<c:if test="${editMode}">
									<tr class="collection-editor-table-buttons">
										<td colspan="${editMode ? 3 : 2}">
											<button class="btn btn-xs pull-right btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.agent.actions.add_address" /></button>	
										</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="addresses" />
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.agent.addresses" />
						</div>
					</div>
				</div>
			</s:bind>
			
			<!-- Agent e-Mail -->
			<s:bind path="eMail">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="eMail" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.email" /></label>
					<div id="eMail-container" class="col-sm-9">
						<c:choose>
							<c:when test="${editMode}">
								<sf:input path="eMail" class="form-control" />
							</c:when>
							<c:otherwise>
								<label class="content-label">
									<a href="mailto:${agent.EMail}">${agent.EMail}</a>
								</label>
							</c:otherwise>
						</c:choose>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="eMail" />
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.agent.email" />
						</div>
					</div>
				</div>
			</s:bind>
			
			<!-- Agent webpage -->
			<s:bind path="webPage">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="webPage" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.webpage" /></label>
					<div id="webPage-container" class="col-sm-9">
						<c:choose>
							<c:when test="${editMode}">
								<sf:input path="webPage" class="form-control" />
							</c:when>
							<c:otherwise>
								<label class="content-label">
									<a href="${agent.webPage}">${agent.webPage}</a>
								</label>
							</c:otherwise>
						</c:choose>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="webPage" />
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.agent.webpage" />
						</div>
					</div>
				</div>
			</s:bind>
			
			<!-- Agent phone -->
			<s:bind path="phone">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="phone" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.phone" /></label>
					<div id="phone-container" class="col-sm-9">
						<c:choose>
							<c:when test="${editMode}">
								<sf:input path="phone" class="form-control" />
							</c:when>
							<c:otherwise>
								<label class="content-label"><a href="javascript:void(0);">${agent.phone}</a></label>
							</c:otherwise>
						</c:choose>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="phone" />
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.agent.phone" />
						</div>
					</div>
				</div>
			</s:bind>
		</div>
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4><s:message code="~eu.dariah.de.colreg.model.agent.groups.contextual" /></h4>
			</div>
			
			<!-- Identifiers -->
			<div class="form-group">
				<label for="lst-agent-provided-identifiers" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.provided_identifiers" /></label>
				<div id="lst-agent-provided-identifiers-container" class="col-sm-9">
					<c:choose>
						<c:when test="${editMode}">
							<ul id="lst-agent-provided-identifiers" class="collection-editor-list">
								<c:if test="${fn:length(agent.providedIdentifier)>0}">
									<c:forEach items="${agent.providedIdentifier}" var="identifier" varStatus="status" >
										<c:set var="currIdentifier" value="${identifier}" scope="request" />
										<c:set var="currIndex" value="${status.index}" scope="request" />
										<jsp:include page="incl/edit_identifier.jsp" />
									</c:forEach>
									<c:remove var="currIdentifier" />	
								</c:if>
								<c:if test="${editMode}">
									<li class="collection-editor-list-buttons">
										<div class="col-sm-12">
											<button onclick="editor.lists['identifierList'].triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.agent.actions.add_identifier" /></button>
										</div>
									</li>
								</c:if>
							</ul>
						</c:when>
						<c:otherwise>
							<label class="control-label">
								<c:forEach items="${agent.providedIdentifier}" var="identifier" varStatus="status" >
									<a href="javascript:void(0)">${identifier}</a><br/>
								</c:forEach>
								<a href="javascript:void(0)"></a>
							</label>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.agent.identifiers" />
					</div>
				</div>
			</div>
			
			<!-- Parent agent -->
			<div class="form-group">
				<label for="parentAgentId" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.parent_agent" /></label>
				<div id="parentAgentIdSelector-container" class="col-sm-9">
					<c:if test="${editMode}">
						<div class="row">
							<div class="col-sm-5">
								<input type="text" id="parentAgentIdSelector" class="form-control" placeholder="<s:message code="~eu.dariah.de.colreg.view.agent.labels.search_by_id_name" />" />
								<sf:hidden path="parentAgentId" />			
							</div>
						</div>
					</c:if>
				
					<div class="row">
						<div class="col-sm-12">
							<div class="parentAgent-display alert alert-default <c:if test="${parentAgent==null}">hide</c:if>">
								<c:if test="${editMode}">
									<button id="parentAgentIdReset" type="button" class="btn btn-xs btn-link pull-right"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
									</c:if>
									<p>
								<c:if test="${parentAgent!=null}">
									<a href="<s:url value="${parentAgent.entityId}" />"><button type="button" class="btn btn-xs btn-link pull-right"><span class="glyphicon glyphicon-link" aria-hidden="true"></span></button><strong>${parentAgent.name} ${parentAgent.foreName}</strong><br />
									<small><em>ID: ${parentAgent.entityId}</em></small><br /></a>
								</c:if>	
								</p>
							</div>
							<div class="parentAgent-display-null <c:if test="${parentAgent!=null}">hide</c:if>">
								<a href="javascript:void(0)"><label class="content-label"><em><s:message code="~eu.dariah.de.colreg.view.agent.labels.no_parent_agent_set" /></em></label></a>
							</div>
						
							<div class="editor-hint">
								<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
								<s:message code="~eu.dariah.de.colreg.editorhint.agent.parent_agent" />
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<!-- Child agents -->
			<div class="form-group">
				<label class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.child_agents" /></label>
				<div id="child-agents" class="col-sm-9">
					<c:choose>
						<c:when test="${childAgents!=null && fn:length(childAgents)>0}">
							<c:forEach items="${childAgents}" var="child" varStatus="status" >
								<div class="alert alert-default">
									<p>
										<a href="<s:url value="${child.entityId}" />"><button type="button" class="btn btn-xs btn-link pull-right"><span class="glyphicon glyphicon-link" aria-hidden="true"></span></button><strong>${child.name} ${child.foreName}</strong><br />
										<small><em>ID: ${child.entityId}</em></small><br /></a>	
									</p>
								</div>
							</c:forEach>		
						</c:when>
						<c:otherwise>
							<label class="control-label">
								<a href="javascript:void(0)"><em><s:message code="~eu.dariah.de.colreg.view.agent.labels.no_children_assigned" /></em></a> 
							</label>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.agent.child_agents" />
					</div>
				</div>
			</div>
			
			<!-- Associated collections -->
			<div class="form-group">
				<label class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.associated_collections" /></label>
				<div id="associated-collections" class="col-sm-9">
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
							<label class="control-label">
								<a href="javascript:void(0)"><em><s:message code="~eu.dariah.de.colreg.view.agent.labels.no_collections_assigned" /></em></a> 
							</label>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.agent.associated_collections" />
					</div>
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
					<div id="agent-identifier" class="col-sm-9">
						<label class="content-label">
							<a href="<s:url value="/agents/${agent.entityId}" />">${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}<s:url value="/agents/${agent.entityId}" /></a>
						</label>
					</div>
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.agent.agent_identifier" />
						</div>
					</div>
				</div>
				
				<!-- Version id -->
				<div class="form-group">
					<label class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.version_identifier" /></label>
					<div id="version-identifier" class="col-sm-9">
						<label class="content-label">
							<a href="<s:url value="/agents/${agent.id}" />">${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}<s:url value="/agents/${agent.id}" /></a>
						</label>
					</div>
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.agent.version_identifier" />
						</div>
					</div>
				</div>
	
				<!-- Version created -->
				<div class="form-group">
					<label class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.current_version" /></label>
					<div id="current-version" class="col-sm-9">
						<label class="control-label">
							<a href="javascript:void(0)"><joda:format value="${agent.versionTimestamp}" style="LM" /> (${agent.versionCreator})</a> 
						</label>
					</div>
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.agent.current_version" />
						</div>
					</div>
				</div>
	
				<!-- Entity timestamp -->
				<div class="form-group">
					<label class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.created" /></label>
					<div id="initially-created" class="col-sm-9">
						<label class="control-label">
							<a href="javascript:void(0)"><joda:format value="${agent.entityTimestamp}" style="LM" /> (${agent.entityCreator})</a> 
						</label>
					</div>
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.agent.created" />
						</div>
					</div>
				</div>
				
				<c:if test="${!isDeleted && editMode && _auth!=null && _auth.auth}">
					<div class="form-group">
						<label class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.agent.groups.administration" /></label>
						<div id="agent-administration" class="col-sm-9">
							<c:choose>
								<c:when test="${activeChildAgents==false && activeCollectionRelation==false}">
									<div class="alert alert-warning alert-sm" role="alert">
										<s:url value="/agents/${agent.entityId}" var="latest_link" />
										<s:message code="~eu.dariah.de.colreg.view.agent.notification.deletable" arguments="${agent.entityId}" />
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
		
		<c:if test="${!isDeleted && agent.succeedingVersionId==null && _auth!=null && _auth.auth && editMode}">
			<div class="form-group editor-buttonbar">
				<div class="col-sm-12">
					<div class="pull-right">
						<a href='<s:url value="/agents/" />' class="btn btn-default cancel form-btn-cancel"><s:message code="~eu.dariah.de.colreg.common.actions.cancel" /></a>
						<button class="btn btn-primary start form-btn-submit"><s:message code="~eu.dariah.de.colreg.common.actions.save" /></button>
					</div>
				</div>
			</div>
		</c:if>
		
	</sf:form>
</div>
