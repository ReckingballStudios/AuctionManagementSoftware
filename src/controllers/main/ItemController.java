package controllers.main;

import back.items.Item;
import back.items.ItemData;
import front.items.add.AddItemEvent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemController {

    private static ItemData itemData = new ItemData();

    public static List<Item> getItems(){
        return itemData.getItems();
    }
    public static List<Item> getItemsFromAuction(int auctionID){
        List<Item> answer = new ArrayList<>();
        for(Item item : itemData.getItems())
            if(auctionID == item.getAuctionID())
                answer.add(item);
        return answer;
    }
    public static void addItem(AddItemEvent itemEvent) throws SQLException {
        itemData.addItem(getItemFromEvent(itemEvent));
    }
    public static void editItem(AddItemEvent itemEvent) throws SQLException{
        itemData.editItem(getItemFromEvent(itemEvent));
    }
    private static Item getItemFromEvent(AddItemEvent itemEvent){
        int id = itemEvent.getId();
        String name = itemEvent.getName();
        int lotNumber = itemEvent.getLotNumber();
        int buyerID = itemEvent.getBuyerID();
        int sellerID = itemEvent.getSellerID();
        float price = itemEvent.getPrice();
        int quantity = itemEvent.getQuantity();
        int auctionID = itemEvent.getAuctionID();
        boolean isSold  = itemEvent.isSold();
        boolean isSoldOnline = itemEvent.isSoldOnline();
        boolean isPaid  = itemEvent.isPaid();
        String paymentMethod = itemEvent.getPaymentMethod();
        String paymentMethod2 = itemEvent.getPaymentMethod2();
        float taxRate  = itemEvent.getTaxRate();
        float premium = itemEvent.getPremium();
        String serialNumber = itemEvent.getSerialNumber();
        String description = itemEvent.getDescription();
        return new Item(id, name, price, quantity, lotNumber, auctionID, isSold, isSoldOnline, isPaid, paymentMethod, paymentMethod2,
                taxRate, premium, buyerID, sellerID, serialNumber, description);
    }
    public static void deleteItem(Item item){

    }
    public static void loadItemsFromDatabase() throws Exception{
        itemData.loadFromDatabase();
    }
    public static List<Item> getPurchasedItemsFromDatabase(int buyerID) throws SQLException{
        return itemData.getPurchasedItemsFromDatabase(buyerID);
    }
    public static List<Item> getSoldItemsFromDatabase(int sellerID) throws SQLException{
        return itemData.getSoldItemsFromDatabase(sellerID);
    }

}
