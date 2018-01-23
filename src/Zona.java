/**
 * @author Baldo Morales
 * @author Kevin Araya
 * @author Joaquin Solano
 */
public class Zona {
	private String nombre;
	private int xInicial;
	private int xFinal;
	private int yInicial;
	private int yFinal;
	
	public Zona(String nombre,int xInicial,int xFinal, int yInicial, int yFinal) {
		this.nombre = nombre;
		this.xInicial = xInicial;
		this.xFinal = xFinal;
		this.yInicial = yInicial;
		this.yFinal = yFinal;
	}
	
	/**
	 * Metodo que retorna el largo de la zona
	 * @return x
	 */
	public int getZonaXInicial() {
		return this.xInicial;
	}

	/**
	 * Metodo que retorna el largo de la zona
	 * @return x
	 */
	public int getZonaXFinal() {
		return this.xFinal;
	}
	/**
	 * Metodo que retorna el alto de la zona
	 * @return y
	 */
	public int getZonaYInicial() {
		return this.yInicial;
	}
	/**
	 * Metodo que retorna el largo de la zona
	 * @return x
	 */
	public int getZonaYFinal() {
		return this.yFinal;
	}
	/**
	 * Metodo que da un nombre a la zona
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * Metodo que devuelve el nombre de la zona
	 * @return
	 */
	public String getNombre() {
		return this.nombre;
	}
}
