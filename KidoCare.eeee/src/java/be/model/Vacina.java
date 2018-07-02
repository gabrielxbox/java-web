package be.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table (name = "vacina")
public class Vacina implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id_vacina")
    private long id_vacina;
    @Column (name = "nome",nullable = false)
    private String nome;
    @Column (name = "confirmar")
    private Integer confirmar = 0;
    @Column (name = "tipo_vacina")
    private Integer tipo_vacina;
    @Temporal(TemporalType.DATE)
    @Column (name = "data_prevista")
    private Date data_prevista;
    @Temporal(TemporalType.DATE)
    @Column (name = "data_aplicacao")
    private Date data_aplicacao;

    
    @ManyToOne (fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Dependente dependente;


   
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getTipo_vacina() {
        return tipo_vacina;
    }

    public void setTipo_vacina(Integer tipo_vacina) {
        this.tipo_vacina = tipo_vacina;
    }

    public Date getData_prevista() {
        return data_prevista;
    }

    public void setData_prevista(Date data_prevista) {
        this.data_prevista = data_prevista;
    }

    public Date getData_aplicacao() {
        return data_aplicacao;
    }

    public void setData_aplicacao(Date data_aplicacao) {
        this.data_aplicacao = data_aplicacao;
    }

    public long getId_vacina() {
        return id_vacina;
    }

    public void setId_vacina(long id_vacina) {
        this.id_vacina = id_vacina;
    }

    public Dependente getDependente() {
        return dependente;
    }

    public void setDependente(Dependente dependente) {
        this.dependente = dependente;
    }
}
