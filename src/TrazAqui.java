/**
* Classe TrazAqui corresponde ao modelo da aplicação TrazAqui.
* @author grupo60
* @version 1.0
*/
import java.time.LocalDateTime;
import java.util.*;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.util.stream.Collectors;

public class TrazAqui implements Serializable {

		private LogIn login;
		private Historico historico;
		private Map<String, Utilizador> utilizadores;
		private Map<String, Loja> lojas;
		private Map<String, Voluntario> voluntarios;
		private Map<String, Transportadora> transportadoras;
		private Map<String, Encomenda> encomendas;
		private List<String> aceites;

		/**
		* Construtor por omissão para a classe TrazAqui.
		*/
		public TrazAqui(){

			this.login = new LogIn();
			this.historico = new Historico();
			this.utilizadores = new HashMap<String,Utilizador>();
			this.lojas = new HashMap<String,Loja>();
			this.voluntarios = new HashMap<String,Voluntario>();
			this.transportadoras = new HashMap<String,Transportadora>();
			this.encomendas = new HashMap<String,Encomenda>();
			this.aceites = new ArrayList<>();
		}

		/**
		* Construtor parametrizado para a classe TrazAqui.
		*
		*/
		public TrazAqui(LogIn login, Historico historico, Map<String, Utilizador> utilizadores, Map<String, Loja> lojas,
			Map<String,Voluntario> voluntarios , Map<String,Transportadora> transportadoras, Map<String,Encomenda> encomendas,List<String> aceites){
			
			this.setLogIn(login);
			this.setHistorico(historico);
			this.setUtilizadores(utilizadores);
			this.setLojas(lojas);
			this.setVoluntarios(voluntarios);
 			this.setTransportadoras(transportadoras);
			this.setEncomendas(encomendas);
			this.setAceites(aceites);
			
		}

		/**
		* Construtor por cópia para a classe TrazAqui.
		*/
		public TrazAqui(TrazAqui traz){
			this.login = traz.getLogIn();
			this.utilizadores = traz.getUtilizadores();
			this.lojas = traz.getLojas();
			this.voluntarios = traz.getVoluntarios();
			this.transportadoras = traz.getTransportadoras();
			this.encomendas = traz.getEncomendas();
			this.aceites = traz.getAceites();
			this.historico = traz.getHistorico();
		}

		/**
		* Construtor que recebe uma instância da classe Parse, para a classe TrazAqui.
		* @param parse Uma instância da classe Parse.
		*/
		public TrazAqui (Parse parse){
			
			this.login = new LogIn();
			this.historico = new Historico();
			this.utilizadores = new HashMap<String,Utilizador>();
			this.lojas = new HashMap<String,Loja>();
			this.voluntarios = new HashMap<String,Voluntario>();
			this.transportadoras = new HashMap<String,Transportadora>();
			this.encomendas = new HashMap<String,Encomenda>();
			this.aceites = new ArrayList<>();

			for (Utilizador u : parse.getUtilizadores()){
				this.utilizadores.put(u.getCodUtilizador(),u.clone());
			}

			for (Loja l: parse.getLojas()){
				this.lojas.put(l.getCodLoja(),l.clone());
			}

			for (Voluntario v: parse.getVoluntarios()){
				this.voluntarios.put(v.getCodVoluntario(), v.clone());
			}

			for (Transportadora t: parse.getTransportadoras()){
				this.transportadoras.put(t.getCodEmpresa(), t.clone());
			}

			for (Encomenda e: parse.getEncomendas()){
				this.encomendas.put(e.getCodigoEncomenda(), e.clone());
			}

			for (Servico s: parse.getServicos()){
				this.historico.addServico(s.clone());
			}

			for(String e:parse.getAceites()){
				this.aceites.add(e);
			}
		}

		public LogIn getLogIn(){
			return this.login.clone();
		}

		public Historico getHistorico(){
			return this.historico.clone();
		}

		/**
		* Método Getter da variável utilizadores para a classe TrazAqui.
		* @return um Map com os utilizadores.
		*/
		public Map<String,Utilizador> getUtilizadores(){
			Map<String, Utilizador> clone = new HashMap<String,Utilizador>();

			for (Utilizador u: this.utilizadores.values()){
				clone.put(u.getCodUtilizador(), u.clone());
			}

			return clone;
		}

		/**
		* Método Getter para a variável lojas da classe TrazAqui.
		* @return um Map com as lojas.
		*/
		public Map<String,Loja> getLojas(){
			Map<String, Loja> clone = new HashMap<String,Loja>();

			for (Loja l: this.lojas.values()){
				clone.put(l.getCodLoja(), l.clone());
			}

			return clone;
		}

		/**
		* Método Getter para a varável voluntarios da classe TrazAqui.
		* @return um Map com os voluntarios.
		*/
		public Map<String, Voluntario> getVoluntarios(){
			Map<String,Voluntario> clone = new HashMap<String,Voluntario>();

			for (Voluntario v: this.voluntarios.values()){
				clone.put(v.getCodVoluntario(), v.clone());
			}

			return clone;
		}

