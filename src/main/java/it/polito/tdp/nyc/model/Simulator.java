package it.polito.tdp.nyc.model;
import java.util.*;
import org.jgrapht.*;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.nyc.model.Event.EventType;

public class Simulator {
	
	Model model;
	Graph<String, DefaultWeightedEdge> grafo;
	
	double p;
	int d;
	
	Map<String, Integer> TempMap;
	Map<String, Integer> CompMap;
	LinkedList<String> ntaa;
	int time = 1;
	int fin= 100;
	
	Queue<Event> queue;
	
	public Simulator(double p, int d, Graph<String, DefaultWeightedEdge> garfo) {
		grafo = garfo;
		this.p = p;
		this.d = d;
		TempMap = new TreeMap<>();
		CompMap = new TreeMap<>();
		queue = new PriorityQueue<>();
		ntaa = new LinkedList<String>(grafo.vertexSet());
		this.map();
	}
	
	public void map() {
		for (String nta : grafo.vertexSet()) {
			TempMap.put(nta, 0);
			CompMap.put(nta, 0);
		}
	}
	
	public void inizialise() {
		while(time<fin+1) {
			if(!(Math.random()>=p)) {
				String nta = ntaa.get((int)(Math.random()*(ntaa.size())));
				queue.add(new Event(EventType.PUT, time, d, nta));
			}
			time++;			
		}
	}
	
	public void run() {
		while(!queue.isEmpty()) {
			processEvent(queue.poll());
		}
	}

	private void processEvent(Event e) {
		
		switch(e.getTipo()) {
		
			
		case PUT:
			String Nta = e.getNta();
			TempMap.put(Nta, TempMap.get(Nta)+1);
			CompMap.put(Nta, CompMap.get(Nta)+1);
			
			queue.add(new Event(EventType.DELETE, time+e.getDurata(), 0, Nta));
			
			if(e.getDurata()>=2) {
			
			List<String> list = new LinkedList<String>(Graphs.neighborListOf(this.grafo, Nta));
			
			DefaultWeightedEdge max = grafo.getEdge(Nta, list.get((int)(Math.random()*(list.size()))));
			
			for(String s : list) {
				DefaultWeightedEdge w = grafo.getEdge(Nta, s);
				if(grafo.getEdgeWeight(w)>grafo.getEdgeWeight(max) && TempMap.get(s)==0){
					max = w;
				}
			}
			
			String s = Graphs.getOppositeVertex(this.grafo, max, Nta);
			queue.add(new Event(EventType.PUT, time, (int)(e.getDurata()/2), s));
			}
			
			break;
			
		case DELETE:
			String Nta1 = e.getNta();
			TempMap.put(Nta1, TempMap.get(Nta1)-1);
			
			break;
		}
	}

	public Map<String, Integer> getTempMap() {
		return TempMap;
	}

	public Map<String, Integer> getCompMap() {
		return CompMap;
	}
	
	
}
