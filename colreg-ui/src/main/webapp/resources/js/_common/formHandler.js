var modalFormHandler = null;

/** These are the placeholders used within this form:
  * 	- ~*... in the ModalFormHandler
  * 	- ~*file... in the SchemaSourceSelector
  * Usually only the ~*... ones are required unless the file upload dialog is used
  */
/*this.translations = [{placeholder: "~*servererror.head", key: "~eu.dariah.de.minfba.common.view.forms.new.servererror.head"},
            		 	{placeholder: "~*servererror.body", key: "~eu.dariah.de.minfba.common.view.forms.new.servererror.body"},
            		 	{placeholder: "~*uploadsuccessful.head", key: "~eu.dariah.de.minfba.common.view.forms.new.importsuccessful.head"},
            		 	{placeholder: "~*uploadsuccessful.body", key: "~eu.dariah.de.minfba.common.view.forms.new.importsuccessful.body"},
            		 	{placeholder: "~*validationerrors.head", key: "~eu.dariah.de.minfba.common.view.forms.new.validationerrors.head"},
            		 	{placeholder: "~*validationerrors.body", key: "~eu.dariah.de.minfba.common.view.forms.new.validationerrors.body"},
            		 	{placeholder: "~*file.validationsucceeded.head", key: "~eu.dariah.de.minfba.common.view.forms.file.validationsucceeded.head"},
            		 	{placeholder: "~*file.validationsucceeded.body", key: "~eu.dariah.de.minfba.common.view.forms.file.validationsucceeded.body"},
            		 	{placeholder: "~*file.servererror.head", key: "~eu.dariah.de.minfba.common.view.forms.new.servererror.head"},
            		 	{placeholder: "~*file.servererror.body", key: "~eu.dariah.de.minfba.common.view.forms.new.servererror.body"},
            		 	{placeholder: "~*file.generalerror.head", key: "~eu.dariah.de.minfba.common.view.forms.file.generalerror.head"},
            		 	{placeholder: "~*file.generalerror.body", key: "~eu.dariah.de.minfba.common.view.forms.file.generalerror.body"},
            		 	{placeholder: "~*file.uploaderror.head", key: "~eu.dariah.de.minfba.common.view.forms.file.uploaderror.head"},
            		 	{placeholder: "~*file.uploaderror.body", key: "~eu.dariah.de.minfba.common.view.forms.file.uploaderror.body"},
            		 	{placeholder: "~*file.deletesucceeded.head", key: "~eu.dariah.de.minfba.common.view.forms.file.deletesucceeded.head"},
            		 	{placeholder: "~*file.deletesucceeded.body", key: "~eu.dariah.de.minfba.common.view.forms.file.deletesucceeded.body"},
            		 	{placeholder: "~*file.uploadcomplete.head", key: "~eu.dariah.de.minfba.common.view.forms.file.uploadcomplete.head"},
            		 	{placeholder: "~*file.uploadcomplete.body", key: "~eu.dariah.de.minfba.common.view.forms.file.uploadcomplete.body"}
            		 ];*/
