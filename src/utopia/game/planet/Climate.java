package utopia.game.planet;

import java.util.Random;

//Tudo que possa afetar as condições de um planeta
public class Climate {
	private Random rand = new Random();
	
	private float minTemperature; //Temperatura global
	private float maxTemperature;
	private float currentTemperature;
	
	private int airPollution = 0;
	private int soilPollution = 0;
	private int rainLevel;
	private int heatVariance; //Velocidade para esquentar/esfriar
	
	//TODO: Eclipses, chuvas, secas, estações
	//TODO: Velocidade do vento, umidade, pressão, composição da atmosfera
	//TODO: UV, efeito estufa, mudanças climáticas, poluição, terremotos, inverno nuclear, meteoritos
	
	
	public Climate(int temperature){
		minTemperature = temperature - 20;
		maxTemperature = temperature + 10;
		
	}
	
	
	public void update(){
		currentTemperature = (float) rand.doubles(minTemperature, maxTemperature).average().getAsDouble();
	}
	
	public float getCurrentTemperature(){
		return currentTemperature;
	}

}
