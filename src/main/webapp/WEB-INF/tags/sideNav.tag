<%@ tag description="Recursively display side navigation items" pageEncoding="UTF-8"%>
<%@ attribute name="navItem" type="eu.dariah.de.minfba.core.web.navigation.NavigationItem" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tpl" tagdir="/WEB-INF/tags" %>

<c:forEach items="${navItem.subItems}" var="navSubItem">
	<c:if test="${navItem.authRequired==false || 
					( (navItem.authRequired==true && _auth!=null && _auth.auth==true) &&
					( navItem.authMinLevel==0 || (navItem.authMinLevel<=_auth.level)) )}">
		<a href="<s:url value='${navSubItem.linkUrl}'/>" class="list-group-item<c:if test="${navSubItem.active}"> active</c:if><c:if test="${navSubItem.childActive}"> childActive</c:if>">
			<c:if test="${navSubItem.glyphicon!=null && fn:length(navSubItem.glyphicon)>0}">
				<span class="${navSubItem.glyphicon}"></span>&nbsp;
			</c:if>
			<s:message code="${navSubItem.displayCode}" />
		</a>
		<c:if test="${navSubItem.subItems!=null && fn:length(navSubItem.subItems)>0}">
			<div class="nav<c:if test="${navSubItem.childActive}"> childActive</c:if>">
		 		<tpl:sideNav navItem="${navSubItem}" />
			</div>
		</c:if>
	</c:if>
</c:forEach>