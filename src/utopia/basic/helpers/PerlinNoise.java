package utopia.basic.helpers;

import java.util.Random;

public class PerlinNoise {
	private final long seed;
	
	
	public PerlinNoise(long seed){
		this.seed = seed;
	}
	
	
	private double[][] generateWhiteNoise(int width, int height){
		//Gera um array 2D com valores aleatórios entre 0 e 1
		Random random = new Random(seed);
	    double[][] noise = new double[width][height];
	    for (int x=0; x<width; x++){
		    for (int y=0; y<height; y++){
	            noise[x][y] = random.nextDouble();
		    }
	    }
	    return noise;
	}

	private double[][] generateSmoothNoise(double[][] baseNoise, int octave){
		//Gera uma versão suavizada do ruído base, onde a oitava controla a intensidade
		
		/* 
		 * Para criar a oitava k..
		 * 
		 * Para cada ponto [x][y], deve-se samplear o ruído base na posição [x * 2^k][y * 2^k] e
		 * interpolar os outros pontos linearmente.
		 * 
		 * O valor (2^k) representa o comprimento de onda da oitava, e (1 / 2^k) é a sua frequência.
		 * 
		 */
		
		int width = baseNoise.length;
		int height = baseNoise[0].length;
		double[][] smooth = new double[width][height];
		
		int samplePeriod = (1 << octave); //Calcula a 2^k
		double sampleFrequency = 1.0f / samplePeriod; //Calcula 1 / 2^k
		
		for (int x=0; x<width; x++){
			//Calcular os índices horizontais de amostragem (sample)
			int sample_x0 = (x / samplePeriod) * samplePeriod;
			int sample_x1 = (sample_x0 + samplePeriod) % width; //wrap around
			double horizontal_blend = (x - sample_x0) * sampleFrequency;
			
			for (int y=0; y<width; y++){
				//Calcular os índices verticais de amostragem (sample)
				int sample_y0 = (y / samplePeriod) * samplePeriod;
				int sample_y1 = (sample_y0 + samplePeriod) % height; //wrap around
				double vertical_blend = (y - sample_y0) * sampleFrequency;

				//Mistura os dois cantos superiores
		        double top = interpolate(baseNoise[sample_x0][sample_y0], baseNoise[sample_x1][sample_y0], horizontal_blend);

				//Mistura os dois cantos inferiores
		        double bottom = interpolate(baseNoise[sample_x0][sample_y1], baseNoise[sample_x1][sample_y1], horizontal_blend);
		 
				//Mistura final
				smooth[x][y] = interpolate(top, bottom, vertical_blend);
			}
		}
		
		return smooth;
	}

	private double interpolate(double a, double b, double alpha){
		//Interpolação linear de dois valores
		return (a * (1 - alpha)) + (b * alpha);
	}

	/**
	 * Gera um ruído gradiente utilizando o método Perlin Noise, por padrão a interpolação é linear.
	 * 
	 * @param
	 * width e height são as dimensões do array,
	 * octaveCount é o número de oitavas a serem processadas
	 * 
	 * @return double[][] contendo valores entre 0 e 1
	 */
	public double[][] generatePerlinNoise(int width, int height, int octaveCount){
		
		//Gera o ruído base do tamanho desejado
		double[][] baseNoise = generateWhiteNoise(width, height);
		
		//Array com todas as versões suavizadas do ruído base
		double[][][] smoothNoise = new double[octaveCount][][];
		
		double persistance = 0.5f; //Modificador da amplitude
		
		//Gerar um ruído suavizado pra cada oitava
		for (int i=0; i<octaveCount; i++){
			smoothNoise[i] = generateSmoothNoise(baseNoise, i);
		}
		
		//Aloca o array final
		double[][] perlinNoise = new double[width][height];
		
		
		//A amplitude indica o peso que cada oitava terá no resultado final
		float amplitude = 1.0f;
		float totalAmplitude = 0.0f;
		
		//Mistura os ruídos considerando a amplitude
		for (int oct = octaveCount-1; oct >= 0; oct--){
			
			//Amplitude diminui a cada oitava, dependendo do valor de persistência
			amplitude *= persistance;
			
			//O valor usado para multiplicar deve ser armazenado e corrigido pela normalização
			totalAmplitude += amplitude;
			
			for (int x=0; x<width; x++){
				for (int y=0; y<height; y++){
					perlinNoise[x][y] += smoothNoise[oct][x][y] * amplitude;
				}
			}
		}
	 
		//Normalização dos valores (garante que a variação seja entre 0 e 1)
		for (int x=0; x<width; x++){
			for (int y=0; y<height; y++){
				perlinNoise[x][y] /= totalAmplitude;
			}
		}
		
		return perlinNoise;
	}
	
	public double[][] generateTerrain(int width, int height, int octaveCount){
		double[][] terrain = generatePerlinNoise(width, height, octaveCount);
		
		for (int x=0; x<width; x++){
			for (int y=0; y<height; y++){
				double value = terrain[x][y];
				
				if (value < 0.5) value *= 0.2;
				else value = (1 + value) / 2;
				
				terrain[x][y] = value;
			}
		}
		
		return terrain;
	}
}
