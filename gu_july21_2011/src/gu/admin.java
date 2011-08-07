package gu;

import gu.client.model.User;
import gu.server.PMF;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class admin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		PrintWriter out = resp.getWriter();
		String s = "";
		if (!req.getParameter("key").equals("admin"))
			s = "<html><body>Incorrect key <p> Please <a href=/admin> try again </a></body></html>";
		else {
			if (req.getParameter("btn").equals("add"))

				if (req.getParameter("name").trim().length() < 1)
					s = "<html><body>Enter a name <p> <a href=/admin> go back </a></body></html>";
				else {
					PersistenceManager pm = PMF.get().getPersistenceManager();

					User u = new User();
					u.setName(req.getParameter("name"));
					u.setPassword(req.getParameter("psw"));
					pm.makePersistent(u);
					s = "<html><body>New user '" + req.getParameter("name")
							+ "' has been added <p> <a href=/admin> go back </a></body></html>";
				}
			if (req.getParameter("btn").equals("del")) {

				try {
					PersistenceManager pm = PMF.get().getPersistenceManager();
					@SuppressWarnings("unchecked")
					List<User> usrs = (List<User>) pm.newQuery(
							"SELECT FROM " + User.class.getName()
									+ " WHERE name==\""
									+ req.getParameter("name") + "\"")
							.execute();
					pm.deletePersistent(usrs.get(0));

					s = "<html><body>The user '" + req.getParameter("name")
							+ "' has been deleted <p> <a href=/admin> go back </a></body></html>";
				} catch (Exception e) {
					s = e.toString();
				}
			}
			if (req.getParameter("btn").equals("users")) {
				s = "<html><body>Users:<br/>_____________<br/>";
				PersistenceManager pm = PMF.get().getPersistenceManager();
				@SuppressWarnings("unchecked")
				List<User> usrs = (List<User>) pm.newQuery(
						"SELECT FROM " + User.class.getName()).execute();
				;
				// + " WHERE name==\""
				// + req.getParameter("name")
				// + "\"")
				// .execute();

				for (int i = 0; i < usrs.size(); i++) {
					s = s + "<br/> " + usrs.get(i).getName();
				}
				s = s
						+ "<br/>_____________<p><a href=/admin> go back </a></body></html>";
			}
		}
		// System.out.println(s);
		out.println(s);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		PrintWriter out = resp.getWriter();
		String s = shta.rff("form.htm");
		// System.out.println(s);
		out.println(s);
	}
}