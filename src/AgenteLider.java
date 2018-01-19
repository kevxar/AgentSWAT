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
	
	protected void setup() {
		System.out.println("Holi soy el lider");
		sistema = new Sistema(this);
		
	}
	
	public void obtenerMision(Mision mis) {
		addBehaviour(new OneShotBehaviour() {
			public void action() {
				mision = mis;				
				System.out.println("He obtenido la misión!");
			}
	});
	}
	
	
	
	protected void takeDown() {

		System.out.println(getAID().getLocalName() +" se despide.");
	}
}
