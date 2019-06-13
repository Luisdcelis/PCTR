import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class prodEscalar implements Callable<Float>
{
    int ini, fin;
    static Float[] v1 = new Float[10000];
    static Float[] v2 = new Float[10000];

    public prodEscalar(int ini,  int fin)
    {
        this.ini = ini; this.fin = fin;
    }

    public Float call()
    {
        Float acum = 0f;
        Float xd;
        for(int i = ini; i < fin; ++i)
        {
            xd = v1[i] * v2[i];
            acum += xd;
        }

        return acum;
    }

    public static void main(String[] args) 
    {

        
        for(int i = 0; i< 10000; ++i)
        {
            // v[i] = rand.nextFloat();
            v1[i] = 1f;
            v2[i] = 1f;
        }

        int nNuc = Runtime.getRuntime().availableProcessors();

        ArrayList<Future<Float>> la = new ArrayList<Future<Float>>();
        ExecutorService ex = Executors.newFixedThreadPool(nNuc);

        Float acum = 0f;
        long ti = System.nanoTime();
        for (int i = 0; i < nNuc; i++) 
        {
            la.add(ex.submit(new prodEscalar(i*10000/nNuc, (i*10000/nNuc)+10000/nNuc)));
        }
        ex.shutdown();


        for (int i = 0; i < nNuc; i++) 
        {
            try{
                acum += la.get(i).get();   
            }catch(Exception e){e.printStackTrace();}
            
        }
        long t = System.nanoTime() - ti;

        System.out.println(t);
    }
}