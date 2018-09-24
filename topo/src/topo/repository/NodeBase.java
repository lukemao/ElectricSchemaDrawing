package topo.repository;

import java.util.ArrayList;

import topo.adaptors.CsvAdapter;

public class NodeBase{
	
    private final ArrayList<String> nodeList;
    private final CsvAdapter nodeAdapter;

    
    public NodeBase(){
        this("C:\\Users\\lukemao\\git\\ElectricSchemaDrawing\\topo\\resources");
    }
    
    public NodeBase(String path){
        nodeList = new ArrayList<>();
        nodeAdapter = new CsvAdapter(path, "node.csv");
        populateNodeList();
    }
    
    private final void populateNodeList(){
        while(this.nodeAdapter.hasNext()){
//            if(!node.startsWith("N")){
//                logger.error("invalide node: "+node);
//            }
            nodeList.add(this.nodeAdapter.readNextLine());
        }
    }
    
    public ArrayList<String> getNodeList(){
        return this.nodeList;
    }
    
    public boolean hasNode(String nodeName){
        return this.nodeList.contains(nodeName);
    }
}