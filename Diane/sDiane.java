import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class  sDiane extends UnicastRemoteObject implements iDiane{
    private AtomicInteger puntuaciones[] = new AtomicInteger[10];
    private Random generador;

    public sDiane() throws RemoteException {
        super();
    }

    public int tirarDardo()throws RemoteException{
        double x = generador.nextDouble() * 10;
        double y = generador.nextDouble() * 10;

        if(generador.nextDouble() < 0.5){
            x = -x;
        }
        if(generador.nextDouble() < 0.5){
            y = -y;
        }
        
        double distancia = Math.sqrt(x*x + y*y);
        if(distancia <= 1){
            puntuaciones[10].addAndGet(1);
            return 10;
        }
        if(distancia > 1 && distancia <= 2){
            puntuaciones[9].addAndGet(1);
            return 9;
        }
        if(distancia > 2 && distancia <= 3){
            puntuaciones[8].addAndGet(1);
            return 8;
        }
        if(distancia > 3 && distancia <= 4){
            puntuaciones[7].addAndGet(1);
            return 7;
        }
        if(distancia > 4 && distancia <= 5){
            puntuaciones[6].addAndGet(1);
            return 6;
        }
        if(distancia > 5 && distancia <= 6){
            puntuaciones[5].addAndGet(1);
            return 5;
        }
        if(distancia > 6 && distancia <= 7){
            puntuaciones[4].addAndGet(1);
            return 4;
        }
        if(distancia > 7 && distancia <= 8){
            puntuaciones[3].addAndGet(1);
            return 3;
        }
        if(distancia > 8 && distancia <= 9){
            puntuaciones[2].addAndGet(1);
            return 2;
        }
        if(distancia > 9 && distancia <= 10){
            puntuaciones[1].addAndGet(1);
            return 1;
        }
        return 0;
    }

    public int puntuacion(int index) throws RemoteException{
        if(index < 0 || index > 10) return -1;
        return puntuaciones[index].get();
    }

    private void inicializa() throws RemoteException{
        for(int i = 0;i < puntuaciones.length;++i){
            puntuaciones[i] = new AtomicInteger(0);
        }
        generador = new Random();
    }

    public static void main(String[] args) throws Exception {
        sDiane servidor = new sDiane();
        Naming.bind("//localhost:2020/Servidor", servidor);
        servidor.inicializa();
        System.out.println("Servidor listo");
    }
}