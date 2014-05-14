package rss;


public class ReadTest {
  public static void main(String[] args) {
	  //RSSFeedParser parser = new RSSFeedParser("http://www.vogella.com/article.rss");
	RSSFeedParser parser = new RSSFeedParser("http://itar-tass.com/rss/v2.xml");
	  
	//RSSFeedParser parser = new RSSFeedParser("http://forum.for-ua.com/feed.php?1,replies=1,type=rss");
	  
	//RSSFeedParser parser = new RSSFeedParser("http://twitter.com/Rusvesna");
	
	
	  
	  
    Feed feed = parser.readFeed();
    System.out.println(feed);
    for (FeedMessage message : feed.getMessages()) {
    	System.out.println(message);
    	System.out.print("\n\n");

    }

  }
} 