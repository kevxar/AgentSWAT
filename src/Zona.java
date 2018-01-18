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
	
	public void rellenarZona() {
		System.out.println("Hola");
		for(int i = 0; i < this.x ; i++) {
			for(int j = 0; j < this.y ; j++) {
				this.zona[i][j] = 0;
			}
		}
	}
}
