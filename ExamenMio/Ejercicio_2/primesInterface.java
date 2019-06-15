import java.rmi.Remote;
import java.rmi.RemoteException;

public interface primesInterface extends Remote
{
    public int primesInRange(int beginRange, int endRange) throws RemoteException;
}