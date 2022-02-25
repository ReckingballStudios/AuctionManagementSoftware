package front.z_other.search;

import back.auctions.Auction;
import back.auctions.CurrentAuction;
import back.customers.Customer;
import back.items.Item;
import controllers.util.ComboBoxModels;
import controllers.util.Screen;
import front.auctions.selector.AuctionSelectedListener;
import front.auctions.selector.AuctionSelector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SearchToolBar extends JToolBar {

    public static final int SEARCH_CUSTOMERS = 0;
    public static final int SEARCH_ITEMS = 1;
    public static final int SEARCH_SELECT_CUSTOMER = 2;
    public static final int SEARCH_AUCTIONS = 3;
    public static final int SEARCH_SELECT_AUCTION = 4;
    public static final int SEARCH_ITEMS_FROM_AUCTION = 5;
    private int tableType = SEARCH_CUSTOMERS;


    private Insets fieldInsets = new Insets(0, 0, 0, 40);
    private Insets labelInsets = new Insets(0, 0, 0, 5);
    private Insets westInsets = new Insets(0, 0, 0, Screen.width(0.33f));

    private SearchListener searchListener = null;
    private NavigateToMenuListener navigateToMenuListener = null;
    private ChangeTableListener changeTableListener = null;

    private JButton gotoNavigationMenu = null;
    private JLabel auctionLabel = null;
    private JComboBox auctionCombo = null;
    private JLabel searchFor = null;
    private JComboBox searchForCombo = null;
    private JLabel search = null;
    private JTextField searchField = null;

    /**
     * @param tableType is what the toolbar is searching through... {@link #SEARCH_CUSTOMERS}{@link #SEARCH_ITEMS} ETC.
     */
    public SearchToolBar(int tableType, int frameWidth) {
        super();
        super.setLayout(new GridBagLayout());
        westInsets = new Insets(0,0,0, (int)(frameWidth*0.33f));
        fieldInsets = new Insets(0, 0, 0, (int)(frameWidth*0.01f));

        initializeComponents(tableType);
        initializeListener();
        layoutComponents();
    }
    private void initializeComponents(int tableType) {
        this.tableType = tableType;
        this.gotoNavigationMenu = new JButton("Navigation Menu");
        this.auctionLabel = new JLabel("Auction: ");
        this.auctionCombo = new JComboBox(ComboBoxModels.getAuctionsModel());
        this.searchFor = new JLabel(" Search For: ");
        this.searchForCombo = new JComboBox();
        DefaultComboBoxModel searchModel = new DefaultComboBoxModel();
        searchModel.addElement("All");
        switch (tableType) {
            //REMEMBER to change getAspect() method if you change something here
            case SEARCH_CUSTOMERS:
            case SEARCH_SELECT_CUSTOMER:
                for (int i = 0; i < Customer.ASPECT_NAME.length; i++)
                    searchModel.addElement(Customer.ASPECT_NAME[i]);
                break;
            case SEARCH_ITEMS:
            case SEARCH_ITEMS_FROM_AUCTION:
                for (int i = 0; i < Item.ASPECT_NAME.length; i++)
                    searchModel.addElement(Item.ASPECT_NAME[i]);
                break;
            case SEARCH_AUCTIONS:
            case SEARCH_SELECT_AUCTION:
                for (int i = 0; i < Auction.ASPECT_NAME.length; i ++)
                    searchModel.addElement(Auction.ASPECT_NAME[i]);
                break;
        }
        this.searchForCombo.setModel(searchModel);
        this.searchForCombo.setSelectedIndex(0);
        this.searchForCombo.setEditable(false);
        this.searchFor.setDisplayedMnemonic(KeyEvent.VK_E);
        this.searchFor.setLabelFor(searchForCombo);
        this.search = new JLabel(" Search: ");
        this.searchField = new JTextField(20);
        this.search.setDisplayedMnemonic(KeyEvent.VK_S);
        this.search.setLabelFor(this.searchField);
    }
    private void initializeListener() {
        this.auctionCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch(auctionCombo.getSelectedIndex()){
                    case 0:
                        if(changeTableListener != null)
                            changeTableListener.changeTableEventOccurred(CurrentAuction.ALL_AUCTIONS);
                        break;
                    case 1:
                        if(changeTableListener != null)
                            changeTableListener.changeTableEventOccurred(CurrentAuction.getAuctionID());
                        break;
                    default:
                        AuctionSelector auctionSelector = new AuctionSelector();
                        auctionSelector.setAuctionSelectedListener(new AuctionSelectedListener() {
                            @Override
                            public void auctionSelectedEventOccurred(int auctionID) {
                                changeTableListener.changeTableEventOccurred(auctionID);
                            }
                        });
                        break;
                }
            }
        });
        this.searchForCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchListener.searchEventOccurred(searchField.getText(), getAspect());
                searchField.requestFocusInWindow();
            }
        });

        this.searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                //Cancel the listen if the character is unreadable, or if there is no listener
                if (searchListener == null || keyEvent.getKeyChar() == '\uFFFF')
                    return;

                String aspect = getAspect();

                if (keyEvent.getKeyCode() == KeyEvent.VK_BACK_SPACE && searchField.getText().length() > 0)
                    searchListener.searchEventOccurred
                            (searchField.getText().substring(0, searchField.getText().length() - 1), aspect);
                else if (keyEvent.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                    searchListener.searchEventOccurred(searchField.getText(), aspect);
                else
                    searchListener.searchEventOccurred
                            (searchField.getText() + keyEvent.getKeyChar(), aspect);
            }
        });

        this.gotoNavigationMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(navigateToMenuListener != null)
                    navigateToMenuListener.NavigateToMenuEventOccurred();
            }
        });
    }
    private String getAspect(){
        String aspect = "All";
        if(searchForCombo.getSelectedIndex() > 0) {
            switch (tableType) {
                case SEARCH_CUSTOMERS:
                case SEARCH_SELECT_CUSTOMER:
                        aspect = Customer.ASPECT_NAME[searchForCombo.getSelectedIndex() - 1];
                    break;
                case SEARCH_ITEMS:
                case SEARCH_ITEMS_FROM_AUCTION:
                        aspect = Item.ASPECT_NAME[searchForCombo.getSelectedIndex() - 1];
                    break;
                case SEARCH_AUCTIONS:
                case SEARCH_SELECT_AUCTION:
                        aspect = Auction.ASPECT_NAME[searchForCombo.getSelectedIndex() - 1];
            }
        }
        return aspect;
    }
    private void layoutComponents() {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridy = 0;

        gc.gridx = 0;
        if(this.tableType != SEARCH_SELECT_CUSTOMER && this.tableType != SEARCH_SELECT_AUCTION) {
            gc.anchor = GridBagConstraints.LINE_START;
            gc.insets = westInsets;
            super.add(this.gotoNavigationMenu, gc);
        }

        if(this.tableType != SEARCH_AUCTIONS && this.tableType != SEARCH_SELECT_AUCTION &&
            this.tableType != SEARCH_ITEMS_FROM_AUCTION) {
            gc.gridx++;
            gc.anchor = GridBagConstraints.LINE_END;
            gc.insets = labelInsets;
            super.add(this.auctionLabel, gc);

            gc.gridx++;
            gc.anchor = GridBagConstraints.LINE_START;
            gc.insets = fieldInsets;
            super.add(this.auctionCombo, gc);
        }

        gc.gridx ++;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = labelInsets;
        super.add(this.searchFor, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = fieldInsets;
        super.add(this.searchForCombo, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = labelInsets;
        super.add(this.search, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = fieldInsets;
        super.add(this.searchField, gc);
    }

    public void setSearchListener(SearchListener searchListener) {
        this.searchListener = searchListener;
    }
    public void setNavigateToMenuListener(NavigateToMenuListener navigateToMenuListener){
        this.navigateToMenuListener = navigateToMenuListener;
    }
    public void setChangeTableListener(ChangeTableListener changeTableListener){
        this.changeTableListener = changeTableListener;
    }
}