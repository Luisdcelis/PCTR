import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class monteCarloRunnable implements Runnable{

	static int nDentro = 0, nTotal = 0;
	int nDentroLocal, nTotalLocal, nIter;
	static Random r = new Random(System.currentTimeMillis());
	
	public monteCarloRunnable(int nIter) {
		this.nIter = nIter;
		nDentroLocal = 0;
		nTotalLocal  = 0;
	}
	
	public void run() {
		for (int i = 0; i < nIter; ++i) {
			double pX = r.nextDouble();
			double pY = r.nextDouble();
			if (Math.sqrt(Math.pow(pX, 2)+Math.pow(pY, 2)) <= 1) {
				++nDentroLocal;
			}
			++nTotalLocal;
		}
		synchronized(r) {
			nDentro += nDentroLocal;
			nTotal  += nTotalLocal;
		}
	}
	
	public static void main(String[] args) throws InterruptedException{
		int nNucleos = Runtime.getRuntime().availableProcessors();
		ExecutorService	ex = Executors.newFixedThreadPool(nNucleos);
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce el numero de iteraciones por hilo");
		int nIt = sc.nextInt();
		long t1 = System.currentTimeMillis();
		
		for (int i = 0; i < nNucleos; ++i) {
			ex.execute(new monteCarloRunnable(nIt));
		}
		ex.shutdown();
		ex.awaitTermination(100, TimeUnit.DAYS);
		long t2 = System.currentTimeMillis();
		System.out.println("Pi es: " +  (4*(nDentro/(float)nTotal)));
		System.out.println("Tiempo empleado: " + ((t2-t1)/(float)1000) + " segundos" );
	}
	
}
