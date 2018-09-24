package topo.graphic2;

import java.awt.Color;
import java.awt.Paint;

import org.apache.commons.collections15.Transformer;

public class NodeBorderTransformer<V, E> implements Transformer<String, Paint>{
    private final Paint[] styles = {Color.BLUE, Color.WHITE };

    @Override
    public Paint transform(String s) {
        if(s.startsWith("C")){
            return styles[0];
        }else if(s.startsWith("N")){
            return styles[1];
        }
        return styles[0];
    }
    
}