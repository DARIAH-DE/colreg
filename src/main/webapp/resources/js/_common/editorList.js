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
	
	this.list = $(this.options.listSelector);
	
	var _this = this;
	this.list.find(this.options.addButtonSelector).click(function() { _this.triggerAddListElement(); return false;});
	this.sort();
}

CollectionEditorList.prototype.triggerAddListElement = function() {
	var _this = this;
	$.ajax({
        url: _this.options.newRowUrl,
        type: "GET",
        dataType: "html",
        success: function(data) {
        	var r = $(data);
        	_this.addNewEntry(r);
        	if (_this.options.newRowCallback!==null && _this.options.newRowCallback!==undefined & 
        			typeof _this.options.newRowCallback==='function') {
        		_this.options.newRowCallback(r);
        	}
        },
        error: function(textStatus) { }
	});
};

CollectionEditorList.prototype.addNewEntry = function(item) {
	// Insert new rows before button row
	this.list.find(this.options.listButtonSelector).before(item);
	this.sort();
}

CollectionEditorList.prototype.removeEntry = function(btn) {
	$(btn).closest(this.options.listItemSelector).remove();
	this.sort();
};

CollectionEditorList.prototype.pushEntryUp = function(btn) {
	var prevEntry = $(btn).closest(this.options.listItemSelector).prev();
	
	if (prevEntry.length>0) {
		prevEntry.before($(btn).closest(this.options.listItemSelector).detach());
		this.sort();
	}
};

CollectionEditorList.prototype.pushEntryDown = function(btn) {
	var nextEntry = $(btn).closest(this.options.listItemSelector).next();
	
	if (nextEntry.length>0) {
		nextEntry.after($(btn).closest(this.options.listItemSelector).detach());		
		this.sort();
	}
};

CollectionEditorList.prototype.sort = function() {
	var _this = this;
	
	var index = 0;
	var lastItem = null;
	this.list.find(this.options.listItemSelector).each(function() {
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