var ModalFormHandler = function(options) {
	this.options = {
			method: "GET",
			data: undefined,
			contentType: undefined
	};
	$.extend(true, this.options, options);

	this.form = null; 				// contains the form-object
	this.container = null;			// styled div-container around form
	this.formResetted = false;		// form has been user-resetted (cancel/save)
	this.fileUploadElements = [];		// elements that contain file-upload
	this.showOnlyLastestAlert = true;	// show only one response to user or pile up
	this.sourceSelectors = [];
	
	/**
	 * NOTE: These are the defaults in the DARIAH Generic Search; adapt or set on initialization accordingly
	 */
	//this.translations = [];
	this.translations = [{placeholder: "~*servererror.head", key: "~eu.dariah.de.minfba.common.view.forms.servererror.head"},
	                     {placeholder: "~*servererror.body", key: "~eu.dariah.de.minfba.common.view.forms.servererror.body"},
	                     {placeholder: "~*uploadsuccessful.head", key: "~eu.dariah.de.minfba.common.view.forms.uploadsuccessful.head"},
	                     {placeholder: "~*uploadsuccessful.body", key: "~eu.dariah.de.minfba.common.view.forms.uploadsuccessful.body"},
	                     {placeholder: "~*validationerrors.head", key: "~eu.dariah.de.minfba.common.view.forms.validationerrors.head"},
	                     {placeholder: "~*validationerrors.body", key: "~eu.dariah.de.minfba.common.view.forms.validationerrors.body"},
	                     {placeholder: "~*file.validationsucceeded.head", key: "~eu.dariah.de.minfba.common.view.forms.file.validationsucceeded.head"},
	            		 {placeholder: "~*file.validationsucceeded.body", key: "~eu.dariah.de.minfba.common.view.forms.file.validationsucceeded.body"},
	            		 {placeholder: "~*file.servererror.head", key: "~eu.dariah.de.minfba.common.view.forms.file.servererror.head", defaultText: "Problem interacting with server"},
	            		 {placeholder: "~*file.servererror.body", key: "~eu.dariah.de.minfba.common.view.forms.file.servererror.body", defaultText: "Could not interact with server. Please check the internet connectivity of your computer, try again or inform the administrator if this problem pertains."},
	            		 {placeholder: "~*file.generalerror.head", key: "~eu.dariah.de.minfba.common.view.forms.file.generalerror.head"},
	            		 {placeholder: "~*file.generalerror.body", key: "~eu.dariah.de.minfba.common.view.forms.file.generalerror.body"},
	            		 {placeholder: "~*file.uploaderror.head", key: "~eu.dariah.de.minfba.common.view.forms.file.uploaderror.head"},
	            		 {placeholder: "~*file.uploaderror.body", key: "~eu.dariah.de.minfba.common.view.forms.file.uploaderror.body"},
	            		 {placeholder: "~*file.deletesucceeded.head", key: "~eu.dariah.de.minfba.common.view.forms.file.deletesucceeded.head"},
	            		 {placeholder: "~*file.deletesucceeded.body", key: "~eu.dariah.de.minfba.common.view.forms.file.deletesucceeded.body"},
	            		 {placeholder: "~*file.uploadcomplete.head", key: "~eu.dariah.de.minfba.common.view.forms.file.uploadcomplete.head"},
	            		 {placeholder: "~*file.uploadcomplete.body", key: "~eu.dariah.de.minfba.common.view.forms.file.uploadcomplete.body"}];
	
	this.displayCallback = null;
	this.setupCallback = null;
	this.completeCallback = null;
};

ModalFormHandler.prototype.update = function() {
	$(this.container).modal("handleUpdate");
};

ModalFormHandler.prototype.show = function(identifier) {
	if (this.container===null) { 
		// new form
		this.init();
	} else if (identifier !== this.options.identifier) { 
		// the identifier changed, form is invalid...
		$(this.container).remove();
    	this.container = null;
    	this.form = null;
    	this.init();
	} else { 
		// show previously hidden form
		$(this.container).modal('show');
	}
	this.formResetted = false;
};

ModalFormHandler.prototype.init = function() {
	var _this = this;
	$('body').modalmanager('loading');
	
	// Prepare required translations for the dialog and retrieve them
	if (this.translations!=null && this.translations instanceof Array) {
		for (var i=0; i<this.translations.length; i++) {
			__translator.addTranslation(this.translations[i].key);
		}
	}
	__translator.getTranslations();

	var url = "";
	if (_this.options.formFullUrl!=null && _this.options.formFullUrl!=undefined) {
		url = _this.options.formFullUrl;
	} else {
		url = window.location.pathname + _this.options.formUrl;
	}
	
	// Get the actual form to display
	$.ajax({
        url: url,
        data: _this.options.data,
        contentType: _this.options.contentType, // Could be undefined, no problem
        type: _this.options.method,
        dataType: "html",
        success: function(data, textStatus, jqXHR) {
        	if (jqXHR.status==203) {
        		__util.processServerError(jqXHR, textStatus);
        	}
        	_this.form = $(jQuery.parseHTML(data));
        	_this.setUpForm();
        },
        error: function(jqXHR, textStatus, errorThrown) { 
        	_this.formResetted==true;
        	$(_this).hide();
        	
        	__util.processServerError(jqXHR, textStatus);
        }
	});
};

