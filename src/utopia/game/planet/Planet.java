package utopia.game.planet;

import java.awt.image.BufferedImage;
import utopia.basic.helpers.Assets;
import utopia.game.buildings.Building;

//Representa um planeta e coisas fixas dele
public class Planet {
	private String planetName;
	private int width; //Tamanho do planeta (em blocos)
	private int height;
	private Block[][] terrain; //Matriz com o terreno
	//Matriz com posição de veículos (TBD)
	//Tileset e outros gráficos específicos do planeta (TBD)
	
	
	public Planet(String name, String file){
		planetName = name;
		loadTerrain(file);
	}
	
	
	public boolean loadTerrain(String file){
		//Carrega a imagem informada e converte a cor em atributos de terreno

		BufferedImage map = Assets.openImageFile(file);
		if (map == null) return false; //Erro de carregamento
		
		width = map.getWidth();
		height = map.getHeight();

		terrain = new Block[width][height];
		int rgb, r, g, b;
		
		for (int y=0; y<height; y++){
			for (int x=0; x<width; x++){
				rgb = map.getRGB(x, y);
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
                terrain[x][y] = new Block(bt);
                
                //G gera recursos naturais
                switch (g) {
				case 10:
					terrain[x][y].setFuel();
					break;
				case 20:
					terrain[x][y].setOre();
					break;
				default:
					break;
				}
                
                //Fazer algo com o Blue?
			}
		}
		
		return true; //Tudo ok
	}

	public String getName(){
		return planetName;
	}

	public void updateDay(){
		//Atualiza 1 dia para todas as estruturas
		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){
				Building b = terrain[x][y].getStructure();
				if (b != null) b.update();
			}
		}
	}
	
	
	public int[][] getTerrainMap(){
		//Versão de testes (que funciona very well)
		int[][] tiles = new int[width][height];
		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){
				tiles[x][y] = terrain[x][y].getTileID();
			}
		}
		return tiles;
	}

	public int[][] getResourceMap(){
		int[][] tiles = new int[width][height];
		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){
				if (terrain[x][y].hasFuel()) tiles[x][y] = 1;
				else if (terrain[x][y].hasOre()) tiles[x][y] = 2;
				else tiles[x][y] = 0;
			}
		}
		return tiles;
	}
	
}
