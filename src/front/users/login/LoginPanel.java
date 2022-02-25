package front.users.login;


import controllers.util.Screen;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoginPanel extends JPanel {

    private BufferedImage resourceImage = null;
    private JLabel usernameLabel = null;
    private JTextField usernameField = null;
    private JLabel passwordLabel = null;
    private JPasswordField passwordField = null;
    private JButton loginButton = null;

    private LoginListener loginListener = null;

    public LoginPanel(){
        super();
        super.setLayout(new GridBagLayout());

        setDimensions();
        setComponents();
        setListeners();
        layoutComponents();
    }
    private void setDimensions(){
        Dimension dim = getPreferredSize();
        dim.width = Screen.width(0.25f);
        dim.height = Screen.height(0.25f);
        Border innerBorder = BorderFactory.createTitledBorder("Log in");
        int xOffsets = Screen.width(0.35f);
        int yOffsets = Screen.height(0.35f);
        Border outerBorder = BorderFactory.createEmptyBorder(yOffsets,xOffsets,yOffsets,xOffsets);
        super.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    }
    private void setComponents(){
        int numColumns = 15;
        this.usernameLabel = new JLabel("Username: ");
        this.usernameField = new JTextField(numColumns);
        this.usernameLabel.setDisplayedMnemonic(KeyEvent.VK_U);
        this.usernameLabel.setLabelFor(usernameField);
        this.passwordLabel = new JLabel("Password: ");
        this.passwordField = new JPasswordField(numColumns);
        this.passwordLabel.setDisplayedMnemonic(KeyEvent.VK_P);
        this.passwordLabel.setLabelFor(passwordField);
        this.loginButton = new JButton("Log in");
        this.loginButton.setMnemonic(KeyEvent.VK_L);

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
    }
    private void setListeners(){
        this.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                loginAttemptEvent();
            }
        });

        this.passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == 10){//user presses enter
                    loginAttemptEvent();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
    private void loginAttemptEvent(){
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        LoginEvent loginEvent = new LoginEvent(this, username, password);
        if(loginListener != null)
            loginListener.loginEventOccurred(loginEvent);
    }


    private void layoutComponents(){
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.NONE;
        gc.weightx = 1;
        gc.weighty = 1;

        layoutUsername(gc);
        layoutPassword(gc);
        layoutButton(gc);
    }
    private void layoutUsername(GridBagConstraints gc){
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weighty = 2;
        gc.anchor = GridBagConstraints.LAST_LINE_END;
        gc.insets = new Insets(0,0,7,5);
        super.add(this.usernameLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.insets = new Insets(0, 0, 7, 0);
        super.add(this.usernameField, gc);
    }
    private void layoutPassword(GridBagConstraints gc){
        gc.gridx = 0;
        gc.gridy = 1;
        gc.weighty = 1;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,5);
        super.add(this.passwordLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0, 0, 0, 0);
        super.add(this.passwordField, gc);
    }
    private void layoutButton(GridBagConstraints gc){
        gc.gridx = 1;
        gc.gridy = 2;
        gc.weighty = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(5,0,0,20);
        super.add(this.loginButton, gc);
    }

    @Override
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        try {
            graphics.drawImage(resourceImage,
                    Screen.width(0.35f), Screen.height(0.18f), Screen.width(0.65f), Screen.height(0.28f),
                    0, 0, this.resourceImage.getWidth(), this.resourceImage.getHeight(), Color.white, this);
        } catch(NullPointerException exception){
            exception.printStackTrace();
        }
    }

    public void setLoginListener(LoginListener listener){
        this.loginListener = listener;
    }
}
