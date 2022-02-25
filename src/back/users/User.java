package back.users;

public class User {

    public static final int LOW_AUTHORITY = 1;
    public static final int MEDIUM_AUTHORITY = 2;
    public static final int HIGH_AUTHORITY = 3;
    public static final int MAX_AUTHORITY = 5;

    private String username = null;
    private String password = null;
    private int authority = 0;

    public User(String username, String password, int authority){
        this.username = username;
        this.password = password;
        this.authority = authority;
    }
    public User(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authority = user.getAuthority();
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
