package mina;

import utils.*;

public class Tonelada {
	private int type;
	private Semaforo semaphore;
	public boolean isTaken;
	public static final int TYPE_REGULAR = 1;
	public static final int TYPE_BUENA = 2;
	public static final int TYPE_EXCELENTE = 3;
	
	public Tonelada(int type) {
		this.type = type;
		this.semaphore = new Semaforo(1);
		isTaken = false;
	}
	
	public int getType() {
		return type;
	}
	
	public Semaforo getSemaphore() {
		return semaphore;
	}
}
