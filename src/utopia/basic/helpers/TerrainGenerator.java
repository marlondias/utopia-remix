package utopia.basic.helpers;

import utopia.game.planet.Block;
import utopia.game.planet.BlockType;

//Tem funções para gerar um terreno de tiles
public class TerrainGenerator {
	final static int WATER = 1, LAND = 2, MOUNTAIN = 3, MOUNTAIN_TOP = 4;
	

	private static int[][] convertToTiles(int w, int h, double[][] values){
		//Converte o valor em um inteiro que corresponda a um tipo de tile
		int[][] ret = new int[w][h];
		for (int x=0; x<w; x++){
			for (int y=0; y<h; y++){
				int type;
				if (values[x][y] < 0.5) type = WATER;
				else if (values[x][y] < 0.8) type = LAND;
				else if (values[x][y] < 0.93) type = MOUNTAIN;
				else type = MOUNTAIN_TOP;
				ret[x][y] = type;
			}
		}
		return ret;
	}

	private static boolean[][] getBorders(int width, int height, int[][] tiles){
		//Considera como fronteira qualquer posição onde os vizinhos são de tipos diferentes
		boolean[][] ret = new boolean[width][height];

		//Verifica a matriz ignorando as extremidades (evita excesso de IFs)
		for (int x=1; x<width-1; x++){
			for (int y=1; y<height-1; y++){
				if(tiles[x][y] != WATER) continue;
				
				int center, nw, ne, sw, se, n, s, w, e; //Todos os vizinhos
				center = tiles[x][y];
				n = tiles[x][y-1];
				s = tiles[x][y+1];
				w = tiles[x-1][y];
				e = tiles[x+1][y];
				nw = tiles[x-1][y-1];
				ne = tiles[x+1][y-1];
				sw = tiles[x-1][y+1];
				se = tiles[x+1][y+1];
				
				if (n != center && s != center && w != center && e != center){
					tiles[x][y] = n;
					continue;
				}
				
				if (n != s || w != e || (nw != n && nw != w) || (ne != n && ne != w) || (sw != s && sw != w) || (se != s && se != e)){
					ret[x][y] = true;
				}
			}
		}
		
		for (int x=0; x<width; x++){
			for (int y=0; y<height; y++){
				if (!ret[x][y]) continue;
				
				int neighboorCount = 0;
				if (x > 0 && ret[x-1][y]) neighboorCount++;
				if (x < width && ret[x+1][y]) neighboorCount++;
				if (y > 0 && ret[x][y-1]) neighboorCount++;
				if (y < height && ret[x][y+1]) neighboorCount++;
				
				if (neighboorCount < 2){
					ret[x][y] = false;
					if (tiles[x][y] == WATER) tiles[x][y] = LAND;
				}
			}
		}
		
		return ret;
	}
	
	public static BlockType[][] generateTiles(int width, int height, int octaveCount){
		//TODO: receber SEEDs
		double[][] terrain = PerlinNoise.generatePerlinNoise(123, width, height, octaveCount);

		//Amplia as diferenças
		for (int x=0; x<width; x++){
			for (int y=0; y<height; y++){
				if (terrain[x][y] < 0.5) terrain[x][y] *= 0.2;
				else if (terrain[x][y] >= 0.8) terrain[x][y] = (terrain[x][y] / 2) + 0.5;
			}
		}
		
		int[][] tiles = convertToTiles(width, height, terrain);
		boolean[][] borders = getBorders(width, height, tiles);

		BlockType[][] blocks = new BlockType[width][height];
		for (int x=0; x<width; x++){
			for (int y=0; y<height; y++){
				if (borders[x][y]){
					//Escolher tipo de fronteira
					blocks[x][y] = BlockType.LAND_WATER;
				}
				else{
					//Escolher tipo normal
					if (tiles[x][y] == WATER) blocks[x][y] = BlockType.WATER;
					else if (tiles[x][y] == LAND) blocks[x][y] = BlockType.LAND;
					else if (tiles[x][y] == MOUNTAIN) blocks[x][y] = BlockType.MOUNTAIN;
					else if(tiles[x][y] == MOUNTAIN_TOP) blocks[x][y] = BlockType.MOUNTAIN_TOP;
				}
			}
		}
		
		return blocks;
	}

	public static double[][] generateHeightMap(long seed, int width, int height, int octaveCount){
		double[][] terrain = PerlinNoise.generatePerlinNoise(seed, width, height, octaveCount);
		
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

	
	
	
	private static BlockType[][] generateTerrainMap(long seed, int width, int height, int octaveCount){
		double[][] elevation = PerlinNoise.generatePerlinNoise(seed, width, height, octaveCount);
		BlockType[][] terrain = new BlockType[width][height];
		for (int x=0; x<width; x++){
			for (int y=0; y<height; y++){
				if (elevation[x][y] < 0.5) terrain[x][y] = BlockType.WATER;
				else if (elevation[x][y] < 0.8) terrain[x][y] = BlockType.LAND;
				else if (elevation[x][y] < 0.9) terrain[x][y] = BlockType.MOUNTAIN;
				else terrain[x][y] = BlockType.MOUNTAIN_TOP;
			}
		}
		return terrain;
	}

	private static int[][] generateResourceMap(long seed, int width, int height, int octaveCount){
		double[][] noise = PerlinNoise.generatePerlinNoise(seed, width, height, octaveCount);
		int[][] resources = new int[width][height];
		for (int x=0; x<width; x++){
			for (int y=0; y<height; y++){
				noise[x][y] *= 0.3;
				if (noise[x][y] > 0.19 && noise[x][y] < 0.2) resources[x][y] = 1;
				else if (noise[x][y] > 0.22 && noise[x][y] < 0.25) resources[x][y] = 2;
				else resources[x][y] = 0;
			}
		}
		return resources;
	}
	
	public static Block[][] generatePlanetTerrain(long seed, int width, int height){
		//Gera um terreno usando Perlin Noise
		Block[][] blocks = new Block[width][height];
		BlockType[][] types = generateTerrainMap(seed, width, height, 5);
		int[][] res = generateResourceMap(seed, width, height, 4);
		
		for (int x=0; x<width; x++){
			for (int y=0; y<height; y++){
				blocks[x][y] = new Block(types[x][y]);
				if (res[x][y] == 1) blocks[x][y].setFuel();
				else if (res[x][y] == 2) blocks[x][y].setOre();
			}
		}
		return blocks;
	}


}
