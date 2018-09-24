package topo.graphic2;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

/**
 * A class to implement the deletion of an edge from within a 
 * PopupMenuMousePlugin.
 * @author Lu Mao
 */
public class DeleteLineMenuItem<E> extends JMenuItem implements LineMenuListener<E> {
    private E edge;
    private VisualizationViewer visComp;
    
    /** Creates a new instance of DeleteLineMenuItem */
    public DeleteLineMenuItem() {
        super("Delete Line");
        this.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                visComp.getPickedEdgeState().pick(edge, false);
                visComp.getGraphLayout().getGraph().removeEdge(edge);
                visComp.repaint();
            }
        });
    }

    public void setLineAndView(E edge, VisualizationViewer visComp) {
        this.edge = edge;
        this.visComp = visComp;
        this.setText("Delete Line " + edge.toString());
    }
    
}