<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<nav id="editor-version-panel" class="sb-slidebar sb-right sb-style-overlay">
	<h3>~ Version history</h3>
	<ul>
		<c:forEach items="${versions}" var="version">
			<li class="${version.deleted ? 'version-deleted ' : (version.draftUserId!=null ? 'version-draft' : 'version-published')}">
				<a href="<s:url value="/collections/${version.id}" />">
					<joda:format value="${version.versionTimestamp}" style="LM" />${version.succeedingVersionId==null ? ' (~current)' : ''}<br />
					<span class="version-action">${version.deleted ? '~deleted ' : (version.draftUserId!=null ? '~draft' : '~published')}</span><br />
					${version.versionCreator}
				</a>
			</li>
		</c:forEach>
	</ul>
</nav>