import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class primesClient
{
    static primesInterface serv = null;
    int inicioRango, finRango;
    int numeroSubr;

    public void leerEntrada()
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Introduzca el inicio del rango");
        inicioRango = scan.nextInt();
        System.out.println("Introduzca el final del rango");
        finRango = scan.nextInt();
        System.out.println("Introduzca el número de subrangos");
        numeroSubr = scan.nextInt();
        
        scan.close();
    }

    public int cantidadPrimos() throws RemoteException
    {
        int cont = 0, ini, fin;
        for(int i = 0; i < numeroSubr; ++i)
        {
            ini = (i*(finRango-inicioRango)/numeroSubr)+inicioRango;
            if(i == numeroSubr -1)
                fin = finRango+1;
            else
                fin = ((i+1)*(finRango-inicioRango)/numeroSubr)+inicioRango;

            cont += serv.primesInRange(ini, fin);
        }

        return cont;
    }

    public static void main(String[] args) throws RemoteException
    {
        try {
			serv = (primesInterface)Naming.lookup("//localhost:1099/servidor");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
        }

        primesClient client = new primesClient();

        client.leerEntrada();
        int primos = client.cantidadPrimos();

        System.out.println("Hay "+ primos+ " primos.");
        
    }

}