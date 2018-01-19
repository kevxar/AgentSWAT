import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

/**
 * @author Baldo Morales
 * @author Kevin Araya
 * @author Joaquin Solano
 */
public class AgenteLider extends Agent {
	private Sistema sistema;
	private Mision mision;
	private Zona[] listaCoordenadas;
	private SwatGui gui;
	
	protected void setup() {
		System.out.println("Holi soy el lider");
		sistema = new Sistema(this);
		gui = new SwatGui(this);
		gui.showGui();
	}
	
	public void obtenerMision(Mision mis) {
		addBehaviour(new OneShotBehaviour() {
			public void action() {
				mision = mis;	
				listaCoordenadas = mision.getMapa().getListaCoordenadas();
				System.out.println("He obtenido la misión!");
				System.out.println("Las coordenadas de las zonas son:");
			
				for(int i=0;i<listaCoordenadas.length;i++) {
					System.out.print("Zona x inicial: "+ listaCoordenadas[i].getZonaXInicial()+"  ");
					System.out.print("Zona x final: "+ listaCoordenadas[i].getZonaXFinal()+"  ");
					System.out.print("Zona y inicial: "+ listaCoordenadas[i].getZonaYInicial()+"  ");
					System.out.print("Zona y final: "+ listaCoordenadas[i].getZonaYFinal()+"  ");
					System.out.println("");
				}
				
			}
	});
	}
	
	
	
	protected void takeDown() {

		System.out.println(getAID().getLocalName() +" se despide.");
	}
}
