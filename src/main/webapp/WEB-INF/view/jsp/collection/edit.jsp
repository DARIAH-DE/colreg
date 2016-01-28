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
			<div class="pull-right">
				<button class="btn btn-default cancel form-btn-cancel" type="reset">~ Cancel</button>
				<button class="btn btn-primary start form-btn-submit" type="submit">~ Save as draft</button>
				<button class="btn btn-primary start form-btn-submit" type="submit">~ Publish</button>
			</div>
		</div>
				
		<legend>~Collection Description</legend>
		
		<!-- lang, title, description, audience, provenance -->
		
		<table id="collection-table">
			<tr>
				<th>Title</th>
				<th>Lang</th>
			</tr>
			<c:forEach items="${c.langs}" var="lang" varStatus="status" >
				<c:set var="currLang" value="${lang}" scope="request" />
				<c:set var="currIndex" value="${status.index}" scope="request" />
				<jsp:include page="incl/edit_lang.jsp" />
			</c:forEach>
			<c:remove var="currLang" />
			<c:set var="currIndex" value="${currIndex+1}" scope="request" />
			<jsp:include page="incl/edit_lang.jsp" />
			
		</table>
		
		
		
		
		

		
		
		
		<sf:hidden path="collectionId" />
		
	</sf:form>
</div>
