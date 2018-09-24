package topo.graphic2;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import topo.repository.Line;

import edu.uci.ics.jung.visualization.VisualizationViewer;

public class OptionMenus {
    
    public static class EdgeMenu extends JPopupMenu {        
        // private JFrame frame; 
        public EdgeMenu(final JFrame frame) {
            super("Edge Menu");
            // this.frame = frame;
            this.add(new DeleteLineMenuItem<Line>());
            this.addSeparator();
            this.add(new WeightDisplay());
            this.add(new CapacityDisplay());
            this.addSeparator();
            this.add(new LinePropItem(frame));           
        }
        
    }
    
    public static class LinePropItem extends JMenuItem implements LineMenuListener<Line>,
            MenuPointListener {
        Line edge;
        VisualizationViewer visComp;
        Point2D point;
        
        public void setLineAndView(Line edge, VisualizationViewer visComp) {
            this.edge = edge;
            this.visComp = visComp;
        }

        public void setPoint(Point2D point) {
            this.point = point;
        }
        
        public LinePropItem(final JFrame frame) {            
            super("Edit Line Properties...");
            this.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    LinePropertyDialog dialog = new LinePropertyDialog(frame, edge);
                    dialog.setLocation((int)point.getX()+ frame.getX(), (int)point.getY()+ frame.getY());
                    dialog.setVisible(true);
                }
                
            });
        }
        
    }
    public static class WeightDisplay extends JMenuItem implements LineMenuListener<Line> {
        public void setLineAndView(Line e, VisualizationViewer visComp) {
            this.setText("Weight " + e.getId() + " = " + e.getWeight());
        }
    }
    
    public static class CapacityDisplay extends JMenuItem implements LineMenuListener<Line> {
        public void setLineAndView(Line e, VisualizationViewer visComp) {
            this.setText("Capacity " + e.getId() + " = " + e.getCapacity());
        }
    }
    
    public static class NodeMenu extends JPopupMenu {
        public NodeMenu() {
            super("Node Menu");
            this.add(new DeleteNodeMenuItem<String>());
        }
    }
    
}