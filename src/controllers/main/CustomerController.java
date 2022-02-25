package controllers.main;

import back.customers.Customer;
import back.customers.CustomerData;
import front.customers.add.AddCustomerEvent;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {

    private static CustomerData customerData = new CustomerData();

    public static List<Customer> getCustomers(){
        return customerData.getCustomers();
    }
    public static List<Customer> getCustomersFromCurrentAuction(){
        List<Customer> answer = new ArrayList<>();
        for(Customer customer : customerData.getCustomers())
            if(!customer.getAuctionBiddingID().equals(""))
                answer.add(customer);

        return answer;
    }

    public static void addCustomer(AddCustomerEvent addCustomerEvent) throws SQLException {
        try {
            loadCustomersFromDatabase();
        } catch(Exception exception){
            JOptionPane.showMessageDialog(null, "Failed to Connect to Database!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        customerData.addCustomer(getCustomerFromEvent(addCustomerEvent));
    }
    public static void editCustomer(AddCustomerEvent editCustomerEvent) throws SQLException{
        try {
            loadCustomersFromDatabase();
        } catch(Exception exception){
            JOptionPane.showMessageDialog(null, "Failed to Connect to Database!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        customerData.editCustomer(getCustomerFromEvent(editCustomerEvent));
    }
    public static void deleteCustomer(int id){
        customerData.deleteCustomer(id);
    }
    private static Customer getCustomerFromEvent(AddCustomerEvent event){
        int id = event.getId();
        String lName = event.getLastName();
        String fName = event.getFirstName();
        String company = event.getCompany();
        String phone = event.getPhoneNumber();
        String phone2 = event.getPhoneNumber2();
        String email = event.getEmail();
        String country = event.getCountry();
        String state = event.getState();
        String city = event.getCity();
        String adr = event.getAddress();
        String zip = event.getZipCode();
        String dl = event.getDriversLicense();
        String taxNum = event.getTaxNumber();
        String offBid = event.getAuctionBiddingID();
        String onBid = event.getOnlineBiddingID();
        return new Customer(id, lName, fName, company, phone, phone2, email, country,
                state, city, adr, zip, dl,taxNum, offBid, onBid);
    }

    public static void saveCustomersToFile(File file) throws IOException{
        customerData.saveToFile(file);
    }
    public static void loadCustomersFromFile(File file) throws IOException, ClassNotFoundException{
        customerData.loadFromFile(file);
    }

    public static void loadCustomersFromDatabase() throws Exception{
        customerData.loadFromDatabase();
    }

    public static void refreshCustomersFromDatabase() throws Exception{

    }

}
