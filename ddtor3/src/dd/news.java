package dd;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;

public class news extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");
		String s = get_datastore_s(10);
		PrintWriter out = resp.getWriter();
		out.write(s);
		out.flush();
		out.close();
	}

	public static String get_datastore_s(int i) throws IOException {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key guestbookKey = KeyFactory.createKey("Guestbook", "guestbookName");

		Query query = new Query("Greeting", guestbookKey).addSort("date",
				Query.SortDirection.DESCENDING);

		// Query query = new Query("Greeting", guestbookKey);
		List<Entity> greetings = datastore.prepare(query).asList(
				FetchOptions.Builder.withLimit(i));
		String s = "";
		Key kk;
		for (Entity greeting : greetings) {
			byte[] bb = ((Text) greeting.getProperty("content")).getValue()
					.getBytes("utf8");
			s = s + par(new String(bb, "UTF-8"))+"<br/>";
			datastore.delete(greeting.getKey());
		}

		return s;
	}

	public static String par(String s) {
		s=cut_first(s,"<table","</table>");
		s=cut_last(s,"<div","</div>");
		s=cut_last(s,"<div","</div>");
		s=cut_last(s,"<!--","-->");
		return s;
	}
	
	public static String cut_first(String s,String s1,String s2) {
		int i1 = s.indexOf(s1);
		if (i1 > 0)
			s1 = s.substring(i1);
		int i2 = s1.indexOf(s2) + s2.length();		
		if (i2 > 0)
			s = s.substring(0, i1) + s1.substring(i2);
		return s;
	}
	public static String cut_last(String s,String s1,String s2) {
		int i1 = s.lastIndexOf(s1);
		if (i1 > 0)
			s1 = s.substring(i1);
		int i2 = s1.indexOf(s2) + s2.length();		
		if (i2 > 0)
			s = s.substring(0, i1) + s1.substring(i2);
		return s;
	}

}