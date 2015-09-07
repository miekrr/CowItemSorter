/**
 * Created by mike on 8/24/2015.
 */
public class Main {

    public static void main(String[] args) {

        if( args.length == 2) {
            final String inputHtmlPath = args[0];
            final String outputFilePath = args[1];

            Parser parser = new Parser(inputHtmlPath);
            List<CowItem> cowItems = parser.getItems();

            CowSorter sorter = new CowSorter();

            sorter.setItemsToSort(cowItems)
                    .filterBestItems()
                    .SaveTo(outputFilePath);
        }
        else
        {
            System.out.println("Usage: " +
                    new java.io.File(Main.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath())
                    .getName() +
                    " inventory.html items-to-be-deleted.txt");
        }
    }
}
