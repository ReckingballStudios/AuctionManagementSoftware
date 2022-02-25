package front.auctions.table;

import back.auctions.Auction;
import controllers.main.AuctionController;

import javax.swing.table.AbstractTableModel;

public class AuctionsTableModel extends AbstractTableModel {


    @Override
    public String getColumnName(int column) {
        return Auction.ASPECT_NAME[column];
    }
    @Override
    public int getRowCount() {
        return AuctionController.getAuctions().size();
    }
    @Override
    public int getColumnCount() {
        //To include the number of purchases and num sales: Delete the -2
        return Auction.ASPECT_NAME.length;
    }
    @Override
    public Object getValueAt(int row, int column) {
        Auction auction = AuctionController.getAuctions().get(row);
        switch(column){
            case 0:
                return auction.getAuctionID();
            case 1:
                return auction.getName();
            case 2:
                return auction.getDate();
            case 3:
                return auction.getLastName();
            case 4:
                return auction.getFirstName();
            case 5:
                return auction.getCountry();
            case 6:
                return auction.getState();
            case 7:
                return auction.getCity();
            case 8:
                return auction.getAddress();
            case 9:
                return auction.getZipCode();
            case 10:
                return auction.getTaxRate();
            case 11:
                return auction.getInternetFee();
            default:
                return null;
        }
    }
}
