/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dao;

import be.model.TipoVacina;
import br.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Gabri
 */
public class TipoVacinaDao implements Serializable{
    private List<TipoVacina> tiposVacinaList;
     
    public List<TipoVacina> getTipoVacinaList(){
        
        tiposVacinaList = new ArrayList<TipoVacina>();
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction ts = sessao.beginTransaction();
            TipoVacina tv = new TipoVacina();
            Criteria crit = sessao.createCriteria(tv.getClass());
            List ref = crit.list();
            
            for(int r = 0;r < ref.size();r++){
                TipoVacina ntv = (TipoVacina) ref.get(r);
                tiposVacinaList.add(ntv);
                System.out.println("INSERT ON TpVacina_LIST:" +ntv.getNome());
            }
            ts.commit();
            sessao.close();
             
        } catch (HibernateException e) {
            System.out.println("br.dao.UsuarioDao.getDependentesList()"+e);
        }
        return tiposVacinaList;
     }
}
