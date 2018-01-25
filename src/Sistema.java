import java.util.Scanner;
/**
 * Clase sistema que se encarga de obtener el largo y el ancho del mapa y la cantidad de zonas
 * Crear la mision y de enviarselo al lider del programa.
 * @author Baldo Morales
 * @author Kevin Araya
 * @author Joaquin Solano
 */
public class Sistema {
	// Mision del programa que contiene el mapa y el objetivo
	private Mision mision;
	
	// Largo del mapa que indica la cantidad de filas
	private int largoMapa;
	
	// Ancho del mapa que indica la cantidad de columnas
	private int anchoMapa;
	
	// Variable que indica la cantidad de zonas en el mapa
	private int sectores;
	
	public Sistema(AgenteLider lid) {

		System.out.println("Iniciando Sistema SWAT");
		Scanner entrada = new Scanner (System.in);
		
		// Obtener el largo del mapa
		System.out.println("Inserte el largo del mapa:");
		largoMapa = entrada.nextInt();
		
		// Obtener el ancho del mapa
		System.out.println("Inserte el ancho del mapa:");
		anchoMapa = entrada.nextInt();
		
		// Obtener la cantidad de zonas
		System.out.println("Inserte la cantidad de sectores del mapa:");
		sectores = entrada.nextInt();	
		
		// Cear la mision entregando los parametros ingresados por pantallas
		mision = new Mision(largoMapa, anchoMapa, sectores);
		
		/**
		 *Metodo que muestra las dimensiones de la matriz y sus respectivas zonas. 
		 * 		
		int [][] matriz = new int[anchoMapa][largoMapa];
		for(int k=0; k < mision.getMapa().getListaCoordenadas().length; k++) {
			Zona zona = mision.getMapa().getListaCoordenadas()[k];
			for(int i=zona.getZonaXInicial();i < zona.getZonaXFinal(); i++) {
				for(int j=zona.getZonaYInicial(); j < zona.getZonaYFinal(); j++) {
					matriz[i][j]= k+1;
				}
			}
		}
		for(int i=0;i < anchoMapa; i++) {
			System.out.println();
			for(int j=0; j < largoMapa; j++) {
				System.out.print(" "+matriz[i][j]);
			}
		}
		**/
		//Le manda la mision al Lider.
		lid.obtenerMision(mision);
		
		
	}
	
	
	
}
