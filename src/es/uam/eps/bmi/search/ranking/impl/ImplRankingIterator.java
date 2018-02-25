package es.uam.eps.bmi.search.ranking.impl;

public class ImplRankingIterator implements SearchRankingIterator {
	
	ScoreDoc[] results;
	Index index;
	int n = 0;
	
	public ImplRankingIterator (Index i, List <ScoreDoc> l) {
		results = l.toArray (new ScoreDox[l.size()]);
		index = i;
	}
	
	public boolean hasNext() {
		return n < results.length;
		
	}
	
	public ImplRankingDoc next() {
		return new ImplRankingDoc (index, results[n++])
	}

}
