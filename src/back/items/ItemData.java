package back.items;

import controllers.util.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemData {

    private List<Item> items = null;

    public ItemData(){
        this.items = new ArrayList<>();
    }


    public void addItem(Item item) throws SQLException {
        this.items.add(items.size(), item);
        if(!Database.isOnline)
            return;

        Statement statement = Database.connection.createStatement();
        statement.executeUpdate(
                "INSERT INTO `items` VALUES (" +
                        item.getId() + ", '" +
                        item.getName() + "', " +
                        item.getLotNumber() + ", " +
                        item.getBuyerID() + ", " +
                        item.getSellerID() + ", " +
                        item.getAuctionID() + ", " +
                        item.getPrice() + ", " +
                        item.getQuantity() + ", " +
                        item.isSold() + ", " +
                        item.isSoldOnline() + ", " +
                        item.isPaid() + ", '" +
                        item.getPaymentMethod() + "', '" +
                        item.getPaymentMethod2() + "', " +
                        item.getTaxRate() + ", " +
                        item.getPremium() + ", '" +
                        item.getSerialNumber() + "', '" +
                        item.getDescription() + "');");
    }

    public void editItem(Item item) throws SQLException{
        Item remove = null;
        for(Item it : items)
            if(it.getId() == item.getId())
                remove = it;

        this.items.remove(remove);
        this.items.add(0, item);
        if(!Database.isOnline)
            return;


        Statement statement = Database.connection.createStatement();
        statement.executeUpdate(
                "UPDATE `items` SET \n" +
                        "`name` = '" + item.getName() + "',\n" +
                        "`lotNumber` = " + item.getLotNumber() + ",\n" +
                        "`buyerID` = " + item.getBuyerID() + ",\n" +
                        "`sellerID` = " + item.getSellerID() + ",\n" +
                        "`auctionID` = " + item.getAuctionID() + ",\n" +
                        "`price` = " + item.getPrice() + ",\n" +
                        "`quantity` = " + item.getQuantity() + ",\n" +
                        "`isSold` = " + item.isSold() + ",\n" +
                        "`soldOnline` = " + item.isSoldOnline() + ",\n" +
                        "`isPaid` = " + item.isPaid() + ",\n" +
                        "`paymentMethod` = '" + item.getPaymentMethod() + "',\n" +
                        "`paymentMethod2` = '" + item.getPaymentMethod2() + "',\n" +
                        "`taxRate` = " + item.getTaxRate() + ",\n" +
                        "`premium` = " + item.getPremium() + ",\n" +
                        "`serialNumber` = '" + item.getSerialNumber() + "',\n" +
                        "`description` = '" + item.getDescription() + "'\n" +
                        "WHERE `id` = " + item.getId() + ";\n"
        );
    }

    public void loadFromDatabase() throws Exception{
        if(!Database.isOnline)
            return;

        this.items.clear();
        Statement statement = Database.connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM `items`");
        while(result.next()){
            int id = result.getInt("id");
            String name = result.getString("name");
            float price = result.getFloat("price");
            int quantity = result.getInt("quantity");
            int lotNumber = result.getInt("lotNumber");
            int auctionID = result.getInt("auctionID");
            boolean isSold = result.getBoolean("isSold");
            boolean isSoldOnline = result.getBoolean("soldOnline");
            boolean isPaid = result.getBoolean("isPaid");
            String paymentMethod = result.getString("paymentMethod");
            String paymentMethod2 = result.getString("paymentMethod2");
            float taxRate = result.getFloat("taxRate");
            float premium = result.getFloat("premium");
            int buyerID = result.getInt("buyerID");
            int sellerID = result.getInt("sellerID");
            String serialNumber = result.getString("serialNumber");
            String description = result.getString("description");

            this.items.add(
                    new Item(id, name, price, quantity, lotNumber, auctionID, isSold, isSoldOnline, isPaid, paymentMethod,
                            paymentMethod2, taxRate, premium, buyerID, sellerID, serialNumber, description)
            );
        }
    }
    public List<Item> getPurchasedItemsFromDatabase(int buyerID) throws SQLException{
        if(!Database.isOnline)
            return new ArrayList<>();

        Statement statement = Database.connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM `items` WHERE `buyerID` = " + buyerID + ";");
        List<Item> items = new ArrayList<>();
        while(result.next()){
            int id = result.getInt("id");
            String name = result.getString("name");
            float price = result.getFloat("price");
            int quantity = result.getInt("quantity");
            int lotNumber = result.getInt("lotNumber");
            int auctionID = result.getInt("auctionID");
            boolean isSold = result.getBoolean("isSold");
            boolean isSoldOnline = result.getBoolean("soldOnline");
            boolean isPaid = result.getBoolean("isPaid");
            String paymentMethod = result.getString("paymentMethod");
            String paymentMethod2 = result.getString("paymentMethod2");
            float taxRate = result.getFloat("taxRate");
            float premium = result.getFloat("premium");
            int sellerID = result.getInt("sellerID");
            String serialNumber = result.getString("serialNumber");
            String description = result.getString("description");
            items.add(
                    new Item(id, name, price, quantity, lotNumber, auctionID, isSold, isSoldOnline, isPaid, paymentMethod,
                            paymentMethod2, taxRate, premium, buyerID, sellerID, serialNumber, description)
            );
        }

        return items;
    }
    public List<Item> getSoldItemsFromDatabase(int sellerID) throws SQLException{
        if(!Database.isOnline)
            return new ArrayList<>();

        Statement statement = Database.connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM `items` WHERE `sellerID` = " + sellerID + ";");
        List<Item> items = new ArrayList<>();
        while(result.next()){
            int id = result.getInt("id");
            String name = result.getString("name");
            float price = result.getFloat("price");
            int quantity = result.getInt("quantity");
            int lotNumber = result.getInt("lotNumber");
            int auctionID = result.getInt("auctionID");
            boolean isSold = result.getBoolean("isSold");
            boolean isSoldOnline = result.getBoolean("soldOnline");
            boolean isPaid = result.getBoolean("isPaid");
            String paymentMethod = result.getString("paymentMethod");
            String paymentMethod2 = result.getString("paymentMethod2");
            float taxRate = result.getFloat("taxRate");
            float premium = result.getFloat("premium");
            int buyerID = result.getInt("buyerID");
            String serialNumber = result.getString("serialNumber");
            String description = result.getString("description");
            items.add(
                    new Item(id, name, price, quantity, lotNumber, auctionID, isSold, isSoldOnline, isPaid, paymentMethod,
                            paymentMethod2, taxRate, premium, buyerID, sellerID, serialNumber, description)
            );
        }

        return items;
    }


    public List<Item> getItems(){
        return items;
    }
}
