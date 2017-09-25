<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tiles:importAttribute name="fluidLayout" />

<div class="jumbotron">
	 <div class="container<c:if test="${fluidLayout==true}">-fluid</c:if>">
		<div class="row">
			<div class="xs-hidden sm-visible col-sm-3 col-lg-2 col-sm-offset-1">
      			<div class="pull-right dariah-flower-white-83">DARIAHSP Test App</div>
			</div>
		</div>
	</div>
</div>

<div class="container<c:if test="${fluidLayout==true}">-fluid</c:if>">
	<div class="row">
		<div id="main-content-wrapper" class="col-sm-10 col-sm-offset-1">
			<ul class="breadcrumb">
				<li class="active">Home</li>
			</ul>
			<div id="main-content">
				<h2>${errorHeading}</h2>
				<c:if test="${errorMsg!=null}">
					<p>
						<em>Message</em>: ${errorMsg}
					</p>
				</c:if>
				<c:if test="${errorDetail!=null}">
					<div class="alert alert-${errorLevel==null ? 'error' : errorLevel}" role="alert">${errorDetail}</div>
				</c:if>
				
				<c:if test="${hideHelpText==null||hideHelpText==false}">
					<p>Please try to reproduce the steps that led to this error and notify the DARIAH-DE helpdesk if the problem persists</p>
				</c:if>
				<pre style="display: none;">Failed URL: ${url}
Exception: ${exception.message}
<c:forEach items="${exception.stackTrace}" var="ste">
${ste}</c:forEach>
</pre>		
			</div>
		</div>
	</div>
</div>

