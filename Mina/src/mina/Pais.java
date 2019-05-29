package mina;

import java.awt.*;
import java.util.Vector;
import javax.swing.*;
import utils.*;

public class Pais extends Thread {
	
	public static final int TYPE_EUROPE = 1;
	public static final int TYPE_ASIA = 2;
	
	private JPanel view, vistaInfo;
	private JLabel lblPeticiones, lblTonsRegular, lblTonsBuena, lblTonsExcelente, lblTotalTons;
	
	private static Semaforo semaphore;
	private int noPais, type, peticiones;
	private Vector<Vector<Tonelada>> tons;
	private static int tonsEuropa, tonsAsia, tonsAvaiablePerContinent;
	private Vector<Tonelada>[] toneladasMina;
	
	public Pais(int noPais, int type, int tonsAvaiablePerContinent, Vector<Tonelada>[] toneladas) {
		semaphore = new Semaforo(1);
		peticiones = 0;
		
		this.noPais = noPais;
		this.type = type;
		this.tonsAvaiablePerContinent = tonsAvaiablePerContinent;
		this.toneladasMina = toneladas;
		
		tons = new Vector<Vector<Tonelada>>();
		for (int i = 0; i < 3; i++)
			tons.add(new Vector<Tonelada>());
		
		view = new JPanel();
		vistaInfo = new JPanel();
		lblPeticiones = new JLabel(peticiones+"");
		lblTonsRegular = new JLabel(tons.elementAt(0).size()+"");
		lblTonsBuena = new JLabel(tons.elementAt(1).size()+"");
		lblTonsExcelente = new JLabel(tons.elementAt(2).size()+"");
		lblTotalTons = new JLabel("0");
		createViews();
	}
	
	public void run() {
		int petitionType, cantidad;
		Tonelada currentTon;
		
		while(continuar()) {
			petitionType = Rutinas.nextInt(1,3);
			cantidad = Rutinas.nextInt(1,2);
			
			for (int i = 0; i < toneladasMina[petitionType-1].size(); i++) {
				currentTon = toneladasMina[petitionType-1].elementAt(i);
				//Verificar si puede tomar la tonelada
				if(currentTon.getType() == petitionType) {
					Mina.getSemaphore(petitionType).Espera();
					if(currentTon.isTaken) {
						Mina.getSemaphore(petitionType).Libera();
						continue;
					}
					currentTon.isTaken = true;
					Mina.getSemaphore(petitionType).Libera();
					tons.elementAt(petitionType-1).add(currentTon);
					cantidad--;
					addTons();
				}
				if(cantidad == 0)
					break;
			}
			
			peticiones++;
			lblPeticiones.setText(peticiones+"");
			lblTonsRegular.setText(tons.elementAt(0).size()+"");
			lblTonsBuena.setText(tons.elementAt(1).size()+"");
			lblTonsExcelente.setText(tons.elementAt(2).size()+"");
			lblTotalTons.setText(tons.elementAt(0).size() + tons.elementAt(1).size() + tons.elementAt(2).size() + "");
			try {		this.sleep(Rutinas.nextInt(1000,2000));		} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	public void addTons() {
		Mina.addTotalTons(type);
		if(type == this.TYPE_EUROPE) {
			semaphore.Espera();
			tonsEuropa++;
			semaphore.Libera();
			return;
		}
		semaphore.Espera();
		tonsAsia++;
		semaphore.Libera();
	}
	
	private boolean continuar() {
		boolean result;
		if (type == this.TYPE_EUROPE) {
            semaphore.Espera();
            result = tonsEuropa < this.tonsAvaiablePerContinent;
            semaphore.Libera();
            return result;
        }
        semaphore.Espera();
        result = tonsAsia < this.tonsAvaiablePerContinent;
        semaphore.Libera();
        return result;
	}
	
	public void createViews() {
		view.setLayout(new BorderLayout());
		view.add(new JLabel("PAÍS #" + noPais), BorderLayout.NORTH);
		vistaInfo.setLayout(new GridLayout(0,2,0,0));
		
		vistaInfo.add(new JLabel("Peticiones: "));
		vistaInfo.add(lblPeticiones);
		
		vistaInfo.add(new JLabel("Regular: "));
		vistaInfo.add(lblTonsRegular);
		
		vistaInfo.add(new JLabel("Buena: "));
		vistaInfo.add(lblTonsBuena);
		
		vistaInfo.add(new JLabel("Excelente: "));
		vistaInfo.add(lblTonsExcelente);
		
		vistaInfo.add(new JLabel("Total de toneladas: "));
		vistaInfo.add(lblTotalTons);
		
		vistaInfo.add(new JLabel());vistaInfo.add(new JLabel());vistaInfo.add(new JLabel());
		view.add(vistaInfo, BorderLayout.CENTER);
	}
	
	public JPanel getView() {
		return view;
	}
}