import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

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
		rellenarMapa();
		dividirEnZonas();
	}
	
	/**
	 * Metodo que crea un mapa rellenando la matriz
	 */
	private void rellenarMapa() {
		for(int i = 0; i < this.filas ; i++) {
			for(int j = 0 ;  j < this.columnas ; j++) {
				this.mapa[i][j] = 0;
			}
		}
	}
	
	/**
	 * Metodo que posiciona la bombar en el mapa
	 */
	public boolean colocarBomba(int XBomba, int YBomba) {

		if(this.mapa[XBomba][YBomba] != 0) {
			return false;
		}
		this.mapa[XBomba][YBomba] = 1; 
		return true;
	}
	
	/**
	 * Metodo Dividir en Zonas que se encarga de
	 * agregar Coordenas a la lista de Zonas
	 */
	private void dividirEnZonas() {
		Queue<Zona> cola = new LinkedList<Zona>();
		int cantidadHabitacionesTotales = 1;
		Zona zona = new Zona("1",0,columnas,0,filas);
		cola.add(zona);
		int cont = 0; int pos = 1; int fina = 1;
		boolean esColumna = true;
		while(cantidadHabitacionesTotales != listaCoordenadas.length) {
			Zona zona2 = cola.poll();
			if(esColumna) {
				int mitad = (zona2.getZonaXFinal()-zona2.getZonaXInicial())/2;
				Zona Aux1 = new Zona("2",
						zona2.getZonaXInicial(),
						zona2.getZonaXFinal()-mitad,
						zona2.getZonaYInicial(),
						zona2.getZonaYFinal());
				Zona Aux2 = new Zona("3",
						zona2.getZonaXInicial()+mitad+1,
						zona2.getZonaXFinal(),
						zona2.getZonaYInicial(),
						zona2.getZonaYFinal());
				if(pos>=fina) {
					esColumna = false;
					cont++;
					fina = (int) Math.pow(2, cont);
				} else {
					pos++;
				}
				
				cola.add(Aux1);
				cola.add(Aux2);
				cantidadHabitacionesTotales++;
			} else {
				int mitad = (zona2.getZonaYFinal()-zona2.getZonaYInicial())/2;
				Zona Aux1 = new Zona("4",
						zona2.getZonaXInicial(),
						zona2.getZonaXFinal(),
						zona2.getZonaYInicial(),
						zona2.getZonaYFinal()-mitad);
				Zona Aux2 = new Zona("5",
						zona2.getZonaXInicial(),
						zona2.getZonaXFinal(),
						zona2.getZonaYInicial()+mitad+1,
						zona2.getZonaYFinal());
				if(pos>=fina) {
					esColumna = false;
					cont++;
					fina = (int) Math.pow(2, cont);
				} else {
					pos++;
				}
				cola.add(Aux1);
				cola.add(Aux2);
				cantidadHabitacionesTotales++;
			}
		}
		for(int i = 0; i < this.listaCoordenadas.length; i++) {
			listaCoordenadas[i] = cola.poll();
		}
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
