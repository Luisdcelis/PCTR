import java.rmi.Naming;

public class cHelion
{   
    static iHelion serv = null;

    public static void main(String[] args) 
    {
        try{
            serv = (iHelion) Naming.lookup("//localhost:2020/servidor");
            
            serv.inicializaHelion();
            serv.status();
            while(!serv.hayArmisitcio())
            {
                serv.LanzaOjiva();
                serv.status();
            }
        }catch(Exception ee){}  
    }
}