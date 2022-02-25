package back.auctions;

public class CurrentAuction {
    public static final int ALL_AUCTIONS = -1;
    private static Auction auction = null;
    private static String auctionName = "No Current Auction";

    public static Auction getAuction() {
        return auction;
    }
    public static void setAuction(Auction auction) {
        CurrentAuction.auction = auction;
        auctionName = auction.getName();
    }
    public static String getAuctionName() {
        return auctionName;
    }
    public static void setAuctionName(String auctionName) {
        CurrentAuction.auctionName = auctionName;
    }
    public static int getAuctionID(){
        if(auction != null)
            return auction.getAuctionID();
        else
            return ALL_AUCTIONS;
    }
    public static float getAuctionTaxRate(){
        if(auction != null)
            return auction.getTaxRate();
        else
            return 0.0f;
    }
}

