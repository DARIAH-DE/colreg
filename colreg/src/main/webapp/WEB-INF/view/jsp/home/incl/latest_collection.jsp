<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<li class="${version.deleted ? 'version-deleted ' : (version.draftUserId!=null ? 'version-draft' : 'version-published')}">
	<c:if test="${version.draftUserId==null || version.draftUserId==_auth.userId}">
		<a href="<s:url value="/collections/${version.succeedingVersionId==null ? version.entityId : version.id}" />">
	</c:if>
		<joda:format value="${version.versionTimestamp}" style="LM" /><br />
		${version.versionCreator}<br />
		<span class="version-action">
			<c:choose>
				<c:when test="${version.deleted}">
					<s:message code="~eu.dariah.de.colreg.common.labels.collection_deleted" />
				</c:when>
				<c:when test="${version.draftUserId!=null}">
					<s:message code="~eu.dariah.de.colreg.common.labels.draft_saved" />
				</c:when>
				<c:otherwise>
					<s:message code="~eu.dariah.de.colreg.common.labels.collection_published" />
				</c:otherwise>
			</c:choose>
		</span><br />		
		<c:if test="${version.draftUserId==null || version.draftUserId==_auth.userId}">		
			<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span> 
			<span class="version-title">${version.localizedDescriptions[0].title}</span>
			<c:if test="${version.versionComment!=null && version.versionComment!=''}">							
				<br /><span class="version-comment">"${version.versionComment}"</span>
			</c:if>
		</c:if>
		
	<c:if test="${version.draftUserId==null}">
		</a>
	</c:if>
</li>