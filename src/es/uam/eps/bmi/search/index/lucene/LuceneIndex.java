package es.uam.eps.bmi.search.index.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Fields;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableFieldType;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;

import es.uam.eps.bmi.search.index.Index;

public class LuceneIndex implements Index {
	private ArrayList<IndexReader> indexReaders = new ArrayList<IndexReader>();

	public LuceneIndex(String indexPath) throws IOException {

		IndexReader indexreader = DirectoryReader.open(FSDirectory.open(FileSystems.getDefault().getPath(indexPath))); //read from disk
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
		
		for(IndexReader reader : this.indexReaders) {
			try {
				Fields fields = MultiFields.getFields(reader);
				
				for (String field : fields) {
		            Terms currentTerms = fields.terms(field);
		            TermsEnum termsEnum = currentTerms.iterator();
		            while (termsEnum.next() != null) {
		            	if(terms.contains(termsEnum.term().toString()) == false) {
		            		terms.add(termsEnum.term().toString()); //this may be wrong
		            	}
		            }
		        }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return terms;
	}

	@Override
	/* 
	 * @Param : a String (represents a term)
	 * @Return : int (represents the number of time the term appeared over all the indexes)
	 * */
	public int getTotalFreq(String term) {
		int count = 0;
		
		for(IndexReader reader : this.indexReaders) {
			try {
				Fields fields = MultiFields.getFields(reader);
				
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
		}
		
		return count;
	}

	@Override
	public Iterable<String> getDocVector(int docID) {
		// TODO Auto-generated method stub
		return null;
	}

}
