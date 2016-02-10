var editor;
$(document).ready(function() {
	editor = new CollectionEditor();
	
	$(".form-btn-submit").on("click", function() {
		$("form").attr("action", $("#js-form-action").val());
		$("form").submit();
	});
	
	$("#btn-delete-collection").on("click", function() {
		editor.deleteEntity("collections/");
	});
	
	$("#btn-publish-collection").on("click", function() {
		$("form").attr("action", $("#js-form-action").val() + "/publish");
		$("form").submit();
	});
	
	$("form").submit(function(event) { editor.submit(event); });
	
	$.slidebars();
});

var CollectionEditor = function() {
	this.prepareTranslations([]);
	this.initVocabularySources();
	this.initEditorComponents();
	
	this.registerParentCollectionTypeahead($("#parentCollectionIdSelector"));
	this.registerLanguageTypeahead($(".language-typeahead"));
	this.registerEncodingSchemeTypeahead($(".encoding-scheme-typeahead"));
	this.registerAgentTypeahead($(".agent-typeahead"));
	this.registerAgentRelationTypeSelection($(".select-relation-type"));
	
	this.registerNavFormControlEvents();
	this.registerFormControlSelectionEvents($("form"));
};

CollectionEditor.prototype = new BaseEditor();

CollectionEditor.prototype.initVocabularySources = function() {
	this.addVocabularySource("languages", "languages/query/");
	this.addVocabularySource("agents", "agents/query/");
	this.addVocabularySource("schemes", "schemes/query/");
	this.addVocabularySource("parentCollections", "collections/query/", "excl=" + this.entityId);
};

CollectionEditor.prototype.initEditorComponents = function() {
	var _this = this;
	
	// Editor tables
	this.tables["descriptionTable"] = new CollectionEditorTable({
		tableSelector: "#tbl-collection-description-sets",
		newRowUrl: __util.getBaseUrl() + "collections/includes/editDescription",
		newRowCallback: function(row) {
			_this.registerLanguageTypeahead($(row).find(".language-typeahead"));
			_this.registerFormControlSelectionEvents($(row));
		}
	});
	this.tables["agentRelationTable"] = new CollectionEditorTable({
		tableSelector: "#tbl-collection-agents",
		newRowUrl: __util.getBaseUrl() + "collections/includes/editAgent",
		newRowCallback: function(row) {
			_this.registerAgentTypeahead($(row).find(".agent-typeahead"));
			_this.registerAgentRelationTypeSelection($(row).find(".select-relation-type"));
			_this.registerFormControlSelectionEvents($(row));
		}
	});
	this.tables["accessMethodTable"] = new CollectionEditorTable({
		tableSelector: "#tbl-collection-access",
		newRowUrl: __util.getBaseUrl() + "collections/includes/editAccess",
		newRowCallback: function(row) {
			_this.registerEncodingSchemeTypeahead($(row).find(".encoding-scheme-typeahead"));
			_this.registerFormControlSelectionEvents($(row));
		},
		initCallback: function(table) {
			table.schemesList = new CollectionEditorList({
				listSelector: ".lst-collection-access-schemes",
				newRowUrl: __util.getBaseUrl() + "collections/includes/editEncodingScheme",
				newRowCallback: function(row) {
					_this.registerEncodingSchemeTypeahead($(row).find(".encoding-scheme-typeahead"));
				},
				addButtonSelector: ".btn-collection-editor-add-scheme"
			});
		}
	});
	this.tables["accrualMethodTable"] = new CollectionEditorTable({
		tableSelector: "#tbl-collection-accrual",
		newRowUrl: __util.getBaseUrl() + "collections/includes/editAccrual",
		newRowCallback: function(row) {
			_this.registerFormControlSelectionEvents($(row));
		}
	});
	this.tables["locations"] = new CollectionEditorTable({
		tableSelector: "#tbl-collection-locations",
		newRowUrl: __util.getBaseUrl() + "collections/includes/editLocation",
		newRowCallback: function(row) {
			_this.registerFormControlSelectionEvents($(row));
		}
	});
	
	
	// Editor lists
	this.lists["itemLanguageList"] = new CollectionEditorList({
		listSelector: "#lst-collection-item-languages",
		newRowUrl: __util.getBaseUrl() + "collections/includes/editItemLanguage",
		newRowCallback: function(row) {
			_this.registerLanguageTypeahead($(row).find(".language-typeahead"));
			_this.registerFormControlSelectionEvents($(row));
		}
	});
	this.lists["identifierList"] = new CollectionEditorList({
		listSelector: "#lst-collection-provided-identifiers",
		newRowUrl: __util.getBaseUrl() + "collections/includes/editProvidedIdentifier",
		newRowCallback: function(row) {
			_this.registerFormControlSelectionEvents($(row));
		}
	});
	this.lists["spatials"] = new CollectionEditorList({
		listSelector: "#lst-collection-spatials",
		newRowUrl: __util.getBaseUrl() + "collections/includes/editSpatial",
		newRowCallback: function(row) {
			_this.registerFormControlSelectionEvents($(row));
		}
	});
	this.lists["temporals"] = new CollectionEditorList({
		listSelector: "#lst-collection-temporals",
		newRowUrl: __util.getBaseUrl() + "collections/includes/editTemporal",
		newRowCallback: function(row) {
			_this.registerFormControlSelectionEvents($(row));
		}
	});
	this.lists["subjects"] = new CollectionEditorList({
		listSelector: "#lst-collection-subjects",
		newRowUrl: __util.getBaseUrl() + "collections/includes/editSubject",
		newRowCallback: function(row) {
			_this.registerFormControlSelectionEvents($(row));
		}
	});
	this.lists["audiences"] = new CollectionEditorList({
		listSelector: "#lst-collection-audiences",
		newRowUrl: __util.getBaseUrl() + "collections/includes/editAudience",
		newRowCallback: function(row) {
			_this.registerFormControlSelectionEvents($(row));
		}
	});
};

