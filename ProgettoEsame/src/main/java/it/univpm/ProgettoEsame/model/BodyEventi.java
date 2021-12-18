package it.univpm.ProgettoEsame.model;

import java.util.Vector;

public class BodyEventi {
	
	private Vector<String>stateCode;
	private Vector<String>generi;
	private Vector<String>periodo;
	
	public BodyEventi(Vector<String> stateCode, Vector<String> generi, Vector<String> periodo) {
		this.stateCode = stateCode;
		this.generi = generi;
		this.periodo = periodo;
	}
	
	public Vector<String> getStati() {
		return stateCode;
	}

	public Vector<String> getGeneri() {
		return generi;
	}

	public Vector<String> getPeriodo() {
		return periodo;
	}

	public void setStateCode(Vector<String> stateCode) {
		this.stateCode = stateCode;
	}

	public void setGeneri(Vector<String> generi) {
		this.generi = generi;
	}

	public void setPeriodo(Vector<String> periodo) {
		this.periodo = periodo;
	}
	

}
