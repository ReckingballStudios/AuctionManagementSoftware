package front.auctions.selector;

import controllers.main.AuctionController;
import controllers.util.Database;
import controllers.util.Screen;
import front.auctions.table.AuctionsTable;
import front.auctions.table.SelectAuctionEvent;
import front.auctions.table.SelectAuctionListener;
import front.z_other.search.SearchListener;
import front.z_other.search.SearchToolBar;

import javax.swing.*;
import java.awt.*;

public class AuctionSelector extends JFrame {
    private AuctionSelectedListener auctionSelectedListener = null;

    private AuctionsTable auctionsTable = null;
    private SearchToolBar searchToolBar = null;

    public AuctionSelector(){
        super("Select Auction");
        initializeFrame();
        initializeComponents();
        initializeListeners();
    }
    private void initializeFrame(){
        super.setLayout(new BorderLayout());
        super.setVisible(true);
        super.setSize(Screen.width(0.65f), Screen.height(0.65f));
        super.setMinimumSize(new Dimension(Screen.width(0.65f), Screen.height(0.65f)));
        Screen.backgroundColor = super.getBackground();
    }
    private void initializeComponents(){
        this.auctionsTable = new AuctionsTable();
        this.searchToolBar = new SearchToolBar(SearchToolBar.SEARCH_SELECT_AUCTION, super.getWidth());

        super.add(this.auctionsTable, BorderLayout.CENTER);
        super.add(this.searchToolBar, BorderLayout.NORTH);
    }
    private void initializeListeners(){
        this.searchToolBar.setSearchListener(new SearchListener() {
            @Override
            public void searchEventOccurred(String text, String aspect) {
                //Stops Database updates during search
                Database.updatingFromDatabase = (text.length() <= 0);
                //Now update right away
                if(Database.updatingFromDatabase && Database.isOnline){
                    try {
                        AuctionController.loadAuctionsFromDatabase();
                    }catch (Exception exception){
                        JOptionPane.showMessageDialog(AuctionSelector.this, "Failed to Connect to Database!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if(aspect.equalsIgnoreCase("All"))
                    auctionsTable.searchAllFor(text);
                else    //'All' is index 0, so we need to offset it to align the data points
                    auctionsTable.searchFor(text, aspect);

                auctionsTable.scrollToTop();
            }
        });
        this.auctionsTable.setSelectAuctionListener(new SelectAuctionListener() {
            @Override
            public void SelectAuctionEventOccurred(SelectAuctionEvent selectAuctionEvent) {
                if(auctionSelectedListener != null)
                    auctionSelectedListener.auctionSelectedEventOccurred(selectAuctionEvent.getAuction().getAuctionID());
                exit();
            }
        });
    }


    private void exit(){
        super.dispose();
    }

    public void setAuctionSelectedListener(AuctionSelectedListener auctionSelectedListener){
        this.auctionSelectedListener = auctionSelectedListener;
    }
}
