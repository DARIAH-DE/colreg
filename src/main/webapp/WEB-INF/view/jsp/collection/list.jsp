<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<tiles:importAttribute name="fluidLayout" />

<c:choose>
	<c:when test="${load=='drafts'}">
		<s:message code="~eu.dariah.de.colreg.titles.drafts" var="title" />					
	</c:when>
	<c:otherwise>
		<s:message code="~eu.dariah.de.colreg.titles.collections" var="title" />
	</c:otherwise>
</c:choose>

<ul class="breadcrumb">
	<li><a href='<s:url value="/" />' target="_self"><s:message code="~eu.dariah.de.colreg.titles.collection_registry" /></a></li>
	<li class="active">${title}</li>
</ul>
<div id="main-content">
	<div class="row">
		<div ${load=="drafts" ? 'id="draft-table-container"' : 'id="collection-table-container"'} class="col-md-12">
			<h2 class="pull-left">${title}</h2>
			<div class="pull-right">
				<c:if test="${_auth!=null && _auth.auth}">
					<a href="<s:url value='new' />" class="btn btn-primary btn-sm pull-left">
						<span class="glyphicon glyphicon-plus"></span> <s:message code="~eu.dariah.de.colreg.view.collection.actions.add_collection" />
					</a>
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
				<table ${load=="drafts" ? 'id="draft-table"' : 'id="collection-table"'} class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th><!-- ~Badges --></th> 
							<th><s:message code="~eu.dariah.de.colreg.model.localized_description.title" /></th>
							<th><s:message code="~eu.dariah.de.colreg.model.collection.collection_type" /></th>
							<th><s:message code="~eu.dariah.de.colreg.model.collection.access" /></th>
							<th><s:message code="~eu.dariah.de.colreg.model.collection.current_version" /></th>
							<th><!-- ~Actions --></th>
						</tr>
					</thead>
					<tbody>
					<tr>
						<td colspan="6" align="center"><s:message code="~eu.dariah.de.colreg.common.view.notifications.nothing_fetched_yet" /></td>
					</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>