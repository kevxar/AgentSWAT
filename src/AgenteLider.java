import java.io.IOException;

import java.io.Serializable;

import javax.swing.JOptionPane;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentController;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.core.behaviours.*;
import jade.lang.acl.*;
/**
 * @author Baldo Morales
 * @author Kevin Araya
 * @author Joaquin Solano
 */
public class AgenteLider extends Agent {
	//Objeto sistema el cual permitira obtener la mision
	private Sistema sistema;
	//Mision para el equipo, contiene mapa y objetivo
	private Mision mision;
	//Arreglo de zonas
	private Zona[] listaCoordenadas;
	//GUI para equipo SWAT
	private SwatGui gui;
	//Arreglo de agentesID que guardara las unidades disponibles.
	private AID[] listaUnidades;

	/**
	 * Setup que inicializa el agente Lider.
	 */
	protected void setup() {
		System.out.println("Hola, soy el lider");
		sistema = new Sistema(this);
		gui = new SwatGui(this);
		gui.showGui();
		addBehaviour(new ReclutarUnidad());
	}
	
	/**
	 * Metodo que obtiene la mision por parte de Sistema.
	 * Luego, imprime las coordenadas de cada Zona.
	 * @param mis
	 */
	public void obtenerMision(Mision mis) {
		addBehaviour(new OneShotBehaviour() {
			public void action() {
				mision = mis;	
				listaCoordenadas = mision.getMapa().getListaCoordenadas();
				System.out.println("He obtenido la misión!");
				System.out.println("Las coordenadas de las zonas son:");

				for(int i=0;i<listaCoordenadas.length;i++) {
					System.out.print(listaCoordenadas[i].getNombre()+ ": x inicial: "+ listaCoordenadas[i].getZonaXInicial()+"  ");
					System.out.print(" x final: "+ listaCoordenadas[i].getZonaXFinal()+"  ");
					System.out.print(" y inicial: "+ listaCoordenadas[i].getZonaYInicial()+"  ");
					System.out.print(" y final: "+ listaCoordenadas[i].getZonaYFinal()+"  ");
					System.out.println("");
				}
			}
		});
	}

