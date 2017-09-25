<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<div class="container<c:if test="${fluidLayout==true}">-fluid</c:if>">
	<div class="row">
		<div id="main-content-wrapper" class="col-sm-10 col-sm-offset-1">
			<ul class="breadcrumb">
				<li><a href='<s:url value="/saml/web/metadata" />' target="_self">Metadata administration</a></li>
				<li class="active">Metadata provider detail</li>
			</ul>
			<div id="main-content">
				<div class="clearfix">
					<h2 class="pull-left">Metadata provider detail</h2>
					<div class="pull-right">
						<a class="btn btn-link btn-sm" href="<s:url value="/saml/web/metadata" />">
							<span class="glyphicon glyphicon-arrow-left"></span> back to list
						</a>
						<a class="btn btn-default btn-sm" href="<s:url value="/saml/web/metadata/removeProvider?providerIndex=${providerIndex}" />">
							<span class="glyphicon glyphicon-trash"></span> remove provider
						</a>
					</div>
				</div>
				<div>
					<div class="well">Overview of a metadata provider which can include multiple SAML entities.</div>
					<form class="form-horizontal">
  						<div class="form-group">
  							<label class="col-sm-2 control-label">Provider:</label>
							<div class="col-sm-10">
								<p class="form-control-static"><c:out value="${provider}" /></p>
							</div>
  						</div>
  					</form>
				</div>
			</div>
		</div>
	</div>
</div>