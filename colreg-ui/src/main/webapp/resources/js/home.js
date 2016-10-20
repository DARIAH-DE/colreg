var dashboard;
$(document).ready(function() {
	dashboard = new Dashboard();
	dashboard.resize();
	dashboard.loadGraph();
});

$(window).resize(function() {
	dashboard.resize();
});

var Dashboard = function() {};

Dashboard.prototype.resize = function() {
	var topOffsetLatest = $("#editor-version-panel").offset().top;
	var topOffsetGraph = $("#graph-container").offset().top;
	
	$("#editor-version-panel").css("height", ($(window).height() - topOffsetLatest - 70) + "px");
	$("#graph-container").css("height", ($(window).height() - topOffsetGraph - 70) + "px");
};

Dashboard.prototype.loadGraph = function() {
	var _this = this;
	$.ajax({
        url: __util.composeUrl("async/graph"),
        type: "GET",
        async: false,
        encoding: "UTF-8",
        dataType: "json",
        success: function(data) {
        	_this.initGraph(data);
        }
    });
};

Dashboard.prototype.followClick = function(e) {
	console.log(e.type, e.label, e.data.captor);
} 

Dashboard.prototype.initGraph = function(data) {
	var i, s, g = {
		nodes : [],
		edges : []
	};
	// Generate a random graph:
	for (i = 0; i < data.nodes.length; i++) {
		g.nodes.push({
			id : data.nodes[i].id,
			label : data.nodes[i].label,
			nodeType : data.nodes[i].type,
			x : Math.random(),
			y : Math.random(),
			size : 1,
			color : data.nodes[i].type=="collection" ? '#003564' : "#483E69"
		});
	}
	for (i = 0; i < data.edges.length; i++) {
		g.edges.push({
			id : 'e' + i,
			source : data.edges[i].source,
			target : data.edges[i].target,
			size : Math.random(),
			color : '#444'
		});
	}
	// Instantiate sigma:
	s = new sigma({
		graph : g,
		settings: {
			labelThreshold: 100,
		},
		container : 'graph-container'
	});
	/*s.startForceAtlas2({
		gravity: 5,
		slowDown: 1,
		adjustSizes: true
	});*/
	s.bind('doubleClickNode rightClickNode', function(e) {
		window.location = __util.composeUrl((e.data.node.nodeType==="collection" ? "collections/" : "agents/") + e.data.node.id);
	});
};