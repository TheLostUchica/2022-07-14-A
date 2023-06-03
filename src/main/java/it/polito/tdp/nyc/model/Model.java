package it.polito.tdp.nyc.model;

import java.util.*;


import it.polito.tdp.nyc.db.NYCDao;
import org.jgrapht.*;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Model {
	
	NYCDao dao;
	Graph<String, DefaultWeightedEdge> grafo;
	List<Coppia> coppie;
	Simulator simulator;
	
	public Model() {
		dao = new NYCDao();
	}
	
	public List<String> getBorghi(){
		return dao.getBorghi();
	}
	
	public List<String> getNTA(String borgo){
		return dao.getNTA(borgo);
	}
	
	public void creaGrafo(String borgo) {
		coppie = new LinkedList<>();
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, this.getNTA(borgo));
		for(String nta1 : this.grafo.vertexSet()) {
			for(String nta2 : this.grafo.vertexSet()) {
				if(nta2.compareTo(nta1)>0) {
				if(!grafo.containsEdge(nta1, nta2)) {
					int i = dao.getPeso(nta1, nta2);
					if(i!=0) {
						Coppia c = new Coppia(nta1, nta2, dao.getPeso(nta1, nta2));
						coppie.add(c);
					Graphs.addEdgeWithVertices(this.grafo, c.getNta1(), c.getNta2(), c.getPeso());}
				}
			}
		}
		}
	}
	
	public Graph<String, DefaultWeightedEdge> getGrafo(){
		return this.grafo;
	}
	
	public boolean isGraphLoaded() {
		if(grafo.vertexSet().size()>0) {
			return true;
		}else {
			return false;
		}
	}
	
	private double getPesoMedio() {
		double sum = 0;
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			sum += grafo.getEdgeWeight(e);
		}
		return sum/grafo.edgeSet().size();
	}
	
	public List<Coppia> getTable() {
		double n = this.getPesoMedio();
		System.out.println(n);
		List<Coppia> result = new LinkedList<>();
		for(Coppia c : this.coppie) {
			if (c.getPeso()>n) {
				result.add(c);
			}
		}
		return result;
	}
	
	public void Simula(double p, int d) {
		simulator = new Simulator(p, d, this.grafo);
		simulator.inizialise();
		simulator.run();
	}
	
	public Map<String, Integer> getMapT(){
		return simulator.getTempMap();
	}
	
	public Map<String, Integer> getMapC(){
		return simulator.getCompMap();
	}
}
