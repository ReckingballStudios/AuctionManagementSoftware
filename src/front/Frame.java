package front;

import back.auctions.CurrentAuction;
import back.customers.Customer;
import back.items.Item;
import back.users.CurrentUser;
import back.users.User;
import controllers.login.UserController;
import controllers.main.AuctionController;
import controllers.main.CustomerController;
import controllers.main.ItemController;
import controllers.util.Database;
import controllers.util.DatabaseListener;
import front.auctions.add.AddAuctionEvent;
import front.auctions.add.AddAuctionListener;
import front.auctions.add.AddAuctionPanel;
import front.auctions.table.AuctionsTable;
import front.auctions.table.SelectAuctionEvent;
import front.auctions.table.SelectAuctionListener;
import front.customers.add.AddCustomerEvent;
import front.customers.add.AddCustomerListener;
import front.customers.add.AddCustomerPanel;
import front.customers.edit.DeleteCustomerListener;
import front.customers.edit.EditCustomerListener;
import front.customers.edit.EditCustomerPanel;
import front.customers.table.CustomersTable;
import front.customers.table.SelectCustomerEvent;
import front.customers.table.SelectCustomerListener;
import front.items.add.AddItemEvent;
import front.items.add.AddItemListener;
import front.items.add.AddItemPanel;
import front.items.edit.EditItemListener;
import front.items.edit.EditItemPanel;
import front.items.image.ItemImagePanel;
import front.items.table.general.ItemsTable;
import front.items.table.SelectItemListener;
import front.items.table.SelectItemEvent;
import front.items.table.split.SplitItemTable;
import front.navigation.NavigateListener;
import front.navigation.NavigationPanel;
import front.z_other.search.ChangeTableListener;
import front.z_other.search.NavigateToMenuListener;
import front.z_other.search.SearchListener;
import front.z_other.search.SearchToolBar;
import front.users.add.AddUserEvent;
import front.users.add.AddUserListener;
import front.users.add.AddUserPanel;
import front.users.change_password.ChangePasswordListener;
import front.users.change_password.ChangePasswordPanel;
import front.users.edit.EditUserListener;
import front.users.edit.EditUserPanel;
import front.users.login.LoginPanel;
import controllers.util.Screen;
import front.users.login.LoginEvent;
import front.users.login.LoginListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.DriverManager;
import java.sql.SQLException;


@SuppressWarnings("MagicConstant")
public class Frame extends JFrame {

    ///////////GENERAL//////////////////////
    private JFileChooser fileChooser = null;
    private LoginPanel loginPanel = null;
    private NavigationPanel navigationPanel = null;

    ////////CUSTOMER PANELS//////////////////////////
    private CustomersTable customersTable = null;
    private AddCustomerPanel addCustomerPanel = null;
    private SearchToolBar searchCustomersTool = null;
    private EditCustomerPanel editCustomerPanel = null;
    private SplitItemTable customerItemsTable = null;

    ////////USER PANELS////////////////////////
    private AddUserPanel addUserPanel = null;
    private EditUserPanel editUserPanel = null;
    private ChangePasswordPanel changePasswordPanel = null;

    ////////ITEM PANELS//////////////////////////
    private AddItemPanel addItemPanel = null;
    private ItemsTable itemsTable = null;
    private SearchToolBar searchItemsTool = null;
    private EditItemPanel editItemPanel = null;
    private ItemImagePanel itemImagePanel = null;

    ////////AUCTION PANELS///////////////////////
    private AddAuctionPanel addAuctionPanel = null;
    private AuctionsTable auctionsTable = null;
    private SearchToolBar searchAuctionsTool = null;



