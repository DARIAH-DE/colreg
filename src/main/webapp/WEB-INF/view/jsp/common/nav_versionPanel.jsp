<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<nav id="editor-version-panel" class="sb-slidebar sb-right sb-style-overlay">
	<h3>~ Version history</h3>
	<ul>
		<c:forEach items="${versions}" var="version">
			<li class="${version.id==selectedVersionId ? 'version-selected ' : ''}${version.deleted ? 'version-deleted ' : (version.draftUserId!=null ? 'version-draft' : 'version-published')}">
				<c:choose>
					<c:when test="${version.id==selectedVersionId}">
						<joda:format value="${version.versionTimestamp}" style="LM" />${version.succeedingVersionId==null ? ' (~latest)' : ''}<br />
						<span class="version-action">${version.deleted ? '~deleted ' : (version.draftUserId!=null ? '~draft' : '~published')}</span><br />
						${version.versionCreator}
					</c:when>
					<c:otherwise>
						<a href="<s:url value="/collections/${version.succeedingVersionId==null ? version.entityId : version.id}" />">
							<joda:format value="${version.versionTimestamp}" style="LM" />${version.succeedingVersionId==null ? ' (~latest)' : ''}<br />
							<span class="version-action">${version.deleted ? '~deleted ' : (version.draftUserId!=null ? '~draft' : '~published')}</span><br />
							${version.versionCreator}
						</a>
					</c:otherwise>
				</c:choose>
			</li>
		</c:forEach>
	</ul>
</nav>