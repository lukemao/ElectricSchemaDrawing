package topo.graphic2;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import org.apache.commons.collections15.Transformer;

public class NodeShapeTransformer<V, E> implements Transformer<String, Shape>{
    private final Shape[] styles = {        
            new Ellipse2D.Double(-10, -10, 20, 20),
            new Polygon(new int[] {10, 20, 30}, new int[] {100, 20, 100}, 3),
            new Ellipse2D.Double(-10, -10, 20, 20),
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
	
}