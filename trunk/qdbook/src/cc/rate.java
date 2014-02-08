package cc;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.jdo.PersistenceManager;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Text;

public class rate extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String s = rfu("http://www.bankofcanada.ca/en/markets/csv/exchange_eng.csv");
		
String s2=grate(s);

try {
	PersistenceManager pm = PMF.get().getPersistenceManager();
	List<Dollar2> rd = (List<Dollar2>) pm.newQuery(
			"SELECT FROM " + Dollar2.class.getName()).execute();
	int i = 0;
	while (i < rd.size())
		pm.deletePersistent(rd.get(i++));
	pm.makePersistent(new Dollar2(new Text(s2), new Date()));
} catch (Exception e) {
	s2 = e.toString();
}

		s = s.substring(s.indexOf("USD"));
		s = s.substring(3, 111);

		int n = s.indexOf("U");
		s = s.substring(0, n);

		s = s.substring(s.lastIndexOf(",") + 1);
		s = s.replace("\r", "");
		s = s.replace("\n", "");
		if (s.length() > 0)
			try {
				PersistenceManager pm = PMF.get().getPersistenceManager();
				List<Dollar> rd = (List<Dollar>) pm.newQuery(
						"SELECT FROM " + Dollar.class.getName()).execute();
				int i = 0;
				while (i < rd.size())
					pm.deletePersistent(rd.get(i++));
				pm.makePersistent(new Dollar(s, new Date()));
			} catch (Exception e) {
				s = e.toString();
			}
			//send_mail("qdone@rogers.com", "rate	" + new Date().toString(),
			//		s+" \r\n"+s2);

		PrintWriter out = resp.getWriter();
		
		
		
		out.write(s);
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String s = "OK";

		String s1 = req.getParameter("a1");
		String s2 = req.getParameter("a2");
		String s3 = req.getParameter("a3");
		try {

			send_mail(s1, s2, s3);
		} catch (Exception e) {
			s = e.toString();
		}
		PrintWriter out = resp.getWriter();
		out.write(s);
		out.flush();
		out.close();
	}
	
	public String grate(String s){
		String s2=s;
		String s3="";
		s = s.substring(s.indexOf("USD"));
		s = s.substring(3, 111);
		int n = s.indexOf("U");
		s = s.substring(0, n);
		s = s.substring(s.lastIndexOf(",") + 1);
		s = s.replace("\r", "");
		s = s.replace("\n", "");
		s3="USD:"+s+"/";
		s2=s2.substring(s2.indexOf("Argentine"));

		while(s2.indexOf("\n")>-1)
		{
		String s4=s2.substring(s2.indexOf(",")+2,s2.indexOf("\n")-1);//+"---"+s2.substring(s2.lastIndexOf(",")+2,s2.indexOf("\n")-1);
		s4=s4.substring(0,3)+":"+s4.substring(s4.lastIndexOf(",")+1)+"/";
		//System.out.println(s4);
		s2=s2.substring(s2.indexOf("\n")+1);
		s3=s3+s4;
		}
		return s3;
	}

	public void send_mail(String s1, String s2, String s3) {
		String[] tt = s1.split(",");

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		s2 = s2 + " " + new Date().toString();

		// msgBody=msgBody+"\r\n<br><br>"+rfu("http://code.google.com/p/ddtor/source/list");

		Message msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress("ymdata@gmail.com", "EM DATA"));

		// msg.setFrom(new
		// InternetAddress("lowrisk.terryfoxfoundation@gmail.com",
		// "LowRisk Admin"));

		for (int i = 0; i < tt.length; i++)
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					tt[i], tt[i]));

		msg.setSubject(s2);
		msg.setHeader("Content-type:", "text/html;charset=ISO-8859-1");
		msg.setText(s3);
		Transport.send(msg);
		
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	public static String rfu(String url) {
		StringBuffer s = new StringBuffer();
		try {
			URL u = new URL(url);
			InputStream in = u.openConnection().getInputStream();
			for (int ch = in.read(); ch > 0; ch = in.read()) {
				s.append((char) ch);
			}
			in.close();
		} catch (IOException e) {
			return e.toString();
		}
		return s.toString();
	}

}
