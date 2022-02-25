package front.items.table;

import back.items.Item;

import java.util.EventObject;

public class SelectItemEvent extends EventObject {

    private Item item = null;
    public SelectItemEvent(Object source, Item item) {
        super(source);
        this.item = item;
    }


    public Item getItem() {
        return item;
    }
    public void setItem(Item item) {
        this.item = item;
    }
}
