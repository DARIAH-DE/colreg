<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 

<div class="container<c:if test="${fluidLayout==true}">-fluid</c:if>">
	<div class="row">
		<div id="main-content-wrapper" class="col-sm-10 col-sm-offset-1">
			<ul class="breadcrumb">
				<li><a href='<s:url value="/saml/web/metadata" />' target="_self">Metadata administration</a></li>
				<li class="active">Metadata detail</li>
			</ul>
			<div id="main-content">
				<div class="clearfix">
					<h2 class="pull-left">Metadata detail</h2>
					<div class="pull-right">
						<a class="pull-left btn btn-link btn-sm" href="<s:url value="/saml/web/metadata" />">
							<span class="glyphicon glyphicon-arrow-left"></span> back to list
						</a>
						<c:choose>
							<c:when test="${metadata.alias != null}">
								<form class="pull-left" action="<c:url value="/saml/metadata/alias/${metadata.alias}"/>" method="get">
									<button class="btn btn-default btn-sm">
										<span class="glyphicon glyphicon-download-alt"></span> Download entity metadata
									</button>
								</form>
							</c:when>
							<c:otherwise>
								<form class="pull-left" action="<c:url value="/saml/metadata"/>" method="get">
									<button class="btn btn-default btn-sm">
										<span class="glyphicon glyphicon-download-alt"></span> Download entity metadata
									</button>
								</form>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="clearfix">
					<div class="well">Detail of a single entity imported to Spring SAML's MetadataManager</div>
					<c:if test="${metadata.local}">
	                	<div class="alert alert-info" role="alert">
							<strong>In order to use this metadata follow these instructions:</strong>
							<ul>
								<li>Download entity metadata and put in a directory that is accessible from the application server</li>
								<li>Copy the supplied configuration properties and paste in application configuration file</li>
								<li>Make sure to <strong>enable SAML support</strong> for the application by providing -Dsaml=true as environment parameter</li>
							</ul>
						</div>
					</c:if>
					
					<sf:form commandName="metadata" class="form-horizontal">
						<div class="form-group">
  							<label class="col-sm-3 control-label">Local entity:</label>
							<div class="col-sm-9">
								<p class="form-control-static">
									<c:out value="${metadata.local}"/>
								</p>
							</div>
  						</div>
  						
  						<div class="form-group">
  							<label class="col-sm-3 control-label">Entity ID:</label>
							<div class="col-sm-9">
								<p class="form-control-static">
									<c:out value="${metadata.entityId}"/>
								</p>
							</div>
  						</div>
  						<c:if test="${metadata.local eq true}">
	  						<div class="form-group">
	  							<label class="col-sm-3 control-label">Entity alias:</label>
								<div class="col-sm-9">
									<p class="form-control-static">
										<c:out value="${metadata.alias}"/>
									</p>
								</div>
	  						</div>
  						</c:if>
  						
  						<div class="form-group">
  							<label class="col-sm-3 control-label">Metadata:</label>
							<div class="col-sm-9">
								<textarea id="metadata" class="form-control" rows="7" readonly="readonly"><c:out value="${metadata.serializedMetadata}"/></textarea>
							</div>
  						</div>
  						<!-- Uncomment to see spring context configuration -->
  						<!-- <div class="form-group">
  							<label class="col-sm-3 control-label">Configuration:</label>
							<div class="col-sm-9">
								<textarea id="configuration" readonly="readonly" class="form-control" rows="7"><c:out value="${metadata.configuration}"/></textarea>
							</div>
  						</div> -->
  						
  						<c:if test="${metadata.local}">
	  						<div class="form-group">
	  							<label class="col-sm-3 control-label">Configuration:</label>
								<div class="col-sm-9">
									<textarea id="configuration" readonly="readonly" class="form-control" rows="12"># Append these properties to your application configuration
auth:
  ...
  saml:
    ...
    sp:
      # Modify this property to point to the downloaded SP metadata
      externalMetadata: /path/to/downloaded/spring-saml-metadata.xml
      alias: ${metadata.alias}
      baseUrl: ${baseUrl}
      entityId: ${metadata.entityId}
      securityProfile: ${metadata.securityProfile}
      sslSecurityProfile: ${metadata.sslSecurityProfile}
      sslHostnameVerification: ${metadata.sslHostnameVerification}
      signMetadata: ${metadata.signMetadata}
      signingAlgorithm: ${metadata.signingAlgorithm}
      signingKey: ${metadata.signingKey}
      encryptionKey: ${metadata.encryptionKey}
      tlsKey: ${metadata.tlsKey}
      requireArtifactResolveSigned: ${metadata.requireArtifactResolveSigned}
      requireAttributeQuerySigned: ${metadata.requireAttributeQuerySigned}
      requireLogoutRequestSigned: ${metadata.requireLogoutRequestSigned}
      requireLogoutResponseSigned: ${metadata.requireLogoutResponseSigned}
      discovery:
        enabled: ${metadata.includeDiscovery}
        url: ${metadata.customDiscoveryURL}
        return: ${metadata.customDiscoveryResponseURL}
      allowedNameIds: <c:forEach begin="0" end="${fn:length(metadata.nameID) - 1}" var="index"><c:out value="${metadata.nameID[index]}"/><c:if test="${index < fn:length(metadata.nameID)-1}">, </c:if></c:forEach>
</textarea>
								</div>
	  						</div>
  						</c:if>
  						
  						
  						
                	</sf:form>
                	
				</div>
			</div>
		</div>
	</div>
</div>