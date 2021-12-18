package it.univpm.ProgettoEsame.service;


import java.util.Vector;

import org.json.simple.JSONObject;

import it.univpm.ProgettoEsame.model.Evento;
import it.univpm.ProgettoEsame.model.Stato;


public interface TicketmasterService {
	
	public abstract JSONObject getJSONEvento(String stateCode);
	public abstract Stato getStatoAPI(String stateCode);
	public abstract Vector<Evento> getStatoEvents(String stateCode);
	public abstract JSONObject toJSON(Vector<Evento> stato);



}
