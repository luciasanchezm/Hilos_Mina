package mina;

import utils.*;

public class Tonelada {
	private int type;
	public boolean isTaken;
	public static final int TYPE_REGULAR = 1;
	public static final int TYPE_BUENA = 2;
	public static final int TYPE_EXCELENTE = 3;
	
	public Tonelada(int type) {
		this.type = type;
		isTaken = false;
	}
	
	public int getType() {
		return type;
	}
}
