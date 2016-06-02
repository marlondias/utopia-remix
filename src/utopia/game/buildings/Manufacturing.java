package utopia.game.buildings;

//Gera novos recursos por conversão de recursos da colônia (Workshop, ArmsLab)
public interface Manufacturing {
	
	public void addInputResources(int amount);
	
	public int getOutputResources();

}
