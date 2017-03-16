package fr.ensai.projetWeb;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import com.google.gson.JsonObject;

public class City {
	public String name ;
	public String adress ;
	
	public HashMap<String, String> datasets = new HashMap<String, String>();
	public HashMap<String, HashMap<String,String>> fields = new HashMap<String,  HashMap<String,String>>();
	
	public String choosenDataset ;
	public String choosenColumn ;
	
	public JsonObject data ;

	public City(String name){
		this.name = name ;

		switch (name.toLowerCase()) {
		case "paris":
			adress = "https://opendata.paris.fr/";
			break;
		case "rennes":
			adress = "https://data.rennesmetropole.fr/";
			break;
		case "toulouse":
			adress = "https://data.toulouse-metropole.fr/";
			break;
		case "lille":
			adress = "https://opendata.lillemetropole.fr/";
			break;
		default: 
			adress = "";
			break;
		}
	}
}
