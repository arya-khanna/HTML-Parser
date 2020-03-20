package Scraper;

import java.io.IOException;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {

        String url = "https://townshiptale.gamepedia.com/Arrow";

        List<String> urlList = HTMLParser.getItems();
        System.out.println(urlList);


        // Map<String, String> elements = HTMLParser.getElements(url);
        // System.out.println(elements);
    }
}
