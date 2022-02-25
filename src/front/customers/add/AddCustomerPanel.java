package front.customers.add;

import back.customers.Customer;
import controllers.main.CustomerController;
import controllers.util.ComboBoxModels;
import controllers.util.Screen;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class AddCustomerPanel extends JPanel {
    private static final Insets FIELD_INSETS = new Insets(0,0,0,0);
    private static final Insets LABEL_INSETS = new Insets(0,0,0,5);

    private AddCustomerListener addCustomerListener = null;

    private JLabel lastName = null;
    private JTextField lastNameField = null;
    private JLabel firstName = null;
    private JTextField firstNameField = null;
    private JLabel company = null;
    private JTextField companyField = null;
    private JLabel phoneNumber = null;
    private JTextField phoneNumberField = null;
    private JLabel phoneNumber2 = null;
    private JTextField phoneNumberField2 = null;
    private JLabel emailLabel = null;
    private JTextField emailField = null;
    private JLabel country = null;
    private JComboBox countryCombo = null;
    private JLabel state = null;
    private JComboBox stateCombo = null;
    private JLabel city = null;
    private JTextField cityField = null;
    private JLabel address = null;
    private JTextField addressField = null;
    private JLabel zipCode = null;
    private JTextField zipCodeField = null;
    private JLabel driversLicense = null;
    private JTextField driversLicenseField = null;
    private JLabel taxNumberLabel = null;
    private JTextField taxNumberField = null;
    private JLabel auctionBidID = null;
    private JTextField auctionBidIDField = null;
    private JLabel onlineBidID = null;
    private JTextField onlineBidIDField = null;
    private JButton addButton = null;


    public AddCustomerPanel(){
        super();
        super.setLayout(new GridBagLayout());
        initializePanel();
        initializeComponents();
        initializeMnemonics();
        initializeListeners();
        layoutComponents();
    }
    private void initializePanel(){
        Dimension dimension = getPreferredSize();
        dimension.width = Screen.width(0.3f);
        setPreferredSize(dimension);
        Border innerBorder = BorderFactory.createTitledBorder("Add Customer");
        Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    }
    private void initializeComponents(){
        int fieldLengths = 25;
        this.lastName = new JLabel("Last Name: ");
        this.lastNameField = new JTextField(fieldLengths);
        this.firstName = new JLabel("First Name: ");
        this.firstNameField = new JTextField(fieldLengths);
        this.company = new JLabel("Company");
        this.companyField = new JTextField(fieldLengths);
        this.phoneNumber = new JLabel("Phone Number: ");
        this.phoneNumberField = new JTextField(fieldLengths);
        this.phoneNumber2 = new JLabel("Phone Number 2: ");
        this.phoneNumberField2 = new JTextField(fieldLengths);
        this.emailLabel = new JLabel("Email Address: ");
        this.emailField = new JTextField(fieldLengths);
        this.country = new JLabel("Country: ");
        this.countryCombo = new JComboBox();
        this.countryCombo.setModel(ComboBoxModels.getCountryModel());
        this.state = new JLabel("State: ");
        this.stateCombo = new JComboBox();
        this.stateCombo.setModel(ComboBoxModels.getStateModel());
        this.stateCombo.setEditable(false);
        this.stateCombo.setSelectedIndex(0);
        this.city = new JLabel("City: ");
        this.cityField = new JTextField(fieldLengths);
        this.address = new JLabel("Address: ");
        this.addressField = new JTextField(fieldLengths);
        this.zipCode = new JLabel("Zip Code: ");
        this.zipCodeField = new JTextField(fieldLengths);
        this.driversLicense = new JLabel("Drivers License: ");
        this.driversLicenseField = new JTextField(fieldLengths);
        this.taxNumberLabel = new JLabel("Tax Number: ");
        this.taxNumberField = new JTextField(fieldLengths);
        this.auctionBidID = new JLabel("Auction Bidding ID: ");
        this.auctionBidIDField = new JTextField(fieldLengths);
        this.onlineBidID = new JLabel("Online Bidding ID: " );
        this.onlineBidIDField = new JTextField(fieldLengths);
        this.addButton = new JButton("Add Customer");
    }
    private void initializeMnemonics(){
        this.lastName.setDisplayedMnemonic(KeyEvent.VK_L);
        this.lastName.setLabelFor(this.lastNameField);
        this.firstName.setDisplayedMnemonic(KeyEvent.VK_I);
        this.firstName.setLabelFor(this.firstNameField);
        this.phoneNumber.setDisplayedMnemonic(KeyEvent.VK_P);
        this.phoneNumber.setLabelFor(this.phoneNumberField);
        this.country.setDisplayedMnemonic(KeyEvent.VK_C);
        this.country.setLabelFor(this.countryCombo);
        this.countryCombo.setEditable(false);
        this.countryCombo.setSelectedIndex(0);
        this.state.setDisplayedMnemonic(KeyEvent.VK_T);
        this.state.setLabelFor(this.stateCombo);
        this.city.setDisplayedMnemonic(KeyEvent.VK_Y);
        this.city.setLabelFor(this.cityField);
        this.address.setDisplayedMnemonic(KeyEvent.VK_A);
        this.address.setLabelFor(this.addressField);
        this.zipCode.setDisplayedMnemonic(KeyEvent.VK_Z);
        this.zipCode.setLabelFor(this.zipCodeField);
        this.driversLicense.setDisplayedMnemonic(KeyEvent.VK_D);
        this.driversLicense.setLabelFor(this.driversLicenseField);
        this.auctionBidID.setDisplayedMnemonic(KeyEvent.VK_B);
        this.auctionBidID.setLabelFor(this.auctionBidIDField);
        this.onlineBidID.setDisplayedMnemonic(KeyEvent.VK_O);
        this.onlineBidID.setLabelFor(this.onlineBidIDField);
        this.addButton.setMnemonic(KeyEvent.VK_R);
    }
    private void initializeListeners(){
        this.countryCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stateCombo.setEditable(false);
                switch (countryCombo.getSelectedIndex()){
                    case 0: //America
                        state.setText("State: ");
                        stateCombo.setModel(ComboBoxModels.getStateModel());
                        break;
                    case 1: //Canada
                        state.setText("Province: ");
                        stateCombo.setModel(ComboBoxModels.getProvinceModel());
                        break;
                    default:
                        state.setText("State/Province: ");
                        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
                        stateCombo.setModel(defaultComboBoxModel);
                        stateCombo.setEditable(true);
                }
            }
        });
        this.onlineBidIDField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                super.keyPressed(keyEvent);
                if(keyEvent.getKeyCode() == 10){//user presses enter
                    try{
                        addCustomer();
                    } catch (Exception exception){
                        JOptionPane.showMessageDialog(AddCustomerPanel.this, "Invalid Entry", "Error", JOptionPane.ERROR_MESSAGE);
                        exception.printStackTrace();
                    }
                }
            }
        });
        this.addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addCustomer();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(AddCustomerPanel.this, "Invalid Entry", "Error", JOptionPane.ERROR_MESSAGE);
                    exception.printStackTrace();
                }
            }
        });
    }
    private void addCustomer(){
        String lastName = this.lastNameField.getText();
        String firstName = this.firstNameField.getText();
        String company = this.companyField.getText();
        String phoneNumber = this.phoneNumberField.getText();
        String phoneNumber2 = this.phoneNumberField2.getText();
        String email = this.emailField.getText();
        String country = "";
        if(this.countryCombo.getSelectedItem() != null)
            country = this.countryCombo.getSelectedItem().toString();
        String state = "";
        if(this.stateCombo.getSelectedItem() != null)
            state = this.stateCombo.getSelectedItem().toString();
        String city = cityField.getText();
        String address = this.addressField.getText();
        String zipCode = this.zipCodeField.getText();
        String driversLicense = driversLicenseField.getText();
        String taxNumber = taxNumberField.getText();
        String auctionBiddingID = auctionBidIDField.getText();
        String onlineBiddingID = onlineBidIDField.getText();

        if(mandatoryFieldsNotFilledCheck(lastName, firstName, company, address, zipCode, email))
            return;
        if(duplicateCheck(driversLicense, address, phoneNumber))
            return;

        AddCustomerEvent addCustomerEvent = new AddCustomerEvent
                (this, Customer.STARTING_ID+CustomerController.getCustomers().size(), lastName, firstName,
                    company, phoneNumber, phoneNumber2, email, country, state, city, address, zipCode,
                    driversLicense, taxNumber, auctionBiddingID, onlineBiddingID);

        if(this.addCustomerListener != null)
            this.addCustomerListener.customerEventOccurred(addCustomerEvent);

        clearForm();
    }
    public static boolean mandatoryFieldsNotFilledCheck
    (String lastName, String firstName, String company, String address, String zip, String email){
        if((lastName.length() <= 0 || firstName.length() <= 0) && company.length() <= 0){
            String errorMessage = "Please enter a valid first and last name; or a company name.";
            JOptionPane.showMessageDialog(null, errorMessage, "Customer Addition Failure", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if(address.length() <= 0){
            String errorMessage = "Please enter a valid address.";
            JOptionPane.showMessageDialog(null, errorMessage, "Customer Addition Failure", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if(zip.length() <= 0){
            String errorMessage = "Please enter a valid zip code.";
            JOptionPane.showMessageDialog(null, errorMessage, "Customer Addition Failure", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if(email.length() <= 0){
            String errorMessage = "Please enter a valid email address.";
            JOptionPane.showMessageDialog(null, errorMessage, "Customer Addition Failure", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }
    private boolean duplicateCheck(String driversLicense, String address, String phoneNumber){
        List<Customer> customers =CustomerController.getCustomers();

        for(Customer customer : customers){
            if(customer.getDriversLicense().equals(driversLicense) ||
               customer.getAddress().equals(address) ||
               customer.getPhoneNumber().equals(phoneNumber)){
                int action = JOptionPane.showConfirmDialog(null,
                        "Your entry has been detected as a duplicate customer.\n " +
                                "Would you like to create the customer anyway?",
                        "Duplicate Found",
                        JOptionPane.YES_NO_OPTION);
                return action != JOptionPane.YES_OPTION;
            }
        }

        return false;
    }
    private void clearForm(){
        this.lastNameField.setText("");
        this.firstNameField.setText("");
        this.phoneNumberField.setText("");
        this.countryCombo.setSelectedIndex(0);
        this.stateCombo.setSelectedIndex(0);
        this.cityField.setText("");
        this.addressField.setText("");
        this.zipCodeField.setText("");
        this.driversLicenseField.setText("");
        this.auctionBidIDField.setText("");
        this.onlineBidIDField.setText("");
    }
    private void layoutComponents(){
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridy = 0;
        layoutLastName(gc);
        gc.gridy ++;
        layoutFirstName(gc);
        gc.gridy ++;
        layoutCompany(gc);
        gc.gridy ++;
        layoutPhoneNumber(gc);
        gc.gridy ++;
        layoutPhoneNumber2(gc);
        gc.gridy ++;
        layoutEmail(gc);
        gc.gridy ++;
        layoutCountry(gc);
        gc.gridy ++;
        layoutState(gc);
        gc.gridy ++;
        layoutCity(gc);
        gc.gridy ++;
        layoutAddress(gc);
        gc.gridy ++;
        layoutZipCode(gc);
        gc.gridy ++;
        layoutDriversLicense(gc);
        gc.gridy ++;
        layoutTaxNumber(gc);
        gc.gridy ++;
        layoutAuctionBiddingID(gc);
        gc.gridy ++;
        layoutOnlineBiddingID(gc);
        gc.gridy ++;
        layoutAddButton(gc);
    }
    private void layoutLastName(GridBagConstraints gc){
        gc.weighty = 2;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LAST_LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.lastName, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.lastNameField, gc);
    }
    private void layoutFirstName(GridBagConstraints gc){
        gc.weighty = 1;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.firstName, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.firstNameField, gc);
    }
    private void layoutCompany(GridBagConstraints gc){
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.company, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.companyField, gc);
    }
    private void layoutPhoneNumber(GridBagConstraints gc){
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.phoneNumber, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.phoneNumberField, gc);
    }
    private void layoutPhoneNumber2(GridBagConstraints gc){
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.phoneNumber2, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.phoneNumberField2, gc);
    }
    private void layoutEmail(GridBagConstraints gc){
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.emailLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.emailField, gc);
    }
    private void layoutCountry(GridBagConstraints gc){
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.country, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.countryCombo, gc);
    }
    private void layoutState(GridBagConstraints gc){
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.state, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.stateCombo, gc);
    }
    private void layoutCity(GridBagConstraints gc){
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.city, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.cityField, gc);
    }
    private void layoutAddress(GridBagConstraints gc){
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.address, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.addressField, gc);
    }
    private void layoutZipCode(GridBagConstraints gc){
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.zipCode, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.zipCodeField, gc);
    }
    private void layoutDriversLicense(GridBagConstraints gc){
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.driversLicense, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.driversLicenseField, gc);
    }
    private void layoutTaxNumber(GridBagConstraints gc){
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.taxNumberLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.taxNumberField, gc);
    }
    private void layoutAuctionBiddingID(GridBagConstraints gc){
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.auctionBidID, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.auctionBidIDField, gc);
    }
    private void layoutOnlineBiddingID(GridBagConstraints gc){
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.onlineBidID, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.onlineBidIDField, gc);
    }
    private void layoutAddButton(GridBagConstraints gc){
        gc.weighty = 2;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0, 0, 0, 25);
        super.add(this.addButton, gc);
    }




    public void setAddCustomerListener(AddCustomerListener listener){
        this.addCustomerListener = listener;
    }
}
