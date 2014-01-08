package tkl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;

public class mh3 extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();

		String s1 = "111", stxt = rfu_utf(sh + "/UFOS_Daily.txt");// +
																	// URLEncoder.encode(s4,
																	// "UTF-8")
		String spre = stxt.substring(0, stxt.indexOf("Orders"));
		spre=spre.replace("\r\n", "<br>");
	
		String sbody="qq";
		try {
			sbody = spre
					//+ "<br><br>"
					+ get_html(", "+stxt.substring(stxt.indexOf("Orders")),
							rfu_utf(sh + "/3.htm"));
			send_admin("UFOS Daily Activity - GET method", sbody, stxt);
			
		} catch (Exception e) {
			s1 = e.toString();
		}
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		out.write(sbody);
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();

		String s = "ok";
		String s1 = req.getParameter("a1");
		String s2 = req.getParameter("a2");
		String s3 = req.getParameter("a3");
		try {
			
			if(s1.trim().equals("ymilov@gmail.com"))
				send_admin3(s2, s3);
			else
				send_mail3(s1, s2, s3);
				
			
		} catch (Exception e) {
				s = e.toString();
		}

		PrintWriter out = resp.getWriter();
		out.write(s);
		out.flush();
		out.close();
	}

	public void send_mail3(String sadr, String subject, String sbody)
			throws Exception {
		String[] tt = sadr.split(",");
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		String sdt = "";
		TimeZone tz = TimeZone.getTimeZone("America/Montreal");
		//@SuppressWarnings("unused")
		//SimpleDateFormat parser = new SimpleDateFormat(
		//		"MMM dd, yyyy 'at' HH:mm:ss z");
		// Date d = parser.parse("Oct 25, 2007 at 18:35:07 EDT");		
		Date d = Calendar.getInstance(TimeZone.getTimeZone("EST")).getTime();
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy/MM/dd  HH:mm:ss z'('Z')'");
		formatter.setTimeZone(tz);
		sdt = formatter.format(d);
		//subject = subject + " " + sdt;		
		Message msg = new MimeMessage(session);
		// msg.setFrom(new InternetAddress("ymilov@gmail.com", "UFOS Daily Activity"));
		msg.setFrom(new InternetAddress("myufos99@gmail.com","UFOS Targets Report"));

		for (int i = 0; i < tt.length; i++) {			
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						tt[i], tt[i]));	
		}
		msg.setSubject(subject);	
		Multipart mp = new MimeMultipart();
		MimeBodyPart textPart = new MimeBodyPart();
		 textPart.setContent(sbody, "text/plain");
		//textPart.setContent(sbody, "text/html");
		mp.addBodyPart(textPart);
		msg.setContent(mp);
		Transport.send(msg);
	}

	public void send_mail_old(String s1, String s2, String s3) throws Exception {
		String[] tt = s1.split(",");

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		String sdt = "";
		TimeZone tz = TimeZone.getTimeZone("America/Montreal");

		@SuppressWarnings("unused")
		SimpleDateFormat parser = new SimpleDateFormat(
				"MMM dd, yyyy 'at' HH:mm:ss z");
		// Date d = parser.parse("Oct 25, 2007 at 18:35:07 EDT");
		Date d = Calendar.getInstance(TimeZone.getTimeZone("EST")).getTime();
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy/MM/dd  HH:mm:ss z'('Z')'");
		formatter.setTimeZone(tz);
		sdt = formatter.format(d);

		// s2 = s2 + " "+ new Date().toString();

		s2 = s2 + " " + sdt;

		// msgBody=msgBody+"\r\n<br><br>"+rfu("http://code.google.com/p/ddtor/source/list");

		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("ymilov@gmail.com",
				"UFOS Daily Activity"));
		// msg.setFrom(new
		// InternetAddress("lowrisk.terryfoxfoundation@gmail.com",
		// "LowRisk Admin"));

		for (int i = 0; i < tt.length; i++)
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					tt[i], tt[i]));

		/*
		 * msg.setSubject(s2);
		 * msg.setHeader("Content-type:","text/html;charset=ISO-8859-1");
		 * msg.setText(s3); Transport.send(msg);
		 */

		msg.setSubject(s2);
		msg.setText(s3);

		Multipart mp = new MimeMultipart();

		MimeBodyPart textPart = new MimeBodyPart();
		textPart.setContent(s3, "text/plain");
		mp.addBodyPart(textPart);

		/*
		MimeBodyPart attachment = new MimeBodyPart();
		String fileName = "UFOS_Daily.txt";
		String filename = URLEncoder.encode(fileName, "UTF-8");
		attachment.setFileName(filename);
		attachment.setDisposition(Part.ATTACHMENT);

		// DataSource src = new ByteArrayDataSource(spreadSheetData,
		// "application/x-ms-excel");
		DataSource src = new ByteArrayDataSource(s3.getBytes(), "plain/text");
		DataHandler handler = new DataHandler(src);
		attachment.setDataHandler(handler);
		mp.addBodyPart(attachment);
*/
		msg.setContent(mp);
		// msg.setSubject(String.format(subj));

		// Transport.send(msg);

		Transport.send(msg);
	}

	private void send_admin(String subject, String body, String txt)
			throws Exception {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);
		// msg.setFrom(new InternetAddress("ymilov@gmail.com","UFOS Daily Activity"));
		msg.setFrom(new InternetAddress("myufos99@gmail.com",
				"UFOS Daily Activity"));
		msg.addRecipient(Message.RecipientType.TO,
				new InternetAddress("admins"));
		msg.setSubject(subject);
		// msg.setText(body);
		// msg.setText("<b>UFOS</b> <i>Daily Activity Report</i> attached");
		
		body= "The test on admins: " + body;
		
		
		msg.setText(body);

		Multipart mp = new MimeMultipart();

		MimeBodyPart textPart = new MimeBodyPart();

		// textPart.setContent(body, "text/html");

		// textPart.setContent("<b>UFOS</b> <i>Daily Activity Report</i> attached",
		// "text/html");
		textPart.setContent(body, "text/html");

		mp.addBodyPart(textPart);

		MimeBodyPart attachment = new MimeBodyPart();
		String fileName = "UFOS_Daily.txt";
		String filename = URLEncoder.encode(fileName, "UTF-8");
		attachment.setFileName(filename);
		attachment.setDisposition(Part.ATTACHMENT);

		// DataSource src = new ByteArrayDataSource(spreadSheetData,
		// "application/x-ms-excel");

		// DataSource src = new ByteArrayDataSource(body.getBytes(),
		// "plain/text");
		DataSource src = new ByteArrayDataSource(txt.getBytes(), "text/html");

		DataHandler handler = new DataHandler(src);
		attachment.setDataHandler(handler);
		mp.addBodyPart(attachment);

		msg.setContent(mp);
		// msg.setSubject(String.format(subj));

		// Transport.send(msg);

		Transport.send(msg);

	}

	
	private void send_admin3(String subject, String body)
			throws Exception {
		
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);
		// msg.setFrom(new InternetAddress("ymilov@gmail.com", "UFOS Daily Activity"));
		
		msg.setFrom(new InternetAddress("myufos99@gmail.com","UFOS Target Report (admin)"));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress("admins"));
		msg.setSubject(subject);
		MimeBodyPart textPart = new MimeBodyPart();
		//textPart.setContent(body, "text/html");
		textPart.setContent(body, "text/plain");
		Multipart mp = new MimeMultipart();
		mp.addBodyPart(textPart);
		msg.setContent(mp);
		
		Transport.send(msg);

	}
	
	
	@SuppressWarnings("unused")
	private static void sm(byte[] data, String ff, String mimeType,
			String from_a, String from_n, String to_a, String to_n,
			String subj, String textBody) throws Exception {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from_a, from_n));

		message.addRecipient(Message.RecipientType.TO, new InternetAddress(
				to_a, to_n));

		Multipart mp = new MimeMultipart();

		MimeBodyPart textPart = new MimeBodyPart();
		// textPart.setContent(textBody, "text/plain");
		textPart.setContent("UFOS Daily Activity Report attached", "text/plain");
		mp.addBodyPart(textPart);

		MimeBodyPart attachment = new MimeBodyPart();
		String fileName = ff;
		String filename = URLEncoder.encode(fileName, "UTF-8");
		attachment.setFileName(filename);
		attachment.setDisposition(Part.ATTACHMENT);

		// DataSource src = new ByteArrayDataSource(spreadSheetData,
		// "application/x-ms-excel");
		DataSource src = new ByteArrayDataSource(data, mimeType);
		DataHandler handler = new DataHandler(src);
		attachment.setDataHandler(handler);
		mp.addBodyPart(attachment);

		message.setContent(mp);
		message.setSubject(String.format(subj));

		Transport.send(message);

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

	public static String get_html(String s, String stemp) throws Exception {
		String sa = "qq";

		// s = IO.readWholeFileAsUTF8("C:/Users/Yuri/Desktop/UFOS_Daily.txt");
		s = s.replace("\r", " ");
		s = s.replace("\n", " ");
		s = s.replace("\t", " ");
		//String phrase = "the music made   it   hard      to        concentrate";
		String delims = "[ ]+";
		// String[] tokens = phrase.split(delims);

		String[] ss = s.split(delims);
		int k = ss.length;
		int i = 0; 

		s = stemp;
		while (i < k) {
		//	if(i==121)
		//		System.out.println(i + " 111111111  " + ss[i]);
			
			if (!(i < 8 || (i > 11 && i < 14) || (i > 17 && i < 21)
					|| (i > 24 && i < 29) || (i == 33) || (i == 38)
					|| (i == 43) || (i == 48) || (i == 53) || (i == 58)
					|| (i == 59) || (i == 60) || (i == 61) || (i == 66)
					|| (i == 71) || (i == 76) || (i == 81) || (i == 86)
					|| (i == 91) || (i == 92) || (i == 93) || (i == 94)
					|| (i == 95) || (i == 100) || (i == 105) || (i == 110)
					|| (i == 115) || (i == 120) || (i == 125) || (i == 126) || (i == 127))) {
				sa = "<td><!-- " + String.valueOf(i) + " --></td>";
				if(i==121)
					System.out.println(i + " 222222222  " + ss[i]);
				
				s = s.replace(sa, "<td> " + ss[i] + " </td>");
				 // System.out.println(i + "   " + ss[i]);
			}
			i++;
		}
		// System.out.println(s);
		
		if (s.indexOf("Currency: </td><td><!-- 128 --></td>")>-1)		
			s=s.substring(0,s.indexOf("<td>______________________</td>"))+"<td>______________________</td></tr></table>";
		
		return s;
	}
}
