package topo.repository;

import org.apache.log4j.Logger;

public class Customer{
	final static Logger logger = Logger.getLogger(Customer.class);

	private final String name;
    private final String node;

	public Customer(String name, String node) {
		this.name = name;
		this.node = node;
	}
	
	public String getCustomer(String node){
	    return node.equals(node)?name:null;
	}

	public String getName() {
		return name;
	}
	
    public String getNode() {
		return node;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((node == null) ? 0 : node.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Customer))
			return false;
		Customer other = (Customer) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (node == null) {
			if (other.node != null)
				return false;
		} else if (!node.equals(other.node))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Customer [name=" + name + ", node=" + node + "]";
	}
}