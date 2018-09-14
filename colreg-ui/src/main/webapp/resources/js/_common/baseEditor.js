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
		
		if ($(href).length==0) {
			href = href + "-container";
		}
				
		if (!_this.isScrolledIntoView(href)) {
			$.scrollTo(href, 300, {
				onAfter: function(target, settings) { $(target).focus() }
			});		
		}
		
		var selectedElement = $(href);
		
		if (selectedElement.find(".btn-collection-editor-add").length>0) {
			selectedElement = selectedElement.find(".btn-collection-editor-add").first();
		} else if (selectedElement.find("a").length>0) { 
			selectedElement = selectedElement.find("a").first();
		} else if (selectedElement.find(".form-control:not(:disabled)").length>0) {
			selectedElement = selectedElement.find(".form-control:not(:disabled)").first();
		}
		selectedElement.focus();
		
		if (!_this.isScrolledIntoView(selectedElement)) {
			$.scrollTo(href, 300);		
		}
		
		e.stopPropagation(); 
		return false;
	});
};

BaseEditor.prototype.registerFormControlSelectionEvents = function(element) {
	var _this = this;
	
	element.find(".btn-collection-editor-add").focus(function() {
		$(".nav-form-controls li").removeClass("active");
		
		var selector = $(this).closest(".collection-editor-table, .collection-editor-list").attr("id");
		if (selector!==undefined) {
			var s = $(".nav-form-controls a[href='#" + selector + "']");
			s.parent().addClass("active");
		}
	});
	
	element.find(".collection-editor-table a.control-link").focus(function() {
		var selector = $(this).closest("table").attr("id");
		
		if (selector!==undefined) {
			$(".nav-form-controls li").removeClass("active");
			var s = $(".nav-form-controls a[href='#" + selector + "']");
			s.parent().addClass("active");		
		}
	});
	
	element.find(".editor-section a:not('.control-link'), .form-control.form-control-subcontrol").focus(function() {
		var selector = $(this).closest("div[id]").attr("id");
		
		if (selector!==undefined) {
			if (selector.endsWith("-container")) {
				selector = selector.replace("-container", "");
			}
			
			$(".nav-form-controls li").removeClass("active");
			var s = $(".nav-form-controls a[href='#" + selector + "']");
			s.parent().addClass("active");	
		}
	});
	
	element.find(".form-control:not('.form-control-subcontrol')").focus(function() {		
		$(".nav-form-controls li").removeClass("active");
		
		var s = $(".nav-form-controls a[href='#" + $(this).attr("id") + "']");
		if (s.length) {
			s.parent().addClass("active");
		} else {
			var selector = $(this).closest(".collection-editor-table, .collection-editor-list").attr("id");
			if (selector!==undefined) {
				s = $(".nav-form-controls a[href='#" + selector + "']");
				s.parent().addClass("active");
			}
		}
		
		
	});
	element.find("select:not('.form-control-subcontrol')").focus(function() {		
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

BaseEditor.prototype.triggerUploadImage = function(e, container) {
	var _this = editor;
	
	// Check for the various File API support.
	if (window.File && window.FileReader && window.FileList && window.Blob) {
		var files = e.target.files; // FileList object

	    // files is a FileList of File objects. List some properties.
	    var output = [];
	    for (var i = 0, f; f = files[i]; i++) {
	    	// Only process image files.
	        if (!f.type.match('image.*')) {
	        	$(".image-hint").html(__translator.translate("~eu.dariah.de.colreg.view.collection.labels.image_not_an_image"));
	        	continue;
	        }
	        if (f.size>_this.imageMaxFileSize) {
	        	$(".image-hint").html(__translator.translate("~eu.dariah.de.colreg.view.collection.labels.image_too_large") + " 5 MB");
	        	continue;
	        }	        
	        var formData = new FormData();
	        formData.append("file", f, f.name);
	        formData.append("entityId", _this.entityId);
	    	        
	        $.ajax({
		        url: __util.composeUrl("image/async/upload"),
		        data: formData,
		        type: "POST",
		        beforeSend: function( xhr ) {
		        	xhr.setRequestHeader("X-File-Name", f.name);
		        	xhr.setRequestHeader("X-File-Size", f.size);
		        	xhr.setRequestHeader("X-File-Type", f.type);
		        },
		        cache: false,
		        contentType: false,
		        processData: false,
		        aysnc: false,
		        timeout: 20000,
		        success: function(data) {
		        	if (data.success) {
		        		_this.lists['images'].triggerAddListElement(container, data.pojo.id);
		        		$(".image-hint").text("");
		        		$(".image-hint").hide();
		        	} else {
		        		$(".image-hint").html(data.objectErrors[0]);
		        		$(".image-hint").show();
		        	}
		        }
		    });
	    }
	}
};

BaseEditor.prototype.initRightsContainer = function() {
	$(".rights-container input[type='radio']").change(function () {
		var id = $(this).closest(".rights-container").prop("id");
		var controls = $(this).closest(".rights-container").find(".form-control");
		var selected = $(this).closest(".rights-container").find("#" + $(this).prop("name") + "-" + $(this).val());
		
		$(controls).prop('disabled', true);
		$(controls).prop('name', "");
		$(selected).prop('disabled', false);
		$(selected).prop('name', id);
	});
};