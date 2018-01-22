import java.io.IOException;
import java.io.Serializable;

import javax.swing.JOptionPane;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.ACLMessage;

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
	private String nombre;
	private AID[] listaUnidades;
	
	protected void setup() {
		System.out.println("Hola, soy el lider");
		sistema = new Sistema(this);
		gui = new SwatGui(this);
		gui.showGui();
		
		addBehaviour(new ReclutarUnidad());
		
		
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
	
	
	private class ReclutarUnidad extends OneShotBehaviour{

		public void action() {
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("unidad-swat");
			template.addServices(sd);
			
			try {
				DFAgentDescription[] result = DFService.search(myAgent, template); 
				System.out.println("Se encontraron " + result.length + " agentes unidades:");
				
				//Pregunto si se necesitan mas unidades aparte de las que ya hay
				if(result.length< listaCoordenadas.length) {
					int diferencia = listaCoordenadas.length-result.length ;
					listaUnidades = new AID[result.length+ diferencia];
				}else {
					listaUnidades = new AID[result.length];
				}
				
				int i;
				for (i=0; i < result.length; ++i) {
					listaUnidades[i] = result[i].getName();
					System.out.println(listaUnidades[i].getName());
				}
				
				for(int j=i;j<listaUnidades.length;j++) {
					AID nuevo = new AID("unidad"+(j+1),AID.ISLOCALNAME);
					listaUnidades[j] = nuevo;
				}
				
				
				
			}
			catch (FIPAException fe) {
				fe.printStackTrace();
			}

			// Perform the request
			myAgent.addBehaviour(new DistribuirUnidades());
			
		}
		
	}
	
	private class DistribuirUnidades extends OneShotBehaviour {
		private int contadorRespuestas = 0; // The counter of replies from seller agents
		private MessageTemplate mt; // The template to receive replies
				
		public void action() {
			
				// Envia el request a todas las unidades
				ACLMessage req = new ACLMessage(ACLMessage.REQUEST);
				
					for (int i = 0; i < listaCoordenadas.length; ++i) {
						
						//Consulta si la lista de zonas es menor a la lista de unidades
						if(i < listaUnidades.length) {
							//Agrego la unidad a la lista de unidades
							req.addReceiver(listaUnidades[i]);
							
							req.setConversationId("envio-zona");
							req.setReplyWith("request"+System.currentTimeMillis()); // Valor unico
							myAgent.send(req);
						}else {
								
							req.addReceiver(new AID("unidad"+(i+1),AID.ISLOCALNAME));
							//Seteo el contenido del mensaje con la zona 
							try {
								req.setContentObject((Serializable) listaCoordenadas[i]);
							} catch (IOException e) {
								e.printStackTrace();
							}
							
							req.setConversationId("envio-zona");
							req.setReplyWith("request"+System.currentTimeMillis()); // Valor unico
							myAgent.send(req);
	
					}
					
				}
					// Preparo el template para obtener respuesta
					mt = MessageTemplate.and(MessageTemplate.MatchConversationId("envio-zona"),
							MessageTemplate.MatchInReplyTo(req.getReplyWith()));
					addBehaviour(new ReunirUnidad());
		}		
			
		
		
	}
	
	private class ReunirUnidad extends CyclicBehaviour {

		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			ACLMessage respuesta = myAgent.receive(mt);
			
			if(respuesta != null) {
				// Se recibe el mensaje y se procesa
				String estado = respuesta.getContent();
				int posicion=0;
				if(estado.equalsIgnoreCase("Encontrado")) {
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
						if(i != posicion) {
						respuesta.addReceiver(listaUnidades[i]);
						
						//Seteo el contenido del mensaje con la zona 
						try {
							respuesta.setContentObject((Serializable) listaCoordenadas[posicion]);
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						respuesta.setConversationId("envio-zona");
						respuesta.setReplyWith("request"+System.currentTimeMillis()); // Valor unico
						myAgent.send(respuesta);
						
						}
					}
					
				}else if(estado.equalsIgnoreCase("Desactivado")) {
					addBehaviour(new ReportarMision());
				}else {
					block();
				}
					
			}
			}
		}
	
	private class ReportarMision extends OneShotBehaviour{

		public void action() {
			JOptionPane.showMessageDialog(null,"La misión ha terminado :D! ");
			doDelete();
		}
		
	}
		
	protected void takeDown() {

		System.out.println(getAID().getLocalName() +" se despide.");
	}

}
