

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SwatGui extends JFrame {
	private AgenteLider aLider;
	private AgenteUnidad aUnidad;
	private Mision mision;
	private JTextField titleField;
	
	SwatGui(AgenteLider lider){
		aLider=lider;
		JPanel p= new JPanel();
		p.setLayout(new GridLayout(1,1));
		p.add(new JLabel("S.W.A.T AGENT TEAM"));
		
		getContentPane().add(p, BorderLayout.CENTER);
		
		
		//Hace que el agente se termine cuando el usuario cierre 
		//la ventana de la GUI utilizando el boton que se encuentra
		//en la esquina superior derecha	
				addWindowListener(new	WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						aLider.doDelete();
					}
				} );
				
				setResizable(false);
	}
	
	
	public void showGui() {
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int)screenSize.getWidth()/2;
		int centerY = (int)screenSize.getHeight()/2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		super.setVisible(true);

	}	
}
