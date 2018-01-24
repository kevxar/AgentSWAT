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
					
					ACLMessage req = new ACLMessage(ACLMessage.CONFIRM);
					req.setContent("Listo");
					req.addReceiver(new AID ("unidad"+(cont+1),AID.ISLOCALNAME));
					req.setConversationId("iniciacion");
		    		req.setReplyWith("si");
		    		myAgent.send(req);
		    		respondio = true;
				}
				
	    		
	    		ACLMessage respuesta = myAgent.receive(mt);
	    		if(respuesta!=null) {
	    			cont++;
	    			respondio = false;
				}
	    		if(cont == listaCoordenadas.length) {	
	    			paso = 2;
	    		}
	    		break;
			case 2:
				DFAgentDescription template = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setType("unidad-swat");
				template.addServices(sd);
				
					try {
	    				DFAgentDescription[] result = DFService.search(myAgent, template);
	    				System.out.println("Se encontraron " + result.length + " agentes unidades:");
	    				listaUnidades = new AID[result.length];
	    				for(int i = 0; i < result.length ; i++) {
	    					listaUnidades[i] = result[i].getName();
	    				}
	    			} catch (FIPAException e) {

	    				e.printStackTrace();
	    			}
	    			// Perform the request
	    		myAgent.addBehaviour(new DistribuirUnidades());
				
				paso = 3;
				break;
			}
	
		}
		@Override
		public boolean done() {
			return (paso == 3);
		}
		
	}
	

	
	private class DistribuirUnidades extends OneShotBehaviour {
		private int contadorRespuestas = 0; // The counter of replies from seller agents
		private MessageTemplate mt; // The template to receive replies
		
		
		public void action() {
			
			System.out.println("Comienzo a distribuir las unidades:");
			// Envia el request a todas las unidades
			ACLMessage req = new ACLMessage(ACLMessage.REQUEST);
			
				for (int i = 0; i < listaCoordenadas.length; ++i) {
					System.out.println("A unidad numero " + i +":" );
					
						//Agrego la unidad a la lista de unidades
						req.addReceiver(listaUnidades[i]);
						
						String coordenadas = listaCoordenadas[i].getNombre()+","+listaCoordenadas[i].getZonaXInicial()+","+listaCoordenadas[i].getZonaYInicial()+","+listaCoordenadas[i].getZonaXFinal()+","+listaCoordenadas[i].getZonaYFinal()+",R";
						System.out.println(coordenadas);
						//Seteo el contenido del mensaje con la zona 
						req.setContent(coordenadas);
						
						req.setConversationId("envio-zona");
						req.setReplyWith("request"+System.currentTimeMillis()); // Valor unico
						myAgent.send(req);

				}
				
				// Preparo el template para obtener respuesta
				mt = MessageTemplate.and(MessageTemplate.MatchConversationId("envio-zona"),
						MessageTemplate.MatchInReplyTo(req.getReplyWith()));
				addBehaviour(new ReunirUnidad());
		}		
			
		
		
	}
	
	private class ReunirUnidad extends CyclicBehaviour {
		private int cont = 0;
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			ACLMessage respuesta = myAgent.receive(mt);
			System.out.println("AQUI");
			if(respuesta != null) {
				// Se recibe el mensaje y se procesa
				String estado = respuesta.getContent();
				int posicion=0;
				if(estado.equalsIgnoreCase("encontrado")) {
					System.out.println("encontrado");
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
						//if(i != posicion) {
							System.out.println("mandar");
							
							ACLMessage reply = respuesta.createReply();
							reply.setPerformative(ACLMessage.INFORM);
							reply.addReceiver(listaUnidades[i]);
							String coordenadas = listaCoordenadas[posicion].getNombre()+","+listaCoordenadas[posicion].getZonaXInicial()+","+listaCoordenadas[posicion].getZonaYInicial()+","+listaCoordenadas[posicion].getZonaXFinal()+","+listaCoordenadas[posicion].getZonaYFinal()+",D";
							System.out.println(coordenadas);
							//Seteo el contenido del mensaje con la zona 
							reply.setContent(coordenadas);
							
							reply.setConversationId("envio-zona");
							reply.setReplyWith("request"+System.currentTimeMillis()); // Valor unico
							myAgent.send(reply);
						
						//}
					}
					
				}else if(estado.equalsIgnoreCase("desactivado")) {
					System.out.println("desactivado");
					cont++;
					if(cont == listaCoordenadas.length) {
						System.out.println("listo");
						addBehaviour(new ReportarMision());
					}
				}else {
					System.out.println("Zona limpia");
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
