package cct;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class cct extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String s = req.getServerName();
		resp.setContentType("plain/html; charset=UTF8");
		resp.setCharacterEncoding("UTF8");
		if(s.contains("www"))
			resp.sendRedirect("http://cct.ddtor.com"+ req.getContextPath());
		else
			resp.sendRedirect("Cct.html");	
		}
}