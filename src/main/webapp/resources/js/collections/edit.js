var editor;
$(document).ready(function() {
	editor = new CollectionEditor();
	$("#btn-add-description").click(function() { editor.triggerAddTableElement(); return false;});
	
	$("form").submit(function(event) { editor.submit(event); });
});

var CollectionEditor = function() {};

CollectionEditor.prototype.triggerAddTableElement = function() {
	$.ajax({
        url: __util.getBaseUrl() + "collections/includes/editDescription",
        type: "GET",
        dataType: "html",
        success: function(data) {
        	
        	// Hide placeholder row
        	$("#tbl-collection-description-sets .collection-editor-table-empty-placeholder").hide();
        	
        	// Hide all existing form-rows
        	$("#tbl-collection-description-sets tr.edit").hide();
        	
        	$("#tbl-collection-description-sets tbody").append(data);
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
};

CollectionEditor.prototype.submit = function(event) {
	
	event.preventDefault();	// Assume error for now
	return false; 
};