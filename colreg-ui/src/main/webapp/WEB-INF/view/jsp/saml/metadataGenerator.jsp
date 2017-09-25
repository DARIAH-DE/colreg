<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="eu.dariah.de.dariahsp.saml.web.controller.MetadataController"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<div class="container<c:if test="${fluidLayout==true}">-fluid</c:if>">
	<div class="row">
		<div id="main-content-wrapper" class="col-sm-10 col-sm-offset-1">
			<ul class="breadcrumb">
				<li><a href='<s:url value="/saml/web/metadata" />' target="_self">Metadata administration</a></li>
				<li class="active">Metadata generation</li>
			</ul>
			<div id="main-content">
				<sf:form commandName="metadata" class="form-horizontal" action="create">
					<div class="clearfix">
						<h2 class="pull-left">Metadata generation</h2>
						<div class="pull-right">
							<a class="pull-left btn btn-link btn-sm" href="<s:url value="/saml/web/metadata" />">
								<span class="glyphicon glyphicon-arrow-left"></span> back to list
							</a>
							<button class="btn btn-default btn-sm">
								<span class="glyphicon glyphicon-check"></span> Generate metadata
							</button>
						</div>
					</div>
					<div class="clearfix">
						<div class="well">Generates new metadata for service provider. Output can be used to configure your securityContext.xml descriptor.</div>
						<sf:errors cssClass="alert alert-danger" element="div" path="*" />
						
						<div class="form-group">
  							<label for="store" class="col-sm-3 control-label">Store for current session:</label>
							<div class="col-sm-3">
                                <sf:select class="form-control" id="store" path="store" multiple="false">
                                    <sf:option value="true">Yes</sf:option>
                                    <sf:option value="false">No</sf:option>
                                </sf:select>
							</div>
							<div class="col-sm-9 col-sm-offset-3">
								<p class="form-control-static"><small>When set to true the generated metadata will be stored in the local metadata manager. The value will be available only until restart of the application server.</small></p>
								<sf:errors cssClass="error" element="div" path="store" />
							</div>
  						</div>
						<div class="form-group">
  							<label for="entityId" class="col-sm-3 control-label">Entity ID:</label>
							<div class="col-sm-9">
                                <sf:input cssClass="text form-control" id="entityId" path="entityId"/>
							</div>
							<div class="col-sm-9 col-sm-offset-3">
								<p class="form-control-static"><small>Entity ID is a unique identifier for an identity or service provider. Value is included in the generated metadata.</small></p>
								<sf:errors cssClass="error" element="div" path="entityId" />
							</div>
  						</div>
						<div class="form-group">
  							<label for="baseURL" class="col-sm-3 control-label">Entity base URL:</label>
							<div class="col-sm-9">
                                <sf:input cssClass="text form-control" id="baseURL" path="baseURL"/>
							</div>
							<div class="col-sm-9 col-sm-offset-3">
								<p class="form-control-static"><small>Base to generate URLs for this server. For example: https://myServer:443/saml-app. The public address your server will be accessed from should be used here.</small></p>
								<sf:errors cssClass="error" element="div" path="baseURL" />
							</div>
  						</div>
  						
						<div class="form-group">
  							<label for="alias" class="col-sm-3 control-label">Entity alias:</label>
							<div class="col-sm-5">
                                <sf:input cssClass="text form-control" id="alias" path="alias"/>
							</div>
							<div class="col-sm-9 col-sm-offset-3">
								<p class="form-control-static"><small>Alias is an internal mechanism allowing collocating multiple service providers on one server. When set, alias must be unique.</small></p>
								<sf:errors cssClass="error" element="div" path="alias" />
							</div>
  						</div>
					
						
						<div class="form-group">
  							<label for="signingKey" class="col-sm-3 control-label">Signing key:</label>
							<div class="col-sm-6">
                                <sf:select path="signingKey" cssClass="form-control" id="signingKey" items="${availableKeys}"/>
							</div>
							<div class="col-sm-9 col-sm-offset-3">
								<sf:errors cssClass="error" element="div" path="signingKey" />
								<p class="form-control-static"><small>Key used for digital signatures of SAML messages. Public key will be included in the metadata.</small></p>
							</div>
  						</div>
  						
  						<div class="form-group">
  							<label for="encryptionKey" class="col-sm-3 control-label">Encryption key:</label>
							<div class="col-sm-6">
                                <sf:select path="encryptionKey" cssClass="form-control" id="encryptionKey" items="${availableKeys}"/>
							</div>
							<div class="col-sm-9 col-sm-offset-3">
								<sf:errors cssClass="error" element="div" path="encryptionKey" />
								<p class="form-control-static"><small>Key used for digital encryption of SAML messages. Public key will be included in the metadata.</small></p>
							</div>
  						</div>
						
						<div class="form-group">
  							<label for="securityProfile" class="col-sm-3 control-label">Signature security profile:</label>
							<div class="col-sm-3">
                                 <sf:select cssClass="form-control" path="securityProfile" id="securityProfile" multiple="false">
                                     <sf:option value="metaiop">MetaIOP</sf:option>
                                     <sf:option value="pkix">PKIX</sf:option>
                                 </sf:select>
							</div>
							<div class="col-sm-9 col-sm-offset-3">
								<sf:errors cssClass="error" element="div" path="securityProfile" />
								<p class="form-control-static">
									<small>
                                        Security profile determines how is trust of digital signatures handled:
                                        <ul>
                                            <li>
                                                In <a href="http://wiki.oasis-open.org/security/SAML2MetadataIOP">MetaIOP</a> mode certificate is
                                                deemed
                                                valid when it's declared in the metadata or extended metadata of the peer entity. No validation of
                                                the certificate is
                                                performed (e.g. revocation) and no certificate chains are evaluated. The value is recommended as a
                                                default.
                                            </li>
                                            <li>
                                                PKIX profile verifies credentials against a set of trust anchors. Certificates present in the
                                                metadata or extended metadata of the peer entity are treated as trust anchors, together with all
                                                keys in
                                                the keystore. Certificate chains are verified in this mode.
                                            </li>
                                        </ul>
                                    </small>
								</p>
							</div>
  						</div>
						
						<div class="form-group">
							<label for="sslSecurityProfile" class="col-sm-3 control-label">SSL/TLS security profile:</label>
							<div class="col-sm-3">
                                 <sf:select cssClass="form-control" path="sslSecurityProfile" id="sslSecurityProfile" multiple="false">
                                     <sf:option value="pkix">PKIX</sf:option>
                                     <sf:option value="metaiop">MetaIOP</sf:option>
                                 </sf:select>
							</div>
							<div class="col-sm-9 col-sm-offset-3">
								<sf:errors cssClass="error" element="div" path="sslSecurityProfile" />
								<p class="form-control-static">
									<small>
                                        SSL/TLS Security profile determines how is trust of peer's SSL/TLS certificate (e.g. during Artifact
                                        resolution) handled:
                                        <ul>
                                            <li>
                                                PKIX profile verifies peer's certificate against a set of trust anchors. All certificates defined in
                                                metadata,
                                                extended metadata or present in the keystore are considered as trusted anchors (certification
                                                authorities)
                                                for PKIX validation.
                                            </li>
                                            <li>
                                                In MetaIOP mode server's SSL/TLS certificate is trusted when it's explicitly declared in metadata or
                                                extended metadata of
                                                the peer.
                                            </li>
                                        </ul>
                                    </small>
								</p>
							</div>
  						</div>
  						
  						
  						
  						<div class="form-group">
  							<label for="sslHostnameVerification" class="col-sm-3 control-label">SSL/TLS hostname verification:</label>
							<div class="col-sm-6">
                                <sf:select path="sslHostnameVerification" cssClass="form-control" id="sslHostnameVerification" multiple="false">
                                    <sf:option value="default">Standard hostname verifier</sf:option>
                                    <sf:option value="defaultAndLocalhost">Standard hostname verifier (skips verification for localhost)</sf:option>
                                    <sf:option value="strict">Strict hostname verifier</sf:option>
                                    <sf:option value="allowAll">Disable hostname verification (allow all)</sf:option>
                                </sf:select>
							</div>
							<div class="col-sm-9 col-sm-offset-3">
								<p class="form-control-static"><small>Algorithm for verification of match between hostname in URL and hostname in the presented certificate.</small></p>
								<sf:errors cssClass="error" element="div" path="sslHostnameVerification" />
							</div>
  						</div>
  						
  						<div class="form-group">
  							<label for="tlsKey" class="col-sm-3 control-label">SSL/TLS client authentication:</label>
							<div class="col-sm-6">
                                <sf:select cssClass="form-control" path="tlsKey" id="tlsKey">
                                    <sf:option value="">None</sf:option>
                                    <sf:options items="${availableKeys}"/>
                                </sf:select>
							</div>
							<div class="col-sm-9 col-sm-offset-3">
								<p class="form-control-static"><small>Key used to authenticate this instance for SSL/TLS connections.</small></p>
								<sf:errors cssClass="error" element="div" path="tlsKey" />
							</div>
  						</div>
  						
  						<div class="form-group">
  							<label for="signMetadata" class="col-sm-3 control-label">Sign metadata:</label>
							<div class="col-sm-6">
                                 <sf:select cssClass="form-control" path="signMetadata" id="signMetadata" multiple="false">
                                     <sf:option value="true">Yes</sf:option>
                                     <sf:option value="false">No</sf:option>
                                 </sf:select>
							</div>
							<div class="col-sm-9 col-sm-offset-3">
								<p class="form-control-static"><small>If true the generated metadata will be digitally signed using the specified signature key.</small></p>
								<sf:errors cssClass="error" element="div" path="tlsKey" />
							</div>
  						</div>
  						
  						
  						<div class="form-group">
  							<label for="signingAlgorithm" class="col-sm-3 control-label">Sign metadata:</label>
							<div class="col-sm-6">
                                <sf:input cssClass="form-control" id="signingAlgorithm" path="signingAlgorithm"/>
							</div>
							<div class="col-sm-9 col-sm-offset-3">
								<p class="form-control-static">
									<small>Algorithm used for creation of digital signature on metadata. Typical values are
                                        "http://www.w3.org/2000/09/xmldsig#rsa-sha1",
                                        "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256" and "http://www.w3.org/2001/04/xmldsig-more#rsa-sha512"
                                    </small>
								</p>
								<sf:errors cssClass="error" element="div" path="signingAlgorithm" />
							</div>
  						</div>
  						
  						<div class="form-group">
  							<label for="requestSigned" class="col-sm-3 control-label">Sign sent AuthNRequests:</label>
							<div class="col-sm-3">
                                <sf:select cssClass="form-control" path="requestSigned" id="requestSigned" multiple="false">
                                    <sf:option value="true">Yes</sf:option>
                                    <sf:option value="false">No</sf:option>
                                </sf:select>
							</div>
							<div class="col-sm-9 col-sm-offset-3">
								<sf:errors cssClass="error" element="div" path="requestSigned" />
							</div>
  						</div>
  						
  						<div class="form-group">
  							<label for="wantAssertionSigned" class="col-sm-3 control-label">Require signed authentication Assertion:</label>
							<div class="col-sm-3">
                                <sf:select cssClass="form-control" path="wantAssertionSigned" id="wantAssertionSigned" multiple="false">
                                    <sf:option value="true">Yes</sf:option>
                                    <sf:option value="false">No</sf:option>
                                </sf:select>
							</div>
							<div class="col-sm-9 col-sm-offset-3">
								<sf:errors cssClass="error" element="div" path="wantAssertionSigned" />
							</div>
  						</div>
  					
						<div class="form-group">
  							<label for="requireAttributeQuerySigned" class="col-sm-3 control-label">Require signed attribute Assertion:</label>
							<div class="col-sm-3">
                                <sf:select cssClass="form-control" path="requireAttributeQuerySigned" id="requireAttributeQuerySigned" multiple="false">
                                    <sf:option value="true">Yes</sf:option>
                                    <sf:option value="false">No</sf:option>
                                </sf:select>
							</div>
							<div class="col-sm-9 col-sm-offset-3">
								<sf:errors cssClass="error" element="div" path="requireAttributeQuerySigned" />
							</div>
  						</div>

  						<div class="form-group">
  							<label for="requireLogoutRequestSigned" class="col-sm-3 control-label">Require signed LogoutRequest:</label>
							<div class="col-sm-3">
                                <sf:select cssClass="form-control" path="requireLogoutRequestSigned" id="requireLogoutRequestSigned" multiple="false">
                                    <sf:option value="true">Yes</sf:option>
                                    <sf:option value="false">No</sf:option>
                                </sf:select>
							</div>
							<div class="col-sm-9 col-sm-offset-3">
								<sf:errors cssClass="error" element="div" path="requireLogoutRequestSigned" />
							</div>
  						</div>
  						
  						<div class="form-group">
  							<label for="requireLogoutResponseSigned" class="col-sm-3 control-label">Require signed LogoutResponse:</label>
							<div class="col-sm-3">
                                <sf:select cssClass="form-control" path="requireLogoutResponseSigned" id="requireLogoutResponseSigned" multiple="false">
                                    <sf:option value="true">Yes</sf:option>
                                    <sf:option value="false">No</sf:option>
                                </sf:select>
							</div>
							<div class="col-sm-9 col-sm-offset-3">
								<sf:errors cssClass="error" element="div" path="requireLogoutResponseSigned" />
							</div>
  						</div>
  						
  						<div class="form-group">
  							<label for="requireArtifactResolveSigned" class="col-sm-3 control-label">Require signed ArtifactResolve:</label>
							<div class="col-sm-3">
                                <sf:select cssClass="form-control" path="requireArtifactResolveSigned" id="requireArtifactResolveSigned" multiple="false">
                                    <sf:option value="true">Yes</sf:option>
                                    <sf:option value="false">No</sf:option>
                                </sf:select>
							</div>
							<div class="col-sm-9 col-sm-offset-3">
								<sf:errors cssClass="error" element="div" path="requireArtifactResolveSigned" />
							</div>
  						</div>
  						
  						<div class="form-group">
  							<label class="col-sm-3 control-label">Single sign-on bindings:</label>
							<div class="col-sm-9">
                                <table>
	                                <tr>
	                                    <th>Default&nbsp;</th>
	                                    <th>Included&nbsp;</th>
	                                    <th>Name&nbsp;</th>
	                                </tr>
	                                <tr>
	                                    <td><sf:radiobutton path="ssoDefaultBinding" value="<%= MetadataController.AllowedSSOBindings.SSO_POST %>"/></td>
	                                    <td><sf:checkbox path="ssoBindings" value="<%= MetadataController.AllowedSSOBindings.SSO_POST %>" id="sso_0"/></td>
	                                    <td><label for="sso_0">SSO HTTP-POST</label></td>
	                                </tr>
	                                <tr>
	                                    <td><sf:radiobutton path="ssoDefaultBinding" value="<%= MetadataController.AllowedSSOBindings.SSO_ARTIFACT %>"/></td>
	                                    <td><sf:checkbox path="ssoBindings" value="<%= MetadataController.AllowedSSOBindings.SSO_ARTIFACT %>" id="sso_1"/></td>
	                                    <td><label for="sso_1">SSO Artifact</label></td>
	                                </tr>
	                                <tr>
	                                    <td><sf:radiobutton path="ssoDefaultBinding" value="<%= MetadataController.AllowedSSOBindings.SSO_PAOS %>"/></td>
	                                    <td><sf:checkbox path="ssoBindings" value="<%= MetadataController.AllowedSSOBindings.SSO_PAOS %>" id="sso_2"/></td>
	                                    <td><label for="sso_2">SSO PAOS</label></td>
	                                </tr>
	                                <tr>
	                                    <td><sf:radiobutton path="ssoDefaultBinding" value="<%= MetadataController.AllowedSSOBindings.HOKSSO_ARTIFACT %>"/></td>
	                                    <td><sf:checkbox path="ssoBindings" value="<%= MetadataController.AllowedSSOBindings.HOKSSO_ARTIFACT %>" id="sso_3"/></td>
	                                    <td><label for="sso_3">HoK SSO Artifact</label></td>
	                                </tr>
	                                <tr>
	                                    <td><sf:radiobutton path="ssoDefaultBinding" value="<%= MetadataController.AllowedSSOBindings.HOKSSO_POST %>"/></td>
	                                    <td><sf:checkbox path="ssoBindings" value="<%= MetadataController.AllowedSSOBindings.HOKSSO_POST %>" id="sso_4"/></td>
	                                    <td><label for="sso_4">HoK SSO HTTP-POST</label></td>
	                                </tr>
	                            </table>
	                            <sf:errors cssClass="error" element="div" path="ssoBindings"/>
	                            <sf:errors cssClass="error" element="div" path="ssoDefaultBinding"/>
							</div>
  						</div>
  						<div class="form-group">
  							<label class="col-sm-3 control-label">Supported NameIDs:</label>
							<div class="col-sm-9">
								<label for="nameid_0"><sf:checkbox path="nameID" value="urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress" id="nameid_0"/> E-Mail</label><br />
								<label for="nameid_1"><sf:checkbox path="nameID" value="urn:oasis:names:tc:SAML:2.0:nameid-format:transient" id="nameid_1"/> Transient</label><br />
								<label for="nameid_2"><sf:checkbox path="nameID" value="urn:oasis:names:tc:SAML:2.0:nameid-format:persistent" id="nameid_2"/> Persistent</label><br />
								<label for="nameid_3"><sf:checkbox path="nameID" value="urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified" id="nameid_3"/> Unspecified</label><br />
								<label for="nameid_4"><sf:checkbox path="nameID" value="urn:oasis:names:tc:SAML:1.1:nameid-format:X509SubjectName" id="nameid_4"/> X509 Subject</label>	                                
	                            <sf:errors cssClass="error" element="div" path="nameID"/>
							</div>
						</div>
							
							
						<div class="form-group">
  							<label for="includeDiscovery" class="col-sm-3 control-label">Enable IDP discovery profile:</label>
							<div class="col-sm-3">
                                <sf:select cssClass="form-control" path="includeDiscovery" id="includeDiscovery" multiple="false">
                                    <sf:option value="true">Yes</sf:option>
                                    <sf:option value="false">No</sf:option>
                                </sf:select>
							</div>
							<div class="col-sm-9 col-sm-offset-3">
								<p class="form-control-static">
									<small><a href="http://docs.oasis-open.org/security/saml/Post2.0/sstc-saml-idp-discovery.pdf">Discovery profile</a> enables service provider to determine which identity provider should be used for a particular user. Spring Security SAML contains it's own discovery service which presents user with an IDP list to select from.</small>
								</p>
								<sf:errors cssClass="error" element="div" path="includeDiscovery" />
							</div>
  						</div>
						
						
						<div class="form-group">
  							<label for="customDiscoveryURL" class="col-sm-3 control-label">Custom URL for IDP discovery:</label>
							<div class="col-sm-6">
                                <sf:input cssClass="form-control" id="customDiscoveryURL" path="customDiscoveryURL"/>
							</div>
							<div class="col-sm-9 col-sm-offset-3">
								<p class="form-control-static"><small>When not set local IDP discovery URL is automatically generated when IDP discovery is enabled.</small></p>
								<sf:errors cssClass="error" element="div" path="customDiscoveryURL" />
							</div>
  						</div>
						
						
						<div class="form-group">
  							<label for="includeDiscovery" class="col-sm-3 control-label">Include IDP discovery extension in metadata:</label>
							<div class="col-sm-3">
                                <sf:select cssClass="form-control" path="includeDiscoveryExtension" id="includeDiscoveryExtension" multiple="false">
                                    <sf:option value="true">Yes</sf:option>
                                    <sf:option value="false">No</sf:option>
                                </sf:select>
							</div>
							<div class="col-sm-9 col-sm-offset-3">
								<sf:errors cssClass="error" element="div" path="includeDiscoveryExtension" />
							</div>
  						</div>
						
						<div class="form-group">
    						<div class="col-sm-offset-3 col-sm-9">
    							<div class="pull-right">
									<a class="pull-left btn btn-link btn-sm" href="<s:url value="/saml/web/metadata" />">
										<span class="glyphicon glyphicon-arrow-left"></span> back to list
									</a>
									<button class="btn btn-default btn-sm">
										<span class="glyphicon glyphicon-check"></span> Generate metadata
									</button>
								</div>
    						</div>
    					</div>
						
					</div>
				</sf:form>
			</div>
		</div>
	</div>
</div>