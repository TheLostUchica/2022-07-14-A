package it.polito.tdp.nyc.model;

public class Coppia implements Comparable<Coppia>{

	String nta1;
	String nta2;
	int peso;
	public Coppia(String nta1, String nta2, int peso) {
		super();
		this.nta1 = nta1;
		this.nta2 = nta2;
		this.peso = peso;
	}
	public String getNta1() {
		return nta1;
	}
	public String getNta2() {
		return nta2;
	}
	public int getPeso() {
		return peso;
	}
	@Override
	public int compareTo(Coppia o) {
		return o.getPeso()-this.getPeso();
	}
	@Override
	public String toString() {
		return "Coppia [nta1=" + nta1 + ", nta2=" + nta2 + ", peso=" + peso + "]";
	}
	
	
	
	
		

}
