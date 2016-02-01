var editor;
$(document).ready(function() {
	editor = new AgentEditor();
});

var AgentEditor = function() {};
	

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