CollectionEditor.prototype.registerLanguageTypeahead = function(element) {
	var _this = this;
	this.registerTypeahead(element, "languages", "code", 8, 
			function(data) { return '<p><strong>' + data.code + '</strong> – ' + data.name + '</p>'; },
			function(t, suggestion) { $(t).closest(".form-group").removeClass("has-error"); },
			function(t, value) { _this.validateInput(t, "languages/", value); }
	);
};

CollectionEditor.prototype.registerAgentTypeahead = function(element) {
	var _this = this;
	this.registerTypeahead(element, "agents", "none", 6, 
			function(data) { return "<p>" + _this.renderAgentSuggestion(data) + "</p>"; },
			function(t, suggestion) { _this.handleAgentSelection(true, t, suggestion); }
	);
	element.closest(".form-group").find(".agent-reset").on("click", function() { 
		_this.handleAgentSelection(false, this, null); 
	});
};

CollectionEditor.prototype.registerEncodingSchemeTypeahead = function(element) {
	var _this = this;
	this.registerTypeahead(element, "schemes", "name", 8, 
			function(data) { return "<p><strong>" + data.name + "</strong><br />" + data.url + "</p>"; },
			function(t, suggestion) { $(t).closest(".form-group").removeClass("has-error"); },
			function(t, value) { _this.validateInput(t, "schemes/", value); }
	);
};

CollectionEditor.prototype.registerParentCollectionTypeahead = function(element) {
	var _this = this;
	this.registerTypeahead(element, "parentCollections", "none", 8, 
			function(data) { return "<p>" + _this.renderCollectionSuggestion(data) + "</p>"; },
			function(t, suggestion) {
				_this.handleParentCollectionSelection(true, suggestion.entityId,
						"<a href='" + suggestion.entityId + "'>" +
							"<button type=\"button\" class=\"btn btn-xs btn-link pull-right\">" +
							"<span class=\"glyphicon glyphicon-link\" aria-hidden=\"true\"></span>" +
						"</button>" + _this.renderCollectionSuggestion(suggestion) + "</a>");
			}, null
	);
	element.closest(".form-group").find(".collection-reset").on("click", function() { 
		_this.handleParentCollectionSelection(false, "", "<span></span>"); 
	});
};

CollectionEditor.prototype.registerAgentRelationTypeSelection = function(element) {
	// Update displayed table list-row content based on AgentRelationType selection
	$(element).on("change", function() {
		var strSelected = "";
		$(this).find(":selected").each(function(i, selected) {
			strSelected += $(selected).text() + " ";
		});
		$(this).closest(".form-group").find(".agent-type-display-helper").val(strSelected).trigger('change');
	});
}

CollectionEditor.prototype.handleParentCollectionSelection = function(select, entityId, html) {
	$("#parentCollectionId").val(entityId);
	$("#parentCollection-display p").html(html);
	
	if (select) {
		$("#parentCollection-display").removeClass("hide");
		$("#parentCollection-display-null").addClass("hide");
	} else {
		$("#parentCollection-display").addClass("hide");
		$("#parentCollection-display-null").removeClass("hide");
	}
};

CollectionEditor.prototype.handleAccessTypeChange = function(select) {
	var editField = $(select).closest("td");
	if ($(select).find(":selected").text()==="OAI-PMH") {
		editField.find(".oaiset").show();
	} else {
		editField.find(".oaiset").hide();
	}
};

CollectionEditor.prototype.handleAgentSelection = function(select, control, suggestion) {
	var _this = this;
	var formGroup = $(control).closest(".form-group"); 
	formGroup.find("input[type='hidden']").val(suggestion!=null ? suggestion.entityId : "");
	formGroup.find(".agent-name-display-helper").val(suggestion!=null ? (suggestion.name + " " + suggestion.foreName) : "").trigger('change');
	
	if (select) {
		formGroup.find(".agent-display p").html(
				"<a href='" + suggestion.entityId + "'>" +
						"<button type=\"button\" class=\"btn btn-xs btn-link pull-right\">" +
							"<span class=\"glyphicon glyphicon-link\" aria-hidden=\"true\"></span>" +
						"</button>" + _this.renderAgentSuggestion(suggestion) + "</a>");
		formGroup.find(".agent-display").removeClass("hide");
		formGroup.find(".agent-display-null").addClass("hide");
	} else {
		formGroup.find(".agent-display p").text("");
		formGroup.find(".agent-display").addClass("hide");
		formGroup.find(".agent-display-null").removeClass("hide");
	}
};

CollectionEditor.prototype.renderCollectionSuggestion = function(collection) {
	return  "<strong>" + collection.localizedDescriptions[0].title + "</strong><br />" +
			"<small><em>ID:" + collection.entityId + "</em></small>";
};

CollectionEditor.prototype.renderAgentSuggestion = function(agent) {
	return  "<strong>" + agent.name + " " + agent.foreName + "</strong><br />" +
			"<small><em>ID:" + agent.entityId + "</em></small>";
};