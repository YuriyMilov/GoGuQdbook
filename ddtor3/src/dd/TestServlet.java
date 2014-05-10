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

public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		response.setContentType("text/event-stream");
		response.setCharacterEncoding("UTF-8");
		
		
		RSSFeedParser parser = new RSSFeedParser(
				"http://yugovostok-ua.su/rss.xml");

	
		Feed feed = parser.readFeed();
		int n = feed.getMessages().size();
		n = (int) (Math.random() * n);
		FeedMessage message = feed.getMessages().get(n);

		String s = message.toString();
		String s1=s.replace("FeedMessage [title=", "<H2>sts - ").replace(", description=", "</H2>");
		
		String s2="";
		if(s.indexOf(", description=")>-1)
				s2=s.substring(s.indexOf(", description=")+14);
		
		
		
		PrintWriter writer = response.getWriter();
		writer.write("data: " + s1 + s2 + "\n\n");
		writer.close();
		response.flushBuffer();
		try {
		Thread.sleep(5000);
		} catch (Exception e) {
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