package front.items.add;

import back.auctions.CurrentAuction;
import controllers.main.ItemController;
import controllers.util.ComboBoxModels;
import controllers.util.Screen;
import front.customers.selector.CustomerSelectedListener;
import front.customers.selector.CustomerSelector;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddItemPanel extends JPanel {
    private static final Insets FIELD_INSETS = new Insets(0,0,0,0);
    private static final Insets LABEL_INSETS = new Insets(0,0,0,5);

    private AddItemListener addItemListener = null;

    private JLabel nameLabel = null;
    private JTextField nameField = null;
    private JLabel lotNumberLabel = null;
    private JTextField lotNumberField = null;
    private JLabel sellerLabel = null;
    private JButton selectSellerBtn = null;
    private JLabel buyerLabel = null;
    private JButton selectBuyerBtn = null;
    private JLabel priceLabel = null;
    private JTextField priceField = null;
    private JLabel quantityLabel = null;
    private JTextField quantityField = null;
    private JCheckBox isSoldCheck = null;
    private JCheckBox isSoldOnline = null;
    private JCheckBox isPaidCheck = null;
    private JLabel paymentMethodLabel = null;
    private JComboBox paymentMethodCombo = null;
    private JLabel checkNumberLabel = null;
    private JTextField checkNumberField = null;
    private JLabel taxRateLabel = null;
    private JComboBox taxRateCombo = null;
    private JLabel premiumLabel = null;
    private JComboBox premiumCombo = null;
    private JLabel serialNumberLabel = null;
    private JTextField serialNumberField = null;
    private JLabel descriptionLabel = null;
    private JTextArea descriptionArea = null;
    private JButton addButton = null;

    public AddItemPanel(){
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
        Border innerBorder = BorderFactory.createTitledBorder("Add Item");
        Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    }
    private void initializeComponents(){
        int fieldLengths = 25;
        this.nameLabel = new JLabel("Item Name: ");
        this.nameField = new JTextField(fieldLengths);
        this.lotNumberLabel = new JLabel("Lot Number: ");
        this.lotNumberField = new JTextField(fieldLengths);
        this.sellerLabel = new JLabel("Seller: ");
        this.selectSellerBtn = new JButton("Select Seller");
        this.buyerLabel = new JLabel("Buyer: ");
        this.selectBuyerBtn = new JButton("Select Buyer");
        this.priceLabel = new JLabel("Price: ");
        this.priceField = new JTextField(fieldLengths);
        this.quantityLabel = new JLabel("Quantity: ");
        this.quantityField = new JTextField("1", 3);
        this.isSoldCheck = new JCheckBox("Item is Sold");
        this.isSoldOnline = new JCheckBox("Item was sold online");
        this.isSoldOnline.setEnabled(false);
        this.isPaidCheck = new JCheckBox("Item is Paid For");
        this.paymentMethodLabel = new JLabel("Payment Method: ");
        this.paymentMethodCombo = new JComboBox(ComboBoxModels.getPaymentTypeModel());
        this.paymentMethodCombo.setSelectedIndex(0);
        this.checkNumberLabel = new JLabel("Check Number: ");
        this.checkNumberField = new JTextField(fieldLengths);
        this.checkNumberField.setEnabled(false);
        this.checkNumberLabel.setEnabled(false);
        this.taxRateLabel = new JLabel("Tax Rate: ");
        this.taxRateCombo = new JComboBox(ComboBoxModels.getTaxRateModel());
        this.taxRateCombo.setSelectedIndex(0);
        this.taxRateCombo.setEditable(true);
        this.premiumLabel = new JLabel("Premium: ");
        this.premiumCombo = new JComboBox(ComboBoxModels.getPremiumModel());
        this.premiumCombo.setSelectedIndex(0);
        this.premiumCombo.setEditable(true);
        this.serialNumberLabel = new JLabel("Serial Number: ");
        this.serialNumberField = new JTextField(fieldLengths);
        this.descriptionLabel = new JLabel("Description: ");
        this.descriptionArea = new JTextArea(8, fieldLengths);
        this.descriptionArea.setLineWrap(true);
        this.addButton = new JButton("Add Item");
    }
    private void initializeMnemonics(){

    }
    private void initializeListeners(){
        this.selectSellerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerSelector customerSelector = new CustomerSelector("Select Seller");
                customerSelector.setCustomerSelectedListener(new CustomerSelectedListener() {
                    @Override
                    public void CustomerSelectedEventOccurred(int customerID) {
                        selectSellerBtn.setText("" + customerID);
                    }
                });
            }
        });
        this.selectBuyerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerSelector customerSelector = new CustomerSelector("Select Buyer");
                customerSelector.setCustomerSelectedListener(new CustomerSelectedListener() {
                    @Override
                    public void CustomerSelectedEventOccurred(int customerID) {
                        selectBuyerBtn.setText("" + customerID);
                    }
                });
            }
        });
        this.isSoldCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isSoldOnline.setEnabled(isSoldCheck.isSelected());
                if(!isSoldCheck.isSelected())
                    isSoldOnline.setSelected(false);
            }
        });
        this.paymentMethodCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int checkIndex = 3;
                checkNumberLabel.setEnabled(paymentMethodCombo.getSelectedIndex() == checkIndex);
                checkNumberField.setEnabled(paymentMethodCombo.getSelectedIndex() == checkIndex);
            }
        });
        this.addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addItem();
                } catch (Exception exception){
                    JOptionPane.showMessageDialog(AddItemPanel.this, "Invalid Entry", "Error", JOptionPane.ERROR_MESSAGE);
                    exception.printStackTrace();
                }
            }
        });
    }
    private void addItem() throws Exception{
        String name = this.nameField.getText();
        float price = 0.0f;
        if(!this.priceField.getText().equals(""))
            price = Float.parseFloat(this.priceField.getText());
        int quantity = 1;
        if(!this.quantityField.getText().equals(""))
            quantity = Integer.parseInt(this.quantityField.getText());
        int lotNumber = -1;
        if(!this.lotNumberField.getText().equals(""))
            lotNumber = Integer.parseInt(this.lotNumberField.getText());
        boolean isSold = this.isSoldCheck.isSelected();
        boolean isSoldOnline = this.isSoldOnline.isSelected();
        boolean isPaid = this.isPaidCheck.isSelected();
        String paymentMethod = "";
        if(this.paymentMethodCombo.getSelectedItem() != null)
            paymentMethod = this.paymentMethodCombo.getSelectedItem().toString();
        String paymentMethod2 = this.checkNumberField.getText();
        float taxRate = 0.0f;
        if(this.taxRateCombo.getSelectedItem() != null)
            taxRate = Float.parseFloat(this.taxRateCombo.getSelectedItem().toString().substring(4));
        float premium = 0.0f;
        if(this.premiumCombo.getSelectedItem() != null)
            premium = Float.parseFloat(this.premiumCombo.getSelectedItem().toString());
        int auctionID = CurrentAuction.getAuctionID();
        int buyerID = -1;
        int sellerID = -1;
        try{
            sellerID = Integer.parseInt(this.selectSellerBtn.getText());
        } catch(NumberFormatException exception){
//            System.out.println("no seller selected");
        }
        try {
            buyerID = Integer.parseInt(this.selectBuyerBtn.getText());
        } catch(NumberFormatException exception){
//            System.out.println("no buyer selected");
        }
        String serialNumber = this.serialNumberField.getText();
        String description = this.descriptionArea.getText();

        if(mandatoryFieldsNotFilledCheck(name, lotNumber))
            return;

        AddItemEvent addItemEvent = new AddItemEvent(this, ItemController.getItems().size(),
            name, price, quantity, lotNumber, auctionID, isSold, isSoldOnline, isPaid, paymentMethod, paymentMethod2, taxRate,
            premium, buyerID, sellerID, serialNumber, description);

        if(this.addItemListener != null)
            this.addItemListener.AddItemEventOccurred(addItemEvent);

        clearForm();
    }
    public static boolean mandatoryFieldsNotFilledCheck(String name, int lotNumber){
        if((name.length() <= 0)){
            String errorMessage = "Please enter a valid name.";
            JOptionPane.showMessageDialog(null, errorMessage, "Item Addition Failure", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if((lotNumber <= 0)){
            String errorMessage = "Please enter a valid lot number.";
            JOptionPane.showMessageDialog(null, errorMessage, "Item Addition Failure", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }
    private void clearForm(){
        this.nameField.setText("");
        this.lotNumberField.setText("");
        this.priceField.setText("");
        this.isSoldCheck.setSelected(false);
        this.isPaidCheck.setSelected(false);
        this.paymentMethodCombo.setSelectedIndex(0);
        this.checkNumberField.setText("");
        this.taxRateCombo.setSelectedIndex(0);
        this.premiumCombo.setSelectedIndex(0);
        this.serialNumberField.setText("");
        this.descriptionArea.setText("");
    }
    private void layoutComponents(){
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridy = 0;
        layoutName(gc);
        gc.gridy ++;
        layoutLotNumber(gc);
        gc.gridy ++;
        layoutSeller(gc);
        gc.gridy ++;
        layoutBuyer(gc);
        gc.gridy ++;
        layoutPrice(gc);
        gc.gridy ++;
        layoutQuantity(gc);
        gc.gridy ++;
        layoutSold(gc);
        gc.gridy ++;
        layoutSoldOnline(gc);
        gc.gridy ++;
        layoutPaidFor(gc);
        gc.gridy ++;
        layoutPaymentMethod(gc);
        gc.gridy ++;
        layoutCheckNumber(gc);
        gc.gridy ++;
        layoutTaxRate(gc);
        gc.gridy ++;
        layoutPremium(gc);
        gc.gridy ++;
        layoutSerialNumber(gc);
        gc.gridy ++;
        layoutDescription(gc);
        gc.gridy ++;
        layoutButton(gc);
    }
    private void layoutName(GridBagConstraints gc){
        gc.weighty = 2;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LAST_LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.nameLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.nameField, gc);
    }
    private void layoutLotNumber(GridBagConstraints gc){
        gc.weighty = 2;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.lotNumberLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.lotNumberField, gc);
    }
    private void layoutSeller(GridBagConstraints gc){
        gc.weighty = 2;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.sellerLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.selectSellerBtn, gc);
    }
    private void layoutBuyer(GridBagConstraints gc){
        gc.weighty = 2;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.buyerLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.selectBuyerBtn, gc);
    }
    private void layoutPrice(GridBagConstraints gc){
        gc.weighty = 2;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.priceLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.priceField, gc);
    }
    private void layoutQuantity(GridBagConstraints gc){
        gc.weighty = 1;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.quantityLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.quantityField, gc);
    }
    private void layoutSold(GridBagConstraints gc){
        gc.weighty = 0.5;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = LABEL_INSETS;
        super.add(this.isSoldCheck, gc);
    }
    private void layoutSoldOnline(GridBagConstraints gc){
        gc.weighty = 0.5;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = LABEL_INSETS;
        super.add(this.isSoldOnline, gc);
    }

    private void layoutPaidFor(GridBagConstraints gc){
        gc.weighty = 0.5;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = LABEL_INSETS;
        super.add(this.isPaidCheck, gc);
    }
    private void layoutPaymentMethod(GridBagConstraints gc){
        gc.weighty = 2;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.paymentMethodLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.paymentMethodCombo, gc);
    }
    private void layoutCheckNumber(GridBagConstraints gc){
        gc.weighty = 2;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.checkNumberLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.checkNumberField, gc);
    }
    private void layoutTaxRate(GridBagConstraints gc){
        gc.weighty = 2;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.taxRateLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.taxRateCombo, gc);
    }
    private void layoutPremium(GridBagConstraints gc){
        gc.weighty = 2;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.premiumLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.premiumCombo, gc);
    }
    private void layoutSerialNumber(GridBagConstraints gc){
        gc.weighty = 2;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.serialNumberLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.serialNumberField, gc);
    }
    private void layoutDescription(GridBagConstraints gc){
        gc.weighty = 2;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = LABEL_INSETS;
        super.add(this.descriptionLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = FIELD_INSETS;
        super.add(this.descriptionArea, gc);
    }
    private void layoutButton(GridBagConstraints gc){
        gc.weighty = 2;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0, 0, 0, 25);
        super.add(this.addButton, gc);
    }


    public void setAddItemListener(AddItemListener listener){
        this.addItemListener = listener;
    }
}
