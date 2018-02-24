package es.uam.eps.bmi.search.index;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

import es.uam.eps.bmi.search.index.freq.FreqVector;
import es.uam.eps.bmi.search.index.freq.TermFreq;

public interface Index {
	
	public Collection<String> getAllTerms();
	
	public int getTotalFreq(String term);
	
	public FreqVector getDocVector(int docID);

	public String getDocPath(int docID);

	public String getTermFreq(String word, int docID);

	public String getDocFreq(String word);
}
