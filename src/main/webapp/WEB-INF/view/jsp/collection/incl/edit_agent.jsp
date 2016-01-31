<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tr class="list" onclick="editor.itemLanguageTable.editEntry(this); return false;">
	<c:choose>
		<c:when test="${currDesc!=null}">
			<td>${currDesc.title}</td>
			<td>${currDesc.lang}</td>
			<td>
				<button onclick="editor.itemLanguageTable.removeEntry(this); return false;" class="btn btn-xs btn-link"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
			</td>
		</c:when>
		<c:otherwise>
			<td colspan="4">~ New entry</td>
		</c:otherwise>
	</c:choose>
</tr>
<tr class="edit" style="display: none;">
	<td colspan="2">
	</td>
</tr>