	/**
	 * Metodo que instancia AgenteUnidad dependiendo de la cantidad de zonas que tiene el mapa.
	 * Luego,recluta a las unidades a traves del DF.
	 * Metodo ocupa un Switch el cual se repite hasta avanzar al otro paso.
	 */
	private class ReclutarUnidad extends Behaviour{
		private int cont = 0;
		private int paso = 0;
		private boolean respondio = false;
		public void action() {
			switch (paso) {
			case 0:
				for(int j = 0;j<listaCoordenadas.length;j++) {			
					try {					
						getContainerController().createNewAgent("unidad"+(j+1), "AgenteUnidad", null ).start();	
					}
					catch (Exception e){}
				}

				paso = 1;
				break;
			case 1:
				MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CONFIRM);
				if(!respondio) {
					//Envia un mensaje a las unidades recien creadas.
					ACLMessage req = new ACLMessage(ACLMessage.CONFIRM);
					req.setContent("Listo");
					req.addReceiver(new AID ("unidad"+(cont+1),AID.ISLOCALNAME));
					req.setConversationId("iniciacion");
					req.setReplyWith("si");
					myAgent.send(req);
					respondio = true;
				}
				//Si se recibio respuesta, se suma el contador de unidades instanciadas.
				ACLMessage respuesta = myAgent.receive(mt);
				if(respuesta!=null) {
					cont++;
					respondio = false;
				}
				//Si el contador es igual a la cantidad de zonas, se da inicio al paso 2.
				if(cont == listaCoordenadas.length) {	
					paso = 2;
				}
				break;
			case 2:
				//Busca en el DF a traves del tipo "unidad-swat" a todos los agentes que presten este servicio
				DFAgentDescription template = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setType("unidad-swat");
				template.addServices(sd);

				try {
					//Se agregan los DFservices encontrados al arreglo result.
					DFAgentDescription[] result = DFService.search(myAgent, template);
					System.out.println("Se encontraron " + result.length + " agentes unidades:");
					listaUnidades = new AID[result.length];
					//Se agregan los AID al arreglo listaUnidades.
					for(int i = 0; i < result.length ; i++) {
						listaUnidades[i] = result[i].getName();
					}
				} catch (FIPAException e) {
					e.printStackTrace();
				}
				// Se da paso al comportamiento de distribuir unidades.
				myAgent.addBehaviour(new DistribuirUnidades());
				paso = 3;
				break;
			}

		}
		//Metodo que da por finalizado este behaviour.
		public boolean done() {
			return (paso == 3);
		}
	}

	/**
	 * Metodo que esta encargado de dar las coordenadas de cada zona a cada agente.
	 * Se las envia a traves de un mensaje con performative REQUEST.
	 * Luego le da paso al comportamiento de ReunirUnidad.
	 */
	private class DistribuirUnidades extends OneShotBehaviour {
		private MessageTemplate mt; 

		public void action() {

			System.out.println("Comienzo a distribuir las unidades:");
			// Envia el request a todas las unidades
			ACLMessage req = new ACLMessage(ACLMessage.REQUEST);

			for (int i = 0; i < listaCoordenadas.length; ++i) {
				//Se limpia la lista de receptores, para evitar problemas de sincronizacion
				req.clearAllReceiver();
				//Agrego la unidad a la lista de unidades
				req.addReceiver(listaUnidades[i]);
				
				String coordenadas = listaCoordenadas[i].getNombre()+","+listaCoordenadas[i].getZonaXInicial()+","+listaCoordenadas[i].getZonaYInicial()+","+listaCoordenadas[i].getZonaXFinal()+","+listaCoordenadas[i].getZonaYFinal();
				//Seteo el contenido del mensaje con la zona 
				req.setContent(coordenadas);
				//Se ajustan algunas propiedades del mensaje
				req.setConversationId("envio-zona");
				req.setReplyWith("request"+System.currentTimeMillis()); // Valor unico
				//Se envia el mensaje
				myAgent.send(req);
			}
			// Preparo el template para obtener respuesta
			//mt = MessageTemplate.and(MessageTemplate.MatchConversationId("envio-zona"),
				//	MessageTemplate.MatchInReplyTo(req.getReplyWith()));
			addBehaviour(new ReunirUnidad());
		}		
	}

	/**
	 * Metodo que recibe un mensaje de performative inform, el cual le informa a las demas unidades
	 * que la bomba se encuentra en cierto mapa, entregando las coordenadas para desactivar el objeto.
	 *
	 */
	private class ReunirUnidad extends CyclicBehaviour {
		private int cont = 0;
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			ACLMessage respuesta = myAgent.receive(mt);
			if(respuesta != null) {
				// Se recibe el mensaje y se procesa
				String estado = respuesta.getContent();
				int posicion=0;
				if(estado.equalsIgnoreCase("encontrado")) {
					AID unidad = respuesta.getSender();
					String nombre = unidad.getLocalName();

					//Se busca el nombre de la unidad que envio el estado de la zona
					for(int i=0; i< listaUnidades.length;i++) {
						if(nombre.equalsIgnoreCase(listaUnidades[i].getLocalName())) {
							posicion = i;
							break;
						}
					}
					for(int i=0; i<listaUnidades.length;i++) {
						ACLMessage reply = respuesta.createReply();
						reply.setPerformative(ACLMessage.INFORM);
						reply.addReceiver(listaUnidades[i]);
						String coordenadas = listaCoordenadas[posicion].getNombre()+","+listaCoordenadas[posicion].getZonaXInicial()+","+listaCoordenadas[posicion].getZonaYInicial()+","+listaCoordenadas[posicion].getZonaXFinal()+","+listaCoordenadas[posicion].getZonaYFinal();
						//Seteo el contenido del mensaje con la zona 
						reply.setContent(coordenadas);

						reply.setConversationId("envio-zona");
						reply.setReplyWith("request"+System.currentTimeMillis()); // Valor unico
						myAgent.send(reply);
					}
				}else 
					if(estado.equalsIgnoreCase("desactivado")) {
						cont++;
						if(cont == listaCoordenadas.length) {
							addBehaviour(new ReportarMision());
						}	
					}
			}else {
				block();
			}
		}
	}

	/**
	 * Metodo que reporta el estado final de la mision y termina con la vida del lider.
	 *
	 */
	private class ReportarMision extends OneShotBehaviour{

		public void action() {
			JOptionPane.showMessageDialog(null,"La misión ha terminado :D! ");
			doDelete();
		}

	}

	/**
	 * Metodo correspondiende a Agente
	 * Se modifica para enviar un mensaje de despedida.
	 */
	protected void takeDown() {

		System.out.println(getAID().getLocalName() +" termina sus servicios, equipo SWAT se despide.");
	}

}
