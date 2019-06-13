package ibex;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jmaj
 */
public class sIBEX5 extends UnicastRemoteObject implements iIBEX5{
    private static int TRI;
    private static int CHN;
    private static int BMM;
    private static int CRR;
    private static int TDC;
    private static Random rnd =new Random();
    
    public sIBEX5()throws RemoteException{
        TRI=rnd.nextInt(10);
        CHN=rnd.nextInt(10);
        BMM=rnd.nextInt(10);
        CRR=rnd.nextInt(10);
        TDC=rnd.nextInt(10);
    }
    @Override
    public String verValor(int i) throws RemoteException {
        int val=-1;
        switch(i){
            case 1:
                val = TRI;
                break;
            case 2:
                val=CHN;
                break;
            case 3:
                val=BMM;
                break;
            case 4:
                val=CRR;
                break;
            case 5:
                val=TDC;
                break;
        }
        if(val==-1)return("Ese valor no existe");
        else return ("El valor es "+val);
        }

    @Override
    public String consultarTodo() throws RemoteException {
      String t = ("TRI="+TRI+"\nCHN="+CHN+"\nBMM="+BMM+"\nCRR="+CRR+"\nTDC="+TDC);
      return t;
    }
    
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            iIBEX5 servidor = new sIBEX5();
            Naming.rebind("rmi:/localhost:1099/ibex", servidor);
            System.out.println("Servidor Ejecutado sin problemas");
            ExecutorService ex = Executors.newSingleThreadExecutor();
            ex.execute(new Runnable(){
                @Override
                public void run(){
                    while(true){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex1) {
                            Logger.getLogger(sIBEX5.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                        TRI=rnd.nextInt(100);
                        CHN=rnd.nextInt(100);
                        BMM=rnd.nextInt(100);
                        CRR=rnd.nextInt(100);
                        TDC=rnd.nextInt(100);
                    }
                }
            });
        } catch (RemoteException | MalformedURLException ex) {
            Logger.getLogger(sIBEX5.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
        
}
