package es.uam.eps.bmi.search.index.lucene;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Fields;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.FSDirectory;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.freq.FreqVector;
import es.uam.eps.bmi.search.index.freq.lucene.LuceneFreqVector;
import es.uam.eps.bmi.search.index.freq.lucene.LuceneFreqVectorIterator;

public class LuceneIndex implements Index {
	//private ArrayList<IndexReader> indexReaders = new ArrayList<IndexReader>();
	private IndexReader indexReader;
	
	/*
	 * @Param : String representing the path to a folder with indexes files 
	 * 
	 * Constructor of this class : need to iterate through the indexes from the folder given as param
	 */
	public LuceneIndex(String indexPath) throws IOException {
		
		this.indexReader = DirectoryReader.open(FSDirectory.open(FileSystems.getDefault().getPath(indexPath))); //read from disk
		//this.indexReaders.add(indexReader);
	}
	
	@Override
	/*
	 * @Param : None
	 * @Returns : A Collection containing every terms from this LuceneIndex (thus, every terms from a folder)
	 * The terms are returned to ensure they're unique (like, you shouldn't have a list with the same term several times)
	 * 
	 * */
	public Collection<String> getAllTerms() {
		ArrayList<String> terms = new ArrayList<String>();
		
		//for(IndexReader reader : this.indexReaders) {
			try {
				Fields fields = MultiFields.getFields(this.indexReader);
				int i = 0;
				for (String field : fields) {
					System.out.println(i++);
		            Terms currentTerms = fields.terms(field);
		            TermsEnum termsEnum = currentTerms.iterator();
		            int j = 0;
		            while (termsEnum.next() != null) {
		            	System.out.println(j++);
		            	if(terms.contains(termsEnum.term().toString()) == false) {
		            		terms.add(termsEnum.term().utf8ToString()); //this may be wrong
		            	}
		            }
		        }
			} catch (IOException e) {
				e.printStackTrace();
			}
		//}
		
		return terms;
	}

	@Override
	/* 
	 * @Param : a String (represents a term)
	 * @Return : int (represents the number of time the term appeared over all the indexes)
	 * */
	public int getTotalFreq(String term) {
		int count = 0;
		
		//for(IndexReader reader : this.indexReaders) {
			try {
				Fields fields = MultiFields.getFields(this.indexReader);
				
				for (String field : fields) {
		            Terms currentTerms = fields.terms(field);
		            TermsEnum termsEnum = currentTerms.iterator();
		            while (termsEnum.next() != null) {
		            	if(termsEnum.term().toString() == term) {
		            		count++;
		            	}
		            }
		        }
			} catch (IOException e) {
				e.printStackTrace();
			}
		//}
		
		return count;
	}

	@Override
	public FreqVector getDocVector(int docID) {
		
		FreqVector freq = null;
		Terms terms = null;
		try {
			terms = this.indexReader.getTermVector(docID, "content");
			freq = new LuceneFreqVector(terms);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return freq;
	}

	@Override
	//get the path of a doc
	public String getDocPath(int docID) {
		
		try {
			return this.indexReader.document(docID).get("path");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	//get the frequency of a term
	public long getTermFreq(String word, int docID) {
		
		LuceneFreqVector fvector;
		try {
			fvector = new LuceneFreqVector(this.indexReader.getTermVector(docID, "content"));
			LuceneFreqVectorIterator iterator = (LuceneFreqVectorIterator) fvector.iterator();
			
			return iterator.getFreq(word);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	//get the number of docs containing this term
	public int getDocFreq(String word) {

		Term term = new Term("content", word);

		try {
			return this.indexReader.docFreq(term);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public IndexReader getIndexReader () {
		return this.indexReader;
	}

}
