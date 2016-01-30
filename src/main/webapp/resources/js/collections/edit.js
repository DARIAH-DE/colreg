var editor;
$(document).ready(function() {
	editor = new CollectionEditor();
	$("#btn-add-description").click(function() { editor.triggerAddTableElement(); return false;});
	
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
	  templates: {
		    empty: [
		      '<div class="empty-message">',
		        'unable to find any Best Picture winners that match the current query',
		      '</div>'
		    ].join('\n'),
		    suggestion: function(data) {
		        return '<p><strong>' + data.code + '</strong> – ' + data.name + '</p>';
		    }
		  }
	});
});

var CollectionEditor = function() {};

CollectionEditor.prototype.triggerAddTableElement = function() {
	$.ajax({
        url: __util.getBaseUrl() + "collections/includes/editDescription",
        type: "GET",
        dataType: "html",
        success: function(data) {
        	// Hide all existing form-rows
        	$("#tbl-collection-description-sets tr.edit").hide();
        	
        	$("#tbl-collection-description-sets .collection-editor-table-empty-placeholder").before(data);
        	editor.sort();
        	editor.expandLast();
        },
        error: function(textStatus) { }
	});
};

CollectionEditor.prototype.editEntry = function(btn) {
	var expand = $(btn).closest("tr").next().css("display")=="none";
	
	$(btn).closest("table").find("tr.edit").hide();
	
	if (expand) {
		$(btn).closest("tr").next().show();
	}
};

CollectionEditor.prototype.removeEntry = function(btn) {
	$(btn).closest("tr").next().remove();
	$(btn).closest("tr").remove();
	
	if ($("#tbl-collection-description-sets tbody tr").size()==1) {
		$("#tbl-collection-description-sets .collection-editor-table-empty-placeholder").show();
	}
	this.sort();
};

CollectionEditor.prototype.sort = function() {
	
	var index = 0;
	$("#tbl-collection-description-sets tbody tr.edit").each(function() {
		$(this).find(".attribute-name-helper").each(function() {
			var name = $(this).text().replace("{}", "[" + index + "]");
			var id = $(this).text().replace("{}", index);
			
			$(this).next().prop("id", id).prop("name", name);
		});
		
		index++;
	});
}

CollectionEditor.prototype.expandLast = function() {
	$("#tbl-collection-description-sets tr.edit").last().css("display","");
}

CollectionEditor.prototype.submit = function(event) {
	this.sort();
	
	//event.preventDefault();	// Assume error for now
	//return false; 
};