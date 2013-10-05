package dd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
import com.google.appengine.api.files.GSFileOptions.GSFileOptionsBuilder;

public class bre extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String s = rf(req.getQueryString());
		
		
	
		PrintWriter out = resp.getWriter();
		out.write(s);
		out.flush();
		out.close();
	}
	public String rf(String s) {

		try {
			   FileService fileService = FileServiceFactory.getFileService();
			      GSFileOptionsBuilder optionsBuilder = new GSFileOptionsBuilder()
			        .setBucket("mybucket")
			        .setKey("myfile")
			        .setMimeType("text/html")
			        .setAcl("public_read")
			        .addUserMetadata("myfield1", "my field value");
			    AppEngineFile writableFile =
			        fileService.createNewGSFile(optionsBuilder.build());
			    // Open a channel to write to it
			    boolean lock = false;
			    FileWriteChannel writeChannel =
			        fileService.openWriteChannel(writableFile, lock);
			    // Different standard Java ways of writing to the channel
			    // are possible. Here we use a PrintWriter:
			    PrintWriter out = new PrintWriter(Channels.newWriter(writeChannel, "UTF8"));
			    out.println("The woods are lovely dark and deep.");
			    out.println("But I have promises to keep.");

			    // Close without finalizing and save the file path for writing later
			    out.close();
			    String path = writableFile.getFullPath();

			    // Write more to the file in a separate request:
			    writableFile = new AppEngineFile(path);

			    // This time lock because we intend to finalize
			    lock = true;
			    writeChannel = fileService.openWriteChannel(writableFile, lock);

			    // This time we write to the channel directly.
			    writeChannel.write(ByteBuffer.wrap(
			        "And miles to go before I sleep.".getBytes()));

			    // Now finalize
			    writeChannel.closeFinally();
			    // At this point the file is visible in App Engine as:
			    // "/gs/mybucket/myfile"
			    // and to anybody on the Internet through Google Storage as:
			    // (http://storage.googleapis.com/mybucket/myfile)
			    // So reading it through Files API:
			    s=path;

		} catch (Exception ee) {
			s = ee.toString();
		}
		return s;

	}


}
