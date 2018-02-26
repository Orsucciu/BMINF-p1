package es.uam.eps.bmi.search.ranking.impl;

import es.uam.eps.bmi.search.ranking.SearchRanking;

public class ImplRanking implements SearchRanking {
	
	private ImplRankingIterator iterator;
	
	public ImplRanking (LuceneIndex index, List<ImplRankedDoc> list) {
		iterator = new ImplRankingIterator (index, list);
	}
	
	public ImplRankingIterator getIterator () {
		return iterator;
	}
	
	public int size () {
		return iterator.results.length();
	}

}
