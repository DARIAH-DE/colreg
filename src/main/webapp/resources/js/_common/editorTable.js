var CollectionEditorTable = function(options) {
	this.options = $.extend(true, 
			{
				// Should be overriden
				tableSelector: ".collection-editor-table",
				newRowUrl: "?",
				newRowCallback: null,
				initCallback: null,
				
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
	
	if (this.options.initCallback!==null && typeof _this.options.initCallback==='function') {
		this.options.initCallback();
	}
}

CollectionEditorTable.prototype.triggerAddTableElement = function() {
	var _this = this;
	$.ajax({
        url: _this.options.newRowUrl,
        type: "GET",
        dataType: "html",
        success: function(data) {
        	var r = $(data);
        	_this.addNewEntry(r);
        	if (_this.options.newRowCallback!==null && typeof _this.options.newRowCallback==='function') {
        		_this.options.newRowCallback(r);
        	}
        },
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
		var expanded = $(btn).closest("tr");
		expanded.addClass("row-open");
		expanded.next().show().addClass("row-open");
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
			
			var next = $(this).next();
			if (next.hasClass("form-control")) {
				next.prop("id", id).prop("name", name);
			} else {
				next.find(".form-control").not(".tt-hint").first().prop("id", id).prop("name", name);
			}
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
	
	listRow.find("." + field).each(function() { 
		$(this).text($(input).val());
	});
};

CollectionEditorTable.prototype.handleSelectChange = function(select, field) {
	var listRow = $(select).closest("tr").prev();
	
	listRow.find("." + field).each(function() { 
		$(this).text($(select).find("option:selected").text());
	});
};

CollectionEditorTable.prototype.hideAllEdit = function() {
	this.table.find(this.options.editRowSelector).hide();
	this.table.find("tr").removeClass("row-open");
};

CollectionEditorTable.prototype.expandLastEdit = function() {
	var expanded = this.table.find(this.options.editRowSelector).last();
	expanded.css("display","").addClass("row-open");
	expanded.prev().addClass("row-open");
}