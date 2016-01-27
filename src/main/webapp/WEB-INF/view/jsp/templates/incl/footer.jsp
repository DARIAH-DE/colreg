<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<footer role="contentinfo" class="footer">
	<span>&copy; <fmt:formatDate value="${date}" pattern="yyyy" /> DARIAH-DE</span>
	<ul class="pull-right inline">
		<li><a href="#"><s:message code="~eu.dariah.de.minfba.common.privacy" /></a></li>
		<li><a href="#"><s:message code="~eu.dariah.de.minfba.common.legal_information" /></a></li>
		<li><a href="#"><s:message code="~eu.dariah.de.minfba.common.contact" /></a></li>
	</ul>
</footer>