/**
 * Created by mike on 8/24/2015.
 */
public class Main {

    public static void main(String[] args) {

        if( args.length == 2) {
            Parser parser = new Parser("D:/SourceCode/cow-site-parser/inventory.html");
            List<CowItem> cowItems = parser.getItems();

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
