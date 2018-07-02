package be.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table (name = "dependente")
 public class Dependente implements Serializable {
    @Id
    @Column (name = "id_dependente" )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_dependente;
    
    @Column (name = "nome" ,nullable = false)
    private String nome;
    @Temporal(TemporalType.DATE)
    @Column (name = "data_nasc",nullable = false)
    private Date dtNasc;
  
//    @JoinColumn(name = "id_usuario",referencedColumnName = "id_usuario",foreignKey = @ForeignKey(name ="fk_usuario"),nullable = false)
//    @ManyToOne( fetch = FetchType.LAZY)
    
    @ManyToOne (fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Usuario usuario;
    
    @OneToMany (cascade = CascadeType.MERGE, fetch = FetchType.EAGER , mappedBy = "dependente")
    @Fetch(FetchMode.SUBSELECT)
    private List<Vacina> vacinas; 
    
    
    public long getId_dependente() {
        return id_dependente;
    }

    public void setId_dependente(long id_dependente) {
        this.id_dependente = id_dependente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDtNasc() {
        return dtNasc;
    }

    public void setDtNasc(Date dtNasc) {
        this.dtNasc = dtNasc;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Vacina> getVacinas() {
        return vacinas;
    }

    public void setVacinas(List<Vacina> vacinas) {
        this.vacinas = vacinas;
    }

    
}
