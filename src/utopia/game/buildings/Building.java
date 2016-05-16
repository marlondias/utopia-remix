package utopia.game.buildings;

public abstract class Building {
	private BuildingType type;
    private int remainingDays; //Dias para o término da obra
    private int currentHP;
    private int repairPrice; //20% do custo inicial
    private boolean finished; //Se já terminou a construção
    private boolean enabled; //Se está funcionando
    private boolean reparable; //Permite reparo se a estrutura tiver HP acima de 33% (alguns nunca permitem)
    
    
    protected Building(BuildingType bt){
    	type = bt;
    	remainingDays = type.getDaysNeeded();
    	currentHP = type.getMaxHP();
    	repairPrice = (int) (type.getPrice() * 0.2);
    	finished = false;
    	enabled = false;
    	reparable = false; //Não pode reparar se não foi construído
    }

    
    public void repairDamage(int value){
    	if (value < 1 || currentHP == 0 || currentHP == type.getMaxHP()) return; //Desnecessário
        //Aumenta o HP e corrige se ultrapassar
    	currentHP += value;
        if (currentHP > type.getMaxHP()) currentHP = type.getMaxHP();
    }
    
    public void causeDamage(int value){
    	if (value < 1 || currentHP == 0) return;

    	//Diminui HP e verifica o estrago
    	currentHP -= value;
        if (currentHP < (type.getMaxHP() / 3)) reparable = false; //Menos de 33% é dano permanente
    	if (currentHP < 1){
    		//Explodiu...
    		currentHP = 0;
    		enabled = false;
    	}
    }
    
    protected abstract void updateDuty(); //Coisas que só a classe derivada pode fazer
    
    private void updateBuild(){
    	if (remainingDays > 0){
    		//Avança um dia na construção
    		remainingDays--;
    		return;
    	}
    	else{
    		if (currentHP < type.getMaxHP()){
        		//Atrasa 1 dia para fazer reparos
    			currentHP = type.getMaxHP();
    			return;
    		}
    		finished = true;
    		reparable = true;
    		enabled = false;
            //Liberar construtores
            //Mudar gráficos
    	}
    }
    
    public void update(){
    	//Atualização diária (tudo)
    	if (!finished){
    		this.updateBuild();
    		return;
    	}
    	
    	//Consome a energia e...
    	this.updateDuty();
    }
    
    public void turnON(){
        if (finished) enabled = true;
    }
    
    public void turnOFF(){
        if (finished) enabled = false;
    }
    
    public boolean isON(){
    	return (finished) ? enabled : false;
    }
    
    public int getPowerDemand(){
    	return (enabled) ? type.getPowerDemand() : 0;
    }
    
    public int getHealthBar(){
        //Retorna a porcentagem de HP
        return (currentHP / type.getMaxHP() * 100);
    }

    public int getRepairPrice(){
    	return (reparable) ? repairPrice : -1;
    }
    


    
    
    protected void showInfo(){
        //PLACEHOLDER
        //Implementar descrição rápida
        //Implementar botão de "More info"
        //Implementar barra de vida
        //Implementar gerenciamento dependendo do tipo da estrutura (Manage energy, manage food, etc)
    }

}
