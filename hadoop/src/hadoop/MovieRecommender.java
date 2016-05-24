package hadoop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.slopeone.SlopeOneRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

public class MovieRecommender {
	static String inputFile = "/home/hadoop/movie/ratings.dat";
	static String outputFile = "/home/hadoop/movie/ratings.csv";
	static String result = "/home/hadoop/movie/recommendations.csv";
	public static void main(String [] args) throws IOException, TasteException{
		Create();
		File rating = new File(outputFile);
		DataModel model = new FileDataModel(rating);
		CachingRecommender re = new CachingRecommender(new SlopeOneRecommender(model));
		LongPrimitiveIterator it = model.getUserIDs(); 
		BufferedWriter record = new BufferedWriter(new FileWriter(result));
		while(it.hasNext()){
			long userid = it.nextLong();
			List<RecommendedItem> recommendations = re.recommend(userid, 10);
			if(recommendations.size()==0){
				System.out.print("User : ");
				System.out.print(userid);
				System.out.println(" : no recommendations");
				record.write(userid+","+"no recommendations");
				record.newLine();
			}
			for(RecommendedItem res : recommendations){
				System.out.print("User ");
				System.out.print(userid+" : ");
				System.out.println(res);
				record.write(userid+","+res);
				record.newLine();
			}
			record.flush();
		}
		record.close();
			
		
	}
	private static void Create() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
		String line = null;
		String line2write = null;
		String [] temp ;
		System.out.println("creating file...");
		while(br.ready()){
			line = br.readLine();
			temp = line.split("::");
			line2write=temp[0]+","+temp[1];
			bw.write(line2write);
			bw.newLine();
			bw.flush();
		}
		br.close();
		bw.close();
		System.out.println("finish");
	}
}
