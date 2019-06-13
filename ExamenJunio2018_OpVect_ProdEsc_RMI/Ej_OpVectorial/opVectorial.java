import java.security.Principal;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;



class opVectorial implements Runnable
{

    static float[] Va,Vb,Vc,Vaux;
    static  int nNuc = Runtime.getRuntime().availableProcessors();
    static Semaphore semaforo = new Semaphore(nNuc);
    static Semaphore sema2 = new Semaphore(1);
    static float k = 0;
    static boolean hecho = false;

    int ini, fin, mio = 0;

    public opVectorial(int a, int b)
    {
        this.ini = a;
        this.fin = b;
    }


    public void run()
    {
        try{
            semaforo.acquire();
            for(int i = ini; i < fin; i++)
            {
                Vaux[i] = Va[i] + Vb[i];   
            }
            semaforo.release();
            while(semaforo.availablePermits() != nNuc){}

            semaforo.acquire();
            for(int i = ini; i < fin; i++)
            {
                Vaux[i] = Vaux[i] * Vc[i];
            }
            semaforo.release();
            while(semaforo.availablePermits() != nNuc){}


            semaforo.acquire();
            for(int i = ini; i < fin; i++)
            {
                mio += Vaux[i]*Vaux[i];
            }
            semaforo.release();
            while(semaforo.availablePermits() != nNuc){}

            semaforo.acquire();
            sema2.acquire();
            k += mio;
            sema2.release();
            semaforo.release();
            while(semaforo.availablePermits() != nNuc){}

            semaforo.acquire();
            sema2.acquire();
            if(hecho == false)
            {
                k = (float) Math.sqrt(k);
                hecho = true;
            }
            sema2.release();
            semaforo.release();
            while(semaforo.availablePermits() != nNuc){}

            semaforo.acquire();
            for(int i = ini; i < fin; i++)
            {
                Vaux[i] = Va[i] * k;
            }
            semaforo.release();
            while(semaforo.availablePermits() != nNuc){}

        }catch(Exception e){System.out.println(e.fillInStackTrace());}
    }

    public static void main(String[] args) 
    {
        int c, tam;
        Scanner scan = new Scanner(System.in);
        System.out.println("1.- 10^6 con rands\n2.- input size y nums");
        c = scan.nextInt();

        if(c == 1)
        {
            Random rand = new Random();
            tam = 8;
            Va = new float[tam];
            Vb = new float[tam];
            Vc = new float[tam];
            Vaux = new float[tam];

            for(int i = 0; i < tam; i++)
            {
                // Va[i] =  rand.nextFloat();
                // Vb[i] =  rand.nextFloat();
                // Vc[i] =  rand.nextFloat();
                Va[i] =  1;
                Vb[i] =  1;
                Vc[i] =  1;
            }
        }else{
            System.out.println("Introduzca el tamaño: ");
            tam = scan.nextInt();
            Va = new float[tam];
            Vb = new float[tam];
            Vc = new float[tam];
            Vaux = new float[tam];
            for(int i  = 0; i < tam; i++)
            {
                System.out.println("Introduzca el elemento " + i + " de Va: ");
                Va[i] = scan.nextFloat();
                System.out.println("Introduzca el elemento " + i + " de Vb: ");
                Vb[i] = scan.nextFloat();
                System.out.println("Introduzca el elemento " + i + " de Vc: ");
                Vb[i] = scan.nextFloat();
            }
        }

        ThreadPoolExecutor pool = new ThreadPoolExecutor(nNuc, nNuc, 6000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());
        
        int ini, fin;
        for(int i = 0; i < nNuc; ++i)
        {
            if(i == nNuc-1)
            {
                ini = i*tam/nNuc;
                fin = tam;
            }else{
                ini = i*tam/nNuc;
                fin = i*tam/nNuc+tam/nNuc;
            }
            // System.out.println(ini + " , "+ fin);
            pool.execute(new opVectorial(ini, fin));

        }
        pool.shutdown();
        while(!pool.isTerminated()){}
        
        System.out.println(Arrays.toString(Vaux));
        System.out.println(k);

    }
}