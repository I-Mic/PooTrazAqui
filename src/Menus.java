import java.text.DecimalFormat;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.*;
import java.io.IOException;

public class Menus {

    private ProgramController aux = new ProgramController();



    public Logger menuLogin()
    {

        Logger a1 = new Logger();
        int op;
        try {
            do {
                op = mostraOpcoes("Menu Principal",
                        new String[] {"Login",
                                "Registar","Show db","Top 10 Utilizadores","Top 10 Empresas","Criar contas automaticamente "});
                switch (op) {
                    case 1: {       //LOGIN!!!!!
                        System.out.print("E-mail:");
                        String user = Input.lerString().toLowerCase();
                        System.out.print("Password:");
                        String pass = Input.lerString();
                        if (aux.validaLogIn(user,pass)) {
                            System.out.println("Login valido");
                            aux.setExistente(true);
                            a1 = aux.returnLogger(user);
                            return a1;
                        } else {
                            System.out.println("Login invalido");
                            aux.setExistente(false);
                        }

                    }
                    break;
                    case 2: {       //REGISTAR
                        op = mostraOpcoes("Menu Registar",
                                new String[] {"Utilizador",
                                        "Transportadora",
                                        "Voluntario",
                                        "Loja"});
                        /**
                         * Criar conta.
                         */
                        //Utilizador
                        if (op == 1) {
                            LogUtilizador u = aux.addUtilizador();
                             a1 = new LogUtilizador(u);
                            Utilizador u1 = new Utilizador(((LogUtilizador) a1).getDadosUtilizador());
                            if (a1!=null){
                                aux.addLogIn(a1);
                            }
                            if(u1!=null){
                                aux.addUtilizador(u1);
                            }
                            //Transportadora
                        } else if (op == 2) {
                            a1 = new LogEmpresa();
                            a1 = aux.addTransportadora();
                            if (a1!=null) {
                                aux.addLogIn(a1);
                                Transportadora t1 = new Transportadora(((LogEmpresa) a1).getDadosEmpresa());
                                if (t1 != null) {
                                    aux.addTransportadora(t1);
                                }
                            }
                            //Voluntario
                        } else if (op == 3) {
                            a1 = new LogVoluntario();
                            a1= aux.addVoluntario();
                            if (a1!=null) {
                                aux.addLogIn(a1);
                                Voluntario t1 = new Voluntario(((LogVoluntario) a1).getDadosVoluntario());
                                if (t1 != null) {
                                    aux.addVoluntario(t1);
                                }
                            }
                            //Loja
                        }else if (op == 4) {
                            a1 = new LogLoja();
                            a1 = aux.addLoja();
                            if (a1!=null) {
                                aux.addLogIn(a1);
                                Loja l1 = new Loja(((LogLoja) a1).getDadosLoja());
                                if (l1 != null) {
                                    aux.addLoja(l1);
                                }
                            }
                        }
                    }break;
                    case 3: {

                        System.out.println(aux.getDb());
                        break;
                    }

                    case 4:{
                        System.out.println("Utilizadores com mais Encomendas feitas:\n(Codigo Utilizadro,Encomendas feitas)\n"+aux.utilizadoresMaisEncomendas());
                        clickEnter();
                        break;
                    }

                    case 5:{
                        System.out.println("Empresas com mais Kms feitos:\n(Codigo Empresa,Kms feitos)\n"+aux.empresasMaisKms());
                        clickEnter();
                        break;
                    }

                    case 6:{
                        System.out.println("Alto, isto é uma funcao restrita, precisa de password:");
                        String pass = Input.lerString();
                        if(pass.equals("devkit123")){
                            System.out.println("A criar contas para todos o dados guardados:");
                            aux.automaticAccountCreation();
                        }
                        else System.out.println("Pass errada");
                        break;
                    }

                    
                    case 0: {
                        System.out.println("A sair da App");
                        op = mostraOpcoes("Deseja gravar estado da App?",
                                new String[] {"Sim",
                                        "Nao"});
                        if(op==1) {
                            System.out.println("Obrigado pela preferência.");
                            /**db.gravaFicheiro(); //  GRAVA O ESTADO DA APP */
                            try {
                                aux.gravaTrazAqui();
                            } catch (IOException e) {
                                System.out.println("Erro ao guardar o ficheiro.\n");
                            }
                            System.exit(0);
                        }
                        else if (op==2){
                            System.exit(0);
                        }
                    
                    }

                }

            } while (op != 0);
        }catch (InputMismatchException | DateTimeException  e){
            System.out.println(e.getMessage());
        }
        return a1;
    }


