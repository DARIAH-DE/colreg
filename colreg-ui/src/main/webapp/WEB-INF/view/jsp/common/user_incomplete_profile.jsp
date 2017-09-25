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
				<h2>Insufficient Information</h2>
				<p>Your home organisation did not provide sufficient attributes to this service.
				In 10 seconds you will be redirected to the <a target="_self" href="${redirectUrl}">DARIAH central user registry</a> and complete your profile in order to use this service.</p>
				<p>After Registration, you will be able to access your resources provided you are authorized.</p>
				
				<h3>Actions</h3>
				Either access the <strong><a target="_self" href="${redirectUrl}">DARIAH central user registry</a></strong> to complete your profile or<br />
				<strong><a target="_self" href='<s:url value="/logout" />'>Logout</a></strong> to use this tool as unauthenticated user (if possible).  
				
				<h3>More Details</h3>
				Home Organisation Name: <b>${user.endpointName}</b><br/>
				Home Organisation entityID: <b>${user.endpointId}</b><br/>
				You were trying to access the following URL: <b>${returnUrl}</b>
				
				
				
				<script type="text/javascript">
				<!--
				//setTimeout(function() { window.location = "${redirectUrl}" }, 5000);
				//-->
				</script>
			</div>
		</div>
	</div>
</div>

