var _csrf = $('meta[name=_csrf]').attr("content");
var _csrfHeader = $('meta[name=_csrf_header]').attr("content");

/*$.ajaxSetup({
    beforeSend: function(xhr, settings) {
        if (settings.type == 'POST' || settings.type == 'PUT' || settings.type == 'DELETE') {
        	xhr.setRequestHeader(_csrfHeader, _csrf);
        }
    }
});*/

/* 
 * Mainly for Safari support
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Function/bind
 */
if (!Function.prototype.bind) {
	  Function.prototype.bind = function (oThis) {
	    if (typeof this !== "function") {
	      // closest thing possible to the ECMAScript 5 internal IsCallable function
	      throw new TypeError("Function.prototype.bind - what is trying to be bound is not callable");
	    }

	    var aArgs = Array.prototype.slice.call(arguments, 1),
	        fToBind = this,
	        fNOP = function () {},
	        fBound = function () {
	          return fToBind.apply(this instanceof fNOP && oThis
	                                 ? this
	                                 : oThis,
	                               aArgs.concat(Array.prototype.slice.call(arguments)));
	        };

	    fNOP.prototype = this.prototype;
	    fBound.prototype = new fNOP();

	    return fBound;
	  };
}


if (!String.format) {
        String.format = function(format) {
                var args = Array.prototype.slice.call(arguments, 1);
                if (format!==null && format!==undefined) {
	                return format.replace(/{(\d+)}/g, function(match, number) {
	                        return typeof args[number] != 'undefined' ? args[number] : match;
	                });
                } else {
                	return format;
                }
        };
}

String.prototype.endsWith = function(suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
};

String.prototype.startsWith = function(start) {
        return this.length >= start.length && this.substr(0,start.length)==start;
};

String.prototype.replaceAll = function (find, replace) {
    var str = this;
    return str.replace(new RegExp(find.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&'), 'g'), replace);
};

var Util = function() {
        __translator.addTranslations(["~eu.dariah.de.colreg.common.view.notifications.login_required.head",
                                      "~eu.dariah.de.colreg.common.view.notifications.login_required.body",
                                      "~eu.dariah.de.colreg.common.link.yes",
                                      "~eu.dariah.de.colreg.common.link.no",
                                      "~eu.dariah.de.colreg.common.view.forms.servererror.head",
                                      "~eu.dariah.de.colreg.common.view.forms.servererror.body"])
        // We depend on the view's main js for this call
    //__translator.getTranslations();
        this.entityMap = {
                "&" : "&amp;",
                "<" : "&lt;",
                ">" : "&gt;",
                '"' : '&quot;',
                "'" : '&#39;',
                "/" : '&#x2F;'
        };
};
var __util = new Util();

Util.prototype.escapeHtml = function(string) {
        var _this = this;
        return String(string).replace(/[&<>"'\/]/g, function(s) {
                return _this.entityMap[s];
        });
};

Util.prototype.showLoginNote = function() {
        var _this = this;

        bootbox.dialog({
                message : __translator.translate("~eu.dariah.de.minfba.common.view.notifications.login_required.body"),
                title : __translator.translate("~eu.dariah.de.minfba.common.view.notifications.login_required.head"),
                buttons : {
                        no : {
                                label : __translator.translate("~eu.dariah.de.minfba.common.link.no"),
                                className : "btn-default"
                        },
                        yes : {
                                label : __translator.translate("~eu.dariah.de.minfba.common.link.yes"),
                                className : "btn-primary",
                                callback : function() {
                                        window.location = $("#login a").prop("href");
                                }
                        }
                }
        });
};

Util.prototype.processServerError = function(jqXHR, textStatus, errorThrown) {
	var errorContainer = $("<div>");
	if (jqXHR.reponseText!==null && jqXHR.reponseText!==undefined) {
		var error = $('<div class="server-error-container">').append(jqXHR.responseText).get();
		$(errorContainer).append(error);
	}
	
	bootbox.alert({
		  title: __translator.translate("~eu.dariah.de.minfba.common.view.forms.servererror.head"),
		  size: "large",
		  message: "<p>" + __translator.translate("~eu.dariah.de.minfba.common.view.forms.servererror.body") + "</p>" + $(errorContainer).html()
	});
};

Util.prototype.isLoggedIn = function() {
        var loggedIn = false;
        $.ajax({
        url: __util.getBaseUrl() + "async/isAuthenticated",
        type: "GET",
        async: false,
        encoding: "UTF-8",
        dataType: "text",
        success: function(data) {
                loggedIn = (data=="true");
        }
        });
       
        if (loggedIn) {
                $("#login").css("display", "none");
                $("#logout").css("display", "block");
        } else {
                $("#login").css("display", "block");
                $("#logout").css("display", "none");
        }
        return loggedIn;       
};

Util.prototype.getBaseUrl = function() {
        return $("#baseUrl").val();
};

Util.prototype.composeUrl = function(target) {
    return $("#baseUrl2").val().replace("{}", target);
};

Util.prototype.renderActivities = function(container, id, data) {
	$(container).html("");
	
	if (data!=null) {
		for (var i=0; i<data.length; i++) {
			$(container).append(
					"<div class=\"alert alert-sm alert-info activity-history-element\">" + 
						"<em>" + (data[i].timestamp==null ? "?" : data[i].timestampString) +"</em><br />" + 
						"<h4>" +
							" " + data[i].user + 
						"</h4>" +
						"<span class=\"glyphicon glyphicon-asterisk\" aria-hidden=\"true\"></span> " + 
							data[i].news + "&nbsp;&nbsp;" +
						"<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span> " + 
							data[i].edits + "&nbsp;&nbsp;" +
						"<span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\"></span> " + 
							data[i].deletes +
					"</div>");
			
		}
	}
}