package dd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileReadChannel;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;

public class qqw extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String s = req.getParameter("e");		

		 s=wf(s);
		 
			String sh = req.getScheme() + "://" + req.getServerName() + ":"		
					+ req.getServerPort() + req.getContextPath();
			
			
			s="<a href="+sh+"/qqr?"+s+">"+s+"</a>";
			
		PrintWriter out = resp.getWriter();
		out.write(s);
		out.flush();
		out.close();
	}
	

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		//All men are mortal. Socrates is a man.
		
		String s = req.getQueryString();		
	
		//String s = wf(get_ape(
		//		"text=All men are mortal. Socrates is a man.&solo=owlxml")
		//		.replace("<", "[").replace(">", "]"));
		
		//s=get_ape(s);
						//.replace("<", "[").replace(">", "]");
		
		 s=wf(s);
		 
			String sh = req.getScheme() + "://" + req.getServerName() + ":"		
					+ req.getServerPort() + req.getContextPath();
			
			
			s="<a href="+sh+"/qqr?"+s+">"+s+"</a>";
			
		PrintWriter out = resp.getWriter();
		out.write(s);
		out.flush();
		out.close();
	}
	
	public String wf(String s) {

			try {
				// Get a file service
				FileService fileService = FileServiceFactory.getFileService();

				// Create a new Blob file with mime-type "text/plain"
				AppEngineFile file = fileService.createNewBlobFile("text/plain","qq.txt");

				// Open a channel to write to it
				boolean lock = false;
				FileWriteChannel writeChannel = fileService.openWriteChannel(file,
						lock);

				// Different standard Java ways of writing to the channel
				// are possible. Here we use a PrintWriter:
				PrintWriter out = new PrintWriter(Channels.newWriter(writeChannel,
						"UTF8"));

				out.println(s);
				//out.println("The woods are lovely dark and deep.");
				//out.println("But I have promises to keep.");

				// Close without finalizing and save the file path for writing later
				out.close();
				String path = file.getFullPath();

				// Write more to the file in a separate request:
				file = new AppEngineFile(path);

				// This time lock because we intend to finalize
				lock = true;
				writeChannel = fileService.openWriteChannel(file, lock);

				// This time we write to the channel directly
				//writeChannel.write(ByteBuffer
				//		.wrap("And miles to go before I sleep.".getBytes()));

				// Now finalize
				writeChannel.closeFinally();

				// Later, read from the file using the file API
				//lock = false; // Let other people read at the same time
				//FileReadChannel readChannel = fileService.openReadChannel(file,
				//		false);

				// Again, different standard Java ways of reading from the channel.
				//BufferedReader reader = new BufferedReader(Channels.newReader(
				//		readChannel, "UTF8"));
				//s="";
				// String thisLine;
				 
				// while ((thisLine = reader.readLine()) != null)
				//s = s + thisLine+" \r\n ";
				
				// line = "The woods are lovely dark and deep."

				//readChannel.close();

				// Now read from the file using the Blobstore API
				
				BlobKey blobKey = fileService.getBlobKey(file);
				s=blobKey.toString();
				
				s=s.substring(10).replace(">", "");
				
				_info.blobkey = s;
				
				//BlobstoreService blobStoreService = BlobstoreServiceFactory
				//		.getBlobstoreService();
				//String s2 = new String(blobStoreService.fetchData(blobKey, 30,
				//		40));

			} catch (Exception ee) {
				s = ee.toString();
			}
			return s;

	}

	public String get_ape(String s) {

		String charEncoding = "iso-8859-1";
		String location = "http://attempto.ifi.uzh.ch/ws/ape/apews.perl?";
		try {

			HttpURLConnection conn;

			conn = (HttpURLConnection) new URL(location).openConnection();

			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length",
					Integer.toString(s.length()));
			conn.getOutputStream().write(s.getBytes(charEncoding));
			conn.connect();
			conn.getResponseCode();

			// System.out
			// .println("\r\n  START getting resporse \r\n-------------------\r\n ");

			InputStream is = conn.getInputStream();
			s = get_is(is);
			// System.out.println(s);
			// System.out.println("\r\n\r\n-------------------\r\n FINISH");

		} catch (Exception e) {
			s = e.toString();
		}

		
		
		 
		return s;
	}


	static String get_is(InputStream is) throws Exception {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while ((i = is.read()) != -1)
			sb.append((char) i);
		return sb.toString();
	}


}
