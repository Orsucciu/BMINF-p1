package es.uam.eps.bmi.search.lucene;

<<<<<<< HEAD
public class LuceneEngine extends AbstractEngine{
	
	private LuceneIndex lucIndex;
	private IndexSearcher indexSearcher;

	public LuceneEngine(String indexPath) throws IOException, NoIndexException {
		super(indexPath);
	}

	@Override
	public SearchRanking search(String query, int cutoff) throws IOException {

		if (this.indexSearcher == null)
			throw new NoIndexException(this.indexFolder);
		
		BooleanQuery.Builder builder = new BooleanQuery.Builder();

		String[] words = query.split(" ");

		for (String s : words) {
			TermQuery tq = new TermQuery(new Term("content", s));
			builder.add(tq, BooleanClause.Occur.SHOULD);
		}

		BooleanQuery pQuery = builder.build();

		// realizamos la busqueda
		TopDocs top = this.indexSearcher.search(pQuery, cutoff);

		return new LuceneRanking(index, top.scoreDocs);
	}

	@Override
	public void loadIndex(String path) throws IOException {

		this.index = new LuceneIndex(path);

		if (this.index != null && this.index.getIndexReader() != null)
			this.indexSearcher = new IndexSearcher(this.index.getIndexReader());
}
=======
public class LuceneEngine extends AbstractEngine {

	private LuceneIndex index;
	private IndexSearcher searcher;
	
	public LuceneEngine (String path) throws IOException {
		super(path)
	}
	
	@Override
	public SearchRanking search (String petition, int quantity) throws IOException {
		
		// we create the query
		
		BooleanQuery.Builder builder = new BooleanQuery.Builder();
		String[] terms = petition.split(" ");
		for (int i = 0; i < terms.length; i++) {
			TermQuery termpetition = new TermQuery (new Term ("content", i));
			builder.add (termpetition);
		}
		
		BooleanQuery query = builder.build();
		TopDocs results = searcher.search (query, quantity);
		return new LuceneRanking (index, results.scoreDocs)
	}
	
	@Override
	public void loadIndex (String path) throws IOException {
		index = new LuceneIndex (path);
		searcher = new IndexSearcher (index.getIndexReader());
	}
	
>>>>>>> 01a1f39c20a483deed1d25c0ebc05ba806e4c958
}
