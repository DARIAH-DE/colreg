var Translator = function() {
	this.translations = [];
	this.translationsUrl = $("#baseUrl").val() + "translate";
};

Translator.prototype.addTranslations = function(codes) {
	if (codes===null && codes===undefined) {
		return;
	}
	if ($.isArray(codes)) {
		for (var i=0; i<codes.length; i++) {
			this.addTranslation(codes[i]);
		}
	} else {
		this.addTranslation(codes);
	}
};

Translator.prototype.addTranslation = function(code) {
	if (code===null && code===undefined) {
		return;
	}
	
	if ($.isArray(code)) {
		this.addTranslations(code);
	} else {
		var codeExists = false;
		for (var i=0; i<this.translations.length; i++) {
		    if (this.translations[i].key===code) {
		    	codeExists = true;
		    	break;
		    }
		}
		if (!codeExists) {
	    	this.translations.push({key: code, translation: null});
	    }
	}
};

Translator.prototype.getTranslations = function() {
	var _this = this;
	$.ajax({
	    url: _this.translationsUrl,
	    type: "POST",
	    dataType: "json",
	    async: false,
	    data: {keys: JSON.stringify(_this.getNonTranslated()) },
	    success: function(data) {
	    	_this.updateTranslations(data);
	    }
	});
};

Translator.prototype.updateTranslations = function(data) {
	for (var i=0; i<data.length; i++) {
		for (var j=0; j<this.translations.length; j++) {
			if (data[i].key===this.translations[j].key) {
				this.translations[j].translation = data[i].translation;
				break;
			}
		}
	}
};

Translator.prototype.getNonTranslated = function() {
	var result = [];
	for (var i=0; i<this.translations.length; i++) {
	    if (this.translations[i].translation===null) {
	    	result.push(this.translations[i]);
	    }
	}
	return result;
};

Translator.prototype.translate = function(code) {
	for(var i=0; i<this.translations.length; i++) {
		if (this.translations[i].key===code) {
			if(this.translations[i].translation==undefined) {
				return this.translations[i].defaultText;
			}
			return this.translations[i].translation;
		}
	}
	return code;
};

var __translator = new Translator();