package front.items.edit;

import back.auctions.CurrentAuction;
import back.items.Item;
import controllers.util.ComboBoxModels;
import controllers.util.Screen;
import front.customers.selector.CustomerSelectedListener;
import front.customers.selector.CustomerSelector;
import front.items.add.AddItemEvent;
import front.items.add.AddItemPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static front.items.add.AddItemPanel.mandatoryFieldsNotFilledCheck;

public class EditItemPanel extends JPanel {
    private static final Insets FIELD_INSETS = new Insets(0,0,0,0);
    private static final Insets LABEL_INSETS = new Insets(0,0,0,5);

    private EditItemListener editItemListener = null;

    private Item item = null;

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
    private JButton editItemBtn = null;

    public EditItemPanel(Item item){
        super();
        super.setLayout(new GridBagLayout());
        this.item = item;
        initializeDimensions();
        initializeComponents();
        initializeListeners();
        layoutComponents();
    }
    private void initializeDimensions() {
        Dimension dimension = getPreferredSize();
        dimension.width = Screen.width(0.32f);
        setPreferredSize(dimension);
        Border innerBorder = BorderFactory.createTitledBorder("View/Edit Item");
        Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
        super.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    }
    private void initializeComponents() {
        int fieldLengths = 25;
        this.nameLabel = new JLabel("Item Name: ");
        this.nameField = new JTextField(this.item.getName(), fieldLengths);
        this.lotNumberLabel = new JLabel("Lot Number: ");
        this.lotNumberField = new JTextField(Integer.toString(this.item.getLotNumber()), fieldLengths);
        this.sellerLabel = new JLabel("Seller: ");
        this.selectSellerBtn = new JButton("Select Seller");
        if(this.item.getSellerID() != -1)
            this.selectSellerBtn.setText(Integer.toString(this.item.getSellerID()));
        this.buyerLabel = new JLabel("Buyer: ");
        this.selectBuyerBtn = new JButton("Select Buyer");
        if(this.item.getBuyerID() != -1)
            this.selectBuyerBtn.setText(Integer.toString(this.item.getBuyerID()));
        this.priceLabel = new JLabel("Price: ");
        this.priceField = new JTextField(Float.toString(this.item.getPrice()), fieldLengths);
        this.quantityLabel = new JLabel("Quantity: ");
        this.quantityField = new JTextField(Integer.toString(this.item.getQuantity()), 3);
        this.isSoldCheck = new JCheckBox("Item is Sold", this.item.isSold());
        this.isSoldOnline = new JCheckBox("Item was sold online", this.item.isSoldOnline());
        this.isSoldOnline.setEnabled(this.item.isSold());
        this.isPaidCheck = new JCheckBox("Item is Paid For", this.item.isPaid());
        this.paymentMethodLabel = new JLabel("Payment Method: ");
        this.paymentMethodCombo = new JComboBox(ComboBoxModels.getPaymentTypeModel());
        switch(this.item.getPaymentMethod()){
            case "Cash":
                this.paymentMethodCombo.setSelectedIndex(1);
                break;
            case "Credit Card":
                this.paymentMethodCombo.setSelectedIndex(2);
                break;
            case "Check":
                this.paymentMethodCombo.setSelectedIndex(3);
                break;
            default:
                this.paymentMethodCombo.setSelectedIndex(0);
                break;
        }
        this.checkNumberLabel = new JLabel("Check Number: ");
        this.checkNumberField = new JTextField(this.item.getPaymentMethod2(), fieldLengths);
        this.checkNumberField.setEnabled(this.paymentMethodCombo.getSelectedIndex() == 3);
        this.checkNumberLabel.setEnabled(this.paymentMethodCombo.getSelectedIndex() == 3);
        this.taxRateLabel = new JLabel("Tax Rate: ");
        this.taxRateCombo = new JComboBox(ComboBoxModels.getTaxRateModel(this.item.getTaxRate()));
        this.taxRateCombo.setSelectedIndex(1);
        this.taxRateCombo.setEditable(true);
        this.premiumLabel = new JLabel("Premium: ");
        this.premiumCombo = new JComboBox(ComboBoxModels.getPremiumModel(this.item.getPremium()));
        this.premiumCombo.setSelectedIndex(1);
        this.premiumCombo.setEditable(true);
        this.serialNumberLabel = new JLabel("Serial Number: ");
        this.serialNumberField = new JTextField(this.item.getSerialNumber(), fieldLengths);
        this.descriptionLabel = new JLabel("Description: ");
        this.descriptionArea = new JTextArea(8, fieldLengths);
        this.descriptionArea.setLineWrap(true);
        this.descriptionArea.setText(this.item.getDescription());
        this.editItemBtn = new JButton("Confirm Edits");
    }
    private void initializeListeners() {
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
        this.editItemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    editItem();
                } catch (Exception exception){
                    JOptionPane.showMessageDialog(EditItemPanel.this, "Invalid Entry", "Error", JOptionPane.ERROR_MESSAGE);
                    exception.printStackTrace();
                }
            }
        });
    }
    private void editItem() throws Exception{
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

        if(AddItemPanel.mandatoryFieldsNotFilledCheck(name, lotNumber))
            return;

        AddItemEvent editItemEvent = new AddItemEvent(this, this.item.getId(),
                name, price, quantity, lotNumber, auctionID, isSold, isSoldOnline, isPaid, paymentMethod, paymentMethod2, taxRate,
                premium, buyerID, sellerID, serialNumber, description);

        if(this.editItemListener != null)
            this.editItemListener.editItemEventOccurred(editItemEvent);

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
        super.add(this.editItemBtn, gc);
    }


    public void setEditItemListener(EditItemListener editItemListener) {
        this.editItemListener = editItemListener;
    }
}
