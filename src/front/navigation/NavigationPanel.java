package front.navigation;

import controllers.util.Screen;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class NavigationPanel extends JPanel {
    public static final int ADD_CUSTOMER = 0;
    public static final int ADD_ITEM = 1;
    public static final int AUCTION = 2;
    public static final int ADD_USER = 3;
    public static final int EDIT_USER = 4;
    public static final int CHANGE_PASSWORD = 5;
    public static final int LOG_OUT = 6;
    public static final int EXIT = 7;
    public static final int CURRENT_AUCTION = 8;
    private static final Dimension buttonDimensions = new Dimension(175, 40);


    private NavigateListener navigateListener = null;
    private BufferedImage resourceImage = null;

    private JButton currentAuction = null;
    private JButton auctions = null;
    private JButton customers = null;
    private JButton items = null;
    private JButton logOut = null;
    private JButton exit = null;


    public NavigationPanel(){
        super();
        super.setLayout(new GridBagLayout());
        setDimensions();
        initializeComponents();
        initializeListeners();
        layoutComponents();
    }
    private void setDimensions(){
        Dimension dim = getPreferredSize();
        dim.width = Screen.width(0.25f);
        dim.height = Screen.height(0.5f);
        Border innerBorder = BorderFactory.createTitledBorder("Navigation");
        int xOffsets = Screen.width(0.35f);
        int yOffsets = Screen.height(0.15f);
        Border outerBorder = BorderFactory.createEmptyBorder(yOffsets+50,xOffsets,yOffsets,xOffsets);
        super.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    }
    private void initializeComponents(){
        try {
            this.resourceImage = ImageIO.read(new File("images/resource.png"));
            for(int x = 0; x < resourceImage.getWidth(); x ++){
                for(int y = 0; y < resourceImage.getHeight(); y ++){
                    if(this.resourceImage.getRGB(x,y) == -1){   //replace the background pixels to fit the background
                        this.resourceImage.setRGB(x, y, Screen.backgroundColor.getRGB());
                    }
                }
            }
        } catch(IOException exception){
            exception.printStackTrace();
        }

        this.currentAuction = new JButton("Current Auction");
        this.currentAuction.setPreferredSize(buttonDimensions);
        this.currentAuction.setMnemonic(KeyEvent.VK_R);
        this.auctions = new JButton("Auctions");
        this.auctions.setMnemonic(KeyEvent.VK_A);
        this.auctions.setPreferredSize(buttonDimensions);
        this.customers = new JButton("Customers");
        this.customers.setMnemonic(KeyEvent.VK_C);
        this.customers.setPreferredSize(buttonDimensions);
        this.items = new JButton("Items");
        this.items.setMnemonic(KeyEvent.VK_I);
        this.items.setPreferredSize(buttonDimensions);
        this.logOut = new JButton("Log Out");
        this.logOut.setMnemonic(KeyEvent.VK_L);
        this.logOut.setPreferredSize(buttonDimensions);
        this.exit = new JButton("Exit");
        this.exit.setMnemonic(KeyEvent.VK_E);
        this.exit.setPreferredSize(buttonDimensions);
    }
    private void initializeListeners(){
        this.currentAuction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(navigateListener != null)
                    navigateListener.NavigationEventOccurred(CURRENT_AUCTION);
            }
        });
        this.auctions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(navigateListener != null)
                    navigateListener.NavigationEventOccurred(AUCTION);
            }
        });
        this.customers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(navigateListener != null)
                    navigateListener.NavigationEventOccurred(ADD_CUSTOMER);
            }
        });
        this.items.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(navigateListener != null)
                    navigateListener.NavigationEventOccurred(ADD_ITEM);
            }
        });
        this.logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(navigateListener != null)
                    navigateListener.NavigationEventOccurred(LOG_OUT);
            }
        });
        this.exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(navigateListener != null)
                    navigateListener.NavigationEventOccurred(EXIT);
            }
        });
    }
    private void layoutComponents(){
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.NONE;
        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridy = 1;
        layoutCurrentAuction(gc);
        gc.gridy ++;
        layoutAuctions(gc);
        gc.gridy ++;
        layoutCustomers(gc);
        gc.gridy ++;
        layoutItems(gc);
        gc.gridy ++;
        layoutLogOut(gc);
        gc.gridy ++;
        layoutExit(gc);
        gc.gridy ++;
    }
    private void layoutCurrentAuction(GridBagConstraints gc){
        gc.weighty = 2;
        gc.anchor = GridBagConstraints.SOUTH;
        gc.insets = new Insets(0, 0, 15, 0);
        super.add(currentAuction, gc);
    }
    private void layoutAuctions(GridBagConstraints gc){
        gc.weighty = 1;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(0,0,0,0);
        super.add(auctions, gc);
    }
    private void layoutCustomers(GridBagConstraints gc){
        gc.weighty = 1;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(0,0,0,0);
        super.add(customers, gc);
    }
    private void layoutItems(GridBagConstraints gc){
        gc.weighty = 1;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(0,0,0,0);
        super.add(items, gc);
    }
    private void layoutLogOut(GridBagConstraints gc){
        gc.weighty = 1;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(0,0,0,0);
        super.add(logOut, gc);
    }
    private void layoutExit(GridBagConstraints gc){
        gc.weighty = 2;
        gc.anchor = GridBagConstraints.NORTH;
        gc.insets = new Insets(20, 0, 0, 0);
        super.add(exit, gc);
    }


    @Override
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        try {
            graphics.drawImage(this.resourceImage,
                    Screen.width(0.35f), Screen.height(0.08f), Screen.width(0.65f), Screen.height(0.18f),
                    0, 0, this.resourceImage.getWidth(), this.resourceImage.getHeight(), Color.white, this);
        } catch(NullPointerException exception){
            exception.printStackTrace();
        }
    }
    public void setNavigateListener(NavigateListener navigateListener){
        this.navigateListener = navigateListener;
    }
}
