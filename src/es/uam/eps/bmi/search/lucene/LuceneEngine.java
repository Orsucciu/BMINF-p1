package es.uam.eps.bmi.search.lucene;

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
	
}
