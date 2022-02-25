package back.users;

import controllers.util.Database;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class UserData {

    public static JSONArray read(){
        try {
            Scanner scanner = new Scanner(new File("data/login.json"));
            String loginData = "";
            while (scanner.hasNextLine())
                loginData += scanner.nextLine();

            return new JSONArray(loginData);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
    public static void write(JSONArray usersJSONArray){
        try {
            FileWriter loginWriter = new FileWriter(new File("data/login.json"));
            usersJSONArray.write(loginWriter, 3, 2);
            loginWriter.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public static String generateHash(String password){
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.reset();
            byte[] hash = digest.digest(password.getBytes());
            return bytesToStringHex(hash);
        } catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return null;
    }
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static String bytesToStringHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for(int j = 0; j < bytes.length; j++){
            int v = bytes[j] & 0xFF;
            hexChars[j*2] = hexArray[v >>> 4];
            hexChars[j*2+1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


    public static void addUser(String username, String password, int authority) throws SQLException {
        if(!Database.isOnline)
            return;

        Statement statement = Database.connection.createStatement();
        statement.executeUpdate(
                "INSERT INTO `employees` VALUES ('" +
                        username + "', '" +
                        generateHash(password) + "', " +
                        authority + ");\n"
        );
    }
    public static void editUser(String username, String password, int authority) throws SQLException{
        if(!Database.isOnline)
            return;

        Statement statement = Database.connection.createStatement();
        if(password.equals("")){
            statement.executeUpdate(
                    "UPDATE `employees` SET \n" +
                            "`authority` = " + authority + "\n" +
                            "WHERE `username` = '" + username + "';\n"
            );
        } else {
            statement.executeUpdate(
                    "UPDATE `employees` SET \n" +
                            "`password` = '" + generateHash(password) + "',\n" +
                            "`authority` = " + authority + "\n" +
                            "WHERE `username` = '" + username + "';\n"
            );
        }
    }
}
