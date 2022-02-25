package back.items;

public class Item {

    public static final String[] ASPECT_NAME = {
            "ID",
            "Name",
            "Price",
            "Lot #",
            "AuctionID",
            "Is Sold",
            "Is Paid",
            "Pay Method",
            "Check Number",
            "Tax Rate",
            "BuyerID",
            "SellerID",
            "Serial #",
            "Description"
    };



    private int id = -1;
    private String name = "";
    private int lotNumber = -1;
    private int buyerID = -1;
    private int sellerID = -1;
    private float price = -1.0f;
    private int quantity = 1;
    private int auctionID = -1;
    private boolean isSold = false;
    private boolean isSoldOnline = false;
    private boolean isPaid = false;
    private String paymentMethod = "";
    private String paymentMethod2 = "";
    private float taxRate = 0.0f;
    private float premium = 0.0f;
    private String serialNumber = "";
    private String description = "";

    public Item(
            int id , String name, float price, int quantity, int lotNumber, int auctionID,
            boolean isSold, boolean isSoldOnline, boolean isPaid, String paymentMethod, String paymentMethod2,
            float taxRate, float premium, int buyerID, int sellerID,
            String serialNumber, String description
    ){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.lotNumber = lotNumber;
        this.auctionID = auctionID;
        this.isSold = isSold;
        this.isSoldOnline = isSoldOnline;
        this.isPaid = isPaid;
        this.paymentMethod = paymentMethod;
        this.paymentMethod2 = paymentMethod2;
        this.taxRate = taxRate;
        this.premium = premium;
        this.buyerID = buyerID;
        this.sellerID = sellerID;
        this.serialNumber = serialNumber;
        this.description = description;
    }







    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getLotNumber() {
        return lotNumber;
    }
    public void setLotNumber(int lotNumber) {
        this.lotNumber = lotNumber;
    }
    public int getAuctionID() {
        return auctionID;
    }
    public void setAuctionID(int auctionID) {
        this.auctionID = auctionID;
    }
    public boolean isSold() {
        return isSold;
    }
    public String getSoldString(){
        if(isSold)
            return "Yes";
        else
            return "No";
    }
    public void setSold(boolean sold) {
        isSold = sold;
    }
    public boolean isSoldOnline() {
        return isSoldOnline;
    }
    public void setSoldOnline(boolean soldOnline) {
        isSoldOnline = soldOnline;
    }
    public String getSoldOnlineString(){
        if(isSoldOnline)
            return "Yes";
        else
            return "No";
    }
    public boolean isPaid() {
        return isPaid;
    }
    public String getPaidString(){
        if(isPaid)
            return "Yes";
        else
            return "No";
    }
    public void setPaid(boolean paid) {
        isPaid = paid;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public String getPaymentMethod2() {
        return paymentMethod2;
    }
    public void setPaymentMethod2(String paymentMethod2) {
        this.paymentMethod2 = paymentMethod2;
    }
    public float getTaxRate() {
        return taxRate;
    }
    public void setTaxRate(float taxRate) {
        this.taxRate = taxRate;
    }
    public float getPremium() {
        return premium;
    }
    public void setPremium(float premium) {
        this.premium = premium;
    }
    public int getBuyerID() {
        return buyerID;
    }
    public void setBuyerID(int buyerID) {
        this.buyerID = buyerID;
    }
    public int getSellerID() {
        return sellerID;
    }
    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }
    public String getSerialNumber() {
        return serialNumber;
    }
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
