<ul class="breadcrumb">
	<li>Lorem ipsum dolor</li>
	<li class="active">sit amet</li>
</ul>
<div id="main-content">
	<h1>sit amet</h1>
	<p>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.</p>

	<!-- Demo from here: http://jschr.github.io/bootstrap-modal/bs3.html -->
	<button class="btn btn-primary" onclick="$('#stack1').modal();">Launch modal</button>
</div>


<div id="stack1" class="modal fade" tabindex="-1"
	data-focus-on="input:first" style="display: none;">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">×</button>
		<h4 class="modal-title">Stack One</h4>
	</div>
	<div class="modal-body">
		<p>One fine body</p>
		<p>One fine body</p>
		<p>One fine body</p>
		<input class="form-control" data-tabindex="1" type="text"> <input
			class="form-control" data-tabindex="2" type="text">
		<button class="btn btn-default" data-toggle="modal" href="#stack2">Launch
			modal</button>
	</div>
	<div class="modal-footer">
		<button type="button" data-dismiss="modal" class="btn btn-default">Close</button>
		<button type="button" class="btn btn-primary">Ok</button>
	</div>
</div>

<div id="stack2" class="modal fade" tabindex="-1"
	data-focus-on="input:first" style="display: none;">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">×</button>
		<h4 class="modal-title">Stack Two</h4>
	</div>
	<div class="modal-body">
		<p>One fine body</p>
		<p>One fine body</p>
		<input class="form-control" data-tabindex="1" type="text"> <input
			class="form-control" data-tabindex="2" type="text">
		<button class="btn btn-default" data-toggle="modal" href="#stack3">Launch
			modal</button>
	</div>
	<div class="modal-footer">
		<button type="button" data-dismiss="modal" class="btn btn-default">Close</button>
		<button type="button" class="btn btn-primary">Ok</button>
	</div>
</div>

<div id="stack3" class="modal fade" tabindex="-1"
	data-focus-on="input:first" style="display: none;">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">×</button>
		<h4 class="modal-title">Stack Three</h4>
	</div>
	<div class="modal-body">
		<p>One fine body</p>
		<input class="form-control" data-tabindex="1" type="text"> <input
			class="form-control" data-tabindex="2" type="text">
	</div>
	<div class="modal-footer">
		<button type="button" data-dismiss="modal" class="btn btn-default">Close</button>
		<button type="button" class="btn btn-primary">Ok</button>
	</div>
</div>