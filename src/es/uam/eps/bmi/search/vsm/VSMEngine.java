package es.uam.eps.bmi.search.vsm;

public class VSMEngine extends AbstractEngine {
	
	private LuceneIndex index;
	
	public VSMEngine (String path) {
		super (path);
	}
	
	@Override
	public SearchRanking search (String petition, int quantity) {
		ArrayList<ImplRankedDoc> matches = new ArrayList <>;
		String[] words = petition.split(" "); 
		
		for (int i = 0; i < this.index.getIndexReader().numDocs(); i++) {
			float docScore = 0;
			for (int j = 0; j < words.length; j++) {
				float tf = CosineSimilarity.termFrequency (index, words[j], i);
				float idf = CosineSimilarity.inverseDocumentFrequency (index, components[j]);
				docScore += (tf*idf);
			}
			
			if (docScore > 0) matches.add (new ImplRankedDoc(i, docScore));
			if (matches.size() >= quantity) break;
		}
		
		return new ImplRanking (index, matches);
	}
	
}
