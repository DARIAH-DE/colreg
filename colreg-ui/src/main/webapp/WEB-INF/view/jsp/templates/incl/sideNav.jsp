<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tpl" tagdir="/WEB-INF/tags" %>

<div class="list-group nav active">
	<h4 class="sidebar-title"><s:message code="~eu.dariah.de.colreg.titles.collection_registry" /></h4>
	
	<a class="list-group-item${_navigationAttribute=='dashboard' ? ' active' : ''}" href="<s:url value='/' />"><s:message code="~eu.dariah.de.colreg.view.common.labels.dashboard" /></a>
	<hr />

	<c:choose>
		<c:when test="${_auth!=null && _auth.auth==true}">
			<h5 class="list-group-header"><s:message code="~eu.dariah.de.colreg.titles.collections" /></h5>
			<a class="list-group-item${_navigationAttribute=='drafts' ? ' active' : ''}" href="<s:url value='/drafts/' />"><s:message code="~eu.dariah.de.colreg.view.common.labels.drafts" arguments="${_draftCount}" /></a>
			<a class="list-group-item${_navigationAttribute=='collections' ? ' active' : ''}" href="<s:url value='/collections/' />"><s:message code="~eu.dariah.de.colreg.titles.public_collections" /></a>
			<hr />
		</c:when>
		<c:otherwise>
			<a class="list-group-item${_navigationAttribute=='collections' ? ' active' : ''}" href="<s:url value='/collections/' />"><s:message code="~eu.dariah.de.colreg.titles.collections" /></a>		
		</c:otherwise>
	</c:choose>
	<a class="list-group-item${_navigationAttribute=='agents' ? ' active' : ''}" href="<s:url value='/agents/' />"><s:message code="~eu.dariah.de.colreg.titles.agents" /></a>
</div>