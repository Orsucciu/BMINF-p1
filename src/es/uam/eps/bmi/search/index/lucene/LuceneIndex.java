package es.uam.eps.bmi.search.index.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexableFieldType;

import es.uam.eps.bmi.search.index.Index;

public class LuceneIndex implements Index {
	
	private final String pathValue = "Path";
	private final String contentValue = "Content";
	private final Document document = new Document();
	private final TextField path;
	
	public LuceneIndex(File file) throws IOException{
		
		this.path = new TextField(this.pathValue, file.getCanonicalPath(), Field.Store.YES);
		this.document.add(this.path);

		for(TextField field : getFieldsFromFile(file)) {
			this.document.add(field);
		}

	}
	
	public LuceneIndex(ZipFile zip) throws IOException {
		
		this.path = new TextField(this.pathValue, zip.getName(), Field.Store.YES);
		this.document.add(this.path);
		
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
				this.document.add(field);
			}
			stream.close();
	    }
	    
	}
	
	public ArrayList<TextField> getFieldsFromFile(File file) throws IOException {
		
		ArrayList<TextField> fields = new ArrayList<TextField>();
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StringBuffer stringBuffer = new StringBuffer();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			stringBuffer.append(line);
			fields.add(new TextField(this.contentValue, line, Field.Store.YES));
		}
		fileReader.close();
		
		return fields;
	}

}
