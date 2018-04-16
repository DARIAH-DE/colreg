package eu.dariah.de.colreg.pojo.api;

import java.util.List;

public class GraphPojo {
	private List<EdgePojo> edges;
	private List<NodePojo> nodes;
	
	
	public List<EdgePojo> getEdges() { return edges; }
	public void setEdges(List<EdgePojo> edges) { this.edges = edges; }
	
	public List<NodePojo> getNodes() { return nodes; }
	public void setNodes(List<NodePojo> nodes) { this.nodes = nodes; }
}
