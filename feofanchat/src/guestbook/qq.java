package guestbook;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;

import com.google.gwt.core.client.EntryPoint;

public class qq extends HttpServlet implements EntryPoint {

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		resp.setContentType("text/html; charset=UTF8");
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();
		stat.sh = sh;
		String s = req.getParameter("p2");
		if (s == null) {
			stq.init(req, resp);
			return;
		}

		String s2 = req.getParameter("test");
		if (s2 == null)
			s2 = "";

		stat.stop = "<br> <b><i> - </i></b>" + s +"<br>";

		if (s2.contains("сбоку"))
			s = сбоку(sh, req.getParameter("p2"));
		if (s2.contains("форум"))
			s = stq.форум(sh, req.getParameter("p2"));
		if (s2.contains("new")) {
			stat.sr = s;
			if (s.indexOf("?") > 0)
				s= stq.рыба(s,sh);
			else
			s = stq.sparql1(sh, s);
		} else {
			stat.sr = stat.sr + s;
			if (s.indexOf("?") > 0)
				s= stq.рыба(s,sh);
			else
			s = stq.sparql1(sh, s);
		}

		stat.page(req, resp, s);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();
		String s = req.getQueryString();

		stat.sh = sh;

		if (req.getQueryString() == null)
			stq.init(req, resp);
		else if (s.indexOf("p2=") > -1)
			doPost(req, resp);

	}

	public static String ййочередь2(String ш) {

		stat.owl_file = "rff?83.owl";
		Owl2Model qw = new Owl2Model(ш + "/" + stat.owl_file);
		String sd = "", s2 = "";

		String[] ss1 = s2.split("[ ]");

		sd = ss1[2].trim();
		OWLObjectProperty живёт_в = qw.getProperty(sd);

		sd = ss1[3].replace(",", "").trim();
		OWLIndividual Красный_дом = qw.getIndividual(sd);

		sd = ss1[5].trim() + "_" + ss1[6].trim();
		OWLClass выращивает_Розы = qw.getOwlClass(sd);

		OWLClassExpression живут_в_Красный_дом = qw.factory
				.getOWLObjectHasValue(живёт_в, Красный_дом);

		OWLClassAxiom тот_кто_выращивает_Розы_живёт_в_Красный_дом = qw.factory
				.getOWLEquivalentClassesAxiom(выращивает_Розы,
						живут_в_Красный_дом);
		qw.manager.addAxiom(qw.ontology,
				тот_кто_выращивает_Розы_живёт_в_Красный_дом);

		stat.sowl = qw.sowl();

		return "<a href=/owl > OWL </a>";

	}

	
	public static String сбоку(String ш, String s) {
		stat.owl_file = "rff?83.owl";
		Owl2Model qw = new Owl2Model(ш + "/" + stat.owl_file);

		String[] ss = s.trim().replaceAll("[\r\n ]+", " ").split("[.]+");

		for (String s2 : ss) {
			String sпоследнее = s2.split("[ ]+")[s2.split("[ ]+").length - 1];
			String sпервое = s2.split("[ ]+")[0];

			if (stq.bim(sпервое) && stq.bim(sпоследнее))
				stq.YM123(qw, s2);
			else if (!stq.bim(sпервое) && !stq.bim(sпоследнее))
				stq.ym123(qw, s2);
			else if (!stq.bim(sпервое) && stq.bim(sпоследнее))
				stq.yM123(qw, s2);
			else if (stq.bim(sпервое) && !stq.bim(sпоследнее))
				stq.Ym123(qw, s2);
		}

		stat.sowl = qw.sowl();
		return "<a href=/owl > OWL </a>";
	}

	public void onModuleLoad() {
	}

	private static final long serialVersionUID = 1L;

	
}