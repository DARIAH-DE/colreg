<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="container<c:if test="${fluidLayout==true}">-fluid</c:if>">
	<div class="row">
		<div id="main-content-wrapper" class="col-sm-10 col-sm-offset-1">
			<ul class="breadcrumb">
				<li class="active">Metadata administration</li>
			</ul>
			<div id="main-content">
				<div class="clearfix">
				<h2 class="pull-left">Metadata administration</h2>
				<div class="pull-right">
					<form class="pull-left" action="<c:url value="/saml/web/metadata/generate"/>" method="get">
						<button id="btn-sync" class="btn btn-default btn-sm">
							<span class="glyphicon glyphicon-asterisk"></span> Generate new SP
						</button>
					</form>
					
					<form class="pull-left" action="<c:url value="/saml/web/metadata/refresh"/>">
                    	<button id="btn-sync" class="btn btn-default btn-sm">
							<span class="glyphicon glyphicon-refresh"></span> Refresh metadata
						</button>
                	</form>		
				</div>
				</div>
				<div class="clearfix">
					<div class="well">Overview of all configured metadata for local service providers and remote identity providers.</div>
					<form class="form-horizontal">
  						<div class="form-group">
  							<label class="col-sm-3 control-label">Service provider:</label>
							<div class="col-sm-9">
								<p class="form-control-static">
									<c:forEach var="entity" items="${hostedSP}">
										<a href="<c:url value="/saml/web/metadata/display"><c:param name="entityId" value="${hostedSP}"/></c:url>">
											<c:out value="${hostedSP}" />
										</a>
									</c:forEach>
									<c:if test="${empty hostedSP}"> - </c:if>
								</p>
							</div>
  						</div>
  						
  						<div class="form-group">
  							<label class="col-sm-3 control-label">Identity providers:</label>
							<div class="col-sm-9">
								<p class="form-control-static">
									<c:forEach var="entity" items="${idpList}">
										<a href="<c:url value="/saml/web/metadata/display"><c:param name="entityId" value="${entity}"/></c:url>">
											<c:out value="${entity}" />
										</a>
										<br />
									</c:forEach>
									<c:if test="${empty idpList}"> - </c:if>
								</p>
							</div>
  						</div>
  						
  						<div class="form-group">
  							<label class="col-sm-3 control-label">Metadata providers:</label>
							<div class="col-sm-9">
								<p class="form-control-static">
									<c:forEach var="entity" items="${metadata}" varStatus="status">
										<a href="<c:url value="/saml/web/metadata/provider"><c:param name="providerIndex" value="${status.index}"/></c:url>">
											<c:out value="${entity}" />
										</a>
										<br />
									</c:forEach>
									<c:if test="${empty metadata}"> - </c:if>
								</p>
							</div>
  						</div>
  					</form>
				</div>
			</div>
		</div>
	</div>
</div>