package be.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name = "tipo_vacina")
public class TipoVacina {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id_tipovacina")
    private long id_tipovacina;
    @Column
    private String nome;
    @Column
    private Integer dias; //tempo em dias depois donascimento para tomar vacina
  

    public long getId_tipovacina() {
        return id_tipovacina;
    }

    public void setId_tipovacina(long id_tipovacina) {
        this.id_tipovacina = id_tipovacina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

  
    
}
