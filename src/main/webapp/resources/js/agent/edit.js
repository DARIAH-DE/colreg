var editor;
$(document).ready(function() {
	editor = new AgentEditor();
	
	$(".form-btn-submit").on("click", function() {
		$("form").attr("action", $("#js-form-action").val());
		$("form").submit();
	});
	
	$("form").submit(function(event) { editor.submit(event); });
});


var AgentEditor = function() {
	var _this = this;
	this.addVocabularySource("parentAgents", "agents/query/", "excl=" + this.entityId);

	this.registerParentAgentTypeahead($('#parentAgentIdSelector'));
	
	this.lists["identifierList"] = new CollectionEditorList({
		listSelector: "#lst-agent-provided-identifiers",
		newRowUrl: __util.getBaseUrl() + "agents/includes/editIdentifier",
	});
	
	/**
	 * Side navigation interaction -> move to BaseEditor once workable
	 */
	$(".nav-form-controls a").on('click', function(e) {
		$($(this).attr("href")).focus();
		e.stopPropagation(); 
		return false;
	});
	$("input").focus(function() {		
		$(".nav-form-controls li").removeClass("active");
		$(".nav-form-controls a[href='#" + $(this).attr("id") + "']").parent().addClass("active");
	});
	$("select").focus(function() {		
		$(".nav-form-controls li").removeClass("active");
		$(".nav-form-controls a[href='#" + $(this).attr("id") + "']").parent().addClass("active");
	});
};

AgentEditor.prototype = new BaseEditor();

AgentEditor.prototype.registerParentAgentTypeahead = function(element) {
	var _this = this;
	this.registerTypeahead(element, "parentAgents", "none", 8, 
			function(data) { return "<p>" + _this.renderAgentSuggestion(data) + "</p>"; },
			function(t, suggestion) {
				_this.handleParentAgentSelection(true, suggestion.entityId,
						"<a href='" + suggestion.entityId + "'>" +
						"<button type=\"button\" class=\"btn btn-xs btn-link pull-right\">" +
							"<span class=\"glyphicon glyphicon-link\" aria-hidden=\"true\"></span>" +
						"</button>" + _this.renderAgentSuggestion(suggestion) + "</a>");
			}
	);
	element.closest(".form-group").find(".collection-reset").on("click", function() { 
		_this.handleParentAgentSelection(false, "", "<span></span>"); 
	});
};

AgentEditor.prototype.handleParentAgentSelection = function(select, entityId, html) {
	$("#parentAgentId").val(entityId);
	$("#parentAgent-display p").html(html);
	
	if (select) {
		$("#parentAgent-display").removeClass("hide");
		$("#parentAgent-display-null").addClass("hide");
	} else {
		$("#parentAgent-display").addClass("hide");
		$("#parentAgent-display-null").removeClass("hide");
	}
};
	
AgentEditor.prototype.renderAgentSuggestion = function(agent) {
	return  "<strong>" + agent.name + " " + agent.foreName + "</strong><br />" +
			"<small><em>ID:" + agent.entityId + "</em></small>" +
			(agent.address!=null && agent.address!="" ? "<br />" + agent.address : "");
};

AgentEditor.prototype.handleAgentTypeChange = function(select) {
	var isNatural = $("#agentTypeId option[value='" + select.value + "']").attr("data-natural")=="true"; 
	
	if (isNatural) {
		$(".agent-nonnatural-only").css("display", "none");
		$(".agent-natural-only").css("display", "");
	} else {
		$(".agent-nonnatural-only").css("display", "");
		$(".agent-natural-only").css("display", "none");
	}
};