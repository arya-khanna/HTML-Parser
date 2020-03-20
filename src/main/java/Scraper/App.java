package Scraper;

import java.io.IOException;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {

        String itemsUrl = "https://townshiptale.gamepedia.com/Category:Items";
        String imgPath = "/Users/ritukhanna/Downloads/Township/TownshipImages/";
        String csvPath = "/Users/ritukhanna/Downloads/Township/items.csv";

        // current options are "Title" "Description" "Image Path" 
        // Always includes Title
        List<String> infoCollected = new ArrayList<String>(); 
        infoCollected.add("Title"); infoCollected.add("Description"); infoCollected.add("Image path");

        HTMLParser parser = new HTMLParser(itemsUrl, imgPath, infoCollected);
        
        parser.parseItems();
        
        try {
            parser.exportToCSV(csvPath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Parser could not export to CSV");
            return;
        }
    }
}
