package back.customers;

import controllers.util.Database;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerData {

    private List<Customer> customers = null;

    public CustomerData(){
        this.customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) throws SQLException {
        this.customers.add(0, customer);
        if(!Database.isOnline)
            return;

        Statement statement = Database.connection.createStatement();
        statement.executeUpdate(
                "INSERT INTO `customers` VALUES (" +
                customer.getId() + ", '" +
                customer.getLastName() + "', '" +
                customer.getFirstName() + "', '" +
                customer.getCompany() + "', '" +
                customer.getPhoneNumber() + "', '" +
                customer.getPhoneNumber2() + "', '" +
                customer.getEmail() + "', '" +
                customer.getCountry() + "', '" +
                customer.getState() + "', '" +
                customer.getCity() + "', '" +
                customer.getAddress() + "', '" +
                customer.getZipCode() + "', '" +
                customer.getDriversLicense() + "', '" +
                customer.getTaxNumber() + "', '" +
                customer.getAuctionBiddingID() + "', '" +
                customer.getOnlineBiddingID() + "');\n");
    }
    public void editCustomer(Customer customer) throws SQLException{
        Customer remove = null;
        for(Customer cus : customers)
            if(cus.getId() == customer.getId())
                remove = cus;

        this.customers.remove(remove);
        this.customers.add(0, customer);
        if(!Database.isOnline)
            return;

        Statement statement = Database.connection.createStatement();
        statement.executeUpdate(
                "UPDATE `customers` SET \n" +
                        "`lastName` = '" + customer.getLastName() + "',\n" +
                        "`firstName` = '" + customer.getFirstName() + "',\n" +
                        "`company` = '" + customer.getCompany() + "',\n" +
                        "`phoneNumber` = '" + customer.getPhoneNumber() + "',\n" +
                        "`phoneNumber2` = '" + customer.getPhoneNumber2() + "',\n" +
                        "`email` = '" + customer.getEmail() + "',\n" +
                        "`country` = '" + customer.getCountry() + "',\n" +
                        "`state` = '" + customer.getState() + "',\n" +
                        "`city` = '" + customer.getCity() + "',\n" +
                        "`address` = '" + customer.getAddress() + "',\n" +
                        "`zipCode` = '" + customer.getZipCode() + "',\n" +
                        "`driversLicense` = '" + customer.getDriversLicense() + "',\n" +
                        "`taxNumber` = '" + customer.getTaxNumber() + "',\n" +
                        "`auctionBiddingID` = '" + customer.getAuctionBiddingID() + "',\n" +
                        "`onlineBiddingID` = '" + customer.getOnlineBiddingID() + "'\n" +
                        "WHERE `id` = " + customer.getId() + ";\n"
        );
    }
    public void deleteCustomer(int id){
        Customer remove = null;
        for(Customer cus : customers)
            if(cus.getId() == id)
                remove = cus;

        this.customers.remove(remove);
    }

    @SuppressWarnings("ToArrayCallWithZeroLengthArrayArgument")
    public void saveToFile(File file) throws IOException {
        JSONArray customersJSONArray = new JSONArray();
        for(Customer customer : this.customers) {
            JSONObject customerJSON = new JSONObject();
            customerJSON.put("id", customer.getId());
            customerJSON.put("lastName", customer.getLastName());
            customerJSON.put("firstName", customer.getFirstName());
            customerJSON.put("company", customer.getCompany());
            customerJSON.put("phoneNumber", customer.getPhoneNumber());
            customerJSON.put("phoneNumber2", customer.getPhoneNumber2());
            customerJSON.put("email", customer.getEmail());
            customerJSON.put("country", customer.getCountry());
            customerJSON.put("state", customer.getState());
            customerJSON.put("city", customer.getCity());
            customerJSON.put("address", customer.getAddress());
            customerJSON.put("zipCode", customer.getZipCode());
            customerJSON.put("driversLicense", customer.getDriversLicense());
            customerJSON.put("taxNumber", customer.getTaxNumber());
            customerJSON.put("auctionBiddingID", customer.getAuctionBiddingID());
            customerJSON.put("onlineBiddingID", customer.getOnlineBiddingID());
            JSONArray customerPurchases = new JSONArray();
            JSONArray customerSales = new JSONArray();
            customerJSON.put("purchases", customerPurchases);
            customerJSON.put("sales", customerSales);

            customersJSONArray.put(customerJSON);
        }

        FileWriter writer = new FileWriter(file);
        customersJSONArray.write(writer, 3, 3);
        writer.flush();
    }
    public void loadFromFile(File file) throws IOException, ClassNotFoundException {
        this.customers.clear();
        Scanner scanner = new Scanner(file);
        String customersData = "";
        while(scanner.hasNextLine())
            customersData += scanner.nextLine();
        JSONArray customersJSONArray = new JSONArray(customersData);
        for(int i = 0; i < customersJSONArray.length(); i ++){
            JSONObject c = customersJSONArray.getJSONObject(i);


            this.customers.add(new Customer(c.getInt("id"), c.getString("lastName"),
                    c.getString("firstName"), c.getString("company"), c.getString("phoneNumber"),
                    c.getString("phoneNumber2"), c.getString("email"), c.getString("country"),
                    c.getString("state"), c.getString("city"), c.getString("address"),
                    c.getString("zipCode"), c.getString("driversLicense"), c.getString("taxNumber"),
                    c.getString("auctionBiddingID"), c.getString("onlineBiddingID")));
        }
    }

    public void loadFromDatabase() throws Exception{
        this.customers.clear();
        Statement statement = Database.connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM customers");
        while(result.next()){
            int id = result.getInt("id");
            String last = result.getString("lastName");
            String first = result.getString("firstName");
            String company = result.getString("company");
            String phone = result.getString("phoneNumber");
            String phone2 = result.getString("phoneNumber2");
            String email = result.getString("email");
            String country = result.getString("country");
            String state = result.getString("state");
            String city = result.getString("city");
            String address = result.getString("address");
            String zipCode = result.getString("zipCode");
            String driversLicense = result.getString("driversLicense");
            String taxNum = result.getString("taxNumber");
            String auctionBiddingID = result.getString("auctionBiddingID");
            String onlineBiddingID = result.getString("onlineBiddingID");

            this.customers.add(0,
                    new Customer(id, last, first, company, phone, phone2, email, country, state, city, address,
                            zipCode, driversLicense, taxNum, auctionBiddingID, onlineBiddingID));
        }
    }

    public List<Customer> getCustomers(){
        return customers;
    }
}