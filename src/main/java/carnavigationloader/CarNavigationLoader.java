package carnavigationloader;

import java.io.BufferedReader;
import java.io.FileReader;

import com.datastax.astra.client.Collection;
import com.datastax.astra.client.DataAPIClient;
import com.datastax.astra.client.Database;
import com.datastax.astra.client.model.Document;

import static com.datastax.astra.client.model.SimilarityMetric.EUCLIDEAN;

public class CarNavigationLoader {
	
	public static void main(String[] args) {

		String mapFile = "city_map_2";
		String token = System.getenv("ASTRA_DB_TOKEN");
		String endpoint = System.getenv("ASTRA_DB_ENDPOINT");
		
		// Initializing client with a token
		DataAPIClient client = new DataAPIClient(token);

		// Accessing the Database through the HTTP endpoint
		Database db = client.getDatabase(endpoint);

		// Create collection with vector support
		Collection<Document> col = db.createCollection("car_navigation", 4, EUCLIDEAN);

		// load vectors from CSV file
		try {
			BufferedReader reader = new BufferedReader(new FileReader(mapFile + "_vectors.txt"));
			
			boolean skipHeader = true;
			String fileLine = reader.readLine();
			while (fileLine != null) {
				
				if (!skipHeader) {
					String[] columns = fileLine.split(",");
					String id = mapFile + "_" + columns[0];
					int xs = Integer.parseInt(columns[1]);
					int ys = Integer.parseInt(columns[2]);
					int xe = Integer.parseInt(columns[3]);
					int ye = Integer.parseInt(columns[4]);
					
					// Insert record
					col.insertOne(new Document(id).vector(new float[]{xs, ys, xe, ye}).append("map", mapFile));
					System.out.println("Loaded street: " + id);
				} else {
					skipHeader = false;
				}
				
				fileLine = reader.readLine();
			}
			reader.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
	}
}
