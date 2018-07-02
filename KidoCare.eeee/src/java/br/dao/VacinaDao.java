
package br.dao;

import be.model.Dependente;
import be.model.Vacina;
import br.util.HibernateUtil;
import java.util.Date;
import java.util.List;
import javax.faces.event.ActionEvent;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class VacinaDao {
   
    public boolean salvar(Vacina vacina) { 
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction trasacao = sessao.beginTransaction();
            sessao.save(vacina);
            trasacao.commit();
            sessao.close();
            return true;
        } catch (HibernateException e){
            System.err.println("ERRO AO SALVAR VACINA: " + e.getMessage());
            sessao.getTransaction().rollback();
            return false;
        }
    }        
//    gabriel
    public boolean removeG(Vacina vacina){
        HibernateUtil.getSessionFactory();
        Session sesacao =HibernateUtil.getSession();
        try {
        Transaction transacao = sesacao.beginTransaction();
        sesacao.delete(vacina);
        transacao.commit();
            sesacao.close();
          return true;
        } catch (HibernateException e) {
        System.out.println("erro ao deletar " + e.getMessage());
        sesacao.getTransaction().rollback();
        return false;
        }
    
                
                
        
    }
    
    public boolean editar(Vacina vacina) {
        HibernateUtil.getSessionFactory();
        Session sessao = HibernateUtil.getSession();
        try {
            Transaction trasacao = sessao.beginTransaction();
            sessao.update(vacina);
            trasacao.commit();
        } catch (HibernateException e) {
            System.out.println("ERROR:"+e);
            return false;
        }
        sessao.close();
        return true;
    }
    
    
   
    
    
    
    
    
    
    
    public boolean remover(Vacina vacina) {
        System.out.println("br.dao.VacinaDao.deletaCadastro()");
        HibernateUtil.getSessionFactory();
        Session sessao = HibernateUtil.getSession();
        try {
            Transaction tr = sessao.beginTransaction();
            sessao.delete(vacina);
            tr.commit();
            sessao.close();
            return true;
        } catch (HibernateException e) {
            System.err.println("ERRO AO SALVAR REMOVER: " + e.getMessage());
            sessao.getTransaction().rollback();
        }
            return false;
    }

    public List<Vacina> list() {
        HibernateUtil.getSessionFactory();
        Session sessao = HibernateUtil.getSession();
        Criteria criteria = sessao.createCriteria(Vacina.class);
        List<Vacina> lista = criteria.list();

        return lista;
    }
    
}
