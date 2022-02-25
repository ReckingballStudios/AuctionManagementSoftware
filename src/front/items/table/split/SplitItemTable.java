package front.items.table.split;


import back.items.Item;
import controllers.main.ItemController;
import front.items.table.SelectItemEvent;
import front.items.table.SelectItemListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class SplitItemTable extends JTable {
    private JTable salesTable = null;
    private JTable purchasesTable = null;
    private SplitItemTableModel soldTableModel = null;
    private SplitItemTableModel purchaseTableModel = null;
    private SelectItemListener selectItemListener = null;

    public SplitItemTable(int connectingID){
        super();
        super.setLayout(new BorderLayout());
        List<Item> soldItems = null;
        List<Item> purchasedItems = null;
        try{
            soldItems = ItemController.getSoldItemsFromDatabase(connectingID);
            purchasedItems = ItemController.getPurchasedItemsFromDatabase(connectingID);
        } catch(Exception exception){
            JOptionPane.showMessageDialog(SplitItemTable.this, "Failed to Connect to Database!", "Error", JOptionPane.ERROR_MESSAGE);
            exception.printStackTrace();
        }
        this.soldTableModel = new SplitItemTableModel();
        this.soldTableModel.setItems(soldItems);
        this.salesTable = new JTable(this.soldTableModel);
        this.salesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                setSelectedItemFromSales();
            }
        });
        super.add(new JScrollPane(this.salesTable), BorderLayout.CENTER);

        this.purchaseTableModel = new SplitItemTableModel();
        this.purchaseTableModel.setItems(purchasedItems);
        this.purchasesTable = new JTable(this.purchaseTableModel);
        this.purchasesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                setSelectedItemFromPurchases();
            }
        });
        super.add(new JScrollPane(this.purchasesTable), BorderLayout.SOUTH);
    }
    private void setSelectedItemFromSales(){
        Item item = ItemController.getItems().get(this.salesTable.getSelectedRow());
        SelectItemEvent selectItemEvent = new SelectItemEvent(this, item);

        if(this.selectItemListener != null)
            this.selectItemListener.selectItemEventOccurred(selectItemEvent);
    }
    private void setSelectedItemFromPurchases(){
        Item item = ItemController.getItems().get(this.purchasesTable.getSelectedRow());
        SelectItemEvent selectItemEvent = new SelectItemEvent(this, item);

        if(this.selectItemListener != null)
            this.selectItemListener.selectItemEventOccurred(selectItemEvent);
    }


    ///////////SEARCH ALGORITHMS/////////////////
    public void searchAllFor(String text){
        List<Item> soldItems = this.soldTableModel.getItems();
        List<Item> purchasedItems = this.purchaseTableModel.getItems();
        int numSalesSelected = 0;
        int numPurchaseSelected = 0;
        String itemID;
        String name;
        String price;
        String lotNumber;
        String auctionID;
        String isSold;
        String isPaid;
        String payMethod;
        String payMethod2;
        String taxRate;
        String buyerID;
        String sellerID;
        String serialNumber;
        String description;
        //Sales Table
        for(int i = 0; i < soldItems.size(); i ++){
            if(text.length() <= 0){
                this.salesTable.removeRowSelectionInterval(0, soldItems.size() - 1);
                break;
            }
            itemID = Integer.toString(soldItems.get(i).getId());
            itemID = constrictStringToText(itemID, text);
            name = soldItems.get(i).getName();
            name = constrictStringToText(name, text);
            price = Float.toString(soldItems.get(i).getPrice());
            price = constrictStringToText(price, text);
            lotNumber = Integer.toString(soldItems.get(i).getLotNumber());
            lotNumber = constrictStringToText(lotNumber, text);
            auctionID = Integer.toString(soldItems.get(i).getAuctionID());
            auctionID = constrictStringToText(auctionID, text);
            isSold = soldItems.get(i).getSoldString();
            isSold = constrictStringToText(isSold, text);
            isPaid = soldItems.get(i).getPaidString();
            isPaid = constrictStringToText(isPaid, text);
            payMethod = soldItems.get(i).getPaymentMethod();
            payMethod = constrictStringToText(payMethod, text);
            payMethod2 = soldItems.get(i).getPaymentMethod2();
            payMethod2 = constrictStringToText(payMethod2, text);
            taxRate = Float.toString(soldItems.get(i).getTaxRate());
            taxRate = constrictStringToText(taxRate, text);
            buyerID = Integer.toString(soldItems.get(i).getBuyerID());
            buyerID = constrictStringToText(buyerID, text);
            sellerID = Integer.toString(soldItems.get(i).getSellerID());
            sellerID = constrictStringToText(sellerID, text);
            serialNumber = soldItems.get(i).getSerialNumber();
            serialNumber = constrictStringToText(serialNumber, text);
            description = soldItems.get(i).getDescription();
            description = constrictStringToText(description, text);

            if
            (
                    text.equalsIgnoreCase(itemID) ||
                    text.equalsIgnoreCase(name) ||
                    text.equalsIgnoreCase(price) ||
                    text.equalsIgnoreCase(lotNumber) ||
                    text.equalsIgnoreCase(auctionID) ||
                    text.equalsIgnoreCase(isSold) ||
                    text.equalsIgnoreCase(isPaid) ||
                    text.equalsIgnoreCase(payMethod) ||
                    text.equalsIgnoreCase(payMethod2) ||
                    text.equalsIgnoreCase(taxRate) ||
                    text.equalsIgnoreCase(buyerID) ||
                    text.equalsIgnoreCase(sellerID) ||
                    text.equalsIgnoreCase(serialNumber) ||
                    text.equalsIgnoreCase(description)
            ){
                Item item = soldItems.get(i);
                soldItems.remove(item);
                soldItems.add(numSalesSelected, item);
                this.salesTable.addRowSelectionInterval(numSalesSelected, numSalesSelected);
                numSalesSelected ++;
            } else {
                this.salesTable.removeRowSelectionInterval(i, i);
            }
        }
        for(int i = 0; i < purchasedItems.size(); i ++){
            if(text.length() <= 0){
                this.purchasesTable.removeRowSelectionInterval(0, purchasedItems.size()-1);
                break;
            }
            itemID = Integer.toString(purchasedItems.get(i).getId());
            itemID = constrictStringToText(itemID, text);
            name = purchasedItems.get(i).getName();
            name = constrictStringToText(name, text);
            price = Float.toString(purchasedItems.get(i).getPrice());
            price = constrictStringToText(price, text);
            lotNumber = Integer.toString(purchasedItems.get(i).getLotNumber());
            lotNumber = constrictStringToText(lotNumber, text);
            auctionID = Integer.toString(purchasedItems.get(i).getAuctionID());
            auctionID = constrictStringToText(auctionID, text);
            isSold = purchasedItems.get(i).getSoldString();
            isSold = constrictStringToText(isSold, text);
            isPaid = purchasedItems.get(i).getPaidString();
            isPaid = constrictStringToText(isPaid, text);
            payMethod = purchasedItems.get(i).getPaymentMethod();
            payMethod = constrictStringToText(payMethod, text);
            payMethod2 = purchasedItems.get(i).getPaymentMethod2();
            payMethod2 = constrictStringToText(payMethod2, text);
            taxRate = Float.toString(purchasedItems.get(i).getTaxRate());
            taxRate = constrictStringToText(taxRate, text);
            buyerID = Integer.toString(purchasedItems.get(i).getBuyerID());
            buyerID = constrictStringToText(buyerID, text);
            sellerID = Integer.toString(purchasedItems.get(i).getSellerID());
            sellerID = constrictStringToText(sellerID, text);
            serialNumber = purchasedItems.get(i).getSerialNumber();
            serialNumber = constrictStringToText(serialNumber, text);
            description = purchasedItems.get(i).getDescription();
            description = constrictStringToText(description, text);

            if
            (
                    text.equalsIgnoreCase(itemID) ||
                            text.equalsIgnoreCase(name) ||
                            text.equalsIgnoreCase(price) ||
                            text.equalsIgnoreCase(lotNumber) ||
                            text.equalsIgnoreCase(auctionID) ||
                            text.equalsIgnoreCase(isSold) ||
                            text.equalsIgnoreCase(isPaid) ||
                            text.equalsIgnoreCase(payMethod) ||
                            text.equalsIgnoreCase(payMethod2) ||
                            text.equalsIgnoreCase(taxRate) ||
                            text.equalsIgnoreCase(buyerID) ||
                            text.equalsIgnoreCase(sellerID) ||
                            text.equalsIgnoreCase(serialNumber) ||
                            text.equalsIgnoreCase(description)
            ){
                Item item = purchasedItems.get(i);
                purchasedItems.remove(item);
                purchasedItems.add(numPurchaseSelected, item);
                this.purchasesTable.addRowSelectionInterval(numPurchaseSelected, numPurchaseSelected);
                numPurchaseSelected ++;
            } else {
                this.purchasesTable.removeRowSelectionInterval(i, i);
            }
        }

        if(soldItems.size() > numSalesSelected + 1)
            this.salesTable.removeRowSelectionInterval(numSalesSelected+1, soldItems.size()-1);
        if(purchasedItems.size() > numPurchaseSelected + 1)
            this.purchasesTable.removeRowSelectionInterval(numPurchaseSelected+1, purchasedItems.size()-1);
    }
    public void searchFor(String text, String aspect){
        List<Item> soldItems = this.soldTableModel.getItems();
        List<Item> purchasedItems = this.purchaseTableModel.getItems();
        int numSalesSelected = 0;
        int numPurchaseSelected = 0;
        String compare = "";
        for(int i = 0; i < soldItems.size(); i ++){
            if(text.length() <= 0){
                this.salesTable.removeRowSelectionInterval(i,i);
                continue;
            }
            switch(aspect){
                case "ID":
                    compare = Integer.toString(soldItems.get(i).getId());
                    break;
                case "Name":
                    compare = soldItems.get(i).getName();
                    break;
                case "Price":
                    compare = Float.toString(soldItems.get(i).getPrice());
                    break;
                case "Lot Number":
                    compare = Integer.toString(soldItems.get(i).getLotNumber());
                    break;
                case "Quantity":
                    compare = Integer.toString(soldItems.get(i).getQuantity());
                    break;
                case "AuctionID":
                    compare = Integer.toString(soldItems.get(i).getAuctionID());
                    break;
                case "Is Sold":
                    compare = soldItems.get(i).getSoldString();
                    break;
                case "Is Paid":
                    compare = soldItems.get(i).getPaidString();
                    break;
                case "Pay Method":
                    compare = soldItems.get(i).getPaymentMethod();
                    break;
                case "Check Number":
                    compare = soldItems.get(i).getPaymentMethod2();
                    break;
                case "Tax Rate":
                    compare = Float.toString(soldItems.get(i).getTaxRate());
                    break;
                case "Premium":
                    compare = Float.toString(soldItems.get(i).getPremium());
                    break;
                case "BuyerID":
                    compare = Integer.toString(soldItems.get(i).getBuyerID());
                    break;
                case "SellerID":
                    compare = Integer.toString(soldItems.get(i).getSellerID());
                    break;
                case "Serial #":
                    compare = soldItems.get(i).getSerialNumber();
                    break;
                case "Description":
                    compare = soldItems.get(i).getDescription();
                    break;
            }
            compare = constrictStringToText(compare, text);
            if(text.equalsIgnoreCase(compare)){
                Item item = soldItems.get(i);
                soldItems.remove(item);
                soldItems.add(numSalesSelected, item);
                this.salesTable.addRowSelectionInterval(numSalesSelected, numSalesSelected);
                numSalesSelected ++;
            } else {
                this.salesTable.removeRowSelectionInterval(i, i);
            }
        }
        for(int i = 0; i < purchasedItems.size(); i ++){
            if(text.length() <= 0){
                this.purchasesTable.removeRowSelectionInterval(i,i);
                continue;
            }
            switch(aspect){
                case "ID":
                    compare = Integer.toString(purchasedItems.get(i).getId());
                    break;
                case "Name":
                    compare = purchasedItems.get(i).getName();
                    break;
                case "Price":
                    compare = Float.toString(purchasedItems.get(i).getPrice());
                    break;
                case "Lot Number":
                    compare = Integer.toString(purchasedItems.get(i).getLotNumber());
                    break;
                case "Quantity":
                    compare = Integer.toString(purchasedItems.get(i).getQuantity());
                    break;
                case "AuctionID":
                    compare = Integer.toString(purchasedItems.get(i).getAuctionID());
                    break;
                case "Is Sold":
                    compare = purchasedItems.get(i).getSoldString();
                    break;
                case "Is Paid":
                    compare = purchasedItems.get(i).getPaidString();
                    break;
                case "Pay Method":
                    compare = purchasedItems.get(i).getPaymentMethod();
                    break;
                case "Check Number":
                    compare = purchasedItems.get(i).getPaymentMethod2();
                    break;
                case "Tax Rate":
                    compare = Float.toString(purchasedItems.get(i).getTaxRate());
                    break;
                case "Premium":
                    compare = Float.toString(purchasedItems.get(i).getPremium());
                    break;
                case "BuyerID":
                    compare = Integer.toString(purchasedItems.get(i).getBuyerID());
                    break;
                case "SellerID":
                    compare = Integer.toString(purchasedItems.get(i).getSellerID());
                    break;
                case "Serial #":
                    compare = purchasedItems.get(i).getSerialNumber();
                    break;
                case "Description":
                    compare = purchasedItems.get(i).getDescription();
                    break;
            }
            compare = constrictStringToText(compare, text);
            if(text.equalsIgnoreCase(compare)){
                Item item = purchasedItems.get(i);
                purchasedItems.remove(item);
                purchasedItems.add(numPurchaseSelected, item);
                this.purchasesTable.addRowSelectionInterval(numPurchaseSelected, numPurchaseSelected);
                numPurchaseSelected ++;
            } else {
                this.purchasesTable.removeRowSelectionInterval(i, i);
            }
        }


        if(soldItems.size() > numSalesSelected + 1)
            this.salesTable.removeRowSelectionInterval(numSalesSelected+1, soldItems.size()-1);
        if(purchasedItems.size() > numPurchaseSelected + 1)
            this.purchasesTable.removeRowSelectionInterval(numPurchaseSelected+1, purchasedItems.size()-1);
    }
    private String constrictStringToText(String itemString, String text){
        if(itemString.length() > text.length())
            return itemString.substring(0, text.length());
        else
            return itemString;
    }



    ////////////UTILS/////////////////////
    public void scrollToTop(){
        this.salesTable.scrollRectToVisible(this.salesTable.getCellRect(0, 0, true));
        this.purchasesTable.scrollRectToVisible(this.purchasesTable.getCellRect(0, 0, true));
    }
    public void scrollToBottom(){
        this.salesTable.scrollRectToVisible(this.salesTable.getCellRect(ItemController.getItems().size(), 0, true));
        this.purchasesTable.scrollRectToVisible(this.purchasesTable.getCellRect(ItemController.getItems().size(), 0, true));
    }
    public void refresh(){
        this.soldTableModel.fireTableDataChanged();
        this.purchaseTableModel.fireTableDataChanged();
    }
    public void setSelectItemListener(SelectItemListener listener){
        this.selectItemListener = listener;
    }
}
