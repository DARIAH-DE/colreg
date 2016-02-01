<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<tiles:importAttribute name="fluidLayout" />

<div class="jumbotron jumbotron-small">
	 <div class="container<c:if test="${fluidLayout==true}">-fluid</c:if>">
		<div class="row">
			<!-- Notifications -->
			<div id="notifications-area" class="col-sm-10 col-sm-offset-1"></div>
			<div class="xs-hidden sm-visible col-sm-3 col-lg-2 col-sm-offset-1">
				<div class="pull-right dariah-flower-white-45">~Collection Registry</div>
			</div>
			<div class="col-sm-6 col-lg-7 col-sm-offset-1">
				<h1>~Agents</h1>
			</div>
		</div>
	</div>
</div>
<div class="container<c:if test="${fluidLayout==true}">-fluid</c:if>">
	<div class="row">
		<div id="main-content-wrapper" class="col-sm-10 col-sm-offset-1">
			<ul class="breadcrumb">
				<li><a href='<s:url value="/" />' target="_self">~Collection Registry</a></li>
				<li class="active">~Agents</li>
			</ul>
			<div id="main-content">
				<div class="row">
					<div class="col-md-12">
						<h2>~Agents</h2>
						<ul>
							<c:forEach items="${agents}" var="a">
								<li><a href='<s:url value="${a.entityId}" />'>${a.entityId}</a></li>
							</c:forEach>
						</ul>
						<!-- 
						<h2 class="pull-left">~Collections</h2>
						<div class="pull-right">
							<a href="<s:url value='new' />" class="btn btn-primary btn-sm pull-left">
								<span class="glyphicon glyphicon-plus"></span> ~Register collection
							</a>
							<div class="data-table-filter pull-left">
								<input type="text" class="form-control input-sm" placeholder='~Filter'>
							</div>
							<div class="data-table-count pull-left">
								<select class="form-control input-sm">
								  <option>10</option>
								  <option>25</option>
								  <option>50</option>
								  <option>100</option>
								  <option>~All</option>
								</select>
							</div>					
						</div>
						<div class="clearfix">
							<table id="schema-table" class="table table-striped table-bordered table-condensed">
								<thead>
									<tr>
										<th></th> 
										<th>~Label</th>
										<th></th>
									</tr>
								</thead>
								<tbody>
								<tr>
									<td colspan="3" align="center">~Nothing fetched yet</td>
								</tr>
								</tbody>
							</table>
						</div>
					</div> -->
				</div>
			</div>
		</div>
	</div>
</div>