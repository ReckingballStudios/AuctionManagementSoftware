package back.auctions;

public class Auction {
    //WARNING: Changing these strings will mess with the auctions table search function... check and change there
    //if you change something here
    public static final String[] ASPECT_NAME = {
            "ID",
            "Name",
            "Date",
            "Last Name",
            "First Name",
            "Country",
            "State/Prov",
            "City",
            "Address",
            "Zip Code",
            "Tax Rate",
            "Internet Fee"
    };

    private int auctionID = -1;
    private String name = "";
    private String lastName = "";
    private String firstName = "";
    private String country = "";
    private String state = "";
    private String city = "";
    private String address = "";
    private String zipCode = "";
    private String date = null;
    private float taxRate = 0.0f;
    private float internetFee = 0.0f;
    private boolean closed = false;


    public Auction
            (int auctionID, String name, String date, String lastName, String firstName, String country, String state,
             String city, String address, String zipCode, float taxRate, float internetFee, boolean closed){
        this.auctionID = auctionID;
        this.name = name;
        this.date = date;
        this.lastName = lastName;
        this.firstName = firstName;
        this.country = country;
        this.state = state;
        this.city = city;
        this.address = address;
        this.zipCode = zipCode;
        this.taxRate = taxRate;
        this.internetFee = internetFee;
        this.closed = closed;
    }



    public int getAuctionID() {
        return auctionID;
    }
    public void setAuctionID(int auctionID) {
        this.auctionID = auctionID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public float getTaxRate() {
        return taxRate;
    }
    public void setTaxRate(float taxRate) {
        this.taxRate = taxRate;
    }
    public float getInternetFee() {
        return internetFee;
    }
    public void setInternetFee(float internetFee) {
        this.internetFee = internetFee;
    }
    public boolean isClosed() {
        return closed;
    }
    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
