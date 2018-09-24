package topo.graphic2;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;
import topo.repository.CustomerBase;
import topo.repository.Line;
import topo.repository.LineBase;
import topo.repository.NodeBase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.apache.commons.collections15.Transformer;

public class GraphGenerator {
	
	private SparseMultigraph<String, Line> g;

    private final NodeBase nodes;
    private final LineBase lines;
    private final CustomerBase customers;
    
    public GraphGenerator(NodeBase nb, LineBase lb, CustomerBase cb){
        this.nodes = nb;
        this.lines = lb;
        this.customers = cb;
        this.g = new SparseMultigraph<String, Line>();        
        nodes.getNodeList().stream().forEach(n -> g.addVertex(n));
        customers.getCustomerList().stream().distinct().forEach(c -> {
            g.addEdge(new Line(c.getName(), c.getName(), c.getNode()), c.getNode(), c.getName(), EdgeType.DIRECTED);
            g.addVertex(c.getName());
        });
        lines.getLineList().stream().forEach(l -> g.addEdge(l, l.getSourceNode(), l.getTargetNode(), EdgeType.UNDIRECTED) );
    }
    
    public static void main(String[] args) {        

        NodeBase nb = new NodeBase();
        LineBase lb = new LineBase();
        CustomerBase cb = new CustomerBase();
        
        GraphGenerator gg = new GraphGenerator(nb, lb, cb);
        
        JFrame frame = new JFrame("Node-Line-Customer");
        // Layout<V, E>, VisualizationViewer<V,E>
//        Map<GraphElements.MyVertex,Point2D> vertexLocations = new HashMap<GraphElements.MyVertex, Point2D>();
        Layout<String, Line> layout = new CircleLayout<>(gg.g);
        layout.setSize(new Dimension(800,800));
        VisualizationViewer<String, Line> vv = new VisualizationViewer<String, Line>(layout);
        vv.setPreferredSize(new Dimension(850,850));
        NodeFillTransformer<String, Paint> vertexFillPaint = new NodeFillTransformer<String, Paint>();  
        NodeBorderTransformer<String, Paint> vertexBorderPaint = new NodeBorderTransformer<String, Paint>();  
        //NodeShapeTransformer<String, Shape> vertexShapePaint = new NodeShapeTransformer<String, Shape>();
        // Show vertex and edge labels
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setVertexFillPaintTransformer(vertexFillPaint);
        //vv.getRenderContext().setVertexShapeTransformer(vertexShapePaint);
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line<String,Line>());
        // Create a graph mouse and add it to the visualization viewer
        EditingModalGraphMouse gm = new EditingModalGraphMouse(vv.getRenderContext(), 
                 TopologyElements.NodeFactory.getInstance(),
                 TopologyElements.LineFactory.getInstance()); 
        // Set some defaults for the Edges...
        TopologyElements.LineFactory.setDefaultCapacity(192.0);
        TopologyElements.LineFactory.setDefaultWeight(5.0);
        // Trying out our new popup menu mouse plugin...
        PopupMenuMousePlugin myPlugin = new PopupMenuMousePlugin();
        // Add some popup menus for the edges and vertices to our mouse plugin.
        JPopupMenu edgeMenu = new OptionMenus.EdgeMenu(frame);
        JPopupMenu vertexMenu = new OptionMenus.NodeMenu();
        myPlugin.setLinePopup(edgeMenu);
        myPlugin.setNodePopup(vertexMenu);
        gm.remove(gm.getPopupEditingPlugin());  // Removes the existing popup editing plugin
        
        gm.add(myPlugin);   // Add our new plugin to the mouse
        
        vv.setGraphMouse(gm);

        
        //JFrame frame = new JFrame("Editing and Mouse Menu Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        
        // Let's add a menu for changing mouse modes
        JMenuBar menuBar = new JMenuBar();
        JMenu modeMenu = gm.getModeMenu();
        modeMenu.setText("Mouse Mode");
        modeMenu.setIcon(null); // I'm using this in a main menu
        modeMenu.setPreferredSize(new Dimension(80,20)); // Change the size so I can see the text
        
        JMenu saveMenu = new JMenu("Save");
//        saveMenu.add(new JMenuItem(){
//        	public JMenuItem(){
//        		
//        	}
//        });
        
        menuBar.add(modeMenu);
        frame.setJMenuBar(menuBar);
        gm.setMode(ModalGraphMouse.Mode.PICKING); // Start off in editing mode
        frame.pack();
        frame.setVisible(true);    
    }
    
    public void saveImage(VisualizationImageServer<String, Line> vis, VisualizationViewer<String,String> vv){
    	// Create the buffered image
    	BufferedImage image = (BufferedImage) vis.getImage(
    	    new Point2D.Double(vv.getGraphLayout().getSize().getWidth() / 2,
    	    vv.getGraphLayout().getSize().getHeight() / 2),
    	    new Dimension(vv.getGraphLayout().getSize()));

    	// Write image to a png file
    	File outputfile = new File("graph.png");

    	try {
    	    ImageIO.write(image, "png", outputfile);
    	} catch (IOException e) {
    	    // Exception handling
    	}
    }
    
    
//    private class DeleteNodeMenuItem<V> extends JMenuItem implements NodeMenuListener<V> {
//        private V node;
//        private VisualizationViewer visComp;
//        
//        /** Creates a new instance of DeleteVertexMenuItem */
//        public DeleteNodeMenuItem() {
//            super("Delete Node");
//            this.addActionListener(new ActionListener(){
//                public void actionPerformed(ActionEvent e) {
//                    visComp.getPickedVertexState().pick(node, false);
//                    visComp.getGraphLayout().getGraph().removeVertex(node);
//                    visComp.repaint();
//                }
//            });
//        }
//
//        public void setNodeAndView(V v, VisualizationViewer visComp) {
//            this.node = v;
//            this.visComp = visComp;
//            this.setText("Delete Node " + v.toString());
//        }
//        
//    }
}