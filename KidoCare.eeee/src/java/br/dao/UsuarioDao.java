
package br.dao;

import be.model.Dependente;
import be.model.Usuario;

import br.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class UsuarioDao {
    
    private List<Dependente> dependentesList = new ArrayList<Dependente>();

    public boolean salvar_cadastro(Usuario usuario) {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction ts = sessao.beginTransaction();
            
            sessao.save(usuario);
            ts.commit();
            sessao.close();
            return true;
           
        } catch (HibernateException e) {
            if (sessao != null) {
                sessao.getTransaction().rollback();
                System.out.println("ERROR AO SALVAR USUARIO: " + e.getMessage());
                sessao.close();
            }
        }
        return false;
 
    }
    
    public boolean alterar_cadastro(Usuario usuario) {

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        try {
            sessao.beginTransaction();
            sessao.update(usuario);
            sessao.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            if (sessao != null) {
                sessao.getTransaction().rollback();
                System.out.println("ERROR AO EDITAR USUARIO: " + e.getMessage());
            }
        } finally {
            sessao.close();
        }
        return false;
    }

     public Usuario logar_usuario(Usuario login) {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Usuario nuser = new Usuario();
        try {
            Transaction ts = sessao.beginTransaction();
            Usuario l = login;
            Criteria crit = sessao.createCriteria(login.getClass());

            crit.add(Restrictions.eq("email", l.getEmail()).ignoreCase());
            crit.add(Restrictions.eq("senha", l.getSenha()));
            List ref = crit.list();
            nuser = (Usuario) ref.get(0);
            System.out.println("TEST" +nuser.getNome());

            sessao.close();
             
        } catch (HibernateException e) {
            System.out.println("br.dao.UsuarioDao.logar_usuario()"+e);
        }
        return nuser;
    }
     
    public List<Dependente> getDependentesList(Usuario login){
        System.out.println("br.dao.UsuarioDao.getDependentesList()");
        dependentesList = new ArrayList<Dependente>();
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction ts = sessao.beginTransaction();
            Dependente dep = new Dependente();
            Criteria crit = sessao.createCriteria(dep.getClass());
            
            crit.add(Restrictions.eq("usuario", login));
            List ref = crit.list();
            
            System.out.println("br.dao.UsuarioDao.getDependentesList()::::::::::::"+ref.size());
            for(int r = 0;r < ref.size();r++){
                Dependente ndep = (Dependente) ref.get(r);
                dependentesList.add(ndep);
                System.out.println("INSERT ON DEP_LIST:" +ndep.getNome());
            }
            login.setDependentes(dependentesList);
            ts.commit();
            sessao.close();
             
        } catch (HibernateException e) {
            System.out.println("br.dao.UsuarioDao.getDependentesList()"+e);
        }
        return dependentesList;
     }
     
}
