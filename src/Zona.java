/**
 * Clase que contiene la posicion inicial y final de una zona del mapa
 * @author Baldo Morales
 * @author Kevin Araya
 * @author Joaquin Solano
 */
public class Zona {
	// Variable que identifica la zona con un nombre
	private String nombre;
	
	// Posiciones inicial y final de las filas
	private int xInicial;
	private int xFinal;
	
	// Posiciones inicial y final de las columnas
	private int yInicial;
	private int yFinal;
	
	/**
	 * Constructor de la clase Zona, se ingresa por parametros el nombre y las posiciones inicial y final de una zona
	 * @param nombre identificador de la zona
	 * @param xInicial fila inicial
	 * @param xFinal fila final
	 * @param yInicial columna inicial
	 * @param yFinal fila final
	 */
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
	 * @return nombre
	 */
	public String getNombre() {
		return this.nombre;
	}
}
