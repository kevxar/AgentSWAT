/**
 * @author Baldo Morales
 * @author Kevin Araya
 * @author Joaquin Solano
 */
public class Mision {
	private Mapa mapa;
	private Objetivo objetivo;
	
	public Mision(Mapa mapa) {
		this.mapa = mapa;
		
		fijarObjetivo(this.objetivo);
	}
	
	private void fijarObjetivo(Objetivo obj) {
		//Se extraen las dimensiones del mapa
		int x = this.mapa.getFilas();
		int y = this.mapa.getColumnas();
		//Se definen dos numeros. 
		int randomX = 0;
		int randomY = 0;
		boolean posicionado = false;
		//Mientras el suelo seleccionado no sea 0
		while(posicionado != true) {
			//Se generan dos numeros Random entre 0 y las dimensiones
			randomX = (int) (Math.random() * (x - 0) + 0);
			randomY = (int) (Math.random() * (y - 0) + 0);
			posicionado = this.mapa.colocarBomba(randomX, randomY);
		}
	    
		this.objetivo = new Objetivo(randomX,randomY);	
	}
	
	
}
