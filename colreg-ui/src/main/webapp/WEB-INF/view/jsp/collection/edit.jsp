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
				<c:if test="${lastSavedVersion!=null}">
					<div class="alert alert-success" role="alert">
						<joda:format value="${lastSavedTimestamp}" style="LM" var="saveTimestamp" />
						<s:message code="~eu.dariah.de.colreg.view.collection.notification.collection_saved" arguments="${saveTimestamp}" /><br />
						<a href="#" onclick="editor.appendComment('collections/', '${lastSavedVersion}'); return false;"><s:message code="~eu.dariah.de.colreg.view.collection.labels.comment_revision" /></a>
					</div>
				</c:if>
			
				<c:choose>
					<c:when test="${isDeleted}">
						<div class="alert alert-danger" role="alert">
							<s:url value="/collections/${collection.entityId}" var="latest_link" />  
							<s:message code="~eu.dariah.de.colreg.view.collection.notification.collection_deleted" arguments="${latest_link}" />
						</div>
					</c:when>
					<c:when test="${isDraft}">
						<div class="alert alert-info" role="alert">
							<s:message code="~eu.dariah.de.colreg.view.collection.notification.draft" />
						</div>
					</c:when>
					<c:when test="${editMode}">
						<div class="alert alert-info" role="alert">
							<s:message code="~eu.dariah.de.colreg.view.collection.notification.public" />
						</div>
					</c:when>
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

		<c:if test="${!isDeleted && agent.succeedingVersionId==null && _auth!=null && _auth.auth && editMode}">
			<div class="form-group editor-buttonbar">
				<div class="col-sm-12">
					<div class="pull-right">
						<a href='<s:url value="/collections/" />' class="btn btn-default cancel form-btn-cancel"><s:message code="~eu.dariah.de.colreg.common.actions.cancel" /></a>
						<button class="btn btn-primary start form-btn-submit"><s:message code="~eu.dariah.de.colreg.common.actions.save" /></button>
					</div>
				</div>
			</div>
		</c:if>
				
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4><s:message code="~eu.dariah.de.colreg.model.collection.groups.mandatory_description" /></h4>
				<div class="editor-hint">
					<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
					<s:message code="~eu.dariah.de.colreg.editorhint.collection.mandatory_description" />
				</div>
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
									<c:if test="${editMode}"><th class="nowrap"></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(collection.localizedDescriptions)>0}">
										<c:forEach items="${collection.localizedDescriptions}" var="desc" varStatus="status" >
											<c:set var="currDesc" value="${desc}" scope="request" />
											<c:set var="currIndex" value="${status.index}" scope="request" />
											<jsp:include page="incl/edit_description.jsp" />
										</c:forEach>
										<c:remove var="currDesc" />	
									</c:when>
								</c:choose>
								<c:if test="${editMode}">
									<tr class="collection-editor-table-buttons">
										<td colspan="${editMode ? 4 : 3}">
											<button class="btn btn-xs pull-right btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.collection.actions.add_description_set" /></button>		
										</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="localizedDescriptions" />
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.localized_descriptions" />
						</div>
					</div>
				</div>
			</s:bind>
			
			<!-- Collection Type -->
			<s:bind path="collectionType">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="collectionType" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.collection_type" /></label>
					<div class="col-sm-9">
						<c:choose>
							<c:when test="${editMode}">
								<sf:input path="collectionType" class="form-control" />
							</c:when>
							<c:otherwise>
								<label class="content-label">${collection.collectionType}</label>
							</c:otherwise>
						</c:choose>		
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="collectionType" />
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.collection_type" />
						</div>
					</div>
				</div>
			</s:bind>
			
			<!-- Rights of collection description -->
			<s:bind path="collectionDescriptionRights">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="collectionDescriptionRights" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.description_rights" /></label>
					<c:set var="currRightsLicenseId" value="${collectionDescriptionRightsIsLicenseId}" scope="request" />
					<c:set var="currRightsName" value="collectionDescriptionRights" scope="request" />
					<c:set var="currRightsValue" value="${collection.collectionDescriptionRights}" scope="request" />
					<jsp:include page="incl/edit_rights.jsp" />	
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="collectionDescriptionRights" />
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.description_rights" />
						</div>
					</div>
				</div>
			</s:bind>
			
			<!-- Access rights -->
			<s:bind path="accessRights">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="accessRights" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.access_rights" /></label>
					<c:set var="currRightsLicenseId" value="${accessRightsIsLicenseId}" scope="request" />
					<c:set var="currRightsName" value="accessRights" scope="request" />
					<c:set var="currRightsValue" value="${collection.accessRights}" scope="request" />
					<jsp:include page="incl/edit_rights.jsp" />	
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="accessRights" />
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.access_rights" />
						</div>
					</div>
				</div>
			</s:bind>
			
		</div>
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4><s:message code="~eu.dariah.de.colreg.model.collection.groups.contact_and_agents" /></h4>
				<div class="editor-hint">
					<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
					<s:message code="~eu.dariah.de.colreg.editorhint.collection.has_contact" />
				</div>
			</div>
			
			<!-- Web page -->
			<s:bind path="webPage">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="webPage" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.webpage" /></label>
					<div class="col-sm-9">
						<c:choose>
							<c:when test="${editMode}">
								<sf:input path="webPage" class="form-control" />
							</c:when>
							<c:otherwise>
								<label class="content-label">
									<a href="${collection.webPage}">${collection.webPage}</a>
								</label>
							</c:otherwise>
						</c:choose>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="webPage" />
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.webpage" />
						</div>
					</div>
				</div>
			</s:bind>
			
			<!-- e-Mail -->
			<s:bind path="eMail">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="eMail" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.email" /></label>
					<div class="col-sm-9">
						<c:choose>
							<c:when test="${editMode}">
								<sf:input path="eMail" class="form-control" />
							</c:when>
							<c:otherwise>
								<label class="content-label">
									<a href="mailto:${collection.EMail}">${collection.EMail}</a>
								</label>
							</c:otherwise>
						</c:choose>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="eMail" />
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.email" />
						</div>
					</div>
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
									<c:if test="${editMode}"><th class="nowrap"></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(collection.locations)>0}">
										<c:forEach items="${collection.locations}" var="addr" varStatus="status" >
											<c:set var="currAddr" value="${addr}" scope="request" />
											<c:set var="currIndex" value="${status.index}" scope="request" />
											<jsp:include page="incl/edit_location.jsp" />
										</c:forEach>
										<c:remove var="currAddr" />	
									</c:when>
								
								</c:choose>
								<c:if test="${editMode}">
									<tr class="collection-editor-table-buttons">
										<td colspan="${editMode ? 3 : 2}">
											<button class="btn btn-xs pull-right btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.collection.actions.add_location" /></button>		
										</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="locations" />
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.locations" />
						</div>
					</div>
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
									<c:if test="${editMode}"><th class="nowrap"></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(collection.agentRelations)>0}">
										<c:forEach items="${collection.agentRelations}" var="agentRelation" varStatus="status" >
											<c:set var="currAgentRelation" value="${agentRelation}" scope="request" />
											<c:set var="currIndex" value="${status.index}" scope="request" />
											<jsp:include page="incl/edit_agent.jsp" />
										</c:forEach>
										<c:remove var="currAgentRelation" />	
									</c:when>
									
								</c:choose>
								<c:if test="${editMode}">
									<tr class="collection-editor-table-buttons">
										<td colspan="${editMode ? 3 : 2}">
											<button class="btn btn-xs pull-right btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.collection.actions.add_agent_relation" /></button>		
										</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="agentRelations" />
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.agents" />
						</div>
					</div>
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
						<c:choose>
							<c:when test="${editMode}">
								<ul id="lst-collection-item-languages" class="collection-editor-list">
									<c:if test="${fn:length(collection.itemLanguages)>0}">
										<c:forEach items="${collection.itemLanguages}" var="lang" varStatus="status" >
											<c:set var="currLang" value="${lang}" scope="request" />
											<c:set var="currIndex" value="${status.index}" scope="request" />
											<jsp:include page="incl/edit_itemlanguage.jsp" />
										</c:forEach>
										<c:remove var="currDesc" />	
									</c:if>
									<c:if test="${editMode}">
										<li class="collection-editor-list-buttons">
											<div class="col-sm-12">
												<button onclick="editor.lists['itemLanguageList'].triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.collection.actions.add_item_language" /></button>
											</div>
										</li>
									</c:if>
								</ul>
							</c:when>
							<c:otherwise>
								<label class="content-label">
									<c:forEach items="${collection.itemLanguages}" var="lang" varStatus="status" >${lang}<br/></c:forEach>
								</label>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.item_languages" />
						</div>
					</div>
				</div>
			</s:bind>
			
			<!-- Subject* -->
			<s:bind path="subjects*">
				<div class="form-group${status.error ? ' container-error' : ' '}">
					<label for="lst-collection-subjects" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.subjects" /></label>
					<div class="col-sm-9">
						<c:choose>
							<c:when test="${editMode}">
								<ul id="lst-collection-subjects" class="collection-editor-list">
									<c:if test="${fn:length(collection.subjects)>0}">
										<c:forEach items="${collection.subjects}" var="subj" varStatus="status" >
											<c:set var="currSubj" value="${subj}" scope="request" />
											<c:set var="currIndex" value="${status.index}" scope="request" />
											<jsp:include page="incl/edit_subject.jsp" />
										</c:forEach>
										<c:remove var="currSubj" />	
									</c:if>
									<c:if test="${editMode}">
										<li class="collection-editor-list-buttons">
											<div class="col-sm-12">
												<button onclick="editor.lists['subjects'].triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.collection.actions.add_subject" /></button>
											</div>
										</li>
									</c:if>
								</ul>
							</c:when>
							<c:otherwise>
								<label class="content-label">
									<c:forEach items="${collection.subjects}" var="subj" varStatus="status" >${subj}<br/></c:forEach>
								</label>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.subjects" />
						</div>
					</div>
				</div>
			</s:bind>
			
			<!-- Temporal* -->
			<s:bind path="temporals*">
				<div class="form-group${status.error ? ' container-error' : ' '}">
					<label for="lst-collection-temporals" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.temporals" /></label>
					<div class="col-sm-9">
						<c:choose>
							<c:when test="${editMode}">
								<ul id="lst-collection-temporals" class="collection-editor-list">
									<c:if test="${fn:length(collection.temporals)>0}">
										<c:forEach items="${collection.temporals}" var="temp" varStatus="status" >
											<c:set var="currTemp" value="${temp}" scope="request" />
											<c:set var="currIndex" value="${status.index}" scope="request" />
											<jsp:include page="incl/edit_temporal.jsp" />
										</c:forEach>
										<c:remove var="currTemp" />	
									</c:if>
									<c:if test="${editMode}">
										<li class="collection-editor-list-buttons">
											<div class="col-sm-12">
												<button onclick="editor.lists['temporals'].triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.collection.actions.add_temporal" /></button>
											</div>
										</li>
									</c:if>
								</ul>
							</c:when>
							<c:otherwise>
								<label class="content-label">
									<c:forEach items="${collection.temporals}" var="temp" varStatus="status" >${temp}<br/></c:forEach>
								</label>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.temporals" />
						</div>
					</div>
				</div>
			</s:bind>
			
			<!-- Spatial* -->
			<s:bind path="spatials*">
				<div class="form-group${status.error ? ' container-error' : ' '}">
					<label for="lst-collection-spatials" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.spatials" /></label>
					<div class="col-sm-9">
						<c:choose>
							<c:when test="${editMode}">
								<ul id="lst-collection-spatials" class="collection-editor-list">
									<c:if test="${fn:length(collection.spatials)>0}">
										<c:forEach items="${collection.spatials}" var="spat" varStatus="status" >
											<c:set var="currSpat" value="${spat}" scope="request" />
											<c:set var="currIndex" value="${status.index}" scope="request" />
											<jsp:include page="incl/edit_spatial.jsp" />
										</c:forEach>
										<c:remove var="currSpat" />	
									</c:if>
									<c:if test="${editMode}">
										<li class="collection-editor-list-buttons">
											<div class="col-sm-12">
												<button onclick="editor.lists['spatials'].triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.collection.actions.add_spacial" /></button>
											</div>
										</li>
									</c:if>
								</ul>
							</c:when>
							<c:otherwise>
								<label class="content-label">
									<c:forEach items="${collection.spatials}" var="spat" varStatus="status" >${spat}<br/></c:forEach>
								</label>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.spatials" />
						</div>
					</div>
				</div>
			</s:bind>

			<!-- Collection Created* -->
			<s:bind path="collectionCreated">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="collectionCreated" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.collection_created" /></label>
					<div class="col-sm-9">
						<c:choose>
							<c:when test="${editMode}">
								<sf:input path="collectionCreated" class="form-control" />
							</c:when>
							<c:otherwise>
								<label class="content-label">${collection.collectionCreated}</label>
							</c:otherwise>
						</c:choose>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="collectionCreated" />
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.collection_created" />
						</div>
					</div>
				</div>
			</s:bind>
			
			<!-- Items Created* -->
			<s:bind path="itemsCreated">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="itemsCreated" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.items_created" /></label>
					<div class="col-sm-9">
						<c:choose>
							<c:when test="${editMode}">
								<sf:input path="itemsCreated" class="form-control" />
							</c:when>
							<c:otherwise>
								<label class="content-label">${collection.itemsCreated}</label>
							</c:otherwise>
						</c:choose>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="itemsCreated" />
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.items_created" />
						</div>
					</div>
				</div>
			</s:bind>
			
			<!-- Item type* -->
			<s:bind path="itemTypeIds">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="itemTypeIds" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.item_types" /></label>
					<c:choose>
						<c:when test="${editMode}">
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
						</c:when>
						<c:otherwise>
							<div class="col-sm-9">
								<label class="content-label">
								<c:forEach items="${itemTypes}" var="type">
									<c:forEach items="${collection.itemTypeIds}" var="typeId">
										<c:if test="${typeId==type.id}">${type.label}<br /></c:if>
									</c:forEach>
								</c:forEach>
								</label>
							</div>
						</c:otherwise>
					</c:choose>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="itemTypeIds" />
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.item_types" />
						</div>
					</div>
				</div>
			</s:bind>
			
			<!-- Size -->
			<s:bind path="size">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="size" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.size" /></label>
					<div class="col-sm-3">
						<c:choose>
							<c:when test="${editMode}">
								<input type="number" id="size" name="size" value="${collection.size}" class="form-control" />
							</c:when>
							<c:otherwise>
								<label class="content-label">${collection.size}</label>
							</c:otherwise>
						</c:choose>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="size" />
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.size" />
						</div>
					</div>
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
					<div class="col-sm-9">
						<c:if test="${editMode}">
							<div class="row">
								<div class="col-sm-5">
									<input type="text" id="parentCollectionIdSelector" class="form-control" placeholder="<s:message code="~eu.dariah.de.colreg.view.collection.labels.search_by_id_name" />" />
									<sf:hidden path="parentCollectionId" />
								</div>
							</div>
						</c:if>
						<div class="row">
							<div class="col-sm-12">
								<div id="parentCollection-display" class="alert alert-default <c:if test="${parentCollection==null}">hide</c:if>">
									<c:if test="${editMode}">
										<button type="button" class="btn btn-xs btn-link pull-right collection-reset"><span class="glyphicon glyphicon-trash glyphicon-color-danger" aria-hidden="true"></span></button>
									</c:if>
										<p>
									<c:if test="${parentCollection!=null}">
										<a href="<s:url value="${parentCollection.entityId}" />"><button type="button" class="btn btn-xs btn-link pull-right"><span class="glyphicon glyphicon-link" aria-hidden="true"></span></button><strong>${parentCollection.localizedDescriptions[0].title}</strong><br />
										<small><em>ID: ${parentCollection.entityId}</em></small></a>
									</c:if>	
									</p>
								</div>
								<div id="parentCollection-display-null" class="<c:if test="${parentCollection!=null}">hide</c:if>">
									<label class="content-label"><em><s:message code="~eu.dariah.de.colreg.view.collection.labels.no_parent_collection_set" /></em></label>
								</div>
							
								<sf:errors element="div" cssClass="validation-error" path="parentCollectionId" />
							
								<div class="editor-hint">
									<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
									<s:message code="~eu.dariah.de.colreg.editorhint.collection.parent_collection" />
								</div>
							</div>
						</div>
					</div>
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
							<label class="control-label">
								<a href="javascript:void(0)"><em><s:message code="~eu.dariah.de.colreg.view.collection.labels.no_children_assigned" /></em></a> 
							</label>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="col-sm-9 col-sm-offset-3">
					<div class="editor-hint">
						<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
						<s:message code="~eu.dariah.de.colreg.editorhint.collection.child_collections" />
					</div>
				</div>
			</div>
			
			<!-- Provided identifiers -->
			<s:bind path="providedIdentifier*">
				<div class="form-group">
					<label for="lst-collection-provided-identifiers" class="col-sm-3 control-label${status.error ? ' container-error' : ' '}"><s:message code="~eu.dariah.de.colreg.model.collection.provided_identifiers" /></label>
					<div class="col-sm-9">
						<c:choose>
							<c:when test="${editMode}">
								<ul id="lst-collection-provided-identifiers" class="collection-editor-list">
									<c:if test="${fn:length(collection.providedIdentifier)>0}">
										<c:forEach items="${collection.providedIdentifier}" var="identifier" varStatus="status" >
											<c:set var="currIdentifier" value="${identifier}" scope="request" />
											<c:set var="currIndex" value="${status.index}" scope="request" />
											<jsp:include page="incl/edit_identifier.jsp" />
										</c:forEach>
										<c:remove var="currIdentifier" />	
									</c:if>
									<c:if test="${editMode}">
										<li class="collection-editor-list-buttons">
											<div class="col-sm-12">
												<button onclick="editor.lists['identifierList'].triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.collection.actions.add_identifier" /></button>
											</div>
										</li>
									</c:if>
								</ul>
							</c:when>
							<c:otherwise>
								<label class="content-label">
									<c:forEach items="${collection.providedIdentifier}" var="ident" varStatus="status" >${ident}<br/></c:forEach>
								</label>
							</c:otherwise>
						</c:choose>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="providedIdentifier" />
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.provided_identifiers" />
						</div>
					</div>
				</div>
			</s:bind>
						
			<!-- Audience* -->
			<s:bind path="audiences*">
				<div class="form-group${status.error ? ' container-error' : ' '}">
					<label for="lst-collection-audiences" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.audiences" /></label>
					<div class="col-sm-9">
						<c:choose>
							<c:when test="${editMode}">
								<ul id="lst-collection-audiences" class="collection-editor-list">
									<c:if test="${fn:length(collection.audiences)>0}">
										<c:forEach items="${collection.audiences}" var="audi" varStatus="status" >
											<c:set var="currAudi" value="${audi}" scope="request" />
											<c:set var="currIndex" value="${status.index}" scope="request" />
											<jsp:include page="incl/edit_audience.jsp" />
										</c:forEach>
										<c:remove var="currAudi" />	
									</c:if>
									<c:if test="${editMode}">
										<li class="collection-editor-list-buttons">
											<div class="col-sm-12">
												<button onclick="editor.lists['audiences'].triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.collection.actions.add_audience" /></button>
											</div>
										</li>
									</c:if>
								</ul>
							</c:when>
							<c:otherwise>
								<label class="content-label">
									<c:forEach items="${collection.audiences}" var="audi" varStatus="status" >${audi}<br/></c:forEach>
								</label>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.audiences" />
						</div>
					</div>
				</div>
			</s:bind>
			
			<!-- Provenance* -->
			<s:bind path="provenanceInfo">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for=provenanceInfo class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.provenance" /></label>
					<div class="col-sm-9">
						<c:choose>
							<c:when test="${editMode}">
								<sf:input path="provenanceInfo" class="form-control" />
							</c:when>
							<c:otherwise>
								<label class="content-label">${collection.provenanceInfo}</label>
							</c:otherwise>
						</c:choose>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="provenanceInfo" />
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.provenance_info" />
						</div>
					</div>
				</div>
			</s:bind>
			
			
			
			<!-- Associated project -->
			<s:bind path="associatedProject">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="associatedProject" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.associated_project" /></label>
					<div class="col-sm-9">
						<c:choose>
							<c:when test="${editMode}">
								<sf:textarea class="form-control" rows="3" path="associatedProject"></sf:textarea>
							</c:when>
							<c:otherwise>
								<label class="content-label">${collection.associatedProject}</label>
							</c:otherwise>
						</c:choose>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="associatedProject" />
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.associated_project" />
						</div>
					</div>
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
									<th class="nowrap"><s:message code="~eu.dariah.de.colreg.model.accrual.periodicity" /></th>
									<c:if test="${editMode}"><th class="nowrap"></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(collection.accrualMethods)>0}">
										<c:forEach items="${collection.accrualMethods}" var="method" varStatus="status" >
											<c:set var="currMethod" value="${method}" scope="request" />
											<c:set var="currIndex" value="${status.index}" scope="request" />
											<jsp:include page="incl/edit_accrual.jsp" />
										</c:forEach>
										<c:remove var="currMethod" />	
									</c:when>
									
								</c:choose>
								<c:if test="${editMode}">
									<tr class="collection-editor-table-buttons">
										<td colspan="${editMode ? 4 : 3}">
											<button class="btn btn-xs pull-right btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.collection.actions.add_accrual" /></button>		
										</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="accrualMethods" />
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.accrual" />
						</div>
					</div>
				</div>
			</s:bind>
			
			<!-- Item rights -->
			<s:bind path="itemRights">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="itemRights" class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.item_rights" /></label>
					<c:set var="currRightsLicenseId" value="${itemRightsIsLicenseId}" scope="request" />
					<c:set var="currRightsName" value="itemRights" scope="request" />
					<c:set var="currRightsValue" value="${collection.itemRights}" scope="request" />
					<jsp:include page="incl/edit_rights.jsp" />	
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="itemRights" />
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.item_rights" />
						</div>
					</div>
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
									<c:if test="${editMode}"><th class="nowrap"></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(collection.accessMethods)>0}">
										<c:forEach items="${collection.accessMethods}" var="method" varStatus="status" >
											<c:set var="currMethod" value="${method}" scope="request" />
											<c:set var="currIndex" value="${status.index}" scope="request" />
											<jsp:include page="incl/edit_access.jsp" />
										</c:forEach>
										<c:remove var="currMethod" />	
									</c:when>
									
								</c:choose>
								<c:if test="${editMode}">
									<tr class="collection-editor-table-buttons">
										<td colspan="${editMode ? 3 : 2}">
											<button class="btn btn-xs pull-right btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><s:message code="~eu.dariah.de.colreg.view.collection.actions.add_access" /></button>		
										</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="accessMethods" />
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.access" />
						</div>
					</div>
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
						<label class="content-label">
							<a href="<s:url value="/collections/${collection.entityId}" />">${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}<s:url value="/collections/${collection.entityId}" /></a>
						</label>
					</div>
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.collection_identifier" />
						</div>
					</div>
				</div>
				
				<!-- Version id -->
				<div class="form-group">
					<label class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.version_identifier" /></label>
					<div id="version-identifier" class="col-sm-9">
						<label class="content-label">
							<a href="<s:url value="/collections/${collection.id}" />">${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}<s:url value="/collections/${collection.id}" /></a>
						</label>
					</div>
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.version_identifier" />
						</div>
					</div>
				</div>
	
				<!-- Version created -->
				<div class="form-group">
					<label class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.current_version" /></label>
					<div id="current-version" class="col-sm-9">
						<label class="control-label">
							<a href="javascript:void(0)"><joda:format value="${collection.versionTimestamp}" style="LM" /> (${collection.versionCreator})</a> 
						</label>
					</div>
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.current_version" />
						</div>
					</div>
				</div>
	
				<!-- Entity timestamp -->
				<div class="form-group">
					<label class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.created" /></label>
					<div id="initially-created" class="col-sm-9">
						<label class="control-label">
							<a href="javascript:void(0)"><joda:format value="${collection.entityTimestamp}" style="LM" /> (${collection.entityCreator})</a> 
						</label>
					</div>
					<div class="col-sm-9 col-sm-offset-3">
						<div class="editor-hint">
							<span class="glyphicon glyphicon-info-sign glyphicon-color-info" aria-hidden="true"></span> 
							<s:message code="~eu.dariah.de.colreg.editorhint.collection.created" />
						</div>
					</div>
				</div>
				
				<c:if test="${!isDeleted && _auth!=null && _auth.auth && editMode}">
					<div class="form-group">
						<label class="col-sm-3 control-label"><s:message code="~eu.dariah.de.colreg.model.collection.groups.administration" /></label>
						<div id="collection-administration" class="col-sm-9">
							<c:choose>
								<c:when test="${activeChildCollections}">
									<div class="alert alert-warning alert-sm" role="alert">
										<s:message code="~eu.dariah.de.colreg.view.collection.notification.not_deletable" />
									</div>
								</c:when>
								<c:otherwise>
									<div class="alert alert-warning alert-sm" role="alert">
										<s:url value="/collections/${collection.entityId}" var="latest_link" />
										<s:message code="~eu.dariah.de.colreg.view.collection.notification.deletable" arguments="${collection.entityId}" />
									</div>		
									<button id="btn-delete-collection" class="btn btn-danger cancel"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span> <s:message code="~eu.dariah.de.colreg.common.actions.delete" /></button>					
								</c:otherwise>
							</c:choose>
							<c:if test="${collection.draftUserId!=null}">
								<button id="btn-publish-collection" class="btn btn-primary cancel"><span class="glyphicon glyphicon-globe" aria-hidden="true"></span> <s:message code="~eu.dariah.de.colreg.common.actions.save_and_publish" /></button>
							</c:if>
						</div>
					</div>
				</c:if>
			</div>		
		</c:if>
		
		<c:if test="${!isDeleted && agent.succeedingVersionId==null && _auth!=null && _auth.auth && editMode}">
			<div class="form-group editor-buttonbar">
				<div class="col-sm-12">
					<div class="pull-right">
						<a href='<s:url value="/collections/" />' class="btn btn-default cancel form-btn-cancel"><s:message code="~eu.dariah.de.colreg.common.actions.cancel" /></a>
						<button class="btn btn-primary start form-btn-submit"><s:message code="~eu.dariah.de.colreg.common.actions.save" /></button>
					</div>
				</div>
			</div>
		</c:if>
				
	</sf:form>
</div>
