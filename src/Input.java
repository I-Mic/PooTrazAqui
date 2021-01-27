import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

public class Input {

    public static String lerString() {
        Scanner input = new Scanner(System.in).useDelimiter("\n");
        boolean ok = false;
        String txt = "";
        while(!ok) {
            try {
                txt = input.next();
                ok = true;
            }
            catch(InputMismatchException e)
            { out.println("Texto Invalido");
                out.print("Novo valor: ");
                input.next();
            }
        }
        //input.close();
        return txt;
    }

    public static LocalDateTime scanDate(){
        Scanner ler= new Scanner(System.in).useDelimiter("\n");
        int dia=0,mes=0,hora=0,minuto=0;
        int help=0;
        while(help==0) {
            System.out.println("Dia: ");
            dia = ler.nextInt();
            if(dia >=0 && dia <= 31) help++;
            else System.out.println("Dia invalido!");
        }
        while(help==1){
            System.out.println("Mes: ");
            mes = ler.nextInt();
            if(0 <= mes&& mes <=12) help++;
            else System.out.println("Mes invalido");
        }
        while(help==2){
            System.out.println("Hora: ");
            hora = ler.nextInt();
            if(0 <= hora && hora <=24) help++;
            else System.out.println("Hora invalida");
        }
        while(help==3){
            System.out.println("Minuto: ");
            minuto = ler.nextInt();
            if(0 <= minuto && minuto <=60) help++;
            else System.out.println("Minuto invalido");
        }
        while(help==4) break;
        return  LocalDateTime.of(2020,mes,dia,hora,minuto);
    }

    public static int lerInt() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        int i = 0;
        while(!ok) {
            try {
                i = input.nextInt();
                ok = true;
            }
            catch(InputMismatchException e)
            { out.println("Inteiro Invalido");
                out.print("Novo valor: ");
                input.nextLine();
            }
        }
        //input.close();
        return i;
    }

    public static double lerDouble() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        double d = 0.0;
        while(!ok) {
            try {
                d = input.nextDouble();
                ok = true;
            }
            catch(InputMismatchException e)
            { out.println("Valor real Invalido");
                out.print("Novo valor: ");
                input.nextLine();
            }
        }
        //input.close();
        return d;
    }
}
