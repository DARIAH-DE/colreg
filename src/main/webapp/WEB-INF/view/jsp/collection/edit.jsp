<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:importAttribute name="fluidLayout" />

<s:url value="${collection.entityId}" var="actionPath" />

<ul class="breadcrumb">
	<li><a href='<s:url value="/" />' target="_self">~Collection Registry</a></li>
	<li><a href='<s:url value="/collections/" />' target="_self">~Collections</a></li>
	<li class="active">
		<c:choose>
			<c:when test="${collection.id=='new'}">~New Collection</c:when>
			<c:otherwise>Collection Id: ${collection.entityId}</c:otherwise>
		</c:choose>
	</li>
</ul>
<div id="main-content">
	<h1>~Collection Editor</h1>
	<input type="hidden" id="js-form-action" value="${actionPath}" />
	<sf:form method="POST" action="javascript:void(0);" modelAttribute="collection" class="form-horizontal" autocomplete="off">

		<div class="form-group">
			<div class="col-sm-12">
				<c:if test="${collection.deleted}">
					<div class="alert alert-warning" role="alert">
						~ This collection is marked deleted and is as such only accessible through its permalink   
					</div>
				</c:if>
				<div id="entity-notifications-area">
					<s:bind path="*">
					<c:if test="${status.error}">
						<div class="alert alert-danger">
							~ There are validation errors, the object has not been saved
						</div>
					</c:if>
					</s:bind>
				</div>
			</div>
		</div>

		<div class="form-group editor-buttonbar">
			<div class="col-sm-12">
				<div class="pull-right">
					<button class="btn btn-default cancel form-btn-cancel">~ Cancel</button>
					<button class="btn btn-info start form-btn-submit">~ Save as draft</button>
					<button class="btn btn-primary start form-btn-submit">~ Publish</button>
				</div>
			</div>
		</div>
				
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4>~Mandatory description</h4>
			</div>
			
			<!-- Localized collection descriptions -->
			<s:bind path="localizedDescriptions*">
				<div class="form-group">
					<label for="description" class="col-sm-3 control-label${status.error ? ' container-error' : ' '}">~Localized description sets</label>
					<div class="col-sm-9">
						<table id="tbl-collection-description-sets" class="collection-editor-table">
							<thead>
								<tr>
									<th class="explode">~Title</th>
									<th class="nowrap">~Acronym</th>
									<th class="nowrap">~Language</th>
									<th class="nowrap">~</th>
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
										<button class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>~ Add description set</button>
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
					<label for="description" class="col-sm-3 control-label">~Collection Type</label>
					<div class="col-sm-9">
						<sf:input path="collectionType" class="form-control" placeholder="~Collection Type" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="collectionType" />
				</div>
			</s:bind>
			
			<!-- Rights of collection description -->
			<s:bind path="collectionDescriptionRights">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="description" class="col-sm-3 control-label">~Collection Description Rights</label>
					<div class="col-sm-9">
						<sf:input path="collectionDescriptionRights" class="form-control" placeholder="~Collection Description Rights" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="collectionDescriptionRights" />
				</div>
			</s:bind>
			
			<!-- Access rights -->
			<s:bind path="accessRights">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="description" class="col-sm-3 control-label">~Access Rights</label>
					<div class="col-sm-9">
						<sf:input path="accessRights" class="form-control" placeholder="~Access Rights" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="accessRights" />
				</div>
			</s:bind>
			
		</div>
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4>~Collection contact &amp; agents</h4>
			</div>
			
			<!-- Web page -->
			<s:bind path="webPage">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="description" class="col-sm-3 control-label">~Web Page</label>
					<div class="col-sm-9">
						<sf:input path="webPage" class="form-control" placeholder="~Web Page" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="webPage" />
				</div>
			</s:bind>
			
			<!-- e-Mail -->
			<s:bind path="eMail">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="description" class="col-sm-3 control-label">~Primary E-Mail</label>
					<div class="col-sm-9">
						<sf:input path="eMail" class="form-control" placeholder="~E-Mail" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="eMail" />
				</div>
			</s:bind>
			
			<!-- Agents -->
			<s:bind path="agentRelations*">
				<div class="form-group">
					<label for="description" class="col-sm-3 control-label${status.error ? ' container-error' : ' '}">~Associated Agents</label>
					<div class="col-sm-9">
						<table id="tbl-collection-agents" class="collection-editor-table">
							<thead>
								<tr>
									<th class="explode">~Agent name</th>
									<th class="explode">~Relation</th>
									<th class="nowrap">~</th>
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
										<button class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>~ Add agent relation</button>
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
				<h4>~Content &amp; item related</h4>
			</div>
			
			<!-- Item language* -->
			<s:bind path="itemLanguages*">
				<div class="form-group${status.error ? ' container-error' : ' '}">
					<label for="description" class="col-sm-3 control-label">~ Item Languages</label>
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
									<button onclick="editor.lists['itemLanguageList'].triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>~ Add item language</button>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</s:bind>
			
			<!-- Subject* -->
			<s:bind path="subjects*">
				<div class="form-group${status.error ? ' container-error' : ' '}">
					<label for="description" class="col-sm-3 control-label">~ Subjects:</label>
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
									<button onclick="editor.lists['subjects'].triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>~ Add subject</button>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</s:bind>
			
			<!-- Temporal* -->
			<s:bind path="temporals*">
				<div class="form-group${status.error ? ' container-error' : ' '}">
					<label for="description" class="col-sm-3 control-label">~ Temporals:</label>
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
									<button onclick="editor.lists['temporals'].triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>~ Add temporal</button>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</s:bind>
			
			<!-- Spatial* -->
			<s:bind path="spatials*">
				<div class="form-group${status.error ? ' container-error' : ' '}">
					<label for="description" class="col-sm-3 control-label">~ Spatials:</label>
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
									<button onclick="editor.lists['spatials'].triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>~ Add spatial</button>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</s:bind>

			<!-- Collection Created* -->
			<s:bind path="collectionCreated">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="collectionCreated" class="col-sm-3 control-label">~Collection created</label>
					<div class="col-sm-9">
						<sf:input path="collectionCreated" class="form-control" placeholder="~Collection created" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="collectionCreated" />
				</div>
			</s:bind>
			
			<!-- Items Created* -->
			<s:bind path="itemsCreated">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="itemsCreated" class="col-sm-3 control-label">~Items created</label>
					<div class="col-sm-9">
						<sf:input path="itemsCreated" class="form-control" placeholder="~Items created" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="itemsCreated" />
				</div>
			</s:bind>
			
			<!-- Item type* -->
			<s:bind path="itemTypeIds">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="itemTypeIds" class="col-sm-3 control-label">~Item types</label>
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
					<label for="description" class="col-sm-3 control-label">~Collection Size</label>
					<div class="col-sm-3">
						<input type="number" id="size" name="size" class="form-control" placeholder="~Collection Size" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="size" />
				</div>
			</s:bind>
		</div>
			
		
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4>~Contextual</h4>
			</div>
			
			<!-- Is part of -->
			<s:bind path="parentCollectionId">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="description" class="col-sm-3 control-label">~Parent/Containing collection:</label>
					<div class="col-sm-5">
						<input type="text" id="parentCollectionIdSelector" class="form-control" placeholder="~ Quick search by name/id" />
						<sf:hidden path="parentCollectionId" />
					</div>
					<div class="col-sm-9">
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
							<label class="control-label"><em>~ No parent collection set</em></label>
						</div>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="parentCollectionId" />
				</div>
			</s:bind>
			
			<!-- Has part of -->
			<div class="form-group">
				<label for="child-agents" class="col-sm-3 control-label">~ Subordinate collections</label>
				<div class="col-sm-9">
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
							<label class="control-label"><em>~ No collections have been assigned</em></label>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			
			<!-- Provided identifiers -->
			<s:bind path="providedIdentifier*">
				<div class="form-group">
					<label for="description" class="col-sm-3 control-label${status.error ? ' container-error' : ' '}">~External identifiers</label>
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
									<button onclick="editor.lists['identifierList'].triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>~ Add identifier</button>
								</div>
							</li>
						</ul>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="providedIdentifier" />
				</div>
			</s:bind>
			
			<!-- Location* -->
			<s:bind path="locations*">
				<div class="form-group${status.error ? ' container-error' : ' '}">
					<label for="locations" class="col-sm-3 control-label">~ Locations:</label>
					<div class="col-sm-9">
						<ul id="lst-collection-locations" class="collection-editor-list">
							<c:if test="${fn:length(collection.locations)>0}">
								<c:forEach items="${collection.locations}" var="loc" varStatus="status" >
									<c:set var="currLoc" value="${loc}" scope="request" />
									<c:set var="currIndex" value="${status.index}" scope="request" />
									<jsp:include page="incl/edit_location.jsp" />
								</c:forEach>
								<c:remove var="currLoc" />	
							</c:if>
							<li class="collection-editor-list-buttons">
								<div class="col-sm-12">
									<button onclick="editor.lists['locations'].triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>~ Add location</button>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</s:bind>
			
			<!-- Audience* -->
			<s:bind path="audiences*">
				<div class="form-group${status.error ? ' container-error' : ' '}">
					<label for="audiences" class="col-sm-3 control-label">~ Audiences:</label>
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
									<button onclick="editor.lists['audiences'].triggerAddListElement(this);" class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>~ Add audience</button>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</s:bind>
			
			<!-- Provenance* -->
			<s:bind path="provenanceInfo">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="description" class="col-sm-3 control-label">~Provenance information</label>
					<div class="col-sm-9">
						<sf:input path="provenanceInfo" class="form-control" placeholder="~Provenance information" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="provenanceInfo" />
				</div>
			</s:bind>
			
			
			
			<!-- Associated project -->
			<s:bind path="associatedProject">
				<div class="form-group${status.error ? ' has-error' : ' '}">
					<label for="description" class="col-sm-3 control-label">~Associated project</label>
					<div class="col-sm-9">
						<sf:input path="associatedProject" class="form-control" placeholder="~Associated project" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="associatedProject" />
				</div>
			</s:bind>
			
			
		</div>
		
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4>~Collection policy</h4>
			</div>
			
			<!-- Accrual -->
			<s:bind path="accrualMethods*">
				<div class="form-group">
					<label for="description" class="col-sm-3 control-label${status.error ? ' container-error' : ' '}">~Accrual</label>
					<div class="col-sm-9">
						<table id="tbl-collection-accrual" class="collection-editor-table">
							<thead>
								<tr>
									<th class="explode">~Method</th>
									<th class="nowrap">~Policy</th>
									<th class="nowrap">~</th>
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
										<button class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>~ Add accrual method</button>
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
					<label for="description" class="col-sm-3 control-label">~Item Rights</label>
					<div class="col-sm-9">
						<sf:input path="itemRights" class="form-control" placeholder="~Item Rights" />
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="itemRights" />
				</div>
			</s:bind>
		</div>
		
			<div class="editor-section">
			<div class="editor-section-heading">
				<h4>~Collection access</h4>
			</div>
			
			<!-- Access method -->
			<s:bind path="accessMethods*">
				<div class="form-group">
					<label for="description" class="col-sm-3 control-label${status.error ? ' container-error' : ' '}">~Access</label>
					<div class="col-sm-9">
						<table id="tbl-collection-access" class="collection-editor-table">
							<thead>
								<tr>
									<th class="explode">~URI</th>
									<th class="nowrap">~Type</th>
									<th class="nowrap">~</th>
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
										<button class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>~ Add access method</button>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<sf:errors element="div" cssClass="validation-error col-sm-9 col-sm-offset-3" path="accessMethods" />
				</div>
			</s:bind>
			
		</div>
		

		<div class="editor-section">
			<div class="editor-section-heading">
				<h4>~Collection identification</h4>
			</div>
			
			<!-- Entity id -->
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Permanent collection identifier</label>
				<div class="col-sm-9">
					<label class="control-label">
						<a href="<s:url value="/collections/${collection.entityId}" />">${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}<s:url value="/collections/${collection.entityId}" /></a>
					</label>
				</div>
			</div>
			
			<!-- Version id -->
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Permanent version identifier ${contextPath}</label>
				<div class="col-sm-9">
					<label class="control-label">
						<a href="<s:url value="/collections/${collection.id}" />">${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}<s:url value="/collections/${collection.id}" /></a>
					</label>
				</div>
			</div>

			<!-- Version created -->
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Current version</label>
				<div class="col-sm-9">
					<label class="control-label">${collection.versionTimestamp}</label><br />
					<label class="control-label">${collection.versionCreator}</label>
				</div>
			</div>

			<!-- Entity timestamp -->
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Initially created</label>
				<div class="col-sm-9">
					<label class="control-label">${collection.entityTimestamp}</label><br />
					<label class="control-label">${collection.entityCreator}</label>
				</div>
			</div>
			
		</div>
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4>~Administrative actions</h4>
			</div>
			<div class="form-group">
				<div class="col-sm-12">
					<c:if test="${collection.deleted==false}">
						<c:choose>
							<c:when test="${activeChildCollections==false && isDraft==false}">
								<div class="alert alert-warning alert-sm" role="alert">
									~ This collection has not been published and could thus be marked as deleted. 
								</div>
								<div>
									<button id="btn-delete-collection" class="btn btn-sm btn-danger">~ Mark deleted</button>
								</div>
							</c:when>
							<c:otherwise>
								<c:if test="${isDraft}">
									<div class="alert alert-warning alert-sm" role="alert">
										~ This collection has already been published and cannot be marked as deleted.   
									</div>
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:if>
				</div>
			</div>
		</div>
		

		<div class="form-group editor-buttonbar">
			<div class="col-sm-12">
				<div class="pull-right">
					<button class="btn btn-default cancel form-btn-cancel">~ Cancel</button>
					<button class="btn btn-primary start form-btn-submit">~ Save as draft</button>
					<button class="btn btn-primary start form-btn-submit">~ Publish</button>
				</div>
			</div>
		</div>
				
	</sf:form>
</div>
