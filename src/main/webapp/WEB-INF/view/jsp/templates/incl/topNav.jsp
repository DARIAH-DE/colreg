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
		      		<a class="navbar-brand" href="../">DARIAH-DE</a>
		    	</div>
		    	<nav role="navigation" class="collapse navbar-collapse bs-navbar-collapse">
		    		<!-- Main top navigation built from configuration -->
		    		<ul class="nav navbar-nav">
		    			<li><a href="<s:url value='/collections/' />">Collections</a></li>
		    			<li><a href="<s:url value='/agents/' />">Agents</a></li>
		    		</ul>
		    
		    		<!-- Elements for language selection and login/logout -->
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown">
							<a aria-expanded="false" role="button" data-toggle="dropdown" class="dropdown-toggle" href="#">
								<span class="glyphicon glyphicon-globe"></span> Language <span class="caret"></span>
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

						<li id="login"<c:if test="${_auth!=null && _auth.auth==true}"> style="display: none;"</c:if>><a href="<s:url value='${_loginUrl}' />" ><span class="glyphicon glyphicon-log-in"></span>&nbsp;<s:message code="~eu.dariah.de.colreg.common.login" /></a></li>
						<li id="logout"<c:if test="${_auth==null || _auth.auth==false}"> style="display: none;"</c:if>><a href="<s:url value='${_logoutUrl}' />" ><span class="glyphicon glyphicon-log-out"></span>&nbsp;<s:message code="~eu.dariah.de.colreg.common.logout" /></a></li>		
					</ul>
		    	</nav>
			</div>
		</div>
		<input id="baseUrl" type="hidden" value="<s:url value="/" />" />
	</div>
</header>