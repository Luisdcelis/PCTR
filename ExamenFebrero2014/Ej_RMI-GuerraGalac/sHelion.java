import java.rmi.Remote;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

import javafx.util.Pair;

public class sHelion extends UnicastRemoteObject implements iHelion
{
    private static final long serialVersionUID = 1L;
    int[][] helion = new int[200][100];
    int nCiudades;
    Pair<Integer, Integer> BlackHole;

    public void inicializaHelion() throws RemoteException
    {
        Random rand = new Random();
        nCiudades = 0;
        int a,b;
        boolean salir = false;


        while(nCiudades != 2500)
        {
            a = rand.nextInt(200);
            b = rand.nextInt(100);

            if(helion[a][b] == 0)
            {
                helion[a][b] = 1;
                nCiudades++;
            }
        }

        while(!salir)
        {
            a = rand.nextInt(200);
            b = rand.nextInt(100);

            if(helion[a][b] == 0)
            {
                helion[a][b] = 2;
                salir = true;
                BlackHole = new Pair<Integer, Integer>(a, b);
            }
        }
    }

    public boolean hayArmisitcio() throws RemoteException
    {
        if(nCiudades < 100 || helion[BlackHole.getKey()][BlackHole.getValue()] == 0) 
            return true;
        else
            return false;
    }

    public void LanzaOjiva() throws RemoteException
    {
        Random rand = new Random();
        int a, b;
        a = rand.nextInt(200);
        b = rand.nextInt(100);

        if(helion[a][b] != 0)
        {
            helion[a][b] = 0;
            nCiudades--;
        }
    }

    public void status() throws RemoteException
    {
        System.out.println("Quedan " + nCiudades + " ciudades.");
    }

    public sHelion() throws RemoteException
    {}

    public static void main(String[] args) 
    {
        try{
            sHelion servidor = new sHelion();
            Naming.bind("//localhost:2020/servidor", servidor);
            System.out.println("tamo rustico");
        }catch(Exception e){}

    }



}