package front.users.add;

import java.util.EventObject;

public class AddUserEvent extends EventObject {

    private String username = null;
    private String password = null;
    private int authority = 0;

    public AddUserEvent(Object source) {
        super(source);
    }
    public AddUserEvent(Object source, String username, String password, int authority) {
        super(source);
        this.username = username;
        this.password = password;
        this.authority = authority;
    }



    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getAuthority() {
        return authority;
    }
    public void setAuthority(int authority) {
        this.authority = authority;
    }
}
