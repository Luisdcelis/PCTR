import java.rmi.Remote;
import java.rmi.RemoteException;

public interface iHelion extends Remote
{
    public void inicializaHelion() throws RemoteException;
    public boolean hayArmisitcio() throws RemoteException;
    public void LanzaOjiva() throws RemoteException;
    public void status() throws RemoteException;
}