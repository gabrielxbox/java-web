/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.generica;

import br.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Gabri

 */
public abstract class GenericDao<T> {
 private final Class typeClass;
private Session sessao ;
   
 public GenericDao(Class typeClass) {
        this.typeClass = typeClass;
        HibernateUtil.getSessionFactory();
        
        Session sessao = HibernateUtil.getSession();
        Transaction transacao = sessao.beginTransaction();
        try {
            sessao.save(typeClass);
            transacao.commit();
            
     } catch (HibernateException e) {
            if(transacao !=null){ 
              sessao.close();
                
            }
     }
                
    }
 
 
}
