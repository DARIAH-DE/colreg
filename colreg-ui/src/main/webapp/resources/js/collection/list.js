var collectionTable;
var draftTable;
$(document).ready(function() {
	draftTable = new CollectionTable(__util.composeUrl("drafts/list"), "#draft-table-container", "#draft-table");
	collectionTable = new CollectionTable(__util.composeUrl("collections/list"), "#collection-table-container", "#collection-table");
});

var CollectionTable = function(url, containerSelector, selector) {
	BaseTable.apply(this, [url, containerSelector]);
	
	this.url = url;
	this.selector = selector;
	
	this.prepareTranslations(["~eu.dariah.de.colreg.common.labels.deleted",
	                          "~eu.dariah.de.colreg.common.labels.draft",
	                          "~eu.dariah.de.colreg.common.labels.published",
	                          "~eu.dariah.de.colreg.common.labels.valid"]);
	this.createTable();
	
};

CollectionTable.prototype = new BaseTable();
CollectionTable.prototype.constructor = BaseTable;

CollectionTable.prototype.createTable = function() {
	var _this = this;
	this._base.table = $(_this.selector).DataTable($.extend(true, {
		"order": [[1, "asc"]],
		"columnDefs": [
	       {
	           "targets": [0],
	           "class" : "td-no-wrap",
	           "data": function (row, type, val, meta) { return _this.renderBadgeColumn(row, type, val, meta); }
	       }, {
	           "targets": [1],
	           "data": function (row, type, val, meta) { return _this.renderImageColumn(row, type, val, meta); },
	           "class" : "td-center"
	       }, {	
	    	   "targets": [2],
	    	   "data": "entity.title",
	    	   "width" : "80%"
	       }, {	
	    	   "targets": [3],
	    	   "data": "entity.type"
	       }, {	
	    	   "targets": [4],
	    	   "data": function (row, type, val, meta) { return _this.renderAccessColumn(row, type, val, meta); },
	    	   "width" : "20%",
	    	  /* "visible" : false*/
	       }, {	
	    	   "targets": [5],
	    	   "data": function (row, type, val, meta) { return _this.renderVersionColumn(row, type, val, meta); }
	    	   //"data": "entity.lastChanged",
	    	   /*"class" : "td-no-wrap",*/
	    	  /* "visible" : false*/
	       }	       
	   ]
	}, this.baseSettings));
	
	$(_this.selector).on('click', 'tr', function () {
        var entityId = _this._base.table.row(this).data().entity.entityId;
        location.href = __util.composeUrl("collections/" + entityId);
    } );
};

CollectionTable.prototype.renderAccessColumn = function(row, type, val, meta) {
	return row.entity.access===undefined ? "" : row.entity.access;
};

CollectionTable.prototype.renderVersionColumn = function(row, type, val, meta) {
	if (type=="display") {
		return row.entity.lastChanged;
	} else {
		return row.entity.versionTimestamp;
	}
};

CollectionTable.prototype.renderImageColumn = function(row, type, val, meta) {
	if (row.entity.imageUrl!==undefined && row.entity.imageUrl!==null) {
		return "<img class='collection-list-thumb' src=\"" + row.entity.imageUrl + "\" />";
	} else {
		return "<img src=\"" + __util.composeUrl("resources/img/page_icon_bw.png") + "\" />";
	}
};

CollectionTable.prototype.renderBadgeColumn = function(row, type, val, meta) {	
	var result = "";
	if (type=="display") {
		if (row.entity.state==="deleted") {
			result += '<span class="label label-danger">' + __translator.translate("~eu.dariah.de.colreg.common.labels.deleted") + '</span> ';
		} else if (row.entity.state==="valid") {
			result += '<span class="label label-info">' + __translator.translate("~eu.dariah.de.colreg.common.labels.valid") + '</span> ';
		} else if (row.entity.state==="published") {
			result += '<span class="label label-info">' + __translator.translate("~eu.dariah.de.colreg.common.labels.published") + '</span> ';
		} else {
			result += '<span class="label label-warning">' + __translator.translate("~eu.dariah.de.colreg.common.labels.draft") + '</span> ';
		} 
	} else {
		if (row.entity.state==="deleted") {
			result += __translator.translate("~eu.dariah.de.colreg.common.labels.deleted");
		} else if (row.entity.state==="valid") {
			result += __translator.translate("~eu.dariah.de.colreg.common.labels.valid");
		} else if (row.entity.state==="published") {
			result += __translator.translate("~eu.dariah.de.colreg.common.labels.published");
		} else {
			result += __translator.translate("~eu.dariah.de.colreg.common.labels.draft");
		} 
	}
	return result;
};