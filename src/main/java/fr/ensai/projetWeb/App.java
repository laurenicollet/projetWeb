package fr.ensai.projetWeb;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.script.*;
import org.renjin.script.*;

public class App {

	public static void main(String[] args) throws IOException {
		
		//Getting the datasets available for the choosen cities and theme
		Data d = new Data(new City("rennes"), new City("toulouse"), new Theme("election"));
		d.getData();
		
		//Choice by the user of the datasets he wants to use in each city
		chooseDatasets(d);
		System.out.println("You will use '"+d.c1.datasets.get(d.c1.choosenDataset)+"' for "+d.c1.name
				+"\n and '"+d.c2.datasets.get(d.c2.choosenDataset)+"' for "+d.c2.name);
		
		//Choice by the user of the columns he wants to use in each dataset
		chooseColumns(d);
		System.out.println("You will use the column '"+d.c1.fields.get(d.c1.choosenDataset).get(d.c1.choosenColumn)+
				"' of the data '"+d.c1.datasets.get(d.c1.choosenDataset)
				+"' for the city of "+d.c1.name
				+"\n and the column '"+d.c2.fields.get(d.c2.choosenDataset).get(d.c2.choosenColumn)
				+"' of the data '"+d.c2.datasets.get(d.c2.choosenDataset)
				+"' for the city of "+d.c2.name);
		
		//Getting data for the choosen columns and datasets
		d.getDatasets(d.c1);
		d.getDatasets(d.c2);
		
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("Your choice have been taken into account, the computation will start now...");
		System.out.println("-----------------------------------------------------------------------------------");
		//computation of the correlation and plot of the result with R code
		RenjinScriptEngineFactory factory = new RenjinScriptEngineFactory();
	    ScriptEngine engine = factory.getScriptEngine();
	    try {
	    	engine.eval("library(jsonlite)");
			engine.eval("print('The correlation and the plot should be displayed here but the R code does not work yet...')");
			engine.eval("print('Please come back in a few years ! ;) ')");
	    	
			/*
	    	 * TODO
	    	 * 
	    	 * The json d.v1.data et d.v2.data must be parsed with the library jsonlite
	    	 * Then the data must be formated to have the same length (for exemple, one can easily
	    	 * truncate the longest vector at the size of the shortest vector)
	    	 * The correlation can be calculated with the R function 'cor' :
	    	 * engine.eval("cor(data.frame(v1=d.v1.data, v2=d.v2.data), method = 'pearson')")
	    	 * It would also be great to display the plot but the plot function is not implemented
	    	 * in renjin yet.
	    	 * 
	    	 */
	    	
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	    
	}

	
	public static void chooseDatasets(Data d){	
		int i=0;
		HashMap<Integer, String> id1 = new HashMap<Integer, String>();
		HashMap<Integer, String> id2 = new HashMap<Integer, String>();
		Scanner sc = new Scanner(System.in);

		//TODO : add an exception when there's no datasets available for the sity and the theme

		System.out.println("---------------------------------------------------------");
		System.out.println("These datasets are available for the first city ("+d.c1.name+") : ");
		System.out.println("---------------------------------------------------------");
		for(String s : d.c1.datasets.keySet()){
			System.out.println(i+" : "+d.c1.datasets.get(s));
			id1.put(i, s);
			i++;
		}

		i=0;
		System.out.println("---------------------------------------------------------");
		System.out.println("These datasets are available for the second city  ("+d.c2.name+") : ");
		System.out.println("---------------------------------------------------------");
		for(String s : d.c2.datasets.keySet()){
			System.out.println(i+" : "+d.c2.datasets.get(s));
			id2.put(i, s);
			i++;
		}
		
		System.out.println("---------------------------------------------------------");
		System.out.println("Please enter the number of your choice for the first city ("+d.c1.name+") : ");
		System.out.println("---------------------------------------------------------");
		int str = sc.nextInt();
		while(str>=id1.size() || str<0){
			System.out.println("You must choose a number between 0 and "+(id1.size()-1));
			str = sc.nextInt();
		}
		d.c1.choosenDataset = id1.get(str);
		
		
		System.out.println("---------------------------------------------------------");
		System.out.println("Please enter the number of your choice for the second city ("+d.c2.name+") : ");
		System.out.println("---------------------------------------------------------");
		str = sc.nextInt();
		while(str>=id2.size() || str<0){
			System.out.println("You must choose a number between 0 and "+(id2.size()-1));
			str = sc.nextInt();
		}
		d.c2.choosenDataset = id2.get(str);
	}
	
	
	
	public static void chooseColumns(Data d){	
		int i=0;
		HashMap<Integer, String> id1 = new HashMap<Integer, String>();
		HashMap<Integer, String> id2 = new HashMap<Integer, String>();
		Scanner sc = new Scanner(System.in);

		System.out.println("---------------------------------------------------------");
		System.out.println("These columns are available for the first dataset ("+d.c1.datasets.get(d.c1.choosenDataset)+") : ");
		System.out.println("---------------------------------------------------------");
		for(String s : d.c1.fields.get(d.c1.choosenDataset).keySet()){
			System.out.println(i+" : "+d.c1.fields.get(d.c1.choosenDataset).get(s));
			id1.put(i, s);
			i++;
		}
		i=0;
		System.out.println("---------------------------------------------------------");
		System.out.println("These columns are available for the second dataset  ("+d.c2.datasets.get(d.c2.choosenDataset)+") : ");
		System.out.println("---------------------------------------------------------");
		for(String s : d.c2.fields.get(d.c2.choosenDataset).keySet()){
			System.out.println(i+" : "+d.c2.fields.get(d.c2.choosenDataset).get(s));
			id2.put(i, s);
			i++;
		}
		
		System.out.println("---------------------------------------------------------");
		System.out.println("Please enter the number of your choice for the first dataset ("+d.c1.datasets.get(d.c1.choosenDataset)+") : ");
		System.out.println("---------------------------------------------------------");
		int str = sc.nextInt();
		while(str>=id1.size() || str<0){
			System.out.println("You must choose a number between 0 and "+(id1.size()-1));
			str = sc.nextInt();
		}
		d.c1.choosenColumn = id1.get(str);
		
		System.out.println("---------------------------------------------------------");
		System.out.println("Please enter the number of your choice for the second dataset ("+d.c2.datasets.get(d.c2.choosenDataset)+") : ");
		System.out.println("---------------------------------------------------------");
		str = sc.nextInt();
		while(str>=id2.size() || str<0){
			System.out.println("You must choose a number between 0 and "+(id2.size()-1));
			str = sc.nextInt();
		}
		d.c2.choosenColumn = id2.get(str);
	}
}
