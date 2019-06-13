import java.rmi.Remote;
import java.rmi.RemoteException;

public interface iDiane extends Remote{
    public int tirarDardo()throws RemoteException;
    public int puntuacion(int index) throws RemoteException;
}