<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<tiles:importAttribute name="fluidLayout" />

<ul class="breadcrumb">
	<li><a href='<s:url value="/" />' target="_self"><s:message code="~eu.dariah.de.colreg.titles.collection_registry" /></a></li>
	<li class="active"><s:message code="~eu.dariah.de.colreg.titles.vocabularies" /></li>
	<li class="active">${vocabulary.localizedLabel}</li>
</ul>
<div id="main-content">
	<div class="row">
		<div id="vocabulary-table-container" class="col-md-12">			
			<h2 class="pull-left">${vocabulary.localizedLabel}</h2>
			<input type="hidden" id="vocabulary-id" value="${vocabulary.id}" />
			<div class="pull-right">
				<c:if test="${_auth!=null && _auth.auth}">
					<button onclick="vocabularyTable.triggerEditVocabularyItem('new');" class="btn btn-primary btn-sm pull-left">
						<span class="glyphicon glyphicon-plus"></span> <s:message code="~eu.dariah.de.colreg.view.agent.actions.add_vocabulary_item" />
					</button>
				</c:if>
				<div class="data-table-filter pull-left">
					<input type="text" class="form-control input-sm" placeholder='<s:message code="~eu.dariah.de.colreg.common.labels.filter" />'>
				</div>
				<div class="data-table-count pull-left">
					<select class="form-control input-sm">
					  <option>10</option>
					  <option>25</option>
					  <option>50</option>
					  <option>100</option>
					  <option><s:message code="~eu.dariah.de.colreg.common.labels.all" /></option>
					</select>
				</div>					
			</div>
			<div class="clearfix">
				<table id="vocabulary-table" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th><s:message code="~eu.dariah.de.colreg.model.vocabulary_item.identifier" /></th>
							<th><s:message code="~eu.dariah.de.colreg.model.vocabulary_item.default_name" /></th>
							<th><s:message code="~eu.dariah.de.colreg.model.vocabulary_item.local_name" /></th>
							<th><!-- Actions --></th>
						</tr>
					</thead>
					<tbody>
					<tr>
						<td colspan="4" align="center"><s:message code="~eu.dariah.de.colreg.common.view.notifications.nothing_fetched_yet" /></td>
					</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>