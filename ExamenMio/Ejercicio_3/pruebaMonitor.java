
public class pruebaMonitor extends Thread
{
    static monitorSem m;
    static int shared_cont = 0;
    
    public static void creaMonitor(int permits)
    {
        m = new monitorSem(permits);
    }

    public void inc()
    {
        m.waitSem();
        shared_cont++;
        m.signalSem();
    }

    public void run() 
    {
        for(int i = 0; i < 1000; ++i)
        {
            inc();
        }   
    }

    public static void main(String[] args) 
    {
        System.out.println("La variable compartida vale: " + shared_cont);
        creaMonitor(1);
        Thread h1 = new Thread(new pruebaMonitor());
        Thread h2 = new Thread(new pruebaMonitor());

        h1.start();
        h2.start();
        try {
            h1.join();
        } catch (InterruptedException ex) {}

        try {
            h2.join();
        } catch (InterruptedException ex) {}

        System.out.println("La variable compartida ahora vale: " + shared_cont);
    }
    
}