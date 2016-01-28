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
			<div class="col-sm-12">
				<button class="btn btn-default cancel form-btn-cancel" type="reset">~ Cancel</button>
				<button class="btn btn-primary start form-btn-submit" type="submit">~ Save</button>
			</div>
		</div>
		
		<sf:hidden path="collectionId" />
		
	</sf:form>
</div>
