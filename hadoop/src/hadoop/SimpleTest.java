package hadoop;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class SimpleTest {
	public static void main(String [] args) throws IOException, TasteException{
		String input = "/home/hadoop/intro.csv";
		DataModel model = new FileDataModel(new File(input));
		UserSimilarity sim = new PearsonCorrelationSimilarity(model);
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, sim, model);
		Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, sim);
		List<RecommendedItem> recommendations = recommender.recommend(3, 2);
		for(RecommendedItem re:recommendations){
			System.out.println(re);
		}
		
	}
}
