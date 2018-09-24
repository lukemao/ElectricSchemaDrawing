package topo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import topo.adaptors.CsvAdapter;

public class CustomerBase{
	final static Logger logger = Logger.getLogger(CustomerBase.class);
	
    private final ArrayList<Customer> customerList;
    private final CsvAdapter customerAdapter;

    
    public CustomerBase(){
        this("C:\\Users\\lukemao\\workspace\\topo\\resources");
    }
    
    public CustomerBase(String path){
        customerList = new ArrayList<>();
        customerAdapter = new CsvAdapter(path, "customer.csv");
        populateCustomerList();
    }
    
    private final void populateCustomerList(){
    	try{
            while(customerAdapter.hasNext()){
                customerAdapter.readNextLine();
                ArrayList<String> fields = new ArrayList<>();
                while(customerAdapter.hasNextField()){
                    fields.add(customerAdapter.readNextField());
                }
                if(fields.size()!=2){
                    throw new Exception("Invalid customer: "+fields);
                }
                customerList.add(new Customer(fields.get(0), fields.get(1)));
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
    }

    
    public ArrayList<Customer> getCustomerList(){
        return this.customerList;
    }
    
    public boolean hasCustomer(Customer c){
        return this.customerList.contains(c);
    }
    
    public Customer getCustomer(String name){
        for(Customer c: customerList){
            if(name.equals(c.getName())){
                return c;
            }
        }
        return null;
    }
    
    public Customer getCustomerByNode(String name){
        for(Customer c: customerList){
            if(name.equals(c.getNode())){
                return c;
            }
        }
        return null;
    }
    
    public List<Customer> getCustomersByNode(String name){
        return customerList.stream()
                           .filter(c -> c.getNode().equals(name))
                           .collect(Collectors.toList());
    }
}