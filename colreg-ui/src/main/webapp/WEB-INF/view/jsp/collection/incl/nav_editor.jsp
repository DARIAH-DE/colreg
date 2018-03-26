<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<nav class="list-group active">
	<h4><s:message code="~eu.dariah.de.colreg.titles.editor_options" /></h4>
  	<div class="list-group-item">
  		<div class="checkbox">
		  <label>
		    <input id="chk-toggle-hints" type="checkbox" value=""> <s:message code="~eu.dariah.de.colreg.view.common.labels.show_hints" />
		  </label>
		</div>
  	</div>
</nav>

<nav class="list-group nav-form-controls">
	<h4><s:message code="~eu.dariah.de.colreg.titles.properties" /></h4>
	<ul class="nav">
		<li><a href="#"><s:message code="~eu.dariah.de.colreg.model.collection.groups.mandatory_description" /></a>
			<ul class="nav">
				<s:bind path="collection.localizedDescriptions*">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#tbl-collection-description-sets"><s:message code="~eu.dariah.de.colreg.model.collection.description_sets" /></a></li>
				</s:bind>
				<s:bind path="collection.collectionType">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#collectionType"><s:message code="~eu.dariah.de.colreg.model.collection.collection_type" /></a></li>
				</s:bind>
				<s:bind path="collection.collectionDescriptionRights">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#collectionDescriptionRights"><s:message code="~eu.dariah.de.colreg.model.collection.description_rights" /></a></li>
				</s:bind>
				<s:bind path="collection.accessRights">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#accessRights"><s:message code="~eu.dariah.de.colreg.model.collection.access_rights" /></a></li>
				</s:bind>
			</ul>
		</li>
		<li><a href="#"><s:message code="~eu.dariah.de.colreg.model.collection.groups.contact_and_agents" /></a>
			<ul class="nav">
				<s:bind path="collection.webPage">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#webPage"><s:message code="~eu.dariah.de.colreg.model.collection.webpage" /></a></li>
				</s:bind>
				<s:bind path="collection.eMail">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#eMail"><s:message code="~eu.dariah.de.colreg.model.collection.email" /></a></li>
				</s:bind>
				<s:bind path="collection.locations*">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#tbl-collection-locations"><s:message code="~eu.dariah.de.colreg.model.collection.locations" /></a></li>
				</s:bind>
				<s:bind path="collection.agentRelations*">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#tbl-collection-agents"><s:message code="~eu.dariah.de.colreg.model.collection.agents" /></a></li>
				</s:bind>
			</ul>
		</li>
		<li><a href="#"><s:message code="~eu.dariah.de.colreg.model.collection.groups.content_related" /></a>
			<ul class="nav">
				<s:bind path="collection.itemLanguages*">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#lst-collection-item-languages"><s:message code="~eu.dariah.de.colreg.model.collection.item_languages" /></a></li>
				</s:bind>
				<s:bind path="collection.subjects*">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#lst-collection-subjects"><s:message code="~eu.dariah.de.colreg.model.collection.subjects" /></a></li>
				</s:bind>
				<s:bind path="collection.temporals*">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#lst-collection-temporals"><s:message code="~eu.dariah.de.colreg.model.collection.temporals" /></a></li>
				</s:bind>
				<s:bind path="collection.spatials*">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#lst-collection-spatials"><s:message code="~eu.dariah.de.colreg.model.collection.spatials" /></a></li>
				</s:bind>
				<s:bind path="collection.collectionCreated">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#collectionCreated"><s:message code="~eu.dariah.de.colreg.model.collection.collection_created" /></a></li>
				</s:bind>
				<s:bind path="collection.itemsCreated">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#itemsCreated"><s:message code="~eu.dariah.de.colreg.model.collection.items_created" /></a></li>
				</s:bind>
				<s:bind path="collection.itemTypeIds">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#itemTypeIds"><s:message code="~eu.dariah.de.colreg.model.collection.item_types" /></a></li>
				</s:bind>
				<s:bind path="collection.size">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#size"><s:message code="~eu.dariah.de.colreg.model.collection.size" /></a></li>
				</s:bind>
			</ul>
		</li>
		<li><a href="#"><s:message code="~eu.dariah.de.colreg.model.collection.groups.contextual" /></a>
			<ul class="nav">
				<s:bind path="collection.parentCollectionId">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#parentCollectionIdSelector"><s:message code="~eu.dariah.de.colreg.model.collection.parent_collection" /></a></li>
				</s:bind>
				<li><a href="#lst-child-collections"><s:message code="~eu.dariah.de.colreg.model.collection.child_collections" /></a></li>
				<s:bind path="collection.providedIdentifier*">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#lst-collection-provided-identifiers"><s:message code="~eu.dariah.de.colreg.model.collection.provided_identifiers" /></a></li>
				</s:bind>
				<s:bind path="collection.audiences*">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#lst-collection-audiences"><s:message code="~eu.dariah.de.colreg.model.collection.audiences" /></a></li>
				</s:bind>
				<s:bind path="collection.provenanceInfo">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#provenanceInfo"><s:message code="~eu.dariah.de.colreg.model.collection.provenance" /></a></li>
				</s:bind>
				<s:bind path="collection.associatedProject">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#associatedProject"><s:message code="~eu.dariah.de.colreg.model.collection.associated_project" /></a></li>
				</s:bind>
				<s:bind path="collection.collectionImages">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#collectionImages"><s:message code="~eu.dariah.de.colreg.model.collection.collection_images" /></a></li>
				</s:bind>
				<s:bind path="collection.collectionImageRights">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#collectionImageRights"><s:message code="~eu.dariah.de.colreg.model.collection.collection_image_rights" /></a></li>
				</s:bind>
			</ul>
		</li>
		<li><a href="#"><s:message code="~eu.dariah.de.colreg.model.collection.groups.policy" /></a>
			<ul class="nav">
				<s:bind path="collection.accrualMethods*">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#tbl-collection-accrual"><s:message code="~eu.dariah.de.colreg.model.collection.accrual" /></a></li>
				</s:bind>
				<s:bind path="collection.itemRights">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#itemRights"><s:message code="~eu.dariah.de.colreg.model.collection.item_rights" /></a></li>
				</s:bind>
			</ul>
		</li>
		<li><a href="#"><s:message code="~eu.dariah.de.colreg.model.collection.groups.access" /></a>
			<ul class="nav">
				<s:bind path="collection.accessMethods*">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#tbl-collection-access"><s:message code="~eu.dariah.de.colreg.model.collection.access" /></a></li>
				</s:bind>
			</ul>
		</li>
		<c:if test="${!isNew}">
			<li><a href="#"><s:message code="~eu.dariah.de.colreg.model.collection.groups.identification_and_administration" /></a>
				<ul class="nav">
					<li><a href="#collection-identifier"><s:message code="~eu.dariah.de.colreg.model.collection.collection_identifier" /></a></li>
					<li><a href="#version-identifier"><s:message code="~eu.dariah.de.colreg.model.collection.version_identifier" /></a></li>
					<li><a href="#current-version"><s:message code="~eu.dariah.de.colreg.model.collection.current_version" /></a></li>
					<li><a href="#initially-created"><s:message code="~eu.dariah.de.colreg.model.collection.created" /></a></li>
					<c:if test="${!isDeleted && editMode}">
						<li><a href="#collection-administration"><s:message code="~eu.dariah.de.colreg.model.collection.groups.administration" /></a></li>
					</c:if>
				</ul>
			</li>		
		</c:if>
	</ul>
</nav>