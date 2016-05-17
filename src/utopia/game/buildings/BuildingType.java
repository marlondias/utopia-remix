package utopia.game.buildings;

public enum BuildingType {
	SOLAR_PANEL("Solar Panel", "Bla blabla energia limpa, legal!", 800, 7, 1, 1, 0, 30),
	LIVING_QUARTERS("Living Quarters", "Pessoas moram aqui, legal!", 4000, 28, 8, 1, 100, 100),
	MOSS_CONVERTER("Moss Converter", "Nossa! Agora posso respirar gases liberados por musgo, legal!", 1000, 14, 5, 1, 50, 70);
	
	
	private final String name; //Nome da estrutura
	private final String description; //Texto explicativo curto
    private final int price; //Valor da construção
    private final int daysNeeded; //Duração da construção (dias)
    private final int buildersNeeded; //Contrutores necessários para iniciar a obra
    private final int size; //Tamanho da estrutura (em Blocks)
	private final int powerDemand; //Consumo energético (diário)
    private final int maxHP; //HP máximo da estrutura
    //Arquivo com as animações dessa estrutura
    
    private BuildingType(String nam, String desc, int price, int days, int builders, int size, int power, int hp){
    	this.name = nam;
    	this.description = desc;
    	this.price = price;
    	this.daysNeeded = days;
    	this.buildersNeeded = builders;
    	this.size = size;
    	this.powerDemand = power;
    	this.maxHP = hp;
    }

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getPrice() {
		return price;
	}

	public int getDaysNeeded() {
		return daysNeeded;
	}

	public int getBuildersNeeded() {
		return buildersNeeded;
	}

	public int getSize() {
		return size;
	}

	public int getPowerDemand() {
		return powerDemand;
	}

	public int getMaxHP() {
		return maxHP;
	}

}
