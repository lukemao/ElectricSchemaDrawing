package mouseMenu;

import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 * An interface for menu items that are interested in knowning the currently selected edge and
 * its visualization component context.  Used with PopupVertexEdgeMenuMousePlugin.
 * @author Lu Mao
 */
public interface EdgeMenuListener<E> {
    /**
     * Used to set the edge and visulization component.
     * @param e 
     * @param visComp 
     */
     void setEdgeAndView(E e, VisualizationViewer visView); 
    
}