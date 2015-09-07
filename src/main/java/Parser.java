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

    public Parser(String filePath){
        byte[] data = new byte[0];
        try {
            data = Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        hData = new String(data);
    }

    private boolean setDataForCowItem(Element e, CowClothes item, Attribute a) {
        switch(a.getKey()) {
            case "data-item-id":
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
                    return true;
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
                        return true;
                    }
                }
                if (item.getItemName().startsWith("pax_")) {
                    element.getAllElements();
                    String strGoldHour = element.getAllElements().get(6).childNodes().get(1).toString().trim();
                    String strGoldRate = element.getAllElements().get(6).childNodes().get(4).toString().trim();

                    String s = strGoldHour.substring(0, strGoldHour.indexOf(" "));
                    double goldPerHour = Double.parseDouble(s);
                    item.goldPercent = goldPerHour;
                    s = strGoldRate.substring(0, strGoldRate.indexOf("%"));

                    double goldGain = Double.parseDouble(s);
                    item.goldRate =  goldGain;
                    return true;
                }
                if (item.getItemName().startsWith("bull")  && !item.getItemName().endsWith("box") &&
                        item.getItemName().compareTo("bullgrillz") != 0) {
                    String strGoldRate = element.getAllElements().get(5).childNodes().get(4).toString().trim();
                    String s = strGoldRate.substring(0, strGoldRate.indexOf(" "));
                    double goldPerHour = Double.parseDouble(s);
                    item.goldPercent = goldPerHour;
                    return true;
                }
                break;
            case "data-item-uid":
                item.setItemUID(a.getValue());
                break;
            case "data-item-id-numeric":
                item.setItemID(a.getValue());
                break;
            case "data-item-recycle":
                item.setItemRecyclable(a.getValue().compareTo("1") == 0 ? true : false);
                break;
        }
        return false;
    }

    private void parseHtml() {
        Document doc = Jsoup.parse(hData);

        Elements items = doc.getElementsByClass("inventory-slot");

        for (Element e : items)
        {
            Attributes attribs = e.childNode(0).attributes();
            CowClothes item = new CowClothes();
            boolean validItem = false;
            for(Attribute a : attribs)
            {
            }
            if(validItem)
                cowItems.add(item);
        }

        System.out.print(items.toString());
        keepUsefulItems();
    }

    public void keepUsefulItems()
    {
        Map<String, CowItem> itemsToKeep = new HashMap<>(25);

        for (CowItem c : cowItems){
            CowItem cowItemStored = itemsToKeep.get(c.getItemName());

            if((cowItemStored == null) || cowItemStored.getItemValue() < c.getItemValue()){
                itemsToKeep.put(c.getItemName(), c);
            }
        }

        getItemsToDelete(itemsToKeep);
    }

    private void getItemsToDelete(Map<String, CowItem> itemsToKeep) {
        Iterator it = itemsToKeep.entrySet().iterator();
        StringBuilder sb = new StringBuilder();

        for( String key:  itemsToKeep.keySet() ) {
            for( CowItem c : cowItems) {
                if (key.equals(c.getItemName()))
                {
                    if( itemsToKeep.get(key).getItemUID().compareTo(c.getItemUID()) != 0)
                    {
                        sb.append(c.getItemUID() + " " + c.getItemID() + "\n");
                    }
                    else {
                        System.out.println("This works ! " + c.getItemUID());
                    }
                }
            }
        }
        try{
            writeFile(sb.toString());
        } catch (Exception ex) {

        }
    }

    private void writeFile(String data) throws IOException {
        FileWriter fw = new FileWriter("ids-to-delete.txt");
        fw.write(data);
        fw.close();

    }
}
