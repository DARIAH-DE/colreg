var collectionTable;
var draftTable;
$(document).ready(function() {
	if ($("#draft-table-container").length>0) {
		draftTable = new CollectionTable(__util.composeUrl("drafts/list"), "#draft-table-container", "#draft-table");
	}
	if ($("#collection-table-container").length>0) {
		collectionTable = new CollectionTable(__util.composeUrl("collections/list"), "#collection-table-container", "#collection-table");
	}
});

var CollectionTable = function(url, containerSelector, selector) {
	BaseTable.apply(this, [url, containerSelector]);
	
	var _this = this;
	this.url = url;
	this.selector = selector;
			
	this.prepareTranslations(["~eu.dariah.de.colreg.common.labels.deleted",
	                          "~eu.dariah.de.colreg.common.labels.draft",
	                          "~eu.dariah.de.colreg.common.labels.published",
	                          "~eu.dariah.de.colreg.common.labels.valid",
	                          "~eu.dariah.de.colreg.view.collection.labels.no_image"]);
	
	this.initializeCollectionTypes(function(types) {
		_this.collectionTypes = types;
		_this.createTable();
	});
};

CollectionTable.prototype = new BaseTable();
CollectionTable.prototype.constructor = BaseTable;

CollectionTable.prototype.initializeCollectionTypes = function(callback) {
	$.ajax({
        url: __util.composeUrl("vocabularies/collectionTypes/items"),
        type: "GET",
        encoding: "UTF-8",
        dataType: "json",
        success: function(data) {
        	if (callback!==undefined) {
        		var types = [];
        		if (data!==null && data!==undefined) {
        			for (var i=0; i<data.length; i++) {
        				types[data[i].id] = data[i].displayLabel;
        			}
        		}
        		callback(types);
        	}
        }
    });
};

CollectionTable.prototype.createTable = function() {
	var _this = this;
	this._base.table = $(_this.selector).DataTable($.extend(true, {
		"responsive": true,
		"order": [[1, "asc"]],
		"columnDefs": [
	       {
	           "targets": [0],
	           "class" : "td-no-wrap",
	           "data": function (row, type, val, meta) { return _this.renderBadgeColumn(row, type, val, meta); }
	       }, {
	           "targets": [1],
	           "data": function (row, type, val, meta) { return _this.renderImageColumn(row, type, val, meta); },
	           "responsivePriority": 10001,
	           "class" : "td-center"
	       }, {	
	    	   "targets": [2],
	    	   "data": "entity.displayTitle",
	    	   "width" : "50%"
	       }, {	
	    	   "targets": [3],
	    	   "data": function (row, type, val, meta) { return _this.renderTypesColumn(row, type, val, meta); },
	       }, {	
	    	   "targets": [4],
	    	   "data": function (row, type, val, meta) { return _this.renderAccessColumn(row, type, val, meta); },
	    	   "width" : "25%",
	    	  /* "visible" : false*/
	       }, {	
	    	   "targets": [5],
	    	   "data": function (row, type, val, meta) { return _this.renderVersionColumn(row, type, val, meta); },
	    	   //"data": "entity.lastChanged",
	    	   /*"class" : "td-no-wrap",*/
	    	  /* "visible" : false*/
	       "width" : "25%",
	       }	       
	   ]
	}, this.baseSettings));
	
	$(_this.selector).on('click', 'tr', function () {
        var entityId = _this._base.table.row(this).data().entity.id;
        location.href = __util.composeUrl("collections/" + entityId);
    } );
};

CollectionTable.prototype.renderAccessColumn = function(row, type, val, meta) {
	var accessString = "";
	if (row.entity.accessTypes==null || row.entity.accessTypes==undefined) {
		return accessString;
	}
	
	// Collect access types and count same types to later show >1 in parentheses 
	var access = [];
	for (var i=0; i<row.entity.accessTypes.length; i++) {
		var match = false;		
		for (var j=0; j<access.length; j++) {
			if (access[j].label==row.entity.accessTypes[i]) {
				access[j].count = access[j].count + 1;
				match = true;
				break;
			}
		}
		if (!match) {
			access.push({ label: row.entity.accessTypes[i], count: 1 });
		}
	} 
	
	// Convert access types to string
	for (var i=0; i<access.length; i++) {
		accessString += "<span class=\"label label-primary\">" + access[i].label + (access[i].count>1 ? " (" + access[i].count + ")" : "") + "</span> ";
		/*accessString += access[i].label + (access[i].count>1 ? " (" + access[i].count + ")" : "");
		if (i<access.length-1) {
			accessString += ", ";
		}*/
	}
	return accessString;
};

CollectionTable.prototype.renderVersionColumn = function(row, type, val, meta) {
	if (type=="display") {
		return row.entity.displayTimestamp;
	} else {
		return row.entity.timestamp;
	}
};

CollectionTable.prototype.renderTypesColumn = function(row, type, val, meta) {
	var types = "";
	if (row.entity.collectionTypeIdentifiers==null || row.entity.collectionTypeIdentifiers==undefined) {
		return types;
	}
	
	for (var i=0; i<row.entity.collectionTypeIdentifiers.length; i++) {
		types += "<span class=\"label label-primary\">" + this.collectionTypes[row.entity.collectionTypeIdentifiers[i]] + "</span> ";
		/*types += this.collectionTypes[row.entity.collectionTypeIdentifiers[i]];
		if (i<row.entity.collectionTypeIdentifiers.length-1) {
			types += ", ";
		}*/
	} 
	return types;
};

CollectionTable.prototype.renderImageColumn = function(row, type, val, meta) {
	if (row.entity.primaryImage!==undefined && row.entity.primaryImage!==null) {
		return "<img class='collection-list-thumb' src=\"" + row.entity.primaryImage.thumbnailUrl + "\" />";
	} else {
		return "<div class=\"collection-list-thumb-nopreview\" style=\"background: url(" + __util.composeUrl("resources/img/dariah-flower.png") + ");\">" +
						"<h4>" + __translator.translate("~eu.dariah.de.colreg.view.collection.labels.no_image") + "</h4>" +
			   "</div>"
	}
};

CollectionTable.prototype.renderBadgeColumn = function(row, type, val, meta) {	
	var result = "";
	if (type=="display") {
		if (row.entity.deleted) {
			result += '<span class="label label-danger">' + __translator.translate("~eu.dariah.de.colreg.common.labels.deleted") + '</span> ';
		} else if (row.entity.published) {
			result += '<span class="label label-info">' + __translator.translate("~eu.dariah.de.colreg.common.labels.published") + '</span> ';
		} else {
			result += '<span class="label label-warning">' + __translator.translate("~eu.dariah.de.colreg.common.labels.draft") + '</span> ';
		} 
	} else {
		if (row.entity.deleted) {
			result += __translator.translate("~eu.dariah.de.colreg.common.labels.deleted");
		} else if (row.entity.published) {
			result += __translator.translate("~eu.dariah.de.colreg.common.labels.published");
		} else {
			result += __translator.translate("~eu.dariah.de.colreg.common.labels.draft");
		} 
	}
	return result;
};