package topo.graphic2;

import edu.uci.ics.jung.visualization.VisualizationViewer;

public interface NodeMenuListener<V> {
    void setNodeAndView(V v, VisualizationViewer visView);    
}