<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<s:bind path="relations[${currIndex}].*">
	<tr class="list${status.error ? ' has-error' : ' '}">
		<td onclick="editor.tables['relationTable'].editEntry(this); return false;">
			<i class="fa fa-home" aria-hidden="true"></i>
		</td>
		
		<td class="td-no-wrap" onclick="editor.tables['relationTable'].editEntry(this); return false;">
			<span class="relationTable_direction">
				<c:choose>
					<c:when test="${currRelation.bidirectional}">
						<i class="fa fa-arrows-h" aria-hidden="true"></i>
						<c:set var="currDirection" value="bidirectional" />
					</c:when>
					<c:when test="${currRelation.source.id==collection.entityId}">
						<i class="fa fa-long-arrow-right" aria-hidden="true"></i>
						<c:set var="currDirection" value="right" />
					</c:when>
					<c:otherwise>
						<i class="fa fa-long-arrow-left" aria-hidden="true"></i>
						<c:set var="currDirection" value="left" />
					</c:otherwise>
				</c:choose>
			</span>
			<span class="relationTable_relationType">
				<c:if test="${currRelation!=null}">
					<c:forEach items="${availableCollectionRelationTypes}" var="type">
						<c:if test="${currRelation.relationTypeId==type.identifier}">
							${type.displayLabel}
						</c:if>
					</c:forEach>
				</c:if>
			</span>
			
		
		</td>
		
		<td onclick="editor.tables['relationTable'].editEntry(this); return false;">
			<i class="fa fa-dot-circle-o" aria-hidden="true"></i>
		</td>
			
		<td class="relationTable_collection explode" onclick="editor.tables['relationTable'].editEntry(this); return false;">
			<c:choose>
				<c:when test="${currRelation.source.id==collection.entityId}">
					<a class="control-link" href="javascript:void(0);">${currRelation.target.displayTitle}</a>
				</c:when>
				<c:otherwise>
					<a class="control-link" href="javascript:void(0);">${currRelation.source.displayTitle}</a>
				</c:otherwise>
			</c:choose>
		</td>
		<c:if test="${editMode}">
			<td class="nowrap">
				<button onclick="editor.tables['relationTable'].pushEntryUp(this); return false;" class="btn btn-xs btn-link btn-push-up"><span class="glyphicon glyphicon glyphicon-arrow-up" aria-hidden="true"></span></button>
				<button onclick="editor.tables['relationTable'].pushEntryDown(this); return false;" class="btn btn-xs btn-link btn-push-down"><span class="glyphicon glyphicon glyphicon-arrow-down" aria-hidden="true"></span></button>
				<button onclick="editor.tables['relationTable'].removeEntry(this); return false;" class="btn btn-xs btn-link"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
			</td>
		</c:if>
	</tr>
