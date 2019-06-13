import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class wireWorld implements Runnable
{
    static int[][] m;
    static int[][] aux;
    static int N;
    static int nIter;
    static int nNuc = Runtime.getRuntime().availableProcessors();
    static CyclicBarrier cb = new CyclicBarrier(nNuc);

    int ini, fin;

    public wireWorld(int ini, int fin)
    {
        this.ini = ini;
        this.fin = fin;
    }

    public static void inicAleat()
    {
        System.out.println("Introduzca la dimension de la reticula: ");
        Scanner scan = new Scanner(System.in);
        N = scan.nextInt();
        aux = new int[N][N];
        m = new int[N][N];

        Random rand = new Random();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                m[i][j] = rand.nextInt(4);
            }
        }
        System.out.println("Introduzca el numero de iteraciones: ");
        nIter = scan.nextInt();
        scan.close();
    }
    public static void inicManual()
    {
        System.out.println("Introduzca la dimension de la reticula: ");
        Scanner scan = new Scanner(System.in);
        N = scan.nextInt();
        aux = new int[N][N];

        Random rand = new Random();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.println("Introduzca el valor del elemento [" + i + "][" + j +"]: ");
                m[i][j] = scan.nextInt();
            }
        }
        System.out.println("Introduzca el numero de iteraciones: ");
        nIter = scan.nextInt();
        scan.close();
    }

    public void status()
    {
        for(int i = 0; i < N; ++i)
        {
            for(int j = 0; j < N; ++j)
            {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int cuentaVecinos(int a, int b)
    {
        int nVec = 0;
        for(int i = a-1; i <= a+1; ++i)
        {
            for(int j = b-1; j <= j+1; ++j)
            {
                if(i != b)
                {
                    try{
                        if(m[Math.floorMod(i, N)][Math.floorMod(j, N)] == 2)
                            nVec++;
                        }catch(Exception e){
                            System.out.println("[i%N] = " + i % N + "[j%N] = " + j%N );
                            System.out.println("i = " + i + " N = " + N);
                        }
                }
            }
        }
        return nVec;
    }

    public void funcTransicion(int a, int b)
    {
        int nVec;
        if(m[a][b] != 0)
        {
            if(m[a][b] == 1)
            {
                aux[a][b] = 2;
            }else{
                if(m[a][b] == 2)
                {
                    aux[a][b] = 3;
                }else{
                    if(m[a][b] == 3)
                    {
                        nVec = cuentaVecinos(a, b);
                        if(nVec == 1 || nVec == 2)
                        {
                            aux[a][b] = 1;
                        }
                    }
                }
            }
        }
    }

    public void siguiente()
    {
        for(int i = ini; i < fin; ++i)
        {
            for(int j = 0; j < N; ++j)
            {
                funcTransicion(i, j);
            }
        }
    }
    
    public void actualiza()
    {
        for(int i = ini; i < fin; ++i)
        {
            for(int j = 0; j < N; ++j)
            {
                m[i][j] = aux[i][j];
            }
        }
    }

    public void run()
    {
        for(int i = 0; i < nIter; ++i)
        {
            if(ini == 0)
            {
                status();
            }

            siguiente();
            
            try{
            cb.await();
            }catch(Exception e){}

            actualiza();

            
        }
    }

    public static void main(String[] args) 
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("1.- Introducir a mano \n2.- Introducir aleat.");
        int c = scan.nextInt();

        switch (c) {
            case 1:
                inicManual();
                break;
        
            case 2:
                inicAleat();
                break;
        }

        ThreadPoolExecutor pool = new ThreadPoolExecutor(nNuc, nNuc, 6000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

        int inic, fina;
        for(int i = 0; i < nNuc; ++i)
        {
            inic = i*(N/nNuc);
            if(i == nNuc-1)
                fina = N;
            else
                fina = i*(N/nNuc)+N/nNuc;
            

            pool.execute(new wireWorld(inic, fina));
        }

        pool.shutdown();
        while(!pool.isTerminated()) {}


    }

}