package dd;

import org.mindswap.pellet.jena.PelletReasonerFactory;
import com.clarkparsia.pellet.sparqldl.jena.SparqlDLExecutionFactory;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class stqdd {
	public static String sfx = "";


	public static String get_q(String sq) {
		String s = "PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX : <http://www.semanticweb.org/ontologies/2013/2/Ontology1363212625516.owl#>"
				+ sq;

		OntModel model = ModelFactory
				.createOntologyModel(PelletReasonerFactory.THE_SPEC);
		model.read("file:marriage.owl");
		com.hp.hpl.jena.query.Query q = QueryFactory.create(s);
		ResultSet r = SparqlDLExecutionFactory.create(q, model).execSelect();
		s = r.nextBinding().toString();
		return s;
	}


}