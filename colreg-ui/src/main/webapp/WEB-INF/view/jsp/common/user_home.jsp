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
				<li class="active">User profile</li>
			</ul>
			<div id="main-content">
				<h2>Home</h2>
				
				<form class="form-horizontal">
					<fieldset>
						<legend>Information from your identity providers</legend>
						<div class="form-group">
							<label class="col-sm-3 control-label" for="user_endpoint">Original identity Provider: </label>
							<div class="col-sm-9">
								<p class="form-control-static">${user.endpointName}</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label" for="user_username">Username: </label>
							<div class="col-sm-9">
								<p class="form-control-static">${user.username}</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label" for="user_username">Home IdP Attributes: </label>
							<div class="col-sm-9">
								<c:if test="${user.originalAttributes!=null}">
									<ul class="form-control-static" style="padding-left: 20px;">
										<c:forEach items="${user.originalAttributes}" var="attr">
											<li><strong>${attr.friendlyName}</strong>: ${attr.value}</li>
										</c:forEach>
									</ul>
								</c:if>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label" for="user_username">Aggregated attributes: </label>
							<div class="col-sm-9">
								<c:if test="${user.aggregatedAttributes!=null}">
									<ul class="form-control-static" style="padding-left: 20px;">
										<c:forEach items="${user.aggregatedAttributes}" var="attr">
											<li><strong>${attr.friendlyName}</strong>: ${attr.value}</li>
										</c:forEach>
									</ul>
								</c:if>
							</div>
						</div>
						<legend>Your assigned privileges</legend>
						<div class="form-group">
							<label class="col-sm-3 control-label" for="user_roles">Assigned roles: </label>
							<div class="col-sm-9">
								<select class="form-control uneditable-input" multiple id="user_roles">
									<c:forEach items="${authorityList}" var="authority">
										<option>${authority.authority}</option>	
									</c:forEach>
								</select>
							</div>
						</div>
					</fieldset>
				</form>		
				
			</div>
		</div>
	</div>
</div>

