package topo.graphic2;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;
import topo.repository.Customer;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import javax.swing.JPopupMenu;

public class PopupMenuMousePlugin<V, E> extends AbstractPopupGraphMousePlugin {
    private JPopupMenu linePopup, nodePopup;// customerPopup;
    
    /** Creates a new instance of PopupMenuMousePlugin */
    public PopupMenuMousePlugin() {
        this(MouseEvent.BUTTON3_MASK);
    }
    
    /**
     * Creates a new instance of PopupMenuMousePlugin
     * @param modifiers mouse event modifiers see the jung visualization Event class.
     */
    public PopupMenuMousePlugin(int modifiers) {
        super(modifiers);
    }
    
    /**
     * Implementation of the AbstractPopupGraphMousePlugin method. This is where the 
     * work gets done. You shouldn't have to modify unless you really want to...
     * @param e 
     */
    protected void handlePopup(MouseEvent e) {
        final VisualizationViewer<V,E> vv =
                (VisualizationViewer<V,E>)e.getSource();
        Point2D p = e.getPoint();
        
        GraphElementAccessor<V,E> pickSupport = vv.getPickSupport();
        if(pickSupport != null) {
            final V node = pickSupport.getVertex(vv.getGraphLayout(), p.getX(), p.getY());
            final E line = pickSupport.getEdge(vv.getGraphLayout(), p.getX(), p.getY());
            if(node != null) {
                updateNodeMenu(node, vv, p);
                nodePopup.show(vv, e.getX(), e.getY());
            } else {
            	if(line != null){
                    updateLineMenu(line, vv, p);
                    linePopup.show(vv, e.getX(), e.getY());
                } 
            }
//            else{
//                updateCustomerMenu(customer, vv, p);
//                customerPopup.show(vv, e.getX(), e.getY());
//                
//            }
        }
    }
    
    private void updateNodeMenu(V v, VisualizationViewer vv, Point2D point) {
        if (nodePopup == null) return;
        Component[] menuComps = nodePopup.getComponents();
        for (Component comp: menuComps) {
            if (comp instanceof NodeMenuListener) {
                ((NodeMenuListener)comp).setNodeAndView(v, vv);
            }
            if (comp instanceof MenuPointListener) {
                ((MenuPointListener)comp).setPoint(point);
            }
        }
        
    }
    
//    private void updateCustomerMenu(V c, VisualizationViewer vv, Point2D point) {
//        if (customerPopup == null) return;
//        Component[] menuComps = customerPopup.getComponents();
//        for (Component comp: menuComps) {
//            if (comp instanceof CustomerMenuListener) {
//                ((CustomerMenuListener)comp).setCustomerAndView(c, vv);
//            }
//            if (comp instanceof MenuPointListener) {
//                ((MenuPointListener)comp).setPoint(point);
//            }
//        }
//        
//    }
    
    public JPopupMenu getLinePopup() {
        return linePopup;
    }
    public void setLinePopup(JPopupMenu linePopup) {
        this.linePopup = linePopup;
    }
    
//    public JPopupMenu getCustomerPopup() {
//        return customerPopup;
//    }
//    public void setCustomerPopup(JPopupMenu cutsomerPopup) {
//        this.customerPopup = customerPopup;
//    }

    public JPopupMenu getNodePopup() {
        return nodePopup;
    }

    public void setNodePopup(JPopupMenu nodePopup) {
        this.nodePopup = nodePopup;
    }
    
    private void updateLineMenu(E edge, VisualizationViewer vv, Point2D point) {
        if (linePopup == null) return;
        Component[] menuComps = linePopup.getComponents();
        for (Component comp: menuComps) {
            if (comp instanceof LineMenuListener) {
                ((LineMenuListener)comp).setLineAndView(edge, vv);
            }
            if (comp instanceof MenuPointListener) {
                ((MenuPointListener)comp).setPoint(point);
            }
        }
    }
    
}