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
		
		lid.obtenerMision(mision);
		
	}
	
	
	
}
