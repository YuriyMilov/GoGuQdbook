package dd;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileReadChannel;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;

public class stkl {
	public static String sts="";
	
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

	public static String get_post(String surl, String body) {
		String s = "";

		try {

			URL url = new URL(surl);
			URLConnection urlConnection = url.openConnection();
			DataOutputStream outStream;

			// Build request body
			// Create connection

			((HttpURLConnection) urlConnection).setRequestMethod("POST");
			urlConnection.setConnectTimeout(0);
			urlConnection.setReadTimeout(0);
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setUseCaches(false);
			urlConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			urlConnection.setRequestProperty("Content-Length",
					"" + body.length());

			// Create I/O streams
			outStream = new DataOutputStream(urlConnection.getOutputStream());

			// Send request
			outStream.writeBytes(body);
			outStream.flush();
			outStream.close();

			// Get Response

			BufferedReader br = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream(), "utf8"));
			s = "";
			String thisLine = "";
			while ((thisLine = br.readLine()) != null) {
				s = s + thisLine + "\r\n";
			}
			br.close();

		} catch (Exception ex) {
			s = ex.toString();
		}
		return s;
	}

	static void sm(String skomu, String s) throws Exception {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("kuka@feofan.com", "Кука Тверской"));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(skomu,
				" "));
		msg.setSubject("Кука Тверской " + new java.util.Date().toString());
		msg.setText(s);
		Transport.send(msg);
	}

	static void smtxt(String s) throws Exception {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("kuka@feofan.com", "Example.com Admin"));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
				"ymilov@gmail.com", "Mr. User"));
		msg.setSubject("Your Example.com account has been activated");
		msg.setText("msgBody");

		// Create the message part
		BodyPart messageBodyPart = new MimeBodyPart();

		// Fill the message
		messageBodyPart.setText(s);

		// Create a multipar message
		Multipart multipart = new MimeMultipart();

		// Set text message part
		multipart.addBodyPart(messageBodyPart);

		// Part two is attachment
		messageBodyPart = new MimeBodyPart();
		String filename = "file.txt";

		DataSource source = new ByteArrayDataSource(
				"xmlData".getBytes("utf-8"), "text/xml; charset=utf-8");
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(filename);
		messageBodyPart.addHeader("Content-Type", " text/xml; charset=utf-8");
		multipart.addBodyPart(messageBodyPart);

		// Send the complete message parts
		msg.setContent(multipart);

		Transport.send(msg);
	}

	public static void send_file(HttpServletRequest req,
			HttpServletResponse resp, String s) throws IOException {
		ServletOutputStream out = resp.getOutputStream();
		resp.setContentType("text/xml");
		resp.setHeader("Content-Disposition", "attachment; filename=owl.xml");
		byte[] b = s.getBytes("UTF8");
		out.write(b);
	}

	

	public static String posti(String surl, String sname, String scontent) {

		try {
			// Encode the query
			String postData = "f=" + URLEncoder.encode(sname, "UTF-8") + "&s="
					+ URLEncoder.encode(scontent, "UTF-8");

			URL url = new URL(surl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length",
					String.valueOf(postData.length()));

			// Write data
			OutputStream os = connection.getOutputStream();
			os.write(postData.getBytes());

			// Read response
			StringBuilder responseSB = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			String line;
			while ((line = br.readLine()) != null)
				responseSB.append(line);

			// Close streams
			br.close();
			os.close();

			return responseSB.toString();
		} catch (Exception e) {
			return e.toString();
		}
	}

	public static String get_is(InputStream is) throws Exception {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while ((i = is.read()) != -1)
			sb.append((char) i);
		return sb.toString();
	}



	public static boolean n(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}

	public static void sm2a(String subject, String body) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);

		try {
			msg.setFrom(new InternetAddress("ddtor M", "admin@ddtor.com"));

			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					"admins"));
			msg.setSubject(subject);
			msg.setText(body);

			Multipart mp = new MimeMultipart();
			MimeBodyPart textPart = new MimeBodyPart();

			textPart.setContent(body, "text/html");
			mp.addBodyPart(textPart);
			Transport.send(msg);
		} catch (Exception e) {
		}
	}

	public static void blob_clear() throws IOException {
		BlobInfoFactory blf = new BlobInfoFactory();
		Iterator<BlobInfo> info = blf.queryBlobInfos();
		BlobInfo bi = null;

		while (info.hasNext()) {
			bi = info.next();
			BlobstoreFS.delete(bi.getBlobKey());
		}
	}
	
	public static String blob_r(String s) {

		try {
			Query query = new Query("__BlobInfo__");
			query.setFilter(FilterOperator.EQUAL.of("filename", s));

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			PreparedQuery pq = datastore.prepare(query);
			List<Entity> entList = pq.asQueryResultList(FetchOptions.Builder
					.withLimit(10));
			if(entList.size()>0){
				s = entList.get(0).getKey().getName();
				s=BlobstoreFS.readToEnd(new BlobKey(s));
			}
		} catch (Exception ee) {
		s = ee.toString();
		}
	
			return s;
	}
	
public static String blob_w(String sname, String s) {
		
		try {
			//blob_clear();		
			Query query = new Query("__BlobInfo__");
			query.setFilter(FilterOperator.EQUAL.of("filename", sname));

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			PreparedQuery pq = datastore.prepare(query);
			List<Entity> entList = pq.asQueryResultList(FetchOptions.Builder
					.withLimit(10));

			if (!entList.isEmpty())
				BlobstoreFS.delete(new BlobKey(entList.get(0).getKey().getName()));

			FileService fileService = FileServiceFactory.getFileService();
			AppEngineFile file = fileService.createNewBlobFile("text/plain", sname);
			boolean lock = false;
			FileWriteChannel writeChannel = fileService
					.openWriteChannel(file, lock);
			PrintWriter out = new PrintWriter(Channels.newWriter(writeChannel,
					"UTF8"));
			out.println(s);
			out.close();
			file = new AppEngineFile(file.getFullPath());
			lock = true;
			writeChannel = fileService.openWriteChannel(file, lock);
			writeChannel.closeFinally();
			
		} catch (Exception e) {
			return e.toString();
		}
		return "ok";
	}	

}