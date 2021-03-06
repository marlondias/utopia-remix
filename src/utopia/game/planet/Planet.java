package utopia.game.planet;

import utopia.game.buildings.SolarPanel;

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
	private final TimeSystem time; // Controla datas e horas
	private final Climate climate;
	
	private Terrain terrain;
	// TODO: Alien races, Biosphere, ColonyStatus, Terrain, Timer, HistoryLine, TimeTravel
	// TODO: Será possível voltar no tempo até 15 dias de jogo, portanto deve haver um registrador de eventos.
	
	
	public Planet(long seed, String name){
		this.seed = seed; // Não é necessário checar
		
		// Inicializa nome do planeta (failsafe)
		if(name != null && !name.isEmpty()) this.name = name;
		else this.name = "(unknown)";
		
		// Inicializa o tempo (avança 2 minutos por tick)
		time = new TimeSystem(this, 720);
		
		// Inicializa clima (quantidade de estações é automática)
		climate = new Climate(seed);
		
		

		
		
		terrain = new Terrain(this.seed, 100, 75);
	}

	
	public long getSeed(){
		return seed;
	}

	public String getName(){
		return name;
	}
	
	public TimeSystem getTime(){
		return time;
	}
	
	public Climate getClimate(){
		return climate;
	}
	
	
	
	
	
	
	public Terrain getTerrain(){
		return terrain;
	}
	
	public void interactionAt(int x, int y){
		//O que acontece quando a tile é clicada
		
		//Verifica se tem veículos primeiro
		
		//Interação com o bloco
		Block bl = terrain.getBlock(x, y);
		if (bl != null) bl.setStructure(new SolarPanel());
		
	}

}
