import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import topo.repository.Customer;
import topo.repository.CustomerBase;
import topo.repository.Line;
import topo.repository.LineBase;
import topo.repository.NodeBase;

public class GraphConstructor{
	final static Logger logger = Logger.getLogger(GraphConstructor.class);
	
    private final NodeBase nodes;
    private final LineBase lines;
    private final CustomerBase customers;
    
    public GraphConstructor(NodeBase nb, LineBase lb, CustomerBase cb){
        this.nodes = nb;
        this.lines = lb;
        this.customers = cb;
    }
    
    public List<Route> findRoutes(String startingNode){
        if(!nodes.hasNode(startingNode)) {
            logger.error("Unknow node: "+startingNode);
        }
        Route r = new Route();
        r.addNode(startingNode);
        return findRoutes(r);
    }

    private List<Route> findRoutes(Route r) {
        List<Route> routes = new LinkedList<>();
        String lastNode = r.getLastNode();
        List<Customer> cList = customers.getCustomersByNode(lastNode);
        for(Customer c:cList){
            Route newRoute = new Route();
            newRoute.addRoute(r).addNode(c.getName());
            routes.add(newRoute);
        }
        List<Line> connectedLines = lines.getLines(lastNode);
        for(Line l:connectedLines){
            String theOtherNode = l.getTheOtherNode(lastNode);
            if(theOtherNode == null) continue;
            if(r.onTheRoute(theOtherNode)) continue;
            Route newRoute = new Route();
            newRoute.addRoute(r).addNode(theOtherNode);
            routes.addAll(findRoutes(newRoute));
        }
        return routes;
    }

    public final int GRID_HEIGHT=50;
    public final int GRID_WIDTH=50;
    private final String[][] GRID = new String[GRID_HEIGHT][GRID_WIDTH];
    private Object[][] OBJECTS = new Object[GRID_HEIGHT][GRID_WIDTH];
    private int starting_x;
    private int starting_y;
    private int next_x;
    private int next_y;
    private final int LINE_LENGTH = 3;
    private MovingDirection direction = MovingDirection.DOWN;
    
    enum MovingDirection{
        UP,DOWN,LEFT,RIGHT,CAN_NOT_MOVE;
    }
    
    void init(){
        starting_x = GRID_HEIGHT/2;
        starting_y = 0;
        next_x = GRID_HEIGHT/2;
        next_y = 0;
    }
    
    void initGRID(){
        for(int i = 0; i< GRID.length; i++){
            for(int j = 0; j<GRID[i].length; j++){
                GRID[i][j] = "\t";
                OBJECTS[i][j] = null;
            }
        }
    }
    
    MovingDirection calculateDirection(int y, int x, String node){
//        System.out.println("calculateDirection for: "+node);
        if(y+LINE_LENGTH<=GRID_HEIGHT&&
                (OBJECTS[y+LINE_LENGTH][x] == null
                ||GRID[y+LINE_LENGTH][x].trim().equalsIgnoreCase(node))&&!node.startsWith("C")){
             return MovingDirection.DOWN;
         }else if(x-LINE_LENGTH>=0&&
             (OBJECTS[y][x-LINE_LENGTH] == null
             ||GRID[y][x-LINE_LENGTH].trim().equalsIgnoreCase(node))){
             return MovingDirection.LEFT;
         }else if(x+LINE_LENGTH<=GRID_WIDTH&&
             (OBJECTS[y][x+LINE_LENGTH] == null
             ||GRID[y][x+LINE_LENGTH].trim().equalsIgnoreCase(node))){
             return MovingDirection.RIGHT;
         }else if(y-LINE_LENGTH>=0&&
             (OBJECTS[y-LINE_LENGTH][x] == null
             ||GRID[y-LINE_LENGTH][x].trim().equalsIgnoreCase(node))&&!node.startsWith("C")){
             return MovingDirection.UP;
         }
         else {
             return MovingDirection.CAN_NOT_MOVE;
         }
    }
    
    public MovingDirection calculateNextMove(String node){
//    	System.out.println("calculateNextMove for: "+node);
        switch (calculateDirection(next_y, next_x, node)){
            case DOWN:{
                next_y += LINE_LENGTH;
                direction = MovingDirection.DOWN;
                return direction;
            }
            case LEFT:{
                next_x -= LINE_LENGTH;
                direction = MovingDirection.LEFT;
                return direction;
            }
            case RIGHT:{
                next_x += LINE_LENGTH;
                direction = MovingDirection.RIGHT;
                return direction;
            }
            case UP:{
                next_y -= LINE_LENGTH;
                direction = MovingDirection.UP;
                return direction;
            }
            default:{
                logger.error("CANNOT MOVE");
                direction = MovingDirection.CAN_NOT_MOVE;
                return direction;
            }
        }
    }
    
    public boolean plotRoute(Route r){
        init();
        Iterator<String> nodes = r.iterator();
        if(r.length()>0){
            plot(nodes.next());
        }
        while(nodes.hasNext()){
            String node = nodes.next();
            if(calculateNextMove(node) == MovingDirection.CAN_NOT_MOVE){
                return false;
            }
            plot(node);
            drawLines();
        }
        return true;
    }
    
