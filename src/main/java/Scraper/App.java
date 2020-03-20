package Scraper;

import java.io.IOException;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {

        String itemsUrl = "https://townshiptale.gamepedia.com/Category:Items";
        String imgFolderPath = "/Users/ritukhanna/Downloads/TownshipImages/";

        HTMLParser parser = new HTMLParser(itemsUrl, imgFolderPath);
        List< Map<String, String> > info = parser.parseItems();
    }
}
