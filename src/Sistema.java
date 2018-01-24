import java.util.Scanner;

public class Sistema {
	private Mision mision;
	private int largoMapa;
	private int anchoMapa;
	private int sectores;
	
	private AgenteLider lider;
	
	public Sistema(AgenteLider lid) {
		this.lider = lid;
		System.out.println("Iniciando Sistema SWAT");
		Scanner entrada = new Scanner (System.in);
		System.out.println("Inserte el largo del mapa:");
		largoMapa = entrada.nextInt();
		System.out.println("Inserte el ancho del mapa:");
		anchoMapa = entrada.nextInt();
		System.out.println("Inserte la cantidad de sectores del mapa:");
		sectores = entrada.nextInt();		
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
