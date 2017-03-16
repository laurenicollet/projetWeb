package fr.ensai.projetWeb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Data {
	public City c1;
	public City c2;
	public Theme theme;

	
	public Data(City ville1, City ville2, Theme t){
		c1=ville1;
		c2=ville2;
		theme=t;
	}

	public void getData() throws IOException {
		searchDatasets(c1);
		searchDatasets(c2);
	}

	public void searchDatasets(City city) throws IOException {
		String url = city.adress + "api/datasets/1.0/search/?q=" + theme.name;
		String content = getText(url);
		
		JsonObject json = new JsonParser().parse(content).getAsJsonObject();
		JsonArray datasets = json.get("datasets").getAsJsonArray();
		
		for (JsonElement e : datasets) {
			JsonObject dataset = e.getAsJsonObject();
			JsonObject metas = dataset.get("metas").getAsJsonObject();
			city.datasets.put(dataset.get("datasetid").getAsString(), metas.get("title").getAsString());
			
			JsonArray arrayFields = dataset.get("fields").getAsJsonArray();
			HashMap<String, String> listeFields = new HashMap<String, String>(); 
			for (JsonElement el : arrayFields) {
				JsonObject field = el.getAsJsonObject();
				String fieldDescription = "Non renseigné";
				if (!field.get("description").toString().equals("null")) {
					fieldDescription = field.get("description").getAsString();
				} else {
					fieldDescription = field.get("label").getAsString();
				}
				String fieldName = field.get("name").getAsString();
				listeFields.put(fieldName, fieldDescription);
			}
			city.fields.put(dataset.get("datasetid").getAsString(), listeFields);
		}
	}
	
	public void getDatasets(City city) throws IOException {
		String url = city.adress + "api/records/1.0/search/?dataset=" + city.choosenDataset;
		String content = getText(url);
		
		city.data = new JsonParser().parse(content).getAsJsonObject();
	}

	
	public static String getText(String url) throws IOException {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) 
            response.append(inputLine);

        in.close();

        return response.toString();
    }

}
