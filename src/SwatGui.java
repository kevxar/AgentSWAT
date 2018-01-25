

import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SwatGui extends JFrame {
	private AgenteLider aLider;
	private JTextField titleField;
	private DefaultListModel listModel;
	private JList list;
	private static final String COLS = "012345678";
	private Image[] iconos = new Image[2];
	JButton[][] espacioMapa;
	private JPanel mapaBase;
	
	SwatGui(AgenteLider lider){
		aLider=lider;
		
	}	
		

	public void inicializarGui() {
		
		

		JPanel gui= new JPanel(new GridLayout(0,2));
		gui.setBorder(new EmptyBorder(15,15,15,15));
		
		gui.add(new JLabel("S.W.A.T AGENT TEAM"), BorderLayout.LINE_START);		
		
		
		getContentPane().add(gui);
		
	
		
		mapaBase.setBorder(new LineBorder(Color.BLACK));	
		gui.add(mapaBase);
		
		//Creo los iconos para los cuadrados
		Insets buttonMargin = new Insets(0,0,0,0);
		for (int i = 0; i < espacioMapa.length; i++) {
            for (int j = 0; j < espacioMapa[i].length; j++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                // our chess pieces are 64x64 px in size, so we'll
                // 'fill this in' using a transparent icon..
                ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB_PRE));
                b.setIcon(icon);
                if ((j % 2 == 1 && i % 2 == 1)//) {
                        || (j % 2 == 0 && i % 2 == 0)) {
                    b.setBackground(Color.WHITE);
                } else {
                    b.setBackground(Color.lightGray);
                }
                espacioMapa[j][i] = b;
            }
        }
		

		

        
		for (int i = 0; i < espacioMapa.length; i++) {
            for(int j=0;j<espacioMapa.length;j++) {
            	mapaBase.add(espacioMapa[j][i]);  
            }
        }
		
		cargarImagen();
      
		//PRUEBAAAAA
		
		
		//Hace que el agente se termine cuando el usuario cierre 
		//la ventana de la GUI utilizando el boton que se encuentra
		//en la esquina superior derecha	
		addWindowListener(new	WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				aLider.doDelete();
			}
		});
				

	//Seteando la ventana principal
	
	pack();
	setMinimumSize(gui.getSize());
	setResizable(true);
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int centerX = (int)screenSize.getWidth()/2;
	int centerY = (int)screenSize.getHeight()/2;
	setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
	setVisible(true);
	}	
	
	
	public void setMapa(int largo, int ancho) {
		mapaBase = new JPanel(new GridLayout(0,largo));
		espacioMapa = new JButton[ancho][largo];
		
	}
	public void dibujarBomba(int x, int y) {
		espacioMapa[x][y].setIcon(new ImageIcon(iconos[1]));
	}
	
	public void dibujarSoldado(int x, int y) {
		espacioMapa[x][y].setIcon(new ImageIcon(iconos[0]));
		
	}
	
	private final void cargarImagen() {
		BufferedImage bomba = new BufferedImage(64,64, BufferedImage.TYPE_INT_RGB);
		BufferedImage soldado = new BufferedImage(64,64, BufferedImage.TYPE_INT_RGB);
		Graphics b = bomba.getGraphics();
		Graphics s = soldado.getGraphics();
		s.drawRect(0, 0, 64, 64);
		s.setColor(Color.green);
		s.fillRect(0, 0, 64, 64);

		b.drawOval(0, 0, 64, 64);
		b.setColor(Color.red);
		b.fillOval(0, 0, 64, 64);
		
		iconos[0] = soldado;
		iconos[1] = bomba;
		espacioMapa[1][1].setIcon(new ImageIcon(soldado));
		espacioMapa[3][3].setIcon(new ImageIcon(bomba));
	}
	
}
