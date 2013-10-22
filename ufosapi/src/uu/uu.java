package uu;


import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class uu extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		//String sh = req.getScheme() + "://" + req.getServerName() + ":"
		//		+ req.getServerPort() + req.getContextPath();
	
		ServletOutputStream out = resp.getOutputStream();
		resp.setContentType("text/xml; charset=UTF8");
		resp.setCharacterEncoding("UTF8");
resp.sendRedirect("http://99.240.96.205/api/ws.asmx");
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doPost(req, resp);
	}

	private static final long serialVersionUID = 1L;
}