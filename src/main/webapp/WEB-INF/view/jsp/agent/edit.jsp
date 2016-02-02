<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:importAttribute name="fluidLayout" />

<s:url value="${a.id}" var="actionPath" />

<ul class="breadcrumb">
	<li><a href='<s:url value="/" />' target="_self">~Collection Registry</a></li>
	<li><a href='<s:url value="/agents/" />' target="_self">~Agents</a></li>
	<li class="active">
		<c:choose>
			<c:when test="${a.id=='new'}">~New Agent</c:when>
			<c:otherwise>Agent Id: ${a.entityId}</c:otherwise>
		</c:choose>
	</li>
</ul>
<div id="main-content">
	<h1>~Agent Editor</h1>
	<sf:form method="POST" action="${actionPath}" modelAttribute="a" class="form-horizontal" autocomplete="off">
		
		<div class="form-group">
			<div class="col-sm-12">
				<div class="pull-right">
					<button class="btn btn-default cancel form-btn-cancel" type="reset">~ Cancel</button>
					<button class="btn btn-primary start form-btn-submit" type="submit">~ Save</button>
				</div>
			</div>
		</div>
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4>~Agent type</h4>
			</div>
			<c:set var="agentIsNatural" value="${agentTypes[0].naturalPerson}" scope="request" />		
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label mandatory">~Type</label>
				<div class="col-sm-4">
					<select class="form-control" name="agentTypeId" id="agentTypeId" onchange="editor.handleAgentTypeChange(this);" autocomplete="off">
						<c:forEach items="${agentTypes}" var="type">
							<option <c:if test="${a.agentTypeId==type.id}">selected="selected"</c:if> value="${type.id}" data-natural="${type.naturalPerson}">${type.label}</option>
							<c:if test="${a.agentTypeId==type.id}">
								<c:set var="agentIsNatural" value="${type.naturalPerson}" scope="request" />
							</c:if>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4>~Agent description</h4>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label mandatory agent-nonnatural-only" <c:if test="${agentIsNatural}"> style="display: none;"</c:if>>~Name</label>
				<label for="description" class="col-sm-3 control-label mandatory agent-natural-only" <c:if test="${!agentIsNatural}"> style="display: none;"</c:if>>~Last Name</label>
				<div class="col-sm-9">
					<sf:input path="name" class="form-control" placeholder="~Agent name" />
				</div>
			</div>
			<div class="form-group agent-natural-only" <c:if test="${!agentIsNatural}"> style="display: none;"</c:if>>
				<label for="description" class="col-sm-3 control-label">~Fore Name</label>
				<div class="col-sm-9">
					<sf:input path="foreName" class="form-control" placeholder="~Fore name" />
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Address</label>
				<div class="col-sm-9">
					<sf:input path="address" class="form-control" placeholder="~Address" />
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~e-Mail</label>
				<div class="col-sm-9">
					<sf:input path="eMail" class="form-control" placeholder="~e-Mail" />
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Webpage</label>
				<div class="col-sm-9">
					<sf:input path="webPage" class="form-control" placeholder="~Webpage" />
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Phone</label>
				<div class="col-sm-9">
					<sf:input path="phone" class="form-control" placeholder="~Phone" />
				</div>
			</div>
		</div>
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4>~Agent hierarchy</h4>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Parent or containing agent</label>
				<div class="col-sm-5">
					<input type="text" id="parentAgentIdSelector" class="form-control" placeholder="~ Quick search by name/id" />
					<sf:hidden path="parentAgentId" />
				</div>
				<div class="col-sm-6 sol-sm-offset-3">
					<div id="parentAgent-display" class="alert alert-info alert-dismissible <c:if test="${parentAgent==null}">hide</c:if>">
						<button id="parentAgentIdReset" type="button" class="close"><span aria-hidden="true">&times;</span></button>
							<p>
						<c:if test="${parentAgent!=null}">
							<strong>${parentAgent.name}  ${parentAgent.foreName}</strong><br />
							<small><em>ID: ${parentAgent.id}</em></small><br />
							${parentAgent.address}
						</c:if>	
						</p>
					</div>
					<div id="parentAgent-display-null" class="well well-sm <c:if test="${parentAgent!=null}">hide</c:if>">
						~ No parent agent set
					</div>
				</div>
				
			</div>
		</div>
		
		<div class="editor-section">
			<div class="editor-section-heading">
				<h4>~Agent identification</h4>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Collection Identifier</label>
				<div class="col-sm-9">
					<sf:input path="entityId" class="form-control" placeholder="~Identifier" readonly="true" />
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~Current description version</label>
				<div class="col-sm-9">
					<sf:input path="id" class="form-control" placeholder="~Identifier" readonly="true" />
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-3 control-label">~ Provided identifier</label>
				<div class="col-sm-9">
					<table id="tbl-agent-identifier" class="collection-editor-table">
						<thead>
							<tr>
								<th class="explode">~Identifier</th>
								<th class="nowrap">~</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${fn:length(a.providedIdentifier)>0}">
								<c:forEach items="${a.providedIdentifier}" var="identifier" varStatus="status" >
									<c:set var="currIdentifier" value="${identifier}" scope="request" />
									<c:set var="currIndex" value="${status.index}" scope="request" />
									<jsp:include page="incl/edit_identifier.jsp" />
								</c:forEach>
								<c:remove var="currIdentifier" />	
							</c:if>
							<tr class="collection-editor-table-buttons">
								<td colspan="4" style="text-align: right;">
									<button class="btn btn-xs btn-link btn-collection-editor-add"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>~ Add identifier</button>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<sf:hidden path="entityId" />
		</div>
	</sf:form>
</div>
