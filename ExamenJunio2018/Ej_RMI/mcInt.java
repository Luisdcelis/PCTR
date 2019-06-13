import java.rmi.Remote;
import java.rmi.RemoteException;

interface mcInt extends Remote
{
    public void addPoints(int nPoints) throws RemoteException;
    public double iValue() throws RemoteException;
}