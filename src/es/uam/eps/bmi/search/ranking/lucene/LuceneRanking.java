package es.uam.eps.bmi.search.ranking.lucene;

public class LuceneRanking implements SearchRanking {
	
	private LuceneRankingIterator iterator;
	
	public LuceneRanking (LuceneIndex index, ScoreDoc[] scores) {
		iterator = new LuceneRankingIterator (index, scores)
	}
	
	@Override
	public int size () {
		return iterator.results.length();
	}

}
