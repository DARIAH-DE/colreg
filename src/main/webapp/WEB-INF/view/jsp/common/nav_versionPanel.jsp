<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<nav id="editor-version-panel" class="sb-slidebar sb-right sb-style-overlay${version.deleted ? ' deleted' : ''} ${version.draftUserId!=null ? ' draft' : ''}">
	<h1>~ Collection versions</h1>
	<ul>
		<c:forEach items="${versions}" var="version">
			<li>
				<joda:format value="${version.versionTimestamp}" style="LM" /><br />
				${version.versionCreator}<br />
				<a href="<s:url value="/collections/${version.id}" />">${version.id}</a>
			</li>
		</c:forEach>
	</ul>
</nav>