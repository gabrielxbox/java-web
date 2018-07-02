package br.beam;

import be.model.Dependente;
import be.model.TipoVacina;
import be.model.Usuario;
import be.model.Vacina;
import br.dao.DependenteDao;
import br.dao.TipoVacinaDao;
import br.dao.UsuarioDao;
import br.dao.VacinaDao;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "mainBeam")
@SessionScoped
public class MainBeam {
    
    private Usuario usuario = new Usuario();
    private UsuarioDao usuarioDao = new UsuarioDao();
    private String loggedId = "0";
    private Dependente dependente = new Dependente();
    private DependenteDao dependenteDao = new DependenteDao();
    private String dependenteEscolhido;
    private List<String> listaDependentes = new ArrayList<String>();
    private Vacina vacina = new Vacina();
    private VacinaDao vacinaDao = new VacinaDao();
    private int periodo = 7;
    private List<TipoVacina> tipoVacina = new ArrayList<TipoVacina>();
    
    @PostConstruct
    private void init(){
        tipoVacina = new TipoVacinaDao().getTipoVacinaList();
    }
    
    //USUARIO
    public String salvarUsuario() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (!usuarioDao.salvar_cadastro(usuario)){
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "erro ao salvar usuario", ""));
            return "cadastro.xhtml";
        }
        return "index.xhtml";
    }
    
    public String logarUsuario() {
        System.out.println("br.beam.UsuarioBeam.logarUsuario()");
        usuario.setDependentes(new ArrayList<Dependente>());
        Usuario login = usuarioDao.logar_usuario(usuario);
        if(login == null){
            return "index.xhtml";
        }
        dependenteEscolhido = "";
        usuario = login;
        System.out.println("br.beam.UsuarioBeam.logarUsuario():::::::::::::pegar");
        pegarListaDependentes();
        
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<"+tipoVacina.size()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        
        //return "main?faces-redirect=true&id="+login.getId_usuario();
        return "main";
    }
    
    public String entrarNovoUsuario() {
        usuario = new Usuario();
        return "cadastro";
    }
    public String sairNovoUsuario() {
        usuario = new Usuario();
        return "index";
    }
    
    public String logout(){
        usuario = new Usuario();
        dependente = new Dependente();
        vacina = new Vacina();
        loggedId = "0";
        return "index";
    }
    
    public String editarUsuario() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (!usuarioDao.alterar_cadastro(usuario)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "erro ao alterar usuario", ""));
            return "cadastro.xhtml";
        }
        return "main.xhtml";
    }
    public String sairEditUsuario() {
        pegarListaDependentes();
        return "main";
    }
    public String entrarEditUsuario() {
        
        return "editUsuario";
    }
    
    //DEPENDENTE
    public String salvar_dep() {
        dependente.setUsuario(usuario);
        FacesContext context = FacesContext.getCurrentInstance();
        if (!dependenteDao.salvar_dependente(dependente)){
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "erro ao salvar usuario", ""));
            return "newDependente";
        }
        dependente = new Dependente();
        List<Vacina> lvac = dependenteDao.getListaVacina(dependente);
        dependente.setVacinas(lvac);
        dependenteEscolhido = dependente.getNome();
        pegarListaDependentes();
        return "main";
    }
    
    public String sairNovoDependente() {
        dependente = new Dependente();
        return "main";
    }
    
    public String entrarNovoDependente() {
        dependente = new Dependente();
        return "main";
    }
    
    
    public List<String> pegarListaDependentes(){
        System.out.println("br.beam.MainBeam.pegarListaDependentes()");
        if(usuario.getId_usuario() > 0){
            listaDependentes = new ArrayList<String>();
            List<Dependente> ldeps = usuarioDao.getDependentesList(usuario);
            if (ldeps.size() > 0) {
                int dRow = 0;
                usuario.setDependentes(ldeps);
                for (int d = 0; d < ldeps.size(); d++) {
                    Dependente atual = usuario.getDependentes().get(d);
                    if(atual.getNome().equals(dependenteEscolhido))
                        dRow = d;
                    if (dependenteEscolhido == "") {
                        dependenteEscolhido = ldeps.get(d).getNome();
                    } 
                    System.out.println("ADICIONANDO DEPENDENTES E VACINAS: "
                            + "" + atual.getId_dependente() + " - "
                            + "" + atual.getNome() + " - "
                            + "" + atual.getDtNasc() + " - "
                            + " USUARIO:" + atual.getUsuario().getId_usuario() + " - "
                            + "        :" + atual.getUsuario().getNome() + " - "
                            + "        :" + atual.getUsuario().getEmail() + " - "
                    );
                    List<Vacina> lvac = dependenteDao.getListaVacina(atual);
                    atual.setVacinas(lvac);
                    listaDependentes.add(ldeps.get(d).getNome());
                }
                dependente = ldeps.get(dRow);
            }
        }
        return listaDependentes;
    }
    
    public String editarDependente() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (!dependenteDao.alterar_cadastro(dependente)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "erro ao alterar usuario", ""));
            return "editDependente.xhtml";
        }
        pegarListaDependentes();
        return "main.xhtml";
    }
    
    public String sairEditDependente() {
        pegarListaDependentes();
        return "main";
    }
    
    public String entrarEditDependente() {
        
        return "editDependente";
    }
    
    public void changeDependente(){
        List<Dependente> ldeps = usuario.getDependentes();
        for(int d = 0;d < ldeps.size();d++){
            if(ldeps.get(d).getNome().equals(dependenteEscolhido)){
                dependente = ldeps.get(d);
                break;
            }
        }
        
        System.out.println("DEPENDENTE:"+dependente.getNome());
        
        List<Vacina> lvac = dependenteDao.getListaVacina(dependente);
        dependente.setVacinas(lvac);

    }
    
    //VACINA
    public String salvarVacina(){
        
        System.out.println("br.beam.MainBeam.salvarVacina()"+dependente.getNome());
        FacesContext context = FacesContext.getCurrentInstance();
        if (!vacinaDao.salvar(vacina)){
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "erro ao salvar vacina", ""));
            return "addVacina";
        }
        pegarListaDependentes();
        return "main";
    }
    public String editarVacina(){
        
        System.out.println("br.beam.MainBeam.editarVacina()"+dependente.getNome());
        FacesContext context = FacesContext.getCurrentInstance();
        if (!vacinaDao.editar(vacina)){
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "erro ao editar vacina "+vacina.getNome(), ""));
            return "editarVacina";
        }
        pegarListaDependentes();
        return "main";
    }
    public String confirmarVacina(){
        
        System.out.println("br.beam.MainBeam.confirmarVacina()"+dependente.getNome());
        FacesContext context = FacesContext.getCurrentInstance();
        if (!vacinaDao.editar(vacina)){
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "erro ao editar vacina "+vacina.getNome(), ""));
            return "confirmarVacina";
        }
        pegarListaDependentes();
        return "main";
    }
    
    public String removerVacina(){
        
        System.out.println("br.beam.MainBeam.removerVacina()"+dependente.getNome());
        FacesContext context = FacesContext.getCurrentInstance();
        if (!vacinaDao.remover(vacina)){
        System.out.println("br.beam.MainBeam.removerVacina()"+vacina.getNome());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "erro ao editar vacina "+vacina.getNome(), ""));
            return "main";
        }
        pegarListaDependentes();
        return "main";
    }
    
    public void gerarVacinas() {
        System.out.println("br.beam.MainBeam.gerarVacinas()");
        Calendar c = Calendar.getInstance();
        for(int v = 0;v < tipoVacina.size();v++){
            Vacina vacaux = new Vacina();
            vacaux.setNome(tipoVacina.get(v).getNome());
            vacaux.setTipo_vacina(v);
            c.setTime(dependente.getDtNasc());
            c.add(c.DAY_OF_MONTH, +tipoVacina.get(v).getDias());
            Date dt = c.getTime();
            vacaux.setDependente(dependente);
            vacaux.setData_prevista(dt);
            vacinaDao.salvar(vacaux);
        }
        
        pegarListaDependentes();
    }
    
    public String sairNovaVacina() {
        dependenteEscolhido = dependente.getNome();
        pegarListaDependentes();
        vacina = new Vacina();
        return "main";
    }
    
    public String entrarNovaVacina() {
        vacina = new Vacina();
        vacina.setDependente(dependente);
        return "addVacina";
    }
    
    public String entrarEditarVacina(Vacina vac) {
        vacina = vac;
        return "editarVacina";
    }
    
    public String entrarConfirmarVacina(Vacina vac) {
        vacina = vac;
        return "confirmarVacina";
    }
    
    public String entrarRemoverVacina(Vacina vac) {
        vacina = vac;
        return "confirmarRemove";
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public String getLoggedId() {
        return loggedId;
    }

    public void setLoggedId(String loggedId) {
        this.loggedId = loggedId;
    }

    public Dependente getDependente() {
        return dependente;
    }

    public void setDependente(Dependente dependente) {
        this.dependente = dependente;
    }

    public DependenteDao getDependenteDao() {
        return dependenteDao;
    }

    public void setDependenteDao(DependenteDao dependenteDao) {
        this.dependenteDao = dependenteDao;
    }

    public String getDependenteEscolhido() {
        return dependenteEscolhido;
    }

    public void setDependenteEscolhido(String dependenteEscolhido) {
        this.dependenteEscolhido = dependenteEscolhido;
    }

    public List<String> getListaDependentes() {
        return listaDependentes;
    }

    public void setListaDependentes(List<String> listaDependentes) {
        this.listaDependentes = listaDependentes;
    }

    public Vacina getVacina() {
        return vacina;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    public VacinaDao getVacinaDao() {
        return vacinaDao;
    }

    public void setVacinaDao(VacinaDao vacinaDao) {
        this.vacinaDao = vacinaDao;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public List<TipoVacina> getTipoVacina() {
        return tipoVacina;
    }

    public void setTipoVacina(List<TipoVacina> tipoVacina) {
        this.tipoVacina = tipoVacina;
    }

    
}
