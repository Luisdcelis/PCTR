import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


// Sencillo ejemplo para ver que un proograma puede ser determinista
// y correcto totalmente (se cumple la exclusion mutua y el programa termina)
// 
// Cada hilo imprime su noombre (no siempre en el mismo orden)
public class EjDeterminismo implements Runnable
{
    String name;
    static Object ob = new Object();
    static int i = 0;

    public EjDeterminismo(String name)
    {
        this.name = name;
    }

    public void run()
    {
        synchronized(ob)
        {
            i++;
            System.out.println("Hola soy el hilo: " + name + "\t  i = "+ i);
        }
    }

    public static void main(String[] args) {
        int nNuc = Runtime.getRuntime().availableProcessors();
        ExecutorService ex = Executors.newFixedThreadPool(nNuc);

        for(int i = 0; i < nNuc; i++)
        {
            ex.execute(new EjDeterminismo("hilo" + Integer.toString(i)));
        }

        ex.shutdown();
        while(!ex.isTerminated()){}

        System.out.println("enga tira");
    }

    
}