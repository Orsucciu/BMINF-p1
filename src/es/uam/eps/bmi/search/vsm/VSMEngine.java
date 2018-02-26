package es.uam.eps.bmi.search.vsm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import es.uam.eps.bmi.search.AbstractEngine;
import es.uam.eps.bmi.search.index.lucene.LuceneIndex;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.impl.ImplRanked;
import es.uam.eps.bmi.search.ranking.impl.ImplRanking;

public class VSMEngine extends AbstractEngine {
	
	private LuceneIndex index;
	
	public VSMEngine (LuceneIndex i) throws IOException{
		super (i);
	}
	
	@Override
	public SearchRanking search(String petition, int quantity) throws IOException {

		ArrayList<ImplRanked> results = new ArrayList<>();
		String[] words = petition.split(" ");
		File normFile = new File("index/modulos.txt");
		boolean norm = false;
		FileReader reader;
		BufferedReader bufferedreader = null;
		
		/* si existe un fichero con los módulos, activamos esa funcionalidad */
		if (normFile.exists()) {
			reader = new FileReader("index/modulos.txt");
			bufferedreader = new BufferedReader(reader);
			norm = true;
		}

		for (int i = 0; i < this.index.getIndexReader().numDocs(); i++) {

			double n = 1; 
			double sum = 0;
			for (int j = 0; j < words.length; j++) {

				double tf = tF(this.index, words[j], i);
				double idf = iDF(this.index, words[j]);
				sum += (tf * idf);
			}

			if (norm) {
				String line = bufferedreader.readLine();
				String[] mods = line.split("\t");
				n = Double.valueOf(mods[1]);
			}

			sum = (double) sum / (n * (Math.sqrt(words.length)));
			if (sum > 0) {
				results.add(new ImplRanked(i, sum));
			}
		}

		Collections.sort(results);

		if (norm == true) {
			bufferedreader.close();
		}

		if (results.size() >= quantity)
			return new ImplRanking(index, results.subList(0, quantity));
		else
			return new ImplRanking(index, results);

	}

	public void loadIndex(String path) throws IOException {
		this.index = new LuceneIndex(path);
	}
	
	public double tF(LuceneIndex index, String term, int docID) throws IOException {

		long frec = index.getTermFreq(term, docID);
		if (frec <= 0)
			return 0;

		return 1 + (Math.log(frec) / Math.log(2));
	}


	public double iDF(LuceneIndex index, String term) throws IOException {

		double idf = (double) (Math.log((double) index.getIndexReader().numDocs() / (1 + index.getDocFreq(term)))
				/ Math.log(2));

		return 1 + idf;
}
	
}
