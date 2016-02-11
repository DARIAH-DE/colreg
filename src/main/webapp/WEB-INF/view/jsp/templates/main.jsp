<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="${pageContext.response.locale}">
	<%@ include file="incl/head.jsp" %>
	<body>
		<tiles:importAttribute name="sideNav" />
		<tiles:importAttribute name="collapsePanel" ignore="true" />
		<tiles:importAttribute name="sideOpts" ignore="true" />
		<tiles:importAttribute name="navbarInverse" />
		<tiles:importAttribute name="fluidLayout" />
		
		<!-- Top Navigation -->
        <%@ include file="incl/topNav.jsp" %>
        
        <c:if test="${collapsePanel!=null}">
        	<tiles:insertAttribute name="collapsePanel"/>
        	<div class="sb-toggle-right" id="collapse-panel-trigger"><span class="glyphicon glyphicon-menu-left" aria-hidden="true"></span></div>
        </c:if>
        
        <div id="sb-site">
			
			<div class="container<c:if test="${fluidLayout==true}">-fluid</c:if>">
				<div id="primary-content-area" class="row">
					<!-- Notifications -->
					<div id="notifications-area"></div>
					<div class="xs-hidden sm-hidden md-visible col-md-3">
						<div class="pull-right"> 
							<div class="sidebar">
								<div class="sidebar-logo">
									<a href="http://portal-de.dariah.eu" title="DARIAH-DE Portal">
										<img width="220" height="103" src="<s:url value="/resources/img/dariah-logo.png" />" alt="DARIAH-DE Digital Research Infrastructure for the Arts and Humanities">
									</a>
								</div>
								<c:if test="${sideNav==true}">
									<%@ include file="incl/sideNav.jsp" %>
								</c:if>
								<c:if test="${sideOpts!=null}">
									<tiles:insertAttribute name="sideOpts"/>
								</c:if>
			              	</div>
		              	</div>
		      		</div>  
		      		
		      		<div class="col-md-8 col-lg-7">
						<div id="main-content-wrapper" class="col-xs-12">
							<tiles:insertAttribute name="content"/>
						</div>
						<!-- Footer -->
						<div class="col-xs-12">
							<%@ include file="incl/footer.jsp" %>
						</div>
					</div>
				</div>
			</div>
			
			
			<noscript>
		        <div><s:message code="~eu.dariah.de.colreg.common.noscript" /></div>
		    </noscript>
	  	</div>
	  	
	  	<!-- JavaScript files at the end for faster loading of documents -->
	  	<tiles:importAttribute name="scripts" />  	
	  	<c:forEach items="${scripts}" var="s">
	  		<script type="text/javascript" src="<s:url value="/resources/js/${s}" />"></script>
	  	</c:forEach>
	</body>
</html>