package es.uam.eps.bmi.search.index.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import es.uam.eps.bmi.search.index.IndexBuilder;

public class LuceneBuilder implements IndexBuilder {
	
	// la méthode qui renvoie les fields d'une file
	public ArrayList<TextField> getFieldsFromFile(File file) throws IOException {
		
		ArrayList<TextField> fields = new ArrayList<TextField>();
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StringBuffer stringBuffer = new StringBuffer();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			stringBuffer.append(line);
			fields.add(new TextField("content", line, Field.Store.YES));
		}
		fileReader.close();
		
		return fields;
	}
	
	// celle ci renvoie le document créé a partir d'un zip
	public Document getDocument (ZipFile zip) throws IOException {
		
		Document doc = new Document ();
		TextField path = new TextField("path", zip.getName(), Field.Store.YES);
		doc.add(path);
		
		Enumeration<? extends ZipEntry> entries = zip.entries();

	    while(entries.hasMoreElements()){
	        ZipEntry entry = entries.nextElement();
	        InputStream stream = zip.getInputStream(entry);
	        
	        StringBuilder out = new StringBuilder();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
	        String line;
	        try {
	            while ((line = reader.readLine()) != null) {
	                out.append(line);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        File file = new File("");
	        Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
	        writer.write(out.toString());
	        writer.close();

	        for(TextField field : getFieldsFromFile(file)) {
				doc.add(field);
			}
			stream.close();
	    }
	    
	    return doc;
	    
	}
	
	// celle ci renvoie le document à partir d'une file, que ce soit un fichier ou une file
	public Document getDocument (File file) throws IOException{
		
		Document doc = new Document ();
		TextField path = new TextField("path", file.getCanonicalPath(), Field.Store.YES);
		doc.add(path);
		
		if (file.isDirectory()) {
			File dir = new File(file.getCanonicalPath());
			File[] filesInDir = dir.listFiles();
			if (filesInDir != null) {
				for (File child : filesInDir) {
					if (!(child.isDirectory())) {
						for(TextField field : getFieldsFromFile(child)) {
							doc.add(field);
						}
					}
				}
			}
		}
		
		else {
			for(TextField field : getFieldsFromFile(file)) {
				doc.add(field);
			}
		}
		
		return doc;
	}
	
	@Override
	public void build(String collectionPath, String indexPath) throws IOException {
		// TODO Auto-generated method stub
		Directory indexDirectory = FSDirectory.open(Paths.get(indexPath));
		IndexWriter index = new IndexWriter (indexDirectory, new IndexWriterConfig ());
		File file = new File (collectionPath);
		Document doc = getDocument (file);
		index.addDocument(doc);
	}


}
