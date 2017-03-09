package fr.ensai.projetWeb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Data {
	public Ville v1;
	public Ville v2;
	public Theme theme;

	
	public Data(Ville ville1, Ville ville2, Theme t){
		v1=ville1;
		v2=ville2;
		theme=t;
	}

	public void getData() throws IOException {
		rechercherDatasets(v1);
		rechercherDatasets(v2);
		
		System.out.println(v1.name);
		for (Entry<String, String> dataset : v1.datasets.entrySet()) {
			System.out.println(dataset.getKey() +" : "+ dataset.getValue());
			for (Entry<String, String> field : v1.fields.get(dataset.getKey()).entrySet() ) {
				System.out.println(field.getKey() +" : "+ field.getValue());
			}
		}
		
		System.out.println(v2.name);
		for (Entry<String, String> dataset : v2.datasets.entrySet()) {
			System.out.println(dataset.getKey() +" : "+ dataset.getValue());
			for (Entry<String, String> field : v2.fields.get(dataset.getKey()).entrySet() ) {
				System.out.println(field.getKey() +" : "+ field.getValue());
			}
		}
	}

	public void rechercherDatasets(Ville ville) throws IOException {
		String url = ville.adress + "api/datasets/1.0/search/?q=" + theme.name;
		String content = getText(url);
		
		JsonObject json = new JsonParser().parse(content).getAsJsonObject();
		JsonArray datasets = json.get("datasets").getAsJsonArray();
		
		for (JsonElement e : datasets) {
			JsonObject dataset = e.getAsJsonObject();
			JsonObject metas = dataset.get("metas").getAsJsonObject();
			ville.datasets.put(dataset.get("datasetid").getAsString(), metas.get("title").getAsString());
			
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
			ville.fields.put(dataset.get("datasetid").getAsString(), listeFields);
		}
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

    public static void main(String[] args) throws IOException {
    	Data d = new Data(new Ville("Paris"), new Ville("Rennes"), new Theme("naissances"));
    	d.getData();
    }
}
