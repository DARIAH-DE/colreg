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
			<!-- Notifications -->
			<div id="notifications-area" class="col-sm-8"></div>
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
				<li class="active">Home</li>
			</ul>
			<div id="main-content">
				<h2><s:message code="~eu.dariah.de.colreg.common.exception.not_found.title" /></h2>
				<p>
					<s:message code="~eu.dariah.de.colreg.common.exception.not_found.message" arguments="${url}" />
					<ul>
						<li><a href="<s:url value='/' />"><s:message code="~eu.dariah.de.colreg.view.common.labels.dashboard" /></a></li>
		    			<c:if test="${_draftCount > 0}">
		    				<li><a href="<s:url value='/drafts/' />"><s:message code="~eu.dariah.de.colreg.view.common.labels.drafts" arguments="${_draftCount}" /></a></li>
		    			</c:if>
		    			<li><a href="<s:url value='/collections/' />"><s:message code="~eu.dariah.de.colreg.titles.collections" /></a></li>
		    			<li><a href="<s:url value='/agents/' />"><s:message code="~eu.dariah.de.colreg.titles.agents" /></a></li>
					</ul>
				</p>
			</div>
		</div>
	</div>
</div>

