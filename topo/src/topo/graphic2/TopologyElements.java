package topo.graphic2;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections15.Factory;

import topo.repository.Customer;
import topo.repository.CustomerBase;
import topo.repository.Line;
import topo.repository.LineBase;
import topo.repository.NodeBase;

/**
 *
 * @author Lu Mao
 */
public class TopologyElements {
    
    /** Creates a new instance of GraphElements */
    List<String> nodes = new ArrayList<>();
    List<Line> lines = new ArrayList<>();
    List<Customer> customers = new ArrayList<>();
	
    public TopologyElements() {
    }
	
    public TopologyElements(NodeBase nodes, LineBase lines, CustomerBase customers) {
        this.nodes = nodes.getNodeList();
        this.lines = lines.getLineList();
        this.customers = customers.getCustomerList();
    }
    
    public static class NodeFactory implements Factory<String> {
        private static int nodeCount = 31;
        private static NodeFactory instance = new NodeFactory();
        
        private NodeFactory() {            
        }
        
        public static NodeFactory getInstance() {
            return instance;
        }
        
        public String create() {
            String name = "N" + nodeCount++;
            return name;
        }
        
        public void setNodeCount(int nodeCount){
            this.nodeCount = nodeCount;
        }
    }
    
    public static class LineFactory implements Factory<Line> {
        private static int lineCount = 33;
        private static int sourceCount = 0;
        private static int targetCount = 0;

		private static double defaultWeight;
        private static double defaultCapacity;

        private static LineFactory instance = new LineFactory();
        
        private LineFactory() {            
        }
        
        public static LineFactory getInstance() {
            return instance;
        }
        
        public Line create() {
            String name = "L" + lineCount++;
            String source = "N" + sourceCount++;
            String target = "N" + targetCount++;
            Line l = new Line(name, source, target);
            l.setWeight(defaultWeight);
            l.setCapacity(defaultCapacity);
            return l;
        }    

        public void setLineCount(int count){
            this.lineCount = count;
        }
        
        public static void setSourceCount(int sourceCount) {
			LineFactory.sourceCount = sourceCount;
		}

		public static void setTargetCount(int targetCount) {
			LineFactory.targetCount = targetCount;
		}
        
        public static double getDefaultWeight() {
            return defaultWeight;
        }

        public static void setDefaultWeight(double aDefaultWeight) {
            defaultWeight = aDefaultWeight;
        }

        public static double getDefaultCapacity() {
            return defaultCapacity;
        }

        public static void setDefaultCapacity(double aDefaultCapacity) {
            defaultCapacity = aDefaultCapacity;
        }
        
    }

    public static class CustomerFactory implements Factory<Customer> {
        private static int customerCount = 0;
        private static CustomerFactory instance = new CustomerFactory();
        
        private CustomerFactory() {            
        }
        
        public static CustomerFactory getInstance() {
            return instance;
        }
        
        public Customer create() {
            String name = "C" + customerCount++;
            String node = "N" + customerCount++;
            return new Customer(name, node);
        }
    }
}