ModalFormHandler.prototype.setUpForm = function() {
	var _this = this;
	this.container = $("<div class='modal fade'>");
	
	if (this.options.additionalModalClasses!==null) {
		$(this.container).addClass(this.options.additionalModalClasses);
	}

	$(this.container).html($(this.form));
	$(this.form).find(".form-header").addClass("modal-header");
	$(this.form).find(".form-header").prepend('<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>');
	$(this.form).find(".form-content").addClass("modal-body");
	$(this.form).find(".form-footer").addClass("modal-footer");
	$(this.form).find(".form-btn-cancel").attr("data-dismiss", "modal");
	$(this.form).find(".form-btn-cancel").attr("aria-hidden", "true");
	
	for (var i=0; i<this.fileUploadElements.length; i++) {
		var inputElement = $(this.form).find(this.fileUploadElements[i].selector);
		var containingElement = $(inputElement).parent();
		
		_this.sourceSelectors.push(new SchemaSourceSelector(_this, containingElement, $(inputElement).attr("id"), this.fileUploadElements[i]));
		$(inputElement).remove();
	}
	
	// Form can be destroyed after it is hidden
	$(this.form).find(".form-btn-cancel").on("click", function() {
		_this.formResetted = true;
	});
	$(this.container).on('hidden', function () {
        if (_this.formResetted==true) {
        	$(_this.container).remove();
        	_this.container = null;
        	_this.form = null;
        	if (_this.options.cancelCallback != undefined && typeof _this.options.cancelCallback == 'function') {
        		_this.options.cancelCallback(_this.container);
        	}
        }
    });
	
	$(this.form).submit(function(e) { 
		_this.submit(this); 
		e.preventDefault();
		return false;
	});
	
	
	
	if (_this.options.setupCallback != undefined && typeof _this.options.setupCallback == 'function') {
		$(this.container).on('show.bs.modal', function (e) {
			_this.options.setupCallback(_this.container, this);
		});
	}	

	if (_this.options.displayCallback != undefined && typeof _this.options.displayCallback == 'function') {
		$(this.container).on('shown.bs.modal', function (e) {
			_this.options.displayCallback(_this.container, _this);
		});
	}
	
	$(this.container).on('shown.bs.modal', function (e) {
		if ($(_this.form).find('.form-control').length>0) {
			$(_this.form).find('.form-control')[0].focus();
		}
	});
	
	$(this.container).modal();
	
};

ModalFormHandler.prototype.addMessage = function(type, header, message) {
	// Build the new alert
	var msgContainer = $("<div class='alert alert-" + type + "'>");
	msgContainer.append("<button data-dismiss='alert' class='close' type='button'>×</button>");
	if (header!=="") { 
		msgContainer.append($("<h4>").html(header)); 
	}
	if (message!=="") {
		msgContainer.append($("<p>").html(message));
	}
	
	var _this = this;
	
	// if only the latest alert should be displayed, remove existing one(s)
	if (this.showOnlyLastestAlert==true) {
		var existing = $(this.form).find(".form-content").find(".alert");
		if ($(existing).length > 0) {
			
			$(_this.form).find(".form-content").prepend(msgContainer);
			//$(existing).delay(2000).fadeOut(400, function() {$(this).remove();});
		} else {
			$(_this.form).find(".form-content").prepend(msgContainer);
		}
	} else {
		$(_this.form).find(".form-content").prepend(msgContainer);
	}
	
	// Show the new alert
	$(msgContainer).fadeIn(200);
};

ModalFormHandler.prototype.close = function() {
	this.formResetted = true;
	$(this.container).modal('hide');
};

ModalFormHandler.prototype.submit = function(data) {
	var _this = this;
	
	if ($(_this.container).find("form").prop("action")!=null && $(_this.container).find("form").prop("method")!=null &&
			$(_this.container).find("form").prop("action")!=undefined && $(_this.container).find("form").prop("method")!=undefined && 
			$(_this.container).find("form").prop("action")!="" && $(_this.container).find("form").prop("method")!="") {
		
		try {
			$.ajax({
		        url: $(_this.container).find("form").prop("action"),
		        data: $(data).serialize(),
		        type: $(_this.container).find("form").prop("method"),
		        dataType: "json",
		        success: function(data) { 
		        	_this.processSubmitResponse(data); 
		        },
		        error: function(jqXHR, textStatus, errorThrown) { 
		        	__util.processServerError(jqXHR, textStatus, errorThrown); 
		        }
			});
		} catch (e) {
			console.log(e);
		}
		
	} else {
		if (_this.options.completeCallback != undefined && typeof _this.options.completeCallback == 'function') {
			_this.options.completeCallback($(data).serialize(), _this.container);
		}
		this.formResetted = true;
		$(this.container).modal('hide');
	}
	

};

