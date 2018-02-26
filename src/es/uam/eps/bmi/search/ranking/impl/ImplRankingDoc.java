package es.uam.eps.bmi.search.ranking.impl;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;

public class ImplRankingDoc extends SearchRankingDoc {
	
	private Index index;
	private ImplRankedDoc doc;
	
	public ImplRankingDoc (Index index, ImplRankedDoc doc) {
		
		this.index = index;
		this.doc = doc;
	}
	
	public double getScore () {
		return this.doc.getScore();
	}
	
	public String getPath () {
		return this.index.getDocPath (this.doc.getDocID());
	}
	
}
