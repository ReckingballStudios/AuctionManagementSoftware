package front.auctions.add;

import back.auctions.CurrentAuction;
import controllers.main.AuctionController;
import controllers.util.ComboBoxModels;
import controllers.util.Screen;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddAuctionPanel extends JPanel {
    private static final Insets FIELD_INSETS = new Insets(0,0,0,0);
    private static final Insets LABEL_INSETS = new Insets(0,0,0,5);

    private AddAuctionListener addAuctionListener = null;

    private JLabel auctionIDLabel = null;
    private JLabel auctionNameLabel = null;
    private JTextField auctionNameField = null;
    private JLabel auctionDateLabel = null;
    private JTextField auctionDateField = null;
    private JLabel lastNameLabel = null;
    private JTextField lastNameField = null;
    private JLabel firstNameLabel = null;
    private JTextField firstNameField = null;
    private JLabel countryLabel = null;
    private JComboBox countryCombo = null;
    private JLabel stateLabel = null;
    private JComboBox stateCombo = null;
    private JLabel cityLabel = null;
    private JTextField cityField = null;
    private JLabel addressLabel = null;
    private JTextField addressField = null;
    private JLabel zipCodeLabel = null;
    private JTextField zipCodeField = null;
    private JLabel taxRateLabel = null;
    private JComboBox taxRateCombo = null;
    private JLabel internetFeeLabel = null;
    private JComboBox internetFeeCombo = null;
    private JButton addAuctionBtn = null;


    public AddAuctionPanel(){
        super();
        super.setLayout(new GridBagLayout());
        initializePanel();
        initializeComponents();
        initializeListeners();
        layoutComponents();
    }
    private void initializePanel(){
        Dimension dimension = getPreferredSize();
        dimension.width = Screen.width(0.3f);
        setPreferredSize(dimension);
        Border innerBorder = BorderFactory.createTitledBorder("Create New Auction");
        Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    }
    private void initializeComponents(){
        int fieldLengths = 25;
        this.auctionIDLabel = new JLabel("Auction ID: " + AuctionController.getAuctions().size());
        this.auctionNameLabel = new JLabel("Name: ");
        this.auctionNameField = new JTextField(fieldLengths);
        this.auctionDateLabel = new JLabel ("Date: (mmddyyyy)");
        this.auctionDateField = new JTextField(8);
        this.lastNameLabel = new JLabel("Last Name: ");
        this.lastNameField = new JTextField(fieldLengths);
        this.firstNameLabel = new JLabel("First Name: ");
        this.firstNameField = new JTextField(fieldLengths);
        this.countryLabel = new JLabel("Country: ");
        this.countryCombo = new JComboBox();
        this.countryCombo.setModel(ComboBoxModels.getCountryModel());
        this.stateLabel = new JLabel("State: ");
        this.stateCombo = new JComboBox();
        this.stateCombo.setModel(ComboBoxModels.getStateModel());
        this.stateCombo.setEditable(false);
        this.stateCombo.setSelectedIndex(0);
        this.cityLabel = new JLabel("City: ");
        this.cityField = new JTextField(fieldLengths);
        this.addressLabel = new JLabel("Address: ");
        this.addressField = new JTextField(fieldLengths);
        this.zipCodeLabel = new JLabel("Zip Code: ");
        this.zipCodeField = new JTextField(fieldLengths);
        this.taxRateLabel = new JLabel("Tax Rate: ");
        this.taxRateCombo = new JComboBox(ComboBoxModels.getTaxRateModel());
        this.taxRateCombo.setEditable(true);
        this.internetFeeLabel = new JLabel("Internet Fee: ");
        this.internetFeeCombo = new JComboBox(ComboBoxModels.getInternetFeeModel());
        this.internetFeeCombo.setEditable(true);
        this.addAuctionBtn = new JButton("Create Auction");
    }
    private void initializeListeners(){
        this.countryCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stateCombo.setEditable(false);
                switch (countryCombo.getSelectedIndex()){
                    case 0: //America
                        stateLabel.setText("State: ");
                        stateCombo.setModel(ComboBoxModels.getStateModel());
                        break;
                    case 1: //Canada
                        stateLabel.setText("Province: ");
                        stateCombo.setModel(ComboBoxModels.getProvinceModel());
                        break;
                    default:
                        stateLabel.setText("State/Province: ");
                        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
                        stateCombo.setModel(defaultComboBoxModel);
                        stateCombo.setEditable(true);
                }
            }
        });
        this.internetFeeCombo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                super.keyPressed(keyEvent);
                if(keyEvent.getKeyCode() == 10){
                    try{
                        addAuction();
                    } catch (Exception exception){
                        JOptionPane.showMessageDialog(AddAuctionPanel.this, "Invalid Entry", "Error", JOptionPane.ERROR_MESSAGE);
                        exception.printStackTrace();
                    }
                }
            }
        });
        this.addAuctionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    addAuction();
                } catch (Exception exception){
                    JOptionPane.showMessageDialog(AddAuctionPanel.this, "Invalid Entry", "Error", JOptionPane.ERROR_MESSAGE);
                    exception.printStackTrace();
                }
            }
        });
    }
    private void addAuction() throws Exception{
        String name = this.auctionNameField.getText();
        String date = this.auctionDateField.getText();
        String lastName = this.lastNameField.getText();
        String firstName = this.firstNameField.getText();
        String country = "";
        if(this.countryCombo.getSelectedItem() != null)
            country = this.countryCombo.getSelectedItem().toString();
        String state = "";
        if(this.stateCombo.getSelectedItem() != null)
            state = this.stateCombo.getSelectedItem().toString();
        String city = cityField.getText();
        String address = this.addressField.getText();
        String zipCode = this.zipCodeField.getText();
        float taxRate = 0.0f;
        if(this.taxRateCombo.getSelectedItem() != null)
            taxRate = Float.parseFloat(this.taxRateCombo.getSelectedItem().toString().substring(4));
        float internetFee = 0.0f;
        if(this.internetFeeCombo.getSelectedItem() != null)
            internetFee = Float.parseFloat(this.internetFeeCombo.getSelectedItem().toString());

        if(mandatoryFieldsNotFilledCheck(name, date, city, address, zipCode))
            return;
        if(CurrentAuction.getAuction() != null){
            String errorMessage = "There is already an ongoing auction.";
            JOptionPane.showMessageDialog(null, errorMessage, "Auction Creation Failure", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        AddAuctionEvent addAuctionEvent = new AddAuctionEvent(this, AuctionController.getAuctions().size(),
                name, date, lastName, firstName, country, state, city, address, zipCode, taxRate, internetFee,
                false);

        if(this.addAuctionListener != null)
            this.addAuctionListener.addAuctionEventOccurred(addAuctionEvent);
    }
    public static boolean mandatoryFieldsNotFilledCheck(String name, String date, String city, String address, String zipCode){
        if(name.length() <= 0){
            String errorMessage = "Please enter a valid name.";
            JOptionPane.showMessageDialog(null, errorMessage, "Auction Creation Failure", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if(date.length() <= 0){
            String errorMessage = "Please enter a valid date.";
            JOptionPane.showMessageDialog(null, errorMessage, "Auction Creation Failure", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if(city.length() <= 0){
            String errorMessage = "Please enter a valid name.";
            JOptionPane.showMessageDialog(null, errorMessage, "Auction Creation Failure", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if(address.length() <= 0){
            String errorMessage = "Please enter a valid address.";
            JOptionPane.showMessageDialog(null, errorMessage, "Auction Creation Failure", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if(zipCode.length() <= 0){
            String errorMessage = "Please enter a valid zipCode.";
            JOptionPane.showMessageDialog(null, errorMessage, "Auction Creation Failure", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }
    private void layoutComponents(){
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridy = 0;
        layoutName(gc);
        gc.gridy ++;
        layoutDate(gc);
        gc.gridy ++;
        layoutLastName(gc);
        gc.gridy ++;
        layoutFirstName(gc);
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
        layoutTaxRate(gc);
        gc.gridy ++;
        layoutInternetFee(gc);
        gc.gridy ++;
        layoutButton(gc);
    }
    private void layoutName(GridBagConstraints gc){
        gc.weighty = 2;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LAST_LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.auctionNameLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.auctionNameField, gc);
    }
    private void layoutDate(GridBagConstraints gc){
        gc.weighty = 1;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.auctionDateLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.auctionDateField, gc);
    }
    private void layoutLastName(GridBagConstraints gc){
        gc.weighty = 1;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.lastNameLabel, gc);

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
        super.add(this.firstNameLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.firstNameField, gc);
    }
    private void layoutCountry(GridBagConstraints gc){
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.countryLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.countryCombo, gc);
    }
    private void layoutState(GridBagConstraints gc){
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.stateLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.stateCombo, gc);
    }
    private void layoutCity(GridBagConstraints gc){
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.cityLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.cityField, gc);
    }
    private void layoutAddress(GridBagConstraints gc){
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.addressLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.addressField, gc);
    }
    private void layoutZipCode(GridBagConstraints gc){
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.zipCodeLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.zipCodeField, gc);
    }
    private void layoutTaxRate(GridBagConstraints gc){
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.taxRateLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.taxRateCombo, gc);
    }
    private void layoutInternetFee(GridBagConstraints gc){
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.internetFeeLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.internetFeeCombo, gc);
    }
    private void layoutButton(GridBagConstraints gc){
        gc.weighty = 2;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0, 0, 0, 25);
        super.add(this.addAuctionBtn, gc);
    }



    public void setAddAuctionListener(AddAuctionListener addAuctionListener){
        this.addAuctionListener = addAuctionListener;
    }
}
