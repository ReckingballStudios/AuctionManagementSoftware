package front.customers.table;

import back.customers.Customer;

import java.util.EventObject;

public class SelectCustomerEvent extends EventObject {

    private Customer customer = null;

    public SelectCustomerEvent(Object source, Customer customer) {
        super(source);

        this.customer = customer;
    }


    public Customer getCustomer(){
        return customer;
    }
}
