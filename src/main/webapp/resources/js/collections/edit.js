var editor;
$(document).ready(function() {
	editor = new CollectionEditor();
	
	$("form").submit(function(event) { editor.submit(event); });
	
	var languages = new Bloodhound({
		  datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
		  queryTokenizer: Bloodhound.tokenizers.whitespace,
		  //prefetch: '../data/films/post_1960.json',
		  remote: {
		    url: __util.getBaseUrl() + 'languages/query/%QUERY',
		    wildcard: '%QUERY'
		  }
	});

	$('.typeahead').typeahead(null, {
	  name: 'language',
	  hint: false,
	  display: 'code',
	  source: languages,
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
});

var CollectionEditor = function() {
	this.descriptionTable = new CollectionEditorTable({
		tableSelector: "#tbl-collection-description-sets",
		newRowUrl: __util.getBaseUrl() + "collections/includes/editDescription"
	});
};





CollectionEditor.prototype.submit = function(event) {
	this.sort();
	
	//event.preventDefault();	// Assume error for now
	//return false; 
};