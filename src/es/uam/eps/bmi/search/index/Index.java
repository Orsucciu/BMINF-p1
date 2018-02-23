package es.uam.eps.bmi.search.index;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

public interface Index {
	
	public String getAllTerms();
	
	public int getTotalFreq();
}
