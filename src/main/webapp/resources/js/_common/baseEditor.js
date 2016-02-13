function BaseEditor() {
	this.entityId = $("#entityId").text();
	this.vocabularySources = new Array();
	this.tables = new Array();
	this.lists = new Array();
	
	this.baseTranslations = ["~eu.dariah.de.colreg.common.labels.no_match_found",
	                         "~eu.dariah.de.colreg.view.common.labels.enter_version_comment",
	                         "~eu.dariah.de.colreg.view.collection.notification.could_not_delete",
	                         "~eu.dariah.de.colreg.view.collection.labels.delete_collection.body",
	                         "~eu.dariah.de.colreg.view.collection.labels.delete_collection.head",
	                         "~eu.dariah.de.colreg.common.link.cancel",
	                         "~eu.dariah.de.colreg.common.link.no",
	                         "~eu.dariah.de.colreg.common.link.ok",
	                         "~eu.dariah.de.colreg.common.link.yes"];
	
	var _this = this;
	$("#chk-toggle-hints").on("change", function() { _this.toggleHints($(this).is(":checked")); });
	$("#chk-toggle-hints").trigger("change");
	
	$(".form-btn-submit").on("click", function(e) { _this.submit(); });
};

BaseEditor.prototype.sort = function() {
	for (var tbl in this.tables) {
		this.tables[tbl].sort();
	}
	for (var lst in this.lists) {
		this.lists[lst].sort();
	}
};

BaseEditor.prototype.appendComment = function(prefix, id) {
	var _this = this;
	bootbox.dialog({
		title : __translator.translate("~eu.dariah.de.colreg.view.common.labels.enter_version_comment"),
		message : 	"<div class='row'>" +
						"<div class='col-sm-12'>" +
							"<input id='last-minute-version-comment' class='form-control'></input>" +
						"</div>" +
					"</div>",
        buttons : {
        	cancel : {
            	label : __translator.translate("~eu.dariah.de.colreg.common.link.cancel"),
                className : "btn-default"
        	},
            ok : {
            	label : __translator.translate("~eu.dariah.de.colreg.common.link.ok"),
                className : "btn-primary",
                callback : function() {
                	$.ajax({
                        url: __util.composeUrl(prefix + _this.entityId + "/commentVersion/" + id),
                        type: "POST",
                        data: {comment: $("#last-minute-version-comment").val()},
                        success: function(data) {
                        	window.location.reload();
                        }
                	});
            	}
            }
    	}
	});
};

BaseEditor.prototype.submit = function() {
	this.sort();
	
	$("form").attr("action", $("#js-form-action").val());
	$("form").submit();
};

BaseEditor.prototype.toggleHints = function(check) {
	if (check) {
		$(".editor-hint").show();
	} else {
		$(".editor-hint").hide();
	}
};

BaseEditor.prototype.addVocabularySource = function(name, urlSuffix, params) {
	this.vocabularySources[name] = new Bloodhound({
		  datumTokenizer: Bloodhound.tokenizers.whitespace,
		  queryTokenizer: Bloodhound.tokenizers.whitespace,
		  remote: {
			  url: __util.composeUrl(urlSuffix + "%QUERY" + (params!==undefined ? "?" + params : "")),
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
        url: __util.composeUrl(urlPrefix + value),
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
                                    url: __util.composeUrl(prefix + _this.entityId + "/delete"),
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