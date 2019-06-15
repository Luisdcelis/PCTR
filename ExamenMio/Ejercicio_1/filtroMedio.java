import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class filtroMedio implements Runnable
{
    static int[][] M;
    static double[][] J;
    static int f = 1;
    static int nNuc = Runtime.getRuntime().availableProcessors();
    static int choose;
    static int n;
    static int m;

    int ini, fin;

    public filtroMedio(int ini, int fin)
    {
        this.ini = ini;
        this.fin = fin;
    }

    private static void InicializaMatriz() 
    {
        
        if(choose == 1)
        {
            Scanner scan = new Scanner(System.in);
            for(int i = 0; i < n; ++i)
            {
                for(int j = 0; j < n; ++j)
                {
                    System.out.println("Introduca el elemento: M["+i+"]["+j+"]: ");
                    M[i][j] = scan.nextInt();
                }
            }
            scan.close();
        }else{
            if(choose == 2)
            {
                Random rand = new Random(System.currentTimeMillis());
                for(int i = 0; i < n; ++i)
                {
                    for(int j = 0; j < n; ++j)
                    {
                        M[i][j] = rand.nextInt(256);
                    }
                }
            }else{
                System.out.println("Introduzca un valor válido.");
            }
        }
        
    }

    private static void ImprimeMatriz(String msg, int[][] mat)
    {
        System.out.println(msg);
        for(int i = 0; i < n; ++i)
        {
            for(int j = 0; j < n; ++j)
            {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void ImprimeMatriz(String msg, double[][] mat)
    {
        System.out.println(msg);
        for(int i = 0; i < n; ++i)
        {
            for(int j = 0; j < n; ++j)
            {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public void run() 
    {
        for(int i = ini; i < fin; ++i)
        {
            for(int j = 0; j < n; ++j)
            {
                J[i][j] = AplicaFiltro(i, j);
            }
        }
    }
    

    private double AplicaFiltro(int a, int b) 
    {
        double res = 0;
        for(int i = a-f; i <= a+f; ++i)
        {
            for(int j = b-f; j <= b+f; ++j)
            {
                try{
                    res += M[i][j];
                }catch(ArrayIndexOutOfBoundsException e){
                    res += 0;
                }
                
            }
        }
        return (double) 1.0/Math.pow((2*f)+1, 2) * res;
    }

    public static void main(String[] args) 
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Introduzca la dimensión de la matriz");
        n = scan.nextInt();
        M = new int[n][n];
        J = new double[n][n];
        System.out.println("1.- Introducir manualmente\n2.-Introducir aleatoriamente");
        choose = scan.nextInt();
        InicializaMatriz();
        if(choose == 1)
            ImprimeMatriz("matriz inicial:", M);

        System.out.println("Introduzca el número de tareas: ");
        m = scan.nextInt();
        
        ThreadPoolExecutor ex = new ThreadPoolExecutor(nNuc, nNuc, 60000L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        int inic, fina;
        long t = System.nanoTime();
        for(int i = 0; i < m; ++i)
        {
            inic = i*(n/m);
            if(i == m-1)
                fina = n;
            else
                fina = (i+1)*(n/m);

            ex.submit(new filtroMedio(inic, fina));
        }

        ex.shutdown();
        while(!ex.isTerminated()){}
        long tiempoT = System.nanoTime()-t;

        if(choose == 1)
            ImprimeMatriz("matriz final:", J);

        System.out.println("Tiempo total: " + tiempoT/1000 + " microsegundos.");
    }
}