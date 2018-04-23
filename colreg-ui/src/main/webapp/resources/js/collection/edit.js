var editor;
$(document).ready(function() {
	editor = new CollectionEditor();

	$("#btn-delete-collection").on("click", function() {
		editor.deleteEntity("collections/");
	});
	
	$("#btn-publish-collection").on("click", function() {
		$("form").attr("action", $("#js-form-action").val() + "/publish");
		$("form").submit();
	});
		
	$('[data-toggle="tooltip"]').tooltip();
	$.slidebars();
});

var CollectionEditor = function() {
	var _this = this;
	this.collectionId = $("#entityId").text();
	this.imageMaxFileSize = 5242880;
	
	this.prepareTranslations([
		"~eu.dariah.de.colreg.view.collection.labels.add_uom",
		"~eu.dariah.de.colreg.view.collection.labels.no_image",
		"~eu.dariah.de.colreg.view.collection.labels.image_not_an_image",
		"~eu.dariah.de.colreg.view.collection.labels.image_too_large"
	]);
	this.initVocabularySources();
	this.initEditorComponents();
	
	this.registerParentCollectionTypeahead($("#parentCollectionIdSelector"));
	this.registerLanguageTypeahead($(".language-typeahead"));
	this.registerEncodingSchemeTypeahead($(".encoding-scheme-typeahead"));
	this.registerAgentTypeahead($(".agent-typeahead"));
	this.registerAgentRelationTypeSelection($(".select-relation-type"));
	
	this.registerNavFormControlEvents();
	this.registerFormControlSelectionEvents($("form"));
	this.initRightsContainer();
	
	$(".relationCollectionEntityIdSelector").each(function() {
		_this.registerRelatedCollectionTypeahead($(this));
	});
};

CollectionEditor.prototype = new BaseEditor();

CollectionEditor.prototype.initVocabularySources = function() {
	this.addVocabularySource("languages", "languages/query/");
	this.addVocabularySource("agents", "agents/query/");
	this.addVocabularySource("schemes", "schemes/query/");
	this.addVocabularySource("parentCollections", "collections/query/", "excl=" + this.entityId);
	
	this.addVocabularySource("relatableCollections", "collections/query/", "excl=" + this.entityId);
};

