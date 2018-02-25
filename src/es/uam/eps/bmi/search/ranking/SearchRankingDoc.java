package es.uam.eps.bmi.search.ranking;

public class SearchRankingDoc implements Comparable<SearchRankingDoc> {
	
	private ScoreDoc scoredoc;
	private String path;
	
	public SearchRankingDoc (ScoreDoc scoredoc, String path) {
		this.scoredoc = scoredoc;
		this.path = path;
	}
	
	@Override
	public int compareTo (SearchRankingDoc o) {
		if (scoredoc.rankedDoc.doc > o.getScoreDoc().doc) return -1;
		else if (scoredoc.rankedDoc.doc < o.getScoreDoc().doc) return 1;
		return 0;
	}

}
