package es.uam.eps.bmi.search.ranking.impl;

import java.util.Iterator;
import java.util.List;

import es.uam.eps.bmi.search.index.lucene.LuceneIndex;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;

public class ImplRanking implements SearchRanking {
	
	private ImplRankingIterator iterator;
	
	public ImplRanking (LuceneIndex index, List<ImplRanked> list) {
		iterator = new ImplRankingIterator (index, list);
	}
	
	public ImplRankingIterator getIterator () {
		return iterator;
	}
	
	public int size () {
		return iterator.results.length;
	}

	@Override
	public Iterator<SearchRankingDoc> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
