package Scraper;

import java.io.IOException;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.io.InputStream;
import java.awt.Image;
import java.nio.file.Paths;
import java.nio.file.Files;

public class HTMLParser {
  private static String imgFileFolder = "/Users/ritukhanna/Downloads/TownshipImages/";

  public static List<String> getItems() {
    return null;
  }

  public static Map<String, String> getElements(String url) {
    Map<String, String> ret = new HashMap<String, String>();

    Document doc;
    try {
      doc = Jsoup.connect(url).get();

      //title
      String title = doc.getElementById("firstHeading").text();
      ret.put("title", title);

      //description
      ret.put("description", getDescription(doc));

      //image
      ret.put("image path", getImage(doc, title));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return ret;
  }

  private static String getDescription(Document doc) {
    String description = doc.getElementsByClass("mw-parser-output").first().getElementsByTag​("p").first().text();
    return description;
  }

  private static String getImage(Document doc, String title) {
    String link = doc.getElementsByClass("infobox").first().getElementsByTag("a").first().getElementsByTag("img").first().attr("abs:src");
    return saveImage(link, title);
  }

  private static String saveImage(String url, String title) {
    Image image = null;
    String filePath = imgFileFolder + title + ".png";
    try {
      InputStream in = new URL(url).openStream();
      Files.copy(in, Paths.get(filePath));
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    return filePath;
  }

}
