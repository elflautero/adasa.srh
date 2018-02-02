package adasa.srh.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import adasa.srh.entity.Denuncia;
import adasa.srh.entity.HibernateUtil;

public class DenunciaDao {

	public void addDenuncia (Denuncia denuncia) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.save(denuncia);
		s.getTransaction().commit();
		s.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Denuncia> listDenunciaEntity(String strPesquisa) {
		List<Denuncia> list = new ArrayList<Denuncia>();
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		Criteria crit = s.createCriteria(Denuncia.class);
		crit.add(Restrictions.like("Documento_Denuncia", '%' + strPesquisa + '%'));
		list = crit.list();
		// SQL list = s.createSQLQuery("SELECT * FROM Denuncia WHERE Documento_Denuncia LIKE '%strPesquisa%'").list();
		//list = s.createQuery("from Denuncia d where d.Documento_Denuncia= : strPesquisa").setString("strPesquisa",strPesquisa).list();
		//list = s.createQuery("from Denuncia").setString("Documento_Denuncia", strPesquisa).list();
		s.getTransaction().commit();
		s.close();
		return list;
	}
	
	// "from Denuncia"
	
	/* não consegui 
	@SuppressWarnings("unchecked")
	public List<Denuncia> listDenunciaEntity(String strRecebePesquisa) { //Denuncia denuncia
	
	Example exDenuncia = Example.create(strRecebePesquisa)
		    .enableLike(MatchMode.ANYWHERE);
		Example exDocumentoDenuncia = Example.create(strRecebePesquisa.get); strRecebePesquisa
		Session s = HibernateUtil.getSessionFactory().openSession();
		List<Denuncia> list = 
		    s.createCriteria(Denuncia.class).add(exDenuncia)
		    .createCriteria("Documento_Denuncia").add(exDocumentoDenuncia)
		      .list();
		return list;
	}
	*/
	
}
