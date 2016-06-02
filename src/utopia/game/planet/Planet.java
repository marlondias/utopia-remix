package utopia.game.planet;

import utopia.game.buildings.SolarPanel;
import utopia.game.colony.Colony;

//Representa as características gerais de um planeta
public class Planet {
	private String name;
	private long seed;
	private Time time; //Como relacionar?
	private Terrain terrain;
	private Climate climate;
	private Biosphere biosphere;
	private Colony colony;
	
	
	public Planet(String name, long seed){
		this.name = name;
		this.seed = seed;
		
		time = new Time(1000);
		
		climate = new Climate(20);
		
		terrain = new Terrain(35, 35);
		terrain.loadFromFile("res/planet_hempa.bmp");
		
		colony = new Colony("The Earthling's Spot", "Garrix");
	}
	
	
	public void updateDay(){
		if (time.update()){
			terrain.updateStructures();
			climate.update();
			colony.update();
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
		if (bl == null) return;
		bl.setStructure(new SolarPanel());
		
	}

}