ModalFormHandler.prototype.translate = function(placeholder) {
	if (this.options.translations != null && this.options.translations instanceof Array) {
		for(var i=0; i<this.options.translations.length; i++) {
			if (this.options.translations[i].placeholder===placeholder) {
				return __translator.translate(this.options.translations[i].key);
			}
		}
	}
	
	for(var i=0; i<this.translations.length; i++) {
		if (this.translations[i].placeholder===placeholder) {
			return __translator.translate(this.translations[i].key);
		}
	}
};

ModalFormHandler.prototype.processSubmitResponse = function(data) {
	var _this = this;
	
	if (data.success == true) {
		// successfully saved
		this.formResetted = true;
		$(this.container).modal('hide');
		
		__notifications.showMessage(NOTIFICATION_TYPES.SUCCESS, _this.translate("~*uploadsuccessful.head"), _this.translate("~*uploadsuccessful.body"));
		
		
		if (_this.options.completeCallback != undefined && typeof _this.options.completeCallback == 'function') {
			_this.options.completeCallback(data);
		}
	} else {
		var msg = String.format(_this.translate("~*validationerrors.body"), data.errorCount);
		
		if (data.message != null && data.message.messageBody != null) {
			msg = msg + "\n" + data.message.messageBody;
		}
		
		if (data.objectErrors !== null) {
			var list = "<ul>";
			for (var i=0; i<data.objectErrors.length; i++) {
				list += "<li>" + data.objectErrors[i] + "</li>";
			}
			msg += list + "</ul>";
		}
		this.addMessage("danger", _this.translate("~*validationerrors.head"), msg);
		
		$(this.form).find(".form-group").removeClass("error");
		$(this.form).find(".help-block").remove();
		
		if (data.fieldErrors !== null) {
			$(data.fieldErrors).each(function() {
				$(_this.form).find("#" + this.field).closest(".form-group").addClass("has-error");
				var msgContainer = $(_this.form).find("#" + this.field).closest("div");
				$(this.errors).each(function() {
    				msgContainer.append($("<span class='help-block'>").text(this));            				
    			});
			});
		}
	}
};


var SchemaSourceSelector = function(owner, container, modelId, options) {
	this.content = null;
	this.container = container;
	this.owner = owner;
	this.options = options;
	this.modelId = modelId;
	
	this.tmpButton = null;
	
	var _this = this;
	
	$.ajax({
        url: window.location.pathname + _this.options.formSource,
        data: _this.options.preexisting == null ? null : { preexisting: $(_this.owner.form).find(_this.options.preexisting).val() },
        type: "GET",
        dataType: "html",
        success: function(data) { _this.displayForm(data); },
        error: function(jqXHR, textStatus, errorThrown) { 
        	_this.owner.formResetted==true;
        	$(_this.owner).hide();


        	__util.processServerError(jqXHR, textStatus, errorThrown);
        }
	});
};

SchemaSourceSelector.prototype.displayForm = function(data) {
	this.content = $(data);
	$(this.content).prop("id", this.modelId);
	$(this.container).append(this.content);
	
	var _this = this;

	
	$(this.content).find(".fileupload-files-preexisting .btn").on('click', function() { 
		_this.handleDelete($(this).find(".deleteLink").val(), $($(this).find(".removeSelector").val())); 
		$(_this.content).find(".fileinput-button").css("display", "inline");
	});

	
	$(this.content).find(".fileinput-button").fileupload({
		url: window.location.pathname + _this.options.uploadTarget,
		dataType: 'json',
        add: function (e, data) { $(_this.owner.form).find(".form-btn-submit").attr("disabled", "true"); _this.handleAdd(e, data); },
        progressall: function (e, data) { _this.handleProgressAll(e, data); },
        done: function (e, data) { _this.handleDone(e, data); },
        fail: function (e, data) { _this.handleFail(e, data); }
    });
};

SchemaSourceSelector.prototype.handleAdd = function(e, data) {
	// Show the progress bar
	$(this.container).find(".fileupload-progress").removeClass("hide");
	
	// Hide the upload-file button if only one file allowed
	if (this.options.multiFiles == false) {
		this.tmpButton = $(this.content).find(".fileinput-button");
    	$(this.tmpButton).hide();
	}      	
    data.submit();
};

SchemaSourceSelector.prototype.handleProgressAll = function(e, data) {
	var progress = parseInt(data.loaded / data.total / 2 * 100, 10);
    $(this.container).find('.progress .bar').css('width', progress + '%');
    $(this.container).find('.progress-extended').text(data.loaded + " of " + data.total + "B");
};

