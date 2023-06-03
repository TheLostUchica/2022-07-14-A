package it.polito.tdp.nyc.model;

public class Event implements Comparable<Event>{
	
	enum EventType{
		PUT,
		DELETE
	}
	
	EventType tipo;
	int time;
	int durata;
	String nta;
	
	public Event(EventType tipo, int time, int durata, String nta) {
		super();
		this.tipo = tipo;
		this.time = time;
		this.durata = durata;
		this.nta = nta;
	}
	
	public String getNta() {
		return this.nta;
	}

	public EventType getTipo() {
		return tipo;
	}

	public int getTime() {
		return time;
	}

	public int getDurata() {
		return durata;
	}

	@Override
	public int compareTo(Event o) {
		return this.time-o.time;
	}
	
	
	
	

}
