package cc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class mat extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String s = "OK";
		try {
		
			byte[] ad = brff("1.jpg");
			String subj="cron job " + new Date().toString();
			String text="Hi guys,<br><br> Regards";
			sm(ad,"ymdata@gmail.com","Yuriy Milov","qdone@rogers.com","Recipient", subj, text);

		} catch (Exception e) {
			s = e.toString();
		}
		PrintWriter out = resp.getWriter();
		out.write(s);
		out.flush();
		out.close();
	}

	public void send_mail() throws Exception {

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		String msgBody = "qq";
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("ymdata@gmail.com", "Yuriy Milov"));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
				"qdone@rogers.com", "Test"));
		msg.setSubject("cron job " + new Date().toString());
		msg.setText(msgBody);
		Multipart mp = new MimeMultipart();

		//MimeBodyPart htmlPart = new MimeBodyPart();
		//String htmlBody = rfu("http://www.google.ca");
		//htmlPart.setContent(htmlBody, "text/html");
		//mp.addBodyPart(htmlPart);

		// MimeBodyPart mbp2 = new MimeBodyPart();

		// attach the file to the message
		// FileDataSource fds = new FileDataSource("1.pdf");
		// mbp2.setDataHandler(new DataHandler(fds));
		// mbp2.setFileName(fds.getName());
		// mp.addBodyPart(mbp2);

		MimeBodyPart attachment = new MimeBodyPart();
		byte[] attachmentData = brff("1.jpg"); // ...
		msg.setText("***" + attachmentData.length + "***");

		//DataSource src = new ByteArrayDataSource(attachmentData, "image/1.jpeg");
		
		FileDataSource fds = new FileDataSource("1.jpg");
		attachment.setDataHandler(new DataHandler(fds));
		attachment.setFileName("1.jpg");
		attachment.setDisposition(Part.ATTACHMENT);
		
		mp.addBodyPart(attachment);
		msg.setContent(mp);
		Transport.send(msg);
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

	public static String rbfu(String url) {
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

	public static byte[] brff(String sf) throws IOException {
		File file = new File(sf);
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		if (length > Integer.MAX_VALUE) {
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}
	
	private static void sm(byte[] data, String from_a, String from_n, String to_a, String to_n, String subj, String textBody) throws Exception{
Properties props = new Properties();
Session session = Session.getDefaultInstance(props, null);

Message message = new MimeMessage(session);
message.setFrom(new InternetAddress(from_a, from_n));

message.addRecipient(Message.RecipientType.TO, new InternetAddress(to_a, to_n));


Multipart mp = new MimeMultipart();

MimeBodyPart textPart = new MimeBodyPart();
textPart.setContent(textBody, "text/html");
mp.addBodyPart(textPart);

MimeBodyPart attachment = new MimeBodyPart();
String fileName = "1.jpg";
String filename = URLEncoder.encode(fileName, "UTF-8");
attachment.setFileName(filename);
attachment.setDisposition(Part.ATTACHMENT);

//DataSource src = new ByteArrayDataSource(spreadSheetData, "application/x-ms-excel");
DataSource src = new ByteArrayDataSource(data, "image/jpeg");
DataHandler handler = new DataHandler(src);
attachment.setDataHandler(handler);
mp.addBodyPart(attachment);

message.setContent(mp);
message.setSubject(String.format(subj));

Transport.send(message);

}

}
