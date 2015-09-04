import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by mike on 8/24/2015.
 */
public class Parser {
    private String hData;
    List<CowItem> cowItems = new LinkedList<>();

    Parser(String filePath){
        byte[] data = new byte[0];
        try {
            data = Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        hData = new String(data);
    }

    public void getItems() {
        Document doc = Jsoup.parse(hData);

        Elements content = doc.getElementsByClass("inventory");
        Elements items = doc.getElementsByClass("inventory-slot");

        for (Element e : items)
        {
            Attributes attribs = e.childNode(0).attributes();
            for(Attribute a : attribs)
            {
                CowClothes item = new CowClothes();

                switch(a.getKey()) {
                    case "data-item-id":
                        System.out.println( a.getValue() );
                        item.setItemID(a.getValue());
                        break;
                    case "data-original-title":
                        Element element = e.html(a.getValue());
                        Element dat = element.children().get(3);
                        List<TextNode> str = dat.textNodes();

                        if (item.getItemID().startsWith("death"))
                        {
                            String strGoldPerDeath = element.getAllElements().get(6).child(1).toString().trim();
                            strGoldPerDeath = strGoldPerDeath.substring(0, strGoldPerDeath.indexOf(" "));
                        }

                        if (item.getItemID().startsWith("cow"))
                        {
                            if(str.size() == 3) {
                                String strGoldHour = str.get(1).toString().trim();
                                String strGoldRate = str.get(2).toString().trim();

                                String s = strGoldHour.substring(0, strGoldHour.indexOf(" "));
                                double goldPerHour = Double.parseDouble(s);
                                s = strGoldRate.substring(0, strGoldRate.indexOf("%"));
                                double goldGain = Double.parseDouble(s);
                                System.out.println("Gold per hour: " + goldPerHour + " gain: " + goldGain);
                            }
                        }

                        break;
                }
                cowItems.add(item);
            }
        }

        System.out.print(items.toString());
    }

    public void keepUsefulItems()
    {
        Map<String, CowItem> itemsToKeep = new HashMap<>(25);

        for (CowItem c : cowItems){
            CowItem cowItemStored = itemsToKeep.get(c.getItemID());
            if(cowItemStored.getItemValue() < c.getItemValue()){
                itemsToKeep.put(c.getItemID(), c);
            }
        }
    private void writeFile(String data) throws IOException {
        FileWriter fw = new FileWriter("ids-to-delete.txt");
        fw.write(data);
        fw.close();

    }
}