    /////////////INITIALIZE//////////////////
    public Frame(){
        super("Resource Auction");
        initializeFrame();
        initializeDatabase();
        gotoLogin();
//        gotoAddCustomer();
//        gotoAddItem();
//        gotoNavigation();
    }
    private void initializeFrame(){
        super.setLayout(new BorderLayout());
        super.setVisible(true);
        super.setSize(Screen.width(), Screen.height());
        super.setMinimumSize(new Dimension(Screen.width(), Screen.height()));
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Screen.backgroundColor = super.getBackground();
        this.fileChooser = new JFileChooser(System.getProperty("user.dir") + "/data");
    }
    private void initializeDatabase(){
        Database database = new Database();
        database.setDatabaseListener(new DatabaseListener() {
            @Override
            public void databaseEventOccurred() {
                if(customersTable != null)
                    customersTable.refresh();
                if(itemsTable != null)
                    itemsTable.refresh();
            }
        });
        try {
            Database.connection = DriverManager.getConnection(Database.DATABASE, "root", Database.DB_PASSWORD);
        }catch (SQLException exception){
            JOptionPane.showMessageDialog(Frame.this, "Failed to Connect to Database!", "Error", JOptionPane.ERROR_MESSAGE);
            Database.isOnline = false;
            exception.printStackTrace();
        }
        try {
            AuctionController.loadAuctionsFromDatabase();
        } catch(Exception exception){
            exception.printStackTrace();
        }
    }
    private void initializeLogin(){
        this.loginPanel = new LoginPanel();
        this.loginPanel.setLoginListener(new LoginListener() {
            @Override
            public void loginEventOccurred(LoginEvent loginEvent) {
                String username = loginEvent.getUsername();
                String password = loginEvent.getPassword();
                if(UserController.attemptLogin(username, password)) {
                    gotoNavigation();
                } else {
                    String errorMessage = "Incorrect Username or Password";
                    JOptionPane.showMessageDialog
                            (Frame.this, errorMessage, "Login Failure", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
    private void initializeNavigation(){
        this.navigationPanel = new NavigationPanel();
        this.navigationPanel.setNavigateListener(new NavigateListener() {
            @Override
            public void NavigationEventOccurred(int navigateTo) {
                switch(navigateTo){
                    case NavigationPanel.ADD_CUSTOMER:
                        gotoAddCustomer();
                        break;
                    case NavigationPanel.ADD_ITEM:
                        gotoAddItem();
                        break;
                    case NavigationPanel.AUCTION:
                        gotoAddAuction();
                        break;
                    case NavigationPanel.CURRENT_AUCTION:
                        gotoCurrentAuction();
                        break;
                    case NavigationPanel.ADD_USER:
                        gotoAddUser();
                        break;
                    case NavigationPanel.EDIT_USER:
                        gotoEditUser();
                        break;
                    case NavigationPanel.CHANGE_PASSWORD:
                        gotoChangePassword();
                        break;
                    case NavigationPanel.LOG_OUT:
                        gotoLogin();
                        break;
                    case NavigationPanel.EXIT:
                        exit();
                        break;
                }
            }
        });
    }
    private void initializeAddAuction(){
        this.auctionsTable = new AuctionsTable();
        this.addAuctionPanel = new AddAuctionPanel();
        this.searchAuctionsTool = new SearchToolBar(SearchToolBar.SEARCH_AUCTIONS, super.getWidth());
        this.addAuctionPanel.setAddAuctionListener(new AddAuctionListener() {
            @Override
            public void addAuctionEventOccurred(AddAuctionEvent addAuctionEvent) {
                try{
                    int action = JOptionPane.showConfirmDialog(null,
                            "Please confirm that you would like to create a new auction.",
                            "Auction Creation Confirmation",
                            JOptionPane.OK_CANCEL_OPTION);
                    if(action == JOptionPane.OK_OPTION) {
                        AuctionController.addAuction(addAuctionEvent);
                        auctionsTable.refresh();
                        auctionsTable.scrollToTop();
                    }
                } catch(SQLException exception){
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(Frame.this, "Failed to add auction to database!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        this.auctionsTable.setSelectAuctionListener(new SelectAuctionListener() {
            @Override
            public void SelectAuctionEventOccurred(SelectAuctionEvent selectAuctionEvent) {
                gotoSpecificAuction(selectAuctionEvent.getAuction().getAuctionID());
            }
        });
        this.searchAuctionsTool.setNavigateToMenuListener(new NavigateToMenuListener() {
            @Override
            public void NavigateToMenuEventOccurred() {
                gotoNavigation();
            }
        });
        this.searchAuctionsTool.setSearchListener(new SearchListener() {
            @Override
            public void searchEventOccurred(String text, String aspect) {
                //Stops Database updates during search
                Database.updatingFromDatabase = (text.length() <= 0);
                //Now update right away
                if(Database.updatingFromDatabase && Database.isOnline){
                    try {
                        AuctionController.loadAuctionsFromDatabase();
                    }catch (Exception exception){
                        JOptionPane.showMessageDialog(Frame.this, "Failed to Connect to Database!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if(aspect.equalsIgnoreCase("All"))
                    auctionsTable.searchAllFor(text);
                else    //'All' is index 0, so we need to offset it to align the data points
                    auctionsTable.searchFor(text, aspect);

                auctionsTable.scrollToTop();
            }
        });
    }
    private void initializeAddCustomer(){
        this.customersTable = new CustomersTable(CurrentAuction.ALL_AUCTIONS);
        this.addCustomerPanel = new AddCustomerPanel();
        this.searchCustomersTool = new SearchToolBar(SearchToolBar.SEARCH_CUSTOMERS, super.getWidth());
        this.addCustomerPanel.setAddCustomerListener(new AddCustomerListener() {
            @Override
            public void customerEventOccurred(AddCustomerEvent addCustomerEvent) {
                try {
                    CustomerController.addCustomer(addCustomerEvent);
                    customersTable.refresh();
                    customersTable.scrollToBottom();
                } catch(SQLException exception){
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(Frame.this, "Failed to add customer to database!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        //When the user clicks a customer from the table it opens their profile
        this.customersTable.setSelectCustomerListener(new SelectCustomerListener() {
            @Override
            public void selectCustomerEventOccurred(SelectCustomerEvent selectCustomerEvent) {
                gotoEditCustomer(selectCustomerEvent.getCustomer());
            }
        });
        this.searchCustomersTool.setChangeTableListener(new ChangeTableListener() {
            @Override
            public void changeTableEventOccurred(int auctionID) {
                customersTable.setCustomersTable(auctionID);
            }
        });
        this.searchCustomersTool.setSearchListener(new SearchListener() {
            @Override
            public void searchEventOccurred(String text, String aspect) {
                //Stops Database updates during search
                Database.updatingFromDatabase = (text.length() <= 0);
                //Now update right away
                if(Database.updatingFromDatabase && Database.isOnline){
                    try {
                        CustomerController.loadCustomersFromDatabase();
                    }catch (Exception exception){
                        JOptionPane.showMessageDialog(Frame.this, "Failed to Connect to Database!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if(aspect.equalsIgnoreCase("All"))
                    customersTable.searchAllFor(text);
                else    //'All' is index 0, so we need to offset it to align the data points
                    customersTable.searchFor(text, aspect);

                customersTable.scrollToTop();
            }
        });
        this.searchCustomersTool.setNavigateToMenuListener(new NavigateToMenuListener() {
            @Override
            public void NavigateToMenuEventOccurred() {
                gotoNavigation();
            }
        });
    }
    private void initializeEditCustomer(Customer customer){
        this.editCustomerPanel = new EditCustomerPanel(customer);
        this.editCustomerPanel.setEditCustomerListener(new EditCustomerListener() {
            @Override
            public void customerEventOccurred(AddCustomerEvent editCustomerEvent) {
                try {
                    CustomerController.editCustomer(editCustomerEvent);
                } catch(SQLException e){
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(Frame.this, "Failed to update customer to database!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                customersTable.refresh();
                gotoAddCustomer();
            }
        });
        this.editCustomerPanel.setDeleteCustomerListener(new DeleteCustomerListener() {
            @Override
            public void deleteCustomerEventOccurred(int id) {
                CustomerController.deleteCustomer(id);
                customersTable.refresh();
                gotoAddCustomer();
            }
        });
        this.customerItemsTable = new SplitItemTable(customer.getId());
        this.customerItemsTable.setSelectItemListener(new SelectItemListener() {
            @Override
            public void selectItemEventOccurred(SelectItemEvent selectItemEvent) {
                gotoEditItem(selectItemEvent.getItem());
            }
        });
        this.searchItemsTool = new SearchToolBar(SearchToolBar.SEARCH_ITEMS, super.getWidth());
        this.searchItemsTool.setNavigateToMenuListener(new NavigateToMenuListener() {
            @Override
            public void NavigateToMenuEventOccurred() {
                gotoNavigation();
            }
        });
        this.searchItemsTool.setSearchListener(new SearchListener() {
            @Override
            public void searchEventOccurred(String text, String aspect) {
                Database.updatingFromDatabase = (text.length() <= 0);

                //Update right away if there is no text
                if(Database.updatingFromDatabase && Database.isOnline){
                    try {
                        ItemController.loadItemsFromDatabase();
                    }catch (Exception exception){
                        JOptionPane.showMessageDialog(Frame.this, "Failed to Connect to Database!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if(aspect.equals("All"))
                    customerItemsTable.searchAllFor(text);
                else
                    customerItemsTable.searchFor(text, aspect);

                customerItemsTable.scrollToTop();
            }
        });
    }
    private void initializeAddUser(){
        this.addUserPanel = new AddUserPanel();
        this.addUserPanel.setAddUserListener(new AddUserListener() {
            @Override
            public void userEventOccurred(AddUserEvent event) {
                try {
                    UserController.addUser(event.getUsername(), event.getPassword(), event.getAuthority());
                    gotoAddCustomer();
                    JOptionPane.showMessageDialog
                            (Frame.this, "User has been successfully added.",
                                    "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch(SQLException exception){
                    JOptionPane.showMessageDialog(Frame.this, "Failed to Upload User to Database!", "Error", JOptionPane.ERROR_MESSAGE);
                    exception.printStackTrace();
                }
            }
        });
    }
    private void initializeEditUser(){
        this.editUserPanel = new EditUserPanel();
        this.editUserPanel.setEditUserListener(new EditUserListener() {
            @Override
            public void userEventOccurred(AddUserEvent event) {
                try {
                    UserController.editUser(event.getUsername(), event.getPassword(), event.getAuthority());
                    gotoAddCustomer();
                    JOptionPane.showMessageDialog
                            (Frame.this, "User has been successfully edited.",
                                    "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch(SQLException exception){
                    JOptionPane.showMessageDialog(Frame.this, "Failed to Upload Edits to Database!", "Error", JOptionPane.ERROR_MESSAGE);
                    exception.printStackTrace();
                }
            }
        });
    }
    private void initializeAddItem(){
        this.addItemPanel = new AddItemPanel();
        this.itemsTable = new ItemsTable(CurrentAuction.ALL_AUCTIONS);
        this.searchItemsTool = new SearchToolBar(SearchToolBar.SEARCH_ITEMS, super.getWidth());
        this.addItemPanel.setAddItemListener(new AddItemListener() {
            @Override
            public void AddItemEventOccurred(AddItemEvent addItemEvent) {
                try {
                    ItemController.addItem(addItemEvent);
                } catch(SQLException exception){
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(Frame.this, "Failed to add item to database!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                itemsTable.refresh();
            }
        });
        initializeItemListeners();
    }
    private void initializeSpecificAuction(int auctionID){
        this.itemsTable = new ItemsTable(auctionID);
        this.searchItemsTool = new SearchToolBar(SearchToolBar.SEARCH_ITEMS_FROM_AUCTION, super.getWidth());
        initializeItemListeners();
    }
    private void initializeEditItem(Item item){
        this.editItemPanel = new EditItemPanel(item);
        this.itemsTable = new ItemsTable(CurrentAuction.ALL_AUCTIONS);
        this.searchItemsTool = new SearchToolBar(SearchToolBar.SEARCH_ITEMS, super.getWidth());
        this.itemImagePanel = new ItemImagePanel();
        this.editItemPanel.setEditItemListener(new EditItemListener() {
            @Override
            public void editItemEventOccurred(AddItemEvent editItemEvent) {
                try{
                    ItemController.editItem(editItemEvent);
                } catch(Exception exception){
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(Frame.this, "Failed to make changes in database!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                itemsTable.refresh();
                gotoAddItem();
            }
        });
        initializeItemListeners();
    }
    private void initializeItemListeners(){
        this.itemsTable.setSelectItemListener(new SelectItemListener() {
            @Override
            public void selectItemEventOccurred(SelectItemEvent selectItemEvent) {
                gotoEditItem(selectItemEvent.getItem());
            }
        });
        this.searchItemsTool.setSearchListener(new SearchListener() {
            @Override
            public void searchEventOccurred(String text, String aspect) {
                //Stops Database updates during search
                Database.updatingFromDatabase = (text.length() <= 0);

                //Now update right away
                if(Database.updatingFromDatabase && Database.isOnline){
                    try {
                        ItemController.loadItemsFromDatabase();
                    } catch (Exception exception){
                        JOptionPane.showMessageDialog(Frame.this, "Failed to Connect to Database!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if(aspect.equals("All"))
                    itemsTable.searchAllFor(text);
                else
                    itemsTable.searchFor(text, aspect);

                itemsTable.scrollToTop();
            }
        });
        this.searchItemsTool.setChangeTableListener(new ChangeTableListener() {
            @Override
            public void changeTableEventOccurred(int auctionID) {
                itemsTable.setItemsTable(auctionID);
            }
        });
        this.searchItemsTool.setNavigateToMenuListener(new NavigateToMenuListener() {
            @Override
            public void NavigateToMenuEventOccurred() {
                gotoNavigation();
            }
        });
    }
    private void initializeChangePassword(){
        this.changePasswordPanel = new ChangePasswordPanel();
        this.changePasswordPanel.setChangePasswordListener(new ChangePasswordListener() {
            @Override
            public void changePasswordEvent(String password) {
                JOptionPane.showMessageDialog
                        (Frame.this, "Password has been reset!",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                gotoAddCustomer();
            }
        });
    }

    /////////////GOTO FUNCTIONS///////////
    private void gotoLogin(){
        super.getContentPane().removeAll();
        initializeLogin();
        super.add(this.loginPanel, BorderLayout.CENTER);
        super.setJMenuBar(loginMenuBar());
        super.getContentPane().repaint();
        super.validate();
    }
    private void gotoNavigation(){
        super.getContentPane().removeAll();
        initializeNavigation();
        super.add(this.navigationPanel, BorderLayout.CENTER);
        super.setJMenuBar(mainMenuBar());
        super.getContentPane().repaint();
        super.validate();
    }
    private void gotoAddAuction(){
        super.getContentPane().removeAll();
        initializeAddAuction();
        if(CurrentAuction.getAuction() == null)
            super.add(this.addAuctionPanel, BorderLayout.WEST);
        super.add(this.auctionsTable, BorderLayout.CENTER);
        super.add(this.searchAuctionsTool, BorderLayout.NORTH);
        super.setJMenuBar(mainMenuBar());
        super.getContentPane().repaint();
        super.validate();
    }
    private void gotoCurrentAuction(){
        if(CurrentAuction.getAuction() == null){
            String errorMessage = "There is currently no on-going auction.";
            JOptionPane.showMessageDialog(null, errorMessage, "No Current Auction", JOptionPane.INFORMATION_MESSAGE);
        } else{
            gotoSpecificAuction(CurrentAuction.getAuctionID());
        }
    }
    private void gotoSpecificAuction(int auctionID){
        super.getContentPane().removeAll();
        initializeSpecificAuction(auctionID);
        super.add(this.itemsTable, BorderLayout.CENTER);
        super.add(this.searchItemsTool, BorderLayout.NORTH);
        super.setJMenuBar(mainMenuBar());
        super.getContentPane().repaint();
        super.validate();
    }
    private void gotoAddCustomer(){
        super.getContentPane().removeAll();
        initializeAddCustomer();
        super.add(this.searchCustomersTool, BorderLayout.NORTH);
        super.add(this.customersTable, BorderLayout.CENTER);
        super.add(this.addCustomerPanel, BorderLayout.WEST);
        super.setJMenuBar(mainMenuBar());
        super.getContentPane().repaint();
        super.validate();
    }
    private void gotoEditCustomer(Customer customer){
        super.getContentPane().removeAll();
        initializeEditCustomer(customer);
        super.add(this.editCustomerPanel, BorderLayout.WEST);
        super.add(this.customerItemsTable, BorderLayout.CENTER);
        super.add(this.searchItemsTool, BorderLayout.NORTH);
        super.setJMenuBar(mainMenuBar());
        super.getContentPane().repaint();
        super.validate();
    }
    private void gotoAddUser(){
        super.getContentPane().removeAll();
        initializeAddUser();
        super.add(this.addUserPanel, BorderLayout.CENTER);
        super.setJMenuBar(mainMenuBar());
        super.getContentPane().repaint();
        super.validate();
    }
    private void gotoEditUser(){
        super.getContentPane().removeAll();
        initializeEditUser();
        super.add(this.editUserPanel, BorderLayout.CENTER);
        super.setJMenuBar(mainMenuBar());
        super.getContentPane().repaint();
        super.validate();
    }
    private void gotoAddItem(){
        super.getContentPane().removeAll();
        initializeAddItem();
        super.add(this.addItemPanel, BorderLayout.WEST);
        super.add(this.itemsTable, BorderLayout.CENTER);
        super.add(this.searchItemsTool, BorderLayout.NORTH);
        super.setJMenuBar(mainMenuBar());
        super.getContentPane().repaint();
        super.validate();
    }
    private void gotoEditItem(Item item){
        super.getContentPane().removeAll();
        initializeEditItem(item);
        super.add(this.editItemPanel, BorderLayout.WEST);
        super.add(this.itemsTable, BorderLayout.CENTER);
        super.add(this.itemImagePanel, BorderLayout.EAST);
        super.add(this.searchItemsTool, BorderLayout.NORTH);
        super.setJMenuBar(mainMenuBar());
        super.getContentPane().repaint();
        super.validate();
    }
    private void gotoChangePassword(){
        super.getContentPane().removeAll();
        initializeChangePassword();
        super.add(this.changePasswordPanel, BorderLayout.CENTER);
        super.setJMenuBar(mainMenuBar());
        super.getContentPane().repaint();
        super.validate();
    }
    private void minimizeWestPanels(){
        if(this.addCustomerPanel != null)
            super.getContentPane().remove(this.addCustomerPanel);
        if(this.addItemPanel != null)
            super.getContentPane().remove(this.addItemPanel);
        if(this.editItemPanel != null)
            super.getContentPane().remove(this.editItemPanel);
        if(this.itemImagePanel != null)
            super.getContentPane().remove(this.itemImagePanel);
//        if(this.editCustomerPanel != null)
//            super.getContentPane().remove(this.editCustomerPanel);
        if(this.addAuctionPanel != null)
            super.getContentPane().remove(this.addAuctionPanel);
        super.getContentPane().repaint();
        super.validate();
    }


    ////////MENU BARS//////////////////////////
    private JMenuBar loginMenuBar(){
        JMenuBar menuBar = basicMenuBar(this);

        return menuBar;
    }
    private JMenuBar mainMenuBar(){
        JMenuBar menuBar = getFileMenu();
        menuBar.add(getNavigateMenu());
        menuBar.add(getManageMenu());

        return menuBar;
    }
    private JMenuBar basicMenuBar(final Frame frame){
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.exit();
            }
        });

        fileMenu.setMnemonic(KeyEvent.VK_F);
        exitItem.setMnemonic(KeyEvent.VK_X);
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        return menuBar;
    }
    private JMenuBar getFileMenu(){
        JMenuBar menuBar = basicMenuBar(this);

        JMenuItem logoutItem = new JMenuItem("Log Out");
        menuBar.getMenu(0).insert(logoutItem, 0);
        logoutItem.setMnemonic(KeyEvent.VK_L);
        logoutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                gotoLogin();
            }
        });

        JMenuItem importDatabaseItem = new JMenuItem("Import From Database");
        importDatabaseItem.setMnemonic(KeyEvent.VK_I);
        importDatabaseItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        menuBar.getMenu(0).insert(importDatabaseItem, 0);
        importDatabaseItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try{
                    CustomerController.loadCustomersFromDatabase();
                    ItemController.loadItemsFromDatabase();
                    if(customersTable != null)
                        customersTable.refresh();
                    if(itemsTable != null)
                        itemsTable.refresh();
                } catch(Exception e){
                    JOptionPane.showMessageDialog(Frame.this, "Failed to connect to database!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JMenuItem importItem = new JMenuItem("Import Customer Data");
        importDatabaseItem.setMnemonic(KeyEvent.VK_C);
        menuBar.getMenu(0).insert(importItem, 1);
        importItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if(fileChooser.showOpenDialog(Frame.this) == JFileChooser.APPROVE_OPTION){
                    try {
                        CustomerController.loadCustomersFromFile(fileChooser.getSelectedFile());
                        customersTable.refresh();
                    } catch (Exception exception){
                        JOptionPane.showMessageDialog(Frame.this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        exception.printStackTrace();
                    }
                }
            }
        });

        JMenuItem backupItem = new JMenuItem("Backup Customer Data");
        menuBar.getMenu(0).insert(backupItem, 2);
        backupItem.setMnemonic(KeyEvent.VK_B);
        backupItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        backupItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if(fileChooser.showSaveDialog(Frame.this) == JFileChooser.APPROVE_OPTION){
                    try {
                        CustomerController.saveCustomersToFile(fileChooser.getSelectedFile());
                    } catch (Exception exception){
                        JOptionPane.showMessageDialog(Frame.this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        exception.printStackTrace();
                    }
                }
            }
        });
        menuBar.getMenu(0).insertSeparator(3);

        return menuBar;
    }
    private JMenu getNavigateMenu(){
        JMenu navigateMenu = new JMenu("Navigate");
        navigateMenu.setMnemonic(KeyEvent.VK_N);

        JMenuItem navigateItem = new JMenuItem("Open Main Menu");
        navigateItem.setMnemonic(KeyEvent.VK_M);
        navigateItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
        navigateItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gotoNavigation();
            }
        });
        navigateMenu.add(navigateItem);
        navigateMenu.addSeparator();

        JMenuItem minimizeItem = new JMenuItem("Minimize Left Panel");
        minimizeItem.setMnemonic(KeyEvent.VK_N);
        minimizeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.CTRL_MASK));
        minimizeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                minimizeWestPanels();
            }
        });
        navigateMenu.add(minimizeItem);
        navigateMenu.addSeparator();

        JMenu auctionMenu = new JMenu("Auction");
        auctionMenu.setMnemonic(KeyEvent.VK_A);
        JMenuItem curAuctionItem = new JMenuItem("View Current Auction");
        curAuctionItem.setMnemonic(KeyEvent.VK_V);
        curAuctionItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gotoCurrentAuction();
            }
        });
        auctionMenu.add(curAuctionItem);

        JMenuItem closeAuctionItem = new JMenuItem("Close Current Auction");
        closeAuctionItem.setMnemonic(KeyEvent.VK_C);
        closeAuctionItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo goto close auction
            }
        });
        auctionMenu.add(closeAuctionItem);

        JMenuItem newAuctionItem = new JMenuItem("New Auction");
        newAuctionItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        newAuctionItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                gotoAddAuction();
            }
        });
        auctionMenu.add(newAuctionItem);

        JMenuItem showAuctionsItem = new JMenuItem("Show Auctions Table");
        showAuctionsItem.setMnemonic(KeyEvent.VK_S);
        showAuctionsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gotoAddAuction();
                minimizeWestPanels();
            }
        });
        auctionMenu.add(showAuctionsItem);
        navigateMenu.add(auctionMenu);

        JMenu customerMenu = new JMenu("Customer");
        customerMenu.setMnemonic(KeyEvent.VK_C);
        JMenuItem addCustomerItem = new JMenuItem("Add Customer");
        addCustomerItem.setMnemonic(KeyEvent.VK_A);
        addCustomerItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

        addCustomerItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                gotoAddCustomer();
            }
        });
        customerMenu.add(addCustomerItem);

        JMenuItem showCustomersItem = new JMenuItem("Show Customers Table");
        showCustomersItem.setMnemonic(KeyEvent.VK_S);
        showCustomersItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gotoAddCustomer();
                minimizeWestPanels();
            }
        });
        customerMenu.add(showCustomersItem);
        navigateMenu.add(customerMenu);

        JMenu itemMenu = new JMenu("Item");
        itemMenu.setMnemonic(KeyEvent.VK_I);

        JMenuItem addItemItem = new JMenuItem("Add Item");
        addItemItem.setMnemonic(KeyEvent.VK_I);
        addItemItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        addItemItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                gotoAddItem();
            }
        });
        itemMenu.add(addItemItem);

        JMenuItem showItemsItem = new JMenuItem("Show Items Table");
        showItemsItem.setMnemonic(KeyEvent.VK_S);
        showItemsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gotoAddItem();
                minimizeWestPanels();
            }
        });
        itemMenu.add(showItemsItem);
        navigateMenu.add(itemMenu);

        return navigateMenu;
    }
    private JMenu getManageMenu(){
        JMenu manage = new JMenu("Manage");
        manage.setMnemonic(KeyEvent.VK_M);

        if(CurrentUser.getCurrentUser() != null && CurrentUser.getCurrentUser().getAuthority() >= User.HIGH_AUTHORITY) {
            JMenuItem addUserItem = new JMenuItem("Add User");
            addUserItem.setMnemonic(KeyEvent.VK_A);
            addUserItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gotoAddUser();
                }
            });
            manage.add(addUserItem);
            JMenuItem editUserItem = new JMenuItem("Edit User");
            editUserItem.setMnemonic(KeyEvent.VK_E);
            editUserItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gotoEditUser();
                }
            });
            manage.add(editUserItem);
            manage.addSeparator();
        }
        JMenuItem changePassword = new JMenuItem("Change Password");
        changePassword.setMnemonic(KeyEvent.VK_P);
        changePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gotoChangePassword();
            }
        });
        manage.add(changePassword);
        return manage;
    }

    private void exit(){
        int action = JOptionPane.showConfirmDialog(Frame.this,
                "Are you sure you would like to exit the application?",
                "Confirm Exit",
                JOptionPane.OK_CANCEL_OPTION);
        if(action == JOptionPane.OK_OPTION) {
            try {
                Database.connection.close();
            } catch(SQLException exception){
                exception.printStackTrace();
            }
            System.exit(0);
        }
    }
}
