import java.rmi.Naming;
import java.util.Scanner;

public class cRMI_dardos{

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		int op = 0;
		iRMI_dardos obj = (iRMI_dardos) Naming.lookup("//localhost:2020/Servidor");

		while(true){
			System.out.println("1. Lanzar dardo");
			System.out.println("2. Ver resultados");
			System.out.println("3. Salir");
			op = sc.nextInt();
			switch(op){
				case 1: 
						System.out.println("Has obtenido "+ obj.lanzar_dardo() +" .");
						break;
				case 2: 
						System.out.println("Que puntuacion quieres consultar?");
						op = sc.nextInt();
						System.out.println("Han obtenido "+op+" puntos "+obj.mostrar_res(op)+" veces.");
						break;
				case 3: 
						System.exit(1);
						break;
			}
		}
	}
}