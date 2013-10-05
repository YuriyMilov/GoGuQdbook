package dd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class post extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();
		

		ServletOutputStream out = resp.getOutputStream();
		resp.setContentType("text/html; charset=UTF8");		
		resp.setCharacterEncoding("UTF8");	
		
		String s = posti(sh+"/qqw",rfu_utf(sh+"/n1.owl"));
		s=rfu_utf(sh+"/qqr2?qqqqqqqq");
		byte[] b = s.getBytes("UTF8");
		out.write(b);
	}
	

	  public static String posti(String surl, String query) throws IOException {
	
	       String encodedQuery = URLEncoder.encode(query, "UTF-8");
	       String postData = "e=" + encodedQuery;
	       URL url = new URL(surl);
	       HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	       connection.setDoOutput(true);
	       connection.setRequestMethod("POST");
	       connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	       connection.setRequestProperty("Content-Length",  String.valueOf(postData.length()));
	        
	       // Write data
	       OutputStream os = connection.getOutputStream();
	       os.write(postData.getBytes());
	        
	       // Read response
	       StringBuilder responseSB = new StringBuilder();
	       BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	         
	       String line;
	       while ( (line = br.readLine()) != null)
	           responseSB.append(line);
	                
	       // Close streams
	       br.close();
	       os.close();
	        
	       return responseSB.toString();	        
	   }
	  
		public static String rfu_utf(String s) {
			try {
				URL url = new URL(s);

				URLConnection conn = url.openConnection();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "utf8"));
				s = "";
				String thisLine = "";
				while ((thisLine = br.readLine()) != null) { // while loop begins
																// here
					s = s + thisLine + "\r\n";
				}
				br.close();
				return s.toString();

			} catch (Exception e) {
				return e.toString();
			}
		}

	private static final long serialVersionUID = 1L;
}