package topo.graphic2;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.picking.PickedState;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

public class DeleteNodeMenuItem<V> extends JMenuItem implements NodeMenuListener<V> {
    private V node;
    private VisualizationViewer visComp;
    
    /** Creates a new instance of DeleteVertexMenuItem */
    public DeleteNodeMenuItem() {
        super("Delete Node");
        this.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                visComp.getPickedVertexState().pick(node, false);
                visComp.getGraphLayout().getGraph().removeVertex(node);
                visComp.repaint();
            }
        });
    }

    public void setNodeAndView(V v, VisualizationViewer visComp) {
        this.node = v;
        this.visComp = visComp;
        this.setText("Delete Node " + v.toString());
    }
    
}