var vocabularyTable;
$(document).ready(function() {
	vocabularyTable = new VocabularyTable();
});

var VocabularyTable = function() {
	this.prepareTranslations(["~eu.dariah.de.colreg.common.labels.deleted",
	                          "~eu.dariah.de.colreg.common.labels.draft",
	                          "~eu.dariah.de.colreg.common.labels.published",
	                          "~eu.dariah.de.colreg.common.labels.valid", 
	                          "~eu.dariah.de.colreg.view.collection.labels.delete_vocabulary_item"]);
	this.vocabularyId = $("#vocabulary-id").val();
	this.createTable();
};

VocabularyTable.prototype = new BaseTable(__util.composeUrl("vocabularies/" + $("#vocabulary-id").val() + "/list"), "#vocabulary-table-container");

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
	    	   "width" : "40%"
	       }, {	
	    	   "targets": [3],
	    	   "data": function (row, type, val, meta) { return vocabularyTable.renderActionColumn(row, type, val, meta); },
	    	   "class" : "td-no-wrap",
	    	   "visible" : $("#table-edit-mode").val()==="true" ? true : false
	      }
	   ]
	}, this.baseSettings));
};


VocabularyTable.prototype.renderLocalNameColumn = function(row, type, val, meta) {
	var result = "";
	if (type=="display") {
		result += "<i class='fa fa-" + (row.entity.hasCurrentLocale ? "check-" : "") + "square-o' aria-hidden='true'></i> ";
		if (row.entity.hasCurrentLocale) {
			result += row.entity.localizedLabel;
		} 
	} else {
		return row.entity.hasCurrentLocale + " " + row.entity.localizedLabel;
	}
	return result;
};

VocabularyTable.prototype.renderActionColumn = function(row, type, val, meta) {
	if (type=="display" && $("#table-edit-mode").val()==="true") {
		return "<button onclick=\"vocabularyTable.triggerEditVocabularyItem('" + row.entity.id + "');\" class=\"btn btn-link btn-xs\">" + 
			      "<span class=\"glyphicon glyphicon-pencil\"></span>" + 
			   "</button> " +
			   "<button onclick=\"vocabularyTable.triggerDeleteVocabularyItem('" + row.entity.id + "');\" class=\"btn btn-link btn-xs\">" + 
			      "<span class=\"glyphicon glyphicon-trash\"></span>" + 
			   "</button>";
	} else {
		return "";
	}
}

VocabularyTable.prototype.triggerDeleteVocabularyItem = function(itemId) {
	var _this = this;
	
	$.ajax({
        url: __util.composeUrl("vocabularies/" + _this.vocabularyId + "/" + itemId + "/countCollections"),
        type: "GET",
        dataType: "json",
        success: function(data) {
        	bootbox.confirm(__translator.translate("~eu.dariah.de.colreg.view.collection.labels.delete_vocabulary_item", [data.pojo.collections, data.pojo.drafts]), function(result) {
        		if(result) {
        			$.ajax({
        		        url: __util.composeUrl("vocabularies/" + _this.vocabularyId + "/" + itemId + "/delete"),
        		        type: "GET",
        		        dataType: "json",
        		        success: function(data) {
        		        	 _this.refresh();
        		        },
        		        error: function(textStatus) { 
        		        	__notifications.showTranslatedMessage(NOTIFICATION_TYPES.ERROR, 
        		        			"~eu.dariah.de.colreg.common.view.forms.servererror.head", 
        		        			"~eu.dariah.de.colreg.common.view.forms.servererror.body");
        		        }
        			});
        		}
        	}); 
        },
        error: function(textStatus) { 
        	__notifications.showTranslatedMessage(NOTIFICATION_TYPES.ERROR, 
        			"~eu.dariah.de.colreg.common.view.forms.servererror.head", 
        			"~eu.dariah.de.colreg.common.view.forms.servererror.body");
        }
	});
};

VocabularyTable.prototype.triggerEditVocabularyItem = function(itemId) {
	var _this = this;
	var form_identifier = this.vocabularyId + "-new-item";
	
	modalFormHandler = new ModalFormHandler({
		formFullUrl: __util.composeUrl("vocabularies/" + _this.vocabularyId + "/" + itemId + "/forms/edit"),
		identifier: form_identifier,
		additionalModalClasses: "wide-modal",
		completeCallback: function(container) { 
			_this.refresh(); 
		},
		//setupCallback: function() { } -> refreshList
	});	
	modalFormHandler.show(form_identifier);
};