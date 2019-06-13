import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class factorialCallable implements Callable<BigInteger>
{
    static int n;
    int ini, fin;

    public factorialCallable(int ini, int fin)
    {
        this.ini = ini;
        this.fin = fin;
    }

    public BigInteger call()
    {
        BigInteger aux = new BigInteger("1");
        for(int i = ini; i < fin; ++i)
        {
            aux = aux.multiply(new BigInteger(Integer.toString(i)));
        }
        return aux;
    }

    public static void main(String[] args) {
        
        int nNuc = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor ex = new ThreadPoolExecutor(nNuc, nNuc, 60000L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

        Scanner scan = new Scanner(System.in);
        System.out.println("Introduce: ");
        n =scan.nextInt();

        ArrayList<Future<BigInteger>> numeros = new  ArrayList<Future<BigInteger>>();

        int inic, fina;
        for(int i = 0; i < nNuc; ++i)
        {
            if(i == 0)
                inic = 1;
            else
                inic = i*n/nNuc;

            if(i != nNuc -1)
                fina = (i+1)*n/nNuc;
            else
                fina = n+1;

            numeros.add(ex.submit(new factorialCallable(inic, fina)));
        }

        ex.shutdown();


        BigInteger lol = new BigInteger("1");
        for(Future<BigInteger> aux: numeros)
        {
            try{
                lol = lol.multiply(aux.get());
            }catch(Exception e){}
        }

        System.out.println(lol.toString());
    }   

    
}
