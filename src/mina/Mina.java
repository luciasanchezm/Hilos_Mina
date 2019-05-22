package mina;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.*;
import utils.*;

public class Mina extends JFrame implements ActionListener {
	private Vector <Pais> continenteEuropeo, continenteAsiatico;
	private Vector<Tonelada> minaToneladas;
	private int noPaisesEuropeos, noPaisesAsiaticos, totalToneladas, tonsEuropa, tonsAsia, tonsRegular, tonsBuena, tonsExcelente;
	private static int [] tonsPerContinent = new int[2];
	private JPanel [] viewPaisesEuropeos, viewPaisesAsiaticos;
	private JPanel panelPrincipal, viewEuropa, panelEuropa, viewAsia, panelAsia;
	private static JLabel lblTonsEuropa, lblTonsAsia;
	private JButton btnIniciar;
	
	public Mina() {
		super("MINA");
		initialize();
		createInterface();
	}
	
	public void initialize() {
		//Inicializar países
		continenteEuropeo = new Vector<Pais>();
		continenteAsiatico = new Vector<Pais>();
		minaToneladas = new Vector<Tonelada>();
		noPaisesEuropeos = Rutinas.nextInt(10,30);
		noPaisesAsiaticos = Rutinas.nextInt(5,7);
		viewPaisesEuropeos = new JPanel [noPaisesEuropeos];
		viewPaisesAsiaticos = new JPanel [noPaisesAsiaticos];
		panelPrincipal = new JPanel();
		panelEuropa = new JPanel();
		panelAsia = new JPanel();
		lblTonsEuropa = new JLabel("0");
		lblTonsAsia = new JLabel("0");
		//Inicializar toneladas
		totalToneladas = Rutinas.nextInt(100, 200);
		if(totalToneladas%2!=0)
			totalToneladas++;
		tonsEuropa = tonsAsia = (int)(totalToneladas/2);
		//Repartir toneladas por tipos
		tonsRegular = (int) (totalToneladas *  0.30);
		tonsBuena = (int) (totalToneladas *  0.60);
		tonsExcelente = totalToneladas - (tonsRegular + tonsBuena);
		
		//Llenar vectores
		for (int i = 0; i < noPaisesEuropeos; i++) {
			continenteEuropeo.addElement(new Pais(i+1, Pais.TYPE_EUROPE, tonsEuropa, minaToneladas));
			viewPaisesEuropeos[i] = continenteEuropeo.elementAt(i).getView();
			panelEuropa.add(viewPaisesEuropeos[i]);
		}
		for (int i = 0; i < noPaisesAsiaticos; i++) {
			continenteAsiatico.addElement(new Pais(i+1, Pais.TYPE_ASIA, tonsAsia, minaToneladas));
			viewPaisesAsiaticos[i] = continenteAsiatico.elementAt(i).getView();
			panelAsia.add(viewPaisesAsiaticos[i]);
		}
		for (int i = 0; i < tonsRegular; i++) 
			minaToneladas.addElement(new Tonelada(Tonelada.TYPE_REGULAR));
		for (int i = 0; i < tonsBuena; i++) 
			minaToneladas.addElement(new Tonelada(Tonelada.TYPE_BUENA));
		for (int i = 0; i < tonsExcelente; i++) 
			minaToneladas.addElement(new Tonelada(Tonelada.TYPE_EXCELENTE));
	}
	
	public static void addTotalTons(int type) {
		tonsPerContinent[type-1]++;
		lblTonsEuropa.setText(tonsPerContinent[0]+"");
		lblTonsAsia.setText(tonsPerContinent[1]+"");
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
		
		//VISTA DE EUROPA
		viewEuropa = new JPanel();
		viewEuropa.setLayout(new BorderLayout());
		viewEuropa.setPreferredSize(new Dimension(1200,900));
		JLabel labelEuropa = new JLabel("CONTINENTE EUROPEO");
		labelEuropa.setFont(new Font("Times new Roman", Font.BOLD, 20));
		labelEuropa.setIcon(Rutinas.AjustarImagen("Europa.png", 100, 100));
		JPanel panelTonsEurope = new JPanel();
		panelTonsEurope.add(new JLabel("Total de toneladas por continente: "));
		panelTonsEurope.add(lblTonsEuropa);
		viewEuropa.add(labelEuropa, BorderLayout.NORTH);
		viewEuropa.add(panelEuropa, BorderLayout.CENTER);
		viewEuropa.add(panelTonsEurope, BorderLayout.SOUTH);
		panelPrincipal.add(viewEuropa);
		
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
	}
}
