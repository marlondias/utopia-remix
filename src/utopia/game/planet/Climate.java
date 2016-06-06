package utopia.game.planet;

import java.util.Random;

//Tudo que possa afetar as condições de um planeta
public class Climate {
	public enum Season{
		EQUIAT, SOLEK, EQUIOT, WANEK; //Primavera, verão, outono, inverno
	}
	
	private final Random randomizer;
	private final float minTemperature, temperatureRange; //Temperatura global (graus)
	private final float averageHumidity; //Umidade média do ar
	private final float averageTemperature; //Temperatura média (afetada por poluição)
	private final float warmingFactor; //Efeito sobre temperatura no verão
	private final float coolingFactor; //Efeito sobre temperatura no inverno/eclipse
	private final float wildness; //Tendência em mudar bruscamente de valores
	
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
		averageHumidity = 0.5F;//0.2F + (randomizer.nextFloat() * 0.7F); //Entre 20 e 90%
		warmingFactor = 0.3F + (randomizer.nextFloat() * 0.7F); //Entre 30 e 100%
		coolingFactor = 0.34F + (randomizer.nextFloat() * 0.66F); //Entre 34 e 100%
		wildness = 0.2F + (randomizer.nextFloat() * 0.5F); //Entre 20 e 70%
		
		currentHumidity = averageHumidity;
		currentTemperature = averageTemperature;
	}

	
	private void updateHumidity(){
		float factor = randomizer.nextFloat(); //Usado para descobrir a direção da mudança
		float amount = 0.1F; //Variação em relação à média (normal: 10 a 20 / calor: 30 a 40 / frio: 0 a 20)

		//Amplia variação
		if (randomizer.nextBoolean()) amount += randomizer.nextFloat() * 0.1F; //Variação normal (até 20%)
		
		//No calor amplia a variação, no frio reduz
		if (currentTemperature > averageTemperature + 0.15F) amount += randomizer.nextFloat() * 0.2F;
		else if (currentTemperature < averageTemperature - 0.15F) amount -= randomizer.nextFloat() * 0.1F;

		
		/*if (warmingFactor > coolingFactor){
			//Clima úmido
			factor += 0.12F;
			if (factor > 1.0F) factor = 1.0F;
		}*/
		
		//Verifica como a próxima medição vai variar
		if (factor < 0.35) nextHumidity = currentHumidity - amount;
		else if (factor < 0.64) nextHumidity = currentHumidity;
		else nextHumidity = currentHumidity + amount;
		
		//Corrige limites
		if (nextHumidity > 1.0F) nextHumidity = 1.0F;
		if (nextHumidity < 0F) nextHumidity = 0F;
		
		//Modifica a medição atual de forma ponderada (considerando o extremismo)
		currentHumidity = (float)((currentHumidity * (1.0 - wildness)) + (nextHumidity * wildness));
	}

	private void updateTemperature(){
		float factor = randomizer.nextFloat(); //Usado para descobrir a direção da mudança
		float seasonMod = 0F; //Usado para alterar os valores de comparação
		float amount = 0.05F; //Variação em relação à média (5 a 35%)
		
		//Amplia variação
		if (randomizer.nextBoolean()){
			amount += randomizer.nextFloat() * 0.1F; //Variação normal (até 15%)
			if (randomizer.nextBoolean()) amount += randomizer.nextFloat() * 0.2F; //Variação extrema (até 35%)
		}

		//Modifica a comparação em casos especiais
		if (season.equals(Season.WANEK) || isEclipsed) seasonMod = 0.31F * coolingFactor;
		else if (season.equals(Season.SOLEK)) seasonMod = -0.31F * warmingFactor;

		//Verifica como a próxima medição vai variar
		if (factor < 0.33 + seasonMod) nextTemperature = currentTemperature - amount;
		else if (factor < 0.66 + seasonMod) nextTemperature = currentTemperature;
		else nextTemperature = currentTemperature + amount;

		//Mantêm o valor próximo à média
		if (season != Season.SOLEK && season != Season.WANEK) nextTemperature = (nextTemperature * 0.85F) + (averageTemperature * 0.15F);
		else nextTemperature = (nextTemperature * 0.92F) + (averageTemperature * 0.08F);
		
		//Corrige limites
		if (nextTemperature > 1.0F) nextTemperature = 1.0F;
		if (nextTemperature < 0F) nextTemperature = 0F;
		
		//Modifica a medição atual de forma ponderada (considerando o extremismo)
		currentTemperature = (float)((currentTemperature * (1.0 - wildness)) + (nextTemperature * wildness));
	}

	public void update(){
		updateHumidity();
		updateTemperature();
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
