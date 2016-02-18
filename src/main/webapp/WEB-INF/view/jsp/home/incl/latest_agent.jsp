<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<li class="${version.deleted ? 'version-deleted ' : 'version-published'}">
	<a href="<s:url value="/agents/${version.succeedingVersionId==null ? version.entityId : version.id}" />">
		<joda:format value="${version.versionTimestamp}" style="LM" />
		<c:if test="${version.succeedingVersionId==null}"></c:if><br />
		${version.versionCreator}<br />
		<span class="version-action">
			<c:choose>
				<c:when test="${version.deleted}">
					<s:message code="~eu.dariah.de.colreg.common.labels.agent_deleted" />
				</c:when>
				<c:otherwise>
					<s:message code="~eu.dariah.de.colreg.common.labels.agent_published" />
				</c:otherwise>
			</c:choose>
		</span><br />
		<c:choose>
			<c:when test="${not empty version.foreName}">
				<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
			</c:when>
			<c:otherwise>
				<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
			</c:otherwise>
		</c:choose>
		<span class="version-title">${version.name}
			<c:if test="${not empty version.foreName}">${version.foreName}</c:if>
		</span>
		<c:if test="${version.versionComment!=null && version.versionComment!=''}">							
			<br /><span class="version-comment">"${version.versionComment}"</span>
		</c:if>							
	</a>
</li>