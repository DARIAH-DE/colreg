<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:importAttribute name="fluidLayout" />

<s:url value="${a.id}" var="actionPath" />

<ul class="breadcrumb">
	<li><a href='<s:url value="/" />' target="_self">~Collection Registry</a></li>
	<li><a href='<s:url value="/collections/" />' target="_self">~Agents</a></li>
	<li class="active">
		<c:choose>
			<c:when test="${a.id=='new'}">~New Agent</c:when>
			<c:otherwise>Agent Id: ${a.entityId}</c:otherwise>
		</c:choose>
	</li>
</ul>
<div id="main-content">
	<h1>~Agent Editor</h1>
	<sf:form method="POST" action="${actionPath}" modelAttribute="a" class="form-horizontal">
		
		<div class="form-group">
			<div class="col-sm-12">
				<div class="pull-right">
					<button class="btn btn-default cancel form-btn-cancel" type="reset">~ Cancel</button>
					<button class="btn btn-primary start form-btn-submit" type="submit">~ Save</button>
				</div>
			</div>
		</div>
						
		<legend>~Agent description</legend>
		
		<div class="form-group">
			<label for="description" class="col-sm-3 control-label">~Name</label>
			<div class="col-sm-9">
				<sf:input path="name" class="form-control" placeholder="~Agent name" />
			</div>
		</div>
		<div class="form-group">
			<label for="description" class="col-sm-3 control-label">~Type</label>
			<div class="col-sm-9">
				<sf:input path="agentTypeId" class="form-control" placeholder="~Agent type" />
			</div>
		</div>
		<div class="form-group">
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
		
		<sf:hidden path="entityId" />
		
	</sf:form>
</div>