</s:bind>
<tr class="edit" style="display: none;">
	<td colspan="${editMode ? 5 : 4}">
	
		<s:bind path="relations[${currIndex}].relationTypeId">
		
			<span class="attribute-name-helper">relations{}.id</span>
			<sf:hidden cssClass="relation-direction-bidirectional" path="relations[${currIndex}].id"/>
		
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="relationTypeId" class="col-sm-3 control-label mandatory"><s:message code="~eu.dariah.de.colreg.model.collection_relation.relation_type" /></label>
				<c:choose>
					<c:when test="${editMode}">
						<div class="col-sm-7">
							<span class="attribute-name-helper">relations{}.relationTypeId</span>
							<sf:select cssClass="form-control" path="relations[${currIndex}].relationTypeId" items="${availableCollectionRelationTypes}" itemLabel="displayLabel" itemValue="identifier"
								id="relations${currIndex}.relationTypeId" name="relations[${currIndex}].relationTypeId" 
								onchange="editor.tables['relationTable'].handleInputChange(this, 'relationTable_relationType', $(this).find('option:selected').text());" 
								onkeyup="editor.tables['relationTable'].handleInputChange(this, 'relationTable_relationType', $(this).find('option:selected').text());" />
						</div>
					</c:when>
					<c:otherwise>
						<div class="col-sm-9">
							<label class="content-label">
								<c:forEach items="${availableCollectionRelationTypes}" var="type">
									<c:if test="${currRelation.relationTypeId==type.identifier}">
										${type.displayLabel}
									</c:if>
								</c:forEach>
							</label>
						</div>
					</c:otherwise>
				</c:choose>
				
				<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="relations[${currIndex}].relationTypeId" />
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.collection_relation.relation_type" />
					</div>
				</div>
			</div>
		</s:bind>
	
		<s:bind path="relations[${currIndex}].bidirectional">
			<div class="form-group${status.error ? ' has-error' : ' '}">
				<label for="relationTypeId" class="col-sm-3 control-label mandatory"><s:message code="~eu.dariah.de.colreg.model.collection_relation.direction" /></label>
				
				<span class="attribute-name-helper">relations{}.bidirectional</span>
				<sf:hidden cssClass="relation-direction-bidirectional" path="relations[${currIndex}].bidirectional"/>
				
				<c:choose>
					<c:when test="${editMode}">
						<div class="col-sm-5">
							<div class="radio">
							  <label>
							  	<span class="attribute-name-helper">relationsHelper{}.direction</span>
							    <input type="radio"
							    	onchange="editor.handleRelationDirectionRadioChange(this);" 
							    	name="relationsHelper[${currIndex}].direction" id="relationsHelper${currIndex}.direction" 
							    	value="right" ${currDirection=="right" ? " checked" : ""}>
							    <i class="fa fa-home" aria-hidden="true"></i> 
							    <i class="fa fa-long-arrow-right" aria-hidden="true"></i> 
							    <i class="fa fa-dot-circle-o" aria-hidden="true"></i>
							  </label>
							</div>
							<div class="radio">
							  <label>
							    <span class="attribute-name-helper">relationsHelper{}.direction</span>
							    <input type="radio" 
							    	onchange="editor.handleRelationDirectionRadioChange(this);" 
							    	name="relationsHelper[${currIndex}].direction" id="relationsHelper${currIndex}.direction" 
							    	value="left"${currDirection=="left" ? " checked" : ""}>
							    <i class="fa fa-home" aria-hidden="true"></i> 
							    <i class="fa fa-long-arrow-left" aria-hidden="true"></i> 
							    <i class="fa fa-dot-circle-o" aria-hidden="true"></i>
							  </label>
							</div>
							<div class="radio">
							  <label>
							  	<span class="attribute-name-helper">relationsHelper{}.direction</span>
							    <input type="radio" 
							   		onchange="editor.handleRelationDirectionRadioChange(this);" 
							    	name="relationsHelper[${currIndex}].direction" id="relationsHelper${currIndex}.direction"  
							    	value="bidirectional"${currDirection=="bidirectional" ? " checked" : ""}>
							    <i class="fa fa-home" aria-hidden="true"></i> 
							    <i class="fa fa-arrows-h" aria-hidden="true"></i> 
							    <i class="fa fa-dot-circle-o" aria-hidden="true"></i>
							  </label>
							</div>
						
							<span class="attribute-name-helper">relations{}.bidirectional</span>
							<input type="hidden" class="agent-type-display-helper" onchange="editor.tables['relationTable'].handleInputChange(this, 'relationTable_bidirectional');" />
						</div>
					</c:when>
					<c:otherwise>
						<div class="col-sm-9">
							<label class="content-label">
							
								<c:choose>
									<c:when test="${currDirection=='right'}">
										<i class="fa fa-home" aria-hidden="true"></i> 
								   	 	<i class="fa fa-long-arrow-right" aria-hidden="true"></i> 
								    	<i class="fa fa-dot-circle-o" aria-hidden="true"></i>
									</c:when>
									<c:when test="${currDirection=='left'}">
										<i class="fa fa-home" aria-hidden="true"></i> 
									    <i class="fa fa-long-arrow-left" aria-hidden="true"></i> 
									    <i class="fa fa-dot-circle-o" aria-hidden="true"></i>
									</c:when>
									<c:otherwise>
										<i class="fa fa-home" aria-hidden="true"></i> 
									    <i class="fa fa-arrows-h" aria-hidden="true"></i> 
									    <i class="fa fa-dot-circle-o" aria-hidden="true"></i>
									</c:otherwise>
								</c:choose>
							</label>
						</div>
					</c:otherwise>
				</c:choose>
				
				<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="relations[${currIndex}].bidirectional" />
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.collection_relation.direction" />
					</div>
				</div>
			</div>
		</s:bind>
	
		<!-- Related collection block -->
		<c:set var="displayCollectionFieldname" value="${currDirection!='left' ? 'targetEntityId' : 'sourceEntityId'}" scope="request" />
		<c:set var="relatedCollectionPojo" value="${currDirection!='left' ? currRelation.target : currRelation.source}" scope="request" />
		<jsp:include page="edit_relation_collection.jsp" />
			
	</td>
</tr>


