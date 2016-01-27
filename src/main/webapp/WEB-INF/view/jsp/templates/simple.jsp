<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="${pageContext.response.locale}">
	<%@ include file="incl/head.jsp" %>
	<body>
        <tiles:importAttribute name="navbarInverse" />
        <tiles:importAttribute name="fluidLayout" />
        
        <!-- Top Navigation -->
        <%@ include file="incl/topNav.jsp" %>
		
		<!-- Content -->
		<tiles:insertAttribute name="content"/>
		
		<!-- Footer -->
		<div class="container<c:if test="${fluidLayout==true}">-fluid</c:if>">
			<div class="row">
				<div class="col-xs-12 col-sm-10 col-sm-offset-1">
					<%@ include file="incl/footer.jsp" %>
				</div>
			</div>
		</div>
		
		<noscript>
	        <div><s:message code="~eu.dariah.de.minfba.common.noscript" /></div>
	    </noscript>
	  	<!-- JavaScript files at the end for faster loading of documents -->
	  	<tiles:importAttribute name="scripts" />  	
	  	<c:forEach items="${scripts}" var="s">
	  		<script type="text/javascript" src="<s:url value="/resources/js/${s}" />"></script>
	  	</c:forEach>
	</body>
</html>