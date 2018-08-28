<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tpl" tagdir="/WEB-INF/tags" %>

<header class="header">
	<div class="wrap">
		<nav class="nav">
			<button type="button" class="nav_toggle -main">
				<svg class="icon">
					<use xlink:href="#icon-menu"></use>
				</svg>
				Menü
			</button>
			<div class="nav_menu -main">
				<ul class="nav_list -level-1">
					<li class="nav_item -level-1 -home">
						<a class="nav_link nav-link" href="<s:url value='/' />" title="Home">
							<svg class="icon">
								<use xlink:href="#icon-home"></use>
							</svg>
							<span class="sr-only"><s:message code="~eu.dariah.de.colreg.view.common.labels.dashboard" /></span>
						</a>
					</li>
					<c:if test="${_draftCount > 0}">
						<a class="nav_link nav-link" href="<s:url value='/drafts/' />"><s:message code="~eu.dariah.de.colreg.view.common.labels.drafts" arguments="${_draftCount}" /></a>
	    			</c:if>
					<li class="nav_item -level-1">
						<a class="nav_link nav-link" href="<s:url value='/collections/' />"><s:message code="~eu.dariah.de.colreg.titles.collections" /></a>
					</li>
					<li class="nav_item -level-1">
						<a class="nav_link nav-link" href="<s:url value='/agents/' />"><s:message code="~eu.dariah.de.colreg.titles.agents" /></a>
					</li>
					<li class="nav_item -level-1 -has-children">
						<a class="nav_link nav-link" href="javascript:"><s:message code="~eu.dariah.de.colreg.titles.vocabularies" />
							<svg class="icon">
								<use xlink:href="#icon-angle-down"></use>
							</svg>
						</a>
						<ul class="nav_list -level-2">
							<li class="nav_item -level-2">
								<a href="" class="nav_link nav-link">
									<i class="fas fa-receipt"></i> <s:message code="~eu.dariah.de.colreg.titles.vocabularies" />
								</a>
								<ul class="nav_list -level-3">
									<c:forEach items="${_vocabularies}" var="vocabulary">
										<li class="nav_item -level-3">
											<a href="<s:url value='/vocabularies/${vocabulary.id}/' />" class="nav_link">${vocabulary.localizedLabel}</a>
										</li>
									</c:forEach>
								</ul>
							</li>
						</ul>
					</li>
					<li class="nav_item -level-1 -has-children">
						<a class="nav_link nav-link" href="javascript:"><s:message code="~eu.dariah.de.colreg.view.help" />
							<svg class="icon">
								<use xlink:href="#icon-angle-down"></use>
							</svg>
						</a>
						<ul class="nav_list -level-2">
							<li class="nav_item -level-2">
								<a href="" class="nav_link nav-link">
									<i class="fas fa-question-circle"></i>
									<s:message code="~eu.dariah.de.colreg.view.help.documents" />
								</a>
								<ul class="nav_list -level-3">
									<li class="nav_item -level-3">
										<a href="https://de.dariah.eu/collection-registry" class="nav_link nav-link"><s:message code="~eu.dariah.de.colreg.view.help.portal" /></a>
									</li>
									<li class="nav_item -level-3">
										<a href="https://wiki.de.dariah.eu/display/publicde/FAQs+zur+Collection+Registry" class="nav_link nav-link"><s:message code="~eu.dariah.de.colreg.view.help.faq" /></a>
									</li>
									<li class="nav_item -level-3">
										<a href="https://wiki.de.dariah.eu/display/publicde/Die+Collection+Registry" class="nav_link nav-link"><s:message code="~eu.dariah.de.colreg.view.help.user_guide" /></a>
									</li>
									<li class="nav_item -level-3">
										<a href="https://wiki.de.dariah.eu/display/publicde/Best-Practice-Empfehlungen" class="nav_link nav-link"><s:message code="~eu.dariah.de.colreg.view.help.best_practices" /></a>
									</li>
								</ul>
							</li>
							
							<li class="nav_item -level-2">
								<a href="" class="nav_link nav-link">
									<i class="fab fa-github"></i> <s:message code="~eu.dariah.de.colreg.view.help.github" />
								</a>
								<ul class="nav_list -level-3">
									<li class="nav_item -level-3">
										<a href="https://github.com/DARIAH-DE/colreg" class="nav_link nav-link"><s:message code="~eu.dariah.de.colreg.view.help.cr_github" /></a>
									</li>
									<li class="nav_item -level-3">
										<a href="https://github.com/DARIAH-DE/DCDDM" class="nav_link nav-link"><s:message code="~eu.dariah.de.colreg.view.help.dcddm" /></a>
									</li>
								</ul>
							</li>
							
							<li class="nav_item -level-2">
								<a href="" class="nav_link nav-link">
									<i class="fas fa-code-branch"></i> <s:message code="~eu.dariah.de.colreg.view.help.progress" />
								</a>
								<ul class="nav_list -level-3">
									<li class="nav_item -level-3">
										<a href="https://minfba.de.dariah.eu/mantisbt/changelog_page.php?project_id=21" class="nav_link nav-link"><s:message code="~eu.dariah.de.colreg.view.help.changelog" /></a>
									</li>
									<li class="nav_item -level-3">
										<a href="https://minfba.de.dariah.eu/mantisbt/roadmap_page.php?project_id=21" class="nav_link nav-link"><s:message code="~eu.dariah.de.colreg.view.help.roadmap" /></a>
									</li>
								</ul>
							</li>
						</ul>
					</li>
				</ul>
			</div>
		</nav>

		<aside class="header_aside">
			<div class="language">
				<button type="button" class="language_toggle" title="Sprache wechseln">
					<span class="language_label -large">
						<svg class="icon">
							<use xlink:href="#icon-world"></use>
						</svg>
						Deutsch
					</span>
					<span class="language_label -small">de</span>
					<span class="sr-only">Sprache wechseln</span>
				</button>
				<ul class="language_list">
					<c:forEach items="${_LANGUAGES}" var="_LANGUAGE">
    					<li class="language_item">
							<a class="language_link" href="?lang=${_LANGUAGE.key}">${_LANGUAGE.value}</a>
						</li>
					</c:forEach>
				</ul>
			</div>

			<div class="account">
				<c:set var="currentUrl" value="${requestScope['javax.servlet.forward.request_uri']}" />
				
				<c:choose>
					<c:when test="${_auth==null || _auth.auth!=true}">
						<a class="account_toggle -logged-out" href="<s:url value='/login?url=${currentUrl}' />" data-real-href="<s:url value='/login?url=${currentUrl}' />" title="Login">
							<svg class="icon">
								<use xlink:href="#icon-import"></use>
							</svg>
							<span class="sr-only">Login</span>
						</a>
					</c:when>
					<c:otherwise>
						<a class="account_toggle -logged-in" href="<s:url value='/logout?url=${currentUrl}' />" data-real-href="<s:url value='/logout?url=${currentUrl}' />" title="Logout">
							<svg class="icon">
								<use xlink:href="#icon-export"></use>
							</svg>
							<span class="sr-only">Logout</span>
						</a>
					</c:otherwise>
				</c:choose>						
			</div>
		</aside>

		<nav class="nav -right">			
			<button type="button" class="nav_toggle -portal">
				<img class="nav_logo -large" src="resources/img/logos/dariah-logo-white.svg" alt="DARIAH Portal">
				<img class="nav_logo -small" src="resources/img/logos/dariah-logo-white-small.svg" alt="DARIAH Portal">
				<svg class="icon">
					<use xlink:href="#icon-angle-down"></use>
				</svg>
			</button>
			<div class="nav_menu -portal">
				<ul id="home_dropdown_menu" class="nav_list -level-2 -portal"></ul>
			</div>
		</nav>
	</div>
</header>
<h1 class="logo">
	<a class="logo_link -large" href="" title="Startseite">
		<img class="logo_logo -large" src="../resources/img/logos/dariah-de-logo-de.svg" alt="DARIAH-DE">
		<svg class="logo_logo -spike -large" xmlns="http://www.w3.org/2000/svg" width="518.74" height="246.549" viewBox="0 -12 518.74 246.549">
			<path d="M52.865 25.768c0-10.093 12.995-16.333 21.549-21.516 6.721 4.093 22.397 11.499 22.4 22.119.004 11.79-4.904 31.006-8.806 31.09-4.246.093-10.111-24.356-13.232-24.292-3.12.066-8.929 24.391-12.845 24.385-4.25-.006-9.066-20.801-9.066-31.786" />
		</svg>
	</a>
</h1>
<input id="currentUrl" type="hidden" value="${requestScope['javax.servlet.forward.request_uri']}" />
<input id="baseUrl" type="hidden" value="<s:url value="/" />" />
<input id="baseUrl2" type="hidden" value="<s:url value="/{}" />" />