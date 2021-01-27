import java.time.LocalDateTime;
import java.util.*;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ProgramController {
    private boolean isExistente;
    private static Menus menus;
    private static String file_path = "../trazaqui10.dat";
    private static TrazAqui db;

    public static String getFile_path() {
        return file_path;
    }


    public static  TrazAqui getDb(){return db.clone();}

    public ProgramController() {
        this.isExistente = false;
    }

    public boolean isExistente() {
        return isExistente;
    }

    public void setExistente(boolean existente) {
        isExistente = existente;
    }


    public static void main(String[] args) {


        menus = new Menus();
        ProgramController aux = new ProgramController();
        db = null;
        Scanner ler = new Scanner(System.in).useDelimiter("\n");
            do {
                int op = menus.mostraOpcoes("Deseja",
                        new String[]{"Carregar ficheiros default",
                                "Carregar outro ficheiro"});

                if (op == 1) {
                    db = initApp(file_path);
                    //System.out.println(db);
                }

                else if (op == 2) {
                    int opAux = 0;
                    do {
                        System.out.println("Tipo de ficheiro\n1).dat      2).txt       3)Voltar");
                        opAux = ler.nextInt();
                        if(opAux == 1) {
                            System.out.println("Escreva path do ficheiro");
                            String path = ler.next();
                            try {
                                db = initApp(path);
                                opAux=3;
                            }
                            catch(NullPointerException ex) {
                                System.out.println("Erro");
                                opAux=0;
                            }
                            
                        }
                        else if(opAux == 2){
                            System.out.println("Escreva path do ficheiro");
                            String path = ler.next();
                            Parse parse = new Parse(path);
                            parse.parse();
                            
                            try {db = new TrazAqui(parse);
                                opAux=3;}
                            catch(NullPointerException ex) {
                                System.out.println("Erro");
                                opAux=0;
                            }
                            /**
                             * Importante: carregar ficheiro tipo txt aqui
                             */

                        }
                    } while (opAux != 3);

                } else System.exit(0);
            }while (db == null);



      

        while(true) {
            aux.setExistente(false);
            Logger ator = menus.menuLogin();
            //if (aux.isExistente) {
                if (ator instanceof LogUtilizador) {       //Vai para o menu de cada tipo de classe
                    menus.menuUtilizador(ator);
                } else if (ator instanceof LogVoluntario) {
                    menus.menuVoluntario(ator);
                }else if (ator instanceof LogEmpresa) {
                    menus.menuTransportadora(ator);
                }else if (ator instanceof LogLoja) {
                    menus.menuLoja(ator);
                }
            //}
        }

    }



        /**
        * Método que inicia o modelo da aplicação TrazAqui.
        */
        private static TrazAqui initApp(String fp) throws NullPointerException{

            TrazAqui traz = new TrazAqui();

        try{FileInputStream fi = new FileInputStream(new File(fp));
            ObjectInputStream oi = new ObjectInputStream(fi);

            traz = (TrazAqui) oi.readObject();
        

            oi.close();
            fi.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return  null;
        } catch (IOException e) {
            System.out.println("Error initializing stream");
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return traz;
    }


    //-------------------------------------------------- Lidar com o MenuLogin------------------------

    public boolean validaLogIn(String mail, String pass){
        return getDb().getLogIn().verificaLogin(mail, pass);
    }

    public Logger returnLogger(String mail){
        return  getDb().getLogIn().getLogger(mail);
    }

    public void addLogIn(Logger a1){
        db.addLogIn(a1.getEmail(),a1);
    }

    public void addUtilizador(Utilizador u){
        db.addUtilizador(u.getCodUtilizador(),u);
    }

    public void addTransportadora(Transportadora t){
        db.addTransportadora(t.getCodEmpresa(),t);
    }

    public void addVoluntario(Voluntario v){
        db.addVoluntario(v.getCodVoluntario(),v);
    }

    public void addLoja(Loja l){
        db.addLoja(l.getCodLoja(),l);
    }


    public LogUtilizador addUtilizador(){
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        String email="n/a", nome="n/a",codUtilizador="n/a",pass="n/a";
        int n=0,x=0,y=0;
        while(n==0) {
            System.out.print("E-mail:");
            email = Input.lerString();
            if (!pattern.matcher(email).matches()) {
                System.out.println("Email nao valido");
                n = 0;
            } else if (getDb().getLogIn().logContainsMail(email.toLowerCase())) {
                System.out.println("Já existe conta com esse e-mail");
            } else n++;
        }

        while(n== 1) {
            System.out.print("Nome:");
            nome = Input.lerString();
            n++;
        }

        while(n==2) {
            System.out.print("CodUtilizador(numero a sua escolha):");
            codUtilizador = ("u" + Input.lerString());
            if (getDb().getUtilizadores().containsKey(codUtilizador)) {
                System.out.println("Já existe utilizador com esse codigo");
            }
            else n++;
        }

        while (n==3) {
            System.out.print("Password:");
            pass = Input.lerString();
            n++;
        }

        while (n==4) {
            System.out.print("Gps(x y):\n");
            x = Input.lerInt();
            y = Input.lerInt();
            n++;
        }

        while(n==5) {
            break;
        }
        Utilizador utilizador = new Utilizador(codUtilizador,nome,new GPS(x,y));
        return new LogUtilizador(email,pass,utilizador);
    }


    public LogEmpresa addTransportadora(){
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);

        try {
            System.out.print("E-mail:");
            String email = Input.lerString();
            if(!pattern.matcher(email).matches())
                throw new InputMismatchException("Email não é válido");
            if(getDb().getLogIn().logContainsMail(email.toLowerCase()))
                throw new ExistingAtorException("Já existe conta com esse e-mail");
            System.out.print("Nome Empresa:");
            String nomeEmpresa = Input.lerString();
            System.out.print("CodEmpresa(numero a sua escolha):");
            String codEmpresa = ("t"+Input.lerString());
            if(getDb().getTransportadoras().containsKey(codEmpresa)){
                throw new ExistingAtorException("Já existe empresa com esse codigo");
            }
            System.out.print("Password:");
            String pass = Input.lerString();
            System.out.print("Gps(x y):\n");
            int x = Input.lerInt();
            int y = Input.lerInt();
            System.out.print("Nif:");
            String nif = Input.lerString();
            System.out.print("Raio de alcance:");
            double raio = Input.lerDouble();
            System.out.print("Preco por Km:");
            double preco = Input.lerDouble();


            Transportadora empresa = new Transportadora(codEmpresa,nomeEmpresa,new GPS(x,y),nif,raio,preco);
            return new LogEmpresa(email,pass,empresa,false);
        } catch (InputMismatchException | ExistingAtorException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public LogVoluntario addVoluntario(){
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        try {
            System.out.print("E-mail:");
            String email = Input.lerString();
            if(!pattern.matcher(email).matches())
                throw new InputMismatchException("Email não é válido");
            if(getDb().getLogIn().logContainsMail(email.toLowerCase()))
                throw new ExistingAtorException("Já existe conta com esse e-mail");
            System.out.print("Nome:");
            String nome = Input.lerString();
            System.out.print("CodVoluntario(numero a sua escolha):");
            String cod = ("v"+Input.lerString());
            if(getDb().getVoluntarios().containsKey(cod)){
                throw new ExistingAtorException("Já existe voluntario com esse codigo");
            }
            System.out.print("Password:");
            String pass = Input.lerString();
            System.out.print("Gps(x y):\n");
            int x = Input.lerInt();
            int y = Input.lerInt();
            System.out.print("Raio de alcance:");
            double raio = Input.lerDouble();

            Voluntario voluntario = new Voluntario(cod,nome,new GPS(x,y),raio);
            return new LogVoluntario(email,pass,false,voluntario);
        } catch (InputMismatchException | ExistingAtorException e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    public LogLoja addLoja(){
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        try {
            System.out.print("E-mail:");
            String email = Input.lerString();
            if(!pattern.matcher(email).matches())
                throw new InputMismatchException("Email não é válido");
            if(getDb().getLogIn().logContainsMail(email.toLowerCase()))
                throw new ExistingAtorException("Já existe conta com esse e-mail");
            System.out.print("Nome:");
            String nome = Input.lerString();
            System.out.print("CodLoja(numero a sua escolha):");
            String cod = ("l"+Input.lerString());
            if(getDb().getLojas().containsKey(cod)){
                throw new ExistingAtorException("Já existe loja com esse codigo");
            }
            System.out.print("Password:");
            String pass = Input.lerString();
            System.out.print("Gps(x y):\n");
            int x = Input.lerInt();
            int y = Input.lerInt();

            Loja loja = new Loja(cod,nome,new GPS(x,y));
            return new LogLoja(email,pass,loja);
        } catch (InputMismatchException | ExistingAtorException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<AuxiliarUtilizadorEncomendas> utilizadoresMaisEncomendas(){
            return getDb().utilizadoresMaisEncomendas();
    }

    public List<AuxiliarEmpresaKms> empresasMaisKms(){
            return getDb().empresasMaisKms();
    }

    public void automaticAccountCreation(){
            TrazAqui db = getDb();
            Map<String,Utilizador> listaUtilizadores = db.getUtilizadores();
            Map<String,Loja> listaLojas = db.getLojas();
            Map<String,Voluntario> listaVoluntarios = db.getVoluntarios();
            Map<String,Transportadora> listaTransportadoras = db.getTransportadoras();
            for(Utilizador e:listaUtilizadores.values()){
                LogUtilizador logUtilizador = new LogUtilizador((e.getCodUtilizador()+"@gmail.com"),e.getCodUtilizador(),e);
                if (!db.getLogIn().getLogs().containsValue(logUtilizador)) db.addLogIn(logUtilizador.getEmail(),logUtilizador);
            }
            System.out.println("Utilizadores feito!");
            for(Loja e:listaLojas.values()){
                LogLoja logLoja = new LogLoja((e.getCodLoja()+"@gmail.com"),e.getCodLoja(),e);
                if (!db.getLogIn().getLogs().containsValue(logLoja)) db.addLogIn(logLoja.getEmail(),logLoja);
            }
            System.out.println("Lojas feito!");
            for(Voluntario e:listaVoluntarios.values()){
                LogVoluntario logVoluntario = new LogVoluntario((e.getCodVoluntario()+"@gmail.com"),e.getCodVoluntario(),false,e);
                if (!db.getLogIn().getLogs().containsValue(logVoluntario)) db.addLogIn(logVoluntario.getEmail(),logVoluntario);
            }
            System.out.println("Voluntarios feito!");
            for(Transportadora e:listaTransportadoras.values()){
                LogEmpresa logEmpresa = new LogEmpresa((e.getCodEmpresa()+"@gmail.com"),e.getCodEmpresa(),e,false);
                if (!db.getLogIn().getLogs().containsValue(logEmpresa)) db.addLogIn(logEmpresa.getEmail(),logEmpresa);
            }
            System.out.println("feito!");
            this.db = new TrazAqui(db);
        }

        public void gravaTrazAqui() throws IOException {
            db.gravaTrazAqui(getFile_path());
        }



    //-------------------------------------------------- Lidar com o MenuUtilizador------------------------

    public Encomenda addEncomenda( Logger a1) {
        LogUtilizador utilizador = (LogUtilizador) a1.clone();
        List<LinhaEncomenda> linhas = new ArrayList<>();
        String codiU = utilizador.getDadosUtilizador().getCodUtilizador();
        int n = 0;
        String codigoE = null;
        String codL = null;
        double peso = 0;

        while(n== 0) {
            System.out.println("Codigo encomenda(numero a sua escolha):");
            codigoE = ("e" + Input.lerString());
            if (getDb().getEncomendas().get(codigoE) != null) {
                System.out.println("Ja existe encomenda com esse codigo.");
            } else n++;
        }
        while(n==1) {
            System.out.println("Codigo da Loja:");
            String nomeL = Input.lerString();
            Loja lojaAux = getDb().getLoja(nomeL);
            if (lojaAux == null) {
                System.out.println("Loja nao encontrada");
            } else {
                codL = lojaAux.getCodLoja();
                n++;
            }
        }
        while(n== 2) {
            System.out.println("Peso:");
            peso = Input.lerDouble();
            n++;
        }
        while(n== 3) {
            int l = 1;
            System.out.println("Produto:");
            while (l == 1) {
                linhas.add(addLinhaEncomenda());
                System.out.println("Adicionar novo Produto?\n1(sim) 2(nao)");
                l = Input.lerInt();
            }
            n++;
        }

        return new Encomenda(codigoE, codiU, codL, peso, (ArrayList<LinhaEncomenda>) linhas);
    }

    public LinhaEncomenda addLinhaEncomenda(){

        System.out.println("Codigo produto(numero a sua escolha):");
        String codigoP=("p"+Input.lerString());

        System.out.println("Nome:");
        String descricao = Input.lerString();

        System.out.println("Quantidade:");
        double quantidade = Input.lerDouble();

        System.out.println("Preco:");
        double preco = Input.lerDouble();

        LinhaEncomenda linha = new LinhaEncomenda(codigoP,descricao,quantidade,preco);
        return linha;
    }

    public void addEncomendaToDb(Encomenda encomenda){
        db.addEncomenda(encomenda.getCodigoEncomenda(), encomenda);
    }

    public List<Servico> getPropostas(LogUtilizador utilizador){
        return getDb().getHistorico().getServicos().stream().filter(e->e.getCodUtilizador().equals(utilizador.getDadosUtilizador().getCodUtilizador()) && !e.isConcluido()).collect(Collectors.toList());
    }



    public List<Servico> getHistoricoTransportador(String cod){
        return getDb().getHistorico().getServicos().stream().filter(e->e.isConcluido() && e.getCodTranportador().equals(cod)).collect(Collectors.toList());
    }

    public List<Servico> getServicoSClassificacao(String cod){
        return getDb().getHistorico().getServicosSClassificacao(cod);
    }

    public void changeClassificacao(Servico s,int x){
            db.changeClassificacao(s,x);
    }

    public Transportadora getTransByCode(String cod){
        return getDb().getTransportadoras().get(cod);
    }

    public void removeServicoHistorico(Servico s){
        db.removeServicoHistorico(s);
    }

    public void addServicoHistorico(Servico s){
        db.addServico(s);
    }

    public void removeEncomenda(String cod){
        db.removeEncomenda(cod);
    }

    public void removeAceites(String cod){
        db.removeAceites(cod);
    }

    public List<Servico> getServicosEntreDatasTrans(LocalDateTime finalData1,LocalDateTime finalData2,String trans){
            return getDb().getHistorico().getServicos().stream().filter(Servico::isConcluido).filter(e->e.isEntreDatas(finalData1, finalData2) && e.getCodTranportador().equals(trans)).collect(Collectors.toList());
    }

    public List<Servico> getServicosEntreDatas(LocalDateTime finalData1,LocalDateTime finalData2){
        return getDb().getHistorico().getServicos().stream().filter(Servico::isConcluido).filter(e->e.isEntreDatas(finalData1, finalData2)&& e.isConcluido()).collect(Collectors.toList());
    }




//-------------------------------------------------- Lidar com o MenuVoluntario------------------------

    public List<Encomenda> encomendasDisponiveisVoluntario(Voluntario v){
            return getDb().encomendasDisponiveisVoluntario(v);
    }

    public double getDistanciaTLojaCliente(Encomenda e){
        return getDb().getLojas().get(e.getCodigoLoja()).getGps().distancia(getDb().getUtilizadores().get(e.getCodigoUtilizador()).getGps());
    }

    public double getDistanciaTLojaVoluntario(Encomenda e, Voluntario v){
        return v.getGps().distancia(getDb().getLojas().get(e.getCodigoLoja()).getGps());
    }


    //-------------------------------------------------- Lidar com o MenuTransportadora------------------------

    public List<Encomenda> encomendasDisponiveisTransportadora(Transportadora t){
            return  getDb().encomendasDisponiveisTransportadora(t);
    }

    public double getDistanciaTLojaTransportadora(Encomenda e, Transportadora t){
        return t.getGps().distancia(getDb().getLojas().get(e.getCodigoLoja()).getGps());
    }

    public double totalFaturado(String cod){
        System.out.println("Escolha as datas:");
        LocalDateTime data1=null,data2=null;
        double res = 0;
        int help =0;
        while(help==0) {
            System.out.println("Escolhe quais as datas que pretende ver a informacao:\nData inicial: ");
            data1 = Input.scanDate();
            System.out.println("Data final: ");
            data2 = Input.scanDate();
            if(data2.isBefore(data1)){
                System.out.println("Datas Invalidas");
            }else help++;
        }
        LocalDateTime finalData1 = data1;
        LocalDateTime finalData2 = data2;
        List<Servico> lista = getDb().getHistorico().getServicos().stream().filter(e->e.getCodTranportador().equals(cod)).collect(Collectors.toList());
        if(lista.size()>0) res = lista.stream().filter(e-> e.isEntreDatas(finalData1,finalData2) && e.isConcluido()).mapToDouble(e->e.getPreco()).sum();
        return res;
    }

    //-------------------------------------------------- Lidar com o MenuLoja------------------------

    public List<Encomenda> encomendasPLoja(String codLoja){
        return getDb().getEncomendas().values().stream().filter(e->e.getCodigoLoja().equals(codLoja) && !(getDb().getAceites().contains(e.getCodigoEncomenda()))).collect(Collectors.toList());
    }

    public void addAceites(String novo){
             db.addAceites(novo);
    }



}