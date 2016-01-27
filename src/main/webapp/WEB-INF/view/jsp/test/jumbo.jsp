<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<tiles:importAttribute name="fluidLayout" />

<div class="jumbotron">
	 <div class="container<c:if test="${fluidLayout==true}">-fluid</c:if>">
		<div class="row">
			<div class="xs-hidden sm-visible lg-hidden col-sm-3 col-sm-offset-1">
				<div class="pull-right dariah-flower-white-45">some&nbsp;tool</div>
			</div>
			<div class="xs-hidden sm-hidden lg-visible col-lg-2 col-lg-offset-1">
				<div class="pull-right dariah-flower-white-83">some&nbsp;tool</div>
			</div>
			<div class="col-sm-6 col-lg-7 col-sm-offset-1">
				<h1>Blu</h1>
				<p>bla</p>
			</div>
		</div>
	</div>
</div>
<div class="container<c:if test="${fluidLayout==true}">-fluid</c:if>">
	<div class="row">
		<!-- Notifications -->
		<div id="notifications-area"></div>
		<div id="main-content-wrapper" class="col-sm-10 col-sm-offset-1">
			<ul class="breadcrumb">
				<li>Lorem ipsum dolor</li>
				<li class="active">sit amet</li>
			</ul>
			<div id="main-content">
				<h1>sit amet</h1>
				<p>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.</p>
			</div>
		</div>
	</div>
</div>