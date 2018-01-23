import java.io.IOException;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

/**
 * Clase Agente de una Unidad, que permite ayudar al lider con revisar zonas de mapas
 * e informar los estados.
 * @author Baldo Morales
 * @author Kevin Araya
 * @author Joaquin Solano
 */
public class AgenteUnidad extends Agent {
	// Variable nombre que contiene el nombre del agente.
	private String nombre;
	
	// Variable estado que contiene los 3 estados del agente "despejado", "encontrado" y "desactivado".
	private String estado;
	
	//Variables que guardan coordenadas de la bomba
	private int bombaX;
	private int bombaY;
	
	/**
	 * Setup que inicializa el agente Unidad.
	 */
	protected void setup() {
		//perimetro = Mapa.getInstancia().getMapa();
		nombre = this.getLocalName();
		//Se añade el servicio de disponibilidad de Unidad SWAT
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("unidad-swat");
		sd.setName("Unidad-SWAT-Disponible");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}catch(FIPAException fe) {
			fe.printStackTrace();
		}
		System.out.println("Unidad: "+nombre+" disponible para la mision.");
		addBehaviour(new revisarPerimetro());
	}
	/**
	 * Cuando el Agente manda un mensaje al irse
	 */
	protected void takeDown() {
		System.out.println("Unidad: "+nombre+" termina su servicio.");
	}
	/**
	 * Clase Revisar Perimetro que es un comportamiento
	 * ciclico que espera una solicitud del lider,
	 * revisa en una matriz si existe un objeto dentro de las casillas,
	 * cuando termine la evaluación procedera a notificar el estado.
	 * @author Baldo Morales
	 *
	 */
	private class revisarPerimetro extends CyclicBehaviour{
		public void action() {
			System.out.println("A " + nombre + " le llego un mensaje");
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			ACLMessage msg = myAgent.receive(mt);
			// Se verifica si el mensaje esta vacio.
			if(msg != null) {
				String coordenadas = msg.getContent();
				String[] partes = coordenadas.split(",");
				String zona = partes[0];
				int xInicial = Integer.parseInt(partes[1]);
				int yInicial = Integer.parseInt(partes[2]);
				int xFinal = Integer.parseInt(partes[3]);
				int yFinal = Integer.parseInt(partes[4]);
				// Se inicia el estado como despejado.
				estado = "despejado";
				
				for(int i = xInicial; i < xFinal ; i++) {
					for(int j = yInicial; j < yFinal; j++) {
						// En caso de encontrar un "1" dentro de la matriz, se cambia el estado a "encontrado" y se sale de inmediato.
						if(Mapa.getInstancia().getMapa()[i][j] == 1) {
							estado = "encontrado";
							bombaX = i;
							bombaY = j;
							System.out.println("Agente "+nombre+" reviso la "+zona + " ("+i+","+j+") y encontro la bomba");
							
							break;
						}
						System.out.println("Agente "+nombre+" reviso la zona ("+i+","+j+") y esta "+estado);
					}
					if(estado == "encontrado") break;
				}
				
				// Se inicia el comportamienteo Noticar Estado.
				addBehaviour(new notificarEstado());
				
			}else {
				block();
			}
			
		}
	} // Fin de la clase Revisar Perimetro
	
	/**
	 * Clase Notificar Estado que informa al lider con el String estado,
	 * solo se realiza una vez cada vez que se revisa el perimetro.
	 * @author Baldo Morales
	 *
	 */
	private class notificarEstado extends OneShotBehaviour{ 
		public void action() {
			System.out.println("Se procede a notificar");
			ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
			inform.addReceiver(new AID("Lider", AID.ISLOCALNAME));
			inform.setContent(estado);
			send(inform);
		}
	} // Fin de la clase Notificar Estado
	
	
}
