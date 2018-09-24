package topo.test;

import org.apache.log4j.Logger;

import topo.repository.NodeBase;

public class TestNodeBase{
	final static Logger logger = Logger.getLogger(TestNodeBase.class);
	public static void main(String args[]){
		NodeBase nb = new NodeBase();
		logger.info(nb.getNodeList());
		logger.info("contains N15?: "+nb.hasNode("N15"));
		logger.info("contains N55?: "+nb.hasNode("N55"));
	}
	
}