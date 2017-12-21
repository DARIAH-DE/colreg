var dashboard;
$(document).ready(function() {
	dashboard = new Dashboard();
	dashboard.resize();
	dashboard.loadGraph();
	
	$('[data-toggle="popover"]').popover();
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
	
	var images = [];
	images['collection'] = "resources/img/fa-static/mi-collections.png";
	images['organization'] = "resources/img/fa-static/fa-university.png";
	images['person'] = "resources/img/fa-static/fa-user.png";
	
	var colors = [];
	colors['collection'] = "#003564";
	colors['organization'] = "#483E69";
	colors['person'] = "#006841";

	for (i = 0; i < data.nodes.length; i++) {
		g.nodes.push({
			id : data.nodes[i].id,
			label : data.nodes[i].label,
			x : Math.random(),
			y : Math.random(),
			size : 1,
			nodeType : data.nodes[i].type,
			color : colors[data.nodes[i].type],
			type : 'circle',
			image : {
				url : images[data.nodes[i].type],
				clip : 0.85
			}
		});
	}
	for (i = 0; i < data.edges.length; i++) {
		g.edges.push({
			id : 'e' + i,
			source : data.edges[i].source,
			target : data.edges[i].target,
			size : 1,
			color : '#444'
		});
	}
	
	s = new sigma({
		graph : g,
		settings : {
			labelThreshold : 100,
		},
		renderer : {
			// NOTE: Canvas rendering is important for images
			container : document.getElementById('graph-container'),
			type : 'canvas'
		}
	});
	
	s.startForceAtlas2({
		gravity: 2,
		edgeWeightInfluence: 0,
		outboundAttractionDistribution: true,
		startingIterations: 1,
		worker: true, 
	});
	
	CustomShapes.init(s);
	s.refresh();
	
	setTimeout(function() { 
		s.stopForceAtlas2();
		// Allow dragging after some force-timeout
		sigma.plugins.dragNodes(s, s.renderers[0]);
	}, 7000);
	
	$("#btn-download-svg").bind("click", function() { s.toSVG({download: true, filename: 'colreg.svg', size: 1000}); });
	
	s.bind('doubleClickNode rightClickNode', function(e) {
		window.location = __util.composeUrl((e.data.node.nodeType==="collection" ? "collections/" : "agents/") + e.data.node.id);
	});
};