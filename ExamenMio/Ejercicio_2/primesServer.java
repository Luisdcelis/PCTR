import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class primesServer extends UnicastRemoteObject implements primesInterface {

    public primesServer() throws RemoteException
    {
        super();
    }
    
    public int primesInRange(int beginRange, int endRange) throws RemoteException {

        int cont = 0;
        for(int i = beginRange; i < endRange; ++i)
        {
            if(i >= 2) //evitamos el 0 y el 1
            {
                int numDivis = 0;
                //Ahora comprobamos que i sea primo
                for(int j = 2; j <= Math.round((float) i/2f); ++j)
                {
                    if(i % j == 0) // si es divisor
                    {
                        numDivis++;
                    }
                }
                if(numDivis == 0)
                {
                    cont++;
                }
            }
        }
        return cont;
    }

    public static void main(String[] args) {
        try {
			primesServer server = new primesServer();
			Naming.bind("//localhost:1099/servidor", server);
			System.out.println("¡Servidor listo!");
		} catch (MalformedURLException | RemoteException | AlreadyBoundException e) {
			e.printStackTrace();
		}
    }
    
}