function BaseEditor() {
	this.entityId = $("#entityId").text();
	this.vocabularySources = new Array();
	this.tables = new Array();
	this.lists = new Array();
	
	this.baseTranslations = ["~eu.dariah.de.colreg.common.labels.no_match_found",
	                         "~eu.dariah.de.colreg.view.collection.notification.could_not_delete",
	                         "~eu.dariah.de.colreg.view.collection.labels.delete_collection.body",
	                         "~eu.dariah.de.colreg.view.collection.labels.delete_collection.head",
	                         "~eu.dariah.de.colreg.common.link.no",
	                         "~eu.dariah.de.colreg.common.link.yes"];
};

BaseEditor.prototype.sort = function() {
	for (var tbl in this.tables) {
		this.tables[tbl].sort();
	}
	for (var lst in this.lists) {
		this.lists[lst].sort();
	}
};

BaseEditor.prototype.submit = function(event) {
	this.sort();
};

BaseEditor.prototype.addVocabularySource = function(name, urlSuffix, params) {
	this.vocabularySources[name] = new Bloodhound({
		  datumTokenizer: Bloodhound.tokenizers.whitespace,
		  queryTokenizer: Bloodhound.tokenizers.whitespace,
		  remote: {
			  url: __util.getBaseUrl() + urlSuffix + "%QUERY" + (params!==undefined ? "?" + params : ""),
			  wildcard: '%QUERY'
		  }
	});
};

BaseEditor.prototype.prepareTranslations = function(translations) {
	if (translations!=null || (translations instanceof Array && translations.length>0)) {
		__translator.addTranslations(translations);
		__translator.addTranslations(this.baseTranslations);
		__translator.getTranslations();
	}
};

BaseEditor.prototype.registerNavFormControlEvents = function() {
	var _this = this;
	$(".nav-form-controls a").on('click', function(e) {
		var href = $(this).attr("href");
		if (href===undefined || href==="#") {
			href = $(this).next().find("a").first().attr("href"); 
		}
		
		if (!_this.isScrolledIntoView(href)) {
			$.scrollTo(href, 300, {
				onAfter: function(target, settings) { $(target).focus() }
			});		
		} else {
			$(href).focus();
		}
		e.stopPropagation(); 
		return false;
	});
};

BaseEditor.prototype.registerFormControlSelectionEvents = function(element) {
	var _this = this;
	element.find("input").focus(function() {		
		$(".nav-form-controls li").removeClass("active");
		
		var select = $(".nav-form-controls a[href='#" + $(this).attr("id") + "']");
		if (select.length) {
			select.parent().addClass("active");
		} else {
			var selector = $(this).closest(".collection-editor-table, .collection-editor-list").attr("id");
			if (selector!==undefined) {
				select = $(".nav-form-controls a[href='#" + selector + "']");
				select.parent().addClass("active");
			}
		}
		
		
	});
	element.find("select").focus(function() {		
		$(".nav-form-controls li").removeClass("active");
		$(".nav-form-controls a[href='#" + $(this).attr("id") + "']").parent().addClass("active");
	});
};

BaseEditor.prototype.isScrolledIntoView = function(elem) {
    var $elem = $(elem);
    var $window = $(window);

    var docViewTop = $window.scrollTop();
    var docViewBottom = docViewTop + $window.height();

    var elemTop = $elem.offset().top;
    var elemBottom = elemTop + $elem.height();

    return ((elemBottom <= docViewBottom) && (elemTop >= docViewTop));
}

BaseEditor.prototype.registerTypeahead = function(element, datasource, displayAttr, limit, 
		suggestionCallback, selectionCallback, changeCallback) {
	
	var _this = this;
	element.typeahead(null, {
		name: datasource,
		hint: false,
		display: displayAttr,
		source: _this.vocabularySources[datasource],
		limit: limit,
		templates: {
			empty: ['<div class="tt-empty-message">',
			        	__translator.translate("~eu.dariah.de.colreg.common.labels.no_match_found"),
			        '</div>'].join('\n'),
			suggestion: function(data) { return suggestionCallback(data); }
		}
	});
	
	// Executed when a suggestion has been accepted by the user
	if (selectionCallback!==undefined && selectionCallback!==null && typeof selectionCallback==='function') {
		element.bind('typeahead:select typeahead:autocomplete', function(ev, suggestion) {
			selectionCallback(this, suggestion);
		});
	}
	
	// Executed on custom input -> typically needs some validation
	if (changeCallback!==undefined && changeCallback!==null && typeof changeCallback==='function') {
		element.bind('change', function() {
			changeCallback(this, $(this).val());
		});
	}
};

BaseEditor.prototype.validateInput = function(element, urlPrefix, value) {
	var _this = this;
	$.ajax({
        url: __util.getBaseUrl() + urlPrefix + value,
        type: "GET",
        dataType: "json",
        success: function(data) {
        	$(element).closest(".form-group").removeClass("has-error");
        },
        error: function(textStatus) { 
        	$(element).closest(".form-group").addClass("has-error");
        }
	});
};

BaseEditor.prototype.deleteEntity = function(prefix) {
	var _this = this;

    bootbox.dialog({
            message : __translator.translate("~eu.dariah.de.colreg.view.collection.labels.delete_collection.body"),
            title : __translator.translate("~eu.dariah.de.colreg.view.collection.labels.delete_collection.head"),
            buttons : {
                    no : {
                            label : __translator.translate("~eu.dariah.de.colreg.common.link.no"),
                            className : "btn-default"
                    },
                    yes : {
                            label : __translator.translate("~eu.dariah.de.colreg.common.link.yes"),
                            className : "btn-primary",
                            callback : function() {
                            	$.ajax({
                                    url: __util.getBaseUrl() + prefix + _this.entityId + "/delete",
                                    type: "POST",
                                    success: function(data) {
                                    	window.location.reload();
                                    },
                                    error: function(textStatus) { 
                                    	alert(__translator.translate("~eu.dariah.de.colreg.view.collection.notification.could_not_delete") + textStatus);
                                    }
                            	});
                            }
                    }
            }
    });
};