    public void menuUtilizador(Logger a1) {
        LogUtilizador utilizador = (LogUtilizador) a1.clone();
        int op;
        DecimalFormat fmt = new DecimalFormat("0.00");
        try{
            do {
                op = mostraOpcoes("Menu Cliente",
                        new String[] {"Solicitar Encomenda",
                                "Ver propostas",
                                "Lista de entregas por tempo",
                                "Lista de entregas por Voluntario ou transportadora",
                                "Classificar Servicos",
                                "Historico de Transportador entre Datas"});
                switch (op) {
                    case 1: {
                        int op1 = mostraOpcoes("Escolha Tipo:",
                                new String[]{ "Encomenda", 
                                        "Encomenda Medica"});


                        if(op1==1 || op1 ==2) {
                            Encomenda encomenda = aux.addEncomenda(a1);
                            if(encomenda!=null) {
                                aux.addEncomendaToDb(encomenda);
                                System.out.println("Encomenda feita");
                            }else System.out.println("Ocorreu um erro.");
                        }
                        break;

                    }
                    case 2: {
                        List<Servico> propostas = aux.getPropostas(utilizador);
                        if (propostas.size()>0) menuAceitacaoProposta(propostas);
                        else System.out.println("Nao ha propostas");
                        break;
                    }
                    case 3: {
                        menuHistoricoSEntidade();

                        break;
                    }
                    case 4: {
                        System.out.println("Codigo do transportador(empresa ou voluntario):");
                        String codV = Input.lerString();
                        List<Servico> servicos = aux.getHistoricoTransportador(codV);
                        if(servicos.size()>0){
                            System.out.println(servicos);
                        }else System.out.println("Ainda nao ha historico deste transportador");
                        break;
                    }
                    case 5: {
                        List<Servico> servicosSC = aux.getServicoSClassificacao(utilizador.getDadosUtilizador().getCodUtilizador());
                        if(servicosSC.size() == 0) System.out.println("Nao ha servicos por classificar");
                        else{
                            for(int i=0;i<servicosSC.size();i++){
                                int aux5=0;
                                    System.out.println(servicosSC.get(i));
                                    System.out.println("1)Classificar Servico               2)Proximo Servico                    3)Sair");
                                    aux5 = Input.lerInt();
                                    if (aux5 == 1) {
                                        System.out.println("Classificacao:");
                                        int calssificacao = Input.lerInt();
                                        if(calssificacao < 0 || calssificacao>10) System.out.println("Classificacao inválida");
                                        else {
                                            aux.changeClassificacao(servicosSC.get(i), calssificacao);
                                            break;
                                        }
                                    }else if(aux5==2) break;
                                    else if(aux5==3) break;break;

                            }
                        }
                        break;
                    }
                    case 6 :{
                        menuHistorico();
                        clickEnter();
                    }break;
                }
            } while (op != 0);
        }catch (InputMismatchException | DateTimeException  e){
            if(e instanceof InputMismatchException)
                System.out.println("Input inválido");
            else
                System.out.println(e.getMessage());
            menuUtilizador(a1);
        }
    }


    public void menuVoluntario(Logger a1) {
        LogVoluntario voluntario = (LogVoluntario) a1;
        int op;
        try {
            do {
                op = mostraOpcoes("Menu Voluntario",
                        new String[]{"Mudar Disponibilidade",
                                "Fazer Entrega"});
                switch (op) {
                    case 1: {
                        System.out.println("Disponibilidade:" + voluntario.isDisponibilidade() + " Deseja mudar?\nSim(s)  Nao(n) ");
                        String r = Input.lerString();
                        if (r .equals("s")) {
                            voluntario.changeDisponibilidade();
                            System.out.println("Disponibilidade mudada.");
                        } else if (r .equals("n")) System.out.println("Disponibilidade mantida");
                        else throw new InputMismatchException("Input Invalido");
                        break;
                    }
                    case 2:{
                        List<Encomenda> lista = aux.encomendasDisponiveisVoluntario(voluntario.getDadosVoluntario());
                        menuServicos(lista,voluntario.getDadosVoluntario());
                        break;
                    }

                }
            }while(op!=0);
        }catch (InputMismatchException |  DateTimeException  e){
            if(e instanceof InputMismatchException)
                System.out.println("Input inválido");
            else
                System.out.println(e.getMessage());
            menuVoluntario(a1);
        }
    }

