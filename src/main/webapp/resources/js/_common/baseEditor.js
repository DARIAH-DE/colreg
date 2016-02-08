function BaseEditor() {
	this.entityId = $("#entityId").val();
	this.vocabularySources = new Array();
	this.tables = new Array();
	this.lists = new Array();
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
			        	'~No match found',
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
	$.ajax({
        url: __util.getBaseUrl() + prefix + _this.entityId + "/delete",
        type: "POST",
        success: function(data) {
        	window.location.reload();
        },
        error: function(textStatus) { 
        	alert("Could not delete entity: " + textStatus);
        }
	});
};