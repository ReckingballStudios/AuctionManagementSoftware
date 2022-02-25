package back.auctions;

import controllers.util.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AuctionData {

    private List<Auction> auctions = null;

    public AuctionData(){
        this.auctions = new ArrayList<>();
    }

    public void addAuction(Auction auction) throws SQLException {
        CurrentAuction.setAuction(auction);
        this.auctions.add(0, auction);
        if(!Database.isOnline)
            return;

        Statement statement = Database.connection.createStatement();
        statement.executeUpdate(
                "INSERT INTO `auctions` VALUES (" +
                    auction.getAuctionID() + ", '" +
                    auction.getName() + "', '" +
                    convertDate(auction.getDate()) + "', '" +
                    auction.getLastName() + "', '" +
                    auction.getFirstName() + "', '" +
                    auction.getCountry() + "', '" +
                    auction.getState() + "', '" +
                    auction.getCity() + "', '" +
                    auction.getAddress() + "', '" +
                    auction.getZipCode() + "', " +
                    auction.getTaxRate() + ", " +
                    auction.getInternetFee() + ", " +
                    auction.isClosed() + ");\n"
        );
    }
    public void editAuction(Auction auction) throws SQLException {
        Auction remove = null;
        for(Auction auct : this.auctions){
            if(auct.getAuctionID() == auction.getAuctionID())
                remove = auct;
        }

        this.auctions.remove(remove);
        this.auctions.add(0, auction);
        if(!Database.isOnline)
            return;

        Statement statement = Database.connection.createStatement();
        statement.executeUpdate(
                "UPDATE `auctions` SET \n" +
                        "'name' = '" + auction.getName() + "',\n" +
                        "'date' = '" + auction.getDate() + "',\n" +
                        "`lastName` = '" + auction.getLastName() + "',\n" +
                        "`firstName` = '" + auction.getFirstName() + "',\n" +
                        "`country` = '" + auction.getCountry() + "',\n" +
                        "`state` = '" + auction.getState() + "',\n" +
                        "`city` = '" + auction.getCity() + "',\n" +
                        "`address` = '" + auction.getAddress() + "',\n" +
                        "`zipCode` = '" + auction.getZipCode() + "',\n" +
                        "`taxRate` = " + auction.getTaxRate() + ",\n" +
                        "`internetFee` = " + auction.getInternetFee() + "\n" +
                        "WHERE `id` = " + auction.getAuctionID() + ";\n"
        );
    }
    private String convertDate(String date){
        String answer = "";
        date = date.replace("-", "");
        answer += date.substring(4);
        answer += date.substring(0, 4);
        return answer;
    }
    public void loadFromDatabase() throws Exception{
        this.auctions.clear();
        Statement statement = Database.connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM auctions");
        while(result.next()){
            int id = result.getInt("id");
            String name = result.getString("name");
            String date = convertDate(result.getString("date"));
            String last = result.getString("lastName");
            String first = result.getString("firstName");
            String country = result.getString("country");
            String state = result.getString("state");
            String city = result.getString("city");
            String address = result.getString("address");
            String zipCode = result.getString("zipCode");
            float tax = result.getFloat("taxRate");
            float iFee = result.getFloat("internetFee");
            boolean closed = result.getBoolean("closed");

            this.auctions.add(0, new Auction(id, name, date, last, first, country,
                    state, city, address, zipCode, tax, iFee, closed));

            if(!this.auctions.get(0).isClosed())
                CurrentAuction.setAuction(this.auctions.get(0));
        }
    }

    public List<Auction> getAuctions(){
        return auctions;
    }
}
