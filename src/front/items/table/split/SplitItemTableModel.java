package front.items.table.split;

import back.items.Item;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class SplitItemTableModel extends AbstractTableModel {

    private List<Item> items = null;

    public void setItems(List<Item> items) {
        this.items = items;
    }
    public List<Item> getItems(){
        return this.items;
    }

    @Override
    public String getColumnName(int column) {
        return Item.ASPECT_NAME[column];
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return Item.ASPECT_NAME.length;
    }

    @Override
    public Object getValueAt(int row, int column) {
        Item item = this.items.get(row);
        switch (column) {
            case 0:
                return item.getId();
            case 1:
                return item.getName();
            case 2:
                return item.getPrice();
            case 3:
                return item.getLotNumber();
            case 4:
                return item.getAuctionID();
            case 5:
                return item.isSold();
            case 6:
                return item.isPaid();
            case 7:
                return item.getPaymentMethod();
            case 8:
                return item.getPaymentMethod2();
            case 9:
                return item.getTaxRate();
            case 10:
                return item.getBuyerID();
            case 11:
                return item.getSellerID();
            case 12:
                return item.getSerialNumber();
            case 13:
                return item.getDescription();
            default:
                return null;
        }
    }
}
