package guestbook;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

import com.google.gwt.core.client.EntryPoint;

public class qq extends HttpServlet implements EntryPoint {

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		resp.setContentType("text/html; charset=UTF8");
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();
		stat.sh = sh;
		
		String s = req.getParameter("p2").trim();	
		

 
		if (s == null) {
			stq.init(req, resp);
			return;
		} else 
			{
				
			if (!s.contains("?")) {
			if (s.indexOf(" ") < 0) {
				stat.command(s, req, resp);
				return;
			} else {
				stq.add_sr(s, sh);
				s = stat.sr;
			}
			

		} else {
			
			stat.stop = stat.stop + "<br> <b><i> - </i></b>" + s;
	
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
			stat.sr = stat.sr + "";
			s = stq.otvet(sh, s);
		}
	}
		// ////////////////////////////////////////////

		stat.page(req, resp, s);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();
		String s = req
				.getQueryString();
		
		stat.sh = sh;
		
		if (req.getQueryString() == null)
			stq.init(req, resp);
		else {
			String s2=req.getParameter("test");
			if(s2!=null)
			{
			if(req.getParameter("test").contains("forum"))
					s=форум(sh);
			if(req.getParameter("test").contains("kar"))
				s=кря(sh);

			if(req.getParameter("test").contains("totkto"))
				s=totkto(sh);

			stat.page(req, resp, s);
			return;
			}
		else
			if (s.indexOf("p2=") > -1)
				doPost(req, resp);
		}
	}
	public static String totkto(String ш){
		
		String s="Тот, кто играет_на Баян, тот живет_рядом_с тот, кто выращивает Розы.";
		String[] ss=null;
		int i = 0;
		if(s.toLowerCase().indexOf("тот, кто")!=s.toLowerCase().lastIndexOf("тот, кто"))
		{
			ss=s.split("тот, кто");
			
			for(String s2:ss)
			{
				s2=ss[i++];				
			}			
		}
		
		stat.owl_file = "rff?83.owl";
		Owl2Model qw = new Owl2Model(ш + "/" + stat.owl_file);
				

		
		OWLClassAxiom аксиома_о_кто_играет_на_Баяне = 
				qw.factory.getOWLEquivalentClassesAxiom(qw.getOwlClass("играет_на_Баяне"),qw.factory.getOWLObjectSomeValuesFrom(qw.getProperty("живет_рядом_с"), qw.getOwlClass("выращивает_Розы")));
		qw.manager.addAxiom(qw.ontology, аксиома_о_кто_играет_на_Баяне);	

		
		
		OWLEquivalentClassesAxiom аксиома_о_Незнайкe = 
				qw.factory.getOWLEquivalentClassesAxiom(qw.getOwlClass("выращивает_Розы"), qw.factory.getOWLObjectOneOf(qw.getIndividual("Незнайка")));
		qw.manager.addAxiom(qw.ontology, аксиома_о_Незнайкe);	
		
		
		
		OWLEquivalentClassesAxiom аксиома_о_Пончикe = 
				qw.factory.getOWLEquivalentClassesAxiom(qw.getOwlClass("играет_на_Баяне"), qw.factory.getOWLObjectOneOf(qw.getIndividual("Пончик")));
			qw.manager.addAxiom(qw.ontology, аксиома_о_Пончикe);	

		
		stat.sowl = qw.sowl();
			return "<a href=/owl > OWL </a>";
				
	}
	
	public static String кря(String ш){

		stat.owl_file = "rff?83.owl";
		Owl2Model qw = new Owl2Model(ш + "/" + stat.owl_file);



		OWLIndividual Пер =qw.getIndividual("Перв");
		OWLIndividual А = qw.getIndividual("А");
		OWLIndividual Б = qw.getIndividual("Б");
		OWLIndividual В = qw.getIndividual("В");
		OWLIndividual Г = qw.getIndividual("Г");
		OWLIndividual Д = qw.getIndividual("Д");
		OWLIndividual Пос =qw.getIndividual("Пос");		
		
		OWLObjectPropertyExpression слева = qw.getProperty("слева");
		OWLObjectPropertyExpression справа = qw.getProperty("справа");
		OWLInverseObjectPropertiesAxiom аксиома_инверсного_отношения =qw.factory.getOWLInverseObjectPropertiesAxiom(слева, справа);
		qw.manager.addAxiom(qw.ontology, аксиома_инверсного_отношения);

	
	OWLObjectPropertyAssertionAxiom аксиома_для_А = qw.factory.getOWLObjectPropertyAssertionAxiom(справа, А, Б);
	qw.manager.addAxiom(qw.ontology, аксиома_для_А);		

	
	OWLObjectPropertyAssertionAxiom аксиома_для_Пер = qw.factory.getOWLObjectPropertyAssertionAxiom(справа, Пер, А);
	qw.manager.addAxiom(qw.ontology, аксиома_для_Пер);		

		OWLObjectPropertyAssertionAxiom аксиома_для_Б = qw.factory.getOWLObjectPropertyAssertionAxiom(справа, Б, В);
		qw.manager.addAxiom(qw.ontology, аксиома_для_Б);		
	
		OWLObjectPropertyAssertionAxiom аксиома_для_В = qw.factory.getOWLObjectPropertyAssertionAxiom(справа, В, Г);
		qw.manager.addAxiom(qw.ontology, аксиома_для_В);		
	
		OWLObjectPropertyAssertionAxiom аксиома_для_Г = qw.factory.getOWLObjectPropertyAssertionAxiom(справа, Г, Д);
		qw.manager.addAxiom(qw.ontology, аксиома_для_Г);		
	
		OWLObjectPropertyAssertionAxiom аксиома_для_Д = qw.factory.getOWLObjectPropertyAssertionAxiom(справа, Д, Пос);
		qw.manager.addAxiom(qw.ontology, аксиома_для_Д);		
	
		
		OWLClass последний = qw.getOwlClass("последний");
		
		OWLClassAssertionAxiom аксиома_о_последнем = qw.factory.getOWLClassAssertionAxiom(последний, Пос);
		qw.manager.addAxiom(qw.ontology, аксиома_о_последнем);	
	
		OWLClass предпоследний = qw.getOwlClass("предпоследний");
		OWLClassExpression имеет_последний_справа = qw.factory.getOWLObjectSomeValuesFrom(справа, последний);
		OWLClassAxiom аксиома2 = qw.factory.getOWLEquivalentClassesAxiom(предпоследний,имеет_последний_справа);
		qw.manager.addAxiom(qw.ontology, аксиома2);		
		
		
	
		OWLClass пятый_справа = qw.getOwlClass("пятый_справа");
		
		OWLClassExpression второй_справа = qw.factory.getOWLObjectSomeValuesFrom(справа, имеет_последний_справа);
		OWLClassExpression третий_справа = qw.factory.getOWLObjectSomeValuesFrom(справа, второй_справа);
		OWLClassExpression четвертый_справа = qw.factory.getOWLObjectSomeValuesFrom(справа, третий_справа);
		OWLClassAxiom аксиома_о_пятом_справа = qw.factory.getOWLEquivalentClassesAxiom(пятый_справа,четвертый_справа);
		qw.manager.addAxiom(qw.ontology, аксиома_о_пятом_справа);	

		
		OWLClass первый = qw.getOwlClass("первый");
		OWLClassAssertionAxiom аксиома_о_первом = qw.factory.getOWLClassAssertionAxiom(первый, Пер);
		qw.manager.addAxiom(qw.ontology, аксиома_о_первом);	



		OWLClass второй = qw.getOwlClass("второй");
		OWLClassExpression о_втором = qw.factory.getOWLObjectSomeValuesFrom(слева, первый);
		OWLClassAxiom аксиома_о_втором = qw.factory.getOWLEquivalentClassesAxiom(второй,о_втором);
		qw.manager.addAxiom(qw.ontology, аксиома_о_втором);	
		

		OWLClassExpression или = qw.factory.getOWLObjectUnionOf(второй, предпоследний);
		OWLClass второй_или_предпоследний = qw.getOwlClass("второй_или_предпоследний");		
 		OWLClassAxiom аксиома1 = qw.factory.getOWLEquivalentClassesAxiom(второй_или_предпоследний,или);		
 		qw.manager.addAxiom(qw.ontology, аксиома1);		
 	

 		
	OWLClass очередь = qw.getOwlClass("очередь");
	qw.manager.addAxiom(qw.ontology, qw.factory.getOWLSubClassOfAxiom(второй_или_предпоследний,очередь));
	qw.manager.addAxiom(qw.ontology, qw.factory.getOWLSubClassOfAxiom(второй,очередь));
	qw.manager.addAxiom(qw.ontology, qw.factory.getOWLSubClassOfAxiom(пятый_справа,очередь));
	qw.manager.addAxiom(qw.ontology, qw.factory.getOWLSubClassOfAxiom(предпоследний,очередь));

	
	OWLIndividual Красный_дом = qw.getIndividual("Красный_дом");
	OWLObjectProperty живут_в = qw.getProperty("живут_в");
	
	OWLClass кто_выращивают_Розы = qw.getOwlClass("кто_выращивают_Розы");
	OWLClassExpression живут_в_Красный_дом = qw.factory.getOWLObjectHasValue(живут_в, Красный_дом);
	
	OWLClassAxiom кто_выращивают_Розы_живут_в_Красный_дом = qw.factory.getOWLEquivalentClassesAxiom(кто_выращивают_Розы,живут_в_Красный_дом);
	qw.manager.addAxiom(qw.ontology, кто_выращивают_Розы_живут_в_Красный_дом);		

	
	OWLClass кто_играет_на_Баяне = qw.getOwlClass("кто_играет_на_Баяне");
	OWLClassExpression о_кто_играет_на_Баяне = qw.factory.getOWLObjectSomeValuesFrom(справа, кто_выращивают_Розы);
	OWLClassAxiom аксиома_о_кто_играет_на_Баяне = qw.factory.getOWLEquivalentClassesAxiom(кто_играет_на_Баяне,о_кто_играет_на_Баяне);
	qw.manager.addAxiom(qw.ontology, аксиома_о_кто_играет_на_Баяне);	

	qw.hasClass(qw.getIndividual("Незнайка"), qw.getOwlClass("кто_выращивают_Розы"));
	
		stat.sowl = qw.sowl();
		
			return "<a href=/owl > OWL </a>";
		}
	public static String форум( String ш)
	{
				String ss = "Незнайка и Пончик - малыш. Кнопочка и Синеглазка это малышка. Если x дружитъс y, то y  дружит_с x. малыш  дружитъс 1 малышка. малышка  дружитъс 1 малыш. Пончик  дружитъс Синеглазка. СПРАШКЛ(Незнайка дружитъс ?кем)";
				//ss="ss=ряд это только А,Б,В,Г. Б > А. В > Б. Г > В. если Б > А, то А < Б. Что < Г?"; 
				
				ss = stq.mm_get_otvet(ш, "тест форум", ss, "forum@feofan.com");
				stq.mail_admins("тест форум", ss);
				
			return "тест форум - полёт нормальный - см. письмо на форуме";
	}
	
	
	public void onModuleLoad() {
	}

	private static final long serialVersionUID = 1L;
}