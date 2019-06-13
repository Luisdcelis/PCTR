import java.util.concurrent.locks.ReentrantLock;

public class Pruebas implements Runnable
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
}
// da un resultado indeterminado entre 1 y 11, los 10 hilos hacen E.M.
// hacen un incremento cada uno y luego ejecutan el wait (en su referencia q 
// que apunta a el obj static p) y nadie lo saca de ahi, por lo que no hace  
// más incrementos. El hilo principal hace un incremento sin controlar E.M. e 
// imprime el valo sin esperar a los 10 hilos, que pueden haber hecho algún
// incremento o ninnguno.