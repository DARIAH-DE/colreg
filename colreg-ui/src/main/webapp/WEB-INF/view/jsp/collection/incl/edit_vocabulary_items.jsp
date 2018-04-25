<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<s:bind path="${vocabularyIdentifier}*">
	<div class="form-group${status.error ? ' container-error' : ' '}">				
		<label for="lst-collection-${vocabularyIdentifier}" class="col-sm-3 control-label linked-control-label">
			<a href="<s:url value='/vocabularies/${vocabularyId}/' />"><i class="fa fa-link" aria-hidden="true"></i> <s:message code="${vocabularyMessageCode}" /></a>
		</label>
		<div id="lst-collection-${vocabularyIdentifier}-container" class="col-sm-9">
			<c:choose>
				<c:when test="${editMode}">
					<ul id="lst-collection-${vocabularyIdentifier}" class="collection-editor-list">
						<c:choose>
							<c:when test="${fn:length(vocabularyModelItems)>0}">
								<c:forEach items="${vocabularyModelItems}" var="vocabularyModelItem" varStatus="status" >
									<c:set var="currIndex" value="${status.index}" scope="request" />
									<jsp:include page="edit_vocabulary_item.jsp" />
								</c:forEach>
								<c:remove var="currType" />	
							</c:when>
							<c:otherwise>
								<c:set var="currIndex" value="0" scope="request" />
								<jsp:include page="edit_vocabulary_item.jsp" />
							</c:otherwise>
						</c:choose>
						<c:if test="${editMode}">
							<li class="collection-editor-list-buttons">
								<div class="col-sm-12">
									<button onclick="editor.lists['${vocabularyIdentifier}'].triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="${vocabularyAddEntryCode}" /></button>
								</div>
							</li>
						</c:if>
					</ul>
				</c:when>
				<c:otherwise>
					<c:forEach items="${vocabularyModelItems}" var="vocabularyModelItem" varStatus="status" >
						<label class="content-label">
							<c:forEach items="${availableVocabularyItems}" var="availableVocabularyItem">
								<c:if test="${availableVocabularyItem.identifier==vocabularyModelItem}">
									<a href="javascript:void(0)">${availableVocabularyItem.displayLabel}</a>
								</c:if>
							</c:forEach>
						</label><br/>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
		<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="${vocabularyIdentifier}" />
		<div class="col-sm-9 col-sm-offset-3">
			<div class="editor-hint">
				<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
				<s:message code="${vocabularyHintCode}" />
			</div>
		</div>
	</div>
</s:bind>