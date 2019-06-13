
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class cRMI implements Runnable{
	
	static iRMI s = null;
	int n;
	
	public void run() {
		try {
			BigInteger aux = s.factorial(n);
			System.out.println(aux.toString());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public cRMI(int n) {
		this.n = n;
	}
	
	public static void main(String[] args) {
		ThreadPoolExecutor ex = new ThreadPoolExecutor(
				Runtime.getRuntime().availableProcessors(),
				Runtime.getRuntime().availableProcessors(),
				60000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		
		try {
			s = (iRMI)Naming.lookup("//localhost:5050/servidor");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < 200; i++) {
			ex.execute(new cRMI(i));
		}
		
		ex.shutdown();
		while(!ex.isTerminated());
		
	}
	
	
}
