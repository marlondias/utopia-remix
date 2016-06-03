package utopia.game.planet;

import java.util.Random;

//Tudo que possa afetar as condições de um planeta
public class Climate {
	public enum Season{
		EQUIAT, SOLEK, EQUIOT, MEVEK; //Primavera, verão, outono, inverno
	}

	private final Random randomizer;
	private final float minTemperature, temperatureRange; //Temperatura global (graus)
	private final float averageHumidity; //Umidade média do ar
	private final float averageTemperature; //Temperatura média (afetada por poluição)
	private final float extremism; //Tendência em mudar bruscamente de valores
	private final float warmingFactor; //Efeito sobre temperatura no verão
	private final float coolingFactor; //Efeito sobre temperatura no inverno/eclipse
	private Season season = Season.EQUIAT;
	
	private float currentTemperature;
	private float nextTemperature;
	private float currentHumidity;
	private float nextHumidity;
	private boolean isEclipsed;
	//TODO: Eclipses, chuvas, secas, estações, vento, pressão, composição da atmosfera
	//TODO: UV, efeito estufa, mudanças climáticas, poluição, terremotos, inverno nuclear, meteoritos, sandstorm
	
	
	public Climate(long seed, int minTemp, int maxTemp){
		randomizer = new Random(seed);
		minTemperature = minTemp;
		temperatureRange = maxTemp - minTemp;

		averageTemperature = 0.42F + (randomizer.nextFloat() * 0.13F); //Entre 42 e 55%
		averageHumidity = 0.2F + (randomizer.nextFloat() * 0.7F); //Entre 20 e 90%
		extremism = 0.1F + (randomizer.nextFloat() * 0.3F); //Entre 10 e 40%
		warmingFactor = 0.3F + (randomizer.nextFloat() * 0.6F); //Entre 20 e 80%
		coolingFactor = 0.3F + (randomizer.nextFloat() * 0.6F); //Entre 20 e 80%
		
		currentHumidity = averageHumidity;
		currentTemperature = averageTemperature;
	}

	
	private void updateHumidity(){
		float factor = randomizer.nextFloat();
		float variance = 0.12F + (randomizer.nextFloat() * 0.58F); //Entre 12 a 70%

		//Verifica como a próxima medição vai variar em relação a média
		if (factor < 0.35) nextHumidity = averageHumidity - variance;
		else if (factor < 0.64) nextHumidity = averageHumidity;
		else nextHumidity = averageHumidity + variance;
		
		//Corrige limites
		if (nextHumidity > 1.0F) nextHumidity = 1.0F;
		if (nextHumidity < 0F) nextHumidity = 0F;
		
		//Modifica a medição atual de forma ponderada (considerando o extremismo)
		currentHumidity = (float)((currentHumidity * (1.0 - extremism)) + (nextHumidity * extremism));
	}

	private void updateTemperature(){
		float factor = randomizer.nextFloat();
		float variance = 0.15F + (randomizer.nextFloat() * 0.6F); //Entre 15 e 75%
		float seasonMod = 0F;

		//Modifica faixas de comparação em casos especiais
		if (season.equals(Season.MEVEK) || isEclipsed) seasonMod = 0.31F * coolingFactor;
		else if (season.equals(Season.SOLEK)) seasonMod = -0.31F * warmingFactor;

		//Verifica como a próxima medição vai variar em relação a média
		if (factor < 0.35 + seasonMod) nextTemperature = averageTemperature - variance;
		else if (factor < 0.64 + seasonMod) nextTemperature = averageTemperature;
		else nextTemperature = averageTemperature + variance;
		
		//Corrige limites
		if (nextTemperature > 1.0F) nextTemperature = 1.0F;
		if (nextTemperature < 0F) nextTemperature = 0F;
		
		//Modifica a medição atual de forma ponderada (considerando o extremismo)
		currentTemperature = (float)((currentTemperature * (1.0 - extremism)) + (nextTemperature * extremism));
	}

	public void update(){
		updateHumidity();
		updateTemperature();
		
		//Verifica umidade e chuvas
		if (currentHumidity < 0.35F){
			System.out.println("O ar está seco!");
		}
		else if (currentHumidity < 0.6F){
			System.out.println("O ar está agradável!");
		}
		else if (currentHumidity < 0.85F){
			System.out.println("O ar está úmido!");
		}
		else {
			System.out.println("Está chovendo!");
		}
		
	}

	public void setSeason(Season s){
		season = s;
	}

	public float getCurrentHumidity(){
		return currentHumidity * 100;
	}
	
	public float getCurrentTemperature(){
		return (currentTemperature * temperatureRange) + minTemperature;
	}

	public float getMinTemperature(){
		return minTemperature;
	}

	public float getMaxTemperature(){
		return temperatureRange + minTemperature;
	}

}
