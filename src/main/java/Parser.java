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

        Elements items = doc.getElementsByClass("inventory-slot");

        for (Element e : items)
        {
            Attributes attribs = e.childNode(0).attributes();
            CowClothes item = new CowClothes();
            boolean validItem = false;
            for(Attribute a : attribs)
            {
                switch(a.getKey()) {
                    case "data-item-id":
                        System.out.println( a.getValue() );
                        item.setItemName(a.getValue());
                        break;
                    case "data-original-title":
                        Element element = e.html(a.getValue());
                        Element dat = element.children().get(3);
                        List<TextNode> str = dat.textNodes();

                        if (item.getItemName().startsWith("death") && !item.getItemName().endsWith("dust"))
                        {
                            String strGoldPerDeath = element.getAllElements().get(6).childNodes().get(1).toString()
                                    .trim();
                            strGoldPerDeath = strGoldPerDeath.substring(0, strGoldPerDeath.indexOf(" "));
                            item.goldRate =  Double.parseDouble(strGoldPerDeath);
                            validItem = true;
                        }

                        if (item.getItemName().startsWith("cow"))
                        {
                            if(str.size() == 3) {
                                String strGoldHour = str.get(1).toString().trim();
                                String strGoldRate = str.get(2).toString().trim();

                                String s = strGoldHour.substring(0, strGoldHour.indexOf(" "));
                                double goldPerHour = Double.parseDouble(s);
                                item.goldPercent = goldPerHour;
                                s = strGoldRate.substring(0, strGoldRate.indexOf("%"));

                                double goldGain = Double.parseDouble(s);
                                item.goldRate =  goldGain;
                                System.out.println("Gold per hour: " + goldPerHour + " gain: " + goldGain);
                                validItem = true;
                            }
                        }

                        break;
                }
                cowItems.add(item);
            }
            if(validItem)
                cowItems.add(item);
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
