import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CowSorter {
    private List<CowItem> _cowItems;

    public KeepItem setItemsToSort(List<CowItem> cowItems) {
        _cowItems = cowItems;

        return new KeepItem(cowItems);
    }


    public class KeepItem
    {
        private static final int MAX_HASH_MAP_SIZE = 25;

        List<CowItem> _cowItems;

        public KeepItem(List<CowItem> cowItems)
        {
            _cowItems = cowItems;
        }

        public CowItemWriter filterBestItems()
        {
            Map<String, CowItem> itemsToKeep = new HashMap<>(MAX_HASH_MAP_SIZE);

            for (CowItem c : _cowItems){
                CowItem cowItemStored = itemsToKeep.get(c.getItemName());

                if((cowItemStored == null) || cowItemStored.getItemValue() < c.getItemValue()){
                    itemsToKeep.put(c.getItemName(), c);
                }
            }
            CowSortItems sortItems = new CowSortItems(itemsToKeep);

            return sortItems.filterBestItems();
        }
    }

    public static class KeepItem
    {
        public static Map<String, CowItem> keepUsefulItems(List<CowItem> cowItems)
        {
            Map<String, CowItem> itemsToKeep = new HashMap<>(25);

            for (CowItem c : cowItems){
                CowItem cowItemStored = itemsToKeep.get(c.getItemName());

                if((cowItemStored == null) || cowItemStored.getItemValue() < c.getItemValue()){
                    itemsToKeep.put(c.getItemName(), c);
                }
            }

            return itemsToKeep;
        }
    }

    public class CowItemWriter
    {
        private String FILE_OUT_DATA;
        private String _data;

        public CowItemWriter(String data)
        {
            _data = data;
        }

        public void SaveTo(String path) {
            FILE_OUT_DATA = path;
            try {
                writeFile(_data);
            } catch (Exception ex) { }

        }

        private void writeFile(String data) throws IOException {
            FileWriter fw = new FileWriter(FILE_OUT_DATA);
            fw.write(data);
            fw.close();
        }
    }

}
