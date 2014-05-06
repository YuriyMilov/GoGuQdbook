package dd;

import java.io.IOException;
import java.io.PrintWriter; 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
       	//writer.write("data: " + System.currentTimeMillis()+ "\n\n");
       	writer.write("data: йй "+ String.valueOf(System.currentTimeMillis()).substring(8,10) +"<br/>ы***ы<br/>\n\n");
        writer.close();
    }
}