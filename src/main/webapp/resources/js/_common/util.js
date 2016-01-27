function deleteObject(id) {
	$.ajax({
        url: window.location.pathname + "/ajax/delete?id=" + id,
        type: "GET",
        dataType: "json",
        success: function(data) {
        	if (data.success==true) { 
        		$("#model_id_" + id).remove();
        		//modalMessage.showMessage(data.message_type, data.message_head, data.message_body);
        	} else {
        		//modalMessage.showMessage(data.message_type, data.message_head, data.message_body);
        	}
        },
        error: function(textStatus) { 
        	alert(textStatus);
        }
	});
}

if (!String.format) {
	  String.format = function(format) {
	    var args = Array.prototype.slice.call(arguments, 1);
	    return format.replace(/{(\d+)}/g, function(match, number) { 
	      return typeof args[number] != 'undefined'
	        ? args[number] 
	        : match
	      ;
	    });
	  };
	}

String.prototype.endsWith = function(suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
};

String.prototype.startsWith = function(start) {
	return this.length >= start.length && this.substr(0,start.length)==start;
};