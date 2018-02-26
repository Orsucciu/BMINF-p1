package es.uam.eps.bmi.search.ranking.impl;

public class ImplRanked implements Comparable<ImplRanked>{
	private int id;
	private double count;
	
	public ImplRanked (int i, double c) {
		id = i;
		count = c;
	}
	
	public int getId () {
		return id;
	}
	
	public void setId (int i) {
		id = i;
	}
	
	public double getCount () {
		return count;
	}
	
	public void setCount(double c) {
		count = c;
	}
	
	@Override
	public int compareTo (ImplRanked o) {
		return Double.compare(o.count, count);
	}
}
