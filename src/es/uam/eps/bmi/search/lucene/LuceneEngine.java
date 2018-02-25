package es.uam.eps.bmi.search.lucene;

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
}
