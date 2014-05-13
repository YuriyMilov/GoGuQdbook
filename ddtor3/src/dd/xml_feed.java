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

public class xml_feed extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		String s=stkl.blob_r(request.getQueryString());
		PrintWriter out = response.getWriter();
		out.write(s);
		out.flush();
		out.close();
	}
}