package controllers.util;

import controllers.main.CustomerController;
import controllers.main.ItemController;

import java.sql.Connection;

public class Database implements Runnable{
    public static final String DATABASE = "jdbc:mysql://localhost:3306/resource_auction_test";
    public static final String DB_PASSWORD = "%password%";
    public static Connection connection = null;
    private DatabaseListener databaseListener = null;
    /**
     * @see #updatingFromDatabase changes on and off during the program
     * @see #isOnline is set at the very beginning stays the same throughout the program
     */
    public static volatile boolean updatingFromDatabase = true;
    public static volatile boolean isOnline = true;


    public Database(){
        Thread thread = new Thread(this,"Database");
        thread.start();
    }


    @Override
    public void run() {
        long lastTime = System.currentTimeMillis();
        //Increase refresh rate to increase performance
        //Decrease refresh rate to update more frequently
        int refreshRate = 5000;
        while(true){
            if(System.currentTimeMillis() - lastTime > refreshRate) {
                lastTime = System.currentTimeMillis();
                if(!updatingFromDatabase || !isOnline)
                    continue;

                try {
                    CustomerController.loadCustomersFromDatabase();
                    ItemController.loadItemsFromDatabase();

                    if(databaseListener != null)
                        databaseListener.databaseEventOccurred();

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    public void setDatabaseListener(DatabaseListener listener){
        this.databaseListener = listener;
    }
}
