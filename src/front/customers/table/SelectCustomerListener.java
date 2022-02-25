package front.customers.table;

import front.customers.add.AddCustomerEvent;

public interface SelectCustomerListener {
    void selectCustomerEventOccurred(SelectCustomerEvent selectCustomerEvent);
}
