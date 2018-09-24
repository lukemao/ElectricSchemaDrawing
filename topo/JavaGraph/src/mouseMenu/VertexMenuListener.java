package mouseMenu;

import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 * Used to indicate that this class wishes to be told of a selected vertex
 * along with its visualization component context. Note that the VisualizationViewer
 * has full access to the graph and layout.
 * @author Lu Mao
 */
public interface VertexMenuListener<V> {
    void setVertexAndView(V v, VisualizationViewer visView);    
}