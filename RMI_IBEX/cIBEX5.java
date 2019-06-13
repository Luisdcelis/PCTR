
package ibex;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jmaj
 */
public class cIBEX5 {
    public static void main(String[] args) {
        try {
            iIBEX5 servidor =(iIBEX5) Naming.lookup("rmi:/localhost:1099/ibex");
            System.out.println("Conexión establecida");
            int elec;
            Scanner sc = new Scanner(System.in);
            do{
                System.out.println("Elige una opción");
                System.out.println("Ver un valor concreto:\n 1.TRINCOSA\n2.CHORIZOS NACIONALES\n3.BANCO MALO PERO MALO\n4.CORRUPTOLANDIA\n5.TOMA EL DINERO Y CORRE");
                System.out.println("6.Mostrar Todo.");
                System.out.println("7.Salir");
                elec = sc.nextInt();
                switch(elec){
                    case 1:
                        System.out.println(servidor.verValor(elec));
                        break;
                    case 2:
                        System.out.println(servidor.verValor(elec));                       
                        break;
                    case 3:
                        System.out.println(servidor.verValor(elec));                        
                        break;
                    case 4:
                        System.out.println(servidor.verValor(elec));                        
                        break;
                    case 5:
                        System.out.println(servidor.verValor(elec));                        
                        break;
                    case 6:
                        System.out.println(servidor.consultarTodo());                        
                        break;
                }
                sc.nextLine();
            }while(elec!=7);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(cIBEX5.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
