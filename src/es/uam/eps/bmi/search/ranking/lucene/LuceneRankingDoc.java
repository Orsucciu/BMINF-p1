package es.uam.eps.bmi.search.ranking.lucene;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import java.io.IOException;
import org.apache.lucene.search.ScoreDoc;

/**
 *
 * @author pablo
 */
public class LuceneRankingDoc extends SearchRankingDoc {
    Index index;
    ScoreDoc rankedDoc;
    
    public LuceneRankingDoc (Index index, ScoreDoc scoredoc) {
        this.index = index;
        this.rankedDoc = scoredoc;
    }
    public double getScore() {
        return rankedDoc.score;
    }

    public String getPath() throws IOException {
        return index.getDocPath(rankedDoc.doc);
    }
}
