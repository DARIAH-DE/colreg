<%@ tag description="Recursively display top navigation items" pageEncoding="UTF-8"%>
<%@ attribute name="navItem" type="eu.dariah.de.minfba.core.web.navigation.NavigationItem" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags" %>

<c:forEach items="${navItem.subItems}" var="navSubItem" varStatus="loopStatus">
	<c:if test="${navSubItem.authRequired==false || 
					( (navSubItem.authRequired==true && _auth!=null && _auth.auth==true) &&
					( navSubItem.authMinLevel==0 || (navSubItem.authMinLevel<=_auth.level)) )}">
		<c:choose>
			<c:when test="${navSubItem.groupHeader==true}">
				<c:if test="${loopStatus.index>0}">
					<li role="presentation" class="divider"></li>
				</c:if>
				<li role="presentation" class="dropdown-header">
					<c:if test="${navSubItem.glyphicon!=null && fn:length(navSubItem.glyphicon)>0}">
						<span class="${navSubItem.glyphicon}"></span>&nbsp;
					</c:if>
					<s:message code="${navSubItem.displayCode}" />
				</li>
				<c:if test="${navSubItem.subItems!=null && fn:length(navSubItem.subItems)>0}">
					<c:forEach items="${navSubItem.subItems}" var="navSubSubItem">
						<li role="presentation">
							<a role="menuitem" tabindex="-1" href="<s:url value='${navSubSubItem.linkUrl}'/>">
								<c:if test="${navSubSubItem.glyphicon!=null && fn:length(navSubSubItem.glyphicon)>0}">
									<span class="${navSubSubItem.glyphicon}"></span>&nbsp;
								</c:if>
								<s:message code="${navSubSubItem.displayCode}" />
							</a>
						</li>
					</c:forEach>
				</c:if>
			</c:when>
			<c:otherwise>
				<li role="presentation">
					<a role="menuitem" tabindex="-1" href="<s:url value='${navSubItem.linkUrl}'/>">
						<c:if test="${navSubItem.glyphicon!=null && fn:length(navSubItem.glyphicon)>0}">
							<span class="${navSubItem.glyphicon}"></span>&nbsp;
						</c:if>
						<s:message code="${navSubItem.displayCode}" />
					</a>
				</li>
			</c:otherwise>
		</c:choose>
	</c:if>
</c:forEach>