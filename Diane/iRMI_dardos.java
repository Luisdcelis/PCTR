
import java.rmi.*;

interface iRMI_dardos extends Remote{

	int lanzar_dardo()	   throws RemoteException;
	int mostrar_res (int o)throws RemoteException;

}