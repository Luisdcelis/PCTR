import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Life implements Runnable
{
    int idHilo;

    static int N = 10;
    static int nCelVivas;
    static int[][] m = new int[N][N];
    static int[][] aux = new int[N][N];
    static int nNuc = 10;
    static CyclicBarrier cb = new CyclicBarrier(nNuc);

    public Life(int id)
    {
        idHilo = id;
    }
    
    public static void InicMatrix()
    {
        Random r = new Random();
        nCelVivas  =  0;
        for(int i = 0; i < N; i++)
        {
            for(int j = 0; j < N; j++)
            {
                if(Math.round(r.nextFloat()) == 1)
                {
                    m[i][j] = 1;
                    nCelVivas++;
                }else{
                    m[i][j] = 0;
                }

            }
        }
    }

    public synchronized int nVecinos(int a, int b)
    {
        int nV = 0;
        for(int i = a-1; i <= a+1; i++)
        {
            for(int j = b-1; j <= b+1; j++)
            {
                if(i != j)
                    nV += m[a%N][b%N];
            }            
        }  
        return nV;
    }

    public synchronized void nextGen(int fila)
    {
        int nV;
        for(int i = 0; i < N; i++)
        {
            nV = nVecinos(fila, i);

            if(m[fila][i] == 1) //si esta  viva
            {
                if(nV < 2 || nV > 3)
                {
                    aux[fila][i] = 0; //muere
                    nCelVivas--;
                }
            }else{ // si esta muerta
                if(nV == 3)
                {
                    aux[fila][i] = 1; //nace
                    nCelVivas++;
                }
            }
        }
    }

    public synchronized void status()
    {
        System.out.println("Soy el hilo " + idHilo + "tengo prioridad " + Thread.currentThread().getPriority() +", hay " + nCelVivas + " celulas vivas.");
    }

    public void run() 
    {
        nextGen(idHilo);
        status();

        try{
            cb.await();
        }catch(Exception e){}
        
        m = aux;
    }

    // pruebaLife.java
    public static void main(String[] args) {
        
        InicMatrix();


        ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 10, 6000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        

        for(int iter  = 0; iter < 100; iter++)
        {
            for(int i = 0; i < 10; i++)
            {
                pool.execute(new Life(i));
            }
            
            
        }

        pool.shutdown();
        while(!pool.isTerminated()){}
    }


    
}