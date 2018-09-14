var agentTable;
$(document).ready(function() {
	agentTable = new AgentTable();
});

var AgentTable = function() {
	this.prepareTranslations(["~eu.dariah.de.colreg.common.labels.deleted",
	                          "~eu.dariah.de.colreg.common.labels.draft",
	                          "~eu.dariah.de.colreg.common.labels.published",
	                          "~eu.dariah.de.colreg.view.agent.labels.no_image",
	                          "~eu.dariah.de.colreg.common.labels.valid"]);
	this.createTable();
};

AgentTable.prototype = new BaseTable(__util.composeUrl("agents/list"), "#agent-table-container");

AgentTable.prototype.createTable = function() {
	var _this = this;
	this._base.table = $('#agent-table').DataTable($.extend(true, {
		"order": [[1, "asc"]],
		"columnDefs": [
	       {
	           "targets": [0],
	           "class" : "td-no-wrap",
	           "data": function (row, type, val, meta) { return agentTable.renderBadgeColumn(row, type, val, meta); }
	       }, {
	           "targets": [1],
	           "data": function (row, type, val, meta) { return _this.renderImageColumn(row, type, val, meta); },
	           "responsivePriority": 10001,
	           "class" : "td-center"
	       }, {	
	    	   "targets": [2],
	    	   "data": "entity.name",
	    	   //"width" : "100%"
	       }, {	
	    	   "targets": [3],
	    	   "data": "entity.type",
	    	   "class" : "td-no-wrap"
	       }, {	
	    	   "targets": [4],
	    	   "data": function (row, type, val, meta) { return agentTable.renderVersionColumn(row, type, val, meta); }
	    	  /* "visible" : false*/
	       }	       
	   ]
	}, this.baseSettings));
	
	$('#agent-table').on('click', 'tr', function () {
        var entityId = _this._base.table.row(this).data().entity.id;
        location.href = __util.composeUrl("agents/" + entityId);
    } );
};

AgentTable.prototype.renderImageColumn = function(row, type, val, meta) {
	if (row.entity.primaryImage!==undefined && row.entity.primaryImage!==null) {
		return "<img class='agent-list-thumb' src=\"" + row.entity.primaryImage.thumbnailUrl + "\" />";
	} else {
		return "<div class=\"agent-list-thumb-nopreview\">" +
						"<h4>" + __translator.translate("~eu.dariah.de.colreg.view.agent.labels.no_image") + "</h4>" +
			   "</div>"
	}
};

AgentTable.prototype.renderVersionColumn = function(row, type, val, meta) {
	if (type=="display") {
		return row.entity.displayTimestamp;
	} else {
		return row.entity.timestamp;
	}
	return "";
};

AgentTable.prototype.renderBadgeColumn = function(row, type, val, meta) {
	var result = "";
	if (type=="display") {
		if (row.entity.deleted) {
			result += '<span class="label label-danger">' + __translator.translate("~eu.dariah.de.colreg.common.labels.deleted") + '</span> ';
		} else {
			result += '<span class="label label-info">' + __translator.translate("~eu.dariah.de.colreg.common.labels.valid") + '</span> ';
		} 
	} else {
		if (row.entity.deleted) {
			result += __translator.translate("~eu.dariah.de.colreg.common.labels.deleted");
		} else {
			result += __translator.translate("~eu.dariah.de.colreg.common.labels.valid");
		} 
	}
	return result;
};