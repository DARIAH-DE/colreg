var editor;
$(document).ready(function() {
	editor = new AgentEditor();
});

var AgentEditor = function() {
	var _this = this;
	
	this.agentId = $("#entityId").val();
	this.parentAgents = new Bloodhound({
		  datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
		  queryTokenizer: Bloodhound.tokenizers.whitespace,
		  remote: { 
			  url: __util.getBaseUrl() + 'agents/query/%QUERY?excl=' + _this.agentId, 
			  wildcard: '%QUERY'
		  }
	});
	
	$('#parentAgentIdSelector').typeahead(null, {
		  name: 'parentAgents',
		  hint: false,
		  display: 'name',
		  source: _this.parentAgents,
		  limit: 8,
		  templates: {
			    empty: [
			      '<div class="tt-empty-message">',
			        '~No match found',
			      '</div>'
			    ].join('\n'),
			    suggestion: function(data) {
			    	return "<p>" + _this.renderAgentSuggestion(data) + "</p>";
			    }
			  }
		});
		
	$('#parentAgentIdSelector').bind('typeahead:select typeahead:autocomplete', function(ev, suggestion) {
		$("#parentAgentId").val(suggestion.entityId);
		$("#parentAgent-display p").html(_this.renderAgentSuggestion(suggestion));
		$("#parentAgent-display").removeClass("hide");
		$("#parentAgent-display-null").addClass("hide");
	});
	
	$("#parentAgentIdReset").on("click", function() {
		$("#parentAgentId").val("");
		
		$("#parentAgent-display p").text("");
		$("#parentAgent-display").addClass("hide");
		$("#parentAgent-display-null").removeClass("hide");
	});
	
	this.identifierTable = new CollectionEditorTable({
		tableSelector: "#tbl-agent-identifier",
		newRowUrl: __util.getBaseUrl() + "agents/includes/editIdentifier"
	});
};
	
AgentEditor.prototype.renderAgentSuggestion = function(agent) {
	return  "<strong>" + agent.name + " " + agent.foreName + "</strong><br />" +
			"<small><em>ID:" + agent.id + "</em></small>" +
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