		/**
		* Método Getter para a variável transportadoras para a classe TrazAqui.
		* @return um Map com as transportadoras.
		*/
		public Map<String, Transportadora> getTransportadoras(){
			Map<String, Transportadora> clone = new HashMap<String,Transportadora>();

			for (Transportadora t: this.transportadoras.values()){
				clone.put(t.getCodEmpresa(), t.clone());
			}

			return clone;
		}

		/**
		* Método Getter para a variável encomendas para a classe TrazAqui.
		* @return Um map com as encomendas.
		*/
		public Map<String, Encomenda> getEncomendas(){
			Map<String,Encomenda> clone = new HashMap<String, Encomenda>();

			for (Encomenda e: this.encomendas.values()){
				clone.put(e.getCodigoEncomenda(), e.clone());
			}

			return clone;
		}

	public List<String> getAceites() {
		return aceites;
	}

	public void setLogIn(LogIn login){
			this.login = login.clone();
		}

		public void setHistorico(Historico historico){
			this.historico = historico.clone();
		}

		/**
		* Método Setter para a variável utilizadores para a classe TrazAqui.
		* @param utilizadores Um map com os utilizadores.
		*/
		public void setUtilizadores(Map<String,Utilizador> utilizadores){
			for (Utilizador u: utilizadores.values()){
				this.utilizadores.put(u.getCodUtilizador(), u.clone());
			}
		}

		/**
		* Método Setter para a variável lojas para a classe TrazAqui.
		* @param lojas Um map com as lojas.
		*/
		public void setLojas(Map<String, Loja> lojas){
			for (Loja l: lojas.values()){
				this.lojas.put(l.getCodLoja(), l.clone());
			}
		}

		/**
		* Método Setter para a variável voluntarios para a classe TrazAqui.
		* @param voluntarios Um map com os voluntarios.
		*/
		public void setVoluntarios(Map<String,Voluntario> voluntarios){
			for(Voluntario v: voluntarios.values()){
				this.voluntarios.put(v.getCodVoluntario(), v.clone());
			}
		}

		/**
		* Método Setter para a variável transportadoras para a classe TrazAqui.
		* @param transportadoras Um map com as transportadoras.
		*/
		public void setTransportadoras(Map<String,Transportadora> transportadoras){
			for(Transportadora t: transportadoras.values()){
				this.transportadoras.put(t.getCodEmpresa(), t.clone());
			}
		}

		/**
		* Método Setter para a variável encomendas para a classe TrazAqui.
		* @param encomendas Um map com das encomendas.
		*/
		public void setEncomendas(Map<String, Encomenda> encomendas){
			for(Encomenda e: encomendas.values()){
				this.encomendas.put(e.getCodigoEncomenda(),e.clone());
			}
		}

		public void setAceites(List<String> aceites) {
			this.aceites = new ArrayList<>(aceites);
		}

	/**
		* Método toString para a classe TrazAqui.
		* @return String com informação do estado de um objecto TrazAqui.
		*/
		public String toString(){
			StringBuilder sb = new StringBuilder();

			for (Utilizador u: this.utilizadores.values()){
				sb.append(u.toString()).append("\n");
			}

			for(Loja l: this.lojas.values()){
				sb.append(l.toString()).append("\n");
			}

			for(Voluntario v: this.voluntarios.values()){
				sb.append(v.toString()).append("\n");
			}

			for (Transportadora t: this.transportadoras.values()){
				sb.append(t.toString()).append("\n");
			}
			
			for (Encomenda e: this.encomendas.values()){
				sb.append(e.toString()).append("\n");
				}

				sb.append("Aceites:\n");
			for (String s: this.aceites){
				sb.append(s+"\n");

			}

			for (Servico ser: this.historico.getServicos()){
				sb.append("Servicos:\n");
				sb.append(ser+"\n");
			}

			

			return sb.toString();
		}

		public TrazAqui clone(){
			return new TrazAqui(this);
		}

	/**
	* Método que grava o estado de uma instância da classe TrazAqui.
	* @param file O nome do ficheiro a guardar o objecto.
	*/
    public void gravaTrazAqui(String file) throws IOException {

    	
        FileOutputStream f = new FileOutputStream(new File(file));
		ObjectOutputStream o = new ObjectOutputStream(f);
		o.writeObject(this);	
		

		o.close();
		f.close();

	}

		/**
		 * Metodo que devolve uma Loja, sabendo o seu nome
		 */

		public Loja getLoja(String cod){
			Loja l = this.lojas.get(cod);
			if(l!= null) return l;
			return null;
		}

	public void addLogIn (String mail,Logger a){
		this.login.addLogIn(mail, a);
	}

		public void addUtilizador(String cod,Utilizador u){
			this.utilizadores.put(cod,u);
		}

		public void addTransportadora(String cod,Transportadora t){
			this.transportadoras.put(cod,t);
		}

