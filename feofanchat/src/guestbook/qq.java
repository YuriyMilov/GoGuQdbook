package guestbook;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
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
		String s = req.getParameter("p2").trim();
		String s2=req.getParameter("test");		
		if(s2!=null)
		{
			if(req.getParameter("test").contains("сбоку"))
				s=Нтот3ктоП1(sh, req.getParameter("p2"));
			if(req.getParameter("test").contains("форум"))
				s=stq.форум(sh, req.getParameter("p2"));
		if(req.getParameter("test").contains("chain"))
			s=ййочередь(sh);			
		if(req.getParameter("test").contains("totkto"))
			s=stq.totkto(sh);
		if(req.getParameter("test").contains("test2"))
			s=test2(sh,"");
		stat.page(req, resp, s);
		return;
		}
		else if (s == null) {
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
		else
			if (s.indexOf("p2=") > -1)
				doPost(req, resp);
		
	}
	public static String ййочередь2(String ш){

		stat.owl_file = "rff?83.owl";
		Owl2Model qw = new Owl2Model(ш + "/" + stat.owl_file);
		String sd = "", s2="";
		
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

	public static String ййочередь(String ш){

		stat.owl_file = "rff?83.owl";
		Owl2Model qw = new Owl2Model(ш + "/" + stat.owl_file);



		OWLIndividual Пер =qw.getIndividual("Первый_дом");
		OWLIndividual А = qw.getIndividual("Второй_дом");
		OWLIndividual Б = qw.getIndividual("Третий_дом");
		OWLIndividual В = qw.getIndividual("Четвертый_дом");
		OWLIndividual Г = qw.getIndividual("Пятый_дом");
		OWLIndividual Д = qw.getIndividual("Шестой_дом");
		OWLIndividual Пос =qw.getIndividual("Седьмой_дом");		
		
		OWLObjectPropertyExpression слева = qw.getProperty("справа_от");
		OWLObjectPropertyExpression справа = qw.getProperty("слева_от");
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
		OWLClass второй_или_предпоследний = qw.getOwlClass("перед_крайними");		
 		OWLClassAxiom аксиома1 = qw.factory.getOWLEquivalentClassesAxiom(второй_или_предпоследний,или);		
 		qw.manager.addAxiom(qw.ontology, аксиома1);		
 	

 		
	OWLClass очередь = qw.getOwlClass("очередь");
	qw.manager.addAxiom(qw.ontology, qw.factory.getOWLSubClassOfAxiom(второй_или_предпоследний,очередь));
	qw.manager.addAxiom(qw.ontology, qw.factory.getOWLSubClassOfAxiom(второй,очередь));
	qw.manager.addAxiom(qw.ontology, qw.factory.getOWLSubClassOfAxiom(пятый_справа,очередь));
	qw.manager.addAxiom(qw.ontology, qw.factory.getOWLSubClassOfAxiom(предпоследний,очередь));

	
//	String s1="Тот, кто справа_от Четвертый_дом, тот Пятый_дом. Тот, кто справа_от Четвертый_дом, тот Третий_дом.Тот, кто справа_от Третий_дом, тот Второй_дом.Тот, кто справа_от Второй_дом, тот Третий_дом.Тот, кто справа_от Первый_дом, тот Второй_дом. ";
//	
//	s1="";
//	String[] sss=s1.split("[.]");
//	for (String s:sss)
//	{
//		if(s.toLowerCase().indexOf("тот, кто")!=s.toLowerCase().lastIndexOf("тот, кто"))
//		{
//		s=s.replace(",", "").replace("Тот", "").replace("тот", "").replace("кто", "").replaceAll("[ ]+", " ").trim();
//		String[] ss=s.split("[ ]");
//		stq.qqcpcec(qw,ss[0]+"_"+ss[1],ss[2],ss[3]+"_"+ss[4].replace(".", ""));
//
//	}
//	}
	
	
		
	//stq.qq1ipec(qw,"Пончик","играет_на_Баяне");
	//stq.qqipiec_ii(qw,"Незнайка","справа_от","Пончик");
	
	
	stat.sowl = qw.sowl();
		
			return "<a href=/owl > OWL </a>";
		}
	
	public static String ййочередь3(String ш){

		stat.owl_file = "rff?83.owl";
		Owl2Model qw = new Owl2Model(ш + "/" + stat.owl_file);



		OWLIndividual Пер =qw.getIndividual("Первый_дом");
		OWLIndividual А = qw.getIndividual("Второй_дом");
		OWLIndividual Б = qw.getIndividual("Третий_дом");
		OWLIndividual В = qw.getIndividual("Четвертый_дом");
		OWLIndividual Г = qw.getIndividual("Пятый_дом");
		OWLIndividual Д = qw.getIndividual("Шестой_дом");
		OWLIndividual Пос =qw.getIndividual("Седьмой_дом");		
		
		OWLObjectPropertyExpression слева = qw.getProperty("справа_от");
		OWLObjectPropertyExpression справа = qw.getProperty("слева_от");
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
	
		
		OWLClass последний = qw.getOwlClass("Последний_дом");
		
		OWLClassAssertionAxiom аксиома_о_последнем = qw.factory.getOWLClassAssertionAxiom(последний, Пос);
		qw.manager.addAxiom(qw.ontology, аксиома_о_последнем);	
	
		OWLClass предпоследний = qw.getOwlClass("Предпоследний_дом");
		OWLClassExpression имеет_последний_справа = qw.factory.getOWLObjectSomeValuesFrom(справа, последний);
		OWLClassAxiom аксиома2 = qw.factory.getOWLEquivalentClassesAxiom(предпоследний,имеет_последний_справа);
		qw.manager.addAxiom(qw.ontology, аксиома2);		
		
		
	
		OWLClass пятый_справа = qw.getOwlClass("Пятый_дом_справа");
		
		OWLClassExpression второй_справа = qw.factory.getOWLObjectSomeValuesFrom(справа, имеет_последний_справа);
		OWLClassExpression третий_справа = qw.factory.getOWLObjectSomeValuesFrom(справа, второй_справа);
		OWLClassExpression четвертый_справа = qw.factory.getOWLObjectSomeValuesFrom(справа, третий_справа);
		OWLClassAxiom аксиома_о_пятом_справа = qw.factory.getOWLEquivalentClassesAxiom(пятый_справа,четвертый_справа);
		qw.manager.addAxiom(qw.ontology, аксиома_о_пятом_справа);	

		
		OWLClass первый = qw.getOwlClass("Первый_дом");
		OWLClassAssertionAxiom аксиома_о_первом = qw.factory.getOWLClassAssertionAxiom(первый, Пер);
		qw.manager.addAxiom(qw.ontology, аксиома_о_первом);	



		OWLClass второй = qw.getOwlClass("Второй_дом");
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

	
//	String s1="Тот, кто справа_от Четвертый_дом, тот Пятый_дом. Тот, кто справа_от Четвертый_дом, тот Третий_дом.Тот, кто справа_от Третий_дом, тот Второй_дом.Тот, кто справа_от Второй_дом, тот Третий_дом.Тот, кто справа_от Первый_дом, тот Второй_дом. ";
//	
//	s1="";
//	String[] sss=s1.split("[.]");
//	for (String s:sss)
//	{
//		if(s.toLowerCase().indexOf("тот, кто")!=s.toLowerCase().lastIndexOf("тот, кто"))
//		{
//		s=s.replace(",", "").replace("Тот", "").replace("тот", "").replace("кто", "").replaceAll("[ ]+", " ").trim();
//		String[] ss=s.split("[ ]");
//		stq.qqcpcec(qw,ss[0]+"_"+ss[1],ss[2],ss[3]+"_"+ss[4].replace(".", ""));
//
//	}
//	}
	
	
	
	
	
	//stq.qq1ipec(qw,"Пончик","играет_на_Баяне");
	//stq.qqipiec_ii(qw,"Незнайка","справа_от","Пончик");
	
	
	stat.sowl = qw.sowl();
		
			return "<a href=/owl > OWL </a>";
		}
	public static String форум( String ш)
	{
				String ss = "Незнайка и Пончик - малыш. Кнопочка и Синеглазка это малышка. Если x дружитъс y, то y  дружит_с x. малыш  дружитъс 1 малышка. малышка  дружитъс 1 малыш. Пончик  дружитъс Синеглазка. СПРАШКЛ(Незнайка дружитъс ?кем)";
				ss = stq.mm_get_otvet(ш, "тест форум", ss, "forum@feofan.com");
				stq.mail_admins("тест форум", ss);
				
			return "тест форум - полёт нормальный - см. письмо на форуме";
	}
	public static String test1( String ш, String s)
	{
		s = "слева_от или справа_от значит рядом_с.";
		
				stat.owl_file = "rff?83.owl";
				Owl2Model qw = new Owl2Model(ш + "/" + stat.owl_file);		
				
				
		OWLObjectProperty слева_от = qw.getProperty("слева_от");
		OWLObjectProperty справа_от = qw.getProperty("справа_от");
		OWLObjectProperty рядом = qw.getProperty("рядом");
		OWLInverseObjectPropertiesAxiom axiom1 = qw.factory
				.getOWLInverseObjectPropertiesAxiom(слева_от, справа_от);
		qw.manager.addAxiom(qw.ontology, axiom1);

		 OWLSubObjectPropertyOfAxiom axiom2 = qw.factory.getOWLSubObjectPropertyOfAxiom(слева_от, рядом);
		qw.manager.addAxiom(qw.ontology, axiom2);
		 OWLSubObjectPropertyOfAxiom axiom3 = qw.factory.getOWLSubObjectPropertyOfAxiom(справа_от,рядом);
		qw.manager.addAxiom(qw.ontology, axiom3);

		
				stat.sowl = qw.sowl();	
			return "<a href=/owl > OWL </a>";
	}
	
	public static String test2(String ш,String s) {
		
		stat.owl_file = "rff?83.owl";
		Owl2Model qw = new Owl2Model(ш + "/" + stat.owl_file);

		s="Тот, кто играет_на Валторна, тот живёт_в тот, кто где_живёт тот, кто выращивает Кувшинки. ";
		s="играет_на Валторна экв живёт_в где_живёт выращивает Кувшинки. ";
	
		
	OWLClass играет_на_Валторна	 = qw.getOwlClass("играет_на_Валторна");
	
	OWLClassExpression a = qw.factory.getOWLObjectSomeValuesFrom(qw.getProperty("выращивает"), qw.factory.getOWLObjectOneOf(
			qw.getIndividual("Кувшинки")));
	OWLClassExpression b = qw.factory.getOWLObjectSomeValuesFrom(qw.getProperty("где_живёт"), a);
	OWLClassExpression c = qw.factory.getOWLObjectSomeValuesFrom(qw.getProperty("живёт_в"), b);
	
	OWLClassAxiom аксиома_abcN = qw.factory.getOWLEquivalentClassesAxiom(играет_на_Валторна,c);
	qw.manager.addAxiom(qw.ontology, аксиома_abcN);	

	
		stat.sowl = qw.sowl();	
		return "<a href=/owl > OWL </a>";
	}
	public static String Нтот3ктоП1(String ш,String s) {
		
		stat.owl_file = "rff?83.owl";
		Owl2Model qw = new Owl2Model(ш + "/" + stat.owl_file);


		Нтот3ктоП(qw,s);
		
		
		stat.sowl = qw.sowl();	
		return "<a href=/owl > OWL </a>";
	}
	
	public static void Нтот3ктоП(Owl2Model qw,String s) {
		
		//s="Незнайка перед тот, кто слева_от тот, кто за тот, кто справа_от Пончик.";

		//s="Незнайка перед A.";
		//s="A слева_от Б.";
		//s="Б за Ц.";
		//s="Ц справа_от Пончик.";
		
		//int n = s.split("тот, кто").length-1;
		//кто_Незнайка экв класс 'перед тот, кто слева_от тот, кто за тот, кто справа_от Пончик'
	OWLClass понятие_о_Незнайке_1 = qw.getOwlClass("кто_Незнайка");
	OWLClassExpression Ц = qw.factory.getOWLObjectSomeValuesFrom(qw.getProperty("справа_от"), qw.factory.getOWLObjectOneOf(
			qw.getIndividual("Пончик")));
	OWLClassExpression Б = qw.factory.getOWLObjectSomeValuesFrom(qw.getProperty("за"), Ц);
	OWLClassExpression А = qw.factory.getOWLObjectSomeValuesFrom(qw.getProperty("слева_от"), Б);
	OWLClassExpression Незнайка = qw.factory.getOWLObjectSomeValuesFrom(qw.getProperty("перед"), А);
	OWLClassAxiom аксиома_кто_Незнайка = qw.factory.getOWLEquivalentClassesAxiom(понятие_о_Незнайке_1, Незнайка);
	qw.manager.addAxiom(qw.ontology, аксиома_кто_Незнайка);	

	// Незнайка инд + кто_Незнайка экв класс {Незнайка}
	OWLClassExpression понятие_о_Незнайке_2 = qw.factory.getOWLObjectOneOf(qw.getIndividual("Незнайка"));
	OWLClassAxiom аксиома_о_Незн = qw.factory.getOWLEquivalentClassesAxiom(понятие_о_Незнайке_1, понятие_о_Незнайке_2);
	qw.manager.addAxiom(qw.ontology, аксиома_о_Незн);
	
	}
	public void onModuleLoad() {
	}

	private static final long serialVersionUID = 1L;
}