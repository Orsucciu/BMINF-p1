package es.uam.eps.bmi.search.ranking.impl;

public class ImplRanked implements Comparable<ImplRanked>{
	private int id;
	private float score;
	
	public ImplRanked (int i, float s) {
		id = i;
		score = s;
	}
	
	public int getId () {
		return id;
	}
	
	public void setId (int i) {
		id = i;
	}
	
	public float getScore () {
		return score;
	}
	
	public void setScore (float s) {
		score = s;
	}
	
	@Override
	public int compareTo (ImplRanked o) {
		return Float.compare(o.score, score);
	}
}
