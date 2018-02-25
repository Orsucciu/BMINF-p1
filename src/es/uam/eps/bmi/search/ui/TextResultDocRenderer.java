package es.uam.eps.bmi.search.ui;

public class TextResultDocRenderer extends ResultsRenderer {
	
	private SearchRankingDoc srdoc;
	
	public TextResultDocRenderer (SearchRankingDoc doc) {
		srdoc = doc;
	}
	
	@Override
	public String toString() {
		return String.valueOf(srdoc.getScore()) + " - " + srdoc.getPath();
	}

}
