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
			<c:otherwise>Collection Id: ${c.collectionId}</c:otherwise>
		</c:choose>
	</li>
</ul>
<div id="main-content">
	<h1>~Collection Editor</h1>
	<sf:form method="POST" action="${actionPath}" modelAttribute="c" class="form-horizontal">
		
		<div class="form-group">
			<div class="col-sm-12">
				<div class="pull-right">
					<button class="btn btn-default cancel form-btn-cancel" type="reset">~ Cancel</button>
					<button class="btn btn-primary start form-btn-submit" type="submit">~ Save as draft</button>
					<button class="btn btn-primary start form-btn-submit" type="submit">~ Publish</button>
				</div>
			</div>
		</div>
				
		<legend>~Collection description</legend>
		<div class="form-group">
			<label for="description" class="col-sm-3 control-label">~Localized description sets</label>
			<div class="col-sm-9">
				<table id="tbl-collection-description-sets" class="collection-editor-table">
					<thead>
						<tr>
							<th>~Lang</th>
							<th>~Title</th>
							<th>~Other elements</th>
							<th>~</th>
						</tr>
					</thead>
					<tbody>
						<tr class="collection-editor-table-empty-placeholder">
							<td colspan="4"<c:if test="${fn:length(c.localizedDescriptions)>0}"> style="display: none;"</c:if>>~ Please provide at least one description set</td>
						</tr>
						<c:if test="${fn:length(c.localizedDescriptions)>0}">
							<c:forEach items="${c.localizedDescriptions}" var="desc" varStatus="status" >
								<c:set var="currDesc" value="${desc}" scope="request" />
								<c:set var="currIndex" value="${status.index}" scope="request" />
								<jsp:include page="incl/edit_description.jsp" />
							</c:forEach>
							<c:remove var="currDesc" />	
						</c:if>
					</tbody>
				</table>
				<div class="collection-editor-buttons">
					<button id="btn-add-description" class="btn btn-sm btn-primary cancel">~ Add description</button>
				</div>
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
				<ul>
					<li>Language 1</li>
					<li>Language 2</li>
				</ul>
			</div>
		</div>
		<div class="form-group">
			<label for="description" class="col-sm-3 control-label">~Collection Size</label>
			<div class="col-sm-3">
				<input type="number" id="size" name="size" class="form-control" placeholder="~Collection Size" />
			</div>
		</div>
		
		
		<legend>Legal information</legend>
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
		
		<legend>~Collection context</legend>
		<div class="form-group">
			<label for="description" class="col-sm-3 control-label">~Parent/Containing collection:</label>
			<div class="col-sm-9">
				<sf:input path="parentCollectionId" class="form-control" placeholder="~Parent/Containing" />
			</div>
		</div>
		<div class="form-group">
			<label for="description" class="col-sm-3 control-label">~Associated Agents</label>
			<div class="col-sm-9">
				
			</div>
		</div>
		<div class="form-group">
			<label for="description" class="col-sm-3 control-label">~Associated project</label>
			<div class="col-sm-9">
				<sf:input path="associatedProject" class="form-control" placeholder="~Associated project" />
			</div>
		</div>
		<div class="form-group">
			<label for="description" class="col-sm-3 control-label">~Provenance information</label>
			<div class="col-sm-9">
				<sf:input path="provenanceInfo" class="form-control" placeholder="~Provenance information" />
			</div>
		</div>
		

		<legend>~Collection identification</legend>
		<div class="form-group">
			<label for="description" class="col-sm-3 control-label">~Collection Identifier</label>
			<div class="col-sm-9">
				<sf:label path="collectionId" class="form-control" placeholder="~Identifier" />
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
		
		<legend>~Access &amp; Accrual</legend>
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
		
		<sf:hidden path="collectionId" />
		
	</sf:form>
</div>
