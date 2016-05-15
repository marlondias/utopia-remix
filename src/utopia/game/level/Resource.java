package utopia.game.level;

//Representa um recurso gerenciado pela colônia (exceto dinheiro e população)
public class Resource {
	private int dailyProduction; //Valores diários de uso
	private int dailyDemand;
	private int currentValue;
	//Enum com o status (ótimo, normal, baixo, crític0)
	//Fila com 10 posições mancando o histórico do CURRENTVALUE (previsóes e gráficos)
	
	
	public Resource(){
		this.dailyProduction = 0;
		this.dailyDemand = 0;
		this.currentValue = 0;
	}
	
	
	public void increaseProduction(int value){
		if (value > 0) dailyProduction += value;
	}
	public void decreaseProduction(int value){
		if (value > 0) dailyProduction -= value;
		if (dailyProduction < 0) dailyProduction = 0;
	}

	public void increaseDemand(int value){
		if (value > 0) dailyDemand += value;
	}
	public void decreaseDemand(int value){
		if (value > 0) dailyDemand -= value;
		if (dailyDemand < 0) dailyDemand = 0;
	}
	
	public void buyResource(int amount){
		if (amount > 0) currentValue += amount;
	}
	public void sellResource(int amount){
		if (amount > 0 && amount <= currentValue) currentValue -= amount;
	}
	
	public void dailyUpdate(){
		currentValue += (dailyProduction - dailyDemand);
	}
	
	public int getCurrentValue(){
		return currentValue;
	}

}
