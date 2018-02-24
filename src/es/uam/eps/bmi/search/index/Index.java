package es.uam.eps.bmi.search.index;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

public interface Index {
	
	public Collection<String> getAllTerms();
	
	public int getTotalFreq(String term);
	
	public Iterable<String> getDocVector(int docID);
}
