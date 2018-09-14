<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<s:bind path="agentImages[${currIndex}]">
	<li class="collection-editor-list-item form-item${status.error ? ' has-error' : ' '}">
		<div class="collection-editor-list-input">
			<span class="attribute-name-helper">agentImages{}</span>

			<input type="hidden" class="form-control" id="agentImages${currIndex}" name="agentImages[${currIndex}]" value="${currImage.id}">

			<span class="collection-image-container">
				<a <c:if test="${currImage.imageUrl==null}">style="display: none;"</c:if> id="agent-image-preview" href="${currImage.imageUrl}" data-lightbox="agentImage" data-title="<s:message code="~eu.dariah.de.colreg.model.agent.agent_image_l" />">
					<img class="collection-image-thumb" src="${currImage.thumbnailUrl}" />
				</a>
				<img <c:if test="${currImage.imageUrl!=null}">style="display: none;"</c:if> id="agent-image-placeholder" src='<s:url value="/resources/img/page_icon_faded.png"></s:url>' />
			</span>
			<c:if test="${editMode}">
				<span class="collection-editor-list-item-buttons">
					<button onclick="editor.lists['images'].pushEntryUp(this); return false;" class="btn btn-xs btn-link btn-push-up"><span class="glyphicon glyphicon glyphicon-arrow-up" aria-hidden="true"></span></button>
					<button onclick="editor.lists['images'].pushEntryDown(this); return false;" class="btn btn-xs btn-link btn-push-down"><span class="glyphicon glyphicon glyphicon-arrow-down" aria-hidden="true"></span></button>
					<button onclick="editor.lists['images'].removeEntry(this); return false;" class="btn btn-xs btn-link"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
				</span>
			</c:if>
		</div>
		<sf:errors element="div" cssClass="validation-error col-sm-12" path="agentImages[${currIndex}]" />
	</li>
</s:bind>
