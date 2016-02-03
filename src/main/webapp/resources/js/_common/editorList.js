var CollectionEditorList = function(options) {
	this.options = $.extend(true, 
			{
				// Should be overriden
				listSelector: ".collection-editor-list",
				newRowUrl: "?",
				newRowCallback: null,
				
				// Defaults should be ok
				attributeNameHelperSelector: ".attribute-name-helper",
				listItemSelector: ".collection-editor-list-item",
				listButtonSelector: ".collection-editor-list-buttons",
				addButtonSelector: ".btn-collection-editor-add",
				pushUpButtonSelector: ".btn-push-up",
				pushDownButtonSelector: ".btn-push-down",
			}, 
			options);
}

CollectionEditorList.prototype.triggerAddListElement = function(btn) {
	var _this = this;
	$.ajax({
        url: _this.options.newRowUrl,
        type: "GET",
        dataType: "html",
        success: function(data) {
        	var r = $(data);
        	_this.addNewEntry(r, $(btn).closest(_this.options.listSelector));
        	if (_this.options.newRowCallback!==null && _this.options.newRowCallback!==undefined & 
        			typeof _this.options.newRowCallback==='function') {
        		_this.options.newRowCallback(r);
        	}
        },
        error: function(textStatus) { }
	});
};

CollectionEditorList.prototype.addNewEntry = function(item, list) {
	// Insert new rows before button row
	list.find(this.options.listButtonSelector).before(item);
	this.sort(list);
}

CollectionEditorList.prototype.removeEntry = function(btn) {
	var _this = this;
	$(btn).closest(this.options.listItemSelector).remove();
	this.sort($(btn).closest(_this.options.listSelector));
};

CollectionEditorList.prototype.pushEntryUp = function(btn) {
	var _this = this;
	var prevEntry = $(btn).closest(this.options.listItemSelector).prev();
	
	if (prevEntry.length>0) {
		prevEntry.before($(btn).closest(this.options.listItemSelector).detach());
		this.sort($(btn).closest(_this.options.listSelector));
	}
};

CollectionEditorList.prototype.pushEntryDown = function(btn) {
	var _this = this;
	var nextEntry = $(btn).closest(this.options.listItemSelector).next();
	
	if (nextEntry.length>0) {
		nextEntry.after($(btn).closest(this.options.listItemSelector).detach());		
		this.sort($(btn).closest(_this.options.listSelector));
	}
};

CollectionEditorList.prototype.sort = function(list) {
	var _this = this;
	
	if (list===undefined) {
		list = $(_this.options.listSelector);
	}
	
	/* Note when debugging the re-indexing behavior here:
	 * 	When called within a sublist of a table, the sort call on the sublist results 
	 * 	in false indexes.
	 * 
	 * This is deliberately ignored as it does not matter, as long as another sort on
	 * 	the main outer table is called afterwards, which regenerates indices.
	 */	
	var index = 0;
	var lastItem = null;
	list.find(this.options.listItemSelector).each(function() {
		$(this).find(_this.options.attributeNameHelperSelector).each(function() {
			// TODO Replaces the first "{}" only still need to be set if ever needed
			var name = $(this).text().replace("{}", "[" + index + "]");
			var id = $(this).text().replace("{}", index);
			
			var next = $(this).next();
			if (next.hasClass("form-control")) {
				next.prop("id", id).prop("name", name);
			} else {
				next.find(".form-control").not(".tt-hint").first().prop("id", id).prop("name", name);
			}			
		});
		
		$(this).find(_this.options.pushDownButtonSelector).addClass("disabled");
		if (index==0) {
			$(this).find(_this.options.pushUpButtonSelector).addClass("disabled");
		} else {
			$(this).find(_this.options.pushUpButtonSelector).removeClass("disabled");
		}
		if (lastItem!=null) {
			$(lastItem).find(_this.options.pushDownButtonSelector).removeClass("disabled");
		}
		lastItem = $(this);
		
		index++;
	});
}