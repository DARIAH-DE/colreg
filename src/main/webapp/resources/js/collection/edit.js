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
	
	this.languages = new Bloodhound({
		  datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
		  queryTokenizer: Bloodhound.tokenizers.whitespace,
		  remote: {
			  url: __util.getBaseUrl() + 'languages/query/%QUERY',
			  wildcard: '%QUERY'
		  }
	});

	this.registerLanguageTypeahead(".language-typeahead");
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
};


CollectionEditor.prototype.submit = function(event) {
	this.sort();
	
	//event.preventDefault();	// Assume error for now
	//return false; 
};