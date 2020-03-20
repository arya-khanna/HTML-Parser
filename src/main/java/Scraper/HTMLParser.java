package Scraper;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
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

import java.io.FileWriter;
import java.io.IOException;

public class HTMLParser {
  private String imgFolderPath;
  private String itemsPage;
  public List<String> itemLinks;
  public List< Map<String, String> > itemsInformation;
  public List<String> infoCollected;

  public HTMLParser(String itemsPage, String imgFolderPath, List<String> infoCollected) {
    this.itemsPage = itemsPage;
    this.imgFolderPath = imgFolderPath;
    this.itemLinks = new ArrayList<String>();
    this.itemsInformation = new ArrayList< Map<String, String> >();
    this.infoCollected = infoCollected;
  }

  //
  public List< Map<String, String> > parseItems() {
    System.out.printf("------Beginning Parsing items------\n");
    this.getItemLinks();
    int count = 0;
    // System.out.println(this.itemLinks);
    for (String url : itemLinks) {
      this.itemsInformation.add( this.getItemInformation(url) );
      count ++;
    }
    System.out.printf("-----------Parsed %d items----------\n\n", count);
    return this.itemsInformation;
  }

  public String exportToCSV(String filename) throws IOException{
    System.out.println("----------Beginning Export----------");
    FileWriter export = new FileWriter(filename);
    
    String line = "";
    for (int i = 0; i < this.infoCollected.size(); i ++) {
        line += this.infoCollected.get(i);
        if (i != this.infoCollected.size() - 1) {
            line += ",";
        }
    }
    line += "\n";
    export.write(line);

    String title;
    int count = 0;
    for (Map<String, String> map : this.itemsInformation) {
        line = "";
        title = map.get("Title");
        for (int i = 0; i < this.infoCollected.size(); i++) {
            String attr = this.infoCollected.get(i);
            line += map.get(attr).replace(",", "");
            if (i != this.infoCollected.size() - 1) {
                line += ",";
            }
        }
        line += "\n";
        System.out.printf("Exported To CSV: %s\n",title);
        export.write(line);
        count++;
    }
    export.close();
    System.out.printf("----Successfully Exported %d items to CSV----\n\n",count);
    return filename;
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

      //title - should always have title
      String title = getTitle(doc);
      ret.put("Title", title);

      //description
      if (this.infoCollected.contains("Description")) ret.put("Description", getDescription(doc));
      //image
      if (this.infoCollected.contains("Title")) ret.put("Image path", getImage(doc, title));

    } catch (Exception e) {
      e.printStackTrace();
    }

    return ret;
  }

  private String getTitle(Document doc) {
    return doc.getElementById("firstHeading").text();
  }

  private String getDescription(Document doc) {
    String description = doc.getElementsByClass("mw-parser-output").first().getElementsByTag​("p").first().text();
    return description;
  }

  // TODO: if no image exists, try to search for any other exisitng images on the page, look at example of shield
  // Example shield, look at including all types of shields for example
  private String getImage(Document doc, String title) {
    String link;
    try {
      link = doc.getElementsByClass("infobox").first().getElementsByTag("a").first().getElementsByTag("img").first().attr("abs:src");
    } catch (Exception e) {
      System.out.println("NO IMAGE FOUND: " + title);
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
      System.out.printf("ERROR: %s | %s\n",title,e);
      // this is the case where the file already exists, most of the time TODO
      return filePath;
    }
    return filePath;
  }

}
