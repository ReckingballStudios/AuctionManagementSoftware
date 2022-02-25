package front.customers.edit;

import back.customers.Customer;
import controllers.util.ComboBoxModels;
import controllers.util.Screen;
import front.customers.add.AddCustomerEvent;
import front.customers.add.AddCustomerPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EditCustomerPanel extends JPanel{
    private static final Insets FIELD_INSETS = new Insets(0,0,0,0);
    private static final Insets LABEL_INSETS = new Insets(0,0,0,5);

    private EditCustomerListener editCustomerListener = null;
    private DeleteCustomerListener deleteCustomerListener = null;
    private Customer customer = null;

    private JLabel id = null;
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
    private JButton deleteButton = null;
    private JButton editButton = null;

    public EditCustomerPanel(Customer customer){
        super();
        this.customer = customer;
        initializePanel();
        initializeComponents();
        initializeMnemonics();
        initializeListeners();
        super.setLayout(new GridBagLayout());
        layoutComponents();
    }
    private void initializePanel(){
        Dimension dimension = getPreferredSize();
        dimension.width = Screen.width(0.32f);
        setPreferredSize(dimension);
        Border innerBorder = BorderFactory.createTitledBorder("View/Edit Customer");
        Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
        super.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    }
    private void initializeComponents(){
        int fieldLengths = 25;
        this.id = new JLabel("ID:   " + this.customer.getId());
        this.lastName = new JLabel("Last Name: ");
        this.lastNameField = new JTextField(this.customer.getLastName(), fieldLengths);
        this.firstName = new JLabel("First Name: ");
        this.firstNameField = new JTextField(this.customer.getFirstName(), fieldLengths);
        this.company = new JLabel("Company");
        this.companyField = new JTextField(this.customer.getCompany(), fieldLengths);
        this.phoneNumber = new JLabel("Phone Number: ");
        this.phoneNumberField = new JTextField(this.customer.getPhoneNumber(), fieldLengths);
        this.phoneNumber2 = new JLabel("Phone Number 2: ");
        this.phoneNumberField2 = new JTextField(this.customer.getPhoneNumber2(), fieldLengths);
        this.emailLabel = new JLabel("Email Address: ");
        this.emailField = new JTextField(this.customer.getEmail(), fieldLengths);
        this.country = new JLabel("Country: ");
        this.countryCombo = new JComboBox();
        this.countryCombo.setModel(ComboBoxModels.getCountryModel());
        this.countryCombo.setEditable(false);
        this.countryCombo.setSelectedIndex(0);
        this.countryCombo.setSelectedItem(this.customer.getCountry());
        this.state = new JLabel("State: ");
        this.stateCombo = new JComboBox();
        switch (countryCombo.getSelectedIndex()){
            case 0: //America
                this.state.setText("State: ");
                this.stateCombo.setModel(ComboBoxModels.getStateModel());
                this.stateCombo.setSelectedItem(this.customer.getState());
                break;
            case 1: //Canada
                state.setText("Province: ");
                this.stateCombo.setModel(ComboBoxModels.getProvinceModel());
                this.stateCombo.setSelectedItem(this.customer.getState());
                break;
            default: //Other
                DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
                defaultComboBoxModel.addElement(this.customer.getState());
                this.stateCombo.setModel(defaultComboBoxModel);
                this.stateCombo.setSelectedIndex(0);
                this.stateCombo.setEditable(true);
        }
        this.city = new JLabel("City: ");
        this.cityField = new JTextField(this.customer.getCity(), fieldLengths);
        this.address = new JLabel("Address: ");
        this.addressField = new JTextField(this.customer.getAddress(), fieldLengths);
        this.zipCode = new JLabel("Zip Code: ");
        this.zipCodeField = new JTextField(this.customer.getZipCode(), fieldLengths);
        this.driversLicense = new JLabel("Drivers License: ");
        this.driversLicenseField = new JTextField(this.customer.getDriversLicense(), fieldLengths);
        this.taxNumberLabel = new JLabel("Tax Number: ");
        this.taxNumberField = new JTextField(this.customer.getTaxNumber(), fieldLengths);
        this.auctionBidID = new JLabel("Auction Bidding ID: ");
        this.auctionBidIDField = new JTextField(this.customer.getAuctionBiddingID(), fieldLengths);
        this.onlineBidID = new JLabel("Online Bidding ID: " );
        this.onlineBidIDField = new JTextField(this.customer.getOnlineBiddingID(), fieldLengths);
        this.deleteButton = new JButton("Delete Customer");
        this.deleteButton.setEnabled(false);
        this.editButton = new JButton("Confirm Edits");
    }
    private void initializeMnemonics(){
        this.lastName.setDisplayedMnemonic(KeyEvent.VK_L);
        this.lastName.setLabelFor(this.lastNameField);
        this.firstName.setDisplayedMnemonic(KeyEvent.VK_I);
        this.firstName.setLabelFor(this.firstNameField);
        this.phoneNumber.setDisplayedMnemonic(KeyEvent.VK_P);
        this.phoneNumber.setLabelFor(this.phoneNumberField);
        this.phoneNumber.setDisplayedMnemonic(KeyEvent.VK_P);
        this.phoneNumber.setLabelFor(this.phoneNumberField);
        this.country.setDisplayedMnemonic(KeyEvent.VK_C);
        this.country.setLabelFor(this.countryCombo);
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
        this.editButton.setMnemonic(KeyEvent.VK_R);
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
        this.onlineBidIDField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { }
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == 10){//user presses enter
                    editCustomer();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) { }
        });
        this.deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int action = JOptionPane.showConfirmDialog(null,
                        "Are you sure you would like to delete this customer?",
                        "Confirm Deletion",
                        JOptionPane.OK_CANCEL_OPTION);
                if(action == JOptionPane.OK_OPTION && deleteCustomerListener != null)
                    deleteCustomerListener.deleteCustomerEventOccurred(customer.getId());
            }
        });
        this.editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editCustomer();
            }
        });
    }
    private void editCustomer(){
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

        if(AddCustomerPanel.mandatoryFieldsNotFilledCheck(lastName, firstName, company, address, zipCode, email))
            return;

        //AddCustomerEvent can also be used to edit customers
        AddCustomerEvent editCustomerEvent = new AddCustomerEvent
                (this, this.customer.getId(), lastName, firstName,
                        company, phoneNumber, phoneNumber2, email, country, state, city, address, zipCode,
                        driversLicense, taxNumber, auctionBiddingID, onlineBiddingID);

        if(this.editCustomerListener != null)
            this.editCustomerListener.customerEventOccurred(editCustomerEvent);
    }
    private void layoutComponents(){
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridy = 0;
        layoutID(gc);
        gc.gridy ++;
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
        layoutButtons(gc);
    }
    private void layoutID(GridBagConstraints gc){
        gc.weighty = 1.5;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.id, gc);
    }
    private void layoutLastName(GridBagConstraints gc){
        gc.weighty = 1;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.lastName, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
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
    private void layoutButtons(GridBagConstraints gc){
        gc.weighty = 2;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(10, 25, 0, 0);
        super.add(this.deleteButton, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(10, 0, 0, 25);
        super.add(this.editButton, gc);
    }

    public void setDeleteCustomerListener(DeleteCustomerListener listener){
        this.deleteCustomerListener = listener;
    }
    public void setEditCustomerListener(EditCustomerListener listener){
        this.editCustomerListener = listener;
    }
}
