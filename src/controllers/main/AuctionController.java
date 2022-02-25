package controllers.main;

import back.auctions.Auction;
import back.auctions.AuctionData;
import front.auctions.add.AddAuctionEvent;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class AuctionController {

    private static AuctionData auctionData = new AuctionData();

    public static List<Auction> getAuctions(){
        return auctionData.getAuctions();
    }

    public static void addAuction(AddAuctionEvent addAuctionEvent) throws SQLException {
        try {
            loadAuctionsFromDatabase();
        } catch(Exception exception){
            JOptionPane.showMessageDialog(null, "Failed to Connect to Database!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        auctionData.addAuction(getAuctionFromEvent(addAuctionEvent));
    }
    public static void editAuction(AddAuctionEvent editAuctionEvent) throws SQLException {
        try {
            loadAuctionsFromDatabase();
        } catch(Exception exception){
            JOptionPane.showMessageDialog(null, "Failed to Connect to Database!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        auctionData.editAuction(getAuctionFromEvent(editAuctionEvent));
    }
    private static Auction getAuctionFromEvent(AddAuctionEvent event){
        int id = event.getAuctionID();
        String name = event.getName();
        String date = event.getDate();
        String last = event.getLastName();
        String first = event.getFirstName();
        String country = event.getCountry();
        String state = event.getState();
        String city = event.getCity();
        String adr = event.getAddress();
        String zip = event.getZipCode();
        float tax = event.getTaxRate();
        float internetFee = event.getInternetFee();
        boolean closed = event.isClosed();
        return new Auction(id, name, date, last, first, country, state, city, adr, zip, tax, internetFee, closed);
    }
    public static void loadAuctionsFromDatabase() throws Exception{
        auctionData.loadFromDatabase();
    }
}
