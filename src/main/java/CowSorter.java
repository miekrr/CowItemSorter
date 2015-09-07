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


}
