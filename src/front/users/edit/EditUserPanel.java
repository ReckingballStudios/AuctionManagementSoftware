package front.users.edit;

import back.users.CurrentUser;
import back.users.User;
import controllers.login.UserController;
import controllers.util.Screen;
import front.users.add.AddUserEvent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EditUserPanel extends JPanel {

    private EditUserListener addUserListener = null;

    private JLabel usernameLabel = null;
    private JComboBox usernameField = null;
    private JLabel oldPasswordLabel = null;
    private JPasswordField oldPasswordField = null;
    private JLabel passwordLabel = null;
    private JPasswordField passwordField = null;
    //Confirmation//
    private JLabel passwordLabel2 = null;
    private JPasswordField passwordField2 = null;
    private JButton editUserButton = null;

    private JLabel authorityLabel = null;
    private ButtonGroup radioGroup = null;
    private JRadioButton authority1 = null;
    private JRadioButton authority2 = null;
    private JRadioButton authority3 = null;


    public EditUserPanel(){
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
        Border innerBorder = BorderFactory.createTitledBorder("Edit User");
        int xOffsets = Screen.width(0.35f);
        int yOffsets = Screen.height(0.15f);
        Border outerBorder = BorderFactory.createEmptyBorder(yOffsets,xOffsets,yOffsets,xOffsets);
        super.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    }
    private void setComponents(){
        int numColumns = 15;
        this.usernameLabel = new JLabel("Set Username: ");
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        List<User> users = UserController.readUsers();
        for(User user : users)
            model.addElement(user.getUsername());
        this.usernameField = new JComboBox(model);
        this.usernameField.setEditable(false);
        this.oldPasswordLabel = new JLabel("Current Password: ");
        this.oldPasswordField = new JPasswordField(numColumns);
        this.passwordLabel = new JLabel("Set New Password: ");
        this.passwordField = new JPasswordField(numColumns);
        this.passwordLabel2 = new JLabel("Confirm Password: ");
        this.passwordField2 = new JPasswordField(numColumns);
        this.authorityLabel = new JLabel("Authority Level");
        this.authority1 = new JRadioButton("1: Low Authority");
        this.authority2 = new JRadioButton("2: Medium Authority");
        this.authority3 = new JRadioButton("3: High Authority");
        this.radioGroup = new ButtonGroup();
        this.radioGroup.add(authority1);
        this.radioGroup.add(authority2);
        this.radioGroup.add(authority3);
        this.editUserButton = new JButton("Confirm Edit");
    }
    private void setListeners(){
        this.editUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUserEvent();
            }
        });
    }
    private void addUserEvent(){
        String username = this.usernameField.getSelectedItem().toString();
        String currentPassword = new String(this.oldPasswordField.getPassword());
        String password = new String(this.passwordField.getPassword());
        String password2 = new String(this.passwordField2.getPassword());
        int authority = 0;
        if(this.authority1.isSelected())
            authority = User.LOW_AUTHORITY;
        else if(this.authority2.isSelected())
            authority = User.MEDIUM_AUTHORITY;
        else if(this.authority3.isSelected())
            authority = User.HIGH_AUTHORITY;

        if(CurrentUser.getCurrentUser().getAuthority() < User.MAX_AUTHORITY && !UserController.attemptLogin(username, currentPassword)){
            JOptionPane.showMessageDialog
                    (EditUserPanel.this, "Incorrect Password",
                            "Edit User Failure", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(!password.equals(password2)){
            JOptionPane.showMessageDialog
                    (EditUserPanel.this, "Passwords do not match.",
                            "Edit User Failure", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int action =
                JOptionPane.showConfirmDialog(null,
                        "Please review and press okay if information is correct.",
                        "Review",
                        JOptionPane.OK_CANCEL_OPTION);
        if(action == JOptionPane.OK_OPTION) {
            AddUserEvent addUserEvent = new AddUserEvent(this, username, password, authority);
            this.addUserListener.userEventOccurred(addUserEvent);
        }
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
        layoutAuthorityLabel(gc);
        gc.gridy ++;
        layoutAuthority1(gc);
        gc.gridy ++;
        layoutAuthority2(gc);
        gc.gridy ++;
        layoutAuthority3(gc);
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
        super.add(this.usernameField, gc);
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
    private void layoutAuthorityLabel(GridBagConstraints gc){
        gc.weighty = 0.2;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,5);
        super.add(this.authorityLabel, gc);
    }
    private void layoutAuthority1(GridBagConstraints gc){
        gc.weighty = 0.2;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0, 0, 0, 0);
        super.add(this.authority1, gc);
    }
    private void layoutAuthority2(GridBagConstraints gc){
        gc.weighty = 0.2;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0, 0, 0, 0);
        super.add(this.authority2, gc);
    }
    private void layoutAuthority3(GridBagConstraints gc){
        gc.weighty = 0.2;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0, 0, 0, 0);
        super.add(this.authority3, gc);
    }
    private void layoutButton(GridBagConstraints gc){
        gc.weighty = 1;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,20);
        super.add(this.editUserButton, gc);
    }

    public void setEditUserListener(EditUserListener listener){
        this.addUserListener = listener;
    }
}
