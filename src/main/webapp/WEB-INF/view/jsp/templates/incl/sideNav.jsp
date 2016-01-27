<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tpl" tagdir="/WEB-INF/tags" %>

<c:forEach items="${_nav.rootItems}" var="_navItem">
	<div class="list-group nav<c:if test="${_navItem.active || _navItem.childActive}"> active</c:if>">
		<h4 class="sidebar-title">
			<c:choose>
				<c:when test="${_navItem.linkUrl!=null && fn:length(_navItem.linkUrl)>0}">
					<a href="<s:url value='${_navItem.linkUrl}'/>">
						<c:if test="${_navItem.glyphicon!=null && fn:length(_navItem.glyphicon)>0}">
							<span class="${_navItem.glyphicon}"></span>&nbsp;
						</c:if>
						<s:message code="${_navItem.displayCode}" />
					</a>
				</c:when>
				<c:otherwise>
					 <c:if test="${_navItem.glyphicon!=null && fn:length(_navItem.glyphicon)>0}">
						<span class="${_navItem.glyphicon}"></span>&nbsp;
					</c:if>
					<s:message code="${_navItem.displayCode}" />
				</c:otherwise>
			</c:choose>
	  	</h4>
	  	<c:if test="${_navItem.active || _navItem.childActive}">
	  		<tpl:sideNav navItem="${_navItem}"></tpl:sideNav>
	  	</c:if>
	</div>
</c:forEach>