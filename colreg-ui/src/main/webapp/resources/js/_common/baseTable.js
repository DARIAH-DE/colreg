/**
 * 	BaseTable: superclass for all editors handling dataTables
 * ==========================================================================================
 * 	Notes:
 * 		- All filter options that are indended to trigger a reload of the table
 * 		  need to hold a class 'editor-option'
 * 		- Defaults for bProcessing, sAjaxSource, bAutoWidth and the fnDrawCallback
 * 		  are set here but can be overridden when initializing the actual table
 * 		- Tooltips in the data are identified by the class 'hint-tooltip' and
 * 		  loaded after every refresh of the editor
 */
function BaseTable(url, containerSelector) {
	// Explicit accessor for base properties in the editors 
	this._base = this;
	this.table = null;
	this.error = false;
	this.containerSelector = containerSelector==undefined ? null : containerSelector;
	
	this.baseTranslations = ["~eu.dariah.de.colreg.common.view.notifications.async_general_error",
	                         "~eu.dariah.de.colreg.common.view.notifications.async_timeout",
	                         "~eu.dariah.de.colreg.common.view.notifications.session_expired_reload"];
	
	this.options = {
			refreshInterval: __properties.refreshIntervalMs,
			cyclicRefresh: __properties.refreshViews
	};
	
	var _this = this;
	// Setting some defaults for the datatables as used in the project
	this.baseSettings = {
			"dom":	"<'row'<'col-sm-12 data-tables-table'tr>>" +
					"<'row'<'col-sm-5'i><'col-sm-7'p>>",
			"autoWidth": false,
			"processing": true,
			"ajax": {
				"url" : url!=null && url !=undefined ? url : window.location.pathname + "async/getData",
				"error": function(xhr, textStatus, error) {_this.handleAjaxError(xhr, textStatus, error); }
			},
			"drawCallback": function (oSettings) {
				_this.handleRefresh(oSettings);
		    },
		    "initComplete" : function(settings, json) { _this.handleInitComplete(settings, json); }
		};
	
	// TODO What was that?
	//$(".editor-option").change(function() { _this.refresh(); });
	this.cycleRefresh();
}

BaseTable.prototype.handleInitComplete = function(settings, json) {
	this.assignTableEvents();
};

BaseTable.prototype.assignTableEvents = function() {
	if (this.containerSelector==null) {
		return;
	}
	var _this = this;
    $(this.containerSelector).find('.data-table-filter input').on('keyup click', function () {
    	_this.table.search($(this).val(), false, false, true).draw();
    });
    // After a reload, some filter might still be applicable
    $(this.containerSelector).find(".data-table-filter input").trigger("keyup");
    
    $(this.containerSelector).find('.data-table-count select').on('change', function () {
    	var len = parseInt($(this).val());
    	if (isNaN(len)) {
    		len = -1; // Show all
    	}    	
    	_this.table.page.len(len).draw();
    });
    // After a reload, some selection might still be applicable
    $(this.containerSelector).find(".data-table-count select").trigger("change");
    		
    $(this.containerSelector).find("tbody").on("click", "tr", function () {
        if ($(this).hasClass("selected")) {
            $(this).removeClass("selected");
            _this.handleSelection(null);
        } else {
        	_this._base.table.$("tr.selected").removeClass("selected");
            $(this).addClass("selected");
            _this.handleSelection($(this).prop("id"));
        }
    });
};

/* Just an 'abstract' method that is intended to be overridden */
BaseTable.prototype.handleSelection = function(id) { };

BaseTable.prototype.handleAjaxError = function(xhr, textStatus, error) {
    // Reload because the session has expired
	if (xhr.status===403) {
    	bootbox.alert(__translator.translate("~eu.dariah.de.colreg.common.view.notifications.session_expired_reload"), function(result) {
    		window.location.reload();
    	});
	} else if (textStatus==='timeout') {
        alert(__translator.translate("~eu.dariah.de.colreg.common.view.notifications.async_timeout"));
    } else {
        //alert(__translator.translate("~eu.dariah.de.colreg.common.view.notifications.async_general_error"));
    }
	alert(textStatus);
	this.error = true;
	if (this.table.fnProcessingIndicator!=undefined) {
		this.table.fnProcessingIndicator(false);
	}
};

BaseTable.prototype.cycleRefresh = function() {
	var _this = this;
	if (this.options.cyclicRefresh) {
		setTimeout(function() { _this.refresh(); _this.cycleRefresh(); }, _this.options.refreshInterval);
	};
};

BaseTable.prototype.refresh = function() {
	var _this = this;
	if (!this.error && this.table!=null) {
		var selected = [];
		this.table.$("tr.selected").each(function() {
			selected.push($(this).prop("id"));
		});
				
		this.table.ajax.reload(function() {
			var hasSelected = false;
			if (selected.length>0) {
				for (var i=0; i<selected.length; i++) {
					$("#"+selected[i]).each(function() {
						$(this).addClass("selected");
						// Only executed if the row (id) still exists
						_this.handleSelection(selected[i]);
						hasSelected = true;
					});
				}
			} 
			if (!hasSelected) {
				_this.handleSelection(null);
			}
		}, false);
	}
};

BaseTable.prototype.handleRefresh = function(oSettings) {
	// This is the case for the pre-load callback
	if (oSettings.aoData===null || oSettings.aoData===undefined || 
			(oSettings.aoData instanceof Array && oSettings.aoData.length==0)) {
		return;
	}

	// We arrive here after data has been loaded into the table
	if (this.table!=null) {
		this.table.$(".hint-tooltip").tooltip({
		     'delay': { show: 500, hide: 0 }
		});
	}
};

BaseTable.prototype.prepareTranslations = function(translations) {
	if (translations!=null || (translations instanceof Array && translations.length>0)) {
		__translator.addTranslations(translations);
		__translator.addTranslations(this.baseTranslations);
		__translator.getTranslations();
	}
};