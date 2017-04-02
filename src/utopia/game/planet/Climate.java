package utopia.game.planet;

import java.util.Random;

/***
 * Reúne as condições que possam afetar o clima no planeta. Inclui estações do ano, efeito estufa, 
 * condições naturais de temperatura e pressão, etc.
 * 
 * @author marlon
 *
 */

public class Climate {
	private final Random randomizer;
	private final float minNaturalTemperature; // Medido em ºC
	private final float maxNaturalTemperature;
	private float minTemperature;
	private float deltaTemperature;
	private final float minWindSpeed; // Medido em m/s
	private final float deltaWindSpeed;
	private Season[] seasons; //As estações climáticas. Poderão ser de 2, 4, ou 6.
	private int seasonsCount;
	private int currentSeason = 0;
	
	//TODO: Eclipses, chuvas, secas, estações, vento, pressão, composição da atmosfera
	//TODO: UV, efeito estufa, mudanças climáticas, poluição, terremotos, inverno nuclear, meteoritos, sandstorm
	
	
	public Climate(long seed){
		randomizer = new Random(seed);
		
		// Calcular valores nominais de clima
		minTemperature = -10 - (float)(randomizer.nextDouble() * 60); // -70 a -10
		deltaTemperature = 30 + (float)(randomizer.nextDouble() * 60); // 30 a 90 (variação)
		minNaturalTemperature = minTemperature;
		maxNaturalTemperature = minTemperature + deltaTemperature; // -40 a 80
		
		minWindSpeed = 1 + (float)(randomizer.nextDouble() * 14); // 1 a 15
		deltaWindSpeed = 10 + (float)(randomizer.nextDouble() * 50); // 10 a 60 (variação)
		
		// Criar estações
		seasonsCount = 2 * (1 + randomizer.nextInt(2)); // 2, 4 ou 6
		seasons = new Season[seasonsCount];
		
		if(seasonsCount == 2){
			seasons[0] = new Season(seed, "Dry season", 0.0F, 0.5F, 0.4F, 1.0F, 0.0F, 1.0F);
			seasons[1] = new Season(seed, "Wet season", 0.5F, 1.0F, 0.0F, 0.6F, 0.2F, 0.7F);
		}
		else if(seasonsCount == 4){
			seasons[0] = new Season(seed, "Spring", 0.5F, 0.9F, 0.3F, 0.8F, 0.2F, 0.7F);
			seasons[1] = new Season(seed, "Summer", 0.3F, 1.0F, 0.5F, 1.0F, 0.0F, 0.5F);
			seasons[2] = new Season(seed, "Fall",   0.1F, 0.7F, 0.2F, 0.7F, 0.3F, 1.0F);
			seasons[3] = new Season(seed, "Winter", 0.2F, 0.8F, 0.0F, 0.5F, 0.1F, 0.4F);
		}
		else if(seasonsCount == 6){
			seasons[0] = new Season(seed, "Spring", 0.5F, 0.9F, 0.3F, 0.8F, 0.2F, 0.7F);
			seasons[1] = new Season(seed, "Summer", 0.3F, 1.0F, 0.5F, 1.0F, 0.0F, 0.5F);
			seasons[2] = new Season(seed, "Summall", 0.2F, 0.8F, 0.4F, 0.8F, 0.1F, 0.8F);
			seasons[3] = new Season(seed, "Fall",   0.1F, 0.7F, 0.2F, 0.7F, 0.3F, 1.0F);
			seasons[4] = new Season(seed, "Winter", 0.2F, 0.8F, 0.0F, 0.5F, 0.1F, 0.4F);
			seasons[5] = new Season(seed, "Spinter", 0.4F, 0.8F, 0.2F, 0.6F, 0.2F, 0.5F);
		}

	}


	public float getMinNaturalTemperature(){
		return minNaturalTemperature;
	}
	
	public float getMaxNaturalTemperature(){
		return maxNaturalTemperature;
	}
	
	public float getMinTemperature(){
		return minTemperature;
	}
	
	public float getMaxTemperature(){
		return minTemperature + deltaTemperature;
	}
	
	public float getCurrentTemperature(){
		return minTemperature + (deltaTemperature * seasons[currentSeason].getTemperature());
	}
	
	public float getMinWindSpeed(){
		return minWindSpeed;
	}

	public float getMaxWindSpeed(){
		return minWindSpeed + deltaWindSpeed;
	}
	
	public float getCurrentWindSpeed(){
		return minWindSpeed + (deltaWindSpeed * seasons[currentSeason].getWindSpeed());
	}

	public void update(){
		seasons[currentSeason].update();
		//magic
	}

}