    public void menuTransportadora(Logger a1) {
        LogEmpresa empresa = (LogEmpresa) a1;
        int op;
        try {
            do {
                op = mostraOpcoes("Menu Transportadora",
                        new String[]{"Mudar Disponibilidade",
                                "Ver total Faturado em determinado tempo",
                                "Fazer Entrega"});
                switch (op) {
                    case 1: {
                        System.out.println("Disponibilidade:" + empresa.isDisponibilidade() + " Deseja mudar?\nSim(1)  Nao(2) ");
                        int r = Input.lerInt();
                        if (r == 1) {
                            empresa.changeDisponibilidade();
                            System.out.println("Disponibilidade mudada.");
                        } else if (r == 2) System.out.println("Disponibilidade mantida");
                        else throw new InputMismatchException("Input Invalido");
                        break;
                    }
                    case 2: {
                        double faturado = aux.totalFaturado(empresa.getDadosEmpresa().getCodEmpresa());
                        System.out.println("Total faturado = " + faturado);
                        break;
                    }

                    case 3:{
                        List<Encomenda> lista = aux.encomendasDisponiveisTransportadora(((LogEmpresa) a1).getDadosEmpresa());
                        menuPropostas(lista,empresa.getDadosEmpresa());
                        break;
                    }

                }
            }while(op!=0);
        }catch (InputMismatchException |  DateTimeException  e){
            if(e instanceof InputMismatchException)
                System.out.println("Input inválido");
            else
                System.out.println(e.getMessage());
            menuTransportadora(a1);
        }
    }



    public void menuLoja(Logger a1) {
        LogLoja loja = (LogLoja) a1;
        int op;
        try {
            do {
                op = mostraOpcoes("Menu Loja",
                        new String[]{"Lista de Encomendas Pedidas"});
                switch (op) {
                    case 1: {
                        List<Encomenda> lista1 = aux.encomendasPLoja(loja.getDadosLoja().getCodLoja());
                        menuAceitacaoLoja(lista1);
                        break;
                    }


                }
            }while(op!=0);
        }catch (InputMismatchException |  DateTimeException  e){
            if(e instanceof InputMismatchException)
                System.out.println("Input inválido");
            else
                System.out.println(e.getMessage());
            menuVoluntario(a1);
        }
    }



    public void menuAceitacaoLoja(List<Encomenda> lista){
        if (lista.size()== 0) System.out.println("Nao ha encomendas pendentes na sua loja");
        else{
            for(int i=0;i<lista.size();){
                System.out.println(lista.get(i));
                System.out.println("1)Aceitar Encomenda     2)Proxima Encomenda        3)Sair");
                int aux = Input.lerInt();
                if(aux==1) {
                    this.aux.addAceites(lista.get(i).getCodigoEncomenda());
                    i++;
                }else if(aux==2)i++;
                else break;
            }
        }
    }

    public void menuHistorico(){
        System.out.println("Codigo de entidade: ");
        String codTransportador = Input.lerString();
        LocalDateTime data1=null, data2=null;
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
        List<Servico> lista = aux.getServicosEntreDatasTrans(finalData1,finalData2,codTransportador);
        if(lista.size()==0) System.out.println("Nao ha servicos desta entidade nestas datas.");
        else System.out.println(lista);
    }

    public void menuHistoricoSEntidade(){
        LocalDateTime data1=null, data2=null;
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
        List<Servico> lista = aux.getServicosEntreDatas(finalData1,finalData2);
        if(lista.size()==0) System.out.println("Nao ha servicos nestas datas.");
        else System.out.println(lista);
    }


