package es.uam.eps.bmi.search.lucene;

import java.io.IOException;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;

import es.uam.eps.bmi.search.AbstractEngine;
import es.uam.eps.bmi.search.index.lucene.LuceneIndex;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.lucene.LuceneRanking;

public class LuceneEngine extends AbstractEngine{
	
	private LuceneIndex index;
	private IndexSearcher searcher;

	public LuceneEngine(String indexPath) throws IOException {
		super(new LuceneIndex(indexPath));
	}

	@Override
	public SearchRanking search(String petition, int quantity) throws IOException {
		
		BooleanQuery.Builder builder = new BooleanQuery.Builder();

		String[] words = petition.split(" ");

		for (String s : words) {
			TermQuery tq = new TermQuery(new Term("content", s));
			builder.add(tq, BooleanClause.Occur.SHOULD);
		}

		BooleanQuery pQuery = builder.build();
		TopDocs top = this.searcher.search(pQuery, quantity);

		return new LuceneRanking((LuceneIndex) index, top.scoreDocs);
	}

	public void loadIndex(String path) throws IOException {

		index = new LuceneIndex(path);

		if (index != null && index.getIndexReader() != null)
			searcher = new IndexSearcher (this.index.getIndexReader());
	}
}
