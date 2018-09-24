package topo.test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import topo.repository.Line;
import topo.repository.LineBase;

public class TestLineBase{
	final static Logger logger = Logger.getLogger(TestLineBase.class);
	public static void main(String args[]){
		LineBase lb = new LineBase();
		for(Line l : lb.getLineList()){
			logger.info(l);
		}
		logger.info("on the line N26, N25?: "+lb.getLine("N25", "N26"));
		logger.info("Lines connected to N21?: "+lb.getLines("N21"));
		Object l1 = new Line("l21", "N21", "N22");
		Object l2 = new Line("l22", "N22", "N21");
		logger.info("Comparing lines: \n"+l1+"\n"+l2+"\n"+l2.equals(l1));
		List<Object> lines = new ArrayList<>();
		lines.add(l1);
		lines.add(l2);
		logger.info("Distinct line list: "+lines.stream().distinct().collect(Collectors.toList()));
		
	}
	
}