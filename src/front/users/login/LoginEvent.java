package front.users.login;

import java.util.EventObject;

public class LoginEvent extends EventObject {

    private String username = null;
    private String password = null;

    LoginEvent(Object source) {
        super(source);
    }
    LoginEvent(Object source, String username, String password) {
        super(source);

        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
}
