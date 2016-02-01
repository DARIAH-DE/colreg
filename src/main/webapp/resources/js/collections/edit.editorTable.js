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
				pushUpButtonSelector: ".btn-push-up",
				pushDownButtonSelector: ".btn-push-down",
			}, 
			options);
	
	this.table = $(this.options.tableSelector);
	
	var _this = this;
	this.table.find(this.options.addButtonSelector).click(function() { _this.triggerAddTableElement(); return false;});
	this.sort();
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

CollectionEditorTable.prototype.pushEntryUp = function(btn) {
	var prevEntry = $(btn).closest("tr").prevAll(this.options.listRowSelector).first();
	
	if (prevEntry.length>0) {
		var editEntry = $(btn).closest("tr").next().detach();
		var listEntry = $(btn).closest("tr").detach();
		
		prevEntry.before(listEntry);
		prevEntry.before(editEntry);
		
		this.sort();
	}
};

CollectionEditorTable.prototype.pushEntryDown = function(btn) {
	var nextEntry = $(btn).closest("tr").next().nextAll(this.options.editRowSelector).first();
	
	if (nextEntry.length>0) {
		var editEntry = $(btn).closest("tr").next().detach();
		var listEntry = $(btn).closest("tr").detach();
		
		nextEntry.after(editEntry);
		nextEntry.after(listEntry);
		
		this.sort();
	}
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
	
	index=0;
	var lastRow = null;
	this.table.find(this.options.listRowSelector).each(function() {
		$(this).find(_this.options.pushDownButtonSelector).addClass("disabled");
		if (index==0) {
			$(this).find(_this.options.pushUpButtonSelector).addClass("disabled");
		} else {
			$(this).find(_this.options.pushUpButtonSelector).removeClass("disabled");
		}
		if (lastRow!=null) {
			$(lastRow).find(_this.options.pushDownButtonSelector).removeClass("disabled");
		}
		lastRow = $(this);
		index++;
	});
}

CollectionEditorTable.prototype.handleInputChange = function(input, field) {
	var listRow = $(input).closest("tr").prev();
	
	listRow.find("." + field).each(function() { $(this).text($(input).val()) });
	
};

CollectionEditorTable.prototype.hideAllEdit = function() {
	this.table.find(this.options.editRowSelector).hide();
};

CollectionEditorTable.prototype.expandLastEdit = function() {
	this.table.find(this.options.editRowSelector).last().css("display","");
}