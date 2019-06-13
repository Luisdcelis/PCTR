import java.rmi.Naming;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class cDiane implements Runnable{
    static iDiane servidor;
    static CyclicBarrier barrera = new CyclicBarrier(10);
    int id;

    public cDiane(int id){
        this.id = id;
    }

    public void run(){
        
        try{
            for(int i = 0;i < 4;++i){
                System.out.println("El cliente " + id + " ha impactado en " + servidor.tirarDardo());            
            }
//            barrera.await();
            System.out.println("El cliente " + id + " ve una puntuacion " + servidor.puntuacion(1));
        }catch(Exception    E){}
    }

    public static void main(String[] args) throws Exception{
        servidor = (iDiane)Naming.lookup("//localhost:2020/Servidor");
        Thread hilos[] = new Thread[10];
        for(int i = 0;i < 10;++i){
            hilos[i] = new Thread(new cDiane(i));
        }
        for(int i = 0;i < 10;++i){
            hilos[i].start();
        }
        for(int i = 0;i < 10;++i){
            hilos[i].join();
        }
        
    }
}