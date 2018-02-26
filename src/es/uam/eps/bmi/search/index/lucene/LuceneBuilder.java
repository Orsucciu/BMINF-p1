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
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import es.uam.eps.bmi.search.index.IndexBuilder;

public class LuceneBuilder implements IndexBuilder {
	
	// returns fields from a file
	// @Return : Field objects
	// @Param : File object
	public ArrayList<TextField> getFieldsFromFile(File file) throws IOException {
		
		ArrayList<TextField> fields = new ArrayList<TextField>();
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StringBuffer stringBuffer = new StringBuffer();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			stringBuffer.append(line);
		}
		fields.add(new TextField("content", stringBuffer.toString(), Field.Store.YES));
		fileReader.close();
		
		return fields;
	}
	
	// returns documents from a zip
	public ArrayList<Document> getDocuments (ZipFile zip) throws IOException {
		
		ArrayList<Document> doc = new ArrayList<Document> ();
		TextField path = new TextField("path", zip.getName(), Field.Store.YES);
		// doc.add(path);
		
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

	        File file = new File("here");
	        Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
	        writer.write(out.toString());
	        writer.close(); 
	        Document currentDoc = new Document();
	        currentDoc.add(path);
	        
	        for(TextField field : getFieldsFromFile(file)) {
				currentDoc.add(field);
			}
			stream.close();
			doc.add (currentDoc);
	    }
	    
	    return doc;
	    
	}
	
	// returns documents from a file
	public ArrayList<Document> getDocuments (File file) throws IOException{
		
		ArrayList<Document> doc = new ArrayList<Document> ();

		// doc.add(path);
		
		if (file.isDirectory()) {
			File dir = new File(file.getCanonicalPath());
			File[] filesInDir = dir.listFiles();
			if (filesInDir != null) {
				for (File child : filesInDir) {
					Document currentDoc = new Document ();
					TextField path = new TextField("path", child.getCanonicalPath(), Field.Store.YES);
					currentDoc.add(path);
					
					if (!(child.isDirectory())) {
						for(TextField field : getFieldsFromFile(child)) {
							currentDoc.add(field);
						}
					}
					doc.add(currentDoc);
				}
			}
		}
		
		else {
			
			/* Document currentDoc = new Document ();
			TextField path = new TextField("path", file.getCanonicalPath(), Field.Store.YES);
			currentDoc.add (path);
			
			for(TextField field : getFieldsFromFile(file)) {
				currentDoc.add(field);
			}
			
			doc.add(currentDoc); */
			
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line;
			
			while ((line = bufferedReader.readLine()) != null) {
				URL url = new URL(line);
				StringBuffer sb = new StringBuffer();
				TextField path = new TextField("path", line, Field.Store.YES);
		        BufferedReader urlReader = new BufferedReader(new InputStreamReader(url.openStream()));
		        String inputLine;
		        
		        while ((inputLine = urlReader.readLine()) != null)
		        	sb.append(inputLine);
		        
		        urlReader.close();
		        TextField content = new TextField ("content", sb.toString(), Field.Store.YES);
		        Document currentDoc = new Document();
		        currentDoc.add(path);
		        currentDoc.add(content);	
		        doc.add(currentDoc);
			}
			
			bufferedReader.close();
		}
		
		return doc;
	}
	
	@Override
	/*
	 * @Return : void. 
	 * @Param : A path (origin of the files/data to parse) (String), A path (location where the indexWriter will create the files)(String)
	 * 
	 * This will take files, analyze them, and write them as Indexes on the disk
	 * */
	public void build(String collectionPath, String indexPath) throws IOException {
		
		Directory indexDirectory = FSDirectory.open(Paths.get(indexPath));
		IndexWriter index = new IndexWriter (indexDirectory, new IndexWriterConfig ()); //analyzer is included in IndexWriterConfig
		File file = new File (collectionPath);
		
		ArrayList <Document> doc = new ArrayList <Document> ();
		
		if (collectionPath.endsWith(".zip")) { doc = getDocuments (new ZipFile (file)); }
		else { doc = getDocuments (file); }
		for (Document d : doc) { 
			index.addDocument(d); 
		}
		index.close(); 
	}
}
