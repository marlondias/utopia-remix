package utopia.game.planet;

import utopia.basic.GameStateManager;
import utopia.game.buildings.SolarPanel;
import utopia.game.colony.Colony;

//Representa as características gerais de um planeta
public class Planet {
	private String name;
	private long seed;
	private Time time;
	private Terrain terrain;
	private Climate climate;
	private Biosphere biosphere;
	private Colony colony;
	
	
	public Planet(String name, long seed){
		this.name = name;
		this.seed = seed;
		
		time = new Time(1000);
		
		climate = new Climate(this.seed, 0, 30);
		
		terrain = new Terrain(this.seed, 100, 75);
		
		colony = new Colony("The Earthling's Spot", "Garrix");
	}
	
	
	public void updateDay(){
		if (time.update()){
			terrain.update();
			//climate.setSeason(time.getCurrentSeason());
			//climate.update();
			//colony.update();
			
			
			GameStateManager.setDate(time.getCurrentDate());
			GameStateManager.setTemperature(climate.getCurrentTemperature());
		}
	}
	
	public String getName(){
		return name;
	}
	
	public long getSeed(){
		return seed;
	}

	public Time getTime(){
		return time;
	}

	public Terrain getTerrain(){
		return terrain;
	}

	public Colony getColony(){
		return colony;
	}

	
	public void interactionAt(int x, int y){
		//O que acontece quando a tile é clicada
		
		//Verifica se tem veículos primeiro
		
		//Interação com o bloco
		Block bl = terrain.getBlock(x, y);
		if (bl != null) bl.setStructure(new SolarPanel());
		
	}

}
