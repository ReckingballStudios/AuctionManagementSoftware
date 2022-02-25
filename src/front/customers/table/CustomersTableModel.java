package front.customers.table;

import back.customers.Customer;
import controllers.main.CustomerController;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CustomersTableModel extends AbstractTableModel {

    private List<Customer> customers = null;

    public void setCustomers(List<Customer> customers){
        this.customers = customers;
    }
    public List<Customer> getCustomers(){
        return this.customers;
    }

    @Override
    public String getColumnName(int column) {
        return Customer.ASPECT_NAME[column];
    }
    @Override
    public int getRowCount() {
        return this.customers.size();
    }
    @Override
    public int getColumnCount() {
        //To include the number of purchases and num sales: Delete the -2
        return Customer.ASPECT_NAME.length;
    }
    @Override
    public Object getValueAt(int row, int column) {
        Customer customer = this.customers.get(row);
        switch(column){
            case 0:
                return customer.getId();
            case 1:
                return customer.getLastName();
            case 2:
                return customer.getFirstName();
            case 3:
                return customer.getCompany();
            case 4:
                return customer.getPhoneNumber();
            case 5:
                return customer.getEmail();
            case 6:
                return customer.getState();
            case 7:
                return customer.getCity();
            case 8:
                return customer.getAddress();
            case 9:
                return customer.getDriversLicense();
            case 10:
                return customer.getTaxNumber();
            case 11:
                return customer.getAuctionBiddingID();
            case 12:
                return customer.getOnlineBiddingID();
            default:
                return null;
        }
    }
}
