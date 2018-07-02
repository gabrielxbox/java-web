/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dao;

import be.model.Dependente;
import be.model.Vacina;
import br.util.HibernateUtil;
import br.util.MensagensView;
import com.sun.org.apache.xml.internal.serializer.utils.Messages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.event.ActionEvent;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;


/**
 *
 * @author Gabri
 */
public class DependenteDao {
    
    private List<Vacina> listaVacina;

    public boolean salvar_dependente(Dependente dependente) {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction trasacao = sessao.beginTransaction();
            
            sessao.save(dependente);
            trasacao.commit();
            sessao.close();
            return true;
        } catch (HibernateException e) {
            System.err.println("ERRO AO SALVAR DEPENDENTE: " + e.getMessage());
            sessao.getTransaction().rollback();
        }
        return false;
    }
   
    public List<Dependente> getListaDependentes(){
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        List<Dependente> deps = null;
        Dependente dep = new Dependente();
        try {
                Transaction ts = sessao.beginTransaction();
                Criteria crit = sessao.createCriteria(dep.getClass());

                crit.add(Restrictions.eq("usuario_id_usuario", dep.getUsuario().getId_usuario()).ignoreCase());
                List ref = crit.list();
                for(int l = 0;l < ref.size();l++){
                    Dependente ndep = (Dependente) ref.get(l);
                    deps.add(ndep);
                    System.out.println("DEPENDENTES >>>>>>>>>>>>>>>>>>"+deps.get(l).getNome());
                }
                ts.commit();
                sessao.close();
             
        } catch (HibernateException e) {
            System.out.println("br.dao.UsuarioDao.logar_usuario()"+e);
        }
        return deps;
    }
    
    
    public void atualizar(Dependente dependente){
        HibernateUtil.getSessionFactory();
        Session sessao = HibernateUtil.getSession();
        
        try {
            dependente.setNome(dependente.getNome());
            dependente.setDtNasc(dependente.getDtNasc());
            sessao.update(dependente);
            Transaction trasacao = sessao.beginTransaction();
            trasacao.commit();
        } catch (HibernateException e) {
        }  finally{
           sessao.close();
        }
  
    }
    public boolean alterar_cadastro(Dependente dep) {

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        try {
            sessao.beginTransaction();
            sessao.update(dep);
            sessao.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            if (sessao != null) {
                sessao.getTransaction().rollback();
                System.out.println("ERROR AO EDITAR DEPENDENTES: " + e.getMessage());
            }
        } finally {
            sessao.close();
        }
        return false;
    }
    
    public void deletar_dependente(Dependente dependente){
     HibernateUtil.getSessionFactory();
     Session sessao = HibernateUtil.getSession();
        try {
            sessao.delete(dependente);
            Transaction transaca = sessao.beginTransaction();
            transaca.commit();
        } catch (Exception e) {
        
        }finally{
            sessao.close();
        }
  
      
    }
    
//    a qui
     public List<Vacina> getListaVacina(Dependente escolhido){
        System.out.println("br.dao.UsuarioDao.getlistaVacina()");
        listaVacina = new ArrayList<Vacina>();
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction ts = sessao.beginTransaction();
            Vacina vac = new Vacina();
            Criteria crit = sessao.createCriteria(vac.getClass());
            
            crit.add(Restrictions.eq("dependente", escolhido));
            List ref = crit.list();
            System.out.println("DEPENDENTE: "+escolhido.getNome()+" VACINA: vacinas "+ref.size());
            
            for(int r = 0;r < ref.size();r++){
                Vacina nvac = (Vacina) ref.get(r);
                listaVacina.add(nvac);
                System.out.println("INSERT ON VAC_LIST:" +nvac.getNome());
            }
            //escolhido.setVacinas(listaVacina);
            ts.commit();
            sessao.close();
             
        } catch (HibernateException e) {
            System.out.println("br.dao.UsuarioDao.getlistaVacina()"+e);
        }
        return listaVacina;
     }
    
     
  public List<Vacina> getListaVacinaG(){
        System.out.println("br.dao.UsuarioDao.getlistaVacina()");
        listaVacina = new ArrayList<Vacina>();
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        try {
            Dependente escolhido = new Dependente();
            Transaction ts = sessao.beginTransaction();
            Vacina vac = new Vacina();
            Criteria crit = sessao.createCriteria(vac.getClass());
            
            crit.add(Restrictions.eq("dependente", escolhido));
            List ref = crit.list();
            System.out.println("DEPENDENTE: "+escolhido.getNome()+" VACINA: vacinas "+ref.size());
            
            for(int r = 0;r < ref.size();r++){
                Vacina nvac = (Vacina) ref.get(r);
                listaVacina.add(nvac);
                System.out.println("INSERT ON VAC_LIST:" +nvac.getNome());
            }
            //escolhido.setVacinas(listaVacina);
            ts.commit();
            sessao.close();
             
        } catch (HibernateException e) {
            System.out.println("br.dao.UsuarioDao.getlistaVacina()"+e);
        }
        return listaVacina;
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
    
    
   
    












}
