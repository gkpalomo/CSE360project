

public class Edge {
	
	private String edgeHead;
	private String edgeTail;
	private int tailDuration;
	
	// Constructor
    public Edge() {
        edgeHead = "";
        edgeTail = "";
        tailDuration = 0;
    }
    
    //Access 
    
    public String getEdgeHead() {
        return edgeHead;
    }
    
    public String getEdgeTail() {
        return edgeTail;
    }
    
    public int getTailDuration() {
        return tailDuration;
    }
    
    //Set
    
    public void setEdgeHead(String name) {
        edgeHead = name;
    }
    
    public void setEdgeTail(String name) {
        edgeTail = name;
    }
    
    public void setTailDuration(int num) {
        tailDuration = num;
    }

    public void setEdge(String head, String tail, int dur) {
    	this.setEdgeHead(head);
    	this.setEdgeTail(tail);
    	this.setTailDuration(dur);
    	
    }

    
	
	
	
}