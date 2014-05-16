package rss;

public class ReadTest {
	public static void main2(String[] args) {
			System.out.println (Math.random());
	}	
	public static void main(String[] args) {
		
		String s = "http://russian.rt.com/rss/";
		s="http://itar-tass.com/rss/v2.xml";
		s="http://forum.for-ua.com/feed.php?1,replies=1,type=rss";
		s="http://forum.for-ua.com/feed.php?1,3894877,type=rss";
		s="http://forum.for-ua.com/feed.php?1,3724341,type=rss";
		
		

		RSSFeedParser parser = new RSSFeedParser(s);
		Feed feed = parser.readFeed();
		int i=feed.getMessages().size();
		FeedMessage mm= feed.getMessages().get(1);
		//s=mm.toString();
		s=""; for(int k=i;k>0;k--)		{			FeedMessage message= feed.getMessages().get(k-1);			s=s+"<b>" + "<a href="+message.getGuid()+">"+message.getTitle()+"</a><br/>"+ message.getDescription()+"<br/><br/>\r\n\r\n ";		}
				
		//s="";for (FeedMessage message : feed.getMessages()) {	s=s+"<b>" + message.getTitle()+"</b><br/>\r\n" + message.getDescription()+"<br/><br/>\r\n\r\n ";}
		
		System.out.println (s);
		//sendFromGMail("ymilov", "quicklyd0ne", new String[]{"qdone@rogers.com"}, "subject", "b<b>od</b>y");
	}
}