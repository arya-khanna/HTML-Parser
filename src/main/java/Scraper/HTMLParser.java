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
  private String imgFolderPath;
  private String itemsPage;
  public List<String> itemLinks;
  public List< Map<String, String> > itemsInformation;

  public HTMLParser(String itemsPage, String imgFolderPath) {
    this.itemsPage = itemsPage;
    this.imgFolderPath = imgFolderPath;
    this.itemLinks = new ArrayList<String>();
    this.itemsInformation = new ArrayList< Map<String, String> >();
  }

  public List< Map<String, String> > parseItems() {
    this.getItemLinks();
    int count = 0;
    // System.out.println(this.itemLinks);
    for (String url : itemLinks) {
      this.itemsInformation.add( this.getItemInformation(url) );
      count ++;
    }
    System.out.printf("------Parsed %d items------\n", count);
    return this.itemsInformation;
  }

  private ArrayList<String> getItemLinks() {
    Document doc;
    ArrayList<String> ret = new ArrayList<String>();
    try {
      doc = Jsoup.connect(this.itemsPage).get();
      Elements list = doc.getElementsByClass("mw-category").first().getElementsByTag("li");
      for (Element elem: list) {
        String link = elem.getElementsByTag("a").first().attr("abs:href");
        this.itemLinks.add(link);
        // System.out.println(link);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return ret;
  }

  private Map<String, String> getItemInformation(String url) {
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

  private String getDescription(Document doc) {
    String description = doc.getElementsByClass("mw-parser-output").first().getElementsByTag​("p").first().text();
    return description;
  }

  // TODO: if no image exists, try to search for any other exisitng images on the page
  private String getImage(Document doc, String title) {
    String link;
    try {
      link = doc.getElementsByClass("infobox").first().getElementsByTag("a").first().getElementsByTag("img").first().attr("abs:src");
    } catch (Exception e) {
      System.out.println(title + ":No Image found");
      return "No Image found";
    }
    
    return saveImage(link, title);
  }

  private String saveImage(String url, String title) {
    String filePath = this.imgFolderPath + title + ".png";
    try {
      InputStream in = new URL(url).openStream();
      Files.copy(in, Paths.get(filePath));
    } catch (IOException e) {
      System.out.printf("%s: %s\n",title,e);
      // this is the case where the file already exists, most of the time TODO
      return filePath;
    }
    return filePath;
  }

}
