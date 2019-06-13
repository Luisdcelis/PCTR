
import java.rmi.*;
import java.util.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.atomic.AtomicInteger;

public class sRMI_dardos extends UnicastRemoteObject  implements iRMI_dardos{

	AtomicInteger[] puntuacion = new AtomicInteger[11];

	public sRMI_dardos() throws RemoteException{ 

		/* super(); */

		for(int i = 0; i!= puntuacion.length ;i++){
			puntuacion[i] = new AtomicInteger();
		}
	}

	@Override
	public int lanzar_dardo(){

		double rx = (double) (Math.random()*10);
		double ry = (double) (Math.random()*10);

		//Dar posibilidad de que sea negativo
		if(Math.random() < 0.5)
			rx *= -1;
		//Por esto suspendí PCTR xd
		if(Math.random() < 0.5)
			ry *= -1;

		double distance = Math.sqrt(Math.pow((rx - 0.0),2) + Math.pow((ry - 0.0),2));
		int basura;

		if(distance <= 1){
			basura = puntuacion[10].addAndGet(1);
			return 10;
		}

		if(distance > 1 && distance <= 2){
			basura = puntuacion[9].addAndGet(1);
			return 9;
		}

		if (distance > 2 && distance <= 3){
			basura = puntuacion[8].addAndGet(1);
			return 8;
		}

		if(distance > 3 && distance <= 4){
			basura = puntuacion[7].addAndGet(1);
			return 7;
		}

		if(distance > 4 && distance <= 5){
			basura = puntuacion[6].addAndGet(1);
			return 6;
		}

		if(distance > 5 && distance <= 6){
			basura = puntuacion[5].addAndGet(1);
			return 5;
		}

		if(distance > 6 && distance <= 7){
			basura = puntuacion[4].addAndGet(1);
			return 4;
		}

		if(distance > 7 && distance <= 8){
			basura = puntuacion[3].addAndGet(1);
			return 3;
		}

		if(distance > 8 && distance <= 9){
			basura = puntuacion[2].addAndGet(1);
			return 2;
		}

		if(distance > 9 && distance <= 10){
			basura = puntuacion[1].addAndGet(1);
			return 1;
		}

		basura = puntuacion[0].addAndGet(1);
		return 0;
	}

	@Override
	public int mostrar_res (int o){
		return puntuacion[o].get();
	}

	public static void main(String[] args) throws Exception {

		iRMI_dardos oRemoto= (iRMI_dardos) new sRMI_dardos();
		Naming.rebind("//localhost:2020/Servidor", oRemoto);
		System.out.println("Servidor preparado...");

	}
}