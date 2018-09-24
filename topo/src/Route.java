import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

public class Route{
	final static Logger logger = Logger.getLogger(Route.class);
	
    private LinkedList<String> nodes;
    
    public Route(){
        nodes = new LinkedList<>();
    }
    
    public Route addNode(String node){
        nodes.add(node);
        return this;
    }
    
    public int length(){
        return nodes.size();
    }
    
    public String getLastNode(){
        return nodes.size()>0?nodes.get(nodes.size()-1):null;
    }
    
    public Route addRoute(Route r){
        for(String n:r.nodes){
            if(n != null) nodes.add(n);
        }
        return this;
    }
    
    public Iterator<String> iterator(){
        return nodes.iterator();
    }
    
    public boolean onTheRoute(String node){
        return nodes.stream()
                    .filter(n -> node.equals(n))
                    .collect(Collectors.toList())
                    .size()>0;
    }
    
    public String toString(){
        if(nodes.size()==0) return null;
        String s = "";
        s = nodes.get(0);
        for(int i = 1; i<nodes.size(); i++){
            s+=" > "+nodes.get(i);
        }
        return s;
    }
    
    public static void main (String args[]){
        Route r = new Route();
        r.addNode("N1").addNode("N2").addNode("N3").addNode("N4");
        logger.info("onTheRoute: "+r.onTheRoute("N5"));
        logger.info("onTheRoute: "+r.onTheRoute("N2"));
        logger.info("print route: "+r);
        logger.info("print new route: "+new Route().addNode("N0").addRoute(r));
    }
}