import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class monitorSem
{
    static int permits;
    static ReentrantLock cerrojo = new ReentrantLock();
    static Condition cond = cerrojo.newCondition();

    public monitorSem(int permits)
    {
        this.permits = permits;
    }

    public void waitSem()
    {
        cerrojo.lock();
        try{
            while(permits == 0)
            {
                try{
                    cond.await();
                }catch(InterruptedException e){}
            }
            permits--;
        }finally{
            cerrojo.unlock();
        }
    }

    public void signalSem()
    {
        cerrojo.lock();
        try{
            permits++;
            cond.signal();
        }finally{
            cerrojo.unlock();
        }
    }






}