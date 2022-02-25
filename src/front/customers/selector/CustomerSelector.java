package front.customers.selector;

import back.auctions.CurrentAuction;
import controllers.main.CustomerController;
import controllers.util.Database;
import controllers.util.Screen;
import front.customers.table.CustomersTable;
import front.customers.table.SelectCustomerEvent;
import front.customers.table.SelectCustomerListener;
import front.z_other.search.ChangeTableListener;
import front.z_other.search.SearchListener;
import front.z_other.search.SearchToolBar;

import javax.swing.*;
import java.awt.*;

public class CustomerSelector extends JFrame {
    private CustomerSelectedListener customerSelectedListener = null;

    private CustomersTable customersTable = null;
    private SearchToolBar searchToolBar = null;

    public CustomerSelector(String name){
        super(name);

        initializeFrame();
        initializeComponents();
        initializeListeners();
    }
    private void initializeFrame(){
        super.setLayout(new BorderLayout());
        super.setVisible(true);
        super.setSize(Screen.width(0.65f), Screen.height(0.65f));
        super.setMinimumSize(new Dimension(Screen.width(0.65f), Screen.height(0.65f)));
        Screen.backgroundColor = super.getBackground();
    }
    private void initializeComponents(){
        this.customersTable = new CustomersTable(CurrentAuction.ALL_AUCTIONS);
        this.searchToolBar = new SearchToolBar(SearchToolBar.SEARCH_SELECT_CUSTOMER, super.getWidth());

        super.add(this.customersTable, BorderLayout.CENTER);
        super.add(this.searchToolBar, BorderLayout.NORTH);
    }
    private void initializeListeners(){
        this.searchToolBar.setSearchListener(new SearchListener() {
            @Override
            public void searchEventOccurred(String text, String aspect) {
                //Stops Database updates during search
                Database.updatingFromDatabase = (text.length() <= 0);
                //Now update right away
                if(Database.updatingFromDatabase && Database.isOnline){
                    try {
                        CustomerController.loadCustomersFromDatabase();
                    }catch (Exception exception){
                        JOptionPane.showMessageDialog(CustomerSelector.this, "Failed to Connect to Database!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if(aspect.equalsIgnoreCase("All"))
                    customersTable.searchAllFor(text);
                else    //'All' is index 0, so we need to offset it to align the data points
                    customersTable.searchFor(text, aspect);

                customersTable.scrollToTop();
            }
        });
        this.customersTable.setSelectCustomerListener(new SelectCustomerListener() {
            @Override
            public void selectCustomerEventOccurred(SelectCustomerEvent selectCustomerEvent) {
                if(customerSelectedListener != null)
                    customerSelectedListener.CustomerSelectedEventOccurred(selectCustomerEvent.getCustomer().getId());
                exit();
            }
        });
        this.searchToolBar.setChangeTableListener(new ChangeTableListener() {
            @Override
            public void changeTableEventOccurred(int auctionID) {
                customersTable.setCustomersTable(auctionID);
            }
        });
    }

    private void exit(){
        super.dispose();
    }

    public void setCustomerSelectedListener(CustomerSelectedListener customerSelectedListener){
        this.customerSelectedListener = customerSelectedListener;
    }
}
