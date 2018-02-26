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
	
	private LuceneIndex lucIndex;
	private IndexSearcher indexSearcher;

	public LuceneEngine(String indexPath) throws IOException {
		super(new LuceneIndex(indexPath));
	}

	@Override
	public SearchRanking search(String query, int cutoff) throws IOException {
		
		BooleanQuery.Builder builder = new BooleanQuery.Builder();

		String[] words = query.split(" ");

		for (String s : words) {
			TermQuery tq = new TermQuery(new Term("content", s));
			builder.add(tq, BooleanClause.Occur.SHOULD);
		}

		BooleanQuery pQuery = builder.build();
		TopDocs top = this.indexSearcher.search(pQuery, cutoff);

		return new LuceneRanking((LuceneIndex) index, top.scoreDocs);
	}

	public void loadIndex(String path) throws IOException {

		this.index = new LuceneIndex(path);

		if (this.index != null && ((IndexSearcher) this.index).getIndexReader() != null)
			this.indexSearcher = new IndexSearcher(((IndexSearcher) this.index).getIndexReader());
	}
}
