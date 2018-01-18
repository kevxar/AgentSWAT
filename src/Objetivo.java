/**
 * @author Baldo Morales
 * @author Kevin Araya
 * @author Joaquin Solano
 */
public class Objetivo {
	private String nombre;
	private String descripcion;
	private int x;
	private int y;
	
	public Objetivo(int x, int y) {
		this.x = x;
		this.y = y;
		this.nombre = "Bomba";
		this.descripcion = "Soy la bomba destructora, enemigos alejaos y temed!";
	}
	
	public int getCoorX() {
		return this.x;
	}
	
	public int getCoorY() {
		return this.y;
	}
	
}
