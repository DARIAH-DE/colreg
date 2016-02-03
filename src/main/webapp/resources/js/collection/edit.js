var editor;
$(document).ready(function() {
	editor = new CollectionEditor();
	
	$("form").submit(function(event) { editor.submit(event); });
});



var CollectionEditor = function() {
	var _this = this;
		
	this.descriptionTable = new CollectionEditorTable({
		tableSelector: "#tbl-collection-description-sets",
		newRowUrl: __util.getBaseUrl() + "collections/includes/editDescription",
		newRowCallback: function(row) {
			_this.registerLanguageTypeahead($(row).find(".language-typeahead"));
		}
	});
	
	this.itemLanguageTable = new CollectionEditorTable({
		tableSelector: "#tbl-collection-item-languages",
		newRowUrl: __util.getBaseUrl() + "collections/includes/editItemLanguage",
		newRowCallback: function(row) {
			_this.registerLanguageTypeahead($(row).find(".language-typeahead"));
		}
	});
	
	this.agentRelationTable = new CollectionEditorTable({
		tableSelector: "#tbl-collection-agents",
		newRowUrl: __util.getBaseUrl() + "collections/includes/editAgent",
		newRowCallback: function(row) {
			_this.registerAgentTypeahead($(row).find(".agent-typeahead"));
		}
	});
	
	this.accessMethodTable = new CollectionEditorTable({
		tableSelector: "#tbl-collection-access",
		newRowUrl: __util.getBaseUrl() + "collections/includes/editAccess"
	});
	
	this.accrualMethodTable = new CollectionEditorTable({
		tableSelector: "#tbl-collection-accrual",
		newRowUrl: __util.getBaseUrl() + "collections/includes/editAccrual"
	});
	
	this.languages = new Bloodhound({
		  datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
		  queryTokenizer: Bloodhound.tokenizers.whitespace,
		  remote: {
			  url: __util.getBaseUrl() + 'languages/query/%QUERY',
			  wildcard: '%QUERY'
		  }
	});
	this.agents = new Bloodhound({
		  datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
		  queryTokenizer: Bloodhound.tokenizers.whitespace,
		  remote: { 
			  url: __util.getBaseUrl() + 'agents/query/%QUERY', 
			  wildcard: '%QUERY'
		  }
	});
	
	$('.agent-typeahead').bind('typeahead:select typeahead:autocomplete', function(ev, suggestion) {
		var container = $(this).closest(".form-group"); 
		
		container.find("input[type='hidden']").val(suggestion.entityId);
		container.find(".agent-name-display-helper").val(suggestion.name + " " + suggestion.foreName).trigger('change');
		
		container.find(".agent-display p").html(
				"<a href='" + suggestion.entityId + "'>" +
						"<button type=\"button\" class=\"btn btn-xs btn-link pull-right\">" +
							"<span class=\"glyphicon glyphicon-link\" aria-hidden=\"true\"></span>" +
						"</button>" + _this.renderAgentSuggestion(suggestion) + "</a>");
		container.find(".agent-display").removeClass("hide");
		container.find(".agent-display-null").addClass("hide");
	});
	
	$(".agent-reset").on("click", function() {
		var container = $(this).closest(".form-group");
		
		container.find("input[type='hidden']").val("");
		container.find(".agent-name-display-helper").val("").trigger('change');
		
		container.find(".agent-display p").text("");
		container.find(".agent-display").addClass("hide");
		container.find(".agent-display-null").removeClass("hide");
	});

	$(".select-relation-type").on("change", function() {
		var strSelected = "";
		
		$(this).find(":selected").each(function(i, selected) {
			strSelected += $(selected).text() + " ";
		});
		
		$(this).closest(".form-group").find(".agent-type-display-helper").val(strSelected).trigger('change');
	});
	
	this.registerLanguageTypeahead(".language-typeahead");
	this.registerAgentTypeahead(".agent-typeahead");
};

CollectionEditor.prototype.registerAgentTypeahead = function(elements) {
	var _this = this;
	$(elements).typeahead(null, {
		  name: 'agents',
		  hint: false,
		  display: 'name',
		  source: _this.agents,
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
};

CollectionEditor.prototype.renderAgentSuggestion = function(agent) {
	return  "<strong>" + agent.name + " " + agent.foreName + "</strong><br />" +
			"<small><em>ID:" + agent.entityId + "</em></small>" +
			(agent.address!=null && agent.address!="" ? "<br />" + agent.address : "");
};

CollectionEditor.prototype.registerLanguageTypeahead = function(elements) {
	var _this = this;
	$(elements).typeahead(null, {
		  name: 'language',
		  hint: false,
		  display: 'code',
		  source: _this.languages,
		  limit: 12,
		  templates: {
			    empty: [
			      '<div class="tt-empty-message">',
			        '~No match found',
			      '</div>'
			    ].join('\n'),
			    suggestion: function(data) {
			        return '<p><strong>' + data.code + '</strong> – ' + data.name + '</p>';
			    }
			  }
	});
	
	$(elements).bind('typeahead:select typeahead:autocomplete', function(ev, suggestion) {
		// Suggestion accepted -> input must be ok
		$(this).closest(".form-group").removeClass("has-error");
	});
	
	$(elements).bind('change', function() {
		// Whatever input -> need to validate
		_this.validateLanguage(this);
	});
};

CollectionEditor.prototype.validateLanguage = function(element) {
	var _this = this;
	$.ajax({
        url: __util.getBaseUrl() + 'languages/' + $(element).val(),
        type: "GET",
        dataType: "json",
        success: function(data) {
        	$(element).closest(".form-group").removeClass("has-error");
        },
        error: function(textStatus) { 
        	$(element).closest(".form-group").addClass("has-error");
        }
	});
};

CollectionEditor.prototype.sort = function() {
	this.descriptionTable.sort();
	this.itemLanguageTable.sort();
	this.agentRelationTable.sort();
};

CollectionEditor.prototype.submit = function(event) {
	this.sort();
	
	//event.preventDefault();	// Assume error for now
	//return false; 
};