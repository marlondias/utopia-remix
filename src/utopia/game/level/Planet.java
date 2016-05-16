package utopia.game.level;

//Representa um planeta e coisas fixas dele
public class Planet {
	private String planetName;
	private Block[][] terrain; //Matriz com o terreno
	//Matriz com posição de estruturas
	//Matriz com posição de veículos
	//Tileset e outros gráficos
	
	
	public Planet(String name){
		this.planetName = name;
		this.terrain = Utils.terrainLoad();
	}
	
	
	public int[][] getTerrainMap(){
		//Versão de testes
		int[][] tiles = new int[35][35];
		
		for(int y=0; y<35; y++){
			for(int x=0; x<35; x++){
				tiles[x][y] = terrain[x][y].getTileID();
			}
		}
		
		return tiles;
	}
	
	

}
