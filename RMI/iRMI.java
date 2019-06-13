
import java.math.BigInteger;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface iRMI extends Remote{

	public BigInteger factorial(int n) throws RemoteException;
	
}
