package mina;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.*;
import utils.*;

public class Mina extends JFrame implements ActionListener {
	private Vector <Pais> continenteEuropeo, continenteAsiatico;
	private Vector<Tonelada> toneladas [];			//LALALALALALA
	private int totalToneladas;
	private static int [] tonsPerContinent = new int[2];
	private static Semaforo semaphores[];
	
	private JPanel [] viewPaisesEuropeos, viewPaisesAsiaticos;
	private JPanel panelPrincipal, viewEurope, panelEurope, viewAsia, panelAsia;
	private static JLabel lblTonsEurope, lblTonsAsia;
	private JButton btnIniciar;
	
	public Mina() {
		super("MINA");
		initialize();
		createInterface();
	}
	
	public void initialize() {
		//Variables declaration
		int noPaisesEuropeos, noPaisesAsiaticos, tonsEurope, tonsAsia, tonsRegular, tonsBuena, tonsExcelente;
		
		//Inicializar países
		continenteEuropeo = new Vector<Pais>();
		continenteAsiatico = new Vector<Pais>();
		toneladas = new Vector[3];
		for (int i = 0; i < toneladas.length; i++)
			toneladas[i] = new Vector<Tonelada>();
		
		noPaisesEuropeos = Rutinas.nextInt(10,30);
		noPaisesAsiaticos = Rutinas.nextInt(5,7);
		
		//Semaphores
		semaphores = new Semaforo[3];
		for (int i = 0; i < semaphores.length; i++)
			semaphores[i] = new Semaforo(1);
		
		//View
		viewPaisesEuropeos = new JPanel [noPaisesEuropeos];
		viewPaisesAsiaticos = new JPanel [noPaisesAsiaticos];
		panelPrincipal = new JPanel();
		panelEurope = new JPanel();
		panelAsia = new JPanel();
		lblTonsEurope = new JLabel("0");
		lblTonsAsia = new JLabel("0");
		
		//Inicializar toneladas
		totalToneladas = Rutinas.nextInt(100, 200);
		if(totalToneladas%2!=0)
			totalToneladas++;
		tonsEurope = tonsAsia = (int)(totalToneladas/2);
		//Repartir toneladas por tipos
		tonsRegular = (int) (totalToneladas *  0.30);
		tonsBuena = (int) (totalToneladas *  0.60);
		tonsExcelente = totalToneladas - (tonsRegular + tonsBuena);
		
		//Llenar vectores
		for (int i = 0; i < tonsRegular; i++) 
			toneladas[Tonelada.TYPE_REGULAR-1].addElement(new Tonelada(Tonelada.TYPE_REGULAR));
		for (int i = 0; i < tonsBuena; i++) 
			toneladas[Tonelada.TYPE_BUENA-1].addElement(new Tonelada(Tonelada.TYPE_BUENA));
		for (int i = 0; i < tonsExcelente; i++) 
			toneladas[Tonelada.TYPE_EXCELENTE-1].addElement(new Tonelada(Tonelada.TYPE_EXCELENTE));
		
		for (int i = 0; i < noPaisesEuropeos; i++) {
			continenteEuropeo.addElement(new Pais(i+1, Pais.TYPE_EUROPE, tonsEurope, toneladas));
			viewPaisesEuropeos[i] = continenteEuropeo.elementAt(i).getView();
			panelEurope.add(viewPaisesEuropeos[i]);
		}
		for (int i = 0; i < noPaisesAsiaticos; i++) {
			continenteAsiatico.addElement(new Pais(i+1, Pais.TYPE_ASIA, tonsAsia, toneladas));
			viewPaisesAsiaticos[i] = continenteAsiatico.elementAt(i).getView();
			panelAsia.add(viewPaisesAsiaticos[i]);
		}
	}
	
	public static void addTotalTons(int type) {
		tonsPerContinent[type-1]++;
		lblTonsEurope.setText(tonsPerContinent[0]+"");
		lblTonsAsia.setText(tonsPerContinent[1]+"");
	}
	
	public static Semaforo getSemaphore(int type) {
		return semaphores[type-1];
	}
	
