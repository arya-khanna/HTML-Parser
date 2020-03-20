package Scraper;

import java.io.IOException;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {

        String homePage = "https://townshiptale.gamepedia.com/Category:Items";
        String url = "https://townshiptale.gamepedia.com/Arrow";

        List<String> itemUrls = HTMLParser.getItems(homePage);
        
        List<Map<String, String>> attrList = new ArrayList<Map<String, String>>();

        // for (int i = 0; i < urlList.size(); i ++) {
        //     attrList.add(HTMLParser.getInformation(urlList.get(i)));
        // }
        
        System.out.println(itemUrls);


        // Map<String, String> elements = HTMLParser.getElements(url);
        // System.out.println(elements);
    }
}
