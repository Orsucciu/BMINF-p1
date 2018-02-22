package es.uam.eps.bmi.search.index.lucene;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;

import es.uam.eps.bmi.search.index.IndexBuilder;

public class LuceneBuilder implements IndexBuilder {

	private Document getDocument (File file) throws IOException {
		Document doc = new Document ();
		FieldType base = new FieldType ();
		base.setStored(true);
		base.setTokenized(true);
		Field contenido = new Field ("contenido", file.toString(), base);
		Field nombre = new Field ("nombre", file.getName(), base);
		doc.add(contenido);
		doc.add(nombre);
		return doc;
	}
	
	
	
	
	
	
	
	@Override
	public void build(String collectionPath, String indexPath) throws IOException {
		// TODO Auto-generated method stub
		
	}


}
