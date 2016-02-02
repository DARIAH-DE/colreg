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
	<sf:form method="POST" action="${actionPath}" modelAttribute="c" class="form-horizontal" autocomplete="off">
		
		<div class="form-group">
			<div class="col-sm-12">
				<div class="pull-right">
					<button class="btn btn-default cancel form-btn-cancel" type="reset">~ Cancel</button>
					<button class="btn btn-primary start form-btn-submit" type="submit">~ Save as draft</button>
					<button class="btn btn-primary start form-btn-submit" type="submit">~ Publish</button>
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
					<table id="tbl-collection-item-languages" class="collection-editor-table">
						<thead>
							<tr>
								<th class="explode">~Lang</th>
								<th class="nowrap">~</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${fn:length(c.itemLanguages)>0}">
								<c:forEach items="${c.itemLanguages}" var="lang" varStatus="status" >
									<c:set var="currLang" value="${lang}" scope="request" />
									<c:set var="currIndex" value="${status.index}" scope="request" />
									<jsp:include page="incl/edit_itemlanguage.jsp" />
								</c:forEach>
								<c:remove var="currDesc" />	
							</c:if>
							<tr class="collection-editor-table-buttons">
								<td colspan="4" style="text-align: right;">
									<button class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>~ Add item language</button>
								</td>
							</tr>
						</tbody>
					</table>
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
				<div class="col-sm-9">
					<sf:input path="parentCollectionId" class="form-control" placeholder="~Parent/Containing" />
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
					<sf:label path="entityId" class="form-control" placeholder="~Identifier" />
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Current description version</label>
				<div class="col-sm-9">
					<sf:label path="id" class="form-control" placeholder="~Identifier" />
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~External identifier</label>
				<div class="col-sm-9">
					<sf:input path="providedIdentifier" class="form-control" placeholder="~External identifier" />
				</div>
			</div>
		</div>
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4>~Access &amp; Accrual</h4>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Access version</label>
				<div class="col-sm-9">
					
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Accrual</label>
				<div class="col-sm-9">
					
				</div>
			</div>
		</div>
		
		<sf:hidden path="entityId" />
		
	</sf:form>
</div>
