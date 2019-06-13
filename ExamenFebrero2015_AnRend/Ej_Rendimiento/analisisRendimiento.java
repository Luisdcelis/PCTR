import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class analisisRendimiento implements Runnable
{ 

    int idHilo;

    static int cont = 0;
    static int nTareas = 100;
    static int nNuc = Runtime.getRuntime().availableProcessors();
    static Object cerr1 = new Object();
    static ReentrantLock cerr2 = new ReentrantLock();
    static AtomicInteger contador = new AtomicInteger(0);
    static CyclicBarrier cb = new CyclicBarrier(nNuc);

    public analisisRendimiento(int idHilo)
    {
        this.idHilo = idHilo;
    }

    public void incSynchronized()
    {
        for(int i = 0; i < nTareas; ++i)
            synchronized(cerr1)
            {
                cont++;
            }
    }

    public void incReentrantLock()
    {
        for(int i = 0; i < nTareas; ++i)
        {
            cerr2.lock();
            cont++;
            cerr2.unlock();
        }
    }

    public void incAtomic()
    {
        for(int i = 0; i < nTareas; ++i)
        {
            contador.incrementAndGet();
        }
    }

    public void run()
    {
        long t = System.nanoTime();
        incSynchronized();
        try{
            cb.await();
        }catch(Exception e){}
        long tiempo = System.nanoTime()-t;
        if(idHilo == 0)
            System.out.println("Tiempo synchronized: " + tiempo);
        

        t = System.nanoTime();
        incReentrantLock();
        try{
            cb.await();
        }catch(Exception e){}
        tiempo = System.nanoTime()-t;
        if(idHilo == 0)
            System.out.println("Tiempo ReentrantLock: " + tiempo);

        
         t = System.nanoTime();
        incAtomic();
        try{
            cb.await();
        }catch(Exception e){}
        tiempo = System.nanoTime()-t;
        if(idHilo == 0)
            System.out.println("Tiempo AtomicInteger: " + tiempo);
        
    }
    
    public static void main(String[] args) 
    {
        ExecutorService ex = Executors.newFixedThreadPool(nNuc);

        for(int i = 0; i < nNuc; ++i)
        {
            ex.submit(new analisisRendimiento(i));
        }

        ex.shutdown();
        while(!ex.isTerminated()){}
    }
}