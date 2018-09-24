import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.PluggableGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.TranslatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Lu Mao
 */
public class InteractiveGraphView1 {
    Graph<String, String> g;
    /** Creates a new instance of SimpleGraphView */
    public InteractiveGraphView1() {
        // Graph<V, E> where V is the type of the vertices and E is the type of the edges
        g = new SparseMultigraph<String, String>();
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
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	

        Transformer<String, Shape> vertexShape = new Transformer<String, Shape>() {
              private final Shape[] styles = { 
                  new Rectangle(-10, -10, 20, 20),
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
    	
        InteractiveGraphView1 sgv = new InteractiveGraphView1(); // Creates the graph...
        // Layout<V, E>, VisualizationComponent<V,E>
        Layout<String, String> layout = new FRLayout(sgv.g);
        layout.setSize(new Dimension(300,300));
        VisualizationViewer<String,String> vv = new VisualizationViewer<String,String>(layout);
        vv.setPreferredSize(new Dimension(350,350));
        // Show vertex and edge labels
        vv.getRenderContext().setVertexShapeTransformer(vertexShape);
        vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line<String,String>());
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        // Create a graph mouse and add it to the visualization component
//        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
//        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
//        vv.setGraphMouse(gm); 
//        // Add the mouses mode key listener to work it needs to be added to the visualization component
//        vv.addKeyListener(gm.getModeKeyListener());
        

        // Create our "custom" mouse here. We start with a PluggableGraphMouse
        // Then add the plugins you desire.
        PluggableGraphMouse gm = new PluggableGraphMouse(); 
        gm.add(new TranslatingGraphMousePlugin(MouseEvent.BUTTON1_MASK));
        gm.add(new ScalingGraphMousePlugin(new CrossoverScalingControl(), 0, 1.1f, 0.9f));
        gm.add(new PickingGraphMousePlugin<>(MouseEvent.ALT_MASK, MouseEvent.SHIFT_MASK));
        
        vv.setGraphMouse(gm); 
        
        JFrame frame = new JFrame("Interactive Graph View 1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);       
    }
    
}