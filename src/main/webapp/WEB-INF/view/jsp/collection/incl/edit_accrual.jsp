<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tr class="list">
	<c:choose>
		<c:when test="${currDesc!=null}">
			<td>${currDesc.title}</td>
			<td>${currDesc.lang}</td>
		</c:when>
	</c:choose>
</tr>
<tr>
	<td colspan="2">
	</td>
</tr>