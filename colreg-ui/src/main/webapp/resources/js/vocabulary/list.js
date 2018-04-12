var vocabularyTable;
$(document).ready(function() {
	vocabularyTable = new VocabularyTable();
});

var VocabularyTable = function() {
	this.prepareTranslations(["~eu.dariah.de.colreg.common.labels.deleted",
	                          "~eu.dariah.de.colreg.common.labels.draft",
	                          "~eu.dariah.de.colreg.common.labels.published",
	                          "~eu.dariah.de.colreg.common.labels.valid"]);
	this.vocabularyId = $("#vocabulary-id").val();
	this.createTable();
};

VocabularyTable.prototype = new BaseTable(__util.composeUrl("vocabulary/" + $("#vocabulary-id").val() + "/list"), "#vocabulary-table-container");

VocabularyTable.prototype.createTable = function() {
	var _this = this;
	this._base.table = $('#vocabulary-table').DataTable($.extend(true, {
		"order": [[1, "asc"]],
		"columnDefs": [
	       {
	           "targets": [0],
	           "class" : "td-no-wrap",
	       	   "data": "entity.identifier",
	       }, {	
	    	   "targets": [1],
	    	   "data": "entity.defaultName",
	    	   "width" : "50%"
	       }, {	
	    	   "targets": [2],
	    	   "data": function (row, type, val, meta) { return vocabularyTable.renderLocalNameColumn(row, type, val, meta); },
	    	   "width" : "50%"
	       }, {	
	    	   "targets": [3],
	    	   "data": "entity.id",
	    	   "class" : "td-no-wrap"
	       }       
	   ]
	}, this.baseSettings));
	
	$('#agent-table').on('click', 'tr', function () {
        var entityId = _this._base.table.row(this).data().entity.entityId;
        location.href = __util.composeUrl("agents/" + entityId);
    } );
};


VocabularyTable.prototype.renderLocalNameColumn = function(row, type, val, meta) {
	var result = "";
	if (type=="display") {
		return "local";
	} else {
		return "local"; 
	}
	return result;
};

VocabularyTable.prototype.triggerEditVocabularyItem = function(itemId) {
	var _this = this;
	var form_identifier = this.vocabularyId + "-new-item";
	
	modalFormHandler = new ModalFormHandler({
		formUrl: __util.composeUrl(itemId + "/forms/edit"),
		identifier: form_identifier,
		//additionalModalClasses: "wide-modal",
		//completeCallback: function(container) {},
		//setupCallback: function() { } -> refreshList
	});	
	modalFormHandler.show(form_identifier);
	
	/*this.createModalForm(-1, "addCollection", ucId + "/forms/assignCollections", "wide-modal", 
			function(container) {
				$(container).find('#user-collections-add-collections-table').DataTable({
					"order": [[ 1, "asc" ]],
					"columnDefs": [
						{"targets": [1], "width": "100%"}
					]
				});
			}, 
			function() { 
				_this.refreshCollections();
			});*/
	
	
	/*$.extend(true, {
					"ajaxSource" : null,
					"order": [[ 1, "asc" ]],
					"columnDefs": [{"targets": [1], "width": "100%"},{"targets": [0], "sortable": false }],
				})*/
};