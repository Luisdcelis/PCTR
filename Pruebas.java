import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* public class Pruebas implements Runnable
{
    public static Integer p = new Integer(0);
    public static ReentrantLock r = new ReentrantLock();
    public int vMax;

    public Pruebas(int v){vMax = v;}
    
    public void run()
    {
        Object q = p;
        for(int cont = 0; cont < vMax; cont++)
        {
            r.lock();
            p++;
            r.unlock();

            synchronized(q)
            {
                try{
                    System.out.println("jajajaja");
                    q.wait();
                }catch(InterruptedException ex){}
            }
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        Thread[] h = new Thread[10];
        for (int i = 0; i < h.length; i++) {
            h[i] = new Thread(new Pruebas(100));
        }
        for (int i = 0; i < h.length; i++) {
            h[i].start();
        }

        Thread j = Thread.currentThread();
        p++;
        System.out.println(p);
    }
} */
// da un resultado indeterminado entre 1 y 11, los 10 hilos hacen E.M.
// hacen un incremento cada uno y luego ejecutan el wait (en su referencia q 
// que apunta a el obj static p) y nadie lo saca de ahi, por lo que no hace  
// más incrementos. El hilo principal hace un incremento sin controlar E.M. e 
// imprime el valo sin esperar a los 10 hilos, que pueden haber hecho algún
// incremento o ninnguno.

// ──────────────────────────────────────────────────────────────────────────

/* public class Pruebas extends Thread
{
    public static int nH = 10;
    public static int n = 0;
    public static ReentrantLock l = new ReentrantLock();
    public Condition v;
    public Pruebas(){v = l.newCondition();}

    public void run()
    {
        l.lock();
        try{
            try{v.await();}catch(InterruptedException ex){}
            n++;
        }finally{
            l.unlock();
        }
        
        
    }

    public static void main(String[] args) throws InterruptedException
    {
        Pruebas[] h = new Pruebas[nH];
        for (int i = 0; i < h.length; i++) 
        {
            h[i] = new Pruebas();
            h[i].start();    
        }

        for (int i = 0; i < h.length; i++) 
        {
            h[i] = new Pruebas();
            h[i].join();    
        }

        n++;
        System.out.println(n);
    }
} */
// El resultado es 1. los hilos se bloquean indefinidamente en su condition
// por lo que no llegan a incrementar el valor de la variable compartida,
// y el join se hace sobre un objeto nuevo, por lo que el hilo principal no 
// para su ejecucion (esperando a los hilos). incrementa la variable y la 
// imprime.

// ──────────────────────────────────────────────────────────────────────────

/* public class Pruebas extends Thread 
{
    static int x = 0;
    Semaphore cerrojo = new Semaphore(1);
    int t;

    public Pruebas(int t){this.t = t;}

    public void run()
    {
        switch(t)
        {
            case 0:
            {
                try{
                    cerrojo.acquire();
                }catch(InterruptedException ex){}
                x++;
                cerrojo.release();
            }
            case 1:
            {
                try{
                    cerrojo.acquire();
                }catch(InterruptedException ex){}
                x--;
                cerrojo.release();
            }
        }
    }

    public static void main(String[] args) throws Exception
    {
        Pruebas[] lista = new Pruebas[10000];
        for (int i = 0; i < lista.length; i++) 
        {
            lista[i] = new Pruebas(1);
            lista[i].start();    
        }

        for (int i = 0; i < lista.length; i++) 
        {
            lista[i].join();
        }

        System.out.println(x);
    }
    
} */
// Como el semaphore no es estático los hilos no entrarán en exclusion mutuaç
// por lo tanto el resultado final será un número entre 1 y -10000.

// ──────────────────────────────────────────────────────────────────────────

/* public class Pruebas extends Thread
{
    public static Object[] locks;
    public static Integer n = new Integer(0);
    private int id;
    
    public Pruebas(int id) {this.id = id;}

    public void run()
    {
        synchronized(locks[id])
        {
            n++;
            if(id%2==0)
            {
                try{
                    locks[id].wait();
                }catch(InterruptedException e){}
            }
            System.out.println(n);
        } 
    }

    public static void main(String[] args)
    {
        locks = new Object[4];
        for (int i = 0; i < 4; i++) 
        {
            locks[i] = new Object();
        }

        for (int i = 0; i < 4; i++) 
        {
            new Pruebas(i).start();
        }
    }
} */
// Los 4 hilos realizan un incremento sobre n sin E.M., ya que cada hilo
// tiene su propio cerrojo (hacen el bloque synchroninzed sobre un 
// objeto distinto). Los hilos con id pares se bloquearán pq cumplen
// la conndicion del if, los hilos impares imprimen la variable n, que tendrá
// un valor entre 1 y 4.

// ──────────────────────────────────────────────────────────────────────────

/* 
public class Pruebas implements Runnable
{
    private static AtomicInteger s = new AtomicInteger();
    private static ReentrantLock l = new ReentrantLock();
    private static int p = 2000;
    private static ArrayList<Condition> c = new ArrayList<Condition>();
    private int n;

    public Pruebas(int n)
    {
        this.n = n;
        c.add(n, l.newCondition());
    }

    public void run()
    {
        int j;

        for(int i = 0; i < 1000; i++); // Este for es de hdp

        l.lock();
        try{
            j = s.incrementAndGet();
            c.get(0).signalAll();
            try{
                c.get(n).await();
            }catch(InterruptedException e){}
        }finally{
            l.unlock();
        }
    }

    public static void main(String[] args) 
    {
        ExecutorService ept = Executors.newCachedThreadPool();
        for(int i = 0; i < 10000; i++)
        {
            ept.submit(new Pruebas(i));
        }
        ept.shutdown();
        System.out.println(s);
    }
} */
// Todos los incrementan la variable en EM pero el hilo principal no espera
// que todos los hilos terminen, por lo tanto el resultado seria indefinido
// entre 1 y 2000. Also, todos los hilos se quedan bloqueados esperando el 
// signal

// ──────────────────────────────────────────────────────────────────────────

/* public class Pruebas implements Runnable
{
    public static int n = 0;
    public static ReentrantLock k = new ReentrantLock();

    public Pruebas() {}

    public void run()
    {
        try{
            k.lock();
            n++;
        }finally{
            k.unlock();
        }
    }

    public static void main(String[] args) {
        //SizePool, MaxSizePool, keepAliveTime, UnitTime, workQueue
        ThreadPoolExecutor tp = new ThreadPoolExecutor(10, 10, 2000L, 
                                    TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        tp.prestartAllCoreThreads();

        Pruebas[] tareas = new Pruebas[5];
        for (int k = 0; k < tareas.length; k++) 
        {
            tareas[k] = new Pruebas();   
            tp.execute(tareas[k]); 
        }

        tp.shutdown();
        System.out.println(n);
    }
} */
// Se crea un pool de 10 hilos de tamaño fijo a el cual se le mandan 5 tareas
// cada una de ellas realiza un incremento  de una variable en E.M., sin
// embargo el hilo principal no espera que termine el pool, si no que 
// directamente pasa a imprimir la variable n, que tiene un valor indeterminado
// entre 0 y 5.

// ──────────────────────────────────────────────────────────────────────────

/* public class Pruebas
{
    public static void main(String[] args) {
        int[][] v = new int[10][10];
        Random rand = new Random();

        for(int i = 0; i < 10; ++i)
        {
            for(int j = 0; j < 10; ++j)
            {
                
                v[i][j] = rand.nextInt(10);
            }
        }
        for(int i = 0; i < 10; ++i)
        {
            for(int j = 0; j < 10; ++j)
            {
                
                System.out.print(v[i][j] +" ");
            }
            System.out.println();
        }

        int[][] v2 = v.clone();

        v2[0][0] = 30;

        for(int i = 0; i < 10; ++i)
        {
            for(int j = 0; j < 10; ++j)
            {
                
                System.out.print(v[i][j] + " ");
            }
            System.out.println();
        }




    }

} */

public class Pruebas
{
    public static void main(String[] args) {
        int i = -1;
        int N = 8;
        System.out.println("res = " + Math.floorMod(i, N));
    }
}