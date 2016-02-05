<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:importAttribute name="fluidLayout" />

<s:url value="${a.id}" var="actionPath" />

<ul class="breadcrumb">
	<li><a href='<s:url value="/" />' target="_self">~Collection Registry</a></li>
	<li><a href='<s:url value="/agents/" />' target="_self">~Agents</a></li>
	<li class="active">
		<c:choose>
			<c:when test="${a.id=='new'}">~New Agent</c:when>
			<c:otherwise>Agent Id: ${a.entityId}</c:otherwise>
		</c:choose>
	</li>
</ul>
<div id="main-content">
	<h1>~Agent Editor</h1>
	<input type="hidden" id="js-form-action" value="${actionPath}" />
	<sf:form method="POST" action="javascript:void(0);" modelAttribute="agent" class="form-horizontal" autocomplete="off">
		
		<div class="form-group">
			<div class="col-sm-12">
				<c:if test="${agent.deleted}">
					<div class="alert alert-warning" role="alert">
						~ This agent is marked deleted and is as such only accessible through its permalink   
					</div>
				</c:if>
				<div id="entity-notifications-area"></div>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-sm-12">
				<div class="pull-right">
					<button class="btn btn-default cancel form-btn-cancel" type="reset">~ Cancel</button>
					<button class="btn btn-primary start form-btn-submit">~ Save</button>
				</div>
			</div>
		</div>
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4>~Agent type</h4>
			</div>
			<c:set var="agentIsNatural" value="${agentTypes[0].naturalPerson}" scope="request" />		
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label mandatory">~Type</label>
				<div class="col-sm-4">
					<select class="form-control" name="agentTypeId" id="agentTypeId" onchange="editor.handleAgentTypeChange(this);" autocomplete="off">
						<c:forEach items="${agentTypes}" var="type">
							<option <c:if test="${agent.agentTypeId==type.id}">selected="selected"</c:if> value="${type.id}" data-natural="${type.naturalPerson}">${type.label}</option>
							<c:if test="${agent.agentTypeId==type.id}">
								<c:set var="agentIsNatural" value="${type.naturalPerson}" scope="request" />
							</c:if>
						</c:forEach>
					</select>
					<sf:errors path="agentTypeId" />
				</div>
			</div>
		</div>
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4>~Agent description</h4>
			</div>
			<s:bind path="name">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label for="description" class="col-sm-3 control-label mandatory agent-nonnatural-only" <c:if test="${agentIsNatural}"> style="display: none;"</c:if>>~Name</label>
				<label for="description" class="col-sm-3 control-label mandatory agent-natural-only" <c:if test="${!agentIsNatural}"> style="display: none;"</c:if>>~Last Name</label>
				<div class="col-sm-9">
					<sf:input path="name" class="form-control" placeholder="~Agent name" />
					<sf:errors path="*" cssClass="error" element="div" />
				</div>
			</div>
			</s:bind>
			<div class="form-group agent-natural-only" <c:if test="${!agentIsNatural}"> style="display: none;"</c:if>>
				<label for="description" class="col-sm-3 control-label">~Fore Name</label>
				<div class="col-sm-9">
					<sf:input path="foreName" class="form-control" placeholder="~Fore name" />
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Address</label>
				<div class="col-sm-9">
					<sf:input path="address" class="form-control" placeholder="~Address" />
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~e-Mail</label>
				<div class="col-sm-9">
					<sf:input path="eMail" class="form-control" placeholder="~e-Mail" />
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Webpage</label>
				<div class="col-sm-9">
					<sf:input path="webPage" class="form-control" placeholder="~Webpage" />
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Phone</label>
				<div class="col-sm-9">
					<sf:input path="phone" class="form-control" placeholder="~Phone" />
				</div>
			</div>
		</div>
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4>~Agent hierarchy</h4>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Parent or containing agent</label>
				<div class="col-sm-5">
					<input type="text" id="parentAgentIdSelector" class="form-control" placeholder="~ Quick search by name/id" />
					<sf:hidden path="parentAgentId" />
				</div>
				<div class="col-sm-9">
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
					<div id="parentAgent-display-null" class="alert alert-default <c:if test="${parentAgent!=null}">hide</c:if>">
						~ No parent agent set
					</div>
				</div>
				
			</div>
			<div class="form-group">
				<label for="child-agents" class="col-sm-3 control-label">~ Subordinate agents</label>
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
							<div class="col-sm-9">
								<label class="control-label">~ No agents have been assigned</label>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4>~Assigned collections</h4>
			</div>
			<div class="form-group">
				<label for="child-agents" class="col-sm-3 control-label">~ Associated collections</label>
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
							<div class="col-sm-9">
								<label class="control-label">~ No agents have been assigned</label>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4>~Agent identification</h4>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Collection Identifier</label>
				<div class="col-sm-9">
					<sf:input path="entityId" class="form-control" placeholder="~Identifier" readonly="true" />
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Current description version</label>
				<div class="col-sm-9">
					<input type="text" value="${agent.id}" class="form-control" readonly />
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~External identifiers</label>
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
								<button onclick="editor.lists['identifierList'].triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>~ Add identifier</button>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4>~Administrative actions</h4>
			</div>
			<div class="form-group">
				<div class="col-sm-12">
					<c:if test="${agent.deleted==false}">
						<c:choose>
							<c:when test="${activeChildAgents==false && activeCollectionRelation==false}">
								<div class="alert alert-warning alert-sm" role="alert">
									~ This agent has no subordinate agents and no active and assigned collections and could thus be marked as deleted. It will then no longer be shown in the list of agents but is still accessible via its permalink. 
								</div>
								<div>
									<button id="btn-delete-agent" class="btn btn-sm btn-danger">~ Mark deleted</button>
								</div>
							</c:when>
							<c:otherwise>
								<c:if test="${activeChildAgents}">
									<div class="alert alert-warning alert-sm" role="alert">
										~ This agent cannot be marked as deleted because there are non-deleted subordinate agents.   
									</div>
								</c:if>
								<c:if test="${activeCollectionRelation}">
									<div class="alert alert-warning alert-sm" role="alert">
										~ This agent cannot be marked as deleted because there assigned non-deleted collections.   
									</div>
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:if>
				</div>
			</div>
		</div>
		
		
		<div class="form-group">
			<div class="col-sm-12">
				<div class="pull-right">
					<button class="btn btn-default cancel form-btn-cancel" type="reset">~ Cancel</button>
					<button class="btn btn-primary start form-btn-submit">~ Save</button>
				</div>
			</div>
		</div>
	</sf:form>
</div>
