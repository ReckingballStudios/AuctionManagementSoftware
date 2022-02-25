package front.auctions.table;

import back.auctions.Auction;

import java.util.EventObject;

public class SelectAuctionEvent extends EventObject {

    private Auction auction = null;

    public SelectAuctionEvent(Object source, Auction auction) {
        super(source);

        this.auction = auction;
    }


    public Auction getAuction(){
        return auction;
    }
}
