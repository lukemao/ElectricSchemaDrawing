package topo.test;

import org.apache.log4j.Logger;

import topo.repository.Customer;
import topo.repository.CustomerBase;

public class TestCustomerBase{
	final static Logger logger = Logger.getLogger(TestCustomerBase.class);
	public static void main(String args[]){
		CustomerBase cb = new CustomerBase();
		for(Customer c : cb.getCustomerList()){
			logger.info(c);
		}
		logger.info("on the line C4?: "+cb.getCustomer("C4"));
	}
	
}