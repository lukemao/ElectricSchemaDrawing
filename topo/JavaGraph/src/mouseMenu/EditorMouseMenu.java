package mouseMenu;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;

/**
 * Illustrates the use of custom edge and vertex classes in a graph editing application.
 * Demonstrates a new graph mouse plugin for bringing up popup menus for vertices and
 * edges.
 * @author Lu Mao
 */
public class EditorMouseMenu {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        JFrame frame = new JFrame("Editing and Mouse Menu Demo");
        SparseMultigraph<GraphElements.MyVertex, GraphElements.MyEdge> g = 
                new SparseMultigraph<GraphElements.MyVertex, GraphElements.MyEdge>();
        // Layout<V, E>, VisualizationViewer<V,E>
//        Map<GraphElements.MyVertex,Point2D> vertexLocations = new HashMap<GraphElements.MyVertex, Point2D>();
        Layout<GraphElements.MyVertex, GraphElements.MyEdge> layout = new StaticLayout(g);
        layout.setSize(new Dimension(300,300));
        VisualizationViewer<GraphElements.MyVertex,GraphElements.MyEdge> vv = 
                new VisualizationViewer<GraphElements.MyVertex,GraphElements.MyEdge>(layout);
        vv.setPreferredSize(new Dimension(350,350));
        // Show vertex and edge labels
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        // Create a graph mouse and add it to the visualization viewer
        EditingModalGraphMouse gm = new EditingModalGraphMouse(vv.getRenderContext(), 
                 GraphElements.MyVertexFactory.getInstance(),
                GraphElements.MyEdgeFactory.getInstance()); 
        // Set some defaults for the Edges...
        GraphElements.MyEdgeFactory.setDefaultCapacity(192.0);
        GraphElements.MyEdgeFactory.setDefaultWeight(5.0);
        // Trying out our new popup menu mouse plugin...
        PopupVertexEdgeMenuMousePlugin myPlugin = new PopupVertexEdgeMenuMousePlugin();
        // Add some popup menus for the edges and vertices to our mouse plugin.
        JPopupMenu edgeMenu = new MyMouseMenus.EdgeMenu(frame);
        JPopupMenu vertexMenu = new MyMouseMenus.VertexMenu();
        myPlugin.setEdgePopup(edgeMenu);
        myPlugin.setVertexPopup(vertexMenu);
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
        
        menuBar.add(modeMenu);
        frame.setJMenuBar(menuBar);
        gm.setMode(ModalGraphMouse.Mode.EDITING); // Start off in editing mode
        frame.pack();
        frame.setVisible(true);    
    }
    
    public void saveImage(VisualizationImageServer<GraphElements.MyVertex,GraphElements.MyEdge> vis, VisualizationViewer<GraphElements.MyVertex,GraphElements.MyEdge> vv){
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
    
}