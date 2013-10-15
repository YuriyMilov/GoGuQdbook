package guestbook;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.mindswap.pellet.jena.PelletReasonerFactory;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;

import com.clarkparsia.pellet.sparqldl.jena.SparqlDLExecutionFactory;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class stq {
	public static String sfx = "";

	public static void init(HttpServletRequest req, HttpServletResponse resp) {
		stat.stop = "";
		stat.sr = "";
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();

		add_sr("", sh);
		stat.page(req, resp, "");
	}

	public static void mail_admins(String subject, String text) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.addHeader("Content-Type", " text/html; charset=utf-8");
			msg.setFrom(new InternetAddress("kuka@feofan.com", MimeUtility
					.encodeText("Феофан", "utf-8", "B")));
			msg.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(
					"admins"));
			msg.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
			msg.setText(text);

			Transport.send(msg);
		} catch (Exception e) {
		}
	}

	public static String get_ans1(String sh, String s) {

		s = s.replace("?", "");
		String[] ss = s.split("[ ]+");
		int i = ss.length;
		String s3 = "";

		boolean bb = ss[0].toLowerCase().equals("кто")
				|| ss[0].toLowerCase().equals("что")
				|| ss[0].toLowerCase().equals("где")
				|| ss[0].toLowerCase().equals("когда")
				|| ss[0].toLowerCase().equals("кого");

		if (bb) {

			// ///////////////////////////////////////////
			//
			// вопрос из 2-х слов - "кто Сократ?"
			//
			// ///////////////////////////////////////////

			if (i == 2) {

				OntModel mm = ModelFactory
						.createOntologyModel(PelletReasonerFactory.THE_SPEC);
				StringReader reader = new StringReader(stat.sowl);
				mm.read(reader, "");
				s = stat.sowl;

				String[] ssss = {
						// "SELECT ?кто  WHERE {?кто a qq:" + ss[1] + "}",
						"SELECT ?кто  WHERE {qq:" + ss[1] + " rdf:type ?кто}",
						"SELECT ?кто  WHERE {?кто rdf:type qq:" + ss[1] + "}" };

				for (String str : ssss) {

					s = stat.get_prefix(sh) + str;

					Query qq = QueryFactory.create(s);
					s = stat.sowl;
					s = "";
					ResultSet r = null;

					try {
						r = SparqlDLExecutionFactory.create(qq, mm)
								.execSelect();
						while (r.hasNext()) {
							s = r.next().toString();
							if (!s3.contains(s))
								s3 = s3 + s;
						}
					} catch (Exception er) {
						s = er.toString();
						// ber = true;
					}
					s3 = s3 + s;
				}

				s = s3;
				if (s.length() == 0)
					s = "Не знаю. Расскажи мне об этом";
			}

			// ///////////////////////////////////////////
			//
			// вопрос из 3-х слов - "кто любит Кнопочку?"
			//
			// ///////////////////////////////////////////
			else

			if (i == 3) {
				OntModel mm = ModelFactory
						.createOntologyModel(PelletReasonerFactory.THE_SPEC);

				StringReader reader = new StringReader(stat.sowl);
				mm.read(reader, "");
				s = stat.sowl;

				// ///////////////////////////////////////////////////////
				String[] ss1 = {
						"SELECT ?кто  WHERE {?кто qq:" + ss[1] + " qq:" + ss[2]
								+ "}",
						"SELECT ?кто  WHERE { qq:" + ss[2] + " qq:" + ss[1]
								+ " ?кто }" };
				String[] ss2 = {
						"select ?кто where {qq:" + ss[1]
								+ " rdfs:domain ?кто }",
						"select ?кто where {qq:" + ss[1] + " rdfs:range ?кто }"

				};

				String[] sss = ss1;

				if (!bim(ss[2]))
					sss = ss2;

				for (String str : sss) {

					s = stat.get_prefix(sh) + str;

					Query qq = QueryFactory.create(s);
					s = stat.sowl;
					s = "";
					ResultSet r = null;

					try {
						r = SparqlDLExecutionFactory.create(qq, mm)
								.execSelect();
						while (r.hasNext()) {
							s = r.next().toString();
							if (!s3.contains(s) && !s.contains(ss[2]))
								s3 = s3 + s;
						}
					} catch (Exception er) {
						s = er.toString();
						// ber = true;
					}
					// s3 = s3 + s;
				}

				// ///////////////////////////////////////////////////////

				s = s3;
				if (s.length() == 0)
					s = "Не знаю :-(";

			} else
				s = "Пока что вопрос должен начинается со слов Кто, Что, Где, Когда, Кого и состоять из 2-х или 3-х слов.";
		}

		// if (ber) {
		// stat.page(req, resp, s);
		// }

		if (s.indexOf("#") > 0) {
			s = s.substring(s.indexOf("#") + 1);

			s = s.replaceAll("[<>]", "").replace("[", "");

			ss = s.split("[#]");
			s = "";
			s3 = ",";
			for (String sd : ss) {
				if (sd.indexOf(")") > 0) {
					s = sd.substring(0, sd.indexOf(")")).trim();
					if (!s3.contains(s))
						s3 = s3 + ", " + s;

				}
				s = s3.replace(",,", "");
			}
		} else
			s = "не знаю :-( ";

		// s = s + ".";
		return s;
	}

	public static String get_ans2(String sh, String s) {

		s = s.replace("?", "");
		String[] ss = s.split("[ ]+");
		int i = ss.length;
		String s3 = "";

		boolean bb = ss[0].toLowerCase().equals("кто")
				|| ss[0].toLowerCase().equals("что")
				|| ss[0].toLowerCase().equals("где")
				|| ss[0].toLowerCase().equals("когда")
				|| ss[0].toLowerCase().equals("кого");

		if (bb) {

			// ///////////////////////////////////////////
			//
			// вопрос из 2-х слов - "кто Сократ?"
			//
			// ///////////////////////////////////////////

			if (i == 2) {

				OntModel mm = ModelFactory
						.createOntologyModel(PelletReasonerFactory.THE_SPEC);
				StringReader reader = new StringReader(stat.sowl);
				mm.read(reader, "");
				s = stat.sowl;

				String[] ssss = {
						// "SELECT ?кто  WHERE {?кто a qq:" + ss[1] + "}",
						"SELECT ?кто  WHERE {qq:" + ss[1] + " rdf:type ?кто}",
						"SELECT ?кто  WHERE {?кто rdf:type qq:" + ss[1] + "}" };

				for (String str : ssss) {

					s = stat.get_prefix(sh) + str;

					Query qq = QueryFactory.create(s);
					s = stat.sowl;
					s = "";
					ResultSet r = null;

					try {
						r = SparqlDLExecutionFactory.create(qq, mm)
								.execSelect();
						while (r.hasNext()) {
							s = r.next().toString();
							if (!s3.contains(s))
								s3 = s3 + s;
						}
					} catch (Exception er) {
						s = er.toString();
						// ber = true;
					}
					s3 = s3 + s;
				}

				s = s3;
				if (s.length() == 0)
					s = "Не знаю. Расскажи мне об этом";
			}

			// ///////////////////////////////////////////
			//
			// вопрос из 3-х слов - "кто любит Кнопочку?"
			//
			// ///////////////////////////////////////////
			else

			if (i == 3) {

				OntModel mm = ModelFactory
						.createOntologyModel(PelletReasonerFactory.THE_SPEC);
				StringReader reader = new StringReader(stat.sowl);
				mm.read(reader, "");
				s = stat.sowl;

				String[] ssss = {
						// "SELECT ?кто  WHERE {?кто a qq:" + ss[1] + "}",
						"SELECT ?кто  WHERE {qq:" + ss[1] + "_" + ss[2]
								+ " rdf:type ?кто}",
						"SELECT ?кто  WHERE {?кто rdf:type qq:" + ss[1] + "_"
								+ ss[2] + "}" };

				for (String str : ssss) {

					s = stat.get_prefix(sh) + str;

					Query qq = QueryFactory.create(s);
					s = stat.sowl;
					s = "";
					ResultSet r = null;
					try {
						r = SparqlDLExecutionFactory.create(qq, mm)
								.execSelect();
						while (r.hasNext()) {
							s = r.next().toString();
							if (!s3.contains(s))
								s3 = s3 + s;
						}
					} catch (Exception er) {
						s = er.toString();
					}
					s3 = s3 + s;
				}
				s = s3;
				if (s.indexOf("#") > 0) {
					s = s.substring(s.indexOf("#") + 1);

					s = s.replaceAll("[<>]", "").replace("[", "");

					ss = s.split("[#]");
					s = "";
					s3 = ",";
					for (String sd : ss) {
						if (sd.indexOf(")") > 0) {
							s = sd.substring(0, sd.indexOf(")")).trim();
							if (!s3.contains(s))
								s3 = s3 + ", " + s;

						}
						s = s3.replace(",,", "");
					}
				} else
					s = "не знаю :-( ";
			}
		}
		return s;
	}

	public static String mm_get_otvet(String sh, String sb, String s, String srp) {
		int i = s.lastIndexOf("-- ");
		if (i > 0)
			s = s.substring(0, i);
		s = smot(sh, s) + "\r\n__________\r\n\r\nЭто ответ для " + srp
				+ ", который написал Феофану:\r\n\r\n" + s;
		return s;
	}

	public static void add_sr(String s, String sh) {
		s = s.replaceAll("[ ]+", " ");
		String[] ss = s.trim().split("[.]");
		try {
			for (String s7 : ss)
				if (!stat.sr.contains(s7.trim()))
					stat.sr = stat.sr.trim() + " " + s7.trim() + ".";

			// stat.get_owl83(stat.sr, sh);
			srowl(stat.sr, sh, "");

			// stat.w2f1("83.owl", stat.sowl);
			// stat.w2f1("sr.txt", stat.sr);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String get_sr() {
		if (stat.sr == null)
			return "";
		else
			return stat.sr;
	}

	static void sm(String skomu, String sbj, String s) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress("kuka@feofan.com", MimeUtility
					.encodeText("Феофан", "utf-8", "B")));

			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					skomu, " "));
			msg.setSubject(sbj);
			msg.setText(s);
			Transport.send(msg);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static boolean bim(String s) {

		if (s.length() > 0)
			if (s.substring(0, 1).toUpperCase().equals(s.substring(0, 1)))
				return true;
			else
				return false;
		else
			return false;
	}

	public static String скобки(String s) {

		while (s.indexOf("(") > -1 && s.indexOf(")") > -1
				&& s.indexOf(")") > s.indexOf("("))
			s = s.replace(s.substring(s.indexOf("("), s.indexOf(")") + 1), "");
		return s;
	}

	public static String smot(String sh, String s) {
		int i = s.indexOf("?");
		String s1 = "";
		if (i > -1)
			s1 = s.substring(0, i);
		i = s1.indexOf(".");
		if (i > 0) {
			s1 = s1.substring(0, s1.lastIndexOf(".") + 1);
			stat.sr = "";
			stq.add_sr(s1, sh);
		}
		s = stq.otvet(sh, s);
		return s;
	}

	public static String otvet(String sh, String s) {
		if (s.toLowerCase().contains("вопрос(")) {
			s = sparql(sh, s);
			return s;
		}

		if (s.contains("?")) {
			int i = s.indexOf("?");
			String s1 = "";
			if (i > -1)
				s1 = s.substring(0, i);
			i = s1.indexOf(".");
			if (i > 0) {
				s1 = s1.substring(0, s1.lastIndexOf(".") + 1);
				stat.sr = stat.sr + "";
				add_sr(s1, sh);
			}

			if (s.contains("."))
				s = s.substring(s.lastIndexOf(".") + 1).trim();

			String[] ss = s.split("[ ]+");
			boolean bb = ss[0].toLowerCase().equals("кто")
					|| ss[0].toLowerCase().equals("что")
					|| ss[0].toLowerCase().equals("где")
					|| ss[0].toLowerCase().equals("когда")
					|| ss[0].toLowerCase().equals("кого");
			if (bb) {
				String san2 = s;
				s = get_ans1(sh, s);
				san2 = get_ans2(sh, san2);
				if (s.contains("не знаю :-(") && san2.contains("не знаю :-("))
					s = "не знаю :-( ";
				else if (s.contains("не знаю :-("))
					s = san2;
			} else {
				s = "Не понял вопрос. См. описание КРЯ";
			}
		}
		//s = s.replaceAll("[_]", " ") + ".";
		s = s + ".";
		return s;
	}

	public static String sparql(String sh, String s) {

		s = Jsoup.parse(s).text().replaceAll("[ ]+", " ").trim();

		stat.sh = sh;
		// String s55 = s;

		add_sr(stat.sr, sh);
		int i = s.toLowerCase().indexOf("вопрос(");

		s = s.substring(i + 7);
		s = s.replace(")", "");

		String[] ss = s.split("[ ]+");
		String svar = "";
		s = "SELECT var  WHERE {";
		for (String str : ss) {

			str = str.trim();

			if (str.equals("это"))
				s = s + " rdf:type";
			else if (str.indexOf("?") == 0) {
				svar = svar + str.trim() + " ";
				s = s + " " + str;
			} else
				s = s + " qq:" + str;

		}
		s = s + ".}";
		s = s.replace("var", svar);
		s = stat.get_prefix(sh) + s;

		OntModel mm = ModelFactory
				.createOntologyModel(PelletReasonerFactory.THE_SPEC);

		mm.read(new StringReader(stat.sowl), "");

		try {
			Query qq = QueryFactory.create(s);
			s = "";
			ResultSet r = SparqlDLExecutionFactory.create(qq, mm).execSelect();
			while (r.hasNext())
				s = s + r.next().toString();

		} catch (Exception ee) {
			s = ee.toString();
		}
		s = s.replace("<", "[").replace(">", "]")
				.replace(sh + "/rff?83.owl#", "");
		// s = s.replace("[Root]", "<br/>").replace("-]", "");

		if (s.trim().length() == 0)
			s = "ответа нет.<br/>мир можно дописать, загрузить или добавить.";

		s = s.replace(")(", ")\r\n(");

		ss = s.split("\r\n");
		s = "";

		for (String sd : ss) {

			String[] sss = sd.split("[(]");
			boolean bb = false;
			for (String sf : sss) {
				if (sss.length > 3) {
					// bb=sss[1].contains("[") && sss[2].contains("[") &&
					// sss[3].contains("[");
					sd = "( " + sss[1] + " ( " + sss[2] + " (" + sss[3];
				}
			}
			if (sd.contains("["))
				s = s + sd + "\r\n";
		}
		s = s.replace("[Root]", "\r\n").replace(" -] ", "");
		// s = s.substring(0, s.length() - 4);
		return "\r\n" + s;
	}

	public static boolean n(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}

	public static String форум(String ш, String ss) {
		ss = stq.mm_get_otvet(ш, "тест форум", ss.trim(), "forum@feofan.com");
		stq.mail_admins("тест форум", ss);
		return ss + "\r\n тест форум - полёт нормальный - см. письмо на форуме";
	}

	public static void Y123M(Owl2Model qw, String sНезнайка,
			ArrayList<String> где, String sПончик) {

		OWLObjectProperty p = null;
		String s = где.get(где.size() - 1);
		if (s.endsWith("/сим")) {
			s = s.replace("/сим", "");
			p = qw.getProperty(s);
			qw.isSymmetric(p);
		} else
			p = qw.getProperty(s);

		OWLClassExpression ктото = qw.factory.getOWLObjectSomeValuesFrom(p,
				qw.factory.getOWLObjectOneOf(qw.getIndividual(sПончик)));

		for (int i = где.size() - 2; i > -1; i--) {
			s = где.get(i);
			if (s.endsWith("/сим")) {
				s = s.replace("/сим", "");
				p = qw.getProperty(s);
				qw.isSymmetric(p);
			} else
				p = qw.getProperty(s);

			ктото = qw.factory.getOWLObjectSomeValuesFrom(p, ктото);
		}
		OWLClass понятие_о_Незнайке_1 = qw.getOwlClass("кто_" + sНезнайка);
		OWLClassAxiom аксиома_кто_Незнайка = qw.factory
				.getOWLEquivalentClassesAxiom(понятие_о_Незнайке_1, ктото);
		qw.manager.addAxiom(qw.ontology, аксиома_кто_Незнайка);

		// экв класс {Незнайка}
		OWLClassExpression понятие_о_Незнайке_2 = qw.factory
				.getOWLObjectOneOf(qw.getIndividual(sНезнайка));
		OWLClassAxiom аксиома_о_Незн = qw.factory.getOWLEquivalentClassesAxiom(
				понятие_о_Незнайке_1, понятие_о_Незнайке_2);
		qw.manager.addAxiom(qw.ontology, аксиома_о_Незн);

	}

	public static void y123m(Owl2Model qw, String syyy, ArrayList<String> где,
			String sчтото) {
		OWLObjectProperty p = null;
		String s = где.get(где.size() - 1);
		if (s.endsWith("/сим")) {
			s = s.replace("/сим", "");
			p = qw.getProperty(s);
			qw.isSymmetric(p);
		} else
			p = qw.getProperty(s);
		OWLClassExpression чтото = qw.factory.getOWLObjectSomeValuesFrom(p,
				qw.getOwlClass(sчтото));

		for (int i = где.size() - 2; i > -1; i--) {
			s = где.get(i);
			if (s.endsWith("/сим")) {
				p = qw.getProperty(s.replace("/сим", ""));
				qw.isSymmetric(p);
			} else
				p = qw.getProperty(s);
			чтото = qw.factory.getOWLObjectSomeValuesFrom(p, чтото);
		}

		OWLClass что_yyy = qw.getOwlClass(syyy);
		OWLClassAxiom аксиома_про_чтото = qw.factory
				.getOWLEquivalentClassesAxiom(что_yyy, чтото);
		qw.manager.addAxiom(qw.ontology, аксиома_про_чтото);

	}

	public static void YM123(Owl2Model qw, String s) {

		s = s.replaceAll("тот, кто", "");
		s = s.trim().replaceAll("[. ]+", " ");

		String[] zz8 = s.split(" ");
		int n = zz8.length - 1;

		if (n == 0)
			qw.getIndividual(s);
		else if (n == 1)
			qw.manager
					.addAxiom(
							qw.ontology,
							qw.factory.getOWLSameIndividualAxiom(
									qw.getIndividual(zz8[0]),
									qw.getIndividual(zz8[1])));
		else {
			ArrayList<String> aa = new ArrayList<String>();
			for (int i = 1; i < n; i++) {
				aa.add(zz8[i]);
			}
			stq.Y123M(qw, zz8[0], aa, zz8[n]);
		}
	}

	public static void yM123(Owl2Model qw, String s) {

		s = s.replaceAll("тот, кто", "");
		s = s.trim().replaceAll("[. ]+", " ");

		String[] zz8 = s.split(" ");
		int n = zz8.length - 1;

		if (n == 0)
			qw.getOwlClass(s);
		else if (n == 1) {
			qw.hasClass(qw.getIndividual(zz8[1]), qw.getOwlClass(zz8[0]));
			qw.manager.addAxiom(qw.ontology, qw.factory
					.getOWLEquivalentClassesAxiom(qw.getOwlClass(zz8[0]),
							qw.factory.getOWLObjectOneOf(qw
									.getIndividual(zz8[1]))));
		} else {
			ArrayList<String> aa = new ArrayList<String>();
			for (int i = 1; i < n; i++) {
				aa.add(zz8[i]);
			}
			stq.y123M(qw, zz8[0], aa, zz8[n]);
		}
	}

	public static void y123M(Owl2Model qw, String sчтото,
			ArrayList<String> где, String sПончик) {

		OWLObjectProperty p = null;
		String s = где.get(где.size() - 1);
		if (s.endsWith("/сим")) {
			s = s.replace("/сим", "");
			p = qw.getProperty(s);
			qw.isSymmetric(p);
		} else
			p = qw.getProperty(s);

		OWLClassExpression чтото = qw.factory.getOWLObjectSomeValuesFrom(p,
				qw.factory.getOWLObjectOneOf(qw.getIndividual(sПончик)));

		for (int i = где.size() - 2; i > -1; i--) {
			s = где.get(i);
			if (s.endsWith("/сим")) {
				s = s.replace("/сим", "");
				p = qw.getProperty(s);
				qw.isSymmetric(p);
			} else
				p = qw.getProperty(s);

			чтото = qw.factory.getOWLObjectSomeValuesFrom(p, чтото);
		}

		OWLClass что_чтото = qw.getOwlClass(sчтото);
		OWLClassAxiom аксиома_про_чтото = qw.factory
				.getOWLEquivalentClassesAxiom(что_чтото, чтото);
		qw.manager.addAxiom(qw.ontology, аксиома_про_чтото);
	}

	public static void Y123m(Owl2Model qw, String sНезнайка,
			ArrayList<String> где, String sктото) {

		OWLObjectProperty p = null;
		String s = где.get(где.size() - 1);
		if (s.endsWith("/сим")) {
			s = s.replace("/сим", "");
			p = qw.getProperty(s);
			qw.isSymmetric(p);
		} else
			p = qw.getProperty(s);

		OWLClassExpression ктото = qw.factory.getOWLObjectSomeValuesFrom(p,
				qw.getOwlClass(sктото));

		for (int i = где.size() - 2; i > -1; i--) {
			s = где.get(i);
			if (s.endsWith("/сим")) {
				s = s.replace("/сим", "");
				p = qw.getProperty(s);
				qw.isSymmetric(p);
			} else
				p = qw.getProperty(s);

			ктото = qw.factory.getOWLObjectSomeValuesFrom(p, ктото);
		}

		OWLClass понятие_о_Незнайке_1 = qw.getOwlClass("кто_" + sНезнайка);
		
		OWLClassAxiom аксиома_кто_Незнайка = qw.factory
				.getOWLEquivalentClassesAxiom(понятие_о_Незнайке_1, ктото);
		qw.manager.addAxiom(qw.ontology, аксиома_кто_Незнайка);

		// экв класс {Незнайка}
		OWLClassExpression понятие_о_Незнайке_2 = qw.factory
				.getOWLObjectOneOf(qw.getIndividual(sНезнайка));
		OWLClassAxiom аксиома_о_Незн = qw.factory.getOWLEquivalentClassesAxiom(
				понятие_о_Незнайке_1, понятие_о_Незнайке_2);
		qw.manager.addAxiom(qw.ontology, аксиома_о_Незн);

	}

	public static void ym123(Owl2Model qw, String s) {

		s = s.replaceAll("тот, кто", "");
		s = s.trim().replaceAll("[. ]+", " ");

		String[] zz8 = s.split(" ");
		int n = zz8.length - 1;
		if (n == 0)
			qw.getOwlClass(s);
		else if (n == 1)
			qw.manager.addAxiom(
					qw.ontology,
					qw.factory.getOWLSubClassOfAxiom(qw.getOwlClass(zz8[0]),
							qw.getOwlClass(zz8[1])));
		else {
			ArrayList<String> aa = new ArrayList<String>();
			for (int i = 1; i < n; i++) {
				aa.add(zz8[i]);
			}
			stq.y123m(qw, zz8[0], aa, zz8[n]);
		}
	}

	public static void Ym123(Owl2Model qw, String s) {

		s = s.replaceAll("тот, кто", "");
		s = s.trim().replaceAll("[. ]+", " ");

		String[] zz8 = s.split(" ");
		int n = zz8.length - 1;

		if (n == 0)
			qw.getOwlClass(s);
		else if (n == 1) {
			qw.hasClass(qw.getIndividual(zz8[0]), qw.getOwlClass(zz8[1]));
			qw.manager.addAxiom(qw.ontology, qw.factory
					.getOWLEquivalentClassesAxiom(qw.getOwlClass(zz8[1]),
							qw.factory.getOWLObjectOneOf(qw
									.getIndividual(zz8[0]))));
		} else {
			ArrayList<String> aa = new ArrayList<String>();
			for (int i = 1; i < n; i++) {
				aa.add(zz8[i]);
			}
			stq.Y123m(qw, zz8[0], aa, zz8[n]);
		}
	}

	public static String srowl(String s, String sh, String sf) {

		stat.owl_file = "rff?83.owl";
		Owl2Model qw = new Owl2Model(sh + "/" + stat.owl_file);
		
		s = скобки(s).replace(" - ", " ").replace(" это ", " ");
		String[] ss = s.replaceAll("[\r\n ]+", " ").split("[.]+");
		for (String s2 : ss) {
			s2=s2.trim();
			String[] ss1 = s2.split("[ ]+");
			if (s2.toLowerCase().startsWith("если") && ss1[2].equals(ss1[6]))
				s = s.replace(ss1[2], ss1[2] + "/сим");
			if (s2.toLowerCase().startsWith("тот, кто") )
			{
				s=s.replace(s2, "qqq-s2-qqq");
				String s11=s2.substring(8,s2.indexOf(", тот")).trim();
				String s22=s11.trim().replace(" ", "_");
				s2 = s22+ " "+ s11+". "+s22 + s2.substring(s2.indexOf(", тот")+5);
				s=s.replace("qqq-s2-qqq",s2);
			}
		} 
		
		ss = s.split("[.]+");
		for (String s2 : ss) {
			String[] ss1 = s2.trim().split("[ ]+");
			String sпервое = ss1[0];
			String sпоследнее = ss1[ss1.length - 1];
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

}