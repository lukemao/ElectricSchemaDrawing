import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Paint;
import java.awt.Stroke;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.List;

import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Lu Mao
 */

public class SimpleGraphView2 {
    private Graph<String, String> g;
    private Layout<String, String> layout; 
    BasicVisualizationServer<String,String> vv;
    
    
    private 
    Transformer<String, Shape> vertexShape = new Transformer<String, Shape>() {
        private final Shape[] styles = { 
            new Rectangle(0, 0, 20, 20),
            new Ellipse2D.Double(-10, -10, 20, 20)
            };
 
        @Override
        public Shape transform(String s) {
            if(s.startsWith("C")){
                return styles[0];
            }else if(s.startsWith("N")){
                return styles[1];
            }
            return styles[0];
        }
    };
    /** Creates a new instance of SimpleGraphView */
    public SimpleGraphView2() {
        g = new DirectedSparseGraph<String, String>();
        // Add some vertices. From above we defined these to be type Integer.
        g.addVertex("N0");
        g.addVertex("N1");
        g.addVertex("N2"); 
        g.addVertex("N3"); 
        g.addVertex("N4"); 
        g.addVertex("C1"); 
        g.addVertex("C2"); 
        g.addVertex("C3"); 

        g.addEdge("L1", "N0", "N1", EdgeType.DIRECTED); 
        g.addEdge("L2", "N1", "N2", EdgeType.DIRECTED); 
        g.addEdge("L3", "N1", "N3", EdgeType.DIRECTED); 
        g.addEdge("L4", "N2", "N4", EdgeType.DIRECTED); 
        g.addEdge("L5", "N3", "N4", EdgeType.DIRECTED); 
        g.addEdge("L6", "N3", "C1", EdgeType.DIRECTED); 
        g.addEdge("L7", "N4", "C2", EdgeType.DIRECTED); 
        g.addEdge("L8", "N2", "C3", EdgeType.DIRECTED); 
        
    }
    
    public SimpleGraphView2(Graph<String,String> g){
        this.g = g;
    }
    
    public SimpleGraphView2(Graph<String,String> g, Layout<String, String> layout){
        this.g = g;
        this.layout = layout;
        vv = new BasicVisualizationServer<String,String>(layout);
    }
    
    public void addVertex(String vertex){
        g.addVertex(vertex);
    }
    
    public void addEdge(String name, String source, String target, EdgeType type){
        g.addEdge(name, source, target, type); 
    }
    
    public void addVertexes(List<String> vertexes){
        if(vertexes!=null){
            vertexes.stream().filter(v -> v!=null).forEach(v -> g.addVertex(v));
        }
    }
    
    void initialiseFRLayout(){
        layout = new FRLayout(g);
        if(vv == null)
            vv = new BasicVisualizationServer<String,String>(layout);
        else
            vv.setGraphLayout(layout);
    }
    
    public Layout<String, String> getLayout(){
        return this.layout;
    }
    
    public void setLayoutDimension(Dimension d){
        this.layout.setSize(d);
    }
    
    public BasicVisualizationServer<String,String> getVisualisationServer(){
        return vv;
    }
    
