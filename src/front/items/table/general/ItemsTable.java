package front.items.table.general;

import back.auctions.CurrentAuction;
import back.items.Item;
import controllers.main.ItemController;
import front.items.table.SelectItemEvent;
import front.items.table.SelectItemListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ItemsTable extends JTable {

    private JTable table = null;
    private ItemsTableModel itemsTableModel = null;
    private SelectItemListener selectItemListener = null;

    public ItemsTable(int auctionID){
        super();
        super.setLayout(new BorderLayout());
        try{
            ItemController.loadItemsFromDatabase();
        } catch(Exception exception){
            JOptionPane.showMessageDialog(ItemsTable.this, "Failed to Connect to Database!", "Error", JOptionPane.ERROR_MESSAGE);
            exception.printStackTrace();
        }

        this.itemsTableModel = new ItemsTableModel();
        if(auctionID == CurrentAuction.ALL_AUCTIONS)
            this.itemsTableModel.setItems(ItemController.getItems());
        else
            this.itemsTableModel.setItems(ItemController.getItemsFromAuction(auctionID));


        this.table = new JTable(this.itemsTableModel);
        this.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                try {
                    setSelectedItem();
                } catch(IndexOutOfBoundsException exception){
                    exception.printStackTrace();
                }
            }
        });
        super.add(new JScrollPane(this.table), BorderLayout.CENTER);
    }
    private void setSelectedItem() throws IndexOutOfBoundsException{
        Item item = ItemController.getItems().get(this.table.getSelectedRow());
        SelectItemEvent selectItemEvent = new SelectItemEvent(this, item);

        if(this.selectItemListener != null)
            this.selectItemListener.selectItemEventOccurred(selectItemEvent);
    }
    public void setItemsTable(int auctionID){
        if(auctionID == CurrentAuction.ALL_AUCTIONS)
            this.itemsTableModel.setItems(ItemController.getItems());
        else
            this.itemsTableModel.setItems(ItemController.getItemsFromAuction(auctionID));
        refresh();
    }


    ///////////SEARCH ALGORITHMS/////////////////
    public void searchAllFor(String text){
        List<Item> items = itemsTableModel.getItems();
        int numItemsSelected = 0;
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
        for(int i = 0; i < items.size(); i ++){
            if(text.length() <= 0){
                this.table.removeRowSelectionInterval(0, items.size()-1);
                break;
            }
            itemID = Integer.toString(items.get(i).getId());
            itemID = constrictStringToText(itemID, text);
            name = items.get(i).getName();
            name = constrictStringToText(name, text);
            price = Float.toString(items.get(i).getPrice());
            price = constrictStringToText(price, text);
            lotNumber = Integer.toString(items.get(i).getLotNumber());
            lotNumber = constrictStringToText(lotNumber, text);
            auctionID = Integer.toString(items.get(i).getAuctionID());
            auctionID = constrictStringToText(auctionID, text);
            isSold = items.get(i).getSoldString();
            isSold = constrictStringToText(isSold, text);
            isPaid = items.get(i).getPaidString();
            isPaid = constrictStringToText(isPaid, text);
            payMethod = items.get(i).getPaymentMethod();
            payMethod = constrictStringToText(payMethod, text);
            payMethod2 = items.get(i).getPaymentMethod2();
            payMethod2 = constrictStringToText(payMethod2, text);
            taxRate = Float.toString(items.get(i).getTaxRate());
            taxRate = constrictStringToText(taxRate, text);
            buyerID = Integer.toString(items.get(i).getBuyerID());
            buyerID = constrictStringToText(buyerID, text);
            sellerID = Integer.toString(items.get(i).getSellerID());
            sellerID = constrictStringToText(sellerID, text);
            serialNumber = items.get(i).getSerialNumber();
            serialNumber = constrictStringToText(serialNumber, text);
            description = items.get(i).getDescription();
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
                Item item = items.get(i);
                items.remove(item);
                items.add(numItemsSelected, item);
                this.table.addRowSelectionInterval(numItemsSelected, numItemsSelected);
                numItemsSelected ++;
            } else {
                this.table.removeRowSelectionInterval(i, i);
            }
        }

        if(items.size() > numItemsSelected + 1)
            this.table.removeRowSelectionInterval(numItemsSelected+1, items.size()-1);
    }
    public void searchFor(String text, String aspect){
        List<Item> items = this.itemsTableModel.getItems();
        String compare = "";
        int numItemsSelected = 0;
        for(int i = 0; i < items.size(); i ++){
            if(text.length() <= 0){
                this.table.removeRowSelectionInterval(i,i);
                continue;
            }

            switch(aspect){
                case "ID":
                    compare = Integer.toString(items.get(i).getId());
                    break;
                case "Name":
                    compare = items.get(i).getName();
                    break;
                case "Price":
                    compare = Float.toString(items.get(i).getPrice());
                    break;
                case "Lot Number":
                    compare = Integer.toString(items.get(i).getLotNumber());
                    break;
                case "AuctionID":
                    compare = Integer.toString(items.get(i).getAuctionID());
                    break;
                case "Is Sold":
                    compare = items.get(i).getSoldString();
                    break;
                case "Is Paid":
                    compare = items.get(i).getPaidString();
                    break;
                case "Pay Method":
                    compare = items.get(i).getPaymentMethod();
                    break;
                case "Check Number":
                    compare = items.get(i).getPaymentMethod2();
                    break;
                case "Tax Rate":
                    compare = Float.toString(items.get(i).getTaxRate());
                    break;
                case "BuyerID":
                    compare = Integer.toString(items.get(i).getBuyerID());
                    break;
                case "SellerID":
                    compare = Integer.toString(items.get(i).getSellerID());
                    break;
                case "Serial #":
                    compare = items.get(i).getSerialNumber();
                    break;
                case "Description":
                    compare = items.get(i).getDescription();
                    break;
            }
            compare = constrictStringToText(compare, text);
            if(text.equalsIgnoreCase(compare)){
                Item item = items.get(i);
                items.remove(item);
                items.add(numItemsSelected, item);
                this.table.addRowSelectionInterval(numItemsSelected, numItemsSelected);
                numItemsSelected ++;
            } else {
                this.table.removeRowSelectionInterval(i, i);
            }
        }
        if(items.size() > numItemsSelected + 1)
            this.table.removeRowSelectionInterval(numItemsSelected+1, items.size()-1);
    }
    private String constrictStringToText(String itemString, String text){
        if(itemString.length() > text.length())
            return itemString.substring(0, text.length());
        else
            return itemString;
    }


    ////////////UTILS/////////////////////
    public void scrollToTop(){
        this.table.scrollRectToVisible(table.getCellRect(0, 0, true));
    }
    public void scrollToBottom(){
        this.table.scrollRectToVisible(table.getCellRect(ItemController.getItems().size(), 0, true));
    }
    public void refresh(){
        this.itemsTableModel.fireTableDataChanged();
    }

    public void setSelectItemListener(SelectItemListener listener){
        this.selectItemListener = listener;
    }
}
