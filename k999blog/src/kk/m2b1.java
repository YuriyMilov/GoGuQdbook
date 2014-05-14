package kk;

import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import rss.Feed;
import rss.FeedMessage;
import rss.RSSFeedParser;

import javax.mail.internet.MimeUtility;

public class m2b1 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		//String sh = req.getScheme() + "://" + req.getServerName() + ":"	+ req.getServerPort() + req.getContextPath();
		try{			
		String s = "http://russian.rt.com/rss/",subj="", body="";	
		RSSFeedParser parser = new RSSFeedParser(s);
		Feed feed = parser.readFeed();	
		//for (FeedMessage message : feed.getMessages()) {
		//body=message.getDescription();
		//}
		int i=((int)(Math.random()*20))+1;
		FeedMessage message = feed.getMessages().get(i);
		body=message.getDescription();
		body=body.replace(" <a href=\"", " ").replace("\">Читать далее</a>", "\r\n\r\n.");
		subj=message.getTitle();
		s=subj+ ": "+ body;
		subj = MimeUtility.encodeText(subj, "utf8", null);
		stkl.smbj("k9992.tverskoy@blogger.com", subj, body);
		resp.setCharacterEncoding("UTF8"); 
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.write(s);
		out.flush();
		out.close();
		} catch (Exception e) {
			try {
				stkl.smbj("k9992.tverskoy@blogger.com", "emailing error", e.toString());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}	
	}
}
