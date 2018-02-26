package es.uam.eps.bmi.search.ranking.impl;

public class ImplRankingDoc extends SearchRankingDoc {
	
	private Index index;
	private ImplRankedDoc doc;
	
	public ImplRankingDoc (Index i, ImplRankedDoc d) {
		index = i;
		doc = d;
	}
	
	public float getScore () {
		return doc.getScore ();
	}
	
	public String getPath () {
		return index.getDocPath (doc.getDocID());
	}
	
}
