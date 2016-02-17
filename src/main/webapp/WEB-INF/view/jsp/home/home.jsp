<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<tiles:importAttribute name="fluidLayout" />

<div class="jumbotron jumbotron-small">
	 <div class="container<c:if test="${fluidLayout==true}">-fluid</c:if>">
		<div class="row">
			<!-- Notifications -->
			<div id="notifications-area" class="col-sm-10 col-sm-offset-1"></div>
			<div class="xs-hidden sm-visible col-sm-3 col-lg-2 col-sm-offset-1">
				<div class="pull-right dariah-flower-white-45"><s:message code="~eu.dariah.de.colreg.titles.colreg" /></div>
			</div>
			<div class="col-sm-6 col-lg-7 col-sm-offset-1">
				<h1><s:message code="~eu.dariah.de.colreg.titles.collection_registry" /></h1>
			</div>
		</div>
	</div>
</div>
<div class="container<c:if test="${fluidLayout==true}">-fluid</c:if>">
	<div class="row">
		<div id="main-content-wrapper" class="col-sm-10 col-sm-offset-1">
			<ul class="breadcrumb">
				<li><a href='<s:url value="/" />' target="_self"><s:message code="~eu.dariah.de.colreg.titles.collection_registry" /></a></li>
				<li class="active"><s:message code="~eu.dariah.de.colreg.titles.home" /></li>
			</ul>
			<div id="main-content">
				<div class="row">
					<div class="col-sm-12 col-md-8">
						<h2><s:message code="~eu.dariah.de.colreg.common.labels.object_network" /></h2>
						<p><s:message code="~eu.dariah.de.colreg.view.common.notification.logout.body" /></p>
						<a href="<s:url value="/" />"><s:message code="~eu.dariah.de.colreg.view.common.labels.back_to_start" /></a>
					</div>
					<div class="col-sm-12 col-md-4">
						<h2><s:message code="~eu.dariah.de.colreg.common.labels.latest_activities" /></h2>
						<a href="<s:url value="/collections/" />"><s:message code="~eu.dariah.de.colreg.common.labels.link_to_collections" /></a><br/>
						<a href="<s:url value="/agents/" />"><s:message code="~eu.dariah.de.colreg.common.labels.link_to_agents" /></a><br />
						<div id="editor-version-panel">
							<ul>
								<c:forEach items="${latest}" var="version">
									<c:set var="version" value="${version}" scope="request" />
									<c:set var="isAgent" value="false" />
									<c:catch var="exception"><c:set var="isAgent" value="${version.collectionType!=null}" /></c:catch>
									<c:choose>
										<c:when test="${isAgent}">
											<jsp:include page="incl/latest_collection.jsp" />
										</c:when>
										<c:otherwise>
											<jsp:include page="incl/latest_agent.jsp" />
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</ul>						
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>