CollectionEditor.prototype.initEditorComponents = function() {
	var _this = this;
	
	// Editor tables
	this.tables["descriptionTable"] = new CollectionEditorTable({
		tableSelector: "#tbl-collection-description-sets",
		newRowUrl: __util.composeUrl("collections/includes/editDescription"),
		newRowCallback: function(row) {
			_this.registerLanguageTypeahead($(row).find(".language-typeahead"));
			_this.registerFormControlSelectionEvents($(row));
			$("#chk-toggle-hints").trigger("change");
		}
	});
	this.tables["agentRelationTable"] = new CollectionEditorTable({
		tableSelector: "#tbl-collection-agents",
		newRowUrl: __util.composeUrl("collections/includes/editAgent"),
		newRowCallback: function(row) {
			_this.registerAgentTypeahead($(row).find(".agent-typeahead"));
			_this.registerAgentRelationTypeSelection($(row).find(".select-relation-type"));
			_this.registerFormControlSelectionEvents($(row));
			$("#chk-toggle-hints").trigger("change");
		}
	});
	this.tables["accessMethodTable"] = new CollectionEditorTable({
		tableSelector: "#tbl-collection-access",
		newRowUrl: __util.composeUrl("collections/includes/editAccess"),
		newRowCallback: function(row) {
			_this.registerEncodingSchemeTypeahead($(row).find(".encoding-scheme-typeahead"));
			_this.registerFormControlSelectionEvents($(row));
			$("#chk-toggle-hints").trigger("change");
		},
		initCallback: function(table) {
			table.schemesList = new CollectionEditorList({
				listSelector: ".lst-collection-access-schemes",
				newRowUrl: __util.composeUrl("collections/includes/editEncodingScheme"),
				newRowCallback: function(row) {
					_this.registerEncodingSchemeTypeahead($(row).find(".encoding-scheme-typeahead"));
				},
				addButtonSelector: ".btn-collection-editor-add-scheme"
			});
		}
	});
	this.tables["accrualMethodTable"] = new CollectionEditorTable({
		tableSelector: "#tbl-collection-accrual",
		newRowUrl: __util.composeUrl("collections/includes/editAccrual"),
		newRowCallback: function(row) {
			_this.registerFormControlSelectionEvents($(row));
			$("#chk-toggle-hints").trigger("change");
		}
	});
	this.tables["locations"] = new CollectionEditorTable({
		tableSelector: "#tbl-collection-locations",
		newRowUrl: __util.composeUrl("collections/includes/editLocation"),
		newRowCallback: function(row) {
			_this.registerFormControlSelectionEvents($(row));
			$("#chk-toggle-hints").trigger("change");
		}
	});
	this.tables["relationTable"] = new CollectionEditorTable({
		tableSelector: "#tbl-collection-relations",
		newRowUrl: __util.composeUrl("collections/includes/editRelation"),
		newRowCallback: function(row) {
			_this.registerFormControlSelectionEvents($(row));
			_this.registerRelatedCollectionTypeahead($(row).find(".relationSourceEntityIdSelector"));
			$("#chk-toggle-hints").trigger("change");
		}
	});
	
	
	// Editor lists
	this.lists["itemLanguageList"] = new CollectionEditorList({
		listSelector: "#lst-collection-item-languages",
		newRowUrl: __util.composeUrl("collections/includes/editItemLanguage"),
		newRowCallback: function(row) {
			_this.registerLanguageTypeahead($(row).find(".language-typeahead"));
			_this.registerFormControlSelectionEvents($(row));
		}
	});
	this.lists["identifierList"] = new CollectionEditorList({
		listSelector: "#lst-collection-provided-identifiers",
		newRowUrl: __util.composeUrl("collections/includes/editProvidedIdentifier"),
		newRowCallback: function(row) {
			_this.registerFormControlSelectionEvents($(row));
		}
	});
	this.lists["spatials"] = new CollectionEditorList({
		listSelector: "#lst-collection-spatials",
		newRowUrl: __util.composeUrl("collections/includes/editSpatial"),
		newRowCallback: function(row) {
			_this.registerFormControlSelectionEvents($(row));
		}
	});
	this.lists["temporals"] = new CollectionEditorList({
		listSelector: "#lst-collection-temporals",
		newRowUrl: __util.composeUrl("collections/includes/editTemporal"),
		newRowCallback: function(row) {
			_this.registerFormControlSelectionEvents($(row));
		}
	});
	this.lists["subjects"] = new CollectionEditorList({
		listSelector: "#lst-collection-subjects",
		newRowUrl: __util.composeUrl("collections/includes/editSubject"),
		newRowCallback: function(row) {
			_this.registerFormControlSelectionEvents($(row));
		}
	});
	this.lists["audiences"] = new CollectionEditorList({
		listSelector: "#lst-collection-audiences",
		newRowUrl: __util.composeUrl("collections/includes/editAudience"),
		newRowCallback: function(row) {
			_this.registerFormControlSelectionEvents($(row));
		}
	});
	this.lists["collectionImages"] = new CollectionEditorList({
		listSelector: "#lst-collection-images",
		newRowUrl: __util.composeUrl("collections/includes/editImage"),
		newRowCallback: function(row) {
			_this.registerFormControlSelectionEvents($(row));
		}
	});
	this.lists["collectionTypes"] = new CollectionEditorList({
		listSelector: "#lst-collection-collectionTypes",
		newRowUrl: __util.composeUrl("collections/includes/editCollectionType"),
		newRowCallback: function(row) {
			_this.registerFormControlSelectionEvents($(row));
		}
	});
	this.lists["itemTypes"] = new CollectionEditorList({
		listSelector: "#lst-collection-itemTypes",
		newRowUrl: __util.composeUrl("collections/includes/editItemType"),
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

CollectionEditor.prototype.registerRelatedCollectionTypeahead = function(element) {
	var _this = this;
	
	this.registerTypeahead(element, "relatableCollections", "none", 8, 
			function(data) { return "<p>" + _this.renderCollectionSuggestion(data) + "</p>"; },
			function(t, suggestion) {
				_this.handleRelatedCollectionSelection(element, true, suggestion.entityId, suggestion.localizedDescriptions[0].title,
						"<a href='" + suggestion.entityId + "' target='_blank'>" +
							"<button type=\"button\" class=\"btn btn-xs btn-link pull-right\">" +
							"<span class=\"glyphicon glyphicon-link\" aria-hidden=\"true\"></span>" +
						"</button>" + _this.renderCollectionSuggestion(suggestion) + "</a>");
			}, null
	);
	element.closest(".relation-container").find(".collection-reset").on("click", function() { 
		_this.handleRelatedCollectionSelection(element, false, "", "<span></span>"); 
	});
};

CollectionEditor.prototype.handleRelatedCollectionSelection = function(element, select, entityId, title, html) {
	
	var container = $(element).closest(".relation-container");
	
	$(container).find(".relation-collection-entityId").val(entityId);
	$(container).find(".relation-collection-displayTitle").val(title);
	$(container).find(".relation-collection-displayTitle").trigger('onchange');
	
	$(container).find(".relatedCollection-display p").html(html);
	
	if (select) {
		$(container).find(".relatedCollection-display").removeClass("hide");
		$(container).find(".relatedCollection-display-null").addClass("hide");
	} else {
		$(container).find(".relatedCollection-display").addClass("hide");
		$(container).find(".relatedCollection-display-null").removeClass("hide");
	}
};

CollectionEditor.prototype.handleRelationDirectionRadioChange = function(element) {
	var value;
	var bidirectional;
	
	if ($(element).val()=="right") {
		value = "<i class='fa fa-long-arrow-right' aria-hidden='true'></i>";
		bidirectional = false;
	} else if ($(element).val()=="left") {
		value = "<i class='fa fa-long-arrow-left' aria-hidden='true'></i>";
		bidirectional = false;
	} else {
		value = "<i class='fa fa-arrows-h' aria-hidden='true'></i>";
		bidirectional = true;
	}
	editor.tables['relationTable'].handleInputChange(element, 'relationTable_direction', value, true);
	$(element).closest(".form-group").find(".relation-direction-bidirectional").val(bidirectional);
}

	
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

CollectionEditor.prototype.initRightsContainer = function() {
	$(".rights-container input[type='radio']").change(function () {
		var id = $(this).closest(".rights-container").prop("id");
		var controls = $(this).closest(".rights-container").find(".form-control");
		var selected = $(this).closest(".rights-container").find("#" + $(this).prop("name") + "-" + $(this).val());
		
		$(controls).prop('disabled', true);
		$(controls).prop('name', "");
		$(selected).prop('disabled', false);
		$(selected).prop('name', id);
	});
};

CollectionEditor.prototype.handleParentCollectionSelection = function(select, entityId, html) {
	$("#parentCollectionId").val(entityId);
	$(".parentCollection-display p").html(html);
	
	if (select) {
		$(".parentCollection-display").removeClass("hide");
		$(".parentCollection-display-null").addClass("hide");
	} else {
		$(".parentCollection-display").addClass("hide");
		$(".parentCollection-display-null").removeClass("hide");
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
	formGroup.find(".agent-name-display-helper").val(suggestion!=null ? (suggestion.name + " " + (suggestion.foreName!==undefined ? suggestion.foreName : "")) : "").trigger('change');
	
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
	return  "<strong>" + agent.name + " " + (agent.foreName!==undefined ? agent.foreName : "") + "</strong><br />" +
			"<small><em>ID:" + agent.entityId + "</em></small>";
};

CollectionEditor.prototype.triggerAddUnitOfMeasurement = function() {
	bootbox.prompt(__translator.translate("~eu.dariah.de.colreg.view.collection.labels.add_uom"), function(result){ 
		$.ajax({
	        url: __util.composeUrl("vocabularies/uom/async/add"),
	        data: { value: result },
	        type: "GET",
	        async: false,
	        encoding: "UTF-8",
	        dataType: "json",
	        success: function(data) {
	        	if (!data.success) {
	        		$("#uom-hint .uom-error-text").html("<small>" + data.message.messageBody + "</small>")
	        		$("#uom-hint").show();
	        	} else {
	        		var newoption = $("<option>").prop("value", data.pojo.id).text(data.pojo.name);
	        		$("#uomId").append(newoption);
	        		$("#uomId option[value=" + data.pojo.id + "]").attr("selected", "selected");
	        		$("#uom-hint").hide();
	        	}
	        }
	    });
	});
};

CollectionEditor.prototype.triggerUploadImage = function(e, container) {
	var _this = editor;
	
	// Check for the various File API support.
	if (window.File && window.FileReader && window.FileList && window.Blob) {
		var files = e.target.files; // FileList object

	    // files is a FileList of File objects. List some properties.
	    var output = [];
	    for (var i = 0, f; f = files[i]; i++) {
	    	// Only process image files.
	        if (!f.type.match('image.*')) {
	        	$("#collection-image-hint").html(__translator.translate("~eu.dariah.de.colreg.view.collection.labels.image_not_an_image"));
	        	continue;
	        }
	        if (f.size>_this.imageMaxFileSize) {
	        	$("#collection-image-hint").html(__translator.translate("~eu.dariah.de.colreg.view.collection.labels.image_too_large") + " 5 MB");
	        	continue;
	        }	        
	        var formData = new FormData();
	        formData.append("file", f, f.name);
	        formData.append("collectionId", _this.collectionId);
	    	        
	        $.ajax({
		        url: __util.composeUrl("image/async/upload"),
		        data: formData,
		        type: "POST",
		        beforeSend: function( xhr ) {
		        	xhr.setRequestHeader("X-File-Name", f.name);
		        	xhr.setRequestHeader("X-File-Size", f.size);
		        	xhr.setRequestHeader("X-File-Type", f.type);
		        },
		        cache: false,
		        contentType: false,
		        processData: false,
		        aysnc: false,
		        timeout: 20000,
		        success: function(data) {
		        	if (data.success) {
		        		_this.lists['collectionImages'].triggerAddListElement(container, data.pojo.id);
		        		$("#collection-image-hint").text("");
		        		$("#collection-image-hint").hide();
		        	} else {
		        		$("#collection-image-hint").html(data.objectErrors[0]);
		        		$("#collection-image-hint").show();
		        	}
		        }
		    });
	    }
	}
};