package guestbook;

import static org.semanticweb.owlapi.vocab.OWLFacet.MAX_EXCLUSIVE;
import static org.semanticweb.owlapi.vocab.OWLFacet.MIN_INCLUSIVE;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import para.st;
import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import com.clarkparsia.pellet.sparqldl.jena.SparqlDLExecutionFactory;
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
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class stat {
	public static String sqq7 = "", sh = "http://www.feofan.com";
	public static String snach = "<html><head></head><body bgcolor=#efefef>";
	public static String skon = "<br/><br/>" +
			" &nbsp; <a href=/Feofanchat.html><button> примеры </button></a>" +
			//" &nbsp; <a href=/forum.htm><button> форум </button></a>" +
			" &nbsp; <a href=/owl><button> OWL </button></a></html>";

	public static String owl_file = "rff?83.owl";
	public static String siri = sh + "/"+ owl_file +"#";
	public static String stop = "";
	public static String sowl = "";
	public static String sr = "";

	public static String get_prefix(String sh2) {

		return "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX qq: <" + sh2 + "/rff?83.owl#> \r\n ";
	}

	public static String chto(String sir) {

		String s = "";

		try {
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

			// IRI iri = IRI.create("http://test.feofan.com/rufish2.owl");
			IRI iri = IRI.create(sir);

			OWLOntology qw = manager.loadOntologyFromOntologyDocument(iri);

			String sa = "", sn = "", sc = "", sp = "";
			Object[] bb = null;
			Set<OWLEntity> set = qw.getSignature();
			int iset = set.size();
			if (iset < 1)
				return "Ничего тут еще нет.";

			bb = set.toArray();
			for (int i = 0; i < bb.length; i++) {
				sa = (qw.getDeclarationAxioms((OWLEntity) bb[i])).toString();

				if (sa.indexOf("Named") > -1)
					sn = sn
							+ sa.substring(sa.indexOf("#") + 1).replace(">))]",
									"") + " ";
				if (sa.indexOf("Class") > -1)
					sc = sc
							+ sa.substring(sa.indexOf("#") + 1).replace(">))]",
									"") + " ";
				if (sa.indexOf("Object") > -1)
					sp = sp
							+ sa.substring(sa.indexOf("#") + 1).replace(">))]",
									"") + " ";

				s = "<br><i>Сущности:</i> " + sn.trim().replace(" ", ", ")
						+ "<br><i>Понятия:</i> " + sc.trim().replace(" ", ", ")
						+ "<br><i>Связи:</i> " + sp.trim().replace(" ", ", ");

			}

			//

			// ByteArrayOutputStream bout = new ByteArrayOutputStream();
			// manager.saveOntology(oo, bout);
			// s = new String(bout.toByteArray(), "UTF-8");

		} catch (Exception e) {
			s = e.toString();
		}

		return s;

	}

	public static void page(HttpServletRequest req, HttpServletResponse resp,
			String sotvet) {

		if (sotvet.trim().length() != 0) {

			if (sotvet.indexOf("Server Error") > -1)
				sotvet = "какая-то проблема на сервере";

			sotvet = sotvet.replace("\r\n", "<br>").trim();
			stop = stop + "<br>\r\n<b><i>Феофан: </i></b>\r\n \r\n<!--otvet-->"
					+ sotvet + "<!--otvet-->\r\n";

			if (stop.length() > 50000)
				stop = stop.substring(stop.length() - 50000);

		}

		ServletOutputStream out;
		try {
			out = resp.getOutputStream();

			resp.setContentType("text/html; charset=UTF8");
			String s = snach + stop + skon + " <br/><br/>";
			byte[] b;

			b = s.getBytes("UTF8");
			out.write(b);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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

	public static String get_owl(String s1) {
		String s = "";

		try {

			OWLOntologyManager mm = OWLManager.createOWLOntologyManager();
			OWLDataFactory ff = mm.getOWLDataFactory();
			mm = OWLManager.createOWLOntologyManager();
			String base = siri;// "http://www.feofan.com/test/";
			PrefixManager pm = new DefaultPrefixManager(base);

			OWLOntology oo = mm.createOntology(IRI.create(base));
			OWLAxiom axiom = null;
			OWLClassAssertionAxiom classAssertion = null;
			OWLObjectProperty obp = null;

			String[] sss = prep_all(s1).split("[.]+");

			for (int i = 0; i < sss.length; i++) {
				String[] ss = sss[i].trim().split("[ ]+");
				if (ss.length != 3) {
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					mm.saveOntology(oo, outputStream);
					s = new String(outputStream.toByteArray(), "UTF-8");
					return s;

				}

				if (ss[1].equals("-")) {
					axiom = ff.getOWLSubClassOfAxiom(
							ff.getOWLClass(":" + ss[0], pm),
							ff.getOWLClass(":" + ss[2], pm));
					mm.addAxiom(oo, axiom);

				} else

				if (ss[1].equals("это")) {
					classAssertion = ff.getOWLClassAssertionAxiom(
							ff.getOWLClass(":" + ss[2], pm),
							ff.getOWLNamedIndividual(":" + ss[0], pm));
					mm.addAxiom(oo, classAssertion);
				} else {
					obp = ff.getOWLObjectProperty(IRI.create(base + ss[1]));
					OWLSymmetricObjectPropertyAxiom obsym = ff
							.getOWLSymmetricObjectPropertyAxiom(obp);
					AddAxiom addAxiomChange1 = new AddAxiom(oo, obsym);
					mm.applyChange(addAxiomChange1);

					if (socrat(ss[0]) && socrat(ss[2])) {
						OWLObjectPropertyAssertionAxiom aa = ff
								.getOWLObjectPropertyAssertionAxiom(obp, ff
										.getOWLNamedIndividual(IRI.create(base
												+ ss[0])), ff
										.getOWLNamedIndividual(IRI.create(base
												+ ss[2])));

						AddAxiom addAxiomChange2 = new AddAxiom(oo, aa);
						mm.applyChange(addAxiomChange2);
					}
					if (!socrat(ss[0]) && !socrat(ss[2]))

					{

						OWLObjectProperty hasPart = ff.getOWLObjectProperty(IRI
								.create(base + ss[1]));
						OWLClass nose = ff
								.getOWLClass(IRI.create(base + ss[2]));

						// Now create a restriction to describe the class of
						// individuals that have at least one
						// part that is a kind of nose

						OWLClassExpression hasPartSomeNose = ff
								.getOWLObjectSomeValuesFrom(hasPart, nose);

						// Obtain a reference to the Head class so that we can
						// specify that Heads have noses
						OWLClass head = ff
								.getOWLClass(IRI.create(base + ss[0]));

						OWLSubClassOfAxiom ax2 = ff.getOWLSubClassOfAxiom(head,
								hasPartSomeNose);
						AddAxiom addAx = new AddAxiom(oo, ax2);
						mm.applyChange(addAx);

						// ///////////////////////
						// ///////////////////////
						// ///////////////////////
						// ///////////////////////
						// ///////////////////////
						// ///////////////////////

					}
				}
			}

			// /////////////////////////
			// /////////////////////////
			// /////////////////////////

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			mm.saveOntology(oo, outputStream);
			s = new String(outputStream.toByteArray(), "UTF-8");

		} catch (Exception e) {
			s = e.toString();
		}

		return s;
	}

	public static String get_owl7(String s) {

		Owl2Model qq = new Owl2Model("http://owl.feofan.com/1");

		try {
			// OWLIndividual Сократ = qq.getIndividual("Сократ");
			// OWLClass человек = qq.getOwlClass("человек");
			// OWLClass смертен = qq.getOwlClass("смертен");
			// qq.hasClass(Сократ, человек);
			// qq.isSubClassOf(человек, смертен);

			String[] sss = prep_all(s).split("[.]+");

			for (int i = 0; i < sss.length; i++) {

				String[] ss = sss[i].trim().split("[ ]+");

				if (ss.length != 3) {
					;
				} else if (ss[1].equals("-")) {
					qq.isSubClassOf(qq.getOwlClass(ss[0]),
							qq.getOwlClass(ss[2]));
				} else if (ss[1].equals("это")) {
					qq.hasClass(qq.getIndividual(ss[0]), qq.getOwlClass(ss[2]));
				}

				else if (socrat(ss[0]) && socrat(ss[2])) {
					qq.assertFact(ss[1], ss[0], ss[2]);
				}

				else if (!socrat(ss[0]) && !socrat(ss[2])) {
					qq.assertDomainAndRange(qq.getProperty(ss[1]),
							qq.getOwlClass(ss[0]), qq.getOwlClass(ss[2]));
				}
			}

			s = qq.sowl();
		} catch (Exception e) {
			s = qq.sowl();
		}

		return s;
	}

	public static String get_owl81(String s) {

		Owl2Model qq = new Owl2Model("http://owl.feofan.com/1");

		try {

			String[] sss = prep_all(s).split("[.]+");

			for (int i = 0; i < sss.length; i++) {

				String[] ss = sss[i].trim().split("[ ]+");

				if (ss.length != 3) {
					;
				} else if (ss[1].equals("-")) {
					qq.isSubClassOf(qq.getOwlClass(ss[0]),
							qq.getOwlClass(ss[2]));
				} else if (ss[1].equals("это")) {
					qq.hasClass(qq.getIndividual(ss[0]), qq.getOwlClass(ss[2]));
				}

				else if (socrat(ss[0]) && socrat(ss[2])) {
					// qq.getProperty(ss[1]);
					qq.assertFact(ss[1], ss[0], ss[2]);
				}

				else if (!socrat(ss[0]) && !socrat(ss[2])) {
					qq.assertDomainAndRange(qq.getProperty(ss[1]),
							qq.getOwlClass(ss[0]), qq.getOwlClass(ss[2]));
				} else if (socrat(ss[0]) && !socrat(ss[2])) {

					qq.hasClass(qq.getIndividual(ss[0]), qq.getOwlClass(ss[2]));
					qq.assertRange(qq.getProperty(ss[1]), qq.getOwlClass(ss[2]));

				}
			}

			s = qq.sowl();

		} catch (Exception e) {
			s = qq.sowl();
		}

		return s;
	}

	public static void get_owl82() {

		Owl2Model qw = new Owl2Model("http://owl.feofan.com/rff?82.owl");

		// OWLClass человек=st.что("человек");
		// OWLClass дурак=st.что("дурак");
		// OWLIndividual Вася=st.кто("Вася");
		// st.что_что(человек, дурак);
		// st.кто_что(Вася, дурак);

		OWLIndividual john = qw.getIndividual("John");
		OWLIndividual mary = qw.getIndividual("Mary");
		OWLIndividual susan = qw.getIndividual("Susan");
		OWLIndividual bill = qw.getIndividual("Bill");
		OWLIndividual david = qw.getIndividual("David");
		OWLIndividual kate = qw.getIndividual("Kate");
		OWLObjectProperty hasWife = qw.getProperty("hasWife");
		OWLObjectProperty hasHusband = qw.getProperty("hasHusband");
		OWLObjectProperty hasChild = qw.getProperty("hasChild");
		qw.assertFact(hasWife, john, mary);
		qw.assertFact(hasHusband, mary, john);
		qw.assertFact(hasWife, david, kate);
		qw.assertFact(hasHusband, kate, david);
		OWLObjectProperty hasSon = qw.getProperty("hasSon");
		OWLObjectProperty hasDaughter = qw.getProperty("hasDaughter");
		qw.assertFact(hasSon, john, bill);
		qw.assertFact(hasDaughter, john, susan);
		OWLDataProperty hasAge2 = null;
		OWLDataProperty hasAge = qw.getDataProperty("hasAge");
		qw.assertFact(hasAge, john, 33);
		qw.assertFact(hasAge, david, 32);
		qw.assertFact(hasAge, kate, 21);
		// qw.assertFact(hasAge, susan, 17);
		qw.assertFact(hasSon, mary, bill);
		qw.assertFact(hasDaughter, mary, susan);
		qw.assertFact(hasAge, mary, 31);
		qw.assertFact(hasAge, bill, 13);
		qw.assertFact(hasAge, susan, 8);
		OWLIndividual male = qw.getIndividual("male");
		OWLIndividual female = qw.getIndividual("female");
		OWLObjectProperty hasGender = qw.getProperty("hasGender");
		qw.assertFact(hasGender, john, male);
		qw.assertFact(hasGender, mary, female);
		qw.assertFact(hasGender, bill, male);
		qw.assertFact(hasGender, susan, female);
		qw.assertFact(hasGender, david, male);
		qw.assertFact(hasGender, kate, female);
		OWLClass person = qw.getOwlClass("Person");
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
		OWLDatatype integerDatatype = qw.factory.getIntegerOWLDatatype();
		qw.inverseProperties(hasWife, hasHusband);
		qw.subPropertyOf(hasSon, hasChild);
		qw.subPropertyOf(hasDaughter, hasChild);
		qw.isFunctional(hasAge);
		qw.isFunctional(hasWife);
		qw.isIrreflexive(hasWife);
		qw.isInverseFunctional(hasWife);
		qw.isAsymmetric(hasWife);
		OWLClass man = qw.getOwlClass("Man");
		OWLClass woman = qw.getOwlClass("Woman");
		OWLClass parent = qw.getOwlClass("Parent");
		qw.isSubClassOf(man, person);
		qw.isSubClassOf(woman, person);
		qw.isSubClassOf(parent, person);
		OWLDataProperty hasGender2 = qw.getDataProperty("hasGender2");
		OWLClassExpression hasAgeRestriction = qw.exactCardinality(hasAge, 1);
		OWLClassExpression hasGenderRestriction = qw.exactCardinality(
				hasGender2, 1);
		OWLObjectOneOf maleOrFemale = qw.factory
				.getOWLObjectOneOf(male, female);
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
		// class that is the intersection of Person, and also,
		// hasGender value male. We need to create
		// the hasGender value male restriction - this describes the
		// class of things that have a hasGender
		// relationship to the individual male.
		OWLObjectHasValue hasGenderValueMaleRestriction = qw.factory
				.getOWLObjectHasValue(hasGender, male);
		// Now combine this with Person in an intersection
		OWLClassExpression personAndHasGenderValueMale = qw.factory
				.getOWLObjectIntersectionOf(person,
						hasGenderValueMaleRestriction);
		// Now specify this anonymous class as the range of hasSon
		// using an object property range axioms
		qw.manager.addAxiom(qw.ontology, qw.factory
				.getOWLObjectPropertyRangeAxiom(hasSon,
						personAndHasGenderValueMale));

		// We can do a similar thing for hasDaughter, by specifying
		// that hasDaughter has a range
		// of Person and hasGender value female. This time, we will
		// make things a little more compact by
		// not using so many variables

		OWLClassExpression rangeOfHasDaughter = qw.factory
				.getOWLObjectIntersectionOf(person,
						qw.factory.getOWLObjectHasValue(hasGender, female));
		qw.manager.addAxiom(qw.ontology,
				qw.factory.getOWLObjectPropertyRangeAxiom(hasDaughter,
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

		// First we take the class Teenager, all of whose instance
		// have an age greater or equal to
		// 13 and less than 20. In Manchester Syntax this is written
		// as Person and hasAge some int[>=13, <20]
		// We create a data range by taking the integer datatype and
		// applying facet restrictions to it.
		// Note that we have statically imported the data range
		// facet vocabulary OWLFacet
		OWLFacetRestriction geq13 = qw.factory.getOWLFacetRestriction(
				MIN_INCLUSIVE, 12);
		// We don't have to explicitly create the typed constant,
		// there are convenience methods to do this
		OWLFacetRestriction lt20 = qw.factory.getOWLFacetRestriction(
				MAX_EXCLUSIVE, 25);
		// Restrict the base type, integer (which is just an XML
		// Schema Datatype) with the facet
		// restrictions.
		OWLFacetRestriction lt30 = qw.factory.getOWLFacetRestriction(
				MAX_EXCLUSIVE, 30);
		OWLDataRange dataRng = qw.factory.getOWLDatatypeRestriction(
				integerDatatype, geq13, lt20);
		OWLDataRange dataRng2 = qw.factory.getOWLDatatypeRestriction(
				integerDatatype, geq13, lt30);
		// Now we have the data range of greater than equal to 13
		// and less than 20 we can use this in a
		// restriction.
		OWLDataSomeValuesFrom teenagerAgeRestriction = qw.factory
				.getOWLDataSomeValuesFrom(hasAge, dataRng);
		OWLDataSomeValuesFrom lessThanThirtyAgeRestriction = qw.factory
				.getOWLDataSomeValuesFrom(hasAge, dataRng2);
		// Now make Teenager equivalent to Person and hasAge some
		// int[>=13, <20]
		// First create the class Person and hasAge some int[>=13,
		// <20]
		OWLClassExpression teenagePerson = qw.factory
				.getOWLObjectIntersectionOf(person, teenagerAgeRestriction);

		OWLClass teenager = qw.factory.getOWLClass(IRI.create(qw.ontologyIRI
				+ "#Teenager"));
		OWLEquivalentClassesAxiom teenagerDefinition = qw.factory
				.getOWLEquivalentClassesAxiom(teenager, teenagePerson);
		qw.manager.addAxiom(qw.ontology, teenagerDefinition);

		// Do the same for Adult that has an age greater than 21
		OWLDataRange geq21 = qw.factory.getOWLDatatypeRestriction(
				integerDatatype,
				qw.factory.getOWLFacetRestriction(MIN_INCLUSIVE, 21));
		OWLClass adult = qw.factory.getOWLClass(IRI.create(qw.ontologyIRI
				+ "#Adult"));
		OWLClassExpression adultAgeRestriction = qw.factory
				.getOWLDataSomeValuesFrom(hasAge, geq21);
		OWLClassExpression adultPerson = qw.factory.getOWLObjectIntersectionOf(
				person, adultAgeRestriction);
		OWLAxiom adultDefinition = qw.factory.getOWLEquivalentClassesAxiom(
				adult, adultPerson);
		qw.manager.addAxiom(qw.ontology, adultDefinition);

		// And finally Child
		OWLDataRange notGeq21 = qw.factory.getOWLDataComplementOf(geq21);
		OWLClass child = qw.factory.getOWLClass(IRI.create(qw.ontologyIRI
				+ "#Child"));
		OWLClassExpression childAgeRestriction = qw.factory
				.getOWLDataSomeValuesFrom(hasAge, notGeq21);
		OWLClassExpression childPerson = qw.factory.getOWLObjectIntersectionOf(
				person, childAgeRestriction);
		OWLAxiom childDefinition = qw.factory.getOWLEquivalentClassesAxiom(
				child, childPerson);
		qw.manager.addAxiom(qw.ontology, childDefinition);

		// ////////////////////////////////////////////////////////////////////////////////////////////
		//
		// Different individuals
		//
		// ////////////////////////////////////////////////////////////////////////////////////////////

		// In OWL, we can say that individuals are different from
		// each other. To do this we use a
		// different individuals axiom. Since John, Mary, Bill and
		// Susan are all different individuals,
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

		// Two say that two classes do not have any instances in
		// common we use a disjoint classes
		// axiom:
		OWLDisjointClassesAxiom disjointClassesAxiom = qw.factory
				.getOWLDisjointClassesAxiom(man, woman);
		qw.manager.addAxiom(qw.ontology, disjointClassesAxiom);

		// //////////////////////////////

		String s = qw.sowl();

		s = stat.posti(sh + "/w2f", "82.owl", s);

	}



	public static void send_file(HttpServletRequest req,
			HttpServletResponse resp, String s) throws IOException {
		ServletOutputStream out = resp.getOutputStream();
		resp.setContentType("text/xml");
		resp.setHeader("Content-Disposition", "attachment; filename=owl.xml");
		byte[] b = s.getBytes("UTF8");
		out.write(b);
	}

	public static void clear_blobstore() throws IOException {
		BlobInfoFactory blf = new BlobInfoFactory();
		Iterator<BlobInfo> info = blf.queryBlobInfos();
		BlobInfo bi = null;

		while (info.hasNext()) {
			bi = info.next();
			BlobstoreFS.delete(bi.getBlobKey());
		}
	}

	public static String wf1(String sname, String s) throws IOException {
		FileService fileService = FileServiceFactory.getFileService();
		AppEngineFile file = fileService.createNewBlobFile("text/plain", sname);
		boolean lock = false;
		FileWriteChannel writeChannel = fileService
				.openWriteChannel(file, lock);
		PrintWriter out = new PrintWriter(Channels.newWriter(writeChannel,
				"UTF8"));
		out.println(s);
		out.close();
		String path = file.getFullPath();
		file = new AppEngineFile(path);
		lock = true;
		writeChannel = fileService.openWriteChannel(file, lock);
		writeChannel.closeFinally();
		BlobKey blobKey = fileService.getBlobKey(file);
		s = blobKey.toString();
		return s.substring(10).replace(">", "");
	}

	public static String w2f1(String sname, String s) {
		try {

			Query query = new Query("__BlobInfo__");
			
			if(query!=null)
			{
			query.setFilter(FilterOperator.EQUAL.of("filename", sname));

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			PreparedQuery pq = datastore.prepare(query);
			List<Entity> entList = pq.asQueryResultList(FetchOptions.Builder
					.withLimit(8));

			if (!entList.isEmpty()) {
				BlobKey blobKey = new BlobKey(entList.get(0).getKey().getName());
				BlobstoreFS.save("text/html", blobKey, s, sname);

			} 			
			else 
			{
				FileService fileService = FileServiceFactory.getFileService();
				AppEngineFile file = fileService.createNewBlobFile(
						"text/plain", sname);

				boolean lock = false;
				FileWriteChannel writeChannel = fileService.openWriteChannel(
						file, lock);
				PrintWriter out = new PrintWriter(Channels.newWriter(
						writeChannel, "UTF8"));
				out.println(s);
				out.close();
				file = new AppEngineFile(file.getFullPath());
				lock = true;
				writeChannel = fileService.openWriteChannel(file, lock);
				writeChannel.closeFinally();
			}
			}
		} catch (IOException e) {
		
			return "не кряк";
		}
		return "кряк";
	}

	public static String w2f_old(String sname, String s) {
		try {

			Query query = new Query("__BlobInfo__");
			query.setFilter(FilterOperator.EQUAL.of("filename", sname));

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			PreparedQuery pq = datastore.prepare(query);
			List<Entity> entList = pq.asQueryResultList(FetchOptions.Builder
					.withLimit(100));

			int ii = entList.size();
			System.err.println(ii);

			if (!entList.isEmpty())
				BlobstoreFS.delete(new BlobKey(entList.get(0).getKey()
						.getName()));

			// /////////////

			FileService fileService = FileServiceFactory.getFileService();
			AppEngineFile file = fileService.createNewBlobFile("text/plain",
					sname);

			boolean lock = false;
			FileWriteChannel writeChannel = fileService.openWriteChannel(file,
					lock);
			PrintWriter out = new PrintWriter(Channels.newWriter(writeChannel,
					"UTF8"));
			out.println(s);
			out.close();
			file = new AppEngineFile(file.getFullPath());
			lock = true;
			writeChannel = fileService.openWriteChannel(file, lock);
			writeChannel.closeFinally();

		} catch (IOException e) {

			return "не кряк";
		}
		return "кряк";
	}

	public static String prep_all(String s) {
		s = s.replaceAll("&nbsp;", " ");
		s = s.replaceAll("[\r\n\"&#<>]", "");
		
		s = s.replace("\r\n","");
		s = s.replaceAll("[ ]+", " ");
		return s.trim();
	}

	public static void command(String s, HttpServletRequest req,
			HttpServletResponse resp) throws IOException {

		if (s.indexOf("очистить") == 0) {
			stq.init(req, resp);
			return;
		} else if (s.equals("загрузить")) {
			s = rfu_utf(sh + "/load.txt");
			ServletOutputStream out = resp.getOutputStream();
			resp.setContentType("text/html; charset=UTF-8");
			s = s.replace("\r\n", "<br>");
			byte[] b = s.getBytes("UTF8");
			out.write(b);
			return;
		} else if (s.equals("добавить")) {
			s = rfu_utf(sh + "/add.txt");
			ServletOutputStream out = resp.getOutputStream();
			resp.setContentType("text/html; charset=UTF-8");
			s = s.replace("\r\n", "<br>");
			byte[] b = s.getBytes("UTF8");
			out.write(b);
			return;
		} else if (s.equals("помощь")) {
			s = stat.rfu_utf(sh + "/dom.txt");
			ServletOutputStream out = resp.getOutputStream();
			resp.setContentType("text/html; charset=UTF-8");
			s = s.replace("\r\n", "<br>");
			byte[] b = s.getBytes("UTF8");
			out.write(b);
			return;
		} else if (s.equals("что")) {
			stat.page(req, resp, chto(sh + "/qq_s"));
			return;
		}
		if (s.equals("мир")) {
			s = stat.sr;
			if (s == null) {
				s = "";
				stat.sr = "";
			}
			if (s.trim().length() == 0)
				s = "мир пуст.<br/>мир можно написать (см. примеры), загрузить, добавить, сохранить (скоро появятся такие возможности).";

			stat.page(req, resp, s);
			return;
		} else if (s.equals("фео")) {
			stat.page(req, resp, "");
			return;
		} else if (s.equals("")) {
			stat.page(req, resp, "");
			return;
		} else
			page(req, resp, rfu_utf(sh + "/cmd.txt"));

	}

	public static boolean socrat(String s) {

		if (s.length() > 0)
			if (s.substring(0, 1).toUpperCase().equals(s.substring(0, 1)))
				return true;
			else
				return false;
		else
			return false;
	}

	public static String para8(String s) {

		try {
			OWLClassAssertionAxiom caa = null;
			AddAxiom adax = null;
			OWLOntologyManager mm = OWLManager.createOWLOntologyManager();
			OWLDataFactory ff = mm.getOWLDataFactory();
			String base = "http://owl.feofan.com/1#";

			PrefixManager pre = new DefaultPrefixManager(base);
			OWLOntology oo = mm.createOntology(IRI.create(base));

			// //////////////////////////////

			// /////////////////////////
			// Классы
			// /////////////////////////

			OWLClass clam = ff.getOWLClass(":нечёт", pre);
			OWLClass clad = ff.getOWLClass(":чёт", pre);

			// ////////////////////////////
			// Cимметричная связь
			// ////////////////////////////

			OWLObjectProperty op = ff.getOWLObjectProperty(IRI.create(base
					+ "пара"));
			OWLSymmetricObjectPropertyAxiom obsym = ff
					.getOWLSymmetricObjectPropertyAxiom(op);
			adax = new AddAxiom(oo, obsym);
			mm.applyChange(adax);

			// /////////////////////////
			// Индивиды
			// /////////////////////////

			ArrayList<OWLNamedIndividual> nnm = new ArrayList<OWLNamedIndividual>();
			ArrayList<OWLNamedIndividual> nnd = new ArrayList<OWLNamedIndividual>();

			nnm.add(ff.getOWLNamedIndividual(":1", pre));
			nnm.add(ff.getOWLNamedIndividual(":3", pre));
			nnm.add(ff.getOWLNamedIndividual(":5", pre));
			nnm.add(ff.getOWLNamedIndividual(":7", pre));

			nnd.add(ff.getOWLNamedIndividual(":2", pre));
			nnd.add(ff.getOWLNamedIndividual(":4", pre));
			nnd.add(ff.getOWLNamedIndividual(":6", pre));
			nnd.add(ff.getOWLNamedIndividual(":8", pre));

			// /////////////////////////
			// Индивиды - члены класса
			// /////////////////////////

			for (int i = 0; i < nnm.size(); i++)
				mm.addAxiom(oo, ff.getOWLClassAssertionAxiom(clam, nnm.get(i)));

			for (int i = 0; i < nnm.size(); i++)
				mm.addAxiom(oo, ff.getOWLClassAssertionAxiom(clad, nnd.get(i)));

			// /////////////////////////
			// 1 пара 2
			// /////////////////////////

			OWLObjectPropertyAssertionAxiom opaa = ff
					.getOWLObjectPropertyAssertionAxiom(op,
							ff.getOWLNamedIndividual(":1", pre),
							ff.getOWLNamedIndividual(":2", pre));

			adax = new AddAxiom(oo, opaa);
			mm.applyChange(adax);

			// /////////////////////////
			// 5 пара 6
			// /////////////////////////

			opaa = ff.getOWLObjectPropertyAssertionAxiom(op,
					ff.getOWLNamedIndividual(":5", pre),
					ff.getOWLNamedIndividual(":6", pre));

			adax = new AddAxiom(oo, opaa);
			mm.applyChange(adax);

			// /////////////////////////
			// 3 пара 8
			// /////////////////////////

			opaa = ff.getOWLObjectPropertyAssertionAxiom(op,
					ff.getOWLNamedIndividual(":3", pre),
					ff.getOWLNamedIndividual(":4", pre));

			adax = new AddAxiom(oo, opaa);
			mm.applyChange(adax);

			// ///////////////////////////////////////
			// {...} и пара только одному индивиду
			// ///////////////////////////////////////

			OWLObjectExactCardinality exactly1m = ff
					.getOWLObjectExactCardinality(1, op, clam);
			OWLObjectExactCardinality exactly1d = ff
					.getOWLObjectExactCardinality(1, op, clad);

			OWLObjectOneOf nm = ff.getOWLObjectOneOf((OWLNamedIndividual[]) nnm
					.toArray(new OWLNamedIndividual[nnm.size()]));
			OWLObjectOneOf nd = ff.getOWLObjectOneOf((OWLNamedIndividual[]) nnd
					.toArray(new OWLNamedIndividual[nnd.size()]));

			OWLObjectIntersectionOf m_i_l_1_d = ff.getOWLObjectIntersectionOf(
					nm, exactly1d);
			OWLClassAxiom clax = ff.getOWLEquivalentClassesAxiom(clam,
					m_i_l_1_d);
			mm.addAxiom(oo, clax);

			OWLObjectIntersectionOf d_i_l_1_m = ff.getOWLObjectIntersectionOf(
					nd, exactly1m);
			OWLClassAxiom clax2 = ff.getOWLEquivalentClassesAxiom(clad,
					d_i_l_1_m);
			mm.addAxiom(oo, clax2);

			// /////////////////////////
			// Индивиды - разные
			// /////////////////////////

			ArrayList<OWLNamedIndividual> nnmd = new ArrayList<OWLNamedIndividual>();
			nnmd.addAll(nnm);
			nnmd.addAll(nnd);

			OWLDifferentIndividualsAxiom diffInds = ff
					.getOWLDifferentIndividualsAxiom((OWLNamedIndividual[]) nnmd
							.toArray(new OWLNamedIndividual[nnmd.size()]));
			mm.addAxiom(oo, diffInds);

			// /////////////////////////
			// /////////////////////////
			// /////////////////////////

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			mm.saveOntology(oo, outputStream);
			s = new String(outputStream.toByteArray(), "UTF-8");

			return s;
		} catch (Exception e) {
			return e.toString();
		}
	}

	public static String para(String s) {

		try {
			OWLClassAssertionAxiom caa = null;
			AddAxiom adax = null;
			OWLOntologyManager mm = OWLManager.createOWLOntologyManager();
			OWLDataFactory ff = mm.getOWLDataFactory();
			String base = "http://owl.feofan.com/1#";
			PrefixManager pre = new DefaultPrefixManager(base);
			OWLOntology oo;

			oo = mm.createOntology(IRI.create(base));

			// /////////////////////////
			// Классы
			// /////////////////////////

			OWLClass clam = ff.getOWLClass(":нечёт", pre);
			OWLClass clad = ff.getOWLClass(":чёт", pre);

			// ////////////////////////////
			// Cимметричная связь
			// ////////////////////////////

			OWLObjectProperty op = ff.getOWLObjectProperty(IRI.create(base
					+ "пара"));
			OWLSymmetricObjectPropertyAxiom obsym = ff
					.getOWLSymmetricObjectPropertyAxiom(op);
			adax = new AddAxiom(oo, obsym);
			mm.applyChange(adax);

			// /////////////////////////
			// Индивиды
			// /////////////////////////

			ArrayList<OWLNamedIndividual> nnm = new ArrayList<OWLNamedIndividual>();
			ArrayList<OWLNamedIndividual> nnd = new ArrayList<OWLNamedIndividual>();

			nnm.add(ff.getOWLNamedIndividual(":1", pre));
			nnm.add(ff.getOWLNamedIndividual(":3", pre));
			nnm.add(ff.getOWLNamedIndividual(":5", pre));
			nnm.add(ff.getOWLNamedIndividual(":7", pre));

			nnd.add(ff.getOWLNamedIndividual(":2", pre));
			nnd.add(ff.getOWLNamedIndividual(":4", pre));
			nnd.add(ff.getOWLNamedIndividual(":6", pre));
			nnd.add(ff.getOWLNamedIndividual(":8", pre));

			// /////////////////////////
			// Индивиды - члены класса
			// /////////////////////////

			for (int i = 0; i < nnm.size(); i++)
				mm.addAxiom(oo, ff.getOWLClassAssertionAxiom(clam, nnm.get(i)));

			for (int i = 0; i < nnm.size(); i++)
				mm.addAxiom(oo, ff.getOWLClassAssertionAxiom(clad, nnd.get(i)));

			// /////////////////////////
			// 1 пара 2
			// /////////////////////////

			OWLObjectPropertyAssertionAxiom opaa = ff
					.getOWLObjectPropertyAssertionAxiom(op,
							ff.getOWLNamedIndividual(":1", pre),
							ff.getOWLNamedIndividual(":2", pre));

			adax = new AddAxiom(oo, opaa);
			mm.applyChange(adax);

			// /////////////////////////
			// 5 пара 6
			// /////////////////////////

			opaa = ff.getOWLObjectPropertyAssertionAxiom(op,
					ff.getOWLNamedIndividual(":5", pre),
					ff.getOWLNamedIndividual(":6", pre));

			adax = new AddAxiom(oo, opaa);
			mm.applyChange(adax);

			// /////////////////////////
			// 3 пара 8
			// /////////////////////////

			opaa = ff.getOWLObjectPropertyAssertionAxiom(op,
					ff.getOWLNamedIndividual(":3", pre),
					ff.getOWLNamedIndividual(":4", pre));

			adax = new AddAxiom(oo, opaa);
			mm.applyChange(adax);

			// ///////////////////////////////////////
			// {...} и пара только одному индивиду
			// ///////////////////////////////////////

			OWLObjectExactCardinality exactly1m = ff
					.getOWLObjectExactCardinality(1, op, clam);
			OWLObjectExactCardinality exactly1d = ff
					.getOWLObjectExactCardinality(1, op, clad);

			OWLObjectOneOf nm = ff.getOWLObjectOneOf((OWLNamedIndividual[]) nnm
					.toArray(new OWLNamedIndividual[nnm.size()]));
			OWLObjectOneOf nd = ff.getOWLObjectOneOf((OWLNamedIndividual[]) nnd
					.toArray(new OWLNamedIndividual[nnd.size()]));

			OWLObjectIntersectionOf m_i_l_1_d = ff.getOWLObjectIntersectionOf(
					nm, exactly1d);
			OWLClassAxiom clax = ff.getOWLEquivalentClassesAxiom(clam,
					m_i_l_1_d);
			mm.addAxiom(oo, clax);

			OWLObjectIntersectionOf d_i_l_1_m = ff.getOWLObjectIntersectionOf(
					nd, exactly1m);
			OWLClassAxiom clax2 = ff.getOWLEquivalentClassesAxiom(clad,
					d_i_l_1_m);
			mm.addAxiom(oo, clax2);

			// /////////////////////////
			// Индивиды - разные
			// /////////////////////////

			ArrayList<OWLNamedIndividual> nnmd = new ArrayList<OWLNamedIndividual>();
			nnmd.addAll(nnm);
			nnmd.addAll(nnd);

			OWLDifferentIndividualsAxiom diffInds = ff
					.getOWLDifferentIndividualsAxiom((OWLNamedIndividual[]) nnmd
							.toArray(new OWLNamedIndividual[nnmd.size()]));
			mm.addAxiom(oo, diffInds);

			// /////////////////////////
			// /////////////////////////
			// /////////////////////////

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			mm.saveOntology(oo, outputStream);
			s = new String(outputStream.toByteArray(), "UTF-8");

			return s;
		} catch (Exception e) {
			return e.toString();
		}
	}

	public static String scrt(String s) {
		try {
			OWLOntologyManager начальник = OWLManager
					.createOWLOntologyManager();
			OWLDataFactory работник = начальник.getOWLDataFactory();
			String где = "http://owl.feofan.com/1#";
			PrefixManager приставка = new DefaultPrefixManager(где);
			OWLOntology онтология = начальник.createOntology(IRI.create(где));

			OWLClass человек = работник.getOWLClass(":человек", приставка);
			OWLClass смертен = работник.getOWLClass(":смертен", приставка);
			OWLNamedIndividual Сократ = работник.getOWLNamedIndividual(
					":Сократ", приставка);

			OWLClassAssertionAxiom Сократ_это_человек = работник
					.getOWLClassAssertionAxiom(человек, Сократ);
			начальник.addAxiom(онтология, Сократ_это_человек);

			OWLSubClassOfAxiom человек_смертен = работник
					.getOWLSubClassOfAxiom(человек, смертен);
			начальник.addAxiom(онтология, человек_смертен);

			// /////////////////////////
			// /////////////////////////
			// /////////////////////////

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			начальник.saveOntology(онтология, outputStream);
			s = new String(outputStream.toByteArray(), "UTF-8");
			return s;
		} catch (Exception e) {
			return e.toString();
		}
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

	public static String rff(String s) {

		try {

			Query query = new Query("__BlobInfo__");

			// query.addFilter("filename", FilterOperator.EQUAL, "test4");
			query.setFilter(FilterOperator.EQUAL.of("filename", s));

			// /////

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			PreparedQuery pq = datastore.prepare(query);
			List<Entity> entList = pq.asQueryResultList(FetchOptions.Builder
					.withLimit(10));

			if (entList.isEmpty())
				return "нет такого файла";

			s = entList.get(0).getKey().getName();

			// ///

			BlobKey blobKey = new BlobKey(s);
			FileService fileService = FileServiceFactory.getFileService();
			AppEngineFile file = fileService.getBlobFile(blobKey);
			FileReadChannel readChannel = fileService.openReadChannel(file,
					false);
			BufferedReader reader = new BufferedReader(Channels.newReader(
					readChannel, "UTF8"));

			String thisLine = "";
			s = "";
			while ((thisLine = reader.readLine()) != null)
				s = s + thisLine + " \r\n ";
			readChannel.close();

		} catch (Exception ee) {
			s = ee.toString();
		}

		return s;
	}

	public static boolean n(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}

	public static void text8_old(String s, HttpServletRequest req,
			HttpServletResponse resp) throws IOException {

		s = prep_all(s);

		String[] sss = s.split("[.]+");

		for (int i = 0; i < sss.length; i++) {
			s = sss[i].trim();
			String[] ss = s.split("[ ]+");

			if (ss.length > 4) {

				stat.stop = stat.stop + "<br> <b><i> - </i></b> " + s;

				s = "это предложение пока слишком длинное для меня: " + s;
				page(req, resp, s);
				return;

			}

			if (Character.isDigit(ss[1].charAt(0))) {
				stat.stop = stat.stop + "<br> <b><i> - </i></b> " + s;

				s = "Отношение (-"
						+ ss[1]
						+ "->) в высказывании (\""
						+ s
						+ "\") в данной версии Феофана не может начинаться с числа";
				page(req, resp, s);
				return;
			}

			else {

				// /////////////////////////////////
				// комментарий - пока добавляется мир
				// /////////////////////////////////

				stat.stop = stat.stop + "<br> <b><i> - </i></b> добавить: " + s;

				if (sr.indexOf(s) < 0) {
					sr = sr + " " + s + ". ";

					// sowl = get_owl(sr);
					// sowl = get_owl7(sr);
				}
			}
		}

		// ///////////////////////////////////

		// get_owl82();

		sowl = get_owl83(sr, "http://owl.feofan.com");

		// ///////////////////////////////////

		// sowl = get_owl81(sr);

		stat.page(req, resp, " Новый мир: \"" + stat.sr.trim() + "\"");
	}

	public static String get_owl83(String s, String sh) {
		try {
			Owl2Model qw = new Owl2Model(sh + "/rff?83.owl");

			s = prep_all(s);
			s = prepare_83(s);

			String[] sss = prep_all(s).split("[.]+");

			for (int i = 0; i < sss.length; i++) {

				String[] ss = sss[i].trim().split("[ ]+");

				String[] sss2 = s.split("[.]+");

				// проверка свойств на симметричность и т.п.

				for (int i4 = 0; i4 < sss2.length; i4++) {
					String[] ss4 = sss2[i4].trim().split("[ ]+");

					if (ss4[0].toLowerCase().equals("если")) {

						// ///////////
						s = s.replace(sss2[i4], "").trim();
						if (s.indexOf(".") == 0)
							s = s.substring(1).trim();

						stq.sfx = "/сим";

						s = s.replace(ss4[2], ss4[2] + stq.sfx);
						// ////////////

					}

				}
				sss2 = s.split("[.]+");
				// /////////////////////////////

				for (int i2 = 0; i2 < sss2.length; i2++) {
					String[] ss2 = sss2[i2].trim().split("[ ]+");

					if (ss2.length == 4) {
						qw.малыш_любит_N_малышек(qw.getOwlClass(ss2[0]),
								qw.getProperty(ss2[1].replace(stq.sfx, "")),
								Integer.parseInt(ss2[2]),
								qw.getOwlClass(ss2[3]));

					}

					if (ss2.length == 1) {
						if (socrat(ss2[0])) {
							qw.manager.applyChange(new AddAxiom(qw.ontology,
									qw.factory.getOWLSameIndividualAxiom(
											qw.getIndividual(ss2[0]),
											qw.getIndividual(ss2[0]))));
						} else if (!socrat(ss2[0])) {
							qw.isSubClassOf(qw.getOwlClass(ss2[0]),
									qw.factory.getOWLThing());
						}
					}

					if (ss2.length == 2) {

						if (socrat(ss2[0]) && ss2[0].indexOf("_") > -1
								&& !socrat(ss2[1]))

						{
							String sss3 = ss2[0].replace("_", " ");
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

						if (socrat(ss2[0]) && !socrat(ss2[1])) {
							qw.hasClass(qw.getIndividual(ss2[0]),
									qw.getOwlClass(ss2[1]));
						} else if (!socrat(ss2[0]) && !socrat(ss2[1])) {
							qw.isSubClassOf(qw.getOwlClass(ss2[0]),
									qw.getOwlClass(ss2[1]));
						} else if (socrat(ss2[0]) && socrat(ss2[1])) {
							qw.manager.applyChange(new AddAxiom(qw.ontology,
									qw.factory.getOWLSameIndividualAxiom(
											qw.getIndividual(ss2[0]),
											qw.getIndividual(ss2[1]))));
						} else if (!socrat(ss2[0]) && socrat(ss2[1])) {

							qw.hasClass(qw.getIndividual(ss2[1]),
									qw.getOwlClass(ss2[0]));
						}
					}

					if (ss2.length == 3) {

						if (ss2[1].indexOf("/сим") > -1) {
							// ss2[1]=ss2[1].replace("/сим","");
							qw.isSymmetric(qw.getProperty(ss2[1].replace(stq.sfx,
									"")));
						}

						if (socrat(ss2[0]) && socrat(ss2[2])
								&& ss2[1].equals("не")) {
							qw.differentIndividuals(qw.getIndividual(ss2[0]),
									qw.getIndividual(ss2[2]));
						} else 
							if (!socrat(ss2[0]) && !socrat(ss2[2])) {
							qw.assertDomainAndRange(
									qw.getProperty(ss2[1].replace(stq.sfx, "")),
									qw.getOwlClass(ss2[0]),
									qw.getOwlClass(ss2[2]));
						}
						else 
							if (!socrat(ss2[0]) && !socrat(ss2[2])
								&& ss2[1].equals("не")) {
							qw.manager.applyChange(new AddAxiom(qw.ontology,
									qw.factory.getOWLDisjointClassesAxiom(
											qw.getOwlClass(ss2[0]),
											qw.getOwlClass(ss2[2]))));

						} else 
							if (socrat(ss2[0]) && n(ss2[2])) {

							OWLDataProperty hasAge = qw.getDataProperty(ss2[1]
									.replace(stq.sfx, ""));
							String sn = ss2[2].trim();
							int n = Integer.parseInt(sn);
							OWLIndividual ind = qw.getIndividual(ss2[0]);
							qw.assertFact(hasAge, ind, n);
						} else

						if (socrat(ss2[0]) && socrat(ss2[2])) {
							qw.assertFact(ss2[1].replace(stq.sfx, ""), ss2[0],
									ss2[2]);
						} else 
							if (!socrat(ss2[0]) && !socrat(ss2[2])) {
							qw.assertDomainAndRange(
									qw.getProperty(ss2[1].replace(stq.sfx, "")),
									qw.getOwlClass(ss2[0]),
									qw.getOwlClass(ss2[2]));
						}
						else 
							if (!socrat(ss2[0]) && socrat(ss2[2])) {
								qw.любит_в_точности_N_Сократов(qw.getOwlClass(ss2[0]),
										qw.getProperty(ss2[1].replace(stq.sfx, "")),
										1,
										qw.getIndividual(ss2[2]));

					}


					}
				}
				sowl = qw.sowl();
				// stat.posti(st.sh + "/w2f", "83.owl", s);
				//s = w2f("83.owl", sowl);

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

					else if (socrat(ss[0]) && n(ss[2])) {

						OWLDataProperty hasAge = qw.getDataProperty(ss[1]);
						String sn = ss[2].trim();
						int n = Integer.parseInt(sn);
						OWLIndividual ind = qw.getIndividual(ss[0]);

						qw.assertFact(hasAge, ind, n);

					}

					else if (socrat(ss[0]) && socrat(ss[2])) {

						qw.assertFact(ss[1], ss[0], ss[2]);

					}

					else if (!socrat(ss[0]) && !socrat(ss[2])) {
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

	public static String prepare_83(String s) {

		while (s.indexOf("(") > -1 && s.indexOf(")") > -1
				&& s.indexOf(")") > s.indexOf("("))
			s = s.replace(s.substring(s.indexOf("("), s.indexOf(")") + 1), "");

		s = s.replace("-", "").replace("это", "").replace(" и ", "_");
		s = s.replaceAll("[ ]+", " ");
		return s.trim();
	}

	

	

	public static String fwr2(String sname, String s) {
		try {
			Query query = new Query("__BlobInfo__");
			query.setFilter(FilterOperator.EQUAL.of("filename", sname));

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			PreparedQuery pq = datastore.prepare(query);
			List<Entity> entList = pq.asQueryResultList(FetchOptions.Builder
					.withLimit(10));

			if (!entList.isEmpty())

				BlobstoreFS.delete(new BlobKey(entList.get(0).getKey()
						.getName()));

			FileService fileService = FileServiceFactory.getFileService();
			AppEngineFile file = fileService.createNewBlobFile("text/plain",
					sname);
			boolean lock = false;
			FileWriteChannel writeChannel = fileService.openWriteChannel(file,
					lock);
			PrintWriter out = new PrintWriter(Channels.newWriter(writeChannel,
					"UTF8"));
			out.println(s);
			out.close();
			file = new AppEngineFile(file.getFullPath());
			lock = true;
			writeChannel = fileService.openWriteChannel(file, lock);
			writeChannel.closeFinally();

		} catch (IOException e) {

			return "не кря";
		}
		return "кря";
	}

	public static String fwr1(String s) {

		try {

			Query query = new Query("__BlobInfo__");

			// query.addFilter("filename", FilterOperator.EQUAL, "test4");
			query.setFilter(FilterOperator.EQUAL.of("filename", s));

			// /////

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			PreparedQuery pq = datastore.prepare(query);
			List<Entity> entList = pq.asQueryResultList(FetchOptions.Builder
					.withLimit(10));

			if (entList.isEmpty())
				return "нет такого файла";

			s = entList.get(0).getKey().getName();

			// ///

			BlobKey blobKey = new BlobKey(s);
			FileService fileService = FileServiceFactory.getFileService();
			AppEngineFile file = fileService.getBlobFile(blobKey);
			FileReadChannel readChannel = fileService.openReadChannel(file,
					false);
			BufferedReader reader = new BufferedReader(Channels.newReader(
					readChannel, "UTF8"));

			String thisLine = "";
			s = "";
			while ((thisLine = reader.readLine()) != null)
				s = s + thisLine + " \r\n ";
			readChannel.close();

		} catch (Exception ee) {
			s = ee.toString();
		}

		return s;
	}
	
}
