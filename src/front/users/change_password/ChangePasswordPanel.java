package front.users.change_password;

import back.users.CurrentUser;
import controllers.login.UserController;
import controllers.util.Screen;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePasswordPanel extends JPanel {

    private ChangePasswordListener changePasswordListener = null;

    private JLabel usernameLabel = null;
    private JLabel usernameLabel2 = null;
    private JLabel oldPasswordLabel = null;
    private JPasswordField oldPasswordField = null;
    private JLabel passwordLabel = null;
    private JPasswordField passwordField = null;
    private JLabel passwordLabel2 = null;
    private JPasswordField passwordField2 = null;
    private JButton confirmButton = null;

    public ChangePasswordPanel(){
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
        dim.height = Screen.height(0.5f);
        Border innerBorder = BorderFactory.createTitledBorder("Change Password");
        int xOffsets = Screen.width(0.35f);
        int yOffsets = Screen.height(0.28f);
        Border outerBorder = BorderFactory.createEmptyBorder(yOffsets,xOffsets,yOffsets,xOffsets);
        super.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    }
    private void setComponents(){
        int numColumns = 15;
        this.usernameLabel = new JLabel("Username: ");
        this.usernameLabel2 = new JLabel(CurrentUser.getCurrentUser().getUsername());
        this.oldPasswordLabel = new JLabel("Current Password: ");
        this.oldPasswordField = new JPasswordField(numColumns);
        this.passwordLabel = new JLabel("Set New Password: ");
        this.passwordField = new JPasswordField(numColumns);
        this.passwordLabel2 = new JLabel("Confirm Password: ");
        this.passwordField2 = new JPasswordField(numColumns);
        this.confirmButton = new JButton("Confirm Edit");
    }
    private void setListeners(){
        this.confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePasswordEvent();
            }
        });
    }
    private void changePasswordEvent(){
        String currentPassword = new String(this.oldPasswordField.getPassword());
        String password = new String(this.passwordField.getPassword());
        String password2 = new String(this.passwordField2.getPassword());

        if(!UserController.attemptLogin(CurrentUser.getCurrentUser().getUsername(), currentPassword)){
            JOptionPane.showMessageDialog
                    (ChangePasswordPanel.this, "Incorrect Password",
                            "User Addition Failure", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(!password.equals(password2)){
            JOptionPane.showMessageDialog
                    (ChangePasswordPanel.this, "Passwords do not match.",
                            "User Addition Failure", JOptionPane.WARNING_MESSAGE);
            return;
        }
        this.changePasswordListener.changePasswordEvent(password);
    }
    private void layoutComponents(){
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridy = 0;
        layoutUsername(gc);
        gc.gridy ++;
        layoutCurrentPassword(gc);
        gc.gridy ++;
        layoutPassword(gc);
        gc.gridy ++;
        layoutPassword2(gc);
        gc.gridy ++;
        layoutButton(gc);
    }
    private void layoutUsername(GridBagConstraints gc){
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,5);
        super.add(this.usernameLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0, 0, 0, 0);
        super.add(this.usernameLabel2, gc);
    }
    private void layoutCurrentPassword(GridBagConstraints gc){
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,5);
        super.add(this.oldPasswordLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0, 0, 0, 0);
        super.add(this.oldPasswordField, gc);
    }
    private void layoutPassword(GridBagConstraints gc){
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,5);
        super.add(this.passwordLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0, 0, 0, 0);
        super.add(this.passwordField, gc);
    }
    private void layoutPassword2(GridBagConstraints gc){
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,5);
        super.add(this.passwordLabel2, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0, 0, 0, 0);
        super.add(this.passwordField2, gc);
    }
    private void layoutButton(GridBagConstraints gc){
        gc.weighty = 1;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,20);
        super.add(this.confirmButton, gc);
    }

    public void setChangePasswordListener(ChangePasswordListener listener){
        this.changePasswordListener = listener;
    }
}