		public void addVoluntario(String cod,Voluntario t){
			this.voluntarios.put(cod,t);
		}

		public void addLoja(String cod,Loja t){
			this.lojas.put(cod,t);
		}

		public void addEncomenda(String cod,Encomenda e){this.encomendas.put(cod,e);}


		public void addAceites(String novo){
			this.aceites.add(novo);
		}


		public void removeServicoHistorico(Servico s){
			this.historico.removeServico(s);
		}

		public void removeEncomenda(String cod){
			this.encomendas.keySet().remove(cod);
		}

		public void removeAceites(String cod){
			this.aceites.remove(cod);
		}

		public void addServico(Servico s){
			this.historico.addServico(s);
		}


	/**
	* Método que determina as 10 empresas com mais kms percorridos.
	* @return Uma Lista com os códigos das 10 empresas com mais kms percorridos.
	*/
	public List<AuxiliarEmpresaKms> empresasMaisKms() throws IndexOutOfBoundsException{

		// filtrar servicos feitos por empresas transportadoras.
		List<Servico> lista = 
			this.historico.getServicos().stream().filter(s->s.getCodTranportador().charAt(0) == 't').collect(Collectors.toList());

		List<String> temp = new ArrayList<String>();

		List<AuxiliarEmpresaKms> aux = new ArrayList<AuxiliarEmpresaKms>();

		for (Servico s: lista){

			if (!temp.contains(s.getCodTranportador())) {
			double kms = 
				lista.stream().filter(x->x.getCodTranportador().equals(s.getCodTranportador()) && x.isConcluido()).map(Servico :: getKmPercorridos).mapToDouble(num-> (double) num).sum();

				aux.add(new AuxiliarEmpresaKms(s.getCodTranportador(), kms));
				temp.add(s.getCodTranportador());
		}
	}


		Collections.sort(aux, new AuxiliarEmpresaKmsComparator());


		int s = aux.size();

		if (s< 10) {
			return aux.subList(0,s);
		}		

		return aux.subList(0,10);
	}

	/**
	* Determinar o código dos 10 utilizadores que mais utilizam o sistema no que toca ao número de encomendas transportadas.
	* @return Lista de AuxiliarUtilizadorEncomendas.
	*/
	public List<AuxiliarUtilizadorEncomendas> utilizadoresMaisEncomendas()  throws IndexOutOfBoundsException{
		List<Servico> lista = this.getHistorico().getServicos();

		List<String> temp = new ArrayList<String>();
		List<AuxiliarUtilizadorEncomendas> aux = new ArrayList<AuxiliarUtilizadorEncomendas>();

		for (Servico s: lista) {
			if (!temp.contains(s.getCodUtilizador())) {
				double numEncomendas = lista.stream().filter(x->x.getCodUtilizador().equals(s.getCodUtilizador()) && x.isConcluido()).count();//mapToDouble(num ->(double) num).sum();
			aux.add(new AuxiliarUtilizadorEncomendas(s.getCodUtilizador(), numEncomendas));
			temp.add(s.getCodUtilizador());
		}
	}

	Collections.sort(aux, new AuxiliarUtilizadorEncomendasComparator());

	int s = aux.size();

		if (s< 10) {
			return aux.subList(0,s);
		}		

		return aux.subList(0,10);
	}

	public List<Encomenda> encomendasDisponiveisVoluntario(Voluntario v) {
		List<Encomenda> lista = new ArrayList<Encomenda>();
		Utilizador u = new Utilizador();
		Loja l = new Loja();

		for (Encomenda e: this.encomendas.values()) {
			String codU = e.getCodigoUtilizador();
			String codL = e.getCodigoLoja();

			try {
				u = this.utilizadores.get(codU);
				l = this.lojas.get(codL);
			}
			catch(Exception ex) {
				System.out.println ("Utilizador ou loja não existentes.");
			}

			if ((v.getGps().distancia(u.getGps()) <= v.getRaio()) && (v.getGps().distancia(l.getGps()) <= v.getRaio())) {
				lista.add(e.clone());
			}
		}

		return lista;
	}

	public List<Encomenda> encomendasDisponiveisTransportadora(Transportadora t) {
		List<Encomenda> lista = new ArrayList<Encomenda>();
		Utilizador u = new Utilizador();
		Loja l = new Loja();

		for (Encomenda e: this.encomendas.values()) {
			String codU = e.getCodigoUtilizador();
			String codL = e.getCodigoLoja();

			try {
				u = this.utilizadores.get(codU);
				l = this.lojas.get(codL);
			}
			catch(Exception ex) {
				System.out.println ("Utilizador ou loja não existentes.");
			}

			if ((t.getGps().distancia(u.getGps()) <= t.getRaio()) && (t.getGps().distancia(l.getGps()) <= t.getRaio())) {
				lista.add(e.clone());
			}
		}

		return lista;
	}


	public void changeClassificacao(Servico s, int classificacao){
		int indice = this.getHistorico().getIndiceServico(s);
		this.historico.setClassServico(classificacao,indice);
	}

}

