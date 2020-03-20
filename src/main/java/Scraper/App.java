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
        String[] infoCollected = new String[]{"Title", "Description", "Image path"};

        HTMLParser parser = new HTMLParser(itemsUrl, imgPath);
        List< Map<String, String> > info = parser.parseItems();
        try {
            parser.exportToCSV(csvPath, infoCollected);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Parser could not export to CSV");
            return;
        }
    }
}
