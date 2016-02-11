<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tpl" tagdir="/WEB-INF/tags" %>

<div class="list-group nav active">
	<h4 class="sidebar-title"><s:message code="~eu.dariah.de.colreg.titles.collection_registry" /></h4>
	<a class="list-group-item" href="<s:url value='/' />"><s:message code="~eu.dariah.de.colreg.view.common.labels.dashboard" /></a>
	<hr />
	
	<c:choose>
		<c:when test="${_auth!=null && _auth.auth==true}">
			<a class="list-group-item" href="<s:url value='/collections/' />"><s:message code="~eu.dariah.de.colreg.titles.collections" /></a>
			<div class="list-group-item" class="nav">
				<a class="list-group-item" href="<s:url value='/collections/' />"><s:message code="~eu.dariah.de.colreg.titles.public_collections" /></a>
				<a class="list-group-item" href="<s:url value='/drafts/' />"><s:message code="~eu.dariah.de.colreg.view.common.labels.drafts" arguments="12" /></a>
			</div>
		</c:when>
		<c:otherwise>
			<a class="list-group-item" href="<s:url value='/collections/' />"><s:message code="~eu.dariah.de.colreg.titles.collections" /></a>		
		</c:otherwise>
	</c:choose>
	<a class="list-group-item" href="<s:url value='/agents/' />"><s:message code="~eu.dariah.de.colreg.titles.agents" /></a>
</div>