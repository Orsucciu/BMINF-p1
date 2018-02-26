package es.uam.eps.bmi.search.index;

import java.util.Collection;
import es.uam.eps.bmi.search.index.freq.FreqVector;

public interface Index {
	
	public Collection<String> getAllTerms();
	
	public int getTotalFreq(String term);
	
	public FreqVector getDocVector(int docID);

	public String getDocPath(int docID);

	public long getTermFreq(String word, int docID);

	public int getDocFreq(String word);
}
