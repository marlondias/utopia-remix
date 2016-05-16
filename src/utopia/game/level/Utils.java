package utopia.game.level;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

//GAMBIARRIZÉRRIMO
public class Utils {
	public static Random rand = new Random(999999);
	
	public static Block[][] terrainLoad(){
		Block[][] result = new Block[35][35];
		int[][] tipos = loadTerrainFromImage("res/utopia-terreno-teste1.bmp");
		int[][] recursos = loadResourcesFromImage("res/utopia-recursos-teste1.bmp");
		
		for(int y=0; y<35; y++){
			for(int x=0; x<35; x++){
				BlockType bt;
				switch (tipos[x][y]){
					case 1:
						bt = BlockType.LAND;
						break;
					case 2:
						bt = BlockType.LAKE;
						break;
					case 3:
						bt = BlockType.MOSS;
						break;
					case 4:
						bt = BlockType.HOLE;
						break;
					case 5:
						bt = BlockType.MOUNTAIN;
						break;
					default:
						bt = BlockType.NULL;
						break;
				}
				result[x][y] = new Block(bt);
				if (recursos[x][y] == 1) result[x][y].setOre();
				if (recursos[x][y] == 2) result[x][y].setFuel();
			}
		}
		return result;
	}

    private static int[][] loadTerrainFromImage(String filePath){
        BufferedImage img = null;
        try {
			img = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        int height = img.getHeight();
        int width = img.getWidth();
        
        int[][] tiles = new int[width][height];
        int rgb, red, green, blue;
        
        for (int y=0; y<height; y++){
            for (int x=0; x<width; x++){
                //pega um numero enorme de RGB e extrai 3 valores
                rgb = img.getRGB(x, y); 
                red = (rgb >> 16 ) & 0x000000FF;
                green = (rgb >> 8 ) & 0x000000FF;
                blue = (rgb) & 0x000000FF;
                
                //compara os RGB e obtem um numero pra cada tipo de terreno
                if (red == 255 && green == 255 && blue == 255){
                    tiles[x][y] = 1; //branco = LAND
                }
                else if (red == 0 && green == 0 && blue == 255){
                    tiles[x][y] = 2; //azul = LAKE
                }
                else if (red == 0 && green == 255 && blue == 0){
                    tiles[x][y] = 3; //verde = MOSS
                }
                else if (red == 0 && green == 0 && blue == 0){
                    tiles[x][y] = 4; //preto = HOLE
                }
                else if (red == 255 && green == 0 && blue == 0){
                    tiles[x][y] = 5; //vermelho = MOUNTAIN
                }
                else {
                    tiles[x][y] = 0; //erro
                }
            }
        }
        
        return tiles;
    }

    private static int[][] loadResourcesFromImage(String filePath){
        BufferedImage img = null;
        try {
			img = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        int height = img.getHeight();
        int width = img.getWidth();
        
        int[][] tiles = new int[width][height];
        int rgb, red, green, blue;
        
        for (int y=0; y<height; y++){
            for (int x=0; x<width; x++){
                //pega um numero enorme de RGB e extrai 3 valores
                rgb = img.getRGB(x, y); 
                red = (rgb >> 16 ) & 0x000000FF;
                green = (rgb >> 8 ) & 0x000000FF;
                blue = (rgb) & 0x000000FF;
                
                //compara os RGB e obtem um numero pra cada tipo de recurso
                if (red == 255 && green == 255 && blue == 0){
                    tiles[x][y] = 1; //amarelo = ORE
                }
                else if (red == 255 && green == 0 && blue == 255){
                    tiles[x][y] = 2; //magenta = FUEL
                }
                else {
                    tiles[x][y] = 0; //erro
                }
            }
        }
        
        return tiles;
    }

	private static void plantSeed(int value, int[][] target, int width, int height){
		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){
				if (rand.nextBoolean()){
					//Coloca o valor apenas se houver espaço
					if (target[x][y] == 0){
						target[x][y] = value;
						if ((x-1 >= 0) && (target[x-1][y] == 0)) target[x-1][y] = value;
						if ((x+1 < width) && (target[x+1][y] == 0)) target[x-1][y] = value;
						if ((y-1 >= 0) && (target[x][y-1] == 0)) target[x-1][y] = value;
						if ((y+1 < height) && (target[x][y+1] == 0)) target[x-1][y] = value;
					}
				}
			}
		}
	}
	
}
