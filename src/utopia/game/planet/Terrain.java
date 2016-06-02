package utopia.game.planet;

import java.awt.image.BufferedImage;
import utopia.basic.helpers.Assets;
import utopia.game.buildings.Building;

//Informações sobre as regiões geográficas de um planeta
public class Terrain {
	private final int width; //Tamanho do planeta (em blocos)
	private final int height;
	private final Block[] terrain;
	//private Block[][] terrain; //Matriz com o terreno (converter em array)
	
	
	public Terrain(int width, int height){
		this.width = width;
		this.height = height;
		this.terrain = new Block[width * height];
	}
	
	
	private int getIndexTo(int x, int y){
		//Retorna o índice do bloco na posição X, Y
		return (y * width) + x;
	}
	
	public Block getBlock(int x, int y){
		//Retorna o bloco na posição informada (se existir)
		if (x >= 0 && x < width && y >= 0 && y < height){
			return terrain[getIndexTo(x, y)];
		}
		return null;
	}
	
	public void updateStructures(){
		for (Block bl : terrain){
			//Atualizar todas as estruturas
			Building b = bl.getStructure();
			if (b != null) b.update();
		}
	}
	
	//public boolean generate(long seed);
	
	public boolean loadFromFile(String file){
		//Carrega a imagem informada e converte a cor em atributos de terreno

		BufferedImage map = Assets.openImageFile(file);
		if (map == null) return false; //Erro de carregamento
		if (width != map.getWidth() || height != map.getHeight()) return false; //Imagem de tamanho incorreto
		
		int[] pixels = map.getRGB(0, 0, width, height, null, 0, width);
		int rgb, r, g, b;

		for (int i=0; i<terrain.length; i++){
			rgb = pixels[i];
            r = (rgb >> 16) & 0x000000FF;
            g = (rgb >> 8) & 0x000000FF;
            b = (rgb) & 0x000000FF; //(?)
                
            //R gera BlockTypes
            BlockType bt;
            switch (r) {
			case 10:
				bt = BlockType.LAND;
				break;
			case 20:
				bt = BlockType.LAKE;
				break;
			case 30:
				bt = BlockType.MOSS;
				break;
			case 40:
				bt = BlockType.HOLE;
				break;
			case 50:
				bt = BlockType.MOUNTAIN;
				break;
			default:
				bt = BlockType.NULL;
				break;
			}
            terrain[i] = new Block(bt);
                
            //G gera recursos naturais
            switch (g) {
			case 10:
				terrain[i].setFuel();
				break;
			case 20:
				terrain[i].setOre();
				break;
			default:
				break;
			}
                
            //Fazer algo com o Blue?
		}
		
		return true; //Tudo ok
	}

	public int[][] getTerrainMap(){
		//Versão de testes (que funciona very well)
		int[][] tiles = new int[width][height];
		
		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){
				tiles[x][y] = terrain[getIndexTo(x, y)].getTileID();
			}
		}
		return tiles;
	}

	public int[][] getResourceMap(){
		int[][] tiles = new int[width][height];
		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){
				if (terrain[getIndexTo(x, y)].hasFuel()) tiles[x][y] = 1;
				else if (terrain[getIndexTo(x, y)].hasOre()) tiles[x][y] = 2;
				else tiles[x][y] = 0;
			}
		}
		return tiles;
	}

}