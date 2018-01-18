/**
 * Clase Mapa que es
 * @author Baldo Morales
 * @author Kevin Araya
 * @author Joaquin Solano
 */
public class Mapa {

	// Variable Filas que especifica el largo del Mapa
	private int filas;
	
	// Variable Columnas que especifica el ancho del Mapa
	private int columnas;
	
	// Lista de todas las Zonas que contiene un Mapa
	private Zona[] listaZonas;
	
	/**
	 * Constructor de la clase Mapa
	 * @param filas que es el largo del mapa
	 * @param columnas que es el ancho del mapa 
	 * @param cantidadZonas que especifica la cantidad de zonas que tiene el mapa
	 */
	public Mapa(int filas, int columnas, int cantidadZonas) {
		
		this.filas = filas;
		this.columnas = columnas;
		this.listaZonas = new Zona[cantidadZonas];
		
	}

	/**
	 * Metodo obtener Fila del mapa
	 * @return filas de la clase
	 */
	public int getFilas() {
		return filas;
	}

	/**
	 * Metodo Cambiar Fila del mapa
	 * @param filas cambiado por filas ingresada por parametro
	 */
	public void setFilas(int filas) {
		this.filas = filas;
	}

	/**
	 * Metodo obtener Columna del mapa
	 * @return columnas de la clase
	 */
	public int getColumnas() {
		return columnas;
	}

	/**
	 * Metodo Cambiar Columna del mapa
	 * @param columnas cambiado por columnas ingresada por parametro
	 */
	public void setColumnas(int columnas) {
		this.columnas = columnas;
	}

	/**
	 * Metodo obtener Lista de listaZonas del mapa
	 * @return Lista listaZonas de la clase
	 */
	public Zona[] getListaZonas() {
		return listaZonas;
	}

	/**
	 * Metodo Cambiar la Lista de listaZonas del mapa
	 * @param listaZonas cambiado por listaZonas ingresada por parametro
	 */
	public void setListaZonas(Zona[] listaZonas) {
		this.listaZonas = listaZonas;
	}

	
}
