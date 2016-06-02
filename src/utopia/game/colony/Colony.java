package utopia.game.colony;

public class Colony {
	private String leaderName;
	private String colonyName;
	private int population;
	private int money;
	//TODO: Tempo decorrido para a população e linha do tempo com eventos importantes
	//TODO: Posição e tipo dos veículos aliados e inimigos (military map)
	//TODO: Raças do player e do inimigo alien
	//TODO: Lista de achievements (dessa colônia)
	//TODO: Lista de tecnologias disponíveis
	//TODO: Lista de estruturas disponíveis
	//TODO: Lista de condições ou emergências atuais (terrorismo, fome, falta de O2, blackout, acidente..)

	private Resource energy = new Resource(); //Recursos em posse da colônia
	private Resource water = new Resource();
	private Resource food = new Resource();
	//Fuel, ore, gems, tech, weapons
	
	
	public Colony(String colony, String leader){
		colonyName = colony;
		leaderName = leader;
	}
	
	
	public void update(){
		//Atualizar veículos, população, finanças, recursos, comércio, estatísticas...
	}
	
	public String getColonyName(){
		return colonyName;
	}
	
	public String getLeaderName(){
		return leaderName;
	}

}
