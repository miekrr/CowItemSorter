public class CowSorter {
    private List<CowItem> _cowItems;

    public CowSortItems setItemsToSort(List<CowItem> cowItems) {
        _cowItems = cowItems;
        return new CowSortItems();
    }

    public class CowSortItems
    {
        public CowSortItems()
        {

        }

        public KeepItem getItemsToDelete(Map<String, CowItem> itemsToKeep) {
            StringBuilder sb = new StringBuilder();

            for (String key : itemsToKeep.keySet()) {
                for (CowItem c : _cowItems) {
                    if (key.equals(c.getItemName())) {
                        if (itemsToKeep.get(key).getItemUID().compareTo(c.getItemUID()) != 0) {
                            sb.append(c.getItemUID() + " " + c.getItemID() + "\n");
                        }
                    }
                }
            }
            return new KeepItem();
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

    public static class CowItemWriter
    {
        private String FILE_OUT_DATA;

        public static void SaveTo(String path) {
            try {
                writeFile(sb.toString());
            } catch (Exception ex) { }

        }

        private void writeFile(String data) throws IOException {
            FileWriter fw = new FileWriter(FILE_OUT_DATA);
            fw.write(data);
            fw.close();
        }
    }

}
