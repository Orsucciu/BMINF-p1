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

	@Override
	public String getAllTerms() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalFreq() {
		// TODO Auto-generated method stub
		return 0;
	}

}
