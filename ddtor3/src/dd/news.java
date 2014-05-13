package dd;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rss.Feed;
import rss.FeedMessage;
import rss.RSSFeedParser;

public class news extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String s = "http://russian.rt.com/rss/";
		RSSFeedParser parser = new RSSFeedParser(s);
		Feed feed = parser.readFeed();
		stkl.ar_rt=null;
		stkl.ar_rt= new ArrayList<String>();
		stkl.ar_rt.clear();
		for (FeedMessage message : feed.getMessages()) {
			s="<br/>&nbsp;&nbsp;<b> " + message.getTitle()+"</b><br/>&nbsp;&nbsp;<div style=\"text-align:right;border-width:15px; \"><i><font color=#B2B2B2>"+message.getDescription().replace(">Читать далее</a>", " target=\"_new\">Читать далее</a>&nbsp;&nbsp;</font></i></div>").replace("<a href", "&nbsp;&nbsp;<br/><a href");
			stkl.ar_rt.add(s);
		}
		s = "http://itar-tass.com/rss/v2.xml";
		parser = new RSSFeedParser(s);
		feed = parser.readFeed();
		stkl.ar_tass=null;
		stkl.ar_tass= new ArrayList<String>();
		stkl.ar_tass.clear();
		for (FeedMessage message : feed.getMessages()) {
			s="<br/>&nbsp;&nbsp;<b> " + message.getTitle()+"</b><br/>&nbsp;&nbsp;<div style=\"text-align:right;border-width:15px; \"><i><font color=#B2B2B2>"+message.getDescription();
			s=s+"&nbsp;&nbsp;<br/><a href=\""+message.getLink()+"\" target=\"_new\">Читать далее</a>&nbsp;&nbsp;</i></div>";
			stkl.ar_tass.add(s);
		}
		PrintWriter out = resp.getWriter();
		resp.setCharacterEncoding("UTF8"); 
		resp.setContentType("text/html");
		out.write("ok");
		out.flush();
		out.close();
	}
}