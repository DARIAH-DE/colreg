<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:importAttribute name="fluidLayout" />

<s:url value="${c.id}" var="actionPath" />

<ul class="breadcrumb">
	<li><a href='<s:url value="/" />' target="_self">~Collection Registry</a></li>
	<li><a href='<s:url value="/collections/" />' target="_self">~Collections</a></li>
	<li class="active">
		<c:choose>
			<c:when test="${c.id=='new'}">~New Collection</c:when>
			<c:otherwise>Collection Id: ${c.entityId}</c:otherwise>
		</c:choose>
	</li>
</ul>
<div id="main-content">
	<h1>~Collection Editor</h1>
	<input type="hidden" id="js-form-action" value="${actionPath}" />
	<sf:form method="POST" action="javascript:void(0);" modelAttribute="c" class="form-horizontal" autocomplete="off">

		<div class="form-group editor-buttonbar">
			<div class="col-sm-12">
				<div class="pull-right">
					<button class="btn btn-default cancel form-btn-cancel">~ Cancel</button>
					<button class="btn btn-primary start form-btn-submit">~ Save as draft</button>
					<button class="btn btn-primary start form-btn-submit">~ Publish</button>
				</div>
			</div>
		</div>
				
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4>~Collection description</h4>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Localized description sets</label>
				<div class="col-sm-9">
					<table id="tbl-collection-description-sets" class="collection-editor-table">
						<thead>
							<tr>
								<th class="explode">~Title</th>
								<th class="nowrap">~Acronym</th>
								<th class="nowrap">~Language</th>
								<th class="nowrap">~</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${fn:length(c.localizedDescriptions)>0}">
								<c:forEach items="${c.localizedDescriptions}" var="desc" varStatus="status" >
									<c:set var="currDesc" value="${desc}" scope="request" />
									<c:set var="currIndex" value="${status.index}" scope="request" />
									<jsp:include page="incl/edit_description.jsp" />
								</c:forEach>
								<c:remove var="currDesc" />	
							</c:if>
							<tr class="collection-editor-table-buttons">
								<td colspan="4" style="text-align: right;">
									<button class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>~ Add description set</button>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Collection Type</label>
				<div class="col-sm-9">
					<sf:input path="typeId" class="form-control" placeholder="~Collection Type" />
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Web Page</label>
				<div class="col-sm-9">
					<sf:input path="webPage" class="form-control" placeholder="~Web Page" />
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~ Item Languages</label>
				<div class="col-sm-9">
					<ul id="lst-collection-item-languages" class="collection-editor-list">
						<c:if test="${fn:length(c.itemLanguages)>0}">
							<c:forEach items="${c.itemLanguages}" var="lang" varStatus="status" >
								<c:set var="currLang" value="${lang}" scope="request" />
								<c:set var="currIndex" value="${status.index}" scope="request" />
								<jsp:include page="incl/edit_itemlanguage.jsp" />
							</c:forEach>
							<c:remove var="currDesc" />	
						</c:if>
						<li class="collection-editor-list-buttons">
							<div class="col-sm-12">
								<button onclick="editor.itemLanguageList.triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>~ Add item language</button>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Collection Size</label>
				<div class="col-sm-3">
					<input type="number" id="size" name="size" class="form-control" placeholder="~Collection Size" />
				</div>
			</div>
		</div>
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4>~Legal information</h4>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Collection Description Rights</label>
				<div class="col-sm-9">
					<sf:input path="collectionDescriptionRights" class="form-control" placeholder="~Collection Description Rights" />
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Access Rights</label>
				<div class="col-sm-9">
					<sf:input path="accessRights" class="form-control" placeholder="~Access Rights" />
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Item Rights</label>
				<div class="col-sm-9">
					<sf:input path="itemRights" class="form-control" placeholder="~Item Rights" />
				</div>
			</div>
		</div>
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4>~Collection description</h4>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Associated Agents</label>
				<div class="col-sm-9">
					<table id="tbl-collection-agents" class="collection-editor-table">
						<thead>
							<tr>
								<th class="explode">~Agent name</th>
								<th class="explode">~Relation</th>
								<th class="nowrap">~</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${fn:length(c.agentRelations)>0}">
								<c:forEach items="${c.agentRelations}" var="agentRelation" varStatus="status" >
									<c:set var="currAgentRelation" value="${agentRelation}" scope="request" />
									<c:set var="currIndex" value="${status.index}" scope="request" />
									<jsp:include page="incl/edit_agent.jsp" />
								</c:forEach>
								<c:remove var="currAgentRelation" />	
							</c:if>
							<tr class="collection-editor-table-buttons">
								<td colspan="4" style="text-align: right;">
									<button class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>~ Add agent relation</button>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Associated project</label>
				<div class="col-sm-9">
					<sf:input path="associatedProject" class="form-control" placeholder="~Associated project" />
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Parent/Containing collection:</label>
				<div class="col-sm-5">
					<input type="text" id="parentCollectionIdSelector" class="form-control" placeholder="~ Quick search by name/id" />
					<sf:hidden path="parentCollectionId" />
				</div>
				<div class="col-sm-9">
					<div id="parentCollection-display" class="alert alert-default <c:if test="${parentCollection==null}">hide</c:if>">
						<button id="parentCollectionIdReset" type="button" class="btn btn-xs btn-link pull-right"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
							<p>
						<c:if test="${parentCollection!=null}">
							<a href="<s:url value="${parentCollection.entityId}" />"><button type="button" class="btn btn-xs btn-link pull-right"><span class="glyphicon glyphicon-link" aria-hidden="true"></span></button><strong>${parentCollection.localizedDescriptions[0].title}</strong><br />
							<small><em>ID: ${parentCollection.entityId}</em></small></a>
						</c:if>	
						</p>
					</div>
					<div id="parentCollection-display-null" class="alert alert-default <c:if test="${parentCollection!=null}">hide</c:if>">
						~ No parent collection set
					</div>
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Provenance information</label>
				<div class="col-sm-9">
					<sf:input path="provenanceInfo" class="form-control" placeholder="~Provenance information" />
				</div>
			</div>
		</div>

		<div class="editor-section">
			<div class="editor-section-heading">
				<h4>~Collection identification</h4>
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
					<sf:input path="id" class="form-control" placeholder="~Identifier" readonly="true" />
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~External identifiers</label>
				<div class="col-sm-9">
					<ul id="lst-collection-provided-identifiers" class="collection-editor-list">
						<c:if test="${fn:length(c.providedIdentifier)>0}">
							<c:forEach items="${c.providedIdentifier}" var="identifier" varStatus="status" >
								<c:set var="currIdentifier" value="${identifier}" scope="request" />
								<c:set var="currIndex" value="${status.index}" scope="request" />
								<jsp:include page="incl/edit_identifier.jsp" />
							</c:forEach>
							<c:remove var="currIdentifier" />	
						</c:if>
						<li class="collection-editor-list-buttons">
							<div class="col-sm-12">
								<button onclick="editor.identifierList.triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>~ Add identifier</button>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4>~Access &amp; Accrual</h4>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Data access</label>
				<div class="col-sm-9">
					<table id="tbl-collection-access" class="collection-editor-table">
						<thead>
							<tr>
								<th class="explode">~URI</th>
								<th class="nowrap">~Type</th>
								<th class="nowrap">~</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${fn:length(c.accessMethods)>0}">
								<c:forEach items="${c.accessMethods}" var="method" varStatus="status" >
									<c:set var="currMethod" value="${method}" scope="request" />
									<c:set var="currIndex" value="${status.index}" scope="request" />
									<jsp:include page="incl/edit_access.jsp" />
								</c:forEach>
								<c:remove var="currMethod" />	
							</c:if>
							<tr class="collection-editor-table-buttons">
								<td colspan="3" style="text-align: right;">
									<button class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>~ Add access method</button>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Data accrual</label>
				<div class="col-sm-9">
					<table id="tbl-collection-accrual" class="collection-editor-table">
						<thead>
							<tr>
								<th class="explode">~Method</th>
								<th class="nowrap">~Policy</th>
								<th class="nowrap">~</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${fn:length(c.accrualMethods)>0}">
								<c:forEach items="${c.accrualMethods}" var="method" varStatus="status" >
									<c:set var="currMethod" value="${method}" scope="request" />
									<c:set var="currIndex" value="${status.index}" scope="request" />
									<jsp:include page="incl/edit_accrual.jsp" />
								</c:forEach>
								<c:remove var="currMethod" />	
							</c:if>
							<tr class="collection-editor-table-buttons">
								<td colspan="3" style="text-align: right;">
									<button class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>~ Add accrual method</button>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		
		
				<div class="form-group editor-buttonbar">
			<div class="col-sm-12">
				<div class="pull-right">
					<button class="btn btn-default cancel form-btn-cancel">~ Cancel</button>
					<button class="btn btn-primary start form-btn-submit">~ Save as draft</button>
					<button class="btn btn-primary start form-btn-submit">~ Publish</button>
				</div>
			</div>
		</div>
				
	</sf:form>
</div>
