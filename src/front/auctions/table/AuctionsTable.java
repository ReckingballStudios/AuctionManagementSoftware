package front.auctions.table;

import back.auctions.Auction;
import controllers.main.AuctionController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AuctionsTable extends JTable {

    private JTable table = null;
    private AuctionsTableModel auctionsTableModel = null;
    private SelectAuctionListener selectAuctionListener = null;

    public AuctionsTable(){
        super();
        super.setLayout(new BorderLayout());

        try {
            AuctionController.loadAuctionsFromDatabase();
        } catch(Exception exception){
            JOptionPane.showMessageDialog(AuctionsTable.this,
                    "Failed to Connect to Database!", "Error", JOptionPane.ERROR_MESSAGE);
            exception.printStackTrace();
        }

        this.auctionsTableModel = new AuctionsTableModel();
        this.table = new JTable(this.auctionsTableModel);
        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                setSelectedAuction();
            }
        });
        super.add(new JScrollPane(this.table), BorderLayout.CENTER);
    }
    private void setSelectedAuction(){
        Auction auction = AuctionController.getAuctions().get(table.getSelectedRow());
        SelectAuctionEvent selectAuctionEvent = new SelectAuctionEvent(this, auction);

        if(this.selectAuctionListener != null)
            this.selectAuctionListener.SelectAuctionEventOccurred(selectAuctionEvent);
    }

    //////////SEARCH ALGORITHMS////////////////////
    public void searchFor(String text, String aspect){

    }
    public void searchAllFor(String text){

    }
    private String constrictStringToText(String auctionString, String text){
        if(auctionString.length() > text.length())
            return auctionString.substring(0, text.length());
        else
            return auctionString;
    }


    ////////////UTILITIES/////////////////
    public void scrollToTop(){
        this.table.scrollRectToVisible(table.getCellRect(0, 0, true));
    }
    public void scrollToBottom(){
        this.table.scrollRectToVisible(table.getCellRect(AuctionController.getAuctions().size(), 0, true));
    }
    public void refresh(){
        this.auctionsTableModel.fireTableDataChanged();
    }

    public void setSelectAuctionListener (SelectAuctionListener listener){
        this.selectAuctionListener = listener;
    }
}
