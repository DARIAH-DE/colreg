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
				<h1><s:message code="~eu.dariah.de.minfba.common.local_login.title" /></h1>
			</div>
		</div>
	</div>
</div>
<div class="container<c:if test="${fluidLayout==true}">-fluid</c:if>">
	<div class="row">
		<!-- Notifications -->
		<div id="notifications-area" class="col-sm-10 col-sm-offset-1"></div>
		<div id="main-content-wrapper" class="col-sm-10 col-sm-offset-1">
			<ul class="breadcrumb">
				<li class="active"><s:message code="~eu.dariah.de.minfba.common.local_login.title" /></li>
			</ul>
			<div id="main-content">
				<h2><s:message code="~eu.dariah.de.minfba.common.local_login.heading" /></h2>
				
				<c:if test="${not empty error}">
					<div class="alert alert-danger" role="alert"><s:message code="~eu.dariah.de.minfba.common.local_login.invalid" /></div>
				</c:if>
				
				<form name='loginForm' class="form-horizontal" action="<c:url value='/localsec/login' />" method='POST'>
					<input type="hidden" name="redirectUrl" id="redirectUrl" value="${redirectUrl}" />
					<div class="form-group">
						<label for="username" class="col-sm-2 control-label"><s:message code="~eu.dariah.de.minfba.common.local_login.username" /></label>
					    <div class="col-sm-4">
					     	<input type="text" class="form-control" id="username" name="username" autofocus>
					    </div>
					</div>
					<div class="form-group">
						<label for="password" class="col-sm-2 control-label"><s:message code="~eu.dariah.de.minfba.common.local_login.password" /></label>
					    <div class="col-sm-4">
					     	<input type="password" class="form-control" id="password" name="password">
					    </div>
					</div>
					<div class="form-group">
					    <div class="col-sm-offset-2 col-sm-10">
					      <button name="submit" type="submit" value="submit" class="btn btn-primary"><s:message code="~eu.dariah.de.minfba.common.local_login.signin" /></button>
					    </div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>