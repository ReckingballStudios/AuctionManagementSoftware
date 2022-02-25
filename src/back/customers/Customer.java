package back.customers;

public class Customer{

    public static final int STARTING_ID = 2000;
    //WARNING: Changing these strings will mess with the customer table search function... check and change there
    //if you change something here
    public static final String[] ASPECT_NAME = {
            "ID",
            "Last Name",
            "First Name",
            "Company",
            "Phone",
            "Email",
            "State/Prov",
            "City",
            "Address",
            "Drivers Lic",
            "Tax Number",
            "Auction Bid #"
    };

    private int id = -1;
    private String lastName = "";
    private String firstName = "";
    private String company = "";
    private String phoneNumber = "";
    private String phoneNumber2 = "";
    private String email = "";
    private String country = null;
    private String state = null;
    private String city = "";
    private String address = "";
    private String zipCode = "";
    private String driversLicense = "";
    private String taxNumber = "";
    private String auctionBiddingID = "";
    private String onlineBiddingID = "";


    public Customer(int id, String lastName, String firstName, String company, String phoneNumber,
                    String phoneNumber2, String email, String country, String state, String city, String address,
                    String zipCode, String driversLicense, String taxNumber, String auctionBiddingID,
                    String onlineBiddingID){
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.company = company;
        this.phoneNumber = phoneNumber;
        this.phoneNumber2 = phoneNumber2;
        this.email = email;
        this.country = country;
        this.state = state;
        this.city = city;
        this.address = address;
        this.zipCode = zipCode;
        this.driversLicense = driversLicense;
        this.taxNumber = taxNumber;
        this.auctionBiddingID = auctionBiddingID;
        this.onlineBiddingID = onlineBiddingID;
    }




    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getPhoneNumber2() {
        return phoneNumber2;
    }
    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getDriversLicense() {
        return driversLicense;
    }
    public void setDriversLicense(String driversLicense) {
        this.driversLicense = driversLicense;
    }
    public String getTaxNumber() {
        return taxNumber;
    }
    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }
    public String getAuctionBiddingID() {
        return auctionBiddingID;
    }
    public void setAuctionBiddingID(String auctionBiddingID) {
        this.auctionBiddingID = auctionBiddingID;
    }
    public String getOnlineBiddingID() {
        return onlineBiddingID;
    }
    public void setOnlineBiddingID(String onlineBiddingID) {
        this.onlineBiddingID = onlineBiddingID;
    }
}
