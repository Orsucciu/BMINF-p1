package es.uam.eps.bmi.search.ranking.lucene;

import java.util.Iterator;

import org.apache.lucene.search.ScoreDoc;

import es.uam.eps.bmi.search.index.lucene.LuceneIndex;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;

public class LuceneRanking implements SearchRanking {
	
	private LuceneRankingIterator iterator;
	
	public LuceneRanking (LuceneIndex index, ScoreDoc[] scores) {
		iterator = new LuceneRankingIterator (index, scores);
	}
	
	public int size () {
		return this.iterator.results.length;
	}

	@Override
	public Iterator<SearchRankingDoc> iterator() {
		
		return this.iterator();
	}

}
