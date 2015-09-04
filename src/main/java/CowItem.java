/**
 * Created by mike on 8/24/2015.
 */
public abstract class  CowItem implements CowItemValue {

    private String itemName;
    private String itemID;
    private String itemUID;
    private boolean itemRecyclable;

    public CowItem(){
        itemName = "";
        itemID = "";
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemUID() {
        return itemUID;
    }

    public void setItemUID(String itemUID) {
        this.itemUID = itemUID;
    }

    public boolean isItemRecyclable() {
        return itemRecyclable;
    }

    public void setItemRecyclable(boolean itemRecyclable) {
        this.itemRecyclable = itemRecyclable;
    }
}
