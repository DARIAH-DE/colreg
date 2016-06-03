<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<footer role="contentinfo" class="footer">
	<span>&copy; <fmt:formatDate value="${date}" pattern="yyyy" /> DARIAH-DE</span>
	<ul class="pull-right inline">
		<li><a href="#"><s:message code="~eu.dariah.de.colreg.common.privacy" /></a></li>
		<li><a href="#"><s:message code="~eu.dariah.de.colreg.common.legal_information" /></a></li>
		<li><a href="#"><s:message code="~eu.dariah.de.colreg.common.contact" /></a></li>
		<li><a target="_blank" href="http://github.com/tgradl/colreg/">
			<img width="24" height="24" alt="ColReg GitHub Repository" src="<s:url value="/resources/img/github-mark.png" />">
		</a></li>
	</ul>
</footer>