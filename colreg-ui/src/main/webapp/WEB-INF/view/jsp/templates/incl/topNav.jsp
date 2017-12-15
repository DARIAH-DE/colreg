<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tpl" tagdir="/WEB-INF/tags" %>

<header role="banner" class="navbar navbar-default navbar-static-top <c:if test="${navbarInverse==true}">navbar-inverse</c:if>">
	<div class="container<c:if test="${fluidLayout==true}">-fluid</c:if>">
		<div class="row">
			<div class="col-sm-11 col-sm-offset-1">
		    	<div class="navbar-header">
		      		<button data-target=".bs-navbar-collapse" data-toggle="collapse" type="button" class="navbar-toggle">
		        		<span class="sr-only">Toggle navigation</span>
		        		<span class="icon-bar"></span>
		        		<span class="icon-bar"></span>
		        		<span class="icon-bar"></span>
		      		</button>
		      		<a class="navbar-brand" href="http://de.dariah.eu">DARIAH-DE</a>
		    	</div>
		    	<nav role="navigation" class="collapse navbar-collapse bs-navbar-collapse">
		    		<!-- Main top navigation built from configuration -->
		    		<ul class="nav navbar-nav">
		    			<li><a href="<s:url value='/' />"><s:message code="~eu.dariah.de.colreg.view.common.labels.dashboard" /></a></li>
		    			<c:if test="${_draftCount > 0}">
		    				<li><a href="<s:url value='/drafts/' />"><s:message code="~eu.dariah.de.colreg.view.common.labels.drafts" arguments="${_draftCount}" /></a></li>
		    			</c:if>
		    			<li><a href="<s:url value='/collections/' />"><s:message code="~eu.dariah.de.colreg.titles.collections" /></a></li>
		    			<li><a href="<s:url value='/agents/' />"><s:message code="~eu.dariah.de.colreg.titles.agents" /></a></li>
		    		</ul>
		    
		    		<!-- Elements for language selection and login/logout -->
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown">
							<a aria-expanded="false" role="button" data-toggle="dropdown" class="dropdown-toggle" href="#">
								<span class="glyphicon glyphicon-question-sign"></span> <s:message code="~eu.dariah.de.colreg.view.help" /> <span class="caret"></span>
							</a>
							<ul role="menu" class="dropdown-menu">
			    				<li role="presentation"><a href="https://de.dariah.eu/collection-registry" target="_blank"><i class="fa fa-home" aria-hidden="true"></i> <s:message code="~eu.dariah.de.colreg.view.help.portal" /></a></li>
			    				<li role="presentation"><a href="https://wiki.de.dariah.eu/display/publicde/FAQs+zur+Collection+Registry" target="_blank"><i class="fa fa-question-circle" aria-hidden="true"></i> <s:message code="~eu.dariah.de.colreg.view.help.faq" /></a></li>
			    				<li role="presentation"><a href="https://wiki.de.dariah.eu/display/publicde/Die+Collection+Registry" target="_blank"><i class="fa fa-info-circle" aria-hidden="true"></i> <s:message code="~eu.dariah.de.colreg.view.help.user_guide" /></a></li>
			    				<li role="presentation"><a href="https://wiki.de.dariah.eu/display/publicde/Best-Practice-Empfehlungen" target="_blank"><i class="fa fa-info-circle" aria-hidden="true"></i> <s:message code="~eu.dariah.de.colreg.view.help.best_practices" /></a></li>
			    				<li role="separator" class="divider"></li>
			    				<li role="presentation"><a href="https://github.com/DARIAH-DE/colreg" target="_blank"><i class="fa fa-github" aria-hidden="true"></i> <s:message code="~eu.dariah.de.colreg.view.help.github" /></a></li>
			    				<li role="presentation"><a href="https://github.com/DARIAH-DE/DCDDM" target="_blank"><i class="fa fa-github" aria-hidden="true"></i> <s:message code="~eu.dariah.de.colreg.view.help.dcddm" /></a></li>
			    				<li role="separator" class="divider"></li>
			    				<li role="presentation"><a href="https://minfba.de.dariah.eu/mantisbt/changelog_page.php?project_id=21" target="_blank"><i class="fa fa-wrench" aria-hidden="true"></i> <s:message code="~eu.dariah.de.colreg.view.help.changelog" /></a></li>
			    				<li role="presentation"><a href="https://minfba.de.dariah.eu/mantisbt/roadmap_page.php?project_id=21" target="_blank"><i class="fa fa-road" aria-hidden="true"></i> <s:message code="~eu.dariah.de.colreg.view.help.roadmap" /></a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a aria-expanded="false" role="button" data-toggle="dropdown" class="dropdown-toggle" href="#">
								<span class="glyphicon glyphicon-globe"></span> <s:message code="~eu.dariah.de.colreg.titles.languages" /> <span class="caret"></span>
							</a>
							<ul role="menu" class="dropdown-menu">
								<c:forEach items="${_LANGUAGES}" var="_LANGUAGE">
			    					<li role="presentation">
			    						<a href="?lang=${_LANGUAGE.key}">
			    							<img src="<s:url value="/resources/img/flags/flag_${_LANGUAGE.key}.png" />" height="32" width="32" alt="${_LANGUAGE.value}" /> ${_LANGUAGE.value}
			    						</a>
			    					</li>
								</c:forEach>
							</ul>
						</li>

						<c:set var="currentUrl" value="${requestScope['javax.servlet.forward.request_uri']}" />
						<li id="login"<c:if test="${_auth!=null && _auth.auth==true}"> style="display: none;"</c:if>><a href="<s:url value='/login?url=${currentUrl}' />" ><span class="glyphicon glyphicon-log-in"></span>&nbsp;Login</a></li>
						<li id="logout"<c:if test="${_auth==null || _auth.auth==false}"> style="display: none;"</c:if>><a href="<s:url value='/logout?url=${currentUrl}' />" ><span class="glyphicon glyphicon-log-out"></span>&nbsp;Logout</a></li>
					</ul>
		    	</nav>
			</div>
		</div>
		<input id="currentUrl" type="hidden" value="${requestScope['javax.servlet.forward.request_uri']}" />
		<input id="baseUrl" type="hidden" value="<s:url value="/" />" />
		<input id="baseUrl2" type="hidden" value="<s:url value="/{}" />" />
	</div>
</header>