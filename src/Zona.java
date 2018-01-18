/**
 * @author Baldo Morales
 * @author Kevin Araya
 * @author Joaquin Solano
 */
public class Zona {
	private int[][] zona;
	private int x;
	private int y;
	
	public Zona(int x, int y) {
		this.zona = new int[x][y];
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Rellena la matriz con 0, este representa el suelo.
	 * 1 representa la bomba
	 */
	public void rellenarZona() {
		System.out.println("Hola");
		for(int i = 0; i < this.x ; i++) {
			for(int j = 0; j < this.y ; j++) {
				this.zona[i][j] = 0;
			}
		}
	}
	
	/**
	 * Cambia la casilla de suelo a bomba, dependiendo de las coordenadas del objetivo.
	 * @param objetivo
	 */
	public void insertarObjetivo(Objetivo objetivo) {
		this.zona[objetivo.getCoorX()][objetivo.getCoorY()] = 1;
	}
	
	/**
	 * Metodo que retorna el largo de la zona
	 * @return x
	 */
	public int getZonaX() {
		return this.x;
	}
	
	/**
	 * Metodo que retorna el alto de la zona
	 * @return y
	 */
	public int getZonaY() {
		return this.y;
	}
}
