package rss;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;


public class RSSFeedParser {
  static final String TITLE = "title";
  static final String DESCRIPTION = "description";
  static final String CHANNEL = "channel";
  static final String LANGUAGE = "language";
  static final String COPYRIGHT = "copyright";
  static final String LINK = "link";
  static final String AUTHOR = "author";
  static final String ITEM = "item";
  static final String PUB_DATE = "pubDate";
  static final String GUID = "guid";

  final URL url;

  public RSSFeedParser(String feedUrl) {
    try {
      this.url = new URL(feedUrl);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  public Feed readFeed() {
    Feed feed = null;
    try {
      boolean isFeedHeader = true;
      // Set header values intial to the empty string
      String description = "";
      String title = "";
      String link = "";
      String language = "";
      String copyright = "";
      String author = "";
      String pubdate = "";
      String guid = "";

      // First create a new XMLInputFactory
      XMLInputFactory inputFactory = XMLInputFactory.newInstance();
      // Setup a new eventReader
      InputStream in = read();
      XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
      // read the XML document
      while (eventReader.hasNext()) {
        XMLEvent event = eventReader.nextEvent();
        if (event.isStartElement()) {
          String localPart = event.asStartElement().getName()
              .getLocalPart();
          
      int n=0;
      if (localPart.equals(ITEM))
    	  n=1;
      if (localPart.equals(TITLE))
    	  n=2;
      if (localPart.equals(DESCRIPTION))
    	  n=3;
      if (localPart.equals(LINK))
    	  n=4;
      if (localPart.equals(GUID))
    	  n=5;
      if (localPart.equals(LANGUAGE))
    	  n=6;
      if (localPart.equals(AUTHOR))
    	  n=7;
      if (localPart.equals(PUB_DATE))
    	  n=8;
      if (localPart.equals(COPYRIGHT))
    	  n=9;

          
          switch (n) {
          case 1:
            if (isFeedHeader) {
              isFeedHeader = false;
              feed = new Feed(title, link, description, language,
                  copyright, pubdate);
            }
            event = eventReader.nextEvent();
            break;
          case 2:
            title = getCharacterData(event, eventReader);
            break;
          case 3:
            description = getCharacterData(event, eventReader);
            break;
          case 4:
            link = getCharacterData(event, eventReader);
            break;
          case 5:
            guid = getCharacterData(event, eventReader);
            break;
          case 6:
            language = getCharacterData(event, eventReader);
            break;
          case 7:
            author = getCharacterData(event, eventReader);
            break;
          case 8:
            pubdate = getCharacterData(event, eventReader);
            break;
          case 9:
            copyright = getCharacterData(event, eventReader);
            break;
          }
        } else if (event.isEndElement()) {
          if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
            FeedMessage message = new FeedMessage();
            message.setAuthor(author);
            message.setDescription(description);
            message.setGuid(guid);
            message.setLink(link);
            message.setTitle(title);
            feed.getMessages().add(message);
            event = eventReader.nextEvent();
            continue;
          }
        }
      }
    } catch (XMLStreamException e) {
      throw new RuntimeException(e);
    }
    return feed;
  }

  private String getCharacterData(XMLEvent event, XMLEventReader eventReader)
      throws XMLStreamException {
    String result = "";
    event = eventReader.nextEvent();
    if (event instanceof Characters) {
      result = event.asCharacters().getData();
    }
    return result;
  }

  private InputStream read() {
    try {
      return url.openStream();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
} 