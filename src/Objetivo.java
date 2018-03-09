/**
 * Clase Objetivo que contiene el tipo de mision con su descripcion
 * Tambien contiene la posicion de la bomba
 * @author Baldo Morales
 * @author Kevin Araya
 * @author Joaquin Solano
 */
public class Objetivo {
	// Variable que dice el tipo de objetivo
	private String nombre;
	
	// Variable que contiene la descripcion del problema
	private String descripcion;
	
	// Variables de la posicion de la bomba
	private int x; // Posicionado en la fila
	private int y; // Posicionado en la columna
	
	/**
	 * Constructor de la clase Objetivo, la cual recibe por parametros la posicion de la bomba
	 * @param x indica la fila de la bomba
	 * @param y indica la columna de la bomba
	 */
	public Objetivo(int x, int y) {
		this.x = x;
		this.y = y;
		this.nombre = "Desactivar la bomba.";
		this.descripcion = "Se le mandara la mision al lider, y el lider se encargara de distribuir las zonas y ordenar a desactivar la bomba.";
	}
	
	
	/**
	 * Metodo para obtener la coordenada X
	 * @return x que es la coordenada de la fila
	 */
	public int getCoorX() {
		return this.x;
	}
	
	/**
	 * Metodo para obtener la coordenada y
	 * @return y que es la coordenada de la columna
	 */
	public int getCoorY() {
		return this.y;
	}
	
	/**
	 * Obtener el tipo de objetivo de la mision
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Obtener la descripcion de la mision
	 * @return
	 */
	public String getDescripcion() {
		return descripcion;
	}

}