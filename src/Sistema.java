import java.util.Scanner;

public class Sistema {
	private Mision mision;
	private int largoMapa;
	private int anchoMapa;
	private int sectores;
	
	private AgenteLider lider;
	
	public Sistema(AgenteLider lid) {
		this.lider = lid;
		System.out.println("Sistema SWAT");
		Scanner entrada = new Scanner (System.in);
		System.out.println("inserte el largo del mapa:");
		largoMapa = entrada.nextInt();
		System.out.println("inserte el ancho del mapa:");
		anchoMapa = entrada.nextInt();
		System.out.println("inserte la cantidad de sectores del mapa:");
		sectores = entrada.nextInt();		
		mision = new Mision(largoMapa, anchoMapa, sectores);
		
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
		lid.obtenerMision(mision);
		
		
	}
	
	
	
}
