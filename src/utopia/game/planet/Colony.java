package utopia.game.planet;

import java.util.Date;

public class Colony {
	private int durationOfDay = 1000; //Duração de um dia da colônia (em milisegundos)
	private boolean running = false; //Indica se a atualização ocorre normalmente
	private boolean fast = false; //Dobra velocidade de atualização
	private long time_lastUpdate = 0L;
	private final long DAY_IN_MS = 24 * 60 * 60 * 1000;
	
	private String leaderName;
	private String colonyName;
	private final Planet planet = new Planet("Hempa", "res/planet_hempa.bmp");
	private long colonyTime = 0L; //Tempo decorrido para a população
	//Instância da raça do player (final)
	//Instância da raça alien (final)
	
	private int population;
	private int money;
	//Lista de achievements (da colônia)
	//Lista de eventos ocorridos

	//Lista de tecnologias disponíveis
	//Lista de estruturas disponíveis
	//Lista de condições ou emergências atuais (terrorismo, fome, falta de O2, blackout, acidente..)

	private Resource energy = new Resource(); //Recursos em posse da colônia
	private Resource water = new Resource();
	private Resource food = new Resource();
	//Fuel, ore, gems, tech, weapons
	
	
	public Colony(String colony, String leader){
		colonyName = colony;
		leaderName = leader;
	}
	
	
	public void start(){
		if (colonyTime != 0) return; //Já foi iniciado
		time_lastUpdate = System.currentTimeMillis();
		population = 200;
		money = 100000;
		running = true;
	}
	
	public void pause(){
		running = false;
		fast = false;
	}
	
	public void resume(){
		running = true;
		fast = false;
	}
	
	public void speedUp(){
		fast = true;
	}
	
	public void update(){
		if (!running) return; //Pause ativo
		
		long delta_time = System.currentTimeMillis() - time_lastUpdate;
		if ( (!fast && delta_time > durationOfDay) || (fast && delta_time > (durationOfDay >> 1)) ){
			colonyTime++;
			planet.updateDay();
			time_lastUpdate = System.currentTimeMillis();
		}
			
	}
	
	
	public String getColonyName(){
		return colonyName;
	}
	
	public String getLeaderName(){
		return leaderName;
	}
	
	public Planet getPlanet(){
		return planet;
	}
	
	public Date getColonyDate(){
		return new Date(colonyTime * DAY_IN_MS);
	}
	
}
