import java.rmi.Naming;

public class clientIntRemote 
{
    static mcInt servidor = null;

    public static void main(String[] args) 
    {
        try{
            servidor = (mcInt) Naming.lookup("//localhost:2221/servidor");
            servidor.addPoints(10000);
            System.out.println(servidor.iValue()); 
        }catch(Exception e){}
    }

}