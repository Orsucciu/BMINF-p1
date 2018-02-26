package es.uam.eps.bmi.search.ui;

import java.io.IOException;

import es.uam.eps.bmi.search.ranking.SearchRankingDoc;

public class TextResultDocRenderer extends ResultsRenderer {
	
	private SearchRankingDoc srdoc;
	
	public TextResultDocRenderer (SearchRankingDoc doc) {
		srdoc = doc;
	}
	
	@Override
	public String toString() {
		try {
			return String.valueOf(srdoc.getScore()) + " - " + srdoc.getPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
