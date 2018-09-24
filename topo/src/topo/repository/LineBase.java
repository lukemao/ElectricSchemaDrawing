package topo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import topo.adaptors.CsvAdapter;

public class LineBase{
	final static Logger logger = Logger.getLogger(LineBase.class);
	
    private final ArrayList<Line> lineList;
    private final CsvAdapter lineAdapter;
    
    public LineBase(){
        this("C:\\Users\\lukemao\\workspace\\topo\\resources");
    }
    
    public LineBase(String path){
        lineList = new ArrayList<>();
        lineAdapter = new CsvAdapter(path, "line.csv");
        populateLineList();
    }
    
    private final void populateLineList(){
    	try{
            while(lineAdapter.hasNext()){
                lineAdapter.readNextLine();
                ArrayList<String> fields = new ArrayList<>();
                while(lineAdapter.hasNextField()){
                    fields.add(lineAdapter.readNextField());
                }
                if(fields.size()!=3){
                    throw new Exception("Invalid line: "+fields);
                }
                Line l = new Line(fields.get(0), fields.get(1), fields.get(2));
                lineList.add(l);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
    }

    
    public ArrayList<Line> getLineList(){
        return this.lineList;
    }
    
    public boolean hasLine(Line line){
        return this.lineList.contains(line);
    }
    
    public Line getLine(String node1, String node2){
    	if(node1==null || node2 == null) return null;
        for(Line l : lineList){
            if(l.onTheLine(node1, node2)){
                return l;
            }
        }
        return null;
    }
    
    public List<Line> getLines(String node){
    	if(node == null || node.isEmpty()) return null;
        return lineList.stream()
                       .distinct()
                       .filter(l -> l.onTheLine(node))
                       .collect(Collectors.toList());
    }
}