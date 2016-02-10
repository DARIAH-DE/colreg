<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:importAttribute name="fluidLayout" />

<s:url value="${collection.entityId}" var="actionPath" />

<ul class="breadcrumb">
	<li><a href='<s:url value="/" />' target="_self"><s:message code="~eu.dariah.de.colreg.titles.collection_registry" /></a></li>
	<li><a href='<s:url value="/collections/" />' target="_self"><s:message code="~eu.dariah.de.colreg.titles.collections" /></a></li>
	<li class="active">
		<c:choose>
			<c:when test="${collection.id=='new'}"><s:message code="~eu.dariah.de.colreg.titles.new_collection" /></c:when>
			<c:otherwise><s:message code="~eu.dariah.de.colreg.titles.collection" />: ${collection.entityId}</c:otherwise>
		</c:choose>
	</li>
</ul>
<div id="main-content">
	<h1><s:message code="~eu.dariah.de.colreg.titles.collection_editor" /></h1>
	<input type="hidden" id="js-form-action" value="${actionPath}" />
	<sf:form method="POST" action="javascript:void(0);" modelAttribute="collection" class="form-horizontal" autocomplete="off">
		<span id="entityId" style="display: none;">${collection.entityId}</span>
		<div class="form-group">
			<div class="col-sm-12">
				<c:choose>
					<c:when test="${isDeleted}">
						<div class="alert alert-danger" role="alert">
							<s:url value="/collections/${collection.entityId}" var="latest_link" />  
							<s:message code="~eu.dariah.de.colreg.view.collection.notification.collection_deleted" arguments="${latest_link}" />
						</div>
					</c:when>
					<c:when test="${!isDraft}">
						<div class="alert alert-info" role="alert">
							<s:message code="~eu.dariah.de.colreg.view.collection.notification.public" />
						</div>
					</c:when>
					<c:otherwise>
						<div class="alert alert-info" role="alert">
							<s:message code="~eu.dariah.de.colreg.view.collection.notification.draft" />
						</div>
					</c:otherwise>
				</c:choose>
				
				
				
				<c:if test="${collection.succeedingVersionId!=null}">
					<div class="alert alert-warning" role="alert">
						<s:url value="/collections/${collection.entityId}" var="latest_link" />
						<s:message code="~eu.dariah.de.colreg.view.collection.notification.outdated_version" arguments="${latest_link}" />
					</div>
				</c:if>
				
				<div id="entity-notifications-area">
					<s:bind path="*">
					<c:if test="${status.error}">
						<div class="alert alert-danger">
							<s:message code="~eu.dariah.de.colreg.view.collection.notification.validation_errors" />
						</div>
					</c:if>
					</s:bind>
				</div>
			</div>
		</div>

		<div class="form-group editor-buttonbar">
			<div class="col-sm-12">
				<div class="pull-right">
					<a href='<s:url value="/collections/" />' class="btn btn-default cancel form-btn-cancel"><s:message code="~eu.dariah.de.colreg.common.actions.cancel" /></a>
					<c:if test="${!isDeleted && collection.succeedingVersionId==null}">
						<button class="btn btn-primary start form-btn-submit"><s:message code="~eu.dariah.de.colreg.common.actions.save" /></button>
					</c:if>
				</div>
			</div>
		</div>
				
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4><s:message code="~eu.dariah.de.colreg.model.collection.groups.mandatory_description" /></h4>
			</div>
			
			<!-- Localized collection descriptions -->
			<s:bind path="localizedDescriptions*">
				<div class="form-group" >
					<label for="tbl-collection-description-sets" class="col-sm-3 control-label${status.error ? ' container-error' : ' '}"><s:message code="~eu.dariah.de.colreg.model.collection.description_sets" /></label>
					<div class="col-sm-9">
						<table id="tbl-collection-description-sets" class="collection-editor-table">
							<thead>
								<tr>
									<th class="explode"><s:message code="~eu.dariah.de.colreg.model.localized_description.title" /></th>
									<th class="nowrap"><s:message code="~eu.dariah.de.colreg.model.localized_description.acronym" /></th>
									<th class="nowrap"><s:message code="~eu.dariah.de.colreg.model.localized_description.language" /></th>
									<th class="nowrap"></th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${fn:length(collection.localizedDescriptions)>0}">
									<c:forEach items="${collection.localizedDescriptions}" var="desc" varStatus="status" >
										<c:set var="currDesc" value="${desc}" scope="request" />
										<c:set var="currIndex" value="${status.index}" scope="request" />
										<jsp:include page="incl/edit_description.jsp" />
									</c:forEach>
									<c:remove var="currDesc" />	
								</c:if>
								<tr class="collection-editor-table-buttons">
									<td colspan="4" style="text-align: right;">
										<button class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.collection.actions.add_description_set" /></button>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="localizedDescriptions" />
				</div>
			</s:bind>
			
			<!-- Collection Type -->
			<s:bind path="collectionType">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="collectionType" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.collection_type" /></label>
					<div class="col-sm-9">
						<sf:input path="collectionType" class="form-control" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="collectionType" />
				</div>
			</s:bind>
			
			<!-- Rights of collection description -->
			<s:bind path="collectionDescriptionRights">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="collectionDescriptionRights" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.description_rights" /></label>
					<div class="col-sm-9">
						<sf:input path="collectionDescriptionRights" class="form-control" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="collectionDescriptionRights" />
				</div>
			</s:bind>
			
			<!-- Access rights -->
			<s:bind path="accessRights">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="accessRights" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.access_rights" /></label>
					<div class="col-sm-9">
						<sf:input path="accessRights" class="form-control" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="accessRights" />
				</div>
			</s:bind>
			
		</div>
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4><s:message code="~eu.dariah.de.colreg.model.collection.groups.contact_and_agents" /></h4>
			</div>
			
			<!-- Web page -->
			<s:bind path="webPage">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="webPage" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.webpage" /></label>
					<div class="col-sm-9">
						<sf:input path="webPage" class="form-control" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="webPage" />
				</div>
			</s:bind>
			
			<!-- e-Mail -->
			<s:bind path="eMail">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="eMail" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.email" /></label>
					<div class="col-sm-9">
						<sf:input path="eMail" class="form-control" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="eMail" />
				</div>
			</s:bind>
			
			<!-- Agents -->
			<s:bind path="agentRelations*">
				<div class="form-group">
					<label for="tbl-collection-agents" class="col-sm-3 control-label${status.error ? ' container-error' : ' '}"><s:message code="~eu.dariah.de.colreg.model.collection.agents" /></label>
					<div class="col-sm-9">
						<table id="tbl-collection-agents" class="collection-editor-table">
							<thead>
								<tr>
									<th class="explode"><s:message code="~eu.dariah.de.colreg.model.agent_relation.agent_name" /></th>
									<th class="explode"><s:message code="~eu.dariah.de.colreg.model.agent_relation.relation" /></th>
									<th class="nowrap"></th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${fn:length(collection.agentRelations)>0}">
									<c:forEach items="${collection.agentRelations}" var="agentRelation" varStatus="status" >
										<c:set var="currAgentRelation" value="${agentRelation}" scope="request" />
										<c:set var="currIndex" value="${status.index}" scope="request" />
										<jsp:include page="incl/edit_agent.jsp" />
									</c:forEach>
									<c:remove var="currAgentRelation" />	
								</c:if>
								<tr class="collection-editor-table-buttons">
									<td colspan="4" style="text-align: right;">
										<button class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.collection.actions.add_agent_relation" /></button>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="agentRelations" />
				</div>
			</s:bind>
		</div>
		
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4><s:message code="~eu.dariah.de.colreg.model.collection.groups.content_related" /></h4>
			</div>
			
			<!-- Item language* -->
			<s:bind path="itemLanguages*">
				<div class="form-group${status.error ? ' container-error' : ' '}">
					<label for="lst-collection-item-languages" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.item_languages" /></label>
					<div class="col-sm-9">
						<ul id="lst-collection-item-languages" class="collection-editor-list">
							<c:if test="${fn:length(collection.itemLanguages)>0}">
								<c:forEach items="${collection.itemLanguages}" var="lang" varStatus="status" >
									<c:set var="currLang" value="${lang}" scope="request" />
									<c:set var="currIndex" value="${status.index}" scope="request" />
									<jsp:include page="incl/edit_itemlanguage.jsp" />
								</c:forEach>
								<c:remove var="currDesc" />	
							</c:if>
							<li class="collection-editor-list-buttons">
								<div class="col-sm-12">
									<button onclick="editor.lists['itemLanguageList'].triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.collection.actions.add_item_language" /></button>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</s:bind>
			
			<!-- Subject* -->
			<s:bind path="subjects*">
				<div class="form-group${status.error ? ' container-error' : ' '}">
					<label for="lst-collection-subjects" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.subjects" /></label>
					<div class="col-sm-9">
						<ul id="lst-collection-subjects" class="collection-editor-list">
							<c:if test="${fn:length(collection.subjects)>0}">
								<c:forEach items="${collection.subjects}" var="subj" varStatus="status" >
									<c:set var="currSubj" value="${subj}" scope="request" />
									<c:set var="currIndex" value="${status.index}" scope="request" />
									<jsp:include page="incl/edit_subject.jsp" />
								</c:forEach>
								<c:remove var="currSubj" />	
							</c:if>
							<li class="collection-editor-list-buttons">
								<div class="col-sm-12">
									<button onclick="editor.lists['subjects'].triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.collection.actions.add_subject" /></button>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</s:bind>
			
			<!-- Temporal* -->
			<s:bind path="temporals*">
				<div class="form-group${status.error ? ' container-error' : ' '}">
					<label for="lst-collection-temporals" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.temporals" /></label>
					<div class="col-sm-9">
						<ul id="lst-collection-temporals" class="collection-editor-list">
							<c:if test="${fn:length(collection.temporals)>0}">
								<c:forEach items="${collection.temporals}" var="temp" varStatus="status" >
									<c:set var="currTemp" value="${temp}" scope="request" />
									<c:set var="currIndex" value="${status.index}" scope="request" />
									<jsp:include page="incl/edit_temporal.jsp" />
								</c:forEach>
								<c:remove var="currTemp" />	
							</c:if>
							<li class="collection-editor-list-buttons">
								<div class="col-sm-12">
									<button onclick="editor.lists['temporals'].triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.collection.actions.add_temporal" /></button>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</s:bind>
			
			<!-- Spatial* -->
			<s:bind path="spatials*">
				<div class="form-group${status.error ? ' container-error' : ' '}">
					<label for="lst-collection-spatials" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.spatials" /></label>
					<div class="col-sm-9">
						<ul id="lst-collection-spatials" class="collection-editor-list">
							<c:if test="${fn:length(collection.spatials)>0}">
								<c:forEach items="${collection.spatials}" var="spat" varStatus="status" >
									<c:set var="currSpat" value="${spat}" scope="request" />
									<c:set var="currIndex" value="${status.index}" scope="request" />
									<jsp:include page="incl/edit_spatial.jsp" />
								</c:forEach>
								<c:remove var="currSpat" />	
							</c:if>
							<li class="collection-editor-list-buttons">
								<div class="col-sm-12">
									<button onclick="editor.lists['spatials'].triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.collection.actions.add_spacial" /></button>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</s:bind>

			<!-- Collection Created* -->
			<s:bind path="collectionCreated">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="collectionCreated" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.collection_created" /></label>
					<div class="col-sm-9">
						<sf:input path="collectionCreated" class="form-control" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="collectionCreated" />
				</div>
			</s:bind>
			
			<!-- Items Created* -->
			<s:bind path="itemsCreated">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="itemsCreated" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.items_created" /></label>
					<div class="col-sm-9">
						<sf:input path="itemsCreated" class="form-control" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="itemsCreated" />
				</div>
			</s:bind>
			
			<!-- Item type* -->
			<s:bind path="itemTypeIds">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="itemTypeIds" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.item_types" /></label>
					<div class="col-sm-5">
						<select class="form-control" id="itemTypeIds" name="itemTypeIds" size="12" multiple="multiple" autocomplete="off">
						<c:forEach items="${itemTypes}" var="type">
							<c:set var="contains" value="false" />
							<c:forEach items="${collection.itemTypeIds}" var="typeId">
								<c:if test="${typeId==type.id}">
									<c:set var="contains" value="true" />
								</c:if>
							</c:forEach>
							<option <c:if test="${contains}">selected="selected"</c:if> value="${type.id}">${type.label}</option>
						</c:forEach>
					</select>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="itemTypeIds" />
				</div>
			</s:bind>
			
			<!-- Size -->
			<s:bind path="size">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="size" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.size" /></label>
					<div class="col-sm-3">
						<input type="number" id="size" name="size" class="form-control" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="size" />
				</div>
			</s:bind>
		</div>
			
		
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4><s:message code="~eu.dariah.de.colreg.model.collection.groups.contextual" /></h4>
			</div>
			
			<!-- Is part of -->
			<s:bind path="parentCollectionId">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="parentCollectionIdSelector" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.parent_collection" /></label>
					<div class="col-sm-5">
						<input type="text" id="parentCollectionIdSelector" class="form-control" placeholder="<s:message code="~eu.dariah.de.colreg.view.collection.labels.search_by_id_name" />" />
						<sf:hidden path="parentCollectionId" />
					</div>
					<div class="col-sm-9 col-sm-offset-3">
						<div id="parentCollection-display" class="alert alert-default <c:if test="${parentCollection==null}">hide</c:if>">
							<button type="button" class="btn btn-xs btn-link pull-right collection-reset"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
								<p>
							<c:if test="${parentCollection!=null}">
								<a href="<s:url value="${parentCollection.entityId}" />"><button type="button" class="btn btn-xs btn-link pull-right"><span class="glyphicon glyphicon-link" aria-hidden="true"></span></button><strong>${parentCollection.localizedDescriptions[0].title}</strong><br />
								<small><em>ID: ${parentCollection.entityId}</em></small></a>
							</c:if>	
							</p>
						</div>
						<div id="parentCollection-display-null" class="<c:if test="${parentCollection!=null}">hide</c:if>">
							<label class="control-label"><em><s:message code="~eu.dariah.de.colreg.view.collection.labels.no_parent_collection_set" /></em></label>
						</div>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="parentCollectionId" />
				</div>
			</s:bind>
			
			<!-- Has part of -->
			<div class="form-group">
				<label class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.child_collections" /></label>
				<div id="lst-child-collections" class="col-sm-9">
					<c:choose>
						<c:when test="${childCollections!=null && fn:length(childCollections)>0}">
							<c:forEach items="${childCollections}" var="child" varStatus="status" >
								<div class="alert alert-default">
									<p>
										<a href="<s:url value="${child.entityId}" />"><button type="button" class="btn btn-xs btn-link pull-right"><span class="glyphicon glyphicon-link" aria-hidden="true"></span></button><strong>${child.localizedDescriptions[0].title}</strong><br />
										<small><em>ID: ${child.entityId}</em></small></a>	
									</p>
								</div>
							</c:forEach>		
						</c:when>
						<c:otherwise>
							<label class="control-label"><em><s:message code="~eu.dariah.de.colreg.view.collection.labels.no_children_assigned" /></em></label>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			
			<!-- Provided identifiers -->
			<s:bind path="providedIdentifier*">
				<div class="form-group">
					<label for="lst-collection-provided-identifiers" class="col-sm-3 control-label${status.error ? ' container-error' : ' '}"><s:message code="~eu.dariah.de.colreg.model.collection.provided_identifiers" /></label>
					<div class="col-sm-9">
						<ul id="lst-collection-provided-identifiers" class="collection-editor-list">
							<c:if test="${fn:length(collection.providedIdentifier)>0}">
								<c:forEach items="${collection.providedIdentifier}" var="identifier" varStatus="status" >
									<c:set var="currIdentifier" value="${identifier}" scope="request" />
									<c:set var="currIndex" value="${status.index}" scope="request" />
									<jsp:include page="incl/edit_identifier.jsp" />
								</c:forEach>
								<c:remove var="currIdentifier" />	
							</c:if>
							<li class="collection-editor-list-buttons">
								<div class="col-sm-12">
									<button onclick="editor.lists['identifierList'].triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.collection.actions.add_identifier" /></button>
								</div>
							</li>
						</ul>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="providedIdentifier" />
				</div>
			</s:bind>
			
			<!-- Location* -->
			<s:bind path="locations*">
				<div class="form-group" >
					<label for="tbl-collection-locations" class="col-sm-3 control-label${status.error ? ' container-error' : ' '}"><s:message code="~eu.dariah.de.colreg.model.collection.locations" /></label>
					<div class="col-sm-9">
						<table id="tbl-collection-locations" class="collection-editor-table">
							<thead>
								<tr>
									<th class="explode"><s:message code="~eu.dariah.de.colreg.model.address.place" /></th>
									<th class="nowrap"><s:message code="~eu.dariah.de.colreg.model.address.country" /></th>
									<th class="nowrap"></th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${fn:length(collection.locations)>0}">
									<c:forEach items="${collection.locations}" var="addr" varStatus="status" >
										<c:set var="currAddr" value="${addr}" scope="request" />
										<c:set var="currIndex" value="${status.index}" scope="request" />
										<jsp:include page="incl/edit_location.jsp" />
									</c:forEach>
									<c:remove var="currAddr" />	
								</c:if>
								<tr class="collection-editor-table-buttons">
									<td colspan="4" style="text-align: right;">
										<button class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.collection.actions.add_location" /></button>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="locations" />
				</div>
			</s:bind>
			
			<!-- Audience* -->
			<s:bind path="audiences*">
				<div class="form-group${status.error ? ' container-error' : ' '}">
					<label for="lst-collection-audiences" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.audiences" /></label>
					<div class="col-sm-9">
						<ul id="lst-collection-audiences" class="collection-editor-list">
							<c:if test="${fn:length(collection.audiences)>0}">
								<c:forEach items="${collection.audiences}" var="audi" varStatus="status" >
									<c:set var="currAudi" value="${audi}" scope="request" />
									<c:set var="currIndex" value="${status.index}" scope="request" />
									<jsp:include page="incl/edit_audience.jsp" />
								</c:forEach>
								<c:remove var="currAudi" />	
							</c:if>
							<li class="collection-editor-list-buttons">
								<div class="col-sm-12">
									<button onclick="editor.lists['audiences'].triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.collection.actions.add_audience" /></button>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</s:bind>
			
			<!-- Provenance* -->
			<s:bind path="provenanceInfo">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for=provenanceInfo class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.provenance" /></label>
					<div class="col-sm-9">
						<sf:input path="provenanceInfo" class="form-control" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="provenanceInfo" />
				</div>
			</s:bind>
			
			
			
			<!-- Associated project -->
			<s:bind path="associatedProject">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="associatedProject" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.associated_project" /></label>
					<div class="col-sm-9">
						<sf:input path="associatedProject" class="form-control" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="associatedProject" />
				</div>
			</s:bind>
			
			
		</div>
		
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4><s:message code="~eu.dariah.de.colreg.model.collection.groups.policy" /></h4>
			</div>
			
			<!-- Accrual -->
			<s:bind path="accrualMethods*">
				<div class="form-group">
					<label for="tbl-collection-accrual" class="col-sm-3 control-label${status.error ? ' container-error' : ' '}"><s:message code="~eu.dariah.de.colreg.model.collection.accrual" /></label>
					<div class="col-sm-9">
						<table id="tbl-collection-accrual" class="collection-editor-table">
							<thead>
								<tr>
									<th class="explode"><s:message code="~eu.dariah.de.colreg.model.accrual.method" /></th>
									<th class="nowrap"><s:message code="~eu.dariah.de.colreg.model.accrual.policy" /></th>
									<th class="nowrap"></th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${fn:length(collection.accrualMethods)>0}">
									<c:forEach items="${collection.accrualMethods}" var="method" varStatus="status" >
										<c:set var="currMethod" value="${method}" scope="request" />
										<c:set var="currIndex" value="${status.index}" scope="request" />
										<jsp:include page="incl/edit_accrual.jsp" />
									</c:forEach>
									<c:remove var="currMethod" />	
								</c:if>
								<tr class="collection-editor-table-buttons">
									<td colspan="3" style="text-align: right;">
										<button class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.collection.actions.add_accrual" /></button>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="accrualMethods" />
				</div>
			</s:bind>
			
			<!-- Item rights -->
			<s:bind path="itemRights">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="itemRights" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.item_rights" /></label>
					<div class="col-sm-9">
						<sf:input path="itemRights" class="form-control" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="itemRights" />
				</div>
			</s:bind>
		</div>
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4><s:message code="~eu.dariah.de.colreg.model.collection.groups.access" /></h4>
			</div>
			
			<!-- Access method -->
			<s:bind path="accessMethods*">
				<div class="form-group">
					<label for="tbl-collection-access" class="col-sm-3 control-label${status.error ? ' container-error' : ' '}"><s:message code="~eu.dariah.de.colreg.model.collection.access" /></label>
					<div class="col-sm-9">
						<a class="form-anchor" id="tbl-collection-access-anchor"></a>
						<table id="tbl-collection-access" class="collection-editor-table">
							<thead>
								<tr>
									<th class="explode"><s:message code="~eu.dariah.de.colreg.model.access.uri" /></th>
									<th class="nowrap"><s:message code="~eu.dariah.de.colreg.model.access.type" /></th>
									<th class="nowrap"></th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${fn:length(collection.accessMethods)>0}">
									<c:forEach items="${collection.accessMethods}" var="method" varStatus="status" >
										<c:set var="currMethod" value="${method}" scope="request" />
										<c:set var="currIndex" value="${status.index}" scope="request" />
										<jsp:include page="incl/edit_access.jsp" />
									</c:forEach>
									<c:remove var="currMethod" />	
								</c:if>
								<tr class="collection-editor-table-buttons">
									<td colspan="3" style="text-align: right;">
										<button class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.collection.actions.add_access" /></button>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="accessMethods" />
				</div>
			</s:bind>
			
		</div>
		

		<c:if test="${!isNew}">
			<div class="editor-section">
				<div class="editor-section-heading">
					<h4><s:message code="~eu.dariah.de.colreg.model.collection.groups.identification_and_administration" /></h4>
				</div>
				
				<!-- Entity id -->
				<div class="form-group">
					<label class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.collection_identifier" /></label>
					<div id="collection-identifier" class="col-sm-9">
						<label class="control-label">
							<a href="<s:url value="/collections/${collection.entityId}" />">${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}<s:url value="/collections/${collection.entityId}" /></a>
						</label>
					</div>
				</div>
				
				<!-- Version id -->
				<div class="form-group">
					<label class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.version_identifier" /></label>
					<div id="version-identifier" class="col-sm-9">
						<label class="control-label">
							<a href="<s:url value="/collections/${collection.id}" />">${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}<s:url value="/collections/${collection.id}" /></a>
						</label>
					</div>
				</div>
	
				<!-- Version created -->
				<div class="form-group">
					<label class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.current_version" /></label>
					<div id="current-version" class="col-sm-9">
						<label class="control-label"><joda:format value="${collection.versionTimestamp}" style="LM" /></label><br />
						<label class="control-label">${collection.versionCreator}</label>
					</div>
				</div>
	
				<!-- Entity timestamp -->
				<div class="form-group">
					<label class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.created" /></label>
					<div id="initially-created" class="col-sm-9">
						<label class="control-label"><joda:format value="${collection.entityTimestamp}" style="LM" /></label><br />
						<label class="control-label">${collection.entityCreator}</label>
					</div>
				</div>
				
				<c:if test="${!isDeleted}">
					<div class="form-group">
						<label class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.groups.administration" /></label>
						<div id="collection-administration" class="col-sm-9">
							<c:if test="${collection.draftUserId!=null}">
								<button id="btn-publish-collection" class="btn btn-primary cancel"><span class="glyphicon glyphicon-globe" aria-hidden="true"></span> <s:message code="~eu.dariah.de.colreg.common.actions.save_and_publish" /></button>
							</c:if>
							<button id="btn-delete-collection" class="btn btn-danger cancel"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span> <s:message code="~eu.dariah.de.colreg.common.actions.delete" /></button>
						</div>
					</div>
				</c:if>
			</div>		
		</c:if>
		<div class="form-group editor-buttonbar">
			<div class="col-sm-12">
				<div class="pull-right">
					<a href='<s:url value="/collections/" />' class="btn btn-default cancel form-btn-cancel"><s:message code="~eu.dariah.de.colreg.common.actions.cancel" /></a>
					<c:if test="${!isDeleted && collection.succeedingVersionId==null}">
						<button class="btn btn-primary start form-btn-submit"><s:message code="~eu.dariah.de.colreg.common.actions.save" /></button>
					</c:if>
				</div>
			</div>
		</div>
				
	</sf:form>
</div>