    public JFrame paint(String frameName){
    	JFrame frame = new JFrame(frameName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);     
        return frame;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SimpleGraphView2 sgv = new SimpleGraphView2(); // This builds the graph
        // Layout<V, E>, VisualizationComponent<V,E>
        FRLayout<String, String> layout = new FRLayout(sgv.g);
        layout.setAttractionMultiplier(1.1);
        layout.setRepulsionMultiplier(10.5);
        layout.setSize(new Dimension(300,300));
        BasicVisualizationServer<String,String> vv = new BasicVisualizationServer<String,String>(layout);
        vv.setPreferredSize(new Dimension(350,350));       
        // Setup up a new vertex to paint transformer...
        Transformer<String,Paint> vertexPaint = new Transformer<String,Paint>() {
            public Paint transform(String s) {
                return Color.GREEN;
            }
        };    
      Transformer<String, Shape> vertexShape = new Transformer<String, Shape>() {
            private final Shape[] styles = { 
                new Rectangle(20, 20),
                new Ellipse2D.Double(-10, -10, 20, 20)
                };
     
            @Override
            public Shape transform(String s) {
                if(s.startsWith("C")){
                    return styles[0];
                }else if(s.startsWith("N")){
                    return styles[1];
                }
                return styles[0];
            }
        };  
        // Set up a new stroke Transformer for the edges
        float dash[] = {0.1f};
        final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
             BasicStroke.JOIN_MITER, 1.0f, dash, 0.0f);
        Transformer<String, Stroke> edgeStrokeTransformer = new Transformer<String, Stroke>() {
            public Stroke transform(String s) {
                return edgeStroke;
            }
        };
        
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vv.getRenderContext().setVertexShapeTransformer(vertexShape);
        vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
        vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line<String,String>());
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);        
        //vv.getRenderer().setVertexRenderer(new MyRenderer());
        
        JFrame frame = new JFrame("Node-Line-Customer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.getContentPane().setLayout(new GridBagLayout());
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);     
    }
    
//    public static void main2(String[] args) {
//        SimpleGraphView2 sgv = new SimpleGraphView2(); // This builds the Treegraph
//        // Layout<V, E>, VisualizationComponent<V,E>
//        Layout<String, String> layout = new RadialTreeLayout(sgv.f);
//        //layout.setSize(new Dimension(300,300));
//        BasicVisualizationServer<String,String> vv = new BasicVisualizationServer<String,String>(layout);
//        //vv.setPreferredSize(new Dimension(350,350));       
//        // Setup up a new vertex to paint transformer...
//        Transformer<String,Paint> vertexPaint = new Transformer<String,Paint>() {
//            public Paint transform(String s) {
//                return Color.WHITE;
//            }
//        };  
////        Transformer<String, Shape> vertexShape = 
////                new Transformer<String, Shape>() {
////                    private final Shape[] styles = { 
////                        new Rectangle(-20, -10, 40, 20),
////                        new Ellipse2D.Double(-25, -10, 50, 20),
////                        new Circle(-25, -10, 50, 20),
////                        new Arc2D.Double(-30, -15, 60, 30, 30, 30, 
////                            Arc2D.PIE) };
////             
////                    @Override
////                    public Shape transform(String s) {
////                        if(s.startsWith("C")){
////                            return styles[0];
////                        }else if(s.startsWith("N")){
////                            return styles[0];
////                        }
////                        return styles[0];
////                    }
////                };  
//        // Set up a new stroke Transformer for the edges
//        float dash[] = {0.1f};
//        final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
//             BasicStroke.JOIN_MITER, 1.0f, dash, 0.0f);
//        Transformer<String, Stroke> edgeStrokeTransformer = new Transformer<String, Stroke>() {
//            public Stroke transform(String s) {
//                return edgeStroke;
//            }
//        };
//        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
////        /vv.getRenderContext().setVertexShapeTransformer(vertexShape);
//        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
//        vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
//        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
//        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);        
//
//        //vv.getRenderer().setVertexRenderer(new MyRenderer());
//        
//        JFrame frame = new JFrame("Node-Line-Customer");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.getContentPane().add(vv);
//        frame.pack();
//        frame.setVisible(true);     
//    }
    
    static class MyRenderer implements Renderer.Vertex<String, String> {
        @Override public void paintVertex(RenderContext<String, String> rc,
            Layout<String, String> layout, String vertex) {
          GraphicsDecorator graphicsContext = rc.getGraphicsContext();
          Point2D center = layout.transform(vertex);
          Shape shape = null;
          Color color = null;
          if(vertex.startsWith("C")) {
            shape = new Rectangle((int)center.getX()-10, (int)center.getY()-10, 20, 20);
            color = Color.WHITE;
          } else if(vertex.startsWith("N")) {
            shape = new Rectangle((int)center.getX()-10, (int)center.getY()-20, 20, 40);
            color = Color.WHITE;
          } 
          graphicsContext.setPaint(color);
          graphicsContext.fill(shape);
        }
      }
    
}