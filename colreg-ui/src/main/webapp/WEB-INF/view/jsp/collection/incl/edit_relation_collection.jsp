<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<s:bind path="relations[${currIndex}].${displayCollectionFieldname}">
	<div class="form-group relation-collection-container${status.error ? ' has-error' : ''}">
		<label class="col-sm-3 control-label mandatory"><s:message code="~eu.dariah.de.colreg.model.collection_relation.related_collection" /></label>
		<div class="col-sm-9">
			<c:if test="${editMode}">
				<div class="row">
					<div class="col-sm-7">
						<span class="attribute-name-helper">relations{}.${displayCollectionFieldname}</span>
						<sf:hidden cssClass="relation-collection-entityId" path="relations[${currIndex}].${displayCollectionFieldname}" />
						<input type="hidden" class="relation-collection-displayTitle" onchange="editor.tables['relationTable'].handleInputChange(this, 'relationTable_collection');"  />
						<input type="text" class="form-control relationCollectionEntityIdSelector" placeholder="<s:message code="~eu.dariah.de.colreg.view.collection.labels.search_by_id_name" />" />
					</div>
				</div>
			</c:if>
			<div class="row">
				<div class="col-sm-12">
					<div class="relatedCollection-display alert alert-default <c:if test="${relatedCollectionPojo==null}">hide</c:if>">
						<c:if test="${editMode}">
							<button type="button" class="btn btn-xs btn-link pull-right collection-reset"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
						</c:if>
						<p>
							<c:if test="${relatedCollectionPojo!=null}">
								<a href="<s:url value="${relatedCollectionPojo.id}" />" target="_blank"><button type="button" class="btn btn-xs btn-link pull-right"><span class="glyphicon glyphicon-link" aria-hidden="true"></span></button><strong>${relatedCollectionPojo.displayTitle}</strong><br />
								<small><em>ID: ${relatedCollectionPojo.id}</em></small></a><br />
								<c:if test="${relatedCollectionPojo.draft}">
									<span class="label label-warning"><s:message code="~eu.dariah.de.colreg.common.labels.draft" /></span>
								</c:if>
							</c:if>	
						</p>
					</div>
					<div class="alert alert-sm alert-warning relatedCollection-draft-hint ${relatedCollectionPojo.draft ? '' : 'hide'}">
						<s:message code="~eu.dariah.de.colreg.editorhint.collection_relation.draft_relation" />
					</div>
					
					<div class="relatedCollection-display-null <c:if test="${relatedCollectionPojo!=null}">hide</c:if>">
						<label class="content-label" style="text-align: left;">
							<a href="javascript:void(0)"><em><s:message code="~eu.dariah.de.colreg.view.collection.labels.no_related_collection_set" /></em></a>
						</label>
					</div>
				
					<sf:errors element="div" cssClass="validation-error" path="relations[${currIndex}]" />
				
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.collection_relation.related_collection" />
					</div>
				</div>
			</div>			
		</div>
		<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="relations[${currIndex}].${displayCollectionFieldname}" />
		<div class="col-sm-9 col-sm-offset-3">
			<div class="editor-hint">
				<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
				<s:message code="~eu.dariah.de.colreg.editorhint.collection.related_collections" />
			</div>
		</div>
	</div>
</s:bind>