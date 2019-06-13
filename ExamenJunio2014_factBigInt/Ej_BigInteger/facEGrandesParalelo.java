import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class facEGrandesParalelo implements Callable<BigInteger> {
    int ini, fin;

    public facEGrandesParalelo(int ini, int fin) {
        this.ini = ini;
        this.fin = fin;
    }

    public BigInteger call() {
        BigInteger res = new BigInteger("1");
        for (int i = ini; i < fin; ++i) {
            if(i != 0)
                res = res.multiply(new BigInteger(Integer.toString(i)));
                
        }

        return res;
    }

    public static void main(String[] args) {
        int nNuc = Runtime.getRuntime().availableProcessors();
        ExecutorService ex = Executors.newFixedThreadPool(nNuc);

        System.out.println("Introuzca numero: ");
        Scanner scan = new Scanner(System.in);
        int num = scan.nextInt();

        int inic, fina;

        ArrayList<Future<BigInteger>> salidas = new ArrayList<Future<BigInteger>>();
        for(int i = 0; i < nNuc; ++i)
        {
            inic = i*(num/nNuc);
            if(i != nNuc-1)
                fina = (i*(num/nNuc))+(num/nNuc);
            else
                fina = num;
                
            System.out.println(inic + ", " + fina);
            salidas.add(ex.submit(new facEGrandesParalelo(inic, fina)));
        }

        ex.shutdown();
        while(!ex.isTerminated()){}

        for (int i = 0; i < nNuc; i++) 
        {
            try{
                System.out.println(salidas.get(i).get().toString());
            }catch(Exception e){}
        }

        BigInteger res = new BigInteger("1");

        for(int i = 0; i < nNuc; ++i)
        {
            try{
                res = res.multiply(salidas.get(i).get());
            }catch(Exception e){}
        }

        System.out.println("El resultado es: " + res.toString());


    }
}