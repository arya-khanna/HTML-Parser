# Web Scraper for Township Wiki Items
### Created by Arya Khanna
This app is created with Java in Gradle, and uses JSoup for parsing HTML.
JSoup is a Java library for working with real-world HTML. It provides a very convenient API for fetching URLs and extracting and manipulating data, using the best of HTML5 DOM methods and CSS selectors.

#### To Start
- Before running the application update the imgPath and csvPath variables found in src/main/java/Scraper App.java (lines 13 - 15) to your desired locations
- Also add/update and desired attributes to the ArrayList infoCollected depending on the attirbutes you want to display in your csv. Currently only Title, Description and Image are available and are set by default. Title is a compulsory field and will always be included
- **To run the application (assuming gradle is installed) enter "gradle run" into your command line.**