    public void plot(String node){
        OBJECTS[next_y][next_x] = node;
        GRID[next_y][next_x] = node+"\t";
    }
    
    public void drawLines(){
        switch (direction){
        case DOWN:{
            for(int i = 1; i<LINE_LENGTH; i++){
                GRID[next_y-i][next_x] = "|\t";
            }
            break;
        }
        case RIGHT:{
            for(int i = 1; i<LINE_LENGTH; i++){
                GRID[next_y][next_x-i] = "-\t";
            }
            break;
        }
        case LEFT:{
            for(int i = 1; i<LINE_LENGTH; i++){
                GRID[next_y][next_x+i] = "-\t";
            }
            break;
        }
        case UP:{
            for(int i = 1; i<LINE_LENGTH; i++){
                GRID[next_y+i][next_x] = "|\t";
            }
            break;
        }
    }
        
    }
    
    public void printGrid(){
        for(int i = 0; i< GRID.length; i++){
            for(int j = 0; j<GRID[i].length; j++){
                System.out.print(GRID[i][j]);
            }
            System.out.println();
        }
    }
    public void printObjectGrid(){
        for(int i = 0; i< OBJECTS.length; i++){
            for(int j = 0; j<OBJECTS[i].length; j++){
                System.out.print(OBJECTS[i][j]);
            }
            System.out.println();
        }
    }
    
    public List<Route> findShortestRoute(List<Route> routes, Customer c){
        ArrayList<Route> shortestRoutes = new ArrayList<>();
        int shortestLength = Integer.MAX_VALUE;
        for(Route tmp:routes){
            if(tmp.getLastNode().equals(c.getName()) && tmp.length()<=shortestLength){
            	shortestRoutes.add(tmp);
                shortestLength = tmp.length();
            }
        }
        Iterator<Route> i = shortestRoutes.iterator();
        while(i.hasNext()){
            Route r = i.next();
            if(r.length()>shortestLength){
                shortestRoutes.remove(r);
            }
        }
        return shortestRoutes;
    }
    
    public static void main(String args[]){
        NodeBase nb = new NodeBase();
        logger.info("Nodes: \n"+nb.getNodeList());
        LineBase lb = new LineBase();
        logger.info("Lines: \n"+lb.getLineList());
        CustomerBase cb = new CustomerBase();
        logger.info("Customers: \n"+cb.getCustomerList());
        
        GraphConstructor graph = new GraphConstructor(nb, lb, cb);
        List<Route> routes = graph.findRoutes("N0");
        int count = 1;
        logger.info("Get all routes ====================================");
        for(Route r:routes){
            logger.info("route"+count+":\t"+r);
            count++;
        }
        List<Route> shortestRoutes = graph.findShortestRoute(routes, cb.getCustomer("C6"));
        shortestRoutes.stream().forEach(r->logger.info("Shortest route for "+r.getLastNode()+": \n"+r));
        System.out.println();
        shortestRoutes = graph.findShortestRoute(routes, cb.getCustomer("C7"));
        shortestRoutes.stream().forEach(r->logger.info("Shortest route for "+r.getLastNode()+": \n"+r));
        System.out.println();
        shortestRoutes = graph.findShortestRoute(routes, cb.getCustomer("C4"));
        shortestRoutes.stream().forEach(r->logger.info("Shortest route for "+r.getLastNode()+": \n"+r));
        System.out.println();
        shortestRoutes = graph.findShortestRoute(routes, cb.getCustomer("C5"));
        shortestRoutes.stream().forEach(r->logger.info("Shortest route for "+r.getLastNode()+": \n"+r));
        System.out.println();
        shortestRoutes = graph.findShortestRoute(routes, cb.getCustomer("C9"));
        shortestRoutes.stream().forEach(r->logger.info("Shortest route for "+r.getLastNode()+": \n"+r));
        System.out.println();
        shortestRoutes = graph.findShortestRoute(routes, cb.getCustomer("C10"));
        shortestRoutes.stream().forEach(r->logger.info("Shortest route for "+r.getLastNode()+": \n"+r));
        
        graph.initGRID();
        graph.init();
        graph.plotRoute(routes.get(0));
        logger.info("=== New Route ===");
        graph.init();
        graph.plotRoute(routes.get(1));
        logger.info("=== New Route ===");
        graph.init();
        graph.plotRoute(routes.get(3));
        logger.info("=== New Route ===");
        graph.init();
        graph.plotRoute(routes.get(4));
        logger.info("=== New Route ===");
        graph.init();
        graph.plotRoute(routes.get(5));
        logger.info("=== New Route ===");
        graph.init();
        graph.plotRoute(routes.get(6));
        logger.info("=== New Route ===");
        graph.init();
        graph.plotRoute(routes.get(7));
        logger.info("=== New Route ===");
        graph.init();
        graph.plotRoute(routes.get(8));
        logger.info("=== New Route ===");
        graph.init();
        graph.plotRoute(routes.get(9));
        graph.printGrid();
    }
}