SchemaSourceSelector.prototype.handleDone = function(e, data) {
	$(this.container).find('.progress .bar').css('width', '75%');
    $(this.container).find('.progress-extended').text("");
	
    /* saved, id, fileType, fileSize, created, delete */
    /*$(this.container).find(".fileupload-progress").hide(0);*/
    
    var _this = this;
    
	$(_this.container).find('.fileupload-files').each(function() {
	   
		var fileTable = $("<table>");
		$(this).append(fileTable);
    
		if (data.result.success == true) {
			_this.owner.addMessage("success", _this.owner.translate("~*file.uploadcomplete.head"), 
					_this.owner.translate("~*file.uploadcomplete.body"));
			
			$.each(data.result.files, function (result, object) {
	    		// Do the validation
	    		$.ajax({
			        url: window.location.pathname + object.validateLink,
			        type: "GET",
			        dataType: "json",
			        success: function(data) { 
			        	$(_this.container).find('.progress .bar').css('width', '75%');
			        	$(_this.container).find(".fileupload-progress").hide(0);
		        		_this.owner.addMessage(data.message.messageType, data.message.messageHead, data.message.messageBody);
			        	
			        	if (data.success==true) {
			        		$(_this.owner.form).find(".form-btn-submit").removeAttr("disabled");
			        	}
			        	
			        	$(_this.container).find("input#file\\.id").attr("value", object.id);
			        	
			        	if (_this.options.elementChangeCallback != undefined && typeof _this.options.elementChangeCallback == 'function') {
			        		_this.options.elementChangeCallback(data);
			        	}
			        },
			        error: function(jqXHR, textStatus, errorThrown) { 
			        	__util.processServerError(jqXHR, textStatus, errorThrown);
			        }
				});
	    		
	    		// General containers
	    		var contentRow = $("<tr>");
	    		
	    		// Buttons
	    		var btnDelete = $('<span class="btn"> <span class="glyphicon glyphicon-trash"></span></button>');
	    		$(btnDelete).on('click', function() { _this.handleDelete(object.deleteLink, fileTable); });
	    		$("<td class='file-actions'>").append(btnDelete).appendTo($(contentRow));
	    		
	    		// File informational area
	    		$(contentRow).append("<td class='file-name'>" + object.fileName + "</td>");
	    		$(contentRow).append("<td class='file-size'>(" + object.fileSize + ")</td>");
	    		      		
	    		$(contentRow).appendTo($(fileTable));
	    		
	    	});
			
		} else {
			_this.owner.addMessage("danger", _this.owner.translate("~*file.generalerror.head"), 
					String.format(_this.owner.translate("~*file.generalerror.body"), data.error));
		}
    });
};

SchemaSourceSelector.prototype.handleFail = function(e, data) {
	var _this = this;
	
    $(this.container).find(".fileupload-progress").hide(0);
    if (this.options.multiFiles == false) {
		$(this.tmpButton).show();
	}
    this.owner.addMessage("danger", this.owner.translate("~*file.uploaderror.head"), 
    		String.format(this.owner.translate("~*file.uploaderror.body"), data.errorThrown));
    
    if (_this.options.elementChangeCallback != undefined && typeof _this.options.elementChangeCallback == 'function') {
		_this.options.elementChangeCallback(null);
	}
};

SchemaSourceSelector.prototype.handleDelete = function(deleteLink, fileContainer) {
	var _this = this;
	$.ajax({
        url: window.location.pathname + deleteLink,
        type: "GET",
        dataType: "text",
        success: function(data) { 
        	_this.owner.addMessage("info", _this.owner.translate("~*file.deletesucceeded.head"), 
            		_this.owner.translate("~*file.deletesucceeded.body"));
        	
        	$(_this.container).find("input#file\\.id").attr("value", "");
        	fileContainer.remove(); 
        	
        	$(_this.owner.form).find(".form-btn-submit").removeAttr("disabled");
        	
        	if (_this.options.multiFiles == false) {
        		$(_this.tmpButton).show();
        	}
        	if (_this.options.elementChangeCallback != undefined && typeof _this.options.elementChangeCallback == 'function') {
        		_this.options.elementChangeCallback(null);
        	}
        },
        error: function(jqXHR, textStatus, errorThrown) { 
        	__util.processServerError(jqXHR, textStatus, errorThrown);
        }
	});
};