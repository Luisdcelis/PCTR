import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class serverIntRemote extends UnicastRemoteObject implements mcInt
{
    static int cont;
    static int total;
    static ReentrantLock l = new ReentrantLock();

    public serverIntRemote() throws RemoteException {cont = 0; total = 0;}


    public void addPoints(int nPoints) throws RemoteException
    {
        Random rand = new Random();
        double x, y;

        for(int i = 0; i < nPoints; i++)
        {
            x = rand.nextDouble();
            y = rand.nextDouble();

            l.lock();
            if(y <= (-(Math.pow(x, 3)-Math.cos(x))))
            {
                cont++;
            }
            total++;
        }
    }

    public double iValue() throws RemoteException
    {
        return cont/total;
    }

    public static void main(String[] args) 
    {
        try
        {
            serverIntRemote server = new serverIntRemote();
            Naming.bind("//localhost:2221/servidor", server);
            System.out.println("servidor ready");
        }catch(Exception e){}
        
    }
}