package utopia.game.planet;

import utopia.basic.GameStateManager;
import utopia.game.buildings.SolarPanel;
import utopia.game.colony.Colony;

/***
 * Reune as características gerais do planeta de jogo. Processos e eventos de nível global são iniciados
 * aqui e delegados às classes correspondentes.
 * 
 * TODO: customName vs nativeName para o título do planeta.
 * 
 * @author marlon
 *
 */
public class Planet {
	private final long seed; //Repassar esse valor para qualquer objeto Random
	private String name; // Nome do planeta
	
	private final Climate climate;
	

	private Time time;
	private Terrain terrain;
	private Colony colony;
	// TODO: Alien races, Biosphere, ColonyStatus, Terrain, Timer, HistoryLine, TimeTravel
	// TODO: Será possível voltar no tempo até 15 dias de jogo, portanto deve haver um registrador de eventos.
	
	
	public Planet(long seed, String name, int seasonsCount){
		this.seed = seed; // Não é necessário checar
		
		// Inicializa nome do planeta (failsafe)
		if(name != null && !name.isEmpty()) this.name = name;
		else this.name = "(unknown)";
		
		// Inicializa clima (quantidade de estações é automática)
		climate = new Climate(seed);
		
		
		
		
		
		
		
		
		
		time = new Time(1000);
				
		terrain = new Terrain(this.seed, 100, 75);
		
		colony = new Colony("The Earthling's Spot", "Garrix");
	}
	
	
	public void updateDay(){
		//TODO: Time deve enviar um sinal quando o dia mudar, as classes devem escutar esse sinal e se virarem
		
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
