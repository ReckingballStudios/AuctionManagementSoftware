package front.customers.table;

import back.auctions.CurrentAuction;
import back.customers.Customer;
import controllers.main.CustomerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CustomersTable extends JTable {

    private JTable table = null;
    private CustomersTableModel customersTableModel = null;
    private SelectCustomerListener selectCustomerListener = null;

    public CustomersTable(int auctionID){
        super();
        super.setLayout(new BorderLayout());

        try {
            CustomerController.loadCustomersFromDatabase();
        }catch (Exception exception){
            JOptionPane.showMessageDialog(CustomersTable.this, "Failed to Connect to Database!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        this.customersTableModel = new CustomersTableModel();
        if(auctionID == CurrentAuction.ALL_AUCTIONS)
            this.customersTableModel.setCustomers(CustomerController.getCustomers());
        else if(auctionID == CurrentAuction.getAuctionID())
            this.customersTableModel.setCustomers(CustomerController.getCustomersFromCurrentAuction());
        else
            this.customersTableModel.setCustomers(CustomerController.getCustomers());

        this.table = new JTable(this.customersTableModel);
        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            setSelectedCustomer();
            }
        });
        super.add(new JScrollPane(this.table), BorderLayout.CENTER);
    }
    private void setSelectedCustomer(){
        Customer customer = CustomerController.getCustomers().get(table.getSelectedRow());
        SelectCustomerEvent selectCustomerEvent = new SelectCustomerEvent(this, customer);

        if(this.selectCustomerListener != null)
            this.selectCustomerListener.selectCustomerEventOccurred(selectCustomerEvent);
    }
    public void setCustomersTable(int auctionID){
        if(CurrentAuction.getAuction() != null){
            if(CurrentAuction.getAuctionID() == auctionID){
                //Only show customers who are in the current auction
                this.customersTableModel.setCustomers(CustomerController.getCustomersFromCurrentAuction());
            } else if(auctionID == CurrentAuction.ALL_AUCTIONS){
                this.customersTableModel.setCustomers(CustomerController.getCustomers());
            } else {
                this.customersTableModel.setCustomers(CustomerController.getCustomers());
            }
            refresh();
        }
    }

    ////////////SEARCH ALGORITHMS///////////////////////
    public void searchFor(String text, String aspect){
        List<Customer> customers = CustomerController.getCustomers();
        String compare = "";
        String compare2 = "";
        int numCustomersSelected = 0;
        for(int i = 0; i < customers.size(); i ++){
            if(text.length() <= 0) {
                this.table.removeRowSelectionInterval(i, i);
                continue;
            }
            compare2 = "";

            if(aspect.equalsIgnoreCase("ID"))
                compare = Integer.toString(customers.get(i).getId());
            else if(aspect.equalsIgnoreCase("Last Name"))
                compare = customers.get(i).getLastName();
            else if(aspect.equalsIgnoreCase("First Name"))
                compare = customers.get(i).getFirstName();
            else if(aspect.equalsIgnoreCase("Company"))
                compare = customers.get(i).getCompany();
            else if(aspect.equalsIgnoreCase("Phone")) {
                compare = customers.get(i).getPhoneNumber();
                compare2 = customers.get(i).getPhoneNumber2();
            }else if(aspect.equalsIgnoreCase("Email"))
                compare = customers.get(i).getEmail();
            else if(aspect.equalsIgnoreCase("State/Prov"))
                compare = customers.get(i).getState();
            else if(aspect.equalsIgnoreCase("City"))
                compare = customers.get(i).getCity();
            else if(aspect.equalsIgnoreCase("Address"))
                compare = customers.get(i).getAddress();
            else if(aspect.equalsIgnoreCase("Drivers Lic"))
                compare = customers.get(i).getDriversLicense();
            else if(aspect.equalsIgnoreCase("Tax Number"))
                compare = customers.get(i).getTaxNumber();
            else if(aspect.equalsIgnoreCase("Auction Bid #"))
                compare = customers.get(i).getAuctionBiddingID();
            else if(aspect.equalsIgnoreCase("Online Bid #"))
                compare = customers.get(i).getOnlineBiddingID();


            compare = constrictStringToText(compare, text);
            compare2 = constrictStringToText(compare2, text);
            if (text.equalsIgnoreCase(compare) || text.equalsIgnoreCase(compare2)) {
                Customer customer = customers.get(i);
                customers.remove(customer);
                customers.add(numCustomersSelected, customer);
                this.table.addRowSelectionInterval(numCustomersSelected, numCustomersSelected);
                numCustomersSelected ++;
            } else {
                this.table.removeRowSelectionInterval(i, i);
            }
        }
        if(customers.size() > numCustomersSelected + 1)
            this.table.removeRowSelectionInterval(numCustomersSelected+1, customers.size()-1);
    }
    public void searchAllFor(String text){
        List<Customer> customers = CustomerController.getCustomers();
        String cusID;
        String last;
        String first;
        String company;
        String phone;
        String phone2;
        String email;
        String country;
        String state;
        String city;
        String address;
        String driversLicense;
        String taxNumber;
        String auctionID;
        String onlineID;
        int numCustomersSelected = 0;
        for(int i = 0; i < customers.size(); i ++) {
            //Clear the table if nothing is written
            if(text.length() <= 0) {
                this.table.removeRowSelectionInterval(i, i);
                continue;
            }
            cusID = Integer.toString(customers.get(i).getId());
            cusID = constrictStringToText(cusID, text);
            last = customers.get(i).getLastName();
            last = constrictStringToText(last, text);
            first = customers.get(i).getFirstName();
            first = constrictStringToText(first, text);
            company = customers.get(i).getCompany();
            company = constrictStringToText(company, text);
            phone = customers.get(i).getPhoneNumber();
            phone = constrictStringToText(phone, text);
            phone2 = customers.get(i).getPhoneNumber2();
            phone2 = constrictStringToText(phone2, text);
            email = customers.get(i).getEmail();
            email = constrictStringToText(email, text);
            country = customers.get(i).getCountry();
            country = constrictStringToText(country, text);
            state = customers.get(i).getState();
            state = constrictStringToText(state, text);
            city = customers.get(i).getCity();
            city = constrictStringToText(city, text);
            address = customers.get(i).getAddress();
            address = constrictStringToText(address, text);
            driversLicense = customers.get(i).getDriversLicense();
            driversLicense = constrictStringToText(driversLicense, text);
            taxNumber = customers.get(i).getTaxNumber();
            taxNumber = constrictStringToText(taxNumber, text);
            auctionID = customers.get(i).getAuctionBiddingID();
            auctionID = constrictStringToText(auctionID, text);
            onlineID = customers.get(i).getOnlineBiddingID();
            onlineID = constrictStringToText(onlineID, text);

            if
            (
                       text.equalsIgnoreCase(cusID)
                    || text.equalsIgnoreCase(last)
                    || text.equalsIgnoreCase(first)
                    || text.equalsIgnoreCase(company)
                    || text.equalsIgnoreCase(phone)
                    || text.equalsIgnoreCase(phone2)
                    || text.equalsIgnoreCase(email)
                    || text.equalsIgnoreCase(country)
                    || text.equalsIgnoreCase(state)
                    || text.equalsIgnoreCase(city)
                    || text.equalsIgnoreCase(address)
                    || text.equalsIgnoreCase(driversLicense)
                    || text.equalsIgnoreCase(taxNumber)
                    || text.equalsIgnoreCase(auctionID)
                    || text.equalsIgnoreCase(onlineID))
            {
                Customer customer = customers.get(i);
                customers.remove(customer);
                customers.add(numCustomersSelected, customer);
                this.table.addRowSelectionInterval(numCustomersSelected, numCustomersSelected);
                numCustomersSelected ++;
            } else {
                this.table.removeRowSelectionInterval(i, i);
            }
        }

        //If your search happens to select all items it would break without the if statement
        if(customers.size() > numCustomersSelected + 1)
            this.table.removeRowSelectionInterval(numCustomersSelected+1, customers.size()-1);
    }
    private String constrictStringToText(String customerString, String text){
        if(customerString.length() > text.length())
            return customerString.substring(0, text.length());
        else
            return customerString;
    }

    ////////////UTILITIES//////////////////////
    public void scrollToTop(){
        this.table.scrollRectToVisible(table.getCellRect(0, 0, true));
    }
    public void scrollToBottom(){
        this.table.scrollRectToVisible(table.getCellRect(CustomerController.getCustomers().size(), 0, true));
    }
    public void refresh(){
        this.customersTableModel.fireTableDataChanged();
    }


    public void setSelectCustomerListener (SelectCustomerListener listener){
        this.selectCustomerListener = listener;
    }
}
