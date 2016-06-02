package utopia.game.buildings;

//Armazena uma quantidade limitada de recursos (Warehouse, FluxPod, FuelTank, Living Quarters)
public interface Storage {
	
	public boolean isFull();
	
	public int addToStorage(int amount);
	
}
