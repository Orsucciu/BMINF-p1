package es.uam.eps.bmi.search.index.lucene;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexableFieldType;

import es.uam.eps.bmi.search.index.Index;

public class LuceneIndex implements Index {

	private final Document document = new Document();
	private final TextField path;
	private final TextField content;
	
	public LuceneIndex(File file) throws IOException{
		
		this.content = new TextField("Content", new FileReader(file));
		this.path = new TextField("Path", file.getCanonicalPath(), Field.Store.YES);
		
		this.document.add(this.content);
		this.document.add(this.path);
		
	}

}
