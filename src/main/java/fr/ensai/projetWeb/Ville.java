package fr.ensai.projetWeb;

import java.util.HashMap;
import java.util.Scanner;

public class Ville {
	public String name ;
	public String adress ;
	
	public HashMap<String, String> datasets = new HashMap<String, String>();
	public HashMap<String, HashMap<String,String>> fields = new HashMap<String,  HashMap<String,String>>();
	
	public String choosenDataset ;
	public String choosenColumn ;

	public Ville(String name){
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
	
	public void chooseDatasets(){	
		int i=0;
		HashMap<Integer, String> id = new HashMap<Integer, String>();
		Scanner sc = new Scanner(System.in);

		//ajouter une exception s'il n'y a pas de datasets pour la ville et le theme choisi
		System.out.println("Voici les données disponibles pour "+name+" : ");
		for(String s : datasets.keySet()){
			System.out.println(i+" : "+datasets.get(s));
			id.put(i, s);
			i++;
		}		
		System.out.println("Veuillez saisir le nombre correspondant à votre choix :");
		int str = sc.nextInt();
		while(str>=id.size()){
			System.out.println("Veuillez choisir un nombre entre 0 et "+(id.size()-1));
			str = sc.nextInt();
		}
		choosenDataset = id.get(str);
	}
	
	public void chooseColumn(){	
		int i=0;
		HashMap<Integer, String> id = new HashMap<Integer, String>();
		Scanner sc = new Scanner(System.in);

		System.out.println("Voici les colonnes disponibles pour les données "+datasets.get(choosenDataset)+" : ");
		for(String s : fields.get(choosenDataset).keySet()){
			System.out.println(i+" : "+fields.get(choosenDataset).get(s));
			id.put(i, s);
			i++;
		}		
		System.out.println("Veuillez saisir le nombre correspondant à votre choix :");
		int str = sc.nextInt();
		while(str>=id.size()){
			System.out.println("Veuillez choisir un nombre entre 0 et "+(id.size()-1));
			str = sc.nextInt();
		}
		choosenColumn = id.get(str);
	}

}
