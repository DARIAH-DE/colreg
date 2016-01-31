var CollectionEditorTable = function(options) {
	this.options = $.extend(true, 
			{
				// Should be overriden
				tableSelector: ".collection-editor-table",
				newRowUrl: "?",
				
				// Defaults should be ok
				attributeNameHelperSelector: ".attribute-name-helper",
				listRowSelector: "tr.list",
				editRowSelector: "tr.edit",
				buttonRowSelector: "tr.collection-editor-table-buttons",
				addButtonSelector: ".btn-collection-editor-add",
			}, 
			options);
	
	this.table = $(this.options.tableSelector);
	
	var _this = this;
	this.table.find(this.options.addButtonSelector).click(function() { _this.triggerAddTableElement(); return false;});
}

CollectionEditorTable.prototype.triggerAddTableElement = function() {
	var _this = this;
	$.ajax({
        url: _this.options.newRowUrl,
        type: "GET",
        dataType: "html",
        success: function(data) { _this.addNewEntry(data); },
        error: function(textStatus) { }
	});
};

CollectionEditorTable.prototype.addNewEntry = function(rows) {
	this.hideAllEdit();
	
	// Insert new rows before button row
	this.table.find(this.options.buttonRowSelector).before(rows);
	
	this.sort();
	this.expandLastEdit();
}

CollectionEditorTable.prototype.editEntry = function(btn) {
	// Toggle open
	var expand = $(btn).closest("tr").next().css("display")=="none";
	this.hideAllEdit();
	if (expand) {
		$(btn).closest("tr").next().show();
	}
};

CollectionEditorTable.prototype.removeEntry = function(btn) {
	$(btn).closest("tr").next().remove();
	$(btn).closest("tr").remove();
	this.sort();
};

CollectionEditorTable.prototype.sort = function() {
	var _this = this;
	var index = 0;
	this.table.find(this.options.editRowSelector).each(function() {
		$(this).find(_this.options.attributeNameHelperSelector).each(function() {
			var name = $(this).text().replace("{}", "[" + index + "]");
			var id = $(this).text().replace("{}", index);
			
			$(this).next().prop("id", id).prop("name", name);
		});
		
		index++;
	});
}

CollectionEditorTable.prototype.hideAllEdit = function() {
	this.table.find(this.options.editRowSelector).hide();
};

CollectionEditorTable.prototype.expandLastEdit = function() {
	this.table.find(this.options.editRowSelector).last().css("display","");
}