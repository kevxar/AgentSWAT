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
		addBehaviour(new respuestaInstancia());

	}
	/**
	 * Cuando el Agente manda un mensaje al irse
	 */
	protected void takeDown() {
		System.out.println("Unidad: "+nombre+" termina su servicio.");
	}

	private class respuestaInstancia extends CyclicBehaviour{

		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CONFIRM);
			ACLMessage msg = myAgent.receive(mt);
			if(msg!=null) {
				ACLMessage respuesta = msg.createReply();
				respuesta.setPerformative(ACLMessage.CONFIRM);
				respuesta.setContent("si");			
				myAgent.send(respuesta);
			}else {
				block();
			}
		}

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
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			ACLMessage msg = myAgent.receive(mt);
			// Se verifica si el mensaje esta vacio.
			if(msg != null) {
				
					String coordenadas = msg.getContent();
					System.out.println(nombre+" recibe "+coordenadas);
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
							//System.out.println("("+i+","+j+")->"+Mapa.getInstancia().getMapa()[i][j]);
							if(Mapa.getInstancia().getMapa()[j][i] == 1) {
									estado = "encontrado";
									bombaX = i;
									bombaY = j;
									System.out.println("Agente "+nombre+" reviso la "+zona + " ("+i+","+j+") y encontro la bomba");
									ACLMessage respuesta = msg.createReply();
									respuesta.setPerformative(ACLMessage.INFORM);
									respuesta.setContent(estado);
									myAgent.send(respuesta);
									break;
							}
							//System.out.println("Agente "+nombre+" reviso la "+zona+" ("+i+","+j+") y esta "+estado);
						}
						if(estado.equalsIgnoreCase("encontrado")) {
							break;
						}
					}
					System.out.println(nombre+ " termino de recorrer la " + zona);
					addBehaviour(new notificarEstado());
				
			}else {
				block();
			}
			// Se inicia el comportamienteo Noticar Estado.
		}
	} // Fin de la clase Revisar Perimetro

	/**
	 * Clase Notificar Estado que informa al lider con el String estado,
	 * solo se realiza una vez cada vez que se revisa el perimetro.
	 * @author Baldo Morales
	 *
	 */
	private class notificarEstado extends CyclicBehaviour{ 
		public void action() {
			System.out.println("Se procede a notificar");
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			//ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
			//inform.addReceiver(new AID("Lider", AID.ISLOCALNAME));
			//inform.setContent(estado);
			//send(inform);
			ACLMessage informacion = myAgent.receive(mt);
			if(informacion != null) {
				String coordenadas = informacion.getContent();
				String[] partes = coordenadas.split(",");
				String zona = partes[0];
				int xInicial = Integer.parseInt(partes[1]);
				int yInicial = Integer.parseInt(partes[2]);
				int xFinal = Integer.parseInt(partes[3]);
				int yFinal = Integer.parseInt(partes[4]);
				System.out.println("La bomba en la " + zona + " fue desactivada por la " +nombre+ " ." );
				ACLMessage respuesta = informacion.createReply();
				respuesta.setPerformative(ACLMessage.INFORM);
				estado = "desactivado";
				respuesta.setContent(estado);
				myAgent.send(respuesta);
				doDelete();
			}else {
				block();
			}
		}
	} // Fin de la clase Notificar Estado
}
