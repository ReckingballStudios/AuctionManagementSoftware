package front.customers.edit;

import front.customers.add.AddCustomerEvent;

public interface EditCustomerListener {
    public void customerEventOccurred(AddCustomerEvent editCustomerEvent);
}
