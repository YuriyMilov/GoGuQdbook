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

public class js1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();
		String s="";
		
		response.setContentType("text/event-stream");
		response.setCharacterEncoding("UTF-8");
		
		int n = 0;//Integer.parseInt(stkl.blob_r("rt_size.txt").trim());
		
		if(Math.random() >0.5)
		{
			n = stkl.ar_rt.size();
			if (n<1)
				stkl.rfu_utf(sh+"/news");
			n = (int) (Math.random() * n);
			//String s = stkl.blob_r("rt_"+String.valueOf(n)+".txt");
			s = stkl.ar_rt.get(n);			
		}
		else
		{			
			n = stkl.ar_tass.size();
			if (n<1)
				stkl.rfu_utf(sh+"/news");
			n = (int) (Math.random() * n);
			//String s = stkl.blob_r("rt_"+String.valueOf(n)+".txt");
			s = stkl.ar_tass.get(n);			
		}
		int i = (int) (Math.random() * 5)+2;
		int j = (int) (Math.random() * 5)+2;
		int k = (int) (Math.random() * 5)+2;

		
		s="<div style=\"color:#FFFFFF; background-color:#"+String.valueOf(i).trim()+"0"+String.valueOf(j).trim()+"2"+String.valueOf(k).trim()+"2"+"\">&nbsp;&nbsp;"+s+" </div>";
		
		PrintWriter writer = response.getWriter();
		writer.write("data: " + s + "<!--n-"+n+"--->\n\n");
		writer.close();
		response.flushBuffer();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
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