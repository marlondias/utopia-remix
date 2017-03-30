package utopia.game.planet;

import java.util.Random;

/***
 * Season representa as estações climática do planeta, durante o ano.
 * Na criação, são informados os limites de variação de Humidade, Temperatura e Ventos.
 * Estes são porcentagens, e só são úteis quando multiplicados pelos valores absolutos do planeta.
 * 
 * Cada estação tem seu nível de interpolação. Desse modo, é possível ter mudanças bruscas no Verão
 * e mudanças suaves no Outono, por exemplo. (Não implementado).
 * 
 * 
 * @author marlon
 *
 */
public class Season {
	private final Random randomizer;
	private final String name;
	
	private float temperatureInterpolation; //Suavidade das variações (podem sofrer alterações externas)
	private float humidityInterpolation;
	private float windInterpolation;
	
	private final float minTemperature; //Regula temperatura
	private final float deltaTemperature;
	private float currentTemperature;

	private final float minHumidity; //Regula chuvas e secas
	private final float deltaHumidity;
	private float currentHumidity;

	private final float minWindSpeed; //Regula tempestades, vendavais e poluição
	private final float deltaWindSpeed;
	private float currentWindSpeed;
	
	
	public Season(long seed, String seasonName, float minHumid, float maxHumid, float minTemp, float maxTemp, float minWind, float maxWind){
		randomizer = new Random(seed);
		name = seasonName;
		
		// Configura interpolações iniciais
		temperatureInterpolation = (float)(0.45 + (randomizer.nextFloat() * 0.2)); // .45 a .65
		humidityInterpolation = (float) (0.42 + (randomizer.nextFloat() * 0.13)); // .42 a .55
		windInterpolation = (float)(0.5 + (randomizer.nextFloat() * 0.35)); // .50 a .85

		//Configura umidade
		if (minHumid >= 0F && maxHumid <= 1F && minHumid <= maxHumid){
			this.minHumidity = minHumid;
			this.deltaHumidity = maxHumid - minHumid;
			this.currentHumidity = this.minHumidity + (this.deltaHumidity * 0.5F);
		}
		else{
			throw new IllegalArgumentException("Invalid values for season humidity!");
		}

		//Configura temperatura
		if (minTemp >= 0F && maxTemp <= 1F && minTemp <= maxTemp){
			this.minTemperature = minTemp;
			this.deltaTemperature = maxTemp - minTemp;
			this.currentTemperature = this.minTemperature + (this.deltaTemperature * 0.5F);
		}
		else{
			throw new IllegalArgumentException("Invalid values for season temperature!");
		}

		//Configura ventos
		if (minWind >= 0F && maxWind <= 1F && minWind <= maxWind){
			this.minWindSpeed = minWind;
			this.deltaWindSpeed = maxWind - minWind;
			this.currentWindSpeed = this.minWindSpeed + (this.deltaWindSpeed * 0.5F);
		}
		else{
			throw new IllegalArgumentException("Invalid values for season wind speed!");
		}

	}
	
	
	private float linearInterpolation(float a, float b, float factor){
		return (a * (1F - factor)) + (b * factor);
	}
	
	public void update(){
		//Atualiza o clima
		//TODO: Futuramente, relacionar as condições para criar mais naturalidade
		
		float newTemp = minTemperature + (randomizer.nextFloat() * deltaTemperature);
		currentTemperature = linearInterpolation(currentTemperature, newTemp, temperatureInterpolation);
		
		float newHumid = minHumidity + (randomizer.nextFloat() * deltaHumidity);
		currentHumidity = linearInterpolation(currentHumidity, newHumid, humidityInterpolation);

		float newWind = minWindSpeed + (randomizer.nextFloat() * deltaWindSpeed);
		currentWindSpeed = linearInterpolation(currentWindSpeed, newWind, windInterpolation);
	}
	
	public String getSeasonName(){
		return name;
	}

	public float getTemperatureInterpolation(){
		return temperatureInterpolation;
	}
	
	public float getHumidityInterpolation(){
		return humidityInterpolation;
	}
	
	public float getWindInterpolation(){
		return windInterpolation;
	}
	
	public float getTemperature(){
		return currentTemperature;
	}

	public float getHumidity(){
		return currentHumidity;
	}

	public float getWindSpeed(){
		return currentWindSpeed;
	}

}
