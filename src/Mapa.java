/**
 * Clase Mapa que contiene una matriz y un listado de coordenadas de la zonas
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
	private Zona[] listaCoordenadas;
	
	// Matriz del mapa en donde se posicionaran la bomba
	private int[][] mapa;
	/**
	 * Constructor de la clase Mapa
	 * @param filas que es el largo del mapa
	 * @param columnas que es el ancho del mapa 
	 * @param listaCoordenadas que especifica la cantidad de zonas que tiene el mapa
	 * @param mapa
	 */
	public Mapa(int filas, int columnas, int cantidadZonas) {
		
		this.filas = filas;
		this.columnas = columnas;
		this.listaCoordenadas = new Zona[cantidadZonas];
		this.mapa = new int[filas][columnas];
	
	}
	
	/**
	 * Metodo que crea un mapa rellenando la matriz
	 */
	public void rellenarMapa() {
		//Formula secreta para rellenar el mapa
	}
	
	/**
	 * Metodo que posiciona la bombar en el mapa
	 */
	public boolean colocarBomba(int XBomba, int YBomba) {
		//Otra Formula secreta para rellenar el mapa
		boolean colocado = false;
		//Si se pudo colocar la bombar, entonces colocado igual "true"
		return colocado;
	}
	
	/**
	 * Metodo Dividir en Zonas que se encarga de
	 * agregar Coordenas a la lista de Zonas
	 */
	public void dividirEnZonas() {
		//Formula Secreta para agregar Zonas
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
	 * Metodo obtener listado de coordenadas
	 * @return listaCoordenadas de la clase
	 */
	public Zona[] getListaCoordenadas() {
		return listaCoordenadas;
	}

	/**
	 * Metodo Cambiar Columna del mapa
	 * @param listaCoordenadas cambiado por listaCoordenadas ingresada por parametro
	 */
	public void setListaCoordenadas(Zona[] listaCoordenadas) {
		this.listaCoordenadas = listaCoordenadas;
	}

	/**
	 * Metodo obtener matriz mapa
	 * @return mapa matriz de la clase
	 */
	public int[][] getMapa() {
		return mapa;
	}

	/**
	 * Metodo Cambiar mapa
	 * @param mapa cambiado por una matriz mapa ingresada por parametro
	 */
	public void setMapa(int[][] mapa) {
		this.mapa = mapa;
	}
	
}
