package adasa.srh.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

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
	public List<Denuncia> listDenunciaEntity() {
		List<Denuncia> list = new ArrayList<Denuncia>();
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		list = s.createQuery("from Denuncia").list();
		s.getTransaction().commit();
		s.close();
		return list;
	}
}
