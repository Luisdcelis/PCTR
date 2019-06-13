
package ibex;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;

/**
 *
 * @author jmaj
 */
public interface iIBEX5 extends Remote{
    
    public String verValor(int i) throws RemoteException;
    
    public String consultarTodo() throws RemoteException;
}