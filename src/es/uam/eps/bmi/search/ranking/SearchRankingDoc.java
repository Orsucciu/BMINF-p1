package es.uam.eps.bmi.search.ranking;

import java.io.IOException;

import org.apache.lucene.search.ScoreDoc;

public class SearchRankingDoc implements Comparable<SearchRankingDoc> {
	
	private ScoreDoc scoredoc;
	private String path;
	
	public SearchRankingDoc (ScoreDoc scoredoc, String path) {
		this.scoredoc = scoredoc;
		this.path = path;
	}
	
	@Override
	public int compareTo (SearchRankingDoc o) {
		if (this.scoredoc.doc > o.getScoreDoc().doc) return -1;
		else if (this.scoredoc.doc < o.getScoreDoc().doc) return 1;
		return 0;
	}

	public String getPath() throws IOException {
		return path;
	}

	public double getScore() throws IOException {
		return this.scoredoc.score;
	}

	public ScoreDoc getScoreDoc() {
		return this.scoredoc;
}

}
