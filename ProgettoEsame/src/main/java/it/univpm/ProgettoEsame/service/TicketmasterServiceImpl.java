package it.univpm.ProgettoEsame.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Service;


import it.univpm.ProgettoEsame.model.Evento;
import it.univpm.ProgettoEsame.model.Stato;

@Service
public class TicketmasterServiceImpl implements TicketmasterService {

	private String apikey="gcbb5hGg46qKGJTo1XeuoIY1AK7AgFiL";
	private String url="https://app.ticketmaster.com/discovery/v2/events.json?stateCode=";

	@Override
	public JSONObject getJSONEvento(String stateCode) {
		
		JSONObject evento=null;
		
		try {
			URL u = new URL(this.url+stateCode+"&apikey="+apikey);

			URLConnection openConnection=u.openConnection();
			InputStream input=openConnection.getInputStream();
		
			String dati="";
			String line="";
			
			try {
				InputStreamReader inReader=new InputStreamReader(input);
				BufferedReader buffer=new BufferedReader(inReader);
				
				while((line=buffer.readLine())!=null){
					dati+=line;
				}
			}finally {
				input.close();
			}
				evento=(JSONObject) JSONValue.parseWithException(dati);
				
		}catch(IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return evento;		
	}

	
	@Override
	public Stato getStatoAPI(String stateCode) {

		JSONObject obj=getJSONEvento(stateCode);	
		Stato state=new Stato(stateCode);

		JSONObject embedded1=(JSONObject)obj.get("_embedded");
		JSONArray events=(JSONArray)embedded1.get("events");
		JSONObject evtemp=(JSONObject)events.get(0);
		JSONObject embedded2=(JSONObject)evtemp.get("_embedded");
		JSONArray venues=(JSONArray)embedded2.get("venues");
		JSONObject venuesTemp=(JSONObject)venues.get(0);
		JSONObject st=(JSONObject)venuesTemp.get("state");
		String name=(String) st.get("name");
		String statecode=(String) st.get("stateCode");

		state.setNomeStato(name);
		state.setStateCode(statecode);

		return state;
	}

	@Override
	public Stato getStatoEvents(String stateCode) {

		JSONObject obj=getJSONEvento(stateCode);
		Stato st=new Stato(stateCode);
		st=getStatoAPI(stateCode);

		JSONObject embedded1=(JSONObject)obj.get("_embedded");
		JSONArray events=(JSONArray)embedded1.get("events");

		Vector<Evento> eventi=new Vector<Evento>(events.size());

		for(int i=0;i<events.size();i++) {

			Evento ev=new Evento();
			
			JSONObject currentCont=(JSONObject)events.get(i);
			
			String nome=(String)currentCont.get("name");
			String url=(String)currentCont.get("url");
			
			ev.setNome(nome);
			ev.setUrl(url);

			JSONObject dates=(JSONObject)currentCont.get("dates");
			JSONObject start=(JSONObject)dates.get("start");
			
			LocalDate date=LocalDate.parse((CharSequence) start.get("localDate"));
			
			ev.setDate(date);
			String localtime=(String)start.get("localTime");
			ev.setOra(localtime);

			JSONArray classifications=(JSONArray)currentCont.get("classifications");
			JSONObject classificationsTemp=(JSONObject)classifications.get(0);
			JSONObject genre=(JSONObject)classificationsTemp.get("genre");
			String nameGenre=(String)genre.get("name");
			ev.setGenere(nameGenre);

			JSONObject embedded2=(JSONObject)currentCont.get("_embedded");
			JSONArray venues=(JSONArray)embedded2.get("venues");

			JSONObject venuesTemp=(JSONObject)venues.get(0);
			JSONObject namecity=(JSONObject)venuesTemp.get("city");
			String citta=(String)namecity.get("name");
			ev.setCitta(citta);
			
			eventi.add(ev);
		}
		st.setEvento(eventi);
		return st;
	}


	@SuppressWarnings("unchecked")
	@Override 
	public JSONObject toJSON(Stato stato) {

		JSONObject obj=new JSONObject();

		obj.put("name", stato.getNomeStato());
		obj.put("stateCode", stato.getStateCode());

		JSONArray listaEventi=new JSONArray();
		

		for(int i=0;i<(stato.getEvento().size());i++) {

			JSONObject Ev=new JSONObject();

			Ev.put("name", (stato.getEvento().get(i)).getNome());
			Ev.put("url", (stato.getEvento().get(i)).getUrl());
			Ev.put("city", (stato.getEvento().get(i)).getCitta());
			
			LocalDate localDate = (stato.getEvento().get(i)).getDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String formattedString = localDate.format(formatter);
			
			Ev.put("localDate", formattedString);
			Ev.put("localTime", (stato.getEvento().get(i)).getOra());
			Ev.put("genre", (stato.getEvento().get(i)).getGenere());

			listaEventi.add(Ev);
		}
		
			obj.put("events", listaEventi);

		return obj;
	}
}
