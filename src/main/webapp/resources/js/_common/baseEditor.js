function BaseEditor() {
	this.entityId = $("#entityId").val();
	this.vocabularySources = new Array();
	this.tables = new Array();
	this.lists = new Array();
};

BaseEditor.prototype.sort = function() {
	for (var tbl in this.tables) {
		this.tables[tbl].sort();
	}
	for (var lst in this.lists) {
		this.lists[lst].sort();
	}
};

BaseEditor.prototype.submit = function(event) {
	this.sort();
};

BaseEditor.prototype.addVocabularySource = function(name, urlSuffix, params) {
	this.vocabularySources[name] = new Bloodhound({
		  datumTokenizer: Bloodhound.tokenizers.whitespace,
		  queryTokenizer: Bloodhound.tokenizers.whitespace,
		  remote: {
			  url: __util.getBaseUrl() + urlSuffix + "%QUERY" + (params!==undefined ? "?" + params : ""),
			  wildcard: '%QUERY'
		  }
	});
};

BaseEditor.prototype.registerTypeahead = function(element, datasource, displayAttr, limit, 
		suggestionCallback, selectionCallback, changeCallback) {
	
	var _this = this;
	element.typeahead(null, {
		name: datasource,
		hint: false,
		display: displayAttr,
		source: _this.vocabularySources[datasource],
		limit: limit,
		templates: {
			empty: ['<div class="tt-empty-message">',
			        	'~No match found',
			        '</div>'].join('\n'),
			suggestion: function(data) { return suggestionCallback(data); }
		}
	});
	
	// Executed when a suggestion has been accepted by the user
	if (selectionCallback!==undefined && selectionCallback!==null && typeof selectionCallback==='function') {
		element.bind('typeahead:select typeahead:autocomplete', function(ev, suggestion) {
			selectionCallback(this, suggestion);
		});
	}
	
	// Executed on custom input -> typically needs some validation
	if (changeCallback!==undefined && changeCallback!==null && typeof changeCallback==='function') {
		element.bind('change', function() {
			changeCallback(this, $(this).val());
		});
	}
};

BaseEditor.prototype.validateInput = function(element, urlPrefix, value) {
	var _this = this;
	$.ajax({
        url: __util.getBaseUrl() + urlPrefix + value,
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