package es.uam.eps.bmi.search.ranking.impl;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;

public class ImplRankingDoc extends SearchRankingDoc {
	
	private Index index;
	private ImplRanked doc;
	
	public ImplRankingDoc (Index index, ImplRanked doc) {
		this.index = index;
		this.doc = doc;
	}
	
	public double getCount () {
		return this.doc.getCount();
	}
	
	public String getPath () {
		return this.index.getDocPath (this.doc.getId());
	}
	
}
