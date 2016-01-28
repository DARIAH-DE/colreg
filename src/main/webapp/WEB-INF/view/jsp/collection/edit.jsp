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
			<c:when test="${isNew}">~New Collection</c:when>
			<c:otherwise>Collection Id: ${c.collectionId}</c:otherwise>
		</c:choose>
	</li>
</ul>
<div id="main-content">
	<h1>~Collection Editor</h1>
	<sf:form method="POST" action="${actionPath}" modelAttribute="c" class="form-horizontal">
		
		<div class="form-group">
			<div class="pull-right">
				<button class="btn btn-default cancel form-btn-cancel" type="reset">~ Cancel</button>
				<button class="btn btn-primary start form-btn-submit" type="submit">~ Save as draft</button>
				<button class="btn btn-primary start form-btn-submit" type="submit">~ Publish</button>
			</div>
		</div>
				
		<legend>~Collection Description</legend>
		
		<!-- lang, title, description, audience, provenance -->
		
		<div class="form-group">
			<label for="title" class="col-sm-3 control-label">~Language</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" id="langs0.lang" name="langs[0].lang" placeholder="~Language">
			</div>
		</div>
		<div class="form-group">
			<label for="title" class="col-sm-3 control-label">~Title</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" id="langs0.title" name="langs[0].title" placeholder="~Title">
			</div>
		</div>
		<div class="form-group">
			<label for="description" class="col-sm-3 control-label">~Description</label>
			<div class="col-sm-9">
				<textarea class="form-control" rows="3" id="langs0.description" name="langs[0].description" placeholder="~Description"></textarea>
			</div>
		</div>
		<div class="form-group">
			<label for="audience" class="col-sm-3 control-label">~Audience</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" id="langs0.audience" name="langs[0].audience" placeholder="~Audience">
			</div>
		</div>
		<div class="form-group">
			<label for="provenance" class="col-sm-3 control-label">~Provenance</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" id="langs0.provenance" name="langs[0].provenance" placeholder="~Provenance">
			</div>
		</div>
		
		
		

		
		
		
		<sf:hidden path="collectionId" />
		
	</sf:form>
</div>
