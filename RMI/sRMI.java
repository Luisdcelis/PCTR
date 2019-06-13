
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class sRMI extends UnicastRemoteObject implements iRMI {

	public sRMI()throws RemoteException {}
	
	public BigInteger factorial(int n) throws RemoteException{
		BigInteger aux = new BigInteger("1");
	
		while (n != 1) {
			aux = aux.multiply(new BigInteger(Integer.toString(n)));
			n--;
		}
		
		return aux;
	}

	public static void main(String[] args) {
		try {
			sRMI server = new sRMI();
			Naming.bind("//localhost:5050/servidor", server);
			System.out.println("Servidor listo!");
		} catch (MalformedURLException | RemoteException | AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
