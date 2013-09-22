package guestbook;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class qq_s extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		ServletOutputStream out = resp.getOutputStream();
		resp.setContentType("text/xml; charset=UTF8");
		resp.setCharacterEncoding("UTF8");
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();
		String s3 = req.getParameter("p3");
		String s4 = req.getParameter("p4");
		String s = "";
		if (s3 == null && s4 == null) {
			
			//if(stat.sr.trim().equals(""))
				//stat.sr=stat.rff("83.owl");
				
			stat.get_owl83(stat.sr);
			s = stat.sowl;
			
			
			byte[] b = s.getBytes("UTF8");
			out.write(b);
			return;
		}

		if (s3.equals("load")) {
			//stat.stop = stat.stop + "<br> <b><i> - </i></b>" + s4;
			s = stat.rfu_utf(sh + "/" + URLEncoder.encode(s4, "UTF-8"));
			if (s.indexOf("Ъ") > -1 && s.length() > s.indexOf("Ъ"))
				s = s.substring(s.indexOf("Ъ") + 1);
			stat.sr = s;
			//stat.text83(s, req, resp);
			stat.get_owl83(s);
			stat.stop = stat.stop + "<br> <b><i> - </i></b> " + s;
			stat.page(req, resp, s.replace("\r\n", "<br>"));
			
			return;
		}

		if (s3.equals("add")) {
			//stat.stop = stat.stop + "<br> <b><i> - </i></b>" + s4;
			s = stat.rfu_utf(sh + "/" + URLEncoder.encode(s4, "UTF-8"));
			if (s.indexOf("Ъ") > -1 && s.length() > s.indexOf("Ъ"))
				s = s.substring(s.indexOf("Ъ") + 1);
			stat.stop = stat.stop + "<br> <b><i> - </i></b> " + s;
			stat.page(req, resp, s.replace("\r\n", "<br>"));
			
			return;
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doPost(req, resp);
	}

	private static final long serialVersionUID = 1L;
}