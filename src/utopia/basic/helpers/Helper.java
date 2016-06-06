package utopia.basic.helpers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

//Processos repetitivos que são necessários em várias classes
public class Helper {
	public static final Random random = new Random(1);
	
	
	/**
	 * Carrega um arquivo de imagem do local informado, pode retornar NULL
	 * 
	 * @param file o caminho completo do arquivo
	 * @return BufferedImage com o conteúdo visual da imagem
	 */
	public static BufferedImage openImageFile(String file){
		File f = new File(file);
		BufferedImage img = null;
        try {
			img = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return img;
	}
	
	public static double[][] generateNoiseMap(int width, int height){
		double map[][] = new double[width][height];
		
		//Gera ruido branco
		for (int y=0; y<height; y++){
			for (int x=0; x<width; x++){
				map[x][y] = random.nextDouble();
			}
		}

		for (int y=0; y<height; y++){
			for (int x=0; x<width; x++){
				//Cria média entre o ponto e seus vizinhos imediatos
				double neighborhood = map[x][y];
				if (x-1 >= 0) neighborhood = (neighborhood + map[x-1][y]) / 2;
				if (y-1 >= 0) neighborhood = (neighborhood + map[x][y-1]) / 2;
				if (x+1 < width)  neighborhood = (neighborhood + map[x+1][y]) / 2;
				if (y+1 < height) neighborhood = (neighborhood + map[x][y+1]) / 2;
				map[x][y] = (map[x][y] * 0.5) + (neighborhood * 0.5);
			}
		}
		
		return map;
	}

	
	

}