	public void createInterface() {
		setSize(2000, 1000);
		setExtendedState(MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		//VISTA ENCABEZADO
		JLabel imagen = new JLabel();
		imagen.setIcon(Rutinas.AjustarImagen("Continentes.png", 150, 50));
		JPanel panelNorth = new JPanel();
		JLabel lblNombre = new JLabel("MINA AFRICANA");
		lblNombre.setFont(new Font("Times new Roman", Font.BOLD, 40));
		panelNorth.add(lblNombre);
		panelNorth.add(imagen);
		JLabel labelNorth = new JLabel("Total de toneladas en la mina: " + totalToneladas);
		labelNorth.setFont(new Font("Times new Roman", Font.BOLD, 30));
		panelNorth.add(labelNorth);
		
		//VISTA BOTON
		btnIniciar = new JButton("INICIAR");
		btnIniciar.addActionListener(this);
		btnIniciar.setPreferredSize(new Dimension(100,30));
		JPanel panelBtn = new JPanel();
		panelBtn.add(btnIniciar);
		
		//VISTA DE Europe
		viewEurope = new JPanel();
		viewEurope.setLayout(new BorderLayout());
		viewEurope.setPreferredSize(new Dimension(1200,900));
		JLabel labelEurope = new JLabel("CONTINENTE EUROPEO");
		labelEurope.setFont(new Font("Times new Roman", Font.BOLD, 20));
		labelEurope.setIcon(Rutinas.AjustarImagen("Europe.png", 100, 100));
		JPanel panelTonsEurope = new JPanel();
		panelTonsEurope.add(new JLabel("Total de toneladas por continente: "));
		panelTonsEurope.add(lblTonsEurope);
		viewEurope.add(labelEurope, BorderLayout.NORTH);
		viewEurope.add(panelEurope, BorderLayout.CENTER);
		viewEurope.add(panelTonsEurope, BorderLayout.SOUTH);
		panelPrincipal.add(viewEurope);
		
		//VISTA DE ASIA
		viewAsia = new JPanel();
		viewAsia.setLayout(new BorderLayout());
		viewAsia.setPreferredSize(new Dimension(550,900));
		JLabel labelAsia = new JLabel("CONTINENTE ASIÁTICO");
		labelAsia.setFont(new Font("Times new Roman", Font.BOLD, 20));
		labelAsia.setIcon(Rutinas.AjustarImagen("Asia.png", 150, 150));
		JPanel panelTonsAsia = new JPanel();
		panelTonsAsia.add(new JLabel("Total de toneladas por continente: "));
		panelTonsAsia.add(lblTonsAsia);
		viewAsia.add(labelAsia, BorderLayout.NORTH);
		viewAsia.add(panelAsia, BorderLayout.CENTER);
		viewAsia.add(panelTonsAsia, BorderLayout.SOUTH);
		panelPrincipal.add(viewAsia);
		
		//AGREGAR AL FRAME
		add(panelNorth, BorderLayout.NORTH);
		add(panelPrincipal, BorderLayout.CENTER);
		add(panelBtn, BorderLayout.SOUTH);
		SwingUtilities.updateComponentTreeUI(this);
	}

	@Override
	public void actionPerformed(ActionEvent Evt) {
		btnIniciar.setEnabled(false);
		for (int i = 0; i < continenteEuropeo.size(); i++)
			continenteEuropeo.elementAt(i).start();
		for (int i = 0; i < continenteAsiatico.size(); i++)
			continenteAsiatico.elementAt(i).start();
//		verify();
//		JOptionPane.showMessageDialog(this, "Se han acabado las toneladas.");
	}
//	
//	private boolean isAlive() {
//		for (int i = 0; i < continenteEuropeo.size(); i++) 
//			if(continenteEuropeo.elementAt(i).isAlive())
//				return true;
//		for (int i = 0; i < continenteAsiatico.size(); i++) 
//			if(continenteAsiatico.elementAt(i).isAlive())
//				return true;
//		return false;
//	}
//	
//	private void verify() {
//		while(isAlive());
//	}
}
