package fr.ensai.projetWeb;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.script.*;
import org.renjin.script.*;

public class App {

	public static void main(String[] args) throws IOException {
		//main method to launch everything

		//ici, le DSL devra remplacer "Paris", "Rennes" et "naissances" 
		//par ce que l'utilisateur aura écrit en utilisantnotre langage
		Data d = new Data(new Ville("rennes"), new Ville("rennes"), new Theme("election"));

		d.getData();

		//choix par l'utilisateur des datasets pour chaque ville
		chooseDatasets(d);
		System.out.println("Vous avez choisi d'utiliser "+d.v1.datasets.get(d.v1.choosenDataset)+" pour "+d.v1.name
				+" et "+d.v2.datasets.get(d.v2.choosenDataset)+" pour "+d.v2.name);

		//choix par l'utilisateur des colonnes pour chaque dataset
		chooseColumns(d);
		System.out.println("Vous avez choisi d'utiliser la colonne '"+d.v1.fields.get(d.v1.choosenDataset).get(d.v1.choosenColumn)+
				"' des données '"+d.v1.datasets.get(d.v1.choosenDataset)
				+"' de la ville de "+d.v1.name
				+"\n et la colonne '"+d.v2.fields.get(d.v2.choosenDataset).get(d.v2.choosenColumn)
				+"' des données '"+d.v2.datasets.get(d.v2.choosenDataset)
				+"' de la ville de "+d.v2.name);

		/*
		 * TODO
		 * 
		 * calculer la correlation / tracer le graphique (utilisation de Renjin ou JSAT ?) sur les objets
		 * d.v1.getDataset("nom du dataset choisi dans la ville 1").getColumn("nom de la 1e colonne choisi")
		 * d.v1.getDataset("nom du dataset choisi dans la ville 1").getColumn("nom de la 2e colonne choisi")
		 * d.v2.getDataset("nom du dataset choisi dans la ville 1").getColumn("nom de la 3e colonne choisi")
		 * d.v2.getDataset("nom du dataset choisi dans la ville 1").getColumn("nom de la 4e colonne choisi")
		 */


		//exemple pour faire de R en java :
		RenjinScriptEngineFactory factory = new RenjinScriptEngineFactory();
		ScriptEngine engine = factory.getScriptEngine();
		try {
			engine.eval("library(jsonlite)");

			engine.eval("df <- data.frame(x=1:10, y=(1:10)+rnorm(n=10))");
			engine.eval("print(df)");
			engine.eval("print(lm(y ~ x, df))");

			engine.put("toto", d.v1.datasets);
			engine.eval("print(typeof(toto))");


		} catch (ScriptException e) {
			e.printStackTrace();
		}

	}
	
	
	
	public static void chooseDatasets(Data d){	
		int i=0;
		HashMap<Integer, String> id1 = new HashMap<Integer, String>();
		HashMap<Integer, String> id2 = new HashMap<Integer, String>();
		Scanner sc = new Scanner(System.in);

		//ajouter une exception s'il n'y a pas de datasets pour la ville et le theme choisi

		System.out.println("---------------------------------------------------------");
		System.out.println("Voici les données disponibles pour la première ville ("+d.v1.name+") : ");
		System.out.println("---------------------------------------------------------");
		for(String s : d.v1.datasets.keySet()){
			System.out.println(i+" : "+d.v1.datasets.get(s));
			id1.put(i, s);
			i++;
		}

		i=0;
		System.out.println("---------------------------------------------------------");
		System.out.println("Voici les données disponibles pour la seconde ville ("+d.v2.name+") : ");
		System.out.println("---------------------------------------------------------");
		for(String s : d.v2.datasets.keySet()){
			System.out.println(i+" : "+d.v2.datasets.get(s));
			id2.put(i, s);
			i++;
		}
		
		System.out.println("---------------------------------------------------------");
		System.out.println("Veuillez saisir le nombre correspondant à votre choix pour la première ville ("+d.v1.name+") : ");
		System.out.println("---------------------------------------------------------");
		int str = sc.nextInt();
		while(str>=id1.size() || str<0){
			System.out.println("Veuillez choisir un nombre entre 0 et "+(id1.size()-1));
			str = sc.nextInt();
		}
		d.v1.choosenDataset = id1.get(str);
		
		
		System.out.println("---------------------------------------------------------");
		System.out.println("Veuillez saisir le nombre correspondant à votre choix pour la seconde ville ("+d.v1.name+") : ");
		System.out.println("---------------------------------------------------------");
		str = sc.nextInt();
		while(str>=id2.size() || str<0){
			System.out.println("Veuillez choisir un nombre entre 0 et "+(id2.size()-1));
			str = sc.nextInt();
		}
		d.v2.choosenDataset = id2.get(str);
	}
	
	
	
	public static void chooseColumns(Data d){	
		int i=0;
		HashMap<Integer, String> id1 = new HashMap<Integer, String>();
		HashMap<Integer, String> id2 = new HashMap<Integer, String>();
		Scanner sc = new Scanner(System.in);

		System.out.println("---------------------------------------------------------");
		System.out.println("Voici les colonnes disponibles pour les premières données ("+d.v1.datasets.get(d.v1.choosenDataset)+") : ");
		System.out.println("---------------------------------------------------------");
		for(String s : d.v1.fields.get(d.v1.choosenDataset).keySet()){
			System.out.println(i+" : "+d.v1.fields.get(d.v1.choosenDataset).get(s));
			id1.put(i, s);
			i++;
		}
		i=0;
		System.out.println("---------------------------------------------------------");
		System.out.println("Voici les colonnes disponibles pour les secondes données ("+d.v2.datasets.get(d.v2.choosenDataset)+") : ");
		System.out.println("---------------------------------------------------------");
		for(String s : d.v2.fields.get(d.v2.choosenDataset).keySet()){
			System.out.println(i+" : "+d.v2.fields.get(d.v2.choosenDataset).get(s));
			id2.put(i, s);
			i++;
		}
		
		System.out.println("---------------------------------------------------------");
		System.out.println("Veuillez saisir le nombre correspondant à votre choix pour les premières données ("+d.v1.datasets.get(d.v1.choosenDataset)+") : ");
		System.out.println("---------------------------------------------------------");
		int str = sc.nextInt();
		while(str>=id1.size() || str<0){
			System.out.println("Veuillez choisir un nombre entre 0 et "+(id1.size()-1));
			str = sc.nextInt();
		}
		d.v1.choosenColumn = id1.get(str);
		
		System.out.println("---------------------------------------------------------");
		System.out.println("Veuillez saisir le nombre correspondant à votre choix pour les secondes données ("+d.v2.datasets.get(d.v2.choosenDataset)+") : ");
		System.out.println("---------------------------------------------------------");
		str = sc.nextInt();
		while(str>=id2.size() || str<0){
			System.out.println("Veuillez choisir un nombre entre 0 et "+(id2.size()-1));
			str = sc.nextInt();
		}
		d.v2.choosenColumn = id2.get(str);
	}

}
