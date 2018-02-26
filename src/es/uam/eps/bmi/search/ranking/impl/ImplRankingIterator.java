package es.uam.eps.bmi.search.ranking.impl;

import java.util.List;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import es.uam.eps.bmi.search.ranking.SearchRankingIterator;

public class ImplRankingIterator implements SearchRankingIterator {

	ImplRanked results[];
	Index index;
	int n = 0;

	public ImplRankingIterator(Index index, List<ImplRanked> list) {
		this.index = index;
		this.results = list.toArray(new ImplRanked[list.size()]);
	}

	public ImplRankingIterator() {
		this.index = null;
		this.results = new ImplRanked[0];
	}

	@Override
	public boolean hasNext() {
		
		return this.n < this.results.length;
	}

	@Override
	public SearchRankingDoc next() {
		return new ImplRankingDoc(index, results[n++]);
	}

}