    public void menuServicos(List<Encomenda> list, Voluntario voluntario){
        for(Encomenda e:list){
            int opcao= 0;
            System.out.println("Lista de encomendas disponiveis");
            System.out.println(e);
            double km = aux.getDistanciaTLojaCliente(e);
            double kmT = km + aux.getDistanciaTLojaVoluntario(e,voluntario);
            System.out.println("Distancia total percorrida em Km: " + kmT);
            System.out.println("Fazer Encomenda?\n1)Sim       2)Nao        3)Sair");
            opcao = Input.lerInt();
            if(opcao==1){
                System.out.println("Codigo servico(numero a sua escolha):");
                String codS = ("s" +Input.lerString());
                System.out.println("Data de Inicio de Entrega:");
                LocalDateTime data = Input.scanDate();
                System.out.println("Tempo demorado(dias):");
                int dias = Input.lerInt();
                Servico servico = new Servico(codS,true,e,data,e.getCodigoUtilizador(),voluntario.getCodVoluntario(),kmT,0,dias);
                aux.addServicoHistorico(servico);
                aux.removeEncomenda(e.getCodigoEncomenda());
                aux.removeAceites(e.getCodigoEncomenda());
                break;
            }
            else if(opcao==3){
                break;
            }
        }
    }



    public void menuPropostas(List<Encomenda> list,Transportadora transportadora){
        for(Encomenda e:list){
            int opcao= 0;
            System.out.println("Lista de encomendas disponiveis");
            System.out.println(e);
            double km = aux.getDistanciaTLojaCliente(e);
            double kmT = km + aux.getDistanciaTLojaTransportadora(e,transportadora);
            System.out.println("Distancia total percorrida em Km: " + kmT);
            System.out.println("Fazer Proposta?\n1)Sim       2)Nao        3)Sair");
            opcao = Input.lerInt();
            if(opcao==1){
                Servico servico = addServico(transportadora.getCodEmpresa(),e,kmT);
                aux.addServicoHistorico(servico);
            }
            else if(opcao==3){
                break;
            }
        }
    }

    public Servico addServico(String codTransportador,Encomenda encomenda,double km) {

        int dias =0;
        String codigoS = null;
        double preco = 0;

        System.out.println("Codigo Servico(numero a sua escolha):");
        codigoS = ("s" + Input.lerString());

        System.out.println("Preco:");
        preco = Input.lerDouble();


        System.out.println("Dias estimados:");
        dias = Input.lerInt();



        return new Servico(codigoS, false, encomenda, null, encomenda.getCodigoUtilizador(),codTransportador,km,preco,dias);
    }


    public void menuAceitacaoProposta(List<Servico> l){

        for (Servico s: l){
            Transportadora transportadora = aux.getTransByCode(s.getCodTranportador());
            System.out.println(s.getEncomenda());
            System.out.println(transportadora.getCodEmpresa() + "," + transportadora.getNomeEmpresa());
            System.out.println("Tempo de espera: " + s.getDias() + " Dias");
            System.out.println("Preco: " + s.getPreco() + " euros");
            System.out.println("Aceitar Servico?\n1)Sim      2)Nao     3)Proximo      0)Sair");
            int op = Input.lerInt();
            if(op==1){
                System.out.println("Data Atual:");
                LocalDateTime date = Input.scanDate();
                Servico servico = new Servico(s.getCodServico(),true,s.getEncomenda(),date,s.getCodUtilizador(),s.getCodTranportador(),s.getKmPercorridos(),s.getPreco(),s.getDias());
                aux.removeServicoHistorico(s);
                aux.addServicoHistorico(servico);
                aux.removeEncomenda(s.getEncomenda().getCodigoEncomenda());
                aux.removeAceites(s.getEncomenda().getCodigoEncomenda());
                break;
            }
            else if(op==2){
                System.out.println("Servico Rejeitado");
                aux.removeServicoHistorico(s);
                break;
            }
            else if(op==3) break;
        }

    }

    public void clickEnter(){
        System.out.println("\nClique ENTER para prosseguir.");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }


    public static int mostraOpcoes(String titulo, String[] opcoes) throws InputMismatchException{
        System.out.println("<=====>" + titulo + "<=====>");
        for (int i = 0; i < opcoes.length; i++) {
            System.out.println((1 + i) + "- " + opcoes[i]);
        }
        System.out.println("0 - Sair");
        int op = Input.lerInt();
        return op;
    }


}
