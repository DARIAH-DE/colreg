<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="${pageContext.response.locale}">
	<%@ include file="incl/head.jsp" %>
	<body class="site">
		<div class="site_wrap">
	        <tiles:importAttribute name="navbarInverse" />
	        <tiles:importAttribute name="fluidLayout" />
		        
	        <!-- Top Navigation -->
	        <%@ include file="incl/topNav.jsp" %>
			
			<main class="main">
				<!-- Content -->
				<tiles:insertAttribute name="content"/>
			</main>
		
		</div>
		
		<!-- Footer -->
		<div>
			<%@ include file="incl/footer.jsp" %>
		</div>
		
		<noscript>
	        <div><s:message code="~eu.dariah.de.colreg.common.noscript" /></div>
	    </noscript>
	  	<!-- JavaScript files at the end for faster loading of documents -->
	  	<tiles:importAttribute name="scripts" />  	
	  	<c:forEach items="${scripts}" var="s">
	  		<script type="text/javascript" src="<s:url value="/resources/js/${s}" />"></script>
	  	</c:forEach>
	</body>
</html>