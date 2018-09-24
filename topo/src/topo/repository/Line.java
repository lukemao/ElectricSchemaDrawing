package topo.repository;

public class Line{
    
    private final String id;
    private final String sourceNode;
    private final String targetNode;
    
    private double weight;
    private double capacity;

	public Line(String id, String sNode, String tNode){
        this.id=id;
        this.sourceNode = sNode;
        this.targetNode = tNode;
    }

    public String getId() {
        return id;
    }

    public String getSourceNode() {
        return sourceNode;
    }

    public String getTargetNode() {
        return targetNode;
    }
    
    public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
    
    public String getTheOtherNode(String node){
        if(!onTheLine(node)) return null;
        return node.equals(sourceNode)?targetNode:sourceNode;
    }
    
    public boolean onTheLine(String node1, String node2){
        return (node1.equals(sourceNode)&&node2.equals(targetNode)) 
                ||(node1.equals(targetNode)&&node2.equals(sourceNode));
    }
    
    public boolean onTheLine(String node){
        return node.equals(sourceNode) || node.equals(targetNode);
    }
    
    public boolean equals(Object o){
        if(o instanceof Line){
            Line l = (Line)o;
            return sourceNode.equals(l.sourceNode) && targetNode.equals(l.targetNode)
                   || sourceNode.equals(l.targetNode) && targetNode.equals(l.sourceNode);
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sourceNode == null) ? 0 : sourceNode.hashCode());
        result = prime * result + ((targetNode == null) ? 0 : targetNode.hashCode());
        return result;
    }

    @Override
    public String toString(){
        //return "line<"+id+">: "+sourceNode +"-"+targetNode;
        return id;
    }
}