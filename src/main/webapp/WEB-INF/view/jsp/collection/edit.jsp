<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

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
		
		<!-- lang, title, description, audience, provenance -->
		
		<div class="form-group">
			<div class="col-sm-12">
				<table id="collection-table">
					<tr>
						<th>~Title</th>
						<th>~Lang</th>
					</tr>
					<c:forEach items="${c.langs}" var="lang" varStatus="status" >
						<c:set var="currLang" value="${lang}" scope="request" />
						<c:set var="currIndex" value="${status.index}" scope="request" />
						<jsp:include page="incl/edit_lang.jsp" />
					</c:forEach>			
				</table>
			</div>
		</div>
		<div class="form-group">
			<label for="description" class="col-sm-3 control-label">~Collection Type</label>
			<div class="col-sm-9">
				<sf:input path="collectionType" class="form-control" placeholder="~Collection Type" />
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
			<div class="col-sm-9">
				<sf:input path="size" class="form-control" placeholder="~Collection Size" />
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
		<legend>~Agent Information</legend>
		
		
		<legend>~Collection context</legend>
		<div class="form-group">
			<label for="description" class="col-sm-3 control-label">~Parent/Containing collection:</label>
			<div class="col-sm-9">
				<sf:input path="parentCollectionId" class="form-control" placeholder="~Parent/Containing" />
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
		
		
		
		<legend>~Service methods</legend>
		
		<legend>~Accrual methods</legend>
		
		<sf:hidden path="collectionId" />
		
	</sf:form>
</div>
