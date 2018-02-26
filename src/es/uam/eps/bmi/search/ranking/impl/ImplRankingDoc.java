package es.uam.eps.bmi.search.ranking.impl;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;

public class ImplRankingDoc extends SearchRankingDoc {
	
	private Index index;
	private ImplRankedDoc doc;
	
	public ImplRankingDoc (Index i, ImplRankedDoc d) {
		
		this.index = i;
		this.doc = d;
	}
	
	public float getScore () {
		return this.doc.getScore();
	}
	
	public String getPath () {
		return this.index.getDocPath (this.doc.getDocID());
	}
	
}
