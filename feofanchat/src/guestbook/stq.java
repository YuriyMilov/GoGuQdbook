package guestbook;

import static org.semanticweb.owlapi.vocab.OWLFacet.MAX_EXCLUSIVE;
import static org.semanticweb.owlapi.vocab.OWLFacet.MIN_INCLUSIVE;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
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
import com.clarkparsia.pellet.sparqldl.jena.SparqlDLExecutionFactory;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class stq {

	// private static Object String;
	public static String sfx = "";

	public static void init(HttpServletRequest req, HttpServletResponse resp) {
		stat.stop = "";
		stat.sr = "";
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();

		add_sr("", sh);
		stat.page(req, resp, "");
	}

	public static String srowl1(String s, String sh, String sf) {
		try {
			stat.owl_file = "rff?83.owl";

			Owl2Model qw = new Owl2Model(sh + "/" + stat.owl_file);

			s = stat.prep_all(s);
			s = stat.prepare_83(s);

			// ///////////

			String[] sss = s.split("[.]+");

			for (int i = 0; i < sss.length; i++) {

				String[] ss = sss[i].trim().split("[ ]+");

				String[] sss2 = s.split("[.]+");

				// //////////////////////////////////////////////////////
				// проверка 1) свойства на симметричность, 2) замкнутость группы
				// индвидов и т.п.
				// //////////////////////////////////////////////////////

				for (int i4 = 0; i4 < sss2.length; i4++) {
					String[] ss4 = sss2[i4].trim().split("[ ]+");

					if (ss4[0].toLowerCase().equals("если")) {
						s = s.replace(sss2[i4], "").trim();
						if (s.indexOf(".") == 0)
							s = s.substring(1).trim();
						sfx = "/сим";
						s = s.replace(ss4[2], ss4[2] + sfx);
					}

					if (ss4[0].toLowerCase().equals("все")
							&& ss4[0].equals("это") && ss4[0].equals("только")) {
						s = s.replace(sss2[i4], "").trim();
						if (s.indexOf(".") == 0)
							s = s.substring(1).trim();
						sfx = "/сим";
						s = s.replace(ss4[2], ss4[2] + sfx);

						// Set<OWLIndividual> inds = new
						// HashSet<OWLIndividual>();
						// inds.add(qw.getIndividual(ss2[0]));
						// OWLClassAxiom аксиома = qw.factory
						// .getOWLEquivalentClassesAxiom(
						// qw.getOwlClass(ss2[1]),
						// qw.factory.getOWLObjectOneOf(inds));
						// qw.manager.addAxiom(qw.ontology, аксиома);

					}

					// //////////////////////////////////////////////////////
					// //////////////////////////////////////////////////////

				}
				sss2 = s.split("[.]+");

				for (int i2 = 0; i2 < sss2.length; i2++) {
					String[] ss2 = sss2[i2].trim().split("[ ]+");

					// ///////////// 1 //////////////////

					if (ss2.length == 1) {
						if (bim(ss2[0])) {
							qw.manager.applyChange(new AddAxiom(qw.ontology,
									qw.factory.getOWLSameIndividualAxiom(
											qw.getIndividual(ss2[0]),
											qw.getIndividual(ss2[0]))));
						} else if (!bim(ss2[0])) {
							qw.isSubClassOf(qw.getOwlClass(ss2[0]),
									qw.factory.getOWLThing());
						}
					}

					// ///////////// 2 //////////////////

					if (ss2.length == 2) {

						if (bim(ss2[0]) && ss2[0].indexOf("&") > -1
								&& !bim(ss2[1]))

						{
							String sss3 = ss2[0].replace("&", " ");
							String[] ss3 = sss3.split("[ ]+");

							Set<OWLIndividual> inds = new HashSet<OWLIndividual>();
							for (int i3 = 0; i3 < ss3.length; i3++)
								inds.add(qw.getIndividual(ss3[i3]));

							OWLClassAxiom аксиома = qw.factory
									.getOWLEquivalentClassesAxiom(
											qw.getOwlClass(ss2[1]),
											qw.factory.getOWLObjectOneOf(inds));
							qw.manager.addAxiom(qw.ontology, аксиома);

							qw.manager.addAxiom(qw.ontology, qw.factory
									.getOWLDifferentIndividualsAxiom(inds));
						}

						else

						if (bim(ss2[0]) && !bim(ss2[1])) {
							qw.hasClass(qw.getIndividual(ss2[0]),
									qw.getOwlClass(ss2[1]));
						}

						if (bim(ss2[0]) && !bim(ss2[1])) {

							// Set<OWLIndividual> inds = new
							// HashSet<OWLIndividual>();
							// inds.add(qw.getIndividual(ss2[0]));
							// OWLClassAxiom аксиома = qw.factory
							// .getOWLEquivalentClassesAxiom(
							// qw.getOwlClass(ss2[1]),
							// qw.factory.getOWLObjectOneOf(inds));
							// qw.manager.addAxiom(qw.ontology, аксиома);

						}

						else if (!bim(ss2[0]) && !bim(ss2[1])) {
							qw.isSubClassOf(qw.getOwlClass(ss2[0]),
									qw.getOwlClass(ss2[1]));
						} else if (bim(ss2[0]) && bim(ss2[1])) {
							qw.manager.applyChange(new AddAxiom(qw.ontology,
									qw.factory.getOWLSameIndividualAxiom(
											qw.getIndividual(ss2[0]),
											qw.getIndividual(ss2[1]))));
						} else if (!bim(ss2[0]) && bim(ss2[1])) {

							qw.hasClass(qw.getIndividual(ss2[1]),
									qw.getOwlClass(ss2[0]));
						}
					}

					else

					// ///////////// 3 //////////////////

					if (ss2.length == 3) {

						/*
						 * if (bim(ss2[0]) && bim(ss2[2]) &&
						 * ss2[1].equals("не")) {
						 * qw.differentIndividuals(qw.getIndividual(ss2[0]),
						 * qw.getIndividual(ss2[2])); } else
						 * 
						 * 
						 * if (!bim(ss2[0]) && !bim(ss2[2]) &&
						 * ss2[1].equals("не")) { qw.manager.applyChange(new
						 * AddAxiom(qw.ontology,
						 * qw.factory.getOWLDisjointClassesAxiom(
						 * qw.getOwlClass(ss2[0]), qw.getOwlClass(ss2[2]))));
						 * 
						 * } else
						 */
						if (bim(ss2[0]) && n(ss2[2])) {

							OWLDataProperty hasAge = qw.getDataProperty(ss2[1]
									.replace(sfx, ""));
							String sn = ss2[2].trim();
							int n = Integer.parseInt(sn);
							OWLIndividual ind = qw.getIndividual(ss2[0]);

							qw.assertFact(hasAge, ind, n);

						} else

						if (bim(ss2[0]) && bim(ss2[2])) {
							OWLObjectProperty любит = qw.getProperty(ss2[1]
									.replace(sfx, ""));

							if (ss2[1].indexOf("/сим") > -1)
								qw.isSymmetric(любит);

							OWLIndividual малыш = qw.getIndividual(ss2[0]);
							OWLIndividual малышка = qw.getIndividual(ss2[2]);
							qw.assertFact(любит, малыш, малышка);

						} else

						if (!bim(ss2[0]) && !bim(ss2[2])) {

							OWLObjectProperty любит = qw.getProperty(ss2[1]
									.replace(sfx, ""));

							if (ss2[1].indexOf("/сим") > -1)
								qw.isSymmetric(любит);

							qw.assertDomainAndRange(любит,
									qw.getOwlClass(ss2[0]),
									qw.getOwlClass(ss2[2]));
						}
					}

					else

					// ///////////// 4 //////////////////

					if (ss2.length == 4) {

						if (bim(ss2[0]) && n(ss2[2]) && !bim(ss2[3]))

						{
							OWLIndividual Васька = qw.getIndividual(ss2[0]);
							OWLObjectProperty любит = qw.getProperty(ss2[1]
									.replace(sfx, ""));
							if (ss2[1].indexOf("/сим") > -1)
								qw.isSymmetric(любит);
							int N = Integer.parseInt(ss2[2]);
							OWLClassExpression молоко = qw.getOwlClass(ss2[3]);
							qw.любит_в_точности_N_Сократов(молоко, любит, N,
									Васька);
						}

						else if (!bim(ss2[0]) && n(ss2[2]) && bim(ss2[3]))

						{
							OWLClassExpression малыш = qw.getOwlClass(ss2[0]);
							OWLObjectProperty любит = qw.getProperty(ss2[1]
									.replace(sfx, ""));
							if (ss2[1].indexOf("/сим") > -1)
								qw.isSymmetric(любит);
							int N = Integer.parseInt(ss2[2]);
							OWLIndividual Синеглазка = qw.getIndividual(ss2[3]);
							qw.любит_в_точности_N_Сократов(малыш, любит, N,
									Синеглазка);
						}

						else

						if (!bim(ss2[0]) && n(ss2[2]) && !bim(ss2[3])) {
							OWLObjectProperty любит = qw.getProperty(ss2[1]
									.replace(sfx, ""));
							if (ss2[1].indexOf("/сим") > -1)
								qw.isSymmetric(любит);
							qw.малыш_любит_N_малышек(qw.getOwlClass(ss2[0]),
									любит, Integer.parseInt(ss2[2]),
									qw.getOwlClass(ss2[3]));
						}

					}

				}
				stat.sowl = qw.sowl();

				if (s.length() > -1)
					return s;

				// /////////////////////////////////////////
				// /////////////////////////////////////////
				// /////////////////////////////////////////

				if (ss.length == 3) {

					if (ss[1].equals("-")) {
						qw.isSubClassOf(qw.getOwlClass(ss[0]),
								qw.getOwlClass(ss[2]));
					} else if (ss[1].equals("это")) {
						qw.hasClass(qw.getIndividual(ss[0]),
								qw.getOwlClass(ss[2]));
					}

					else if (bim(ss[0]) && n(ss[2])) {

						OWLDataProperty hasAge = qw.getDataProperty(ss[1]);
						String sn = ss[2].trim();
						int n = Integer.parseInt(sn);
						OWLIndividual ind = qw.getIndividual(ss[0]);

						qw.assertFact(hasAge, ind, n);

					}

					else if (bim(ss[0]) && bim(ss[2])) {

						qw.assertFact(ss[1], ss[0], ss[2]);

					}

					else if (!bim(ss[0]) && !bim(ss[2])) {
						// qw.assertDomainAndRange(qw.getProperty(ss[1]),
						// qw.getOwlClass(ss[0]), qw.getOwlClass(ss[2]));
						qw.assertFact(ss[1], ss[0], ss[2]);
					}

				}

				// else if (socrat(ss[0]) && !socrat(ss[2])) {
				// qw.hasClass(qw.getIndividual(ss[0]), qw.getOwlClass(ss[2]));
				// qw.assertRange(qw.getProperty(ss[1]), qw.getOwlClass(ss[2]));
				// }

			}

			// //////////////////////////////////

			OWLIndividual john = qw.getIndividual("John");
			OWLIndividual mary = qw.getIndividual("Mary");
			OWLIndividual susan = qw.getIndividual("Susan");
			OWLIndividual bill = qw.getIndividual("Bill");
			OWLIndividual david = qw.getIndividual("David");
			OWLIndividual kate = qw.getIndividual("Kate");
			OWLDataProperty hasAge = qw.getDataProperty("hasAge");
			OWLObjectProperty hasSon = qw.getProperty("hasSon");
			OWLObjectProperty hasDaughter = qw.getProperty("hasDaughter");
			OWLObjectProperty hasWife = qw.getProperty("hasWife");
			OWLObjectProperty hasHusband = qw.getProperty("hasHusband");
			OWLObjectProperty hasChild = qw.getProperty("hasChild");
			OWLIndividual male = qw.getIndividual("male");
			OWLIndividual female = qw.getIndividual("female");
			OWLObjectProperty hasGender = qw.getProperty("hasGender");
			OWLClass man = qw.getOwlClass("man");
			OWLClass woman = qw.getOwlClass("woman");
			// OWLClass parent = qw.getOwlClass("parent");
			OWLClass person = qw.getOwlClass("person");

			qw.assertDomain(hasWife, person);
			qw.assertRange(hasWife, person);
			qw.assertDomain(hasSon, person);
			qw.assertRange(hasSon, person);
			qw.assertDomain(hasDaughter, person);
			qw.assertRange(hasDaughter, person);
			qw.assertDataDomain(hasAge, person);
			qw.assertRangeAsInteger(hasAge);
			qw.hasClass(david, person);
			qw.hasClass(bill, person);
			qw.hasClass(kate, person);
			qw.hasClass(mary, person);
			qw.hasClass(susan, person);
			qw.hasClass(john, person);
			qw.inverseProperties(hasWife, hasHusband);
			qw.subPropertyOf(hasSon, hasChild);
			qw.subPropertyOf(hasDaughter, hasChild);
			qw.isFunctional(hasAge);
			qw.isFunctional(hasWife);
			qw.isIrreflexive(hasWife);
			qw.isInverseFunctional(hasWife);
			qw.isAsymmetric(hasWife);

			OWLDataProperty hasGender2 = qw.getDataProperty("hasGender2");
			OWLClassExpression hasAgeRestriction = qw.exactCardinality(hasAge,
					1);
			OWLClassExpression hasGenderRestriction = qw.exactCardinality(
					hasGender2, 1);
			OWLObjectOneOf maleOrFemale = qw.factory.getOWLObjectOneOf(male,
					female);
			OWLObjectAllValuesFrom hasGenderOnlyMaleFemale = qw.factory
					.getOWLObjectAllValuesFrom(hasGender, maleOrFemale);

			// Finally, we bundle these restrictions up into an
			// intersection, since we want person
			// to be a subclass of the intersection of them
			OWLObjectIntersectionOf intersection = qw.factory
					.getOWLObjectIntersectionOf(hasAgeRestriction,
							hasGenderRestriction, hasGenderOnlyMaleFemale);
			// And now we set this anonymous intersection class to be a
			// superclass of Person using a subclass axiom
			qw.manager.addAxiom(qw.ontology,
					qw.factory.getOWLSubClassOfAxiom(person, intersection));

			// Restrictions and other anonymous classes can also be used
			// anywhere a named class can be used.
			// Let's set the range of hasSon to be Person and hasGender
			// value male. This requires an anonymous
			// class that is the intersection of Person, and also, hasGender
			// value male. We need to create
			// the hasGender value male restriction - this describes the
			// class of things that have a hasGender
			// relationship to the individual male.
			OWLObjectHasValue hasGenderValueMaleRestriction = qw.factory
					.getOWLObjectHasValue(hasGender, male);
			// Now combine this with Person in an intersection
			OWLClassExpression personAndHasGenderValueMale = qw.factory
					.getOWLObjectIntersectionOf(person,
							hasGenderValueMaleRestriction);
			// Now specify this anonymous class as the range of hasSon using
			// an object property range axioms
			qw.manager.addAxiom(qw.ontology, qw.factory
					.getOWLObjectPropertyRangeAxiom(hasSon,
							personAndHasGenderValueMale));

			// We can do a similar thing for hasDaughter, by specifying that
			// hasDaughter has a range
			// of Person and hasGender value female. This time, we will make
			// things a little more compact by
			// not using so many variables

			OWLClassExpression rangeOfHasDaughter = qw.factory
					.getOWLObjectIntersectionOf(person,
							qw.factory.getOWLObjectHasValue(hasGender, female));
			qw.manager.addAxiom(qw.ontology, qw.factory
					.getOWLObjectPropertyRangeAxiom(hasDaughter,
							rangeOfHasDaughter));

			// ////////////////////////////////////////////////////////////////////////////////////////////
			//
			// Data Ranges and Equivalent Classes axioms
			//
			// ////////////////////////////////////////////////////////////////////////////////////////////

			// In OWL 2, we can specify expressive data ranges. Here, we
			// will specify the classes
			// Teenage, Adult and Child by saying something about
			// individuals ages.

			// First we take the class Teenager, all of whose instance have
			// an age greater or equal to
			// 13 and less than 20. In Manchester Syntax this is written as
			// Person and hasAge some int[>=13, <20]
			// We create a data range by taking the integer datatype and
			// applying facet restrictions to it.
			// Note that we have statically imported the data range facet
			// vocabulary OWLFacet
			OWLFacetRestriction geq13 = qw.factory.getOWLFacetRestriction(
					MIN_INCLUSIVE, 12);
			// We don't have to explicitly create the typed constant, there
			// are convenience methods to do this
			OWLFacetRestriction lt20 = qw.factory.getOWLFacetRestriction(
					MAX_EXCLUSIVE, 25);
			// Restrict the base type, integer (which is just an XML Schema
			// Datatype) with the facet
			// restrictions.
			OWLFacetRestriction lt30 = qw.factory.getOWLFacetRestriction(
					MAX_EXCLUSIVE, 30);

			OWLClass teenager = qw.factory.getOWLClass(IRI
					.create(qw.ontologyIRI + "#Teenager"));

			OWLClass adult = qw.factory.getOWLClass(IRI.create(qw.ontologyIRI
					+ "#Adult"));

			// And finally Child
			OWLClass child = qw.factory.getOWLClass(IRI.create(qw.ontologyIRI
					+ "#Child"));

			// ////////////////////////////////////////////////////////////////////////////////////////////
			//
			// Different individuals
			//
			// ////////////////////////////////////////////////////////////////////////////////////////////

			// In OWL, we can say that individuals are different from each
			// other. To do this we use a
			// different individuals axiom. Since John, Mary, Bill and Susan
			// are all different individuals,
			// we can express this using a different individuals axiom.
			OWLDifferentIndividualsAxiom diffInds = qw.factory
					.getOWLDifferentIndividualsAxiom(john, mary, bill, susan,
							david, kate);
			qw.manager.addAxiom(qw.ontology, diffInds);
			// Male and Female are also different
			qw.manager.addAxiom(qw.ontology,
					qw.factory.getOWLDifferentIndividualsAxiom(male, female));

			// ////////////////////////////////////////////////////////////////////////////////////////////
			//
			// Disjoint classes
			//
			// ////////////////////////////////////////////////////////////////////////////////////////////

			// Two say that two classes do not have any instances in common
			// we use a disjoint classes
			// axiom:
			OWLDisjointClassesAxiom disjointClassesAxiom = qw.factory
					.getOWLDisjointClassesAxiom(man, woman);
			qw.manager.addAxiom(qw.ontology, disjointClassesAxiom);

			OWLDatatype integerDatatype = qw.factory.getIntegerOWLDatatype();
			OWLDataRange dataRng = qw.factory.getOWLDatatypeRestriction(
					integerDatatype, geq13, lt20);
			OWLDataRange dataRng2 = qw.factory.getOWLDatatypeRestriction(
					integerDatatype, geq13, lt30);
			// Now we have the data range of greater than equal to 13 and
			// less than 20 we can use this in a
			// restriction.
			OWLDataSomeValuesFrom teenagerAgeRestriction = qw.factory
					.getOWLDataSomeValuesFrom(hasAge, dataRng);
			OWLDataSomeValuesFrom lessThanThirtyAgeRestriction = qw.factory
					.getOWLDataSomeValuesFrom(hasAge, dataRng2);
			// Now make Teenager equivalent to Person and hasAge some
			// int[>=13, <20]
			// First create the class Person and hasAge some int[>=13, <20]
			OWLClassExpression teenagePerson = qw.factory
					.getOWLObjectIntersectionOf(person, teenagerAgeRestriction);
			// Do the same for Adult that has an age greater than 21
			OWLDataRange geq21 = qw.factory.getOWLDatatypeRestriction(
					integerDatatype,
					qw.factory.getOWLFacetRestriction(MIN_INCLUSIVE, 21));
			OWLEquivalentClassesAxiom teenagerDefinition = qw.factory
					.getOWLEquivalentClassesAxiom(teenager, teenagePerson);
			qw.manager.addAxiom(qw.ontology, teenagerDefinition);
			OWLClassExpression adultAgeRestriction = qw.factory
					.getOWLDataSomeValuesFrom(hasAge, geq21);
			OWLClassExpression adultPerson = qw.factory
					.getOWLObjectIntersectionOf(person, adultAgeRestriction);
			OWLAxiom adultDefinition = qw.factory.getOWLEquivalentClassesAxiom(
					adult, adultPerson);
			qw.manager.addAxiom(qw.ontology, adultDefinition);
			OWLDataRange notGeq21 = qw.factory.getOWLDataComplementOf(geq21);
			OWLClassExpression childAgeRestriction = qw.factory
					.getOWLDataSomeValuesFrom(hasAge, notGeq21);
			OWLClassExpression childPerson = qw.factory
					.getOWLObjectIntersectionOf(person, childAgeRestriction);
			OWLAxiom childDefinition = qw.factory.getOWLEquivalentClassesAxiom(
					child, childPerson);
			qw.manager.addAxiom(qw.ontology, childDefinition);

			// //////////////////////////////////
			s = qw.sowl();

			stat.posti(sh + "/w2f", "83.owl", s);

		} catch (Exception e2) {
			System.err.println(e2.toString());
		}
		return s;
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
			// вопрос из 3-х слов - "Кто выращивает Розы?"
			//
			// ///////////////////////////////////////////

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
		String[] ss = s.split("[.]");
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

	public static String pripare(String s) {

		while (s.indexOf("(") > -1 && s.indexOf(")") > -1
				&& s.indexOf(")") > s.indexOf("("))
			s = s.replace(s.substring(s.indexOf("("), s.indexOf(")") + 1), "");

		s = s.replace(" - ", " ").replace(" это ", " ").replace(" и ", "&");

		return s.trim();
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

	@SuppressWarnings("unused")
	public static String srowl(String s, String sh, String sf) {
		boolean bb = true;
		stat.owl_file = "rff?83.owl";
		Owl2Model qw = new Owl2Model(sh + "/" + stat.owl_file);
		s = pripare(s);
		String[] ss = s.split("[.]+");
		s = "";
		for (String s2 : ss) {
			s2 = s2.trim().replaceAll("[ ]+", " ");
			String[] sss = s2.split("[.]+");

			// //////////////////////////////////////////////////////
			//
			// переделка входного текста под нрорму кря
			//
			// //////////////////////////////////////////////////////

			bb = true;
			String[] ss1 = s2.split("[ ]");
			String sd = "";

			if (s2.toLowerCase().indexOf("тот, кто") != s2.toLowerCase()
					.lastIndexOf("тот, кто")) {
				s2 = s2.replace(",", "").replace("Тот", "").replace("тот", "")
						.replace("кто", "").replaceAll("[ ]+", " ").trim();
				String[] ss6 = s2.split("[ ]");
				qqcpcec(qw, ss6[0] + "_" + ss6[1], ss6[2], ss6[3] + "_"
						+ ss6[4].replace(".", ""));
				bb = false;
			} else if (s2.toLowerCase().indexOf("тот, кто") == 0) {

				s2 = s2.replace(".", "").replace(",", "").replace("Тот", "")
						.replace("тот", "").replace("кто", "")
						.replaceAll("[ ]+", " ").trim();
				String[] ss6 = s2.split("[ ]");
				if (ss6.length == 3)
					qqipiec_ii(qw, ss6[2], ss6[0], ss6[1]);

				else if (ss1.length > 4) {

					sd = ss1[2].trim();
					OWLObjectProperty живут_в = qw.getProperty(sd);

					sd = ss1[3].replace(",", "").trim();
					OWLIndividual Красный_дом = qw.getIndividual(sd);

					sd = ss1[5].trim() + "_" + ss1[6].trim();
					OWLClass выращивают_Р = qw.getOwlClass(sd);

					OWLClassExpression живут_в_Красный_дом = qw.factory
							.getOWLObjectHasValue(живут_в, Красный_дом);

					OWLClassAxiom кто_выращивают_Розы_живут_в_Красный_дом = qw.factory
							.getOWLEquivalentClassesAxiom(выращивают_Р,
									живут_в_Красный_дом);
					qw.manager.addAxiom(qw.ontology,
							кто_выращивают_Розы_живут_в_Красный_дом);					
				}
				bb = false;
			}

			if (s2.toLowerCase().indexOf("если") == 0) {

				String s22 = ss1[2];
				String s77 = ss1[6];
				if (s22.equals(s77)) {

					// ///////////// симметрия

					bb = true;
					s2 = ss1[2] + "/сим";
				}

				else {

					// ///////////// инверсия

					OWLObjectProperty слева = qw.getProperty(s22);
					OWLObjectProperty справа = qw.getProperty(s77);
					OWLInverseObjectPropertiesAxiom axiom = qw.factory
							.getOWLInverseObjectPropertiesAxiom(слева, справа);
					qw.manager.addAxiom(qw.ontology, axiom);

				}
			}

			if (s2.contains("только")) {
				s2 = s2.substring(s2.indexOf("только") + 7);
				s2 = s2.replace("&", ", ");

				String[] ss5 = s2.split("[,]");

				Set<OWLIndividual> inds = new HashSet<OWLIndividual>();

				for (int i = 0; i < ss5.length; i++) {
					if (bim(ss5[i]))
						inds.add(qw.getIndividual(ss5[i].trim()));
				}
				OWLClassAxiom аксиома = qw.factory
						.getOWLEquivalentClassesAxiom(qw.getOwlClass(ss1[0]),
								qw.factory.getOWLObjectOneOf(inds));
				qw.manager.addAxiom(qw.ontology, аксиома);
				bb = false;

			}

			if (bb)
				s = s + s2 + ". ";
		}

		// stq.iec(qw,"Пончик","играет_на_Баяне");
		// stq.ipi(qw,"Незнайка","живет_справа_от","Пончик");

		// /////////////////////////////////
		//
		// 2-й проход (обработка нормализованного КРЯ без ограничителей)
		//
		// /////////////////////////////////

		ss = s.split("[.]+");
		if (ss.length > 1)
			for (String s3 : ss) {

				s3 = s3.trim().replaceAll("[ ]+", " ");
				String[] ss2 = s3.split("[ ]+");

				// ///////////// 2 //////////////////

				if (ss2.length == 2) {

					if (bim(ss2[0]) && ss2[0].indexOf("&") > -1 && !bim(ss2[1]))

					{
						String sss3 = ss2[0].replace("&", " ");
						String[] ss3 = sss3.split("[ ]+");

						Set<OWLIndividual> inds = new HashSet<OWLIndividual>();
						for (int i3 = 0; i3 < ss3.length; i3++)
							inds.add(qw.getIndividual(ss3[i3]));

						OWLClassAxiom аксиома = qw.factory
								.getOWLEquivalentClassesAxiom(
										qw.getOwlClass(ss2[1]),
										qw.factory.getOWLObjectOneOf(inds));
						qw.manager.addAxiom(qw.ontology, аксиома);

						qw.manager.addAxiom(qw.ontology, qw.factory
								.getOWLDifferentIndividualsAxiom(inds));
					}

					else

					if (bim(ss2[0]) && !bim(ss2[1])) {
						qw.hasClass(qw.getIndividual(ss2[0]),
								qw.getOwlClass(ss2[1]));
					}

					if (bim(ss2[0]) && !bim(ss2[1])) {

						qq1ipec(qw, ss2[0], ss2[1]);

						// Set<OWLIndividual> inds = new
						// HashSet<OWLIndividual>();
						// inds.add(qw.getIndividual(ss2[0]));
						// OWLClassAxiom аксиома = qw.factory
						// .getOWLEquivalentClassesAxiom(
						// qw.getOwlClass(ss2[1]),
						// qw.factory.getOWLObjectOneOf(inds));
						// qw.manager.addAxiom(qw.ontology, аксиома);

					}

					else if (!bim(ss2[0]) && !bim(ss2[1])) {
						qw.isSubClassOf(qw.getOwlClass(ss2[0]),
								qw.getOwlClass(ss2[1]));
					} else if (bim(ss2[0]) && bim(ss2[1])) {
						qw.manager.applyChange(new AddAxiom(qw.ontology,
								qw.factory.getOWLSameIndividualAxiom(
										qw.getIndividual(ss2[0]),
										qw.getIndividual(ss2[1]))));
					} else if (!bim(ss2[0]) && bim(ss2[1])) {

						qw.hasClass(qw.getIndividual(ss2[1]),
								qw.getOwlClass(ss2[0]));
					}
				}

				else

				// ///////////// 3 //////////////////

				if (ss2.length == 3) {

					/*
					 * if (bim(ss2[0]) && bim(ss2[2]) && ss2[1].equals("не")) {
					 * qw.differentIndividuals(qw.getIndividual(ss2[0]),
					 * qw.getIndividual(ss2[2])); } else
					 * 
					 * 
					 * if (!bim(ss2[0]) && !bim(ss2[2]) && ss2[1].equals("не"))
					 * { qw.manager.applyChange(new AddAxiom(qw.ontology,
					 * qw.factory.getOWLDisjointClassesAxiom(
					 * qw.getOwlClass(ss2[0]), qw.getOwlClass(ss2[2]))));
					 * 
					 * } else
					 */
					if (bim(ss2[0]) && n(ss2[2])) {

						OWLDataProperty hasAge = qw.getDataProperty(ss2[1]
								.replace(sfx, ""));
						String sn = ss2[2].trim();
						int n = Integer.parseInt(sn);
						OWLIndividual ind = qw.getIndividual(ss2[0]);

						qw.assertFact(hasAge, ind, n);

					} else

					if (bim(ss2[0]) && bim(ss2[2])) {
						OWLObjectProperty любит = qw.getProperty(ss2[1]
								.replace(sfx, ""));

						if (ss2[1].indexOf("/сим") > -1)
							qw.isSymmetric(любит);

						qqipiec_ii(qw, ss2[0], ss2[1].replace(sfx, ""), ss2[2]);
						qq1ipec(qw, ss2[0], ss2[1].replace(sfx, "") + "_"
								+ ss2[2]);

						// OWLIndividual малыш = qw.getIndividual(ss2[0]);
						// OWLIndividual малышка = qw.getIndividual(ss2[2]);
						// qw.assertFact(любит, малыш, малышка);

					} else

					if (!bim(ss2[0]) && !bim(ss2[2])) {

						OWLObjectProperty любит = qw.getProperty(ss2[1]
								.replace(sfx, ""));

						if (ss2[1].indexOf("/сим") > -1)
							qw.isSymmetric(любит);

						qw.assertDomainAndRange(любит, qw.getOwlClass(ss2[0]),
								qw.getOwlClass(ss2[2]));
					}
				}

				else

				// ///////////// 4 //////////////////

				if (ss2.length == 4) {

					if (bim(ss2[0]) && n(ss2[2]) && !bim(ss2[3]))

					{
						OWLIndividual Васька = qw.getIndividual(ss2[0]);
						OWLObjectProperty любит = qw.getProperty(ss2[1]
								.replace(sfx, ""));
						if (ss2[1].indexOf("/сим") > -1)
							qw.isSymmetric(любит);
						int N = Integer.parseInt(ss2[2]);
						OWLClassExpression молоко = qw.getOwlClass(ss2[3]);
						qw.любит_в_точности_N_Сократов(молоко, любит, N, Васька);
					}

					else if (!bim(ss2[0]) && n(ss2[2]) && bim(ss2[3]))

					{
						OWLClassExpression малыш = qw.getOwlClass(ss2[0]);
						OWLObjectProperty любит = qw.getProperty(ss2[1]
								.replace(sfx, ""));
						if (ss2[1].indexOf("/сим") > -1)
							qw.isSymmetric(любит);
						int N = Integer.parseInt(ss2[2]);
						OWLIndividual Синеглазка = qw.getIndividual(ss2[3]);
						qw.любит_в_точности_N_Сократов(малыш, любит, N,
								Синеглазка);
					}

					else

					if (!bim(ss2[0]) && n(ss2[2]) && !bim(ss2[3])) {
						OWLObjectProperty любит = qw.getProperty(ss2[1]
								.replace(sfx, ""));
						if (ss2[1].indexOf("/сим") > -1)
							qw.isSymmetric(любит);
						qw.малыш_любит_N_малышек(qw.getOwlClass(ss2[0]), любит,
								Integer.parseInt(ss2[2]),
								qw.getOwlClass(ss2[3]));
					}

				}
				// ///////////// 1 слово - ПОСЛЕДНИЙ ШТРИХ //////////////////

				if (ss2.length == 1) {

					if (ss2[0].contains("/сим")) {
						ss2[0] = ss2[0].replace("/сим", "");
						OWLObjectProperty любит = qw.getProperty(ss2[0]);
						qw.isSymmetric(любит);
					}
				}

				s = s + "";
			}

		stat.sowl = qw.sowl();

		return "ok";
	}

	public static void qq1ipec(Owl2Model qw, String Незнайка,
			String играет_на_Трубе) {
		OWLEquivalentClassesAxiom аксиома_о_Пончикe1 = qw.factory
				.getOWLEquivalentClassesAxiom(
						qw.getOwlClass(играет_на_Трубе),
						qw.factory.getOWLObjectOneOf(qw.getIndividual(Незнайка)));
		qw.manager.addAxiom(qw.ontology, аксиома_о_Пончикe1);

	}

	public static void qq2ipec(Owl2Model qw, String Незнайка, String Пончик,
			String играет_на_Трубе) {
		OWLEquivalentClassesAxiom аксиома_о_Пончикe1 = qw.factory
				.getOWLEquivalentClassesAxiom(qw.getOwlClass(играет_на_Трубе),
						qw.factory.getOWLObjectOneOf(
								qw.getIndividual(Незнайка),
								qw.getIndividual(Пончик)));
		qw.manager.addAxiom(qw.ontology, аксиома_о_Пончикe1);

	}

	public static void qqcpcec(Owl2Model qw, String тот_кто_выращивает_Розы,
			String живет_справа_от, String того_кто_играет_на_Баянe) {
		OWLClassAxiom аксиома_о_кто_играет_на_Баяне = qw.factory
				.getOWLEquivalentClassesAxiom(
						qw.getOwlClass(тот_кто_выращивает_Розы),
						qw.factory.getOWLObjectSomeValuesFrom(
								qw.getProperty(живет_справа_от),
								qw.getOwlClass(того_кто_играет_на_Баянe)));
		qw.manager.addAxiom(qw.ontology, аксиома_о_кто_играет_на_Баяне);
	}

	public static void qqipiec_cc(Owl2Model qw, String Незнайка,
			String живет_справа_от, String Пончик) {

		OWLEquivalentClassesAxiom аксиома_о_Пончикe1 = qw.factory
				.getOWLEquivalentClassesAxiom(
						qw.getOwlClass(Незнайка),
						qw.factory.getOWLObjectSomeValuesFrom(
								qw.getProperty(живет_справа_от),
								qw.getOwlClass(Пончик)));
		qw.manager.addAxiom(qw.ontology, аксиома_о_Пончикe1);

	}

	public static void qqipiec_ii(Owl2Model qw, String Незнайка,
			String живет_справа_от, String Пончик) {

		OWLObjectPropertyAssertionAxiom аксиома_о_Пончикe2 = qw.factory
				.getOWLObjectPropertyAssertionAxiom(
						qw.getProperty(живет_справа_от),
						qw.getIndividual(Незнайка), qw.getIndividual(Пончик));
		qw.manager.addAxiom(qw.ontology, аксиома_о_Пончикe2);

	}

	public static String totkto(String ш) {
		stat.owl_file = "rff?83.owl";
		Owl2Model qw = new Owl2Model(ш + "/" + stat.owl_file);

		String s = "Тот, кто выращивает Розы, тот живет_справа_от тот, кто играет_на Баяне.";
		String[] ss = null;
		if (s.toLowerCase().indexOf("тот, кто") != s.toLowerCase().lastIndexOf(
				"тот, кто")) {
			s = s.replace(",", "").replace("Тот", "").replace("тот", "")
					.replace("кто", "").replaceAll("[ ]+", " ").trim();
			ss = s.split("[ ]");
			qqcpcec(qw, ss[0] + "_" + ss[1], ss[2],
					ss[3] + "_" + ss[4].replace(".", ""));

		}
		qq1ipec(qw, "Пончик", "играет_на_Баяне");
		qqipiec_ii(qw, "Незнайка", "живет_справа_от", "Пончик");
		qqipiec_cc(qw, "Незнайка", "живет_справа_от", "Пончик");
		stat.sowl = qw.sowl();
		return "<a href=/owl > OWL </a>";
	}

	public static String очередь(String ш) {

		stat.owl_file = "rff?83.owl";
		Owl2Model qw = new Owl2Model(ш + "/" + stat.owl_file);

		OWLIndividual Пер = qw.getIndividual("Перв");
		OWLIndividual А = qw.getIndividual("А");
		OWLIndividual Б = qw.getIndividual("Б");
		OWLIndividual В = qw.getIndividual("В");
		OWLIndividual Г = qw.getIndividual("Г");
		OWLIndividual Д = qw.getIndividual("Д");
		OWLIndividual Пос = qw.getIndividual("Пос");

		OWLObjectPropertyExpression слева = qw.getProperty("слева");
		OWLObjectPropertyExpression справа = qw.getProperty("справа");
		OWLInverseObjectPropertiesAxiom аксиома_инверсного_отношения = qw.factory
				.getOWLInverseObjectPropertiesAxiom(слева, справа);
		qw.manager.addAxiom(qw.ontology, аксиома_инверсного_отношения);

		OWLObjectPropertyAssertionAxiom аксиома_для_А = qw.factory
				.getOWLObjectPropertyAssertionAxiom(справа, А, Б);
		qw.manager.addAxiom(qw.ontology, аксиома_для_А);

		OWLObjectPropertyAssertionAxiom аксиома_для_Пер = qw.factory
				.getOWLObjectPropertyAssertionAxiom(справа, Пер, А);
		qw.manager.addAxiom(qw.ontology, аксиома_для_Пер);

		OWLObjectPropertyAssertionAxiom аксиома_для_Б = qw.factory
				.getOWLObjectPropertyAssertionAxiom(справа, Б, В);
		qw.manager.addAxiom(qw.ontology, аксиома_для_Б);

		OWLObjectPropertyAssertionAxiom аксиома_для_В = qw.factory
				.getOWLObjectPropertyAssertionAxiom(справа, В, Г);
		qw.manager.addAxiom(qw.ontology, аксиома_для_В);

		OWLObjectPropertyAssertionAxiom аксиома_для_Г = qw.factory
				.getOWLObjectPropertyAssertionAxiom(справа, Г, Д);
		qw.manager.addAxiom(qw.ontology, аксиома_для_Г);

		OWLObjectPropertyAssertionAxiom аксиома_для_Д = qw.factory
				.getOWLObjectPropertyAssertionAxiom(справа, Д, Пос);
		qw.manager.addAxiom(qw.ontology, аксиома_для_Д);

		OWLClass последний = qw.getOwlClass("последний");

		OWLClassAssertionAxiom аксиома_о_последнем = qw.factory
				.getOWLClassAssertionAxiom(последний, Пос);
		qw.manager.addAxiom(qw.ontology, аксиома_о_последнем);

		OWLClass предпоследний = qw.getOwlClass("предпоследний");
		OWLClassExpression имеет_последний_справа = qw.factory
				.getOWLObjectSomeValuesFrom(справа, последний);
		OWLClassAxiom аксиома2 = qw.factory.getOWLEquivalentClassesAxiom(
				предпоследний, имеет_последний_справа);
		qw.manager.addAxiom(qw.ontology, аксиома2);

		OWLClass пятый_справа = qw.getOwlClass("пятый_справа");

		OWLClassExpression второй_справа = qw.factory
				.getOWLObjectSomeValuesFrom(справа, имеет_последний_справа);
		OWLClassExpression третий_справа = qw.factory
				.getOWLObjectSomeValuesFrom(справа, второй_справа);
		OWLClassExpression четвертый_справа = qw.factory
				.getOWLObjectSomeValuesFrom(справа, третий_справа);
		OWLClassAxiom аксиома_о_пятом_справа = qw.factory
				.getOWLEquivalentClassesAxiom(пятый_справа, четвертый_справа);
		qw.manager.addAxiom(qw.ontology, аксиома_о_пятом_справа);

		OWLClass первый = qw.getOwlClass("первый");
		OWLClassAssertionAxiom аксиома_о_первом = qw.factory
				.getOWLClassAssertionAxiom(первый, Пер);
		qw.manager.addAxiom(qw.ontology, аксиома_о_первом);

		OWLClass второй = qw.getOwlClass("второй");
		OWLClassExpression о_втором = qw.factory.getOWLObjectSomeValuesFrom(
				слева, первый);
		OWLClassAxiom аксиома_о_втором = qw.factory
				.getOWLEquivalentClassesAxiom(второй, о_втором);
		qw.manager.addAxiom(qw.ontology, аксиома_о_втором);

		OWLClassExpression или = qw.factory.getOWLObjectUnionOf(второй,
				предпоследний);
		OWLClass второй_или_предпоследний = qw
				.getOwlClass("второй_или_предпоследний");
		OWLClassAxiom аксиома1 = qw.factory.getOWLEquivalentClassesAxiom(
				второй_или_предпоследний, или);
		qw.manager.addAxiom(qw.ontology, аксиома1);

		OWLClass очередь = qw.getOwlClass("очередь");
		qw.manager.addAxiom(qw.ontology, qw.factory.getOWLSubClassOfAxiom(
				второй_или_предпоследний, очередь));
		qw.manager.addAxiom(qw.ontology,
				qw.factory.getOWLSubClassOfAxiom(второй, очередь));
		qw.manager.addAxiom(qw.ontology,
				qw.factory.getOWLSubClassOfAxiom(пятый_справа, очередь));
		qw.manager.addAxiom(qw.ontology,
				qw.factory.getOWLSubClassOfAxiom(предпоследний, очередь));

		OWLIndividual Красный_дом = qw.getIndividual("Красный_дом");
		OWLObjectProperty живут_в = qw.getProperty("живут_в");

		OWLClass кто_выращивают_Розы = qw.getOwlClass("кто_выращивают_Розы");
		OWLClassExpression живут_в_Красный_дом = qw.factory
				.getOWLObjectHasValue(живут_в, Красный_дом);

		OWLClassAxiom кто_выращивают_Розы_живут_в_Красный_дом = qw.factory
				.getOWLEquivalentClassesAxiom(кто_выращивают_Розы,
						живут_в_Красный_дом);
		qw.manager.addAxiom(qw.ontology,
				кто_выращивают_Розы_живут_в_Красный_дом);

		OWLClass кто_играет_на_Баяне = qw.getOwlClass("кто_играет_на_Баяне");
		OWLClassExpression о_кто_играет_на_Баяне = qw.factory
				.getOWLObjectSomeValuesFrom(справа, кто_выращивают_Розы);
		OWLClassAxiom аксиома_о_кто_играет_на_Баяне = qw.factory
				.getOWLEquivalentClassesAxiom(кто_играет_на_Баяне,
						о_кто_играет_на_Баяне);
		qw.manager.addAxiom(qw.ontology, аксиома_о_кто_играет_на_Баяне);

		qw.hasClass(qw.getIndividual("Незнайка"),
				qw.getOwlClass("кто_выращивают_Розы"));

		stat.sowl = qw.sowl();

		return "<a href=/owl > OWL </a>";
	}

	public static String форум(String ш) {
		String ss = "Незнайка и Пончик - малыш. Кнопочка и Синеглазка это малышка. Если x дружитъс y, то y  дружит_с x. малыш  дружитъс 1 малышка. малышка  дружитъс 1 малыш. Пончик  дружитъс Синеглазка. СПРАШКЛ(Незнайка дружитъс ?кем)";
		ss = stq.mm_get_otvet(ш, "тест форум", ss, "forum@feofan.com");
		stq.mail_admins("тест форум", ss);

		return "тест форум - полёт нормальный - см. письмо на форуме";
	}

}