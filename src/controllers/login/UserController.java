package controllers.login;

import back.users.UserData;
import back.users.User;
import back.users.CurrentUser;
import controllers.util.Database;
import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@SuppressWarnings("ConstantConditions")
public class UserController {
    public static boolean attemptLogin(String username, String password){
        boolean successBool = false;

        ArrayList<User> users = null;
        if(Database.isOnline)
            users = readUsers();
        else
            users = readUsersFromJSON();


        for(User user : users){
            if(user.getUsername().compareTo(username) != 0)
                continue;

            if(user.getPassword().compareTo(UserData.generateHash(password)) == 0){
                CurrentUser.setCurrentUser(new User(user));
                successBool = true;
                break;
            }
        }
        return successBool;
    }
    public static ArrayList<User> readUsers(){
        ArrayList<User> users = new ArrayList<>();
        try{
            Statement statement = Database.connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM employees");
            while(result.next()){
                String username = result.getString("username");
                String passwordHash = result.getString("password");
                int authority = result.getInt("authority");
                users.add(new User(username, passwordHash, authority));
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }

        return users;
    }
    private static ArrayList<User> readUsersFromJSON() {
        ArrayList<User> users = new ArrayList<>();
        JSONArray usersJSONArray = UserData.read();
        for(int i = 0; i < usersJSONArray.length(); i ++){
            JSONObject userJSON = usersJSONArray.getJSONObject(i);

            String username = userJSON.getString("username");
            String password = userJSON.getString("password");
            int authority = userJSON.getInt("authority");

            users.add(new User(username, password, authority));
        }
        return users;
    }

    public static void addUser(String username, String password, int authority) throws SQLException {
        UserData.addUser(username, password, authority);
    }
    public static void editUser(String username, String password, int authority) throws SQLException {
        UserData.editUser(username, password, authority);
    }
}
