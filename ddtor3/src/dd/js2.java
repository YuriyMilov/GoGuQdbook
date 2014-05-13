package dd;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rss.Feed;
import rss.FeedMessage;
import rss.RSSFeedParser;

public class js2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();
		
		response.setContentType("text/event-stream");
		response.setCharacterEncoding("UTF-8");
		String s=sh+"/xml_feed?tass.txt";
				
		RSSFeedParser parser = new RSSFeedParser(
				s);

		String s2="";
		Feed feed = parser.readFeed();
		int n = feed.getMessages().size();
		n = (int) (Math.random() * n);
		FeedMessage message = feed.getMessages().get(n);

		s = message.toString();
		String s1=s.replace("FeedMessage [title=", "<b>").replace(", description=", "</b> <br />");
		


		if(s.indexOf(", description=")>-1)
			s2=s.substring(s1.indexOf(", description=")+14);
	
		if(s1.indexOf(", link=")>0)
			s1=s1.substring(0,s1.indexOf(", link="));		
		
		PrintWriter writer = response.getWriter();
		writer.write("data: " + s1 + "\n\n");
		writer.close();
		response.flushBuffer();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
/*
 * <!DOCTYPE HTML> <html> <meta charset="utf-8"> <head> <script
 * type="text/javascript"> function start() { var eventSource = new
 * EventSource("TestServlet"); eventSource.onmessage = function(event) {
 * document.getElementById('foo').innerHTML =
 * document.getElementById('foo').innerHTML + " яя<br/>фф "+event.data; }; }
 * </script> </head> <body onload="start()"> Time: <span id="foo"></span>
 